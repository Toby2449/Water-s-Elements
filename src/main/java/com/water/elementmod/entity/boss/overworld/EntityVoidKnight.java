package com.water.elementmod.entity.boss.overworld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.water.elementmod.EMCorePotionEffects;
import com.water.elementmod.entity.ai.EntityAIMoveTo;
import com.water.elementmod.entity.boss.fire.EntityFireBoss;
import com.water.elementmod.entity.boss.nature._ConfigEntityNatureBoss;
import com.water.elementmod.entity.boss.water.EntityWaterBoss;
import com.water.elementmod.entity.boss.water.EntityWaterBossClone;
import com.water.elementmod.entity.boss.water.EntityWaterBossMeleeMinion;
import com.water.elementmod.entity.boss.water.EntityWaterBossRangedMinion;
import com.water.elementmod.entity.boss.water.EntityWaterTrash;
import com.water.elementmod.entity.boss.water._ConfigEntityWaterBoss;
import com.water.elementmod.particle.EnumCustomParticleTypes;
import com.water.elementmod.particle.ParticleSpawner;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public class EntityVoidKnight extends EntityMob
{
	private static final DataParameter<Boolean> INITIATE = EntityDataManager.<Boolean>createKey(EntityVoidKnight.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_INVURNERABLE = EntityDataManager.<Boolean>createKey(EntityVoidKnight.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> PHASE = EntityDataManager.<Integer>createKey(EntityVoidKnight.class, DataSerializers.VARINT);
	private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.WHITE, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);
	private final EntityAIAttackMelee aiMeleeAttack = new EntityAIAttackMelee(this, 0.5D, true);
	private EntityAIMoveTo aiMoveToSide = null;
	private BlockPos spawn_location;
	private int fightCountdown = _ConfigEntityVoidKnight.FIGHT_STARTER_TIMER;
	private static List explosionPoints = new ArrayList();
	private static List explosionPointTimers = new ArrayList();
	
	private int blobCount = 0;
	
	private int P1BlobCD = 0;
	private int P1SmasherCD = 0;
	private int P1explosionCD = 0;
	private int P1phaseCD = 0;
	
	private int P2SmasherCD = 0;
	private int P2BlobCD = 0;
	private int P2explosionCD = 0;
	private int P2phaseCD = 0;
	
	private int P3LoSCD = 0;
	private int P3LoSCast = 0;
	private int P3explosionCD = 0;
	private int P3PhaseCD = 0;
	
	private int P4BlobCD = 0;
	private int P4explosionCD = 0;
	private int P4PhaseCD = 0;
	
	private int P5WalkCD = 0;
	
	private boolean P6Walked = false;
	
	private boolean chatted1 = false;
	private boolean chatted2 = false;
	private boolean chatted3 = false;
	private boolean chatted4 = false;
	private boolean chatted5 = false;
	private boolean chatted6 = false;
	private boolean chatted7 = false;
	private boolean chatted8 = false;
	
	public EntityVoidKnight(World worldIn) 
	{
		super(worldIn);
		this.setSpawnLocation(this.getPosition());
		this.setSize(1.4F, 5.0F);
	}
	
	public EntityVoidKnight(World worldIn, BlockPos pos) 
	{
		super(worldIn);
		this.setSpawnLocation(pos);
		this.setSize(1.5F, 5.0F);
	}
	
	public static void registerFixesBoss(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntityVoidKnight.class);
    }
	
	@Override
	protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    }
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(450.0F);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(9999999999999.0F);
	}
	
	@Override
	protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(IS_INVURNERABLE, Boolean.valueOf(true));
        this.dataManager.register(INITIATE, Boolean.valueOf(false));
        this.dataManager.register(PHASE, Integer.valueOf(0));
    }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setIntArray("SpawnPos", new int[] {this.getSpawnLocation().getX(), this.getSpawnLocation().getY(), this.getSpawnLocation().getZ()});
        compound.setBoolean("Invul", this.getInvulState());
        compound.setBoolean("FightState", this.isFightActivated());
        compound.setInteger("Phase", this.getPhase());
        compound.setInteger("BlobCount", this.blobCount);
        compound.setBoolean("P6Walked", this.P6Walked);
    }
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        int[] pos = compound.getIntArray("SpawnPos");
        BlockPos blockpos = new BlockPos(pos[0], pos[1], pos[2]);
        this.setSpawnLocation(blockpos);
        
        this.setInvulState(compound.getBoolean("Invul"));
        this.setFightState(compound.getBoolean("FightState"));
        this.setPhase(compound.getInteger("Phase"));
        this.blobCount = compound.getInteger("BlobCount");
        this.P6Walked = compound.getBoolean("P6Walked");
        
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
	
    @Override
	public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	
    	if(this.world.isRemote)
    	{
    		if(!this.explosionPoints.isEmpty() && !this.explosionPointTimers.isEmpty())
    		{
	    		for(int i = 0; i < this.explosionPoints.size(); i++)
				{
					int CircleTimer = (Integer)this.explosionPointTimers.get(i);
					ArrayList pos = (ArrayList)this.explosionPoints.get(i);
					
					if((Integer)this.explosionPointTimers.get(i) > 0)
					{
						if((Integer)this.explosionPointTimers.get(i) % 8 == 1)
						{
							this.VoidRingAnimation((double)pos.get(0), (double)pos.get(1), (double)pos.get(2), 2, this.world);
						}
						
						if((Integer)this.explosionPointTimers.get(i) <= 1)
						{
							this.VoidRingExplosionAnimation((double)pos.get(0), (double)pos.get(1), (double)pos.get(2), 2, 8, this.world);
							AxisAlignedBB AoePoint = new AxisAlignedBB((double)pos.get(0) - 0.5D, (double)pos.get(1) - 0.5D, (double)pos.get(2) - 0.5D, (double)pos.get(0) + 1.0D, (double)pos.get(1) + 1.0D, (double)pos.get(2) + 1.0D);
							List<EntityPlayer> AABBPlayer = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint);
							if (!AABBPlayer.isEmpty())
						    {
						        for (EntityPlayer ent : AABBPlayer)
						        {
						        	ent.addPotionEffect(new PotionEffect(EMCorePotionEffects.POTION_CORRUPTION, 100, 0));
						        }
						    }
						}
						
						this.explosionPointTimers.set(i, CircleTimer - 1);
					}
					else
					{
						this.explosionPoints.remove(i);
						this.explosionPointTimers.remove(i);
					}
				}
    		}
    	}
    	
    	if(!this.world.isRemote)
    	{
    		if(!this.explosionPoints.isEmpty() && !this.explosionPointTimers.isEmpty())
    		{
	    		for(int i = 0; i < this.explosionPoints.size(); i++)
				{
					int CircleTimer = (Integer)this.explosionPointTimers.get(i);
					ArrayList pos = (ArrayList)this.explosionPoints.get(i);
					
					if((Integer)this.explosionPointTimers.get(i) > 0)
					{
						if((Integer)this.explosionPointTimers.get(i) <= 1)
						{
							AxisAlignedBB AoePoint = new AxisAlignedBB((double)pos.get(0) - 0.5D, (double)pos.get(1) - 0.5D, (double)pos.get(2) - 0.5D, (double)pos.get(0) + 1.0D, (double)pos.get(1) + 1.0D, (double)pos.get(2) + 1.0D);
							List<EntityPlayer> AABBPlayer = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint);
							if (!AABBPlayer.isEmpty())
						    {
						        for (EntityPlayer ent : AABBPlayer)
						        {
						        	ent.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 150, 1));
						        	ent.addPotionEffect(new PotionEffect(EMCorePotionEffects.POTION_CORRUPTION, 100, 0));
						        	ent.attackEntityFrom(DamageSource.MAGIC, 7.5F);
						        }
						    }
						}
					}
					else
					{
						this.explosionPoints.remove(i);
						this.explosionPointTimers.remove(i);
					}
				}
    		}
    		
    		if(this.ticksExisted % 20 == 1)
        	{
    			if(this.getPhase() != 6)
    			{
	            	List<EntityPlayer> playerlist = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidKnight.MF_SIZE_X, _ConfigEntityVoidKnight.MF_SIZE_Y, _ConfigEntityVoidKnight.MF_SIZE_Z).offset(0, -20, 0));
	    	        
	    	        for (EntityPlayer player : playerlist)
	    	        {
	    	        	player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 100, 2));
	    	        	player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 100, 1));
	    	        }
    			}
        	}
    		
    		// If nobodies in the arena
	        if(this.isFightActivated())
	        {
	        	List<EntityPlayer> players = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidKnight.ARENA_SIZE_X, _ConfigEntityVoidKnight.ARENA_SIZE_Y, _ConfigEntityVoidKnight.ARENA_SIZE_Z));
		        
	        	if(players.size() <= 0)
	        	{
	        		this.resetFight();
	        	}
	        }
	        
	        if(!this.isFightActivated() && this.getPhase() < 5)
	        {
	        	this.setHealth(this.getMaxHealth());
	        }
	        
	        List<EntityMob> list = this.world.<EntityMob>getEntitiesWithinAABB(EntityMob.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidKnight.ARENA_SIZE_X, _ConfigEntityVoidKnight.ARENA_SIZE_Y, _ConfigEntityVoidKnight.ARENA_SIZE_Z));
	        for (EntityMob entity : list)
	        {
	        	if(!(entity instanceof EntityVoidSmasher || entity instanceof EntityVoidBlob))
	        	{
	        		entity.isDead = true;
	        	}
	        }
    	}
    		
    	
    	switch(this.getPhase())
    	{
    	
		/**
		 * =================================================
		 * PHASE 0 
		 * =================================================
		**/	    	
    	case 0:
    		if(!this.world.isRemote)
    		{
	    		boolean numberOfPlayers = false;
	    		
		    	List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidKnight.ARENA_SIZE_X, _ConfigEntityVoidKnight.ARENA_SIZE_Y, _ConfigEntityVoidKnight.ARENA_SIZE_Z).offset(0, -4, 0));
				for (EntityPlayer player : list)
		        {
					numberOfPlayers = true;
		        }
				if(numberOfPlayers)
				{
					this.fightCountdown--;
				}
				else
				{
					this.fightCountdown = _ConfigEntityVoidKnight.FIGHT_STARTER_TIMER;
				}
				if(this.fightCountdown <= 0)
				{
					aiMoveToSide = new EntityAIMoveTo(this, 0.36D, 10, (this.getSpawnLocation().getX() + 0.5D), this.getSpawnLocation().getY(), (this.getSpawnLocation().getZ() - 10D));
					this.setFightState(true);
					this.setPhase(1);
				}
    		}
    		else
    		{
    			boolean numberOfPlayers = false;
	    		
		    	List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidKnight.ARENA_SIZE_X, _ConfigEntityVoidKnight.ARENA_SIZE_Y, _ConfigEntityVoidKnight.ARENA_SIZE_Z).offset(0, -4, 0));
				for (EntityPlayer player : list)
		        {
					numberOfPlayers = true;
		        }
				if(numberOfPlayers)
				{
					this.sayChatLine(0);
				}
				else
				{
					this.chatted1 = false;
				}
    		}
			break;
			
			
			
		/**
		 * =================================================
		 * PHASE 1 
		 * =================================================
		**/	 
    	case 1:
    		if(this.world.isRemote)
	    	{
	    		this.sayChatLine(1);
	    		
	    		this.VoidPortalAnimation(this.posX, this.posY, this.posZ + 14, 3, this.world);
	        	this.VoidPortalAnimation(this.posX, this.posY, this.posZ - 14, 3, this.world);
	        	
	        	this.P1explosionCD++;
	        	List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidKnight.ARENA_SIZE_X, _ConfigEntityVoidKnight.ARENA_SIZE_Y, _ConfigEntityVoidKnight.ARENA_SIZE_Z).offset(0, -4, 0));
				for (EntityPlayer player : list)
		        {
					if(this.P1explosionCD >= _ConfigEntityVoidKnight.P1_EXPLOSION_CD)
					{
						this.spawnVoidExplosion(player.posX, player.posY, player.posZ);
						this.P1explosionCD = 0;
					}
		        }
	    	}
    		
	    	if(!this.world.isRemote)
	    	{
	    		this.P1explosionCD++;
	    		this.P1BlobCD++;
	    		this.P1SmasherCD++;
	    		this.P1phaseCD++;
	    		
	    		List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidKnight.ARENA_SIZE_X, _ConfigEntityVoidKnight.ARENA_SIZE_Y, _ConfigEntityVoidKnight.ARENA_SIZE_Z).offset(0, -4, 0));
				for (EntityPlayer player : list)
		        {
					if(this.P1explosionCD >= _ConfigEntityVoidKnight.P1_EXPLOSION_CD)
					{
						this.spawnVoidExplosion(player.posX, player.posY, player.posZ);
						this.P1explosionCD = 0;
					}
		        }
				
				if(this.P1BlobCD >= _ConfigEntityVoidKnight.P1_BLOB_CD)
	    		{
	    			if(this.rand.nextInt(99) > 50)
	    			{
			    		EntityVoidBlob entity = new EntityVoidBlob(this.world, this.posX - 0.5D, this.posY, this.posZ, 0.325D);
				       	entity.setPosition(this.getPosition().getX(), this.getPosition().getY() + 1, this.getPosition().getZ() + 14D);
				   		this.world.spawnEntity(entity);
	    			}
	    			else
	    			{
				   		EntityVoidBlob entity2 = new EntityVoidBlob(this.world, this.posX - 0.5D, this.posY, this.posZ, 0.325D);
				       	entity2.setPosition(this.getPosition().getX(), this.getPosition().getY() + 1, this.getPosition().getZ() - 14D);
				   		this.world.spawnEntity(entity2);
	    			}
	    			this.P1BlobCD = 0;
	    		}
	    		
	    		if(this.P1SmasherCD >= _ConfigEntityVoidKnight.P1_SMASHER_CD)
	    		{
	    			if(this.rand.nextInt(99) > 50)
	    			{
	    				EntityVoidSmasher entity = new EntityVoidSmasher(this.world);
				       	entity.setPosition(this.getPosition().getX(), this.getPosition().getY() + 3, this.getPosition().getZ() + 12D);
				       	entity.getAttackTarget();
				   		this.world.spawnEntity(entity);
	    			}
	    			else
	    			{
	    				EntityVoidSmasher entity2 = new EntityVoidSmasher(this.world);
				       	entity2.setPosition(this.getPosition().getX(), this.getPosition().getY() + 3, this.getPosition().getZ() - 12D);
				       	entity2.getAttackTarget();
				   		this.world.spawnEntity(entity2);
	    			}
	    			this.P1SmasherCD = 0;
	    		}
	    		
	    		if(this.P1phaseCD >= _ConfigEntityVoidKnight.P1_PHASE_CD)
	    		{
	    			this.setPhase(2);
	    		}
	    	}
	    	break;
	    	
	    	
	    	
	    /**
		 * =================================================
		 * PHASE 2 
		 * =================================================
		**/	 
    	case 2:
    		if(this.world.isRemote)
	    	{
	    		this.sayChatLine(2);
	    		
	    		this.VoidPortalAnimation(this.posX, this.posY, this.posZ + 14, 3, this.world);
	        	this.VoidPortalAnimation(this.posX, this.posY, this.posZ - 14, 3, this.world);
	        	
	        	this.P2explosionCD++;
	        	List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidKnight.ARENA_SIZE_X, _ConfigEntityVoidKnight.ARENA_SIZE_Y, _ConfigEntityVoidKnight.ARENA_SIZE_Z).offset(0, -4, 0));
				for (EntityPlayer player : list)
		        {
					if(this.P2explosionCD >= _ConfigEntityVoidKnight.P2_EXPLOSION_CD)
					{
						this.spawnVoidExplosion(player.posX, player.posY, player.posZ);
						this.P2explosionCD = 0;
					}
		        }
	    	}
    		
	    	if(!this.world.isRemote)
	    	{
	    		this.P2explosionCD++;
	    		this.P2BlobCD++;
	    		this.P2SmasherCD++;
	    		this.P2phaseCD++;
	    		
	    		List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidKnight.ARENA_SIZE_X, _ConfigEntityVoidKnight.ARENA_SIZE_Y, _ConfigEntityVoidKnight.ARENA_SIZE_Z).offset(0, -4, 0));
				for (EntityPlayer player : list)
		        {
					if(this.P2explosionCD >= _ConfigEntityVoidKnight.P2_EXPLOSION_CD)
					{
						this.spawnVoidExplosion(player.posX, player.posY, player.posZ);
						this.P2explosionCD = 0;
					}
		        }
				
				if(this.P2BlobCD >= _ConfigEntityVoidKnight.P2_BLOB_CD)
	    		{
			    	EntityVoidBlob entity = new EntityVoidBlob(this.world, this.posX - 0.5D, this.posY, this.posZ, 0.4D);
				    entity.setPosition(this.getPosition().getX(), this.getPosition().getY() + 1, this.getPosition().getZ() + 14D);
				   	this.world.spawnEntity(entity);
				   	
				   	EntityVoidBlob entity2 = new EntityVoidBlob(this.world, this.posX - 0.5D, this.posY, this.posZ, 0.4D);
				    entity2.setPosition(this.getPosition().getX(), this.getPosition().getY() + 1, this.getPosition().getZ() - 14D);
				    this.world.spawnEntity(entity2);
				    
	    			this.P2BlobCD = 0;
	    		}
	    		
	    		if(this.P2SmasherCD >= _ConfigEntityVoidKnight.P2_SMASHER_CD)
	    		{
	    			if(this.rand.nextInt(99) > 50)
	    			{
	    				EntityVoidSmasher entity = new EntityVoidSmasher(this.world);
				       	entity.setPosition(this.getPosition().getX(), this.getPosition().getY() + 3, this.getPosition().getZ() + 12D);
				   		this.world.spawnEntity(entity);
	    			}
	    			else
	    			{
	    				EntityVoidSmasher entity2 = new EntityVoidSmasher(this.world);
				       	entity2.setPosition(this.getPosition().getX(), this.getPosition().getY() + 3, this.getPosition().getZ() - 12D);
				       	entity2.getAttackTarget();
				   		this.world.spawnEntity(entity2);
	    			}
	    			this.P2SmasherCD = 0;
	    		}
	    		
	    		if(this.P2phaseCD >= _ConfigEntityVoidKnight.P2_PHASE_CD)
	    		{
	    			this.setHealth(this.getMaxHealth());
	    			this.setPhase(3);
	    		}
	    		
	    		if(this.getHealth() <= (this.getMaxHealth() / 2))
	    		{
	    			this.setHealth(this.getMaxHealth());
	    			this.setPhase(3);
	    		}
	    	}
    		break;
    		
    		
    		
    	/**
    	 * =================================================
    	 * PHASE 3 
    	 * =================================================
    	**/	 
    	case 3:
    		this.P4explosionCD = 0;
    		
    		if(this.world.isRemote)
    		{
    			for (int i = 0; i < 3; ++i)
                {
                	ParticleSpawner.spawnParticle(EnumCustomParticleTypes.VOID_PORTAL_BLOCK, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
                }
    			
    			this.sayChatLine(3);
    			this.chatted6 = false;
    			this.P3LoSCD++;
    			this.P3explosionCD++;
        		List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidKnight.ARENA_SIZE_X, _ConfigEntityVoidKnight.ARENA_SIZE_Y, _ConfigEntityVoidKnight.ARENA_SIZE_Z).offset(0, -4, 0));
				for (EntityPlayer player : list)
	        	{
					if(this.P3explosionCD >= _ConfigEntityVoidKnight.P3_EXPLOSION_CD)
					{
						this.spawnVoidExplosion(player.posX, player.posY, player.posZ);
						this.P3explosionCD = 0;
					}
	        	}
				
				if(this.P3LoSCD >= _ConfigEntityVoidKnight.P3_LOS_CD)
    			{
					this.sayChatLine(4);
    				this.P3LoSCast++;
    				VoidRingBigAnimation(this.posX, this.posY, this.posZ, 15, this.world);
    				if(this.P3LoSCast >= _ConfigEntityVoidKnight.P3_LOS_CAST)
    				{
    					VoidRingExplosionAnimation(this.posX, this.posY, this.posZ, 15, 10, this.world);
    	    			this.P3LoSCast = 0;
    	    			this.P3LoSCD = 0;
    	    			this.chatted5 = false;
    				}
    			}
    		}
    		
    		if(!this.world.isRemote)
    		{
    			this.setInvulState(false);
    			this.P3explosionCD++;
    			this.P3PhaseCD++;
    			this.P3LoSCD++;
    			
    			List<EntityVoidBlob> blobList = this.world.<EntityVoidBlob>getEntitiesWithinAABB(EntityVoidBlob.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidKnight.ARENA_SIZE_X, _ConfigEntityVoidKnight.ARENA_SIZE_Y, _ConfigEntityVoidKnight.ARENA_SIZE_Z).offset(0, -4, 0));
				for (EntityVoidBlob entity : blobList)
		        {
					entity.isDead = true;
		        }
    			
    			List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidKnight.ARENA_SIZE_X, _ConfigEntityVoidKnight.ARENA_SIZE_Y, _ConfigEntityVoidKnight.ARENA_SIZE_Z).offset(0, -4, 0));
    			for (EntityPlayer player : list)
    			{
    				if(this.P3explosionCD >= _ConfigEntityVoidKnight.P3_EXPLOSION_CD)
    				{
    					this.spawnVoidExplosion(player.posX, player.posY, player.posZ);
    					this.P3explosionCD = 0;
    				}
    			}
    			
    			if(this.P3LoSCD >= _ConfigEntityVoidKnight.P3_LOS_CD)
    			{
    				this.P3LoSCast++;
    				if(this.P3LoSCast >= _ConfigEntityVoidKnight.P3_LOS_CAST)
    				{
    					List<EntityPlayer> list2 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidKnight.ARENA_SIZE_X, _ConfigEntityVoidKnight.ARENA_SIZE_Y, _ConfigEntityVoidKnight.ARENA_SIZE_Z).offset(0, -4, 0));
    	    			for (EntityPlayer player : list2)
    	    			{
    	    				if(this.getEntitySenses().canSee(player))
    	    				{
    	    					player.attackEntityFrom(DamageSource.MAGIC, 17.5F);
    	    				}
    	    			}
    	    			
    	    			this.P3LoSCast = 0;
    	    			this.P3LoSCD = 0;
    				}
    			}
    			
    			if(this.P3PhaseCD >= _ConfigEntityVoidKnight.P3_PHASE_CD)
    			{
	    			this.P3PhaseCD = 0;
    				this.setPhase(4);
    			}
    			
    			if(this.getHealth() <= 40.0F)
    			{
    				this.setPhase(5);
    			}
    		}
    		break;
    		
    		
    		
    	/**
    	 * =================================================
    	 * PHASE 4 
    	 * =================================================
    	**/	 
    	case 4:
    		this.P3explosionCD = 0;
			this.P3LoSCast = 0;
			this.P3LoSCD = 0;
			
    		if(this.world.isRemote)
    		{
    			for (int i = 0; i < 3; ++i)
                {
                	ParticleSpawner.spawnParticle(EnumCustomParticleTypes.VOID_PORTAL_BLOCK, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
                }
    			
    			this.sayChatLine(5);
    			this.VoidPortalAnimation(this.posX, this.posY, this.posZ + 14, 3, this.world);
	        	this.VoidPortalAnimation(this.posX, this.posY, this.posZ - 14, 3, this.world);
	        	
    			this.P4explosionCD++;
        		List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidKnight.ARENA_SIZE_X, _ConfigEntityVoidKnight.ARENA_SIZE_Y, _ConfigEntityVoidKnight.ARENA_SIZE_Z).offset(0, -4, 0));
				for (EntityPlayer player : list)
	        	{
					if(this.P4explosionCD >= _ConfigEntityVoidKnight.P4_EXPLOSION_CD)
					{
						this.spawnVoidExplosion(player.posX, player.posY, player.posZ);
						this.P4explosionCD = 0;
					}
	        	}
    		}
    		
    		if(!this.world.isRemote)
    		{
    			this.setInvulState(true);
    			this.P4explosionCD++;
    			this.P4PhaseCD++;
    			this.P4BlobCD++;
    			
    			List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidKnight.ARENA_SIZE_X, _ConfigEntityVoidKnight.ARENA_SIZE_Y, _ConfigEntityVoidKnight.ARENA_SIZE_Z).offset(0, -4, 0));
    			for (EntityPlayer player : list)
    			{
    				if(this.P4explosionCD >= _ConfigEntityVoidKnight.P4_EXPLOSION_CD)
    				{
    					this.spawnVoidExplosion(player.posX, player.posY, player.posZ);
    					this.P4explosionCD = 0;
    				}
    			}
    			
    			if(this.P4BlobCD >= _ConfigEntityVoidKnight.P4_BLOB_CD)
	    		{
			    	EntityVoidBlob entity = new EntityVoidBlob(this.world, this.posX - 0.5D, this.posY, this.posZ, 0.475D);
				    entity.setPosition(this.getPosition().getX(), this.getPosition().getY() + 1, this.getPosition().getZ() + 14D);
				   	this.world.spawnEntity(entity);
				   	
				   	EntityVoidBlob entity2 = new EntityVoidBlob(this.world, this.posX - 0.5D, this.posY, this.posZ, 0.475D);
				    entity2.setPosition(this.getPosition().getX(), this.getPosition().getY() + 1, this.getPosition().getZ() - 14D);
				    this.world.spawnEntity(entity2);
				    
	    			this.P4BlobCD = 0;
	    		}
    			if(this.P4PhaseCD >= _ConfigEntityVoidKnight.P4_PHASE_CD || this.getHealth() == this.getMaxHealth())
    			{
    				this.P4PhaseCD = 0;
    				this.P4BlobCD = 0;
    				this.setPhase(3);
    			}
    		}
    		break;
    		
    		
    		
    	/**
    	 * =================================================
    	 * PHASE 5 
    	 * =================================================
    	**/	 
    	case 5:
    		if(this.world.isRemote)
    		{
    			this.sayChatLine(6);
    		}
    		
    		if(!this.world.isRemote)
    		{
    			this.setInvulState(true);
    			this.setFightState(false);
    			this.P5WalkCD++;
    			if(this.P5WalkCD >= _ConfigEntityVoidKnight.P5_WALK_CD)
    			{
    				this.setPhase(6);
    			}
    		}
    		break;
    		
    		
    		
    	/**
         * =================================================
         * PHASE 6 
         * =================================================
        **/
        	case 6:
        		if(this.world.isRemote)
        		{
        			this.sayChatLine(7);
        		}
        		
        		if(!this.world.isRemote)
        		{
        			this.setInvulState(true);
        		}
        		break;
    	}
    	
    	
    	
		this.ticksExisted++;
        
        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }
	
	@Override
	protected void updateAITasks()
    {
		super.updateAITasks();
		
		switch(this.getPhase())
		{
		case 0:
			this.tasks.removeTask(aiMeleeAttack);
			this.setPosition(this.getSpawnLocation().getX() + 1.0D, this.getSpawnLocation().getY(), this.getSpawnLocation().getZ() + 1.0D);
			break;
		case 1:
			this.setPosition(this.getSpawnLocation().getX() + 1.0D, this.getSpawnLocation().getY(), this.getSpawnLocation().getZ() + 1.0D);
			break;
		case 2:
			this.setPosition(this.getSpawnLocation().getX() + 1.0D, this.getSpawnLocation().getY(), this.getSpawnLocation().getZ() + 1.0D);
			break;
		case 3:
			this.tasks.addTask(1, aiMeleeAttack);
			break;
		case 4:
			this.setPosition(this.getSpawnLocation().getX() + 1.0D, this.getSpawnLocation().getY(), this.getSpawnLocation().getZ() + 1.0D);
			this.tasks.removeTask(aiMeleeAttack);
			break;
		case 5:
			this.tasks.removeTask(aiMeleeAttack);
			this.setPosition(this.getSpawnLocation().getX() + 1.0D, this.getSpawnLocation().getY(), this.getSpawnLocation().getZ() + 1.0D);
			break;
		case 6:
			if(!this.P6Walked)
			{
				this.tasks.addTask(1, aiMoveToSide);
				this.P6Walked = true;
			}
			break;
		}
		
		if(this.P3LoSCast > 0)
		{
			this.setPosition(this.getSpawnLocation().getX() + 1.0D, this.getSpawnLocation().getY(), this.getSpawnLocation().getZ() + 1.0D);
		}
    }
	
	@Override
	public float getEyeHeight()
	{
		return 4.7F;
	}
	
	public void VoidPortalAnimation(double x, double y, double z, double radius, World world)
	{
		Random rand = new Random();
		for(double r = 0.6D; r <= radius; r += 0.2D)
		{
			for(float i = 0.0F; i < 360.0F; i += 15.0F)
			{
				double deltaX = Math.cos(Math.toRadians(i))*r + rand.nextDouble();
				double deltaY = -Math.sin(Math.toRadians(i))*r + rand.nextDouble();
				double finalX = x - 0.5D + deltaX;
				double finalY = y - 0.5D + deltaY;
			    
				if(this.rand.nextInt(99) < 20) ParticleSpawner.spawnParticle(EnumCustomParticleTypes.VOID_PORTAL, finalX, finalY + 2.25D, z, 0.0D, 0.0D, 0.0D);
			}
		}
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundEvents.ENTITY_BLAZE_HURT;
	}
	
	@Override
	protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_SHULKER_DEATH;
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
		if(entity instanceof EntityVoidBlob)
		{
			List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidKnight.ARENA_SIZE_X, _ConfigEntityVoidKnight.ARENA_SIZE_Y, _ConfigEntityVoidKnight.ARENA_SIZE_Z).offset(0, -4, 0));
	        
    		for (EntityPlayer player : list)
	        {
    			player.attackEntityFrom(DamageSource.causeMobDamage(this), 10.0F);
	        }
    		
    		VoidRingAnimation(this.posX, this.posY, this.posZ, 20, this.world);
    		return super.attackEntityFrom(source, amount);
		}
		else
		{
			if(this.getInvulState() == false)
	    	{
	    		return super.attackEntityFrom(source, amount);
	    	}
		    else
		    {
		    	return false;
		    }
		}
    }
    
    @Override
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);
        
        this.sayChatLine(6);
    }
    
    public void VoidRingAnimation(double x, double y, double z, double radius, World world)
	{
		Random rand = new Random();
		for(double r = 0.6D; r <= radius; r += 0.2D)
		{
			for(float i = 0.0F; i < 360.0F; i += 15.0F)
			{
				double deltaX = Math.cos(Math.toRadians(i))*r + rand.nextDouble();
				double deltaZ = -Math.sin(Math.toRadians(i))*r + rand.nextDouble();
				double finalX = x - 0.5D + deltaX;
				double finalZ = z - 0.5D + deltaZ;
			    
				ParticleSpawner.spawnParticle(EnumCustomParticleTypes.VOID_PORTAL, finalX, y + 0.15D, finalZ, 0.0D, 0.0D, 0.0D);
			}
		}
	}
    
    public void VoidRingBigAnimation(double x, double y, double z, double radius, World world)
	{
		Random rand = new Random();
		for(double r = 0.6D; r <= radius; r += 0.2D)
		{
			for(float i = 0.0F; i < 360.0F; i += 15.0F)
			{
				double deltaX = Math.cos(Math.toRadians(i))*r + rand.nextDouble();
				double deltaZ = -Math.sin(Math.toRadians(i))*r + rand.nextDouble();
				double finalX = x - 0.5D + deltaX;
				double finalZ = z - 0.5D + deltaZ;
			    
				if(this.rand.nextInt(99) < 20) ParticleSpawner.spawnParticle(EnumCustomParticleTypes.VOID_PORTAL, finalX, y + 0.15D, finalZ, 0.0D, 0.0D, 0.0D);
			}
		}
	}
    
    public void VoidRingExplosionAnimation(double x, double y, double z, double radius, int height, World world)
	{
		Random rand = new Random();
		for(int m = 0; m < 20; m++)
		{
			for(double r = 0.6D; r <= radius; r += 0.45D)
			{
				for(float i = 0.0F; i < 360.0F; i += 30.0F)
				{
					double deltaX = Math.cos(Math.toRadians(i))*r + rand.nextDouble();
					double deltaZ = -Math.sin(Math.toRadians(i))*r + rand.nextDouble();
					double finalX = x - 0.5D + deltaX;
					double finalZ = z - 0.5D + deltaZ;
				    
					ParticleSpawner.spawnParticle(EnumCustomParticleTypes.VOID_PORTAL, finalX, (y + this.rand.nextInt(height) + this.rand.nextDouble()) + 0.15D, finalZ, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}
    
    public void resetFight()
    {
    	this.setFightState(false);
    	this.setInvulState(true);
    	this.setPhase(0);
    	this.explosionPoints.clear();
    	this.explosionPointTimers.clear();
    	fightCountdown = _ConfigEntityVoidKnight.FIGHT_STARTER_TIMER;
    	
    	blobCount = 0;
    	
    	P1BlobCD = 0;
    	P1SmasherCD = 0;
    	P1explosionCD = 0;
    	P1phaseCD = 0;
    	
    	P2SmasherCD = 0;
    	P2BlobCD = 0;
    	P2explosionCD = 0;
    	P2phaseCD = 0;
    	
    	P3LoSCD = 0;
    	P3LoSCast = 0;
    	P3explosionCD = 0;
    	P3PhaseCD = 0;
    	
    	P4BlobCD = 0;
    	P4explosionCD = 0;
    	P4PhaseCD = 0;
    	
    	chatted1 = false;
    	chatted2 = false;
    	chatted3 = false;
    	chatted4 = false;
    	chatted5 = false;
    	chatted6 = false;
    	
    	List<EntityVoidBlob> list = this.world.<EntityVoidBlob>getEntitiesWithinAABB(EntityVoidBlob.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidKnight.ARENA_SIZE_X, _ConfigEntityVoidKnight.ARENA_SIZE_Y, _ConfigEntityVoidKnight.ARENA_SIZE_Z));
    	
    	if(!list.isEmpty())
    	{
	    	for (EntityVoidBlob entity : list)
	        {
	    		entity.setDead();
	        }
    	}
    	
    	List<EntityVoidSmasher> list1 = this.world.<EntityVoidSmasher>getEntitiesWithinAABB(EntityVoidSmasher.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidKnight.ARENA_SIZE_X, _ConfigEntityVoidKnight.ARENA_SIZE_Y, _ConfigEntityVoidKnight.ARENA_SIZE_Z));
    	
    	if(!list1.isEmpty())
    	{
	    	for (EntityVoidSmasher entity : list1)
	        {
	    		entity.setDead();
	        }
    	}
    }
    
    public void sayChatLine(int message)
	{
		if(this.world.isRemote)
		{
			List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidKnight.ARENA_SIZE_X, _ConfigEntityVoidKnight.ARENA_SIZE_Y, _ConfigEntityVoidKnight.ARENA_SIZE_Z));
	    	
	    	if(!list.isEmpty())
	    	{
		    	for (EntityPlayer player : list)
		        {
		    		switch(message)
		    		{
		    		case 0:
		    			if(this.chatted1 == false)
		    			{
		    				this.chatted1 = true;
		    				player.sendMessage(new TextComponentTranslation("message.em.voidknight.player_in_arena"));
		    			}
		    			break;
		        	case 1:
		    			if(this.chatted2 == false)
		    			{
		    				this.chatted2 = true;
		    				player.sendMessage(new TextComponentTranslation("message.em.voidknight.fight_initiate"));
		    			}
		    			break;
		        	case 2:
		    			if(this.chatted3 == false)
		    			{
		    				this.chatted3 = true;
		    				player.sendMessage(new TextComponentTranslation("message.em.voidknight.phase2"));
		    			}
		    			break;
		        	case 3:
		        		if(this.chatted4 == false)
		        		{
		        			this.chatted4 = true;
		        			player.sendMessage(new TextComponentTranslation("message.em.voidknight.phase3"));
		        		}
		        		break;
		        	case 4:
		        		if(this.chatted5 == false)
		        		{
		        			this.chatted5 = true;
		        			player.sendMessage(new TextComponentTranslation("message.em.voidknight.phase3_1"));
		        		}
		        		break;
		        	case 5:
		        		if(this.chatted6 == false)
		        		{
		        			this.chatted6 = true;
		        			player.sendMessage(new TextComponentTranslation("message.em.voidknight.phase4"));
		        		}
		        		break;
		        	case 6:
		        		if(this.chatted7 == false)
		        		{
		        			this.chatted7 = true;
		        			player.sendMessage(new TextComponentTranslation("message.em.voidknight.cleansed"));
		        		}
		        		break;
		        	case 7:
		        		if(this.chatted8 == false)
		        		{
		        			this.chatted8 = true;
		        			player.sendMessage(new TextComponentTranslation("message.em.voidknight.ending_message"));
		        		}
		        		break;
		    		}
		        }
	    	}
		}
	}
    
    public void spawnVoidExplosion(double x, double y, double z)
	{
		List pos = new ArrayList();
		pos.add(x);
		pos.add(y);
		pos.add(z);
		this.explosionPoints.add(pos);
		this.explosionPointTimers.add(_ConfigEntityVoidKnight.EXPLOSION_TIMER);
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
    
    public int getPhase()
    {
        return ((Integer)this.dataManager.get(PHASE)).intValue();
    }
    
    public void setPhase(int state)
    {
        this.dataManager.set(PHASE, Integer.valueOf(state));
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
}
