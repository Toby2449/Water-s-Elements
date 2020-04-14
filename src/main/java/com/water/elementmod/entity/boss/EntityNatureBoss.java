package com.water.elementmod.entity.boss;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicates;
import com.water.elementmod.EMCoreItems;
import com.water.elementmod.entity.ai.EntityAIMeleeCollide;
import com.water.elementmod.entity.monster.EntityWaterSkeleton;
import com.water.elementmod.entity.projectile.EntityFireArrow;
import com.water.elementmod.entity.projectile.EntityPoisonBall;
import com.water.elementmod.particle.EnumCustomParticleTypes;
import com.water.elementmod.particle.ParticleSpawner;
import com.water.elementmod.util.handlers.EMLootTableHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIAttackRangedBow;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFollow;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIZombieAttack;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.boss.dragon.phase.PhaseManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityDragonFireball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import scala.reflect.internal.Trees.This;

public class EntityNatureBoss extends EntityMob implements IRangedAttackMob
{
	private static final DataParameter<Boolean> INITIATE = EntityDataManager.<Boolean>createKey(EntityNatureBoss.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_INVURNERABLE = EntityDataManager.<Boolean>createKey(EntityNatureBoss.class, DataSerializers.BOOLEAN);
	private final EntityAIAttackRanged aiRangedAttack = new EntityAIAttackRanged(this, 1.0D, 80, 1.0F);
    private final EntityAIAttackMelee aiMeleeAttack = new EntityAIAttackMelee(this, 1.5D, false);
	private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.GREEN, BossInfo.Overlay.PROGRESS)).setDarkenSky(false);
	private int crystals_alive;
	private boolean minions_spawned;
	private BlockPos spawn_location;
	private int ticks_existed_after_vunerable;
	private static List crystal_locations = new ArrayList();
	private final double ARENA_SIZE_X = 30.0D;
	private final double ARENA_SIZE_Y = 20.0D;
	private final double ARENA_SIZE_Z = 30.0D;
	
	public EntityNatureBoss(World worldIn) 
	{
		super(worldIn);
		this.setSpawnLocation(null);
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
		List<EntityPhotoSynthesizerCrystal> list = this.world.<EntityPhotoSynthesizerCrystal>getEntitiesWithinAABB(EntityPhotoSynthesizerCrystal.class, this.getEntityBoundingBox().grow(ARENA_SIZE_X, ARENA_SIZE_Y, ARENA_SIZE_Z));
		
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
	    	if (world.isRemote)
	    	{
	    		player.sendMessage(new TextComponentTranslation("message.em.natureboss.fight_initiate"));
	    	}
	    	this.arenaInit();
	    	this.activateFight();
	    	this.placeDoor();
	    	this.minions_spawned = false;
	    	this.world.playBroadcastSound(1023, new BlockPos(this), 0);
	    	return super.processInteract(player, hand);
    	}
    	return false;
    }
    
    @Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) 
	{
		this.throwPoisonBall(target);
	}
	
    @Override
	public void onLivingUpdate()
    {
        if (this.world.isRemote)
        {
            ParticleSpawner.spawnParticle(EnumCustomParticleTypes.LEAF, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
        }
        
        super.onLivingUpdate();
        
        this.crystals_alive = 0;
        
        if(!this.world.isRemote)
        {
	        List<EntityPhotoSynthesizerCrystal> list = this.world.<EntityPhotoSynthesizerCrystal>getEntitiesWithinAABB(EntityPhotoSynthesizerCrystal.class, this.getEntityBoundingBox().grow(ARENA_SIZE_X, ARENA_SIZE_Y, ARENA_SIZE_Z));
	        
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
			        			for(int i = 0; i < 2; i++)
			        			{
						        	EntityNatureBossMinion minion = new EntityNatureBossMinion(this.world);
						        	minion.setPosition(this.posX, this.posY, this.posZ);
							        this.world.spawnEntity(minion);
			        			}
			        		}
			        		else if(this.world.getDifficulty() == EnumDifficulty.NORMAL)
			        		{
			        			for(int i = 0; i < 3; i++)
			        			{
						        	EntityNatureBossMinion minion = new EntityNatureBossMinion(this.world);
						        	minion.setPosition(this.posX, this.posY, this.posZ);
							        this.world.spawnEntity(minion);
			        			}
			        		}
			        		else if(this.world.getDifficulty() == EnumDifficulty.HARD)
			        		{
			        			for(int i = 0; i < 4; i++)
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
	        		this.heal(0.1F);
	        	}
	    	}
    	}
		
		this.ticksExisted++;
        
        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
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
        
        for(int l = 0; this.crystal_locations.size() > 0; l++) 
    	{
    		this.crystal_locations.remove(l);
    	}
        
        List<EntityNatureBossMinion> list = this.world.<EntityNatureBossMinion>getEntitiesWithinAABB(EntityNatureBossMinion.class, this.getEntityBoundingBox().grow(ARENA_SIZE_X, ARENA_SIZE_Y, ARENA_SIZE_Z));
        
        for (EntityNatureBossMinion entity : list)
        {
        	entity.isDead = true;
        }
    }
    
    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier)
    {
        EntityItem entityitem = this.dropItem(EMCoreItems.HEART_OF_THE_WILD, 1);
        
        if (entityitem != null)
        {
            entityitem.setNoDespawn();
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
