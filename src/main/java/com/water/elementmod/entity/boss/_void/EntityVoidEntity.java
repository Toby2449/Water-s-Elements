package com.water.elementmod.entity.boss._void;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.water.elementmod.EMCoreBlocks;
import com.water.elementmod.EMCorePotionEffects;
import com.water.elementmod.entity.EntityBossMob;
import com.water.elementmod.network.PacketCarapaceParticleCircle;
import com.water.elementmod.network.PacketCarapaceParticleRing;
import com.water.elementmod.network.PacketCarapaceRingExplosion;
import com.water.elementmod.network.PacketCustomPotionEffect;
import com.water.elementmod.network.PacketHandler;
import com.water.elementmod.network.PacketPlayMusic;
import com.water.elementmod.network.PacketStopMusic;
import com.water.elementmod.network.PacketVEGlimpseAnimation;
import com.water.elementmod.network.PacketVESightAttack;
import com.water.elementmod.network.PacketVESightExplode;
import com.water.elementmod.util.handlers.EMSoundHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
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
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public class EntityVoidEntity extends EntityBossMob
{
	private static final DataParameter<Boolean> INITIATE = EntityDataManager.<Boolean>createKey(EntityVoidEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_INVURNERABLE = EntityDataManager.<Boolean>createKey(EntityVoidEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> PHASE = EntityDataManager.<Integer>createKey(EntityVoidEntity.class, DataSerializers.VARINT);
	private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS)).setDarkenSky(false);
	private static final DataParameter<Integer> NUM_OF_PLAYERS = EntityDataManager.<Integer>createKey(EntityVoidEntity.class, DataSerializers.VARINT);
	private BlockPos spawn_location;
	private int musicDuration = _ConfigEntityVoidEntity.MUSIC_DURATION;
	private boolean musicPlaying = false;
	private static List explosionPoints = new ArrayList();
	private int explosionPointTimer = _ConfigEntityVoidEntity.EXPLOSION_TIMER;
	private int pre_phase_talk = _ConfigEntityVoidEntity.TALK_PHASE_TIMER;
	private boolean spawnedFirstLunacy = false;
	private int firstLunacySpawnTimer = _ConfigEntityVoidEntity.FIRST_LUNACY_SPAWN_TIMER;
	private int glimpseAnimationTimer = _ConfigEntityVoidEntity.GLIMPSE_ANIM_TIMER;
	private int P2Timer = _ConfigEntityVoidEntity.P2TIMER;
	private int P3SoakCD = _ConfigEntityVoidEntity.P3SOAKCD;
	private int P3SoakTimer = _ConfigEntityVoidEntity.P3SOAKTIMER;
	private boolean P3SoakPlayerFound = false;
	private EntityPlayer P3SoakPlayer = null;
	private int P3ExplosionCD = _ConfigEntityVoidEntity.P3EXPLOSIONTIMER;
	private int P3MinionSpawnTimer = _ConfigEntityVoidEntity.P3MINIONSPAWNTIMER;
	private int secondLunacySpawnTimer = _ConfigEntityVoidEntity.SECOND_LUNACY_SPAWN_TIMER;
	private boolean spawnedSecondLunacy = false;
	private int P4SoakCD = _ConfigEntityVoidEntity.P4SOAKCD;
	private int P4SoakTimer = _ConfigEntityVoidEntity.P4SOAKTIMER;
	private boolean P4SoakPlayerFound = false;
	private EntityPlayer P4SoakPlayer = null;
	private int P4ExplosionCD = _ConfigEntityVoidEntity.P4EXPLOSIONTIMER;
	private int P4MinionSpawnTimer = _ConfigEntityVoidEntity.P4MINIONSPAWNTIMER;
	private int P6MinionSpawnTimer = _ConfigEntityVoidEntity.P6MINIONSPAWNTIMER;
	private int P6SecondMinionSpawnTimer = _ConfigEntityVoidEntity.P6SECONDMINIONSPAWNTIMER;
	private boolean P6SecondMinionSpawn = false;
	private int P6ExplosionCD = _ConfigEntityVoidEntity.P6EXPLOSIONTIMER;
	private int P6SoakCD = _ConfigEntityVoidEntity.P6SOAKCD;
	private int P6SoakTimer = _ConfigEntityVoidEntity.P6SOAKTIMER;
	private boolean P6SoakPlayerFound = false;
	private BlockPos P6SoakPos = null;
	private int P6AnguishTimer = _ConfigEntityVoidEntity.P6ANGUISHTIMER;
	private int P6ConeCD = _ConfigEntityVoidEntity.P6CONECD;
	private int P6ConeCastTime = _ConfigEntityVoidEntity.P6CONETIMER;
	private boolean P6FacingFound = false;
	private int P6Facing = 0;
	private int death_phase_talk = _ConfigEntityVoidEntity.TALK_PHASE_TIMER2;
	
	public EntityVoidEntity(World worldIn) 
	{
		super(worldIn);
		this.setSpawnLocation(this.getPosition());
		this.setSize(8.0F, 8.5F);
		this.isImmuneToFire = true;
	}
	
	public EntityVoidEntity(World worldIn, BlockPos pos) 
	{
		super(worldIn);
		this.setSpawnLocation(pos);
		this.setSize(8.0F, 8.5F);
		this.isImmuneToFire = true;
	}
	
	public static void registerFixesBoss(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntityVoidEntity.class);
    }
	
	@Override
	protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 100.0F));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    }
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1000.0F);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(9999999999999.0F);
	}
	
	@Override
	protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(IS_INVURNERABLE, Boolean.valueOf(true));
        this.dataManager.register(INITIATE, Boolean.valueOf(false));
        this.dataManager.register(PHASE, Integer.valueOf(0));
        this.dataManager.register(NUM_OF_PLAYERS, Integer.valueOf(1));
    }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setIntArray("SpawnPos", new int[] {this.getSpawnLocation().getX(), this.getSpawnLocation().getY(), this.getSpawnLocation().getZ()});
        compound.setBoolean("Invul", this.getInvulState());
        compound.setBoolean("FightState", this.isFightActivated());
        compound.setInteger("Phase", this.getPhase());
        compound.setBoolean("FirstLunacySpawned", this.spawnedFirstLunacy);
        compound.setBoolean("SecondLunacySpawned", this.spawnedSecondLunacy);
        compound.setInteger("NumOfPlayers", this.getNumOfPlayers());
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
        this.spawnedFirstLunacy = compound.getBoolean("FirstLunacySpawned");
        this.spawnedSecondLunacy = compound.getBoolean("SecondLunacySpawned");
        this.setNumOfPlayers(compound.getInteger("NumOfPlayers"));
        
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
    	
    	if(!this.world.isRemote)
    	{
    		if(!this.explosionPoints.isEmpty())
    		{
	    		for(int i = 0; i < this.explosionPoints.size(); i++)
				{
					ArrayList pos = (ArrayList)this.explosionPoints.get(i);
					
					if(this.explosionPoints.get(i) != null)
					{
						this.explosionPointTimer--;
						if(this.explosionPointTimer > 0)
						{
							PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceParticleCircle(this, this.world, 9, (double)pos.get(0), (double)pos.get(1), (double)pos.get(2), 0.0D, 0.0D,0.0D, 2), this.dimension);
						}
						else
						{
							Double x = new Double((double)pos.get(0)); 
							int x1 = x.intValue();
							Double y = new Double((double)pos.get(1)); 
							int y1 = y.intValue();
							Double z = new Double((double)pos.get(2)); 
							int z1 = z.intValue();
							this.playImpactSound((int)x1, (int)y1, (int)z1, 3.5F);
							PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceRingExplosion(this, this.world, 9, (double)pos.get(0), (double)pos.get(1), (double)pos.get(2), 0.0D, 0.0D,0.0D, 2, 8), this.dimension);
							AxisAlignedBB AoePoint = new AxisAlignedBB((double)pos.get(0) - 2.25D, (double)pos.get(1) - 0.5D, (double)pos.get(2) - 2.25D, (double)pos.get(0) + 2.25D, (double)pos.get(1) + 10.0D, (double)pos.get(2) + 2.25D);
							List<EntityPlayer> AABBPlayer = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint);
							if (!AABBPlayer.isEmpty())
						    {
						        for (EntityPlayer ent : AABBPlayer)
						        {
						        	ent.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 1));
						        	PacketHandler.INSTANCE.sendTo(new PacketCarapaceParticleRing(ent, this.world, 9, (double)pos.get(0), (double)pos.get(1), (double)pos.get(2), 0.0D, 0.0D,0.0D, 3), (EntityPlayerMP) ent);
						        	ent.attackEntityFrom(DamageSource.MAGIC, 30.0F);
						        }
						    }
							
							this.explosionPoints.clear();
							this.explosionPointTimer = _ConfigEntityCarapace.EXPLOSION_TIMER;
						}
					}
				}
    		}
    		
    		List<EntityPlayer> players = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE).offset(0, -5, 0));
	        if(players.isEmpty())
	        {
	        	if(this.getPhase() != 0)
	        	{
	        		this.resetFight();
	        	}
	        }
    		
    		if(this.getPhase() == 0)
	        {
	    		if(this.pre_phase_talk > 0)
	    		{
	    			this.setInvulState(true);
	    			List<EntityCarapaceAnguish> list6 = this.world.<EntityCarapaceAnguish>getEntitiesWithinAABB(EntityCarapaceAnguish.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
	    	    	if(!list6.isEmpty())
	    	    	{
	    		    	for (EntityCarapaceAnguish entity : list6)
	    		        {
	    		    		entity.isDead = true;
	    		        }
	    	    	}
		    		this.pre_phase_talk--;
		    		
		    		if(this.pre_phase_talk == _ConfigEntityVoidEntity.TALK_PHASE_TIMER - 1)
		    		{
		    			this.sayChatLine(0);
		    		}
		    		
		    		if(this.pre_phase_talk == 600)
		    		{
		    			this.sayChatLine(1);
		    		}
	    		}
	    		else
	    		{
	    			this.setInvulState(false);
	    		}
	        }

    		if(this.getPhase() == 0) //HP Scale
	        {
	        	int numOfPlayers = 0;
	        	List<EntityPlayer> players1 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE).offset(0, -5, 0));
		        if(!players1.isEmpty())
		        {
		        	for (EntityPlayer entity : players1)
			        {
		        		numOfPlayers++;
			        }
		        }
		        
		        this.setNumOfPlayers(numOfPlayers);
	        }
    		
    		if(this.musicPlaying)
    		{
	    		this.musicDuration--;
		        if(this.musicDuration <= 0)
		        {
		        	this.musicDuration = _ConfigEntityCarapace.MUSIC_DURATION;
		        	PacketHandler.INSTANCE.sendToDimension(new PacketPlayMusic(this, this.world, 1), this.dimension);
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
    		if(!this.world.isRemote) // Start fight
        	{
        		if(this.getMaxHealth() != this.getHealth())
        		{
        			this.setPhase(1);
        			this.setInvisible(true);
        			PacketHandler.INSTANCE.sendToDimension(new PacketPlayMusic(this, this.world, 1), this.dimension);
        			this.musicPlaying = true;
        			this.closeDoor(97, 48, -1);
        			this.sayChatLine(2);
        		}
        	}
    		break;
    		
    		
    		
    	/**
         * =================================================
         * PHASE 1 
         * =================================================
        **/
    	case 1:
    		if(!this.world.isRemote)
    		{
    			this.setInvulState(true);
    			this.setInvisible(true);
    			List<EntityPlayer> GlimpseList = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
    			for (EntityPlayer player : GlimpseList)
    	        {
    				player.addPotionEffect(new PotionEffect(EMCorePotionEffects.POTION_GLIMPSE, 20, 0));
    				PacketHandler.INSTANCE.sendTo(new PacketCustomPotionEffect(player, this.world, 1, 20, 0), (EntityPlayerMP) player);
    	        }
    			
    			this.firstLunacySpawnTimer--;
    			if(this.firstLunacySpawnTimer <= 0)
    			{
	    			if(!this.spawnedFirstLunacy)
	    			{
	    				this.spawnedFirstLunacy = true;
	    				
	    				EntityLunacy entity = new EntityLunacy(this.world);
	    		       	entity.setPosition(this.getPosition().getX() + 10, this.getPosition().getY() + 1, this.getPosition().getZ());
	    		       	entity.enablePersistence();
	    		   		this.world.spawnEntity(entity);
	    			}
    			}
    			
    			this.glimpseAnimationTimer--;
    			if(this.glimpseAnimationTimer == 33)
    			{
    				this.playVoidMagicExplosionSound(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 1, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 30)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 3, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 27)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 5, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 24)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 7, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 21)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 9, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 18)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 11, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 15)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 13, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 12)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 15, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 9)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 17, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 6)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 19, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 3)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 21, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 0)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 23, 3), this.dimension);
    			}
    			
    			List<EntityLunacy> LunacyList = this.world.<EntityLunacy>getEntitiesWithinAABB(EntityLunacy.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
    			if(LunacyList.isEmpty() && this.firstLunacySpawnTimer < 0)
    	        {
    				this.setPhase(2);
    				this.setInvisible(false);
    				this.glimpseAnimationTimer = _ConfigEntityVoidEntity.GLIMPSE_ANIM_TIMER;
    				this.sayChatLine(3);
    	        }
    		}
    		break;
    		
    		
    		
    	/**
         * =================================================
         * PHASE 2 
         * =================================================
        **/
    	case 2:
    		if(!this.world.isRemote)
    		{
    			this.setInvulState(false);
    			
    			this.glimpseAnimationTimer--;
    			if(this.glimpseAnimationTimer == 33)
    			{
    				this.playVoidMagicExplosionSound(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 1, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 30)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 3, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 27)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 5, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 24)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 7, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 21)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 9, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 18)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 11, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 15)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 13, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 12)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 15, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 9)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 17, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 6)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 19, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 3)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 21, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 0)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 23, 3), this.dimension);
    			}
    			
    			this.P2Timer--;
    			if(this.P2Timer <= 0)
    			{
    				this.glimpseAnimationTimer = _ConfigEntityVoidEntity.GLIMPSE_ANIM_TIMER;
    				EntityVoidSpectralSmall entity = new EntityVoidSpectralSmall(this.world);
    		       	entity.setPosition(this.getPosition().getX() + 14, this.getPosition().getY() + 1, this.getPosition().getZ());
    		       	entity.enablePersistence();
    		   		this.world.spawnEntity(entity);
    		   		
    		   		EntityVoidSpectralSmall entity2 = new EntityVoidSpectralSmall(this.world);
    		       	entity2.setPosition(this.getPosition().getX() - 14, this.getPosition().getY() + 1, this.getPosition().getZ());
    		       	entity2.enablePersistence();
    		   		this.world.spawnEntity(entity2);
    		   		
    		   		EntityVoidSpectralSmall entity3 = new EntityVoidSpectralSmall(this.world);
    		       	entity3.setPosition(this.getPosition().getX(), this.getPosition().getY() + 1, this.getPosition().getZ() + 14);
    		       	entity3.enablePersistence();
    		   		this.world.spawnEntity(entity3);
    		   		
    		   		EntityVoidSpectralSmall entity4 = new EntityVoidSpectralSmall(this.world);
    		       	entity4.setPosition(this.getPosition().getX(), this.getPosition().getY() + 1, this.getPosition().getZ() - 14);
    		       	entity4.enablePersistence();
    		   		this.world.spawnEntity(entity4);
    				this.setPhase(3);
    				this.sayChatLine(4);
    			}
    		}
    		break;
    		
    		
    		
    	/**
         * =================================================
         * PHASE 3 
         * =================================================
        **/
    	case 3:
    		if(!this.world.isRemote)
    		{
    			this.setInvulState(false);
    			
    			this.P3SoakCD--;
    			if(this.P3SoakCD <= 0)
    			{
    				List<EntityPlayer> SoakList = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
	    			if(!this.P3SoakPlayerFound) 
	    			{
	    				this.P3SoakPlayerFound = true;
	    				this.P3SoakPlayer = SoakList.get(this.rand.nextInt(SoakList.size()));
	    				this.playVoidMagicCastSound(this.P3SoakPlayer.getPosition().getX(), this.P3SoakPlayer.getPosition().getY(), this.P3SoakPlayer.getPosition().getZ());
	    			}
	    			PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceParticleRing(this, this.world, 9, this.P3SoakPlayer.posX, this.P3SoakPlayer.posY, this.P3SoakPlayer.posZ, 0.0D, 0.0D,0.0D, 3), this.dimension);
	    			PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceRingExplosion(this, this.world, 9, this.P3SoakPlayer.posX, this.P3SoakPlayer.posY, this.P3SoakPlayer.posZ, 0.0D, 0.0D,0.0D, 1, 8), this.dimension);
	    			this.P3SoakTimer--;
	    			if(this.P3SoakTimer <= 0)
	    			{
	    				this.playVoidMagicExplosionSound(this.P3SoakPlayer.getPosition().getX(), this.P3SoakPlayer.getPosition().getY(), this.P3SoakPlayer.getPosition().getZ());
	    				PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceRingExplosion(this, this.world, 9, this.P3SoakPlayer.posX, this.P3SoakPlayer.posY, this.P3SoakPlayer.posZ, 0.0D, 0.0D,0.0D, 3, 8), this.dimension);
	    				AxisAlignedBB AoePoint = new AxisAlignedBB(this.P3SoakPlayer.posX - 3.25D, this.P3SoakPlayer.posY - 0.5D, this.P3SoakPlayer.posZ - 3.25D, this.P3SoakPlayer.posX + 3.25D, this.P3SoakPlayer.posY + 10.0D, this.P3SoakPlayer.posZ + 3.25D);
						List<EntityPlayer> AABBPlayer = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint);
						if (!AABBPlayer.isEmpty())
					    {
							if(AABBPlayer.size() > 1)
							{
						        for (EntityPlayer ent : AABBPlayer)
						        {
						        	float damage = (25.0F * (AABBPlayer.size() / 2)) / AABBPlayer.size();
						        	ent.attackEntityFrom(DamageSource.MAGIC, damage);
						        }
							}
					    }
						
	    				this.P3SoakCD = _ConfigEntityVoidEntity.P3SOAKCD;
	    				this.P3SoakTimer = _ConfigEntityVoidEntity.P3SOAKTIMER;
	    				this.P3SoakPlayer = null;
	    				this.P3SoakPlayerFound = false;
	    			}
    			}
    			
    			this.P3ExplosionCD--;
    			if(this.P3ExplosionCD <= 0) 
    			{
    				List<EntityPlayer> ExplosionList = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
    				for (EntityPlayer ent : ExplosionList)
			        {
    					this.spawnVoidExplosion(ent.posX, 53, ent.posZ);
			        }
    				this.P3ExplosionCD = _ConfigEntityVoidEntity.P3EXPLOSIONTIMER;
    			}
    			
    			this.P3MinionSpawnTimer--;
    			if(this.P3MinionSpawnTimer <= 0)
    			{
    				this.P3MinionSpawnTimer = _ConfigEntityVoidEntity.P3MINIONSPAWNTIMER;
    				EntityVoidSpectralSmall entity = new EntityVoidSpectralSmall(this.world);
    		       	entity.setPosition(this.getPosition().getX() + 14, this.getPosition().getY() + 1, this.getPosition().getZ());
    		       	entity.enablePersistence();
    		   		this.world.spawnEntity(entity);
    		   		
    		   		EntityVoidSpectralSmall entity2 = new EntityVoidSpectralSmall(this.world);
    		       	entity2.setPosition(this.getPosition().getX() - 14, this.getPosition().getY() + 1, this.getPosition().getZ());
    		       	entity2.enablePersistence();
    		   		this.world.spawnEntity(entity2);
    		   		
    		   		EntityVoidSpectralSmall entity3 = new EntityVoidSpectralSmall(this.world);
    		       	entity3.setPosition(this.getPosition().getX(), this.getPosition().getY() + 1, this.getPosition().getZ() + 14);
    		       	entity3.enablePersistence();
    		   		this.world.spawnEntity(entity3);
    		   		
    		   		EntityVoidSpectralSmall entity4 = new EntityVoidSpectralSmall(this.world);
    		       	entity4.setPosition(this.getPosition().getX(), this.getPosition().getY() + 1, this.getPosition().getZ() - 14);
    		       	entity4.enablePersistence();
    		   		this.world.spawnEntity(entity4);
    			}
    			
    			if(this.getHealth() <= (this.getMaxHealth() / 4) * 3)
    			{
    				this.setPhase(4);
    				this.sayChatLine(5);
    				this.setInvisible(true);
    				List<EntityVoidSpectralSmall> VSSList = this.world.<EntityVoidSpectralSmall>getEntitiesWithinAABB(EntityVoidSpectralSmall.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
    				for (EntityVoidSpectralSmall ent : VSSList)
    		        {
    					ent.isDead = true;
    		        }
    				List<EntityVoidSpectralMedium> VSMList = this.world.<EntityVoidSpectralMedium>getEntitiesWithinAABB(EntityVoidSpectralMedium.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
    				for (EntityVoidSpectralMedium ent : VSMList)
    		        {
    					ent.isDead = true;
    		        }
    				List<EntityVoidSpectralLarge> VSLList = this.world.<EntityVoidSpectralLarge>getEntitiesWithinAABB(EntityVoidSpectralLarge.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
    				for (EntityVoidSpectralLarge ent : VSLList)
    		        {
    					ent.isDead = true;
    		        }
    			}
    		}
    		break;
    		
    		
    		
    	/**
         * =================================================
         * PHASE 4 
         * =================================================
        **/
    	case 4:
    		if(!this.world.isRemote)
    		{
	    		this.setInvulState(true);
	    		this.setInvisible(true);
				List<EntityPlayer> GlimpseList = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
				for (EntityPlayer player : GlimpseList)
		        {
					player.addPotionEffect(new PotionEffect(EMCorePotionEffects.POTION_GLIMPSE, 20, 0));
					PacketHandler.INSTANCE.sendTo(new PacketCustomPotionEffect(player, this.world, 1, 20, 0), (EntityPlayerMP) player);
		        }
				
				this.secondLunacySpawnTimer--;
				if(this.secondLunacySpawnTimer <= 0)
				{
	    			if(!this.spawnedSecondLunacy)
	    			{
	    				this.spawnedSecondLunacy = true;
	    				
	    				EntityLunacy entity = new EntityLunacy(this.world);
	    		       	entity.setPosition(this.getPosition().getX() - 10, this.getPosition().getY() + 1, this.getPosition().getZ());
	    		       	entity.enablePersistence();
	    		   		this.world.spawnEntity(entity);
	    			}
				}
				
				this.glimpseAnimationTimer--;
				if(this.glimpseAnimationTimer == 33)
				{
					this.playVoidMagicExplosionSound(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
					PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 1, 3), this.dimension);
				}
				else if(this.glimpseAnimationTimer == 30)
				{
					PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 3, 3), this.dimension);
				}
				else if(this.glimpseAnimationTimer == 27)
				{
					PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 5, 3), this.dimension);
				}
				else if(this.glimpseAnimationTimer == 24)
				{
					PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 7, 3), this.dimension);
				}
				else if(this.glimpseAnimationTimer == 21)
				{
					PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 9, 3), this.dimension);
				}
				else if(this.glimpseAnimationTimer == 18)
				{
					PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 11, 3), this.dimension);
				}
				else if(this.glimpseAnimationTimer == 15)
				{
					PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 13, 3), this.dimension);
				}
				else if(this.glimpseAnimationTimer == 12)
				{
					PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 15, 3), this.dimension);
				}
				else if(this.glimpseAnimationTimer == 9)
				{
					PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 17, 3), this.dimension);
				}
				else if(this.glimpseAnimationTimer == 6)
				{
					PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 19, 3), this.dimension);
				}
				else if(this.glimpseAnimationTimer == 3)
				{
					PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 21, 3), this.dimension);
				}
				else if(this.glimpseAnimationTimer == 0)
				{
					PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 23, 3), this.dimension);
				}
				
				List<EntityLunacy> LunacyList = this.world.<EntityLunacy>getEntitiesWithinAABB(EntityLunacy.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
    			if(LunacyList.isEmpty() && this.secondLunacySpawnTimer < 0)
    	        {
    				this.setPhase(5);
    				this.setInvisible(false);
    				this.glimpseAnimationTimer = _ConfigEntityVoidEntity.GLIMPSE_ANIM_TIMER;
    				EntityVoidSpectralSmall entity = new EntityVoidSpectralSmall(this.world);
    		       	entity.setPosition(this.getPosition().getX() + 14, this.getPosition().getY() + 1, this.getPosition().getZ());
    		       	entity.enablePersistence();
    		   		this.world.spawnEntity(entity);
    		   		
    		   		EntityVoidSpectralSmall entity2 = new EntityVoidSpectralSmall(this.world);
    		       	entity2.setPosition(this.getPosition().getX() - 14, this.getPosition().getY() + 1, this.getPosition().getZ());
    		       	entity2.enablePersistence();
    		   		this.world.spawnEntity(entity2);
    		   		
    		   		EntityVoidSpectralSmall entity3 = new EntityVoidSpectralSmall(this.world);
    		       	entity3.setPosition(this.getPosition().getX(), this.getPosition().getY() + 1, this.getPosition().getZ() + 14);
    		       	entity3.enablePersistence();
    		   		this.world.spawnEntity(entity3);
    		   		
    		   		EntityVoidSpectralSmall entity4 = new EntityVoidSpectralSmall(this.world);
    		       	entity4.setPosition(this.getPosition().getX(), this.getPosition().getY() + 1, this.getPosition().getZ() - 14);
    		       	entity4.enablePersistence();
    		   		this.world.spawnEntity(entity4);
    				this.sayChatLine(6);
    	        }
    		}
    		break;
    		
    		
    		
    	/**
         * =================================================
         * PHASE 5 
         * =================================================
        **/
    	case 5:
    		if(!this.world.isRemote)
    		{
    			this.setInvulState(false);
    			this.glimpseAnimationTimer--;
    			if(this.glimpseAnimationTimer == 33)
    			{
    				this.playVoidMagicExplosionSound(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 1, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 30)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 3, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 27)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 5, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 24)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 7, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 21)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 9, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 18)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 11, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 15)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 13, 3), this.dimension);
    				List<EntityCarapaceAnguish> AnguishList = this.world.<EntityCarapaceAnguish>getEntitiesWithinAABB(EntityCarapaceAnguish.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
	    			for (EntityCarapaceAnguish ent : AnguishList)
	    	        {
	    				ent.isDead = true;
	    	        }
    			}
    			else if(this.glimpseAnimationTimer == 12)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 15, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 9)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 17, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 6)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 19, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 3)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 21, 3), this.dimension);
    			}
    			else if(this.glimpseAnimationTimer == 0)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketVEGlimpseAnimation(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 23, 3), this.dimension);
    			}
    			
    			this.P4SoakCD--;
    			if(this.P4SoakCD <= 0)
    			{
    				List<EntityPlayer> SoakList = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE));
	    			if(!this.P4SoakPlayerFound) 
	    			{
	    				this.P4SoakPlayerFound = true;
	    				this.P4SoakPlayer = SoakList.get(this.rand.nextInt(SoakList.size()));
	    				this.playVoidMagicCastSound(this.P4SoakPlayer.getPosition().getX(), this.P4SoakPlayer.getPosition().getY(), this.P4SoakPlayer.getPosition().getZ());
	    			}
	    			PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceParticleRing(this, this.world, 9, this.P4SoakPlayer.posX, this.P4SoakPlayer.posY, this.P4SoakPlayer.posZ, 0.0D, 0.0D,0.0D, 3), this.dimension);
	    			PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceRingExplosion(this, this.world, 9, this.P4SoakPlayer.posX, this.P4SoakPlayer.posY, this.P4SoakPlayer.posZ, 0.0D, 0.0D,0.0D, 1, 8), this.dimension);
	    			this.P4SoakTimer--;
	    			if(this.P4SoakTimer <= 0)
	    			{
	    				this.playVoidMagicExplosionSound(this.P4SoakPlayer.getPosition().getX(), this.P4SoakPlayer.getPosition().getY(), this.P4SoakPlayer.getPosition().getZ());
	    				PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceRingExplosion(this, this.world, 9, this.P4SoakPlayer.posX, this.P4SoakPlayer.posY, this.P4SoakPlayer.posZ, 0.0D, 0.0D,0.0D, 3, 8), this.dimension);
	    				AxisAlignedBB AoePoint = new AxisAlignedBB(this.P4SoakPlayer.posX - 3.25D, this.P4SoakPlayer.posY - 0.5D, this.P4SoakPlayer.posZ - 3.25D, this.P4SoakPlayer.posX + 3.25D, this.P4SoakPlayer.posY + 10.0D, this.P4SoakPlayer.posZ + 3.25D);
						List<EntityPlayer> AABBPlayer = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint);
						if (!AABBPlayer.isEmpty())
					    {
							if(AABBPlayer.size() > 1)
							{
						        for (EntityPlayer ent : AABBPlayer)
						        {
						        	float damage = (25.0F * (AABBPlayer.size() / 2)) / AABBPlayer.size();
						        	ent.attackEntityFrom(DamageSource.MAGIC, damage);
						        }
							}
					    }
						
	    				this.P4SoakCD = _ConfigEntityVoidEntity.P4SOAKCD;
	    				this.P4SoakTimer = _ConfigEntityVoidEntity.P4SOAKTIMER;
	    				this.P4SoakPlayer = null;
	    				this.P4SoakPlayerFound = false;
	    			}
    			}
    			
    			this.P4ExplosionCD--;
    			if(this.P4ExplosionCD <= 0) 
    			{
    				List<EntityPlayer> ExplosionList = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
    				for (EntityPlayer ent : ExplosionList)
			        {
    					this.spawnVoidExplosion(ent.posX, 53, ent.posZ);
			        }
    				this.P4ExplosionCD = _ConfigEntityVoidEntity.P4EXPLOSIONTIMER;
    			}
    			
    			this.P4MinionSpawnTimer--;
    			if(this.P4MinionSpawnTimer <= 0)
    			{
    				this.P4MinionSpawnTimer = _ConfigEntityVoidEntity.P4MINIONSPAWNTIMER;
    				EntityVoidSpectralSmall entity = new EntityVoidSpectralSmall(this.world);
    		       	entity.setPosition(this.getPosition().getX() + 14, this.getPosition().getY() + 1, this.getPosition().getZ());
    		       	entity.enablePersistence();
    		   		this.world.spawnEntity(entity);
    		   		
    		   		EntityVoidSpectralSmall entity2 = new EntityVoidSpectralSmall(this.world);
    		       	entity2.setPosition(this.getPosition().getX() - 14, this.getPosition().getY() + 1, this.getPosition().getZ());
    		       	entity2.enablePersistence();
    		   		this.world.spawnEntity(entity2);
    		   		
    		   		EntityVoidSpectralSmall entity3 = new EntityVoidSpectralSmall(this.world);
    		       	entity3.setPosition(this.getPosition().getX(), this.getPosition().getY() + 1, this.getPosition().getZ() + 14);
    		       	entity3.enablePersistence();
    		   		this.world.spawnEntity(entity3);
    		   		
    		   		EntityVoidSpectralSmall entity4 = new EntityVoidSpectralSmall(this.world);
    		       	entity4.setPosition(this.getPosition().getX(), this.getPosition().getY() + 1, this.getPosition().getZ() - 14);
    		       	entity4.enablePersistence();
    		   		this.world.spawnEntity(entity4);
    			}
    			
    			if(this.getHealth() <= this.getMaxHealth() / 2)
    			{
    				this.setPhase(6);
    				this.sayChatLine(7);
    				EntitySlaveMaster entity = new EntitySlaveMaster(this.world);
    				entity.setPosition(this.getPosition().getX(), this.getPosition().getY() + 1, this.getPosition().getZ() - 14);
    				entity.enablePersistence();
    		   		this.world.spawnEntity(entity);
    			}
    		}
    		break;
    		
    		
    		
    	/**
         * =================================================
         * PHASE 6 
         * =================================================
        **/
    	case 6:
    		if(!this.world.isRemote)
    		{
    			this.setInvulState(false);
    			
    			this.P6MinionSpawnTimer--;
    			this.P6SecondMinionSpawnTimer--;
    			if(this.P6SecondMinionSpawnTimer <= 0) this.P6SecondMinionSpawn = true;
    			if(this.P6MinionSpawnTimer <= 0)
    			{
    				this.P6MinionSpawnTimer = _ConfigEntityVoidEntity.P6MINIONSPAWNTIMER;
    				EntitySlaveMaster entity = new EntitySlaveMaster(this.world);
    				entity.setPosition(this.getPosition().getX(), this.getPosition().getY() + 1, this.getPosition().getZ() - 14);
    				entity.enablePersistence();
    		   		this.world.spawnEntity(entity);
    		   		
    		   		if(this.P6SecondMinionSpawn)
    		   		{
    		   			EntitySlaveMaster entity2 = new EntitySlaveMaster(this.world);
        				entity2.setPosition(this.getPosition().getX(), this.getPosition().getY() + 1, this.getPosition().getZ() + 14);
        				entity2.enablePersistence();
        		   		this.world.spawnEntity(entity2);
    		   		}
    			}
    			
    			this.P6ExplosionCD--;
    			if(this.P6ExplosionCD <= 0) 
    			{
    				List<EntityPlayer> ExplosionList = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE));
    				for (EntityPlayer ent : ExplosionList)
			        {
    					this.spawnVoidExplosion(ent.posX, 53, ent.posZ);
			        }
    				this.P6ExplosionCD = _ConfigEntityVoidEntity.P6EXPLOSIONTIMER;
    			}
    			
    			this.P6SoakCD--;
    			if(this.P6SoakCD <= 0 && this.P6ConeCD >= 0)
    			{
    				List<EntityPlayer> SoakList = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE));
	    			if(!this.P6SoakPlayerFound) 
	    			{
	    				this.P6SoakPlayerFound = true;
	    				this.P6SoakPos = SoakList.get(this.rand.nextInt(SoakList.size())).getPosition();
	    				this.playVoidMagicCastSound(this.P6SoakPos.getX(), this.P6SoakPos.getY(), this.P6SoakPos.getZ());
	    			}
	    			PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceParticleRing(this, this.world, 9, (double)this.P6SoakPos.getX(), (double)this.P6SoakPos.getY(), (double)this.P6SoakPos.getZ(), 0.0D, 0.0D,0.0D, 3), this.dimension);
	    			AxisAlignedBB AoePoint = new AxisAlignedBB((double)this.P6SoakPos.getX() - 3.25D, (double)this.P6SoakPos.getY() - 0.5D, (double)this.P6SoakPos.getZ() - 3.25D, (double)this.P6SoakPos.getX() + 3.25D, (double)this.P6SoakPos.getY() + 10.0D, (double)this.P6SoakPos.getZ() + 3.25D);
					List<EntityPlayer> AABBPlayer = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint);
					if (AABBPlayer.isEmpty())
				    {
						PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceRingExplosion(this, this.world, 9, (double)this.P6SoakPos.getX(), (double)this.P6SoakPos.getY(), (double)this.P6SoakPos.getZ(), 0.0D, 0.0D,0.0D, 1, 8), this.dimension);
				    }
	    			this.P6SoakTimer--;
	    			if(this.P6SoakTimer <= 0)
	    			{
	    				this.playVoidMagicExplosionSound(this.P6SoakPos.getX(), this.P6SoakPos.getY(), this.P6SoakPos.getZ());
	    				PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceRingExplosion(this, this.world, 9, (double)this.P6SoakPos.getX(), (double)this.P6SoakPos.getY(), (double)this.P6SoakPos.getZ(), 0.0D, 0.0D,0.0D, 3, 8), this.dimension);
						if (!AABBPlayer.isEmpty())
					    {
							if(AABBPlayer.size() > 1)
							{
						        for (EntityPlayer ent : AABBPlayer)
						        {
						        	float damage = (25.0F * (AABBPlayer.size() / 2)) / AABBPlayer.size();
						        	ent.attackEntityFrom(DamageSource.MAGIC, damage);
						        }
							}
					    }
						else
						{
							List<EntityPlayer> AABBPlayer2 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint.grow(100.0D, 100.0D, 100.0D));
							if (!AABBPlayer.isEmpty())
						    {
								for (EntityPlayer ent : AABBPlayer2)
						        {
									ent.attackEntityFrom(DamageSource.MAGIC, 999999999.9F);
						        }
						    }
							PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceParticleCircle(this, this.world, 9, (double)this.P6SoakPos.getX(), (double)this.P6SoakPos.getY(), (double)this.P6SoakPos.getZ(), 0.0D, 0.0D,0.0D, 15), this.dimension);
						}
						
	    				this.P6SoakCD = _ConfigEntityVoidEntity.P4SOAKCD;
	    				this.P6SoakTimer = _ConfigEntityVoidEntity.P4SOAKTIMER;
	    				this.P6SoakPos = null;
	    				this.P6SoakPlayerFound = false;
	    			}
    			}
    			
    			this.P6AnguishTimer--;
    			if(this.P6AnguishTimer <= 0)
    			{
    				this.P6AnguishTimer = _ConfigEntityVoidEntity.P6ANGUISHTIMER;
    				
    				List<EntityPlayer> AnguishList = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE));
        			for (EntityPlayer player : AnguishList)
        	        {
        				EntityGrowingAnguishSmall entity = new EntityGrowingAnguishSmall(this.world);
        				entity.setPosition(player.posX, 53, player.posZ);
        				entity.enablePersistence();
        		   		this.world.spawnEntity(entity);
        	        }
    			}
    			
    			this.P6ConeCD--;
    			if(this.P6ConeCD <= 0)
    			{
	    			Random rand = new Random();
	        		if(!this.P6FacingFound)
	        		{
	        			this.P6FacingFound = true;
	        			this.playVoidMagicCastSound(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
	        			this.P6Facing = rand.nextInt(2);
	        		}
	    			
	    			this.P6ConeCastTime--;
	    			switch(this.P6Facing)
	    			{
	    			case 0:
	    				PacketHandler.INSTANCE.sendToDimension(new PacketVESightAttack(this, this.world, this.posX, this.posY + 1, this.posZ, 0.0D, 0.0D, 0.0D, 0), this.dimension);
	    				PacketHandler.INSTANCE.sendToDimension(new PacketVESightAttack(this, this.world, this.posX, this.posY + 1, this.posZ, 0.0D, 0.0D, 0.0D, 2), this.dimension);
	    				break;
	    			case 1:
	    				PacketHandler.INSTANCE.sendToDimension(new PacketVESightAttack(this, this.world, this.posX, this.posY + 1, this.posZ, 0.0D, 0.0D, 0.0D, 1), this.dimension);
	    				PacketHandler.INSTANCE.sendToDimension(new PacketVESightAttack(this, this.world, this.posX, this.posY + 1, this.posZ, 0.0D, 0.0D, 0.0D, 3), this.dimension);
	    				break;
	    			}
	    			if(this.P6ConeCastTime <= 0)
	    			{
	    				switch(this.P6Facing)
	        			{
	        			case 0:
	        				PacketHandler.INSTANCE.sendToDimension(new PacketVESightExplode(this, this.world, this.posX, this.posY + 1, this.posZ, 0.0D, 0.0D, 0.0D, 0, 10), this.dimension);
	        				AxisAlignedBB AoePoint1 = new AxisAlignedBB(this.posX, this.posY + 1, this.posZ, this.posX + 100, this.posY + 50, this.posZ + 100);
	        				List<EntityPlayer> AABBPlayer1 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint1);
	        				if (!AABBPlayer1.isEmpty())
	        			    {
	        			        for (EntityPlayer ent : AABBPlayer1)
	        			        {
	        			        	ent.attackEntityFrom(DamageSource.MAGIC, 40.0f);
	        			        	ent.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100, 0));
	        			        	ent.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 1));
	        			        }
	        			    }
	        				
	        				PacketHandler.INSTANCE.sendToDimension(new PacketVESightExplode(this, this.world, this.posX, this.posY + 1, this.posZ, 0.0D, 0.0D, 0.0D, 2, 10), this.dimension);
	        				AxisAlignedBB AoePoint3 = new AxisAlignedBB(this.posX, this.posY + 1, this.posZ, this.posX - 100, this.posY + 50, this.posZ - 100);
	        				List<EntityPlayer> AABBPlayer3 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint3);
	        				if (!AABBPlayer3.isEmpty())
	        			    {
	        			        for (EntityPlayer ent : AABBPlayer3)
	        			        {
	        			        	ent.attackEntityFrom(DamageSource.MAGIC, 40.0f);
	        			        	ent.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100, 0));
	        			        	ent.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 1));
	        			        }
	        			    }
	        				break;
	        			case 1:
	        				PacketHandler.INSTANCE.sendToDimension(new PacketVESightExplode(this, this.world, this.posX, this.posY + 1, this.posZ, 0.0D, 0.0D, 0.0D, 1, 10), this.dimension);
	        				AxisAlignedBB AoePoint2 = new AxisAlignedBB(this.posX, this.posY + 1, this.posZ, this.posX - 100, this.posY + 50, this.posZ + 100);
	        				List<EntityPlayer> AABBPlayer2 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint2);
	        				if (!AABBPlayer2.isEmpty())
	        			    {
	        			        for (EntityPlayer ent : AABBPlayer2)
	        			        {
	        			        	ent.attackEntityFrom(DamageSource.MAGIC, 40.0f);
	        			        	ent.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100, 0));
	        			        	ent.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 1));
	        			        }
	        			    }
	        				
	        				PacketHandler.INSTANCE.sendToDimension(new PacketVESightExplode(this, this.world, this.posX, this.posY + 1, this.posZ, 0.0D, 0.0D, 0.0D, 3, 10), this.dimension);
	        				AxisAlignedBB AoePoint4 = new AxisAlignedBB(this.posX, this.posY + 1, this.posZ, this.posX + 100, this.posY + 50, this.posZ - 100);
	        				List<EntityPlayer> AABBPlayer4 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint4);
	        				if (!AABBPlayer4.isEmpty())
	        			    {
	        			        for (EntityPlayer ent : AABBPlayer4)
	        			        {
	        			        	ent.attackEntityFrom(DamageSource.MAGIC, 40.0f);
	        			        	ent.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100, 0));
	        			        	ent.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 1));
	        			        }
	        			    }
	        				break;
	        			}
	    				
	    				this.playVoidMagicExplosionSound(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
	    				this.P6ConeCastTime = _ConfigEntityVoidEntity.P6CONETIMER;
	    				this.P6FacingFound = false;
	    				this.P6Facing = 0;
	    				this.P6ConeCD = _ConfigEntityVoidEntity.P6CONECD;
	    				this.P6SoakCD = _ConfigEntityVoidEntity.P4SOAKCD;
	    				this.P6SoakTimer = _ConfigEntityVoidEntity.P4SOAKTIMER;
	    			}
    			}
    			
    			if(this.getHealth() <= 25.0F)
    			{
    				this.setPhase(7);
    				this.sayChatLine(8);
    				
    				List<EntityVEAnguish> AnguishList = this.world.<EntityVEAnguish>getEntitiesWithinAABB(EntityVEAnguish.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
    				for (EntityVEAnguish ent : AnguishList)
    		        {
    					ent.isDead = true;
    		        }
    				
    				List<EntityGrowingAnguishSmall> AnguishList1 = this.world.<EntityGrowingAnguishSmall>getEntitiesWithinAABB(EntityGrowingAnguishSmall.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
    				for (EntityGrowingAnguishSmall ent : AnguishList1)
    		        {
    					ent.isDead = true;
    		        }
    				
    				List<EntityGrowingAnguishMedium> AnguishList2 = this.world.<EntityGrowingAnguishMedium>getEntitiesWithinAABB(EntityGrowingAnguishMedium.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
    				for (EntityGrowingAnguishMedium ent : AnguishList2)
    		        {
    					ent.isDead = true;
    		        }
    				
    				List<EntityGrowingAnguishLarge> AnguishList3 = this.world.<EntityGrowingAnguishLarge>getEntitiesWithinAABB(EntityGrowingAnguishLarge.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
    				for (EntityGrowingAnguishLarge ent : AnguishList3)
    		        {
    					ent.isDead = true;
    		        }
    				
    				List<EntitySlaveMaster> SlaveMasterList = this.world.<EntitySlaveMaster>getEntitiesWithinAABB(EntitySlaveMaster.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
    				for (EntitySlaveMaster ent : SlaveMasterList)
    		        {
    					ent.isDead = true;
    		        }
    				
    				List<EntityVoidSpectralSmall> VSSList = this.world.<EntityVoidSpectralSmall>getEntitiesWithinAABB(EntityVoidSpectralSmall.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
    				for (EntityVoidSpectralSmall ent : VSSList)
    		        {
    					ent.isDead = true;
    		        }
    				
    				List<EntityVoidSpectralMedium> VSMList = this.world.<EntityVoidSpectralMedium>getEntitiesWithinAABB(EntityVoidSpectralMedium.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
    				for (EntityVoidSpectralMedium ent : VSMList)
    		        {
    					ent.isDead = true;
    		        }
    				
    				List<EntityVoidSpectralLarge> VSLList = this.world.<EntityVoidSpectralLarge>getEntitiesWithinAABB(EntityVoidSpectralLarge.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
    				for (EntityVoidSpectralLarge ent : VSLList)
    		        {
    					ent.isDead = true;
    		        }
    			}
    		}
    		break;
    	case 7:
    		if(!this.world.isRemote)
    		{
    			this.setInvulState(true);
    			
    			this.death_phase_talk--;
    			
    			if(this.death_phase_talk == _ConfigEntityVoidEntity.TALK_PHASE_TIMER2 - 200)
    			{
    				this.sayChatLine(9);
    			}
    			
    			if(this.death_phase_talk <= 0)
    			{
    				this.sayChatLine(10);
    				EntityVoidEntityChest entity = new EntityVoidEntityChest(this.world);
    				entity.setPosition(this.getPosition().getX() - 7, this.getPosition().getY() + 5, this.getPosition().getZ());
    		   		this.world.spawnEntity(entity);
    		   		
    		   		PacketHandler.INSTANCE.sendToDimension(new PacketStopMusic(this, this.world), this.dimension);
    		    	this.openDoorDeath(97, 49, -1);
    				this.setDead();
    			}
    		}
    		break;
    	}
    	
		this.ticksExisted++;
        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }
	
	@Override
	protected void updateAITasks()
    {
		
    }
	
	@Override
	public float getEyeHeight()
	{
		return 8.3F;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return EMSoundHandler.VE_WOUND;
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
		if(this.getInvulState() == false)
		{
			return super.attackEntityFrom(source, this.calculateHealthReduction(amount));
		}
		else
		{
			return false;
		}
    }
    
    @Override
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);
    }
    
    public void resetFight()
    {
    	this.explosionPoints.clear();
    	this.explosionPointTimer = _ConfigEntityVoidEntity.EXPLOSION_TIMER;
    	this.musicPlaying = false;
    	this.musicDuration = _ConfigEntityVoidEntity.MUSIC_DURATION;
    	this.pre_phase_talk = _ConfigEntityVoidEntity.TALK_PHASE_TIMER;
    	this.spawnedFirstLunacy = false;
    	this.firstLunacySpawnTimer = _ConfigEntityVoidEntity.FIRST_LUNACY_SPAWN_TIMER;
    	this.glimpseAnimationTimer = _ConfigEntityVoidEntity.GLIMPSE_ANIM_TIMER;
    	this.P2Timer = _ConfigEntityVoidEntity.P2TIMER;
    	this.P3SoakCD = _ConfigEntityVoidEntity.P3SOAKCD;
    	this.P3SoakTimer = _ConfigEntityVoidEntity.P3SOAKTIMER;
    	this.P3SoakPlayerFound = false;
    	this.P3SoakPlayer = null;
    	this.P3ExplosionCD = _ConfigEntityVoidEntity.P3EXPLOSIONTIMER;
    	this.P3MinionSpawnTimer = _ConfigEntityVoidEntity.P3MINIONSPAWNTIMER;
    	this.secondLunacySpawnTimer = _ConfigEntityVoidEntity.SECOND_LUNACY_SPAWN_TIMER;
    	this.spawnedSecondLunacy = false;
    	this.P4SoakCD = _ConfigEntityVoidEntity.P4SOAKCD;
    	this.P4SoakTimer = _ConfigEntityVoidEntity.P4SOAKTIMER;
    	this.P4SoakPlayerFound = false;
    	this.P4SoakPlayer = null;
    	this.P4ExplosionCD = _ConfigEntityVoidEntity.P4EXPLOSIONTIMER;
    	this.P4MinionSpawnTimer = _ConfigEntityVoidEntity.P4MINIONSPAWNTIMER;
    	this.P6MinionSpawnTimer = _ConfigEntityVoidEntity.P6MINIONSPAWNTIMER;
    	this.P6SecondMinionSpawnTimer = _ConfigEntityVoidEntity.P6SECONDMINIONSPAWNTIMER;
    	this.P6SecondMinionSpawn = false;
    	this.P6ExplosionCD = _ConfigEntityVoidEntity.P6EXPLOSIONTIMER;
    	this.P6SoakCD = _ConfigEntityVoidEntity.P6SOAKCD;
    	this.P6SoakTimer = _ConfigEntityVoidEntity.P6SOAKTIMER;
    	this.P6SoakPlayerFound = false;
    	this.P6SoakPos = null;
    	this.P6AnguishTimer = _ConfigEntityVoidEntity.P6ANGUISHTIMER;
    	this.P6ConeCD = _ConfigEntityVoidEntity.P6CONECD;
    	this.P6ConeCastTime = _ConfigEntityVoidEntity.P6CONETIMER;
    	this.P6FacingFound = false;
    	this.P6Facing = 0;
    	this.setPhase(0);
    	this.setInvulState(true);
    	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(_ConfigEntityVoidEntity.BASE_HP);
    	this.setHealth(_ConfigEntityVoidEntity.BASE_HP);
    	this.setInvisible(false);
    	
    	PacketHandler.INSTANCE.sendToDimension(new PacketStopMusic(this, this.world), this.dimension);
    	this.openDoorDeath(97, 49, -1);
    	List<EntityVEAnguish> AnguishList = this.world.<EntityVEAnguish>getEntitiesWithinAABB(EntityVEAnguish.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
		for (EntityVEAnguish ent : AnguishList)
        {
			ent.isDead = true;
        }
		
		List<EntityGrowingAnguishSmall> AnguishList1 = this.world.<EntityGrowingAnguishSmall>getEntitiesWithinAABB(EntityGrowingAnguishSmall.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
		for (EntityGrowingAnguishSmall ent : AnguishList1)
        {
			ent.isDead = true;
        }
		
		List<EntityGrowingAnguishMedium> AnguishList2 = this.world.<EntityGrowingAnguishMedium>getEntitiesWithinAABB(EntityGrowingAnguishMedium.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
		for (EntityGrowingAnguishMedium ent : AnguishList2)
        {
			ent.isDead = true;
        }
		
		List<EntityGrowingAnguishLarge> AnguishList3 = this.world.<EntityGrowingAnguishLarge>getEntitiesWithinAABB(EntityGrowingAnguishLarge.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
		for (EntityGrowingAnguishLarge ent : AnguishList3)
        {
			ent.isDead = true;
        }
		
		List<EntitySlaveMaster> SlaveMasterList = this.world.<EntitySlaveMaster>getEntitiesWithinAABB(EntitySlaveMaster.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
		for (EntitySlaveMaster ent : SlaveMasterList)
        {
			ent.isDead = true;
        }
		
		List<EntityLunacy> LunacyList = this.world.<EntityLunacy>getEntitiesWithinAABB(EntityLunacy.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
		for (EntityLunacy ent : LunacyList)
        {
			ent.isDead = true;
        }
		
		List<EntityVoidSpectralSmall> VSS = this.world.<EntityVoidSpectralSmall>getEntitiesWithinAABB(EntityVoidSpectralSmall.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
		for (EntityVoidSpectralSmall ent : VSS)
        {
			ent.isDead = true;
        }
		
		List<EntityVoidSpectralMedium> VSM = this.world.<EntityVoidSpectralMedium>getEntitiesWithinAABB(EntityVoidSpectralMedium.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
		for (EntityVoidSpectralMedium ent : VSM)
        {
			ent.isDead = true;
        }
		
		List<EntityVoidSpectralLarge> VSL = this.world.<EntityVoidSpectralLarge>getEntitiesWithinAABB(EntityVoidSpectralLarge.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE).offset(0, -5, 0));
		for (EntityVoidSpectralLarge ent : VSL)
        {
			ent.isDead = true;
        }
    }
    
    public void spawnVoidExplosion(double x, double y, double z)
	{
		List pos = new ArrayList();
		pos.add(x);
		pos.add(y);
		pos.add(z);
		this.explosionPoints.add(pos);
		this.explosionPointTimer = _ConfigEntityVoidEntity.EXPLOSION_TIMER;
	}
    
    public void sayChatLine(int message)
	{
    	List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE));
    	
    	if(!list.isEmpty())
    	{
	    	for (EntityPlayer player : list)
	        {
	    		switch(message)
	    		{
	    		case 0:
	    			player.sendMessage(new TextComponentTranslation("message.em.voidentity.chat1"));
	    			this.world.playSound((EntityPlayer)null, new BlockPos(this.posX, this.posY, this.posZ), EMSoundHandler.VO_VOIDENTITY_1, SoundCategory.HOSTILE, 3.5F, 1.0f);
	    			break;
	    		case 1:
	    			player.sendMessage(new TextComponentTranslation("message.em.voidentity.chat2"));
	    			this.world.playSound((EntityPlayer)null, new BlockPos(this.posX, this.posY, this.posZ), EMSoundHandler.VO_VOIDENTITY_2, SoundCategory.HOSTILE, 3.5F, 1.0f);
	    			break;
	    		case 2:
	    			player.sendMessage(new TextComponentTranslation("message.em.voidentity.chat3"));
	    			this.world.playSound((EntityPlayer)null, new BlockPos(this.posX, this.posY, this.posZ), EMSoundHandler.VO_VOIDENTITY_3, SoundCategory.HOSTILE, 3.5F, 1.0f);
	    			break;
	    		case 3:
	    			player.sendMessage(new TextComponentTranslation("message.em.voidentity.chat4"));
	    			this.world.playSound((EntityPlayer)null, new BlockPos(this.posX, this.posY, this.posZ), EMSoundHandler.VO_VOIDENTITY_6, SoundCategory.HOSTILE, 3.5F, 1.0f);
	    			break;
	    		case 4:
	    			player.sendMessage(new TextComponentTranslation("message.em.voidentity.chat5"));
	    			this.world.playSound((EntityPlayer)null, new BlockPos(this.posX, this.posY, this.posZ), EMSoundHandler.VO_VOIDENTITY_8, SoundCategory.HOSTILE, 3.5F, 1.0f);
	    			break;
	    		case 5:
	    			player.sendMessage(new TextComponentTranslation("message.em.voidentity.chat6"));
	    			this.world.playSound((EntityPlayer)null, new BlockPos(this.posX, this.posY, this.posZ), EMSoundHandler.VO_VOIDENTITY_10, SoundCategory.HOSTILE, 3.5F, 1.0f);
	    			break;
	    		case 6:
	    			player.sendMessage(new TextComponentTranslation("message.em.voidentity.chat7"));
	    			this.world.playSound((EntityPlayer)null, new BlockPos(this.posX, this.posY, this.posZ), EMSoundHandler.VO_VOIDENTITY_5, SoundCategory.HOSTILE, 3.5F, 1.0f);
	    			break;
	    		case 7:
	    			player.sendMessage(new TextComponentTranslation("message.em.voidentity.chat8"));
	    			this.world.playSound((EntityPlayer)null, new BlockPos(this.posX, this.posY, this.posZ), EMSoundHandler.VO_VOIDENTITY_11, SoundCategory.HOSTILE, 3.5F, 1.0f);
	    			break;
	    		case 8:
	    			player.sendMessage(new TextComponentTranslation("message.em.voidentity.chat9"));
	    			this.world.playSound((EntityPlayer)null, new BlockPos(this.posX, this.posY, this.posZ), EMSoundHandler.VO_VOIDENTITY_13, SoundCategory.HOSTILE, 3.5F, 1.0f);
	    			break;
	    		case 9:
	    			player.sendMessage(new TextComponentTranslation("message.em.voidentity.chat10"));
	    			this.world.playSound((EntityPlayer)null, new BlockPos(this.posX, this.posY, this.posZ), EMSoundHandler.VO_VOIDENTITY_14, SoundCategory.HOSTILE, 3.5F, 1.0f);
	    			break;
	    		case 10:
	    			player.sendMessage(new TextComponentTranslation("message.em.voidentity.chat11"));
	    			this.world.playSound((EntityPlayer)null, new BlockPos(this.posX, this.posY, this.posZ), EMSoundHandler.VO_VOIDENTITY_15, SoundCategory.HOSTILE, 3.5F, 1.0f);
	    			break;
	    		}
	        }
    	}
	}
    
    public void playVoidMagicCastSound(int x, int y, int z)
    {
    	Random rand = new Random();
    	int r = rand.nextInt(3);
    	switch(r)
    	{
    	case 0:
    		this.world.playSound((EntityPlayer)null, new BlockPos(x, y, z), EMSoundHandler.VOIDMAGIC_CAST_01, SoundCategory.HOSTILE, 3.5F, 1.0f);
    		break;
    	case 1:
    		this.world.playSound((EntityPlayer)null, new BlockPos(x, y, z), EMSoundHandler.VOIDMAGIC_CAST_02, SoundCategory.HOSTILE, 3.5F, 1.0f);
    		break;
    	case 2:
    		this.world.playSound((EntityPlayer)null, new BlockPos(x, y, z), EMSoundHandler.VOIDMAGIC_CAST_03, SoundCategory.HOSTILE, 3.5F, 1.0f);
    		break;
    	}
    }
    
    public void playVoidMagicExplosionSound(int x, int y, int z)
    {
    	Random rand = new Random();
    	int r = rand.nextInt(3);
    	switch(r)
    	{
    	case 0:
    		this.world.playSound((EntityPlayer)null, new BlockPos(x, y, z), EMSoundHandler.VOIDMAGIC_EXPLOSION_01, SoundCategory.HOSTILE, 3.5F, 1.0f);
    		break;
    	case 1:
    		this.world.playSound((EntityPlayer)null, new BlockPos(x, y, z), EMSoundHandler.VOIDMAGIC_EXPLOSION_02, SoundCategory.HOSTILE, 3.5F, 1.0f);
    		break;
    	case 2:
    		this.world.playSound((EntityPlayer)null, new BlockPos(x, y, z), EMSoundHandler.VOIDMAGIC_EXPLOSION_03, SoundCategory.HOSTILE, 3.5F, 1.0f);
    		break;
    	}
    }
    
    public void playImpactSound(int x, int y, int z, float volume)
    {
    	Random rand = new Random();
    	int r = rand.nextInt(3);
    	switch(r)
    	{
    	case 0:
    		this.world.playSound((EntityPlayer)null, new BlockPos(x, y, z), EMSoundHandler.SPELL_IMPACT_01, SoundCategory.HOSTILE, volume, 1.0f);
    		break;
    	case 1:
    		this.world.playSound((EntityPlayer)null, new BlockPos(x, y, z), EMSoundHandler.SPELL_IMPACT_02, SoundCategory.HOSTILE, volume, 1.0f);
    		break;
    	case 2:
    		this.world.playSound((EntityPlayer)null, new BlockPos(x, y, z), EMSoundHandler.SPELL_IMPACT_03, SoundCategory.HOSTILE, volume, 1.0f);
    		break;
    	}
    }
    
    public void closeDoor(int x, int y, int z)
    {
    	BlockPos doorpos = new BlockPos(x, y, z);
    	this.world.setBlockState(doorpos.add(1, 3, 0), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 3, 3), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 2, 0), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 2, 3), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 1, 0), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 1, 3), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 0, 0), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 0, 3), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, -1, 0), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, -1, 3), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 4, 1), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 4, 2), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 3, 1), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 3, 2), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 2, 1), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 2, 2), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 1, 1), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 1, 2), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 0, 1), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 0, 2), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, -1, 1), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, -1, 2), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 4, 1), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 4, 2), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    }
    
    public void openDoorDeath(int x, int y, int z)
    {
    	BlockPos doorpos = new BlockPos(x, y, z);
    	this.world.setBlockToAir(doorpos.add(1, 2, 0));
    	this.world.setBlockToAir(doorpos.add(1, 2, 3));
    	this.world.setBlockToAir(doorpos.add(1, 1, 0));
    	this.world.setBlockToAir(doorpos.add(1, 1, 3));
    	this.world.setBlockToAir(doorpos.add(1, 0, 0));
    	this.world.setBlockToAir(doorpos.add(1, 0, 3));
    	this.world.setBlockToAir(doorpos.add(1, -1, 0));
    	this.world.setBlockToAir(doorpos.add(1, -1, 3));
    	this.world.setBlockToAir(doorpos.add(0, 3, 1));
    	this.world.setBlockToAir(doorpos.add(0, 3, 2));
    	this.world.setBlockToAir(doorpos.add(0, 2, 1));
    	this.world.setBlockToAir(doorpos.add(0, 2, 2));
    	this.world.setBlockToAir(doorpos.add(0, 1, 1));
    	this.world.setBlockToAir(doorpos.add(0, 1, 2));
    	this.world.setBlockToAir(doorpos.add(0, 0, 1));
    	this.world.setBlockToAir(doorpos.add(0, 0, 2));
    	this.world.setBlockToAir(doorpos.add(0, -1, 1));
    	this.world.setBlockToAir(doorpos.add(0, -1, 2));
    	this.world.setBlockToAir(doorpos.add(1, 3, 1));
    	this.world.setBlockToAir(doorpos.add(1, 3, 2));
    }
    
    public float calculateHealthReduction(float amount)
    {
    	return amount * (1000 / (_ConfigEntityCarapace.BASE_HP + (_ConfigEntityCarapace.HP_SCALE_AMOUNT * (this.getNumOfPlayers() - 1))));
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
    
    public int getNumOfPlayers()
    {
        return ((Integer)this.dataManager.get(NUM_OF_PLAYERS)).intValue();
    }
    
    public void setNumOfPlayers(int state)
    {
        this.dataManager.set(NUM_OF_PLAYERS, Integer.valueOf(state));
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
