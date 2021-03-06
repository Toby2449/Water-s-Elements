package com.water.elementmod.entity.boss.nature;

import java.util.ArrayList;
import java.util.List;

import com.water.elementmod.EMCoreItems;
import com.water.elementmod.entity.projectile.EntityFireArrow;
import com.water.elementmod.entity.projectile.EntityPoisonBall;
import com.water.elementmod.particle.EnumCustomParticleTypes;
import com.water.elementmod.particle.ParticleSpawner;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityNatureBoss extends EntityMob implements IRangedAttackMob
{
	private static final DataParameter<Boolean> INITIATE = EntityDataManager.<Boolean>createKey(EntityNatureBoss.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_INVURNERABLE = EntityDataManager.<Boolean>createKey(EntityNatureBoss.class, DataSerializers.BOOLEAN);
	private final EntityAIAttackRanged aiRangedAttack = new EntityAIAttackRanged(this, 1.0D, 80, 1.0F);
    private final EntityAIAttackMelee aiMeleeAttack = new EntityAIAttackMelee(this, 1.5D, false);
	private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.GREEN, BossInfo.Overlay.PROGRESS)).setDarkenSky(false);
	private int crystals_alive;
	private boolean fightActive = false;
	private boolean minions_spawned;
	private BlockPos spawn_location;
	private int ticks_existed_after_vunerable;
	private static List crystal_locations = new ArrayList();
	private int chatted = 0;
	
	public EntityNatureBoss(World worldIn) 
	{
		super(worldIn);
		this.setSpawnLocation(this.getPosition());
		this.setSize(1.5F, 5.0F);
	}
	
	public EntityNatureBoss(World worldIn, BlockPos pos) 
	{
		super(worldIn);
		this.setSpawnLocation(pos);
		this.setSize(1.5F, 5.0F);
	}
	
	public static void registerFixesBoss(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntityNatureBoss.class);
    }
	
	@Override
	protected void initEntityAI()
    {
		this.tasks.addTask(0, new EntityAIWanderAvoidWater(this, 1.0D));
		this.tasks.addTask(1, new EntityNatureBoss.AIDoNothing());
        this.tasks.addTask(2, new EntityAISwimming(this));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    }
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300.0F);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.14000001192092896D);
	}
	
	@Override
	protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(IS_INVURNERABLE, Boolean.valueOf(true));
        this.dataManager.register(INITIATE, Boolean.valueOf(false));
    }
	
	public void arenaInit()
	{
		List<EntityPhotoSynthesizerCrystal> list = this.world.<EntityPhotoSynthesizerCrystal>getEntitiesWithinAABB(EntityPhotoSynthesizerCrystal.class, this.getEntityBoundingBox().grow(_ConfigEntityNatureBoss.ARENA_SIZE_X, _ConfigEntityNatureBoss.ARENA_SIZE_Y, _ConfigEntityNatureBoss.ARENA_SIZE_Z));
		
		for (EntityPhotoSynthesizerCrystal entity : list)
	    {
			if(this.crystal_locations.size() < 4)
			{
				List pos = new ArrayList();
				pos.add(entity.posX);
				pos.add(entity.posY);
				pos.add(entity.posZ);
				this.crystal_locations.add(pos);
			}
	    }
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setBoolean("Invul", this.getInvulState());
        compound.setBoolean("FightState", this.isFightActivated());
        compound.setIntArray("SpawnPos", new int[] {this.getSpawnLocation().getX(), this.getSpawnLocation().getY(), this.getSpawnLocation().getZ()});
        compound.setBoolean("MinionsSpawned", this.minions_spawned);
    }
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setInvulState(compound.getBoolean("Invul"));
        this.setFightState(compound.getBoolean("FightState"));
        int[] pos = compound.getIntArray("SpawnPos");
        BlockPos blockpos = new BlockPos(pos[0], pos[1], pos[2]);
        this.setSpawnLocation(blockpos);
        this.minions_spawned = compound.getBoolean("MinionsSpawned");
        if (this.hasCustomName())
        {
            this.bossInfo.setName(this.getDisplayName());
        }
    }

    /**
     * Sets the custom name tag for this entity
     */
	@Override
    public void setCustomNameTag(String name)
    {
        super.setCustomNameTag(name);
        this.bossInfo.setName(this.getDisplayName());
    }
    
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
    	if(!this.isFightActivated()) 
    	{
	    	this.sayChatLine(0);
	    	this.arenaInit();
	    	this.activateFight();
	    	this.placeDoor();
	    	this.minions_spawned = false;
	    	this.world.playBroadcastSound(1023, new BlockPos(this), 0);
	    	this.fightActive = true;
	    	return super.processInteract(player, hand);
    	}
    	return false;
    }
    
    @Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) 
	{
		this.throwPoisonBall(target);
		this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.AMBIENT, 2.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
	}
	
    @Override
	public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	
        if (this.world.isRemote)
        {
            ParticleSpawner.spawnParticle(EnumCustomParticleTypes.LEAF, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
        }
        
        this.crystals_alive = 0;
        
        if(!this.world.isRemote)
        {
	        List<EntityPhotoSynthesizerCrystal> list = this.world.<EntityPhotoSynthesizerCrystal>getEntitiesWithinAABB(EntityPhotoSynthesizerCrystal.class, this.getEntityBoundingBox().grow(_ConfigEntityNatureBoss.ARENA_SIZE_X, _ConfigEntityNatureBoss.ARENA_SIZE_Y, _ConfigEntityNatureBoss.ARENA_SIZE_Z));
	        
	        for (EntityPhotoSynthesizerCrystal entity : list)
	        {
	        	this.crystals_alive++;
	        }
	        
	        if(this.isFightActivated())
	        {
		        if(this.crystals_alive == 0)
		        {
		        	this.setInvulState(false);
		        	
		        	this.ticks_existed_after_vunerable++;
		        	if(this.ticks_existed_after_vunerable >= 350)
		        	{
		        		if(this.crystal_locations.size() >= 0)
		        		{
				        	for(int l = 0; l < this.crystal_locations.size(); l++) 
				        	{
					        	ArrayList position = (ArrayList) this.crystal_locations.get(l);
					        	
					        	EntityPhotoSynthesizerCrystal entitylg = new EntityPhotoSynthesizerCrystal(this.world, (double)position.get(0), (double)position.get(1) + 1.0D, (double)position.get(2));
					            this.world.spawnEntity(entitylg);
					            
					            this.setInvulState(true);
					            this.minions_spawned = false;
					            this.ticks_existed_after_vunerable = 0;
				        	}
		        		}
		        	}
		        	
		        	if(!this.minions_spawned)
		        	{
		        		if(this.getInvulState() == false)
		        		{
			        		if(this.world.getDifficulty() == EnumDifficulty.EASY)
			        		{
			        			for(int i = 0; i < _ConfigEntityNatureBoss.EASY_MINION_COUNT; i++)
			        			{
						        	EntityNatureBossMinion minion = new EntityNatureBossMinion(this.world);
						        	minion.setPosition(this.posX, this.posY, this.posZ);
							        this.world.spawnEntity(minion);
			        			}
			        		}
			        		else if(this.world.getDifficulty() == EnumDifficulty.NORMAL)
			        		{
			        			for(int i = 0; i < _ConfigEntityNatureBoss.NORMAL_MINION_COUNT; i++)
			        			{
						        	EntityNatureBossMinion minion = new EntityNatureBossMinion(this.world);
						        	minion.setPosition(this.posX, this.posY, this.posZ);
							        this.world.spawnEntity(minion);
			        			}
			        		}
			        		else if(this.world.getDifficulty() == EnumDifficulty.HARD)
			        		{
			        			for(int i = 0; i < _ConfigEntityNatureBoss.HARD_MINION_COUNT; i++)
			        			{
						        	EntityNatureBossMinion minion = new EntityNatureBossMinion(this.world);
						        	minion.setPosition(this.posX, this.posY, this.posZ);
							        this.world.spawnEntity(minion);
			        			}
			        		}
				            
				            this.minions_spawned = true;
		        		}
		        	}
		        }
		        if(this.getInvulState() == true)
	        	{
		        	if(this.world.getDifficulty() == EnumDifficulty.EASY)
	        		{
		        		this.heal(0.01F);
	        		}
	        		else if(this.world.getDifficulty() == EnumDifficulty.NORMAL)
	        		{
	        			this.heal(0.025F);
	        		}
	        		else if(this.world.getDifficulty() == EnumDifficulty.HARD)
	        		{
	        			this.heal(0.035F);
	        		}
	        	}
	    	}
	        
	        // If nobodies in the arena
	        if(this.isFightActivated() && this.fightActive)
	        {
	        	List<EntityPlayer> players = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityNatureBoss.ARENA_SIZE_X, _ConfigEntityNatureBoss.ARENA_SIZE_Y, _ConfigEntityNatureBoss.ARENA_SIZE_Z));
		        
	        	if(players.size() <= 0)
	        	{
	        		this.fightActive = false;
	        		this.resetFight();
	        	}
	        }
	        
	        if(!this.isFightActivated())
	        {
	        	this.setHealth(this.getMaxHealth());
	        }
    	}
        else
        {
        	if(this.isFightActivated())
        	{
	        	this.chatted++;
	        	if(this.chatted >= _ConfigEntityNatureBoss.CHATTED)
	        	{
	        		this.sayChatLine(this.rand.nextInt(4) + 1);
	        		this.chatted = 0;
	        	}
        	}
        }
		
		this.ticksExisted++;
        
        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }
    
    public void resetFight()
    {
    	this.setFightState(false);
    	this.setInvulState(true);
    	this.openDoor();
    	
    	List<EntityPhotoSynthesizerCrystal> list = this.world.<EntityPhotoSynthesizerCrystal>getEntitiesWithinAABB(EntityPhotoSynthesizerCrystal.class, this.getEntityBoundingBox().grow(_ConfigEntityNatureBoss.ARENA_SIZE_X, _ConfigEntityNatureBoss.ARENA_SIZE_Y, _ConfigEntityNatureBoss.ARENA_SIZE_Z));
    	
    	if(!list.isEmpty())
    	{
	    	for (EntityPhotoSynthesizerCrystal entity : list)
	        {
	        	entity.setDead();
	        }
    	}
    	
    	List<EntityNatureBossMinion> list1 = this.world.<EntityNatureBossMinion>getEntitiesWithinAABB(EntityNatureBossMinion.class, this.getEntityBoundingBox().grow(_ConfigEntityNatureBoss.ARENA_SIZE_X, _ConfigEntityNatureBoss.ARENA_SIZE_Y, _ConfigEntityNatureBoss.ARENA_SIZE_Z));
    	
    	if(!list1.isEmpty())
    	{
	    	for (EntityNatureBossMinion entity : list1)
	        {
	    		entity.setDead();
	        }
    	}
    	
    	if(this.crystal_locations.size() >= 0)
		{
        	for(int l = 0; l < this.crystal_locations.size(); l++) 
        	{
	        	ArrayList position = (ArrayList) this.crystal_locations.get(l);
	        	
	        	EntityPhotoSynthesizerCrystal entitylg = new EntityPhotoSynthesizerCrystal(this.world, (double)position.get(0), (double)position.get(1) + 1.0D, (double)position.get(2));
	            this.world.spawnEntity(entitylg);
        	}
		}
    }
	
	@Override
	protected void updateAITasks()
    {
		super.updateAITasks();
		
		if(this.isFightActivated())
		{
			if(this.getInvulState() == true) 
			{
				this.tasks.removeTask(this.aiMeleeAttack);
				this.tasks.addTask(0, this.aiRangedAttack); 
			}
			else 
			{
				this.tasks.removeTask(this.aiRangedAttack);
				this.tasks.addTask(1, this.aiMeleeAttack);
			}
		}
		else
		{
			this.tasks.removeTask(this.aiRangedAttack);
			this.tasks.removeTask(this.aiMeleeAttack);
		}
    }
	
	@Override
	public float getEyeHeight()
	{
		return 4.4F;
	}
	
	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_WITHER_AMBIENT;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundEvents.ENTITY_WITHER_HURT;
	}
	
	@Override
	protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_WITHER_DEATH;
    }
	
	public void sayChatLine(int message)
	{
		if(this.world.isRemote)
		{
			List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityNatureBoss.ARENA_SIZE_X, _ConfigEntityNatureBoss.ARENA_SIZE_Y, _ConfigEntityNatureBoss.ARENA_SIZE_Z));
	    	
	    	if(!list.isEmpty())
	    	{
		    	for (EntityPlayer player : list)
		        {
		    		switch(message)
		    		{
		    		case 0:
		    			player.sendMessage(new TextComponentTranslation("message.em.natureboss.fight_initiate"));
		    			break;
		    		case 1:
		    			player.sendMessage(new TextComponentTranslation("message.em.natureboss.chat1"));
		    			break;
		    		case 2:
		    			player.sendMessage(new TextComponentTranslation("message.em.natureboss.chat2"));
		    			break;
		    		case 3:
		    			player.sendMessage(new TextComponentTranslation("message.em.natureboss.chat3"));
		    			break;
		    		case 4:
		    			player.sendMessage(new TextComponentTranslation("message.em.natureboss.chat4"));
		    			break;
		    		case 5:
		    			player.sendMessage(new TextComponentTranslation("message.em.natureboss.chat5"));
		    			break;
		    		case 6:
		    			player.sendMessage(new TextComponentTranslation("message.em.natureboss.chat6"));
		    			break;
		    		case 7:
		    			player.sendMessage(new TextComponentTranslation("message.em.natureboss.voidentitydeathmessagenature"));
		    			break;
		    		}
		        }
	    	}
		}
	}
	
	/**
     * Add the given player to the list of players tracking this entity. For instance, a player may track a boss in
     * order to view its associated boss bar.
     */
	@Override
    public void addTrackingPlayer(EntityPlayerMP player)
    {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    /**
     * Removes the given player from the list of players tracking this entity. See {@link Entity#addTrackingPlayer} for
     * more information on tracking.
     */
	@Override
    public void removeTrackingPlayer(EntityPlayerMP player)
    {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }
    
    /**
     * Called when the entity is attacked.
     */
	@Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	Entity entity = source.getImmediateSource();
    	
    	if(this.getInvulState() == false)
    	{
	    	if (entity instanceof EntityArrow)
	        {
	    		if(entity instanceof EntityFireArrow)
	    		{
	    			return super.attackEntityFrom(source, 0f);
	    		}
	    		else
	    		{
	            return false;
	    		}
	        }
	    	else
	    	{
	    		return super.attackEntityFrom(source, amount);
	    	}
    	}
	    else
	    {
	    	return false;
	    }
    }
    
    private void throwPoisonBall(EntityLivingBase p_82216_2_)
    {
    	EntityPoisonBall entitywitherskull = new EntityPoisonBall(this.world, this);
        double d0 = p_82216_2_.posY - 1.100000023841858D;
        double d1 = p_82216_2_.posX - this.posX;
        double d2 = d0 - entitywitherskull.posY;
        double d3 = p_82216_2_.posZ - this.posZ;
        float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        entitywitherskull.shoot(d1, d2 + (double)f, d3, 1.6F, 12.0F);
        this.world.spawnEntity(entitywitherskull);
    }
    
    @Override
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);

        this.openDoor();
        
        this.crystal_locations.clear();

        this.sayChatLine(6);
        this.sayChatLine(7);
    }
    
    @Override
    protected int getExperiencePoints(EntityPlayer player)
    {
    	return 750;
    }
    
    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier)
    {
        EntityItem entityitem = this.dropItem(EMCoreItems.HEART_OF_THE_WILD, 1);
        
        if (entityitem != null)
        {
            entityitem.setNoDespawn();
            entityitem.setEntityInvulnerable(true);
        }
    }
    
    public void placeDoor()
    {
    	if(this.getSpawnLocation() != null)
    	{
	    	BlockPos doorpos = this.getSpawnLocation().add(0, 1, 12);
	    	
	    	// initial
	    	this.world.setBlockState(doorpos, Blocks.STONE.getDefaultState());
	    	
	    	// top
	    	this.world.setBlockState(doorpos.add(0, 1, 0), Blocks.STONE.getDefaultState());
	    	
	    	// sides
	    	this.world.setBlockState(doorpos.add(1, 0, 0), Blocks.STONE.getDefaultState());
	    	this.world.setBlockState(doorpos.add(-1, 0, 0), Blocks.STONE.getDefaultState());
	    	
	    	// bottom
	    	this.world.setBlockState(doorpos.add(1, -1, 0), Blocks.STONE.getDefaultState());
	    	this.world.setBlockState(doorpos.add(0, -1, 0), Blocks.STONE.getDefaultState());
	    	this.world.setBlockState(doorpos.add(-1, -1, 0), Blocks.STONE.getDefaultState());
    	}
    }
    
    public void openDoor()
    {
    	if(this.getSpawnLocation() != null)
    	{
	    	BlockPos doorpos = this.getSpawnLocation().add(0, 1, 12);
	    	
	    	// initial
	    	this.world.setBlockToAir(doorpos);
	    	
	    	// top
	    	this.world.setBlockToAir(doorpos.add(0, 1, 0));
	    	
	    	// sides
	    	this.world.setBlockToAir(doorpos.add(1, 0, 0));
	    	this.world.setBlockToAir(doorpos.add(-1, 0, 0));
	    	
	    	// bottom
	    	this.world.setBlockToAir(doorpos.add(1, -1, 0));
	    	this.world.setBlockToAir(doorpos.add(0, -1, 0));
	    	this.world.setBlockToAir(doorpos.add(-1, -1, 0));
    	}
    }
    
    public boolean getInvulState()
    {
        return ((Boolean)this.dataManager.get(IS_INVURNERABLE)).booleanValue();
    }

    public void setInvulState(boolean state)
    {
        this.dataManager.set(IS_INVURNERABLE, Boolean.valueOf(state));
    }
    
    public boolean isFightActivated()
    {
        return ((Boolean)this.dataManager.get(INITIATE)).booleanValue();
    }
    
    public void setFightState(boolean state)
    {
        this.dataManager.set(INITIATE, Boolean.valueOf(state));
    }

    public void activateFight()
    {
        this.dataManager.set(INITIATE, Boolean.valueOf(true));
    }
    
    public void setSpawnLocation(BlockPos pos)
    {
    	this.spawn_location = pos;
    }
    
    public BlockPos getSpawnLocation()
    {
    	return this.spawn_location;
    }
    
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    @Override
    public boolean isNonBoss()
    {
        return false;
    }
    
    class AIDoNothing extends EntityAIBase
    {
        public AIDoNothing()
        {
            this.setMutexBits(7);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            return EntityNatureBoss.this.isFightActivated() == false;
        }
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer entityIn) {}

    @Override
    public boolean canBeCollidedWith() {
    	return true;
    }
    
    @Override
    public boolean canBePushed() {
    	return false; 
    }
    
    @Override
    protected void collideWithEntity(Entity p_82167_1_) {}

    @Override
    protected void collideWithNearbyEntities() {}

	@Override
	public void setSwingingArms(boolean swingingArms) 
	{
		
	}

}
