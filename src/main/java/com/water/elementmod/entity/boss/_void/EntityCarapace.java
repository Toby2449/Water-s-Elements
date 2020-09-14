package com.water.elementmod.entity.boss._void;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.water.elementmod.EMCoreBlocks;
import com.water.elementmod.entity.EntityBossMob;
import com.water.elementmod.entity.ai.EntityAIMoveTo;
import com.water.elementmod.network.PacketCarapaceBeam;
import com.water.elementmod.network.PacketCarapaceBeamGrow;
import com.water.elementmod.network.PacketCarapaceBlueBuffExplosion;
import com.water.elementmod.network.PacketCarapaceParticleCircle;
import com.water.elementmod.network.PacketCarapaceParticleRing;
import com.water.elementmod.network.PacketCarapacePortalParticles;
import com.water.elementmod.network.PacketCarapacePurpleBuffArea1;
import com.water.elementmod.network.PacketCarapacePurpleBuffArea2;
import com.water.elementmod.network.PacketCarapacePurpleBuffArea3;
import com.water.elementmod.network.PacketCarapacePurpleBuffExplode1;
import com.water.elementmod.network.PacketCarapacePurpleBuffExplode2;
import com.water.elementmod.network.PacketCarapacePurpleBuffExplode3;
import com.water.elementmod.network.PacketCarapaceRingExplosion;
import com.water.elementmod.network.PacketCarapaceSightAttack;
import com.water.elementmod.network.PacketCarapaceSightExplode;
import com.water.elementmod.network.PacketHandler;
import com.water.elementmod.network.PacketPlayMusic;
import com.water.elementmod.network.PacketStopMusic;
import com.water.elementmod.particle.EnumCustomParticleTypes;
import com.water.elementmod.particle.ParticleSpawner;
import com.water.elementmod.util.handlers.EMSoundHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public class EntityCarapace extends EntityBossMob
{
	private static final DataParameter<Boolean> INITIATE = EntityDataManager.<Boolean>createKey(EntityCarapace.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_INVURNERABLE = EntityDataManager.<Boolean>createKey(EntityCarapace.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> PHASE = EntityDataManager.<Integer>createKey(EntityCarapace.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> PRE_PHASE = EntityDataManager.<Integer>createKey(EntityCarapace.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> BLUE_BUFF = EntityDataManager.<Boolean>createKey(EntityCarapace.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> PURPLE_BUFF = EntityDataManager.<Boolean>createKey(EntityCarapace.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> NUM_OF_PLAYERS = EntityDataManager.<Integer>createKey(EntityCarapace.class, DataSerializers.VARINT);
	private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.PINK, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);
	private EntityAIMoveTo aiMoveToMainArea = null;
	private EntityAIMoveTo aiMoveToRoom1Mid = null;
	private EntityAIMoveTo aiMoveBackToStartRoom1 = null;
	private EntityAIMoveTo aiMoveToSpawnLocation = null;
	private EntityAIMoveTo aiMoveToSecondArea = null;
	private EntityAIMoveTo aiMoveToSecondAreaDoor = null;
	private EntityAIMoveTo aiMoveToThroughSecondDoor = null;
	private EntityAIMoveTo aiMoveToThirdRoom = null;
	private EntityAIWatchClosest aiWatchClosest = new EntityAIWatchClosest(this, EntityPlayer.class, 20.0F);
	private final EntityAIAttackMelee aiMeleeAttack = new EntityAIAttackMelee(this, 0.5D, true);
	private BlockPos spawn_location;
	private static List explosionPoints = new ArrayList();
	private int explosionPointTimer = _ConfigEntityCarapace.EXPLOSION_TIMER;
	private static List explosionPointPlayers = new ArrayList();
	private int musicDuration = _ConfigEntityCarapace.MUSIC_DURATION;
	private boolean musicPlaying = false;
	
	private int AttackSoundDelay = _ConfigEntityCarapace.ATTACK_SOUND_DELAY;
	
	private int prefighttimer1 = _ConfigEntityCarapace.PREFIGHTTIMER1;
	private int prefightdooranimdelay = _ConfigEntityCarapace.PREFIGHTDOORANIMDELAY;
	private int doorPhase = 0;
	
	private int P1explosionCD = _ConfigEntityCarapace.P1EXPLOSIONTIMER;
	private int P1TTimer = _ConfigEntityCarapace.P1TIMER;
	private int P2explosionCD = _ConfigEntityCarapace.P2EXPLOSIONTIMER;
	private int P2Timer = _ConfigEntityCarapace.P2TIMER;
	private int P2RunToMid = _ConfigEntityCarapace.RUNTOMIDTIMER;
	private boolean P2OrbsSpawned = false;
	private int P3explosionCD = _ConfigEntityCarapace.P3EXPLOSIONTIMER;
	private int P3Timer = _ConfigEntityCarapace.P3TIMER;
	private boolean BlueMinionSpawned = false;
	private int BlueBuffCD = _ConfigEntityCarapace.BLUEBUFFCD;
	private int BlueBuffSoakTimer = _ConfigEntityCarapace.BLUEBUFFSOAKTIMER;
	private BlockPos BlueBuffSoakLocation = null;
	private boolean BlueBuffPosFound = false;
	private boolean PurpleMinionSpawned = false;
	private int PurpleBuffCD = _ConfigEntityCarapace.PURPLEBUFFCD;
	private int PurpleBuffTimer = _ConfigEntityCarapace.PURPLEBUFFTIMER;
	private int PurpleBuffPosition = 0;
	private boolean PurpleBuffDoublePosition = false;
	private int PurpleBuffPosition2 = 0;
	private boolean PurpleBuffPosFound = false;
	
	private int P5DoorAnimDelay = _ConfigEntityCarapace.P5DOORANIMDELAY;
	private int P5Timer = _ConfigEntityCarapace.P5TIMER;
	private int P6WeaknessTimer = _ConfigEntityCarapace.P6WEAKNESSTIMER;
	private int P6WeaknessCastTimer = _ConfigEntityCarapace.P6WEAKNESSCASTTIME;
	public boolean WeaknessCasting = false;
	private int P6explosionCD = _ConfigEntityCarapace.P6EXPLOSIONTIMER;
	private int P7DoorAnimDelay = _ConfigEntityCarapace.P7DOORANIMDELAY;
	private int P7WeaknessTimer = _ConfigEntityCarapace.P7WEAKNESSTIMER;
	private int P7WeaknessCastTimer = _ConfigEntityCarapace.P7WEAKNESSCASTTIME;
	private int P7explosionCD = _ConfigEntityCarapace.P7EXPLOSIONTIMER;
	private int P7Timer = _ConfigEntityCarapace.P7TIMER;
	private int P8CastTime = _ConfigEntityCarapace.P8CASTTIME;
	private boolean P8FacingFound = false;
	private int P8Facing = 0;
	private EntityPlayer P8FacingPlayer = null;
	private boolean P8PlayerFound = false;
	private boolean P9Teleported = false;
	private int P9DoorAnimDelay = _ConfigEntityCarapace.P9DOORANIMDELAY;
	private int P9Timer = _ConfigEntityCarapace.P9TIMER;
	private int P10DoorAnimDelay = _ConfigEntityCarapace.P10DOORANIMDELAY;
	private int P10BigGroundAOE = _ConfigEntityCarapace.P10BIGGROUNDAOE;
	private int P10ExplosionTimer = _ConfigEntityCarapace.P10EXPLOSIONTIMER;
	private int PurpleBeamCD = _ConfigEntityCarapace.PURPLEBEAMCD;
	private int PurpleBeamTimer = _ConfigEntityCarapace.PURPLEBEAMTIMER;
	private static List PurpleBeamLocations = new ArrayList();
	private boolean PurpleBeamPosFound = false;
	
	public EntityCarapace(World worldIn) 
	{
		super(worldIn);
		this.setSpawnLocation(this.getPosition());
		this.setSize(1.6F, 4.5F);
	}
	
	public EntityCarapace(World worldIn, BlockPos pos) 
	{
		super(worldIn);
		this.setSpawnLocation(pos);
		this.setSize(1.6F, 4.5F);
	}
	
	public static void registerFixesBoss(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntityCarapace.class);
    }
	
	@Override
	protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
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
        this.dataManager.register(PRE_PHASE, Integer.valueOf(0));
        this.dataManager.register(BLUE_BUFF, Boolean.valueOf(false));
        this.dataManager.register(PURPLE_BUFF, Boolean.valueOf(false));
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
        compound.setInteger("PrePhase", this.getPrePhase());
        compound.setBoolean("BlueBuff", this.getBlueBuffActive());
        compound.setBoolean("PurpleBuff", this.getPurpleBuffActive());
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
        this.setPrePhase(compound.getInteger("PrePhase"));
        this.setBlueBuff(compound.getBoolean("BlueBuff"));
        this.setPurpleBuff(compound.getBoolean("PurpleBuff"));
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
					
					if(this.explosionPoints.get(i) != null && this.explosionPointPlayers.get(i) != null)
					{
						this.explosionPointTimer--;
						if(this.explosionPointTimer > 0)
						{
							PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceParticleRing(this, this.world, 9, (double)pos.get(0), (double)pos.get(1), (double)pos.get(2), 0.0D, 0.0D,0.0D, 2), this.dimension);
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
						        	ent.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 150, 1));
						        	PacketHandler.INSTANCE.sendTo(new PacketCarapaceParticleRing(ent, this.world, 9, (double)pos.get(0), (double)pos.get(1), (double)pos.get(2), 0.0D, 0.0D,0.0D, 3), (EntityPlayerMP) ent);
						        	ent.attackEntityFrom(DamageSource.MAGIC, 20.0F);
						        }
						    }
							
							this.explosionPoints.clear();
							this.explosionPointTimer = _ConfigEntityCarapace.EXPLOSION_TIMER;
							this.explosionPointPlayers.clear();
						}
					}
				}
    		}
    		
    		// If nobodies in the arena
	        if(this.getPrePhase() > 0 || this.getPhase() > 0)
	        {
	        	List<EntityPlayer> players = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
		        
	        	if(players.size() <= 0)
	        	{
	        		this.resetFight();
	        	}
	        }
	        
	        if(this.getPhase() == 0) //HP Scale
	        {
	        	int numOfPlayers = 0;
	        	List<EntityPlayer> players = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
		        if(!players.isEmpty())
		        {
		        	for (EntityPlayer entity : players)
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
		        	this.playBossMusic();
		        }
	        }
    	}
    	
    	switch(this.getPrePhase())
    	{
    	
		/**
		 * -------------------------------------------------
		 * PRE PHASE 0 
		 * -------------------------------------------------
		**/	 
    	case 0:
    		if(!this.world.isRemote)
    		{
    			this.setInvulState(true);
	    		boolean startPreFightAnim = false;
	    		List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.FIGHT_INIT_RADIUS, _ConfigEntityCarapace.FIGHT_INIT_RADIUS, _ConfigEntityCarapace.FIGHT_INIT_RADIUS));
		        for (EntityPlayer entity : list)
		        {
		        	startPreFightAnim = true;
		        }
		        
		        if(startPreFightAnim) 
		        {
		        	this.setPrePhase(1);
		        }
    		}
    		break;
    		
    		
    		
    	/**
    	 * -------------------------------------------------
    	 * PRE PHASE 1 
    	 * -------------------------------------------------
    	**/
    	case 1:
    		if(!this.world.isRemote)
    		{
    			switch(this.doorPhase)
    			{
    			case 0:
    				this.openDoor1(this.getSpawnLocation().getX() - 8, this.getSpawnLocation().getY() + 3, this.getSpawnLocation().getZ() - 1);
    				this.prefightdooranimdelay--;
    				if(this.prefightdooranimdelay <= 0)
    				{
    					this.prefightdooranimdelay = _ConfigEntityCarapace.PREFIGHTDOORANIMDELAY;
    					this.doorPhase = 1;
    				}
    				break;
    			case 1:
    				this.openDoor2(this.getSpawnLocation().getX() - 8, this.getSpawnLocation().getY() + 3, this.getSpawnLocation().getZ() - 1);
    				this.prefightdooranimdelay--;
    				if(this.prefightdooranimdelay <= 0)
    				{
    					this.prefightdooranimdelay = _ConfigEntityCarapace.PREFIGHTDOORANIMDELAY;
    					this.doorPhase = 2;
    				}
    				break;
    			case 2:
    				this.openDoor3(this.getSpawnLocation().getX() - 8, this.getSpawnLocation().getY() + 3, this.getSpawnLocation().getZ() - 1);
    				this.doorPhase = -1;
    				break;
    			}
    			
    			this.prefighttimer1--;
	    		if(this.prefighttimer1 <= 0) this.setPrePhase(2);
    		}
    		break;
    		
    		
    		
    	/**
    	 * -------------------------------------------------
    	 * PRE PHASE 2 
    	 * -------------------------------------------------
    	**/
    	case 2:
    		if(!this.world.isRemote)
    		{
	    		this.setInvulState(false);
	    		if(this.getHealth() != this.getMaxHealth())
	    		{
	    			this.playBossMusic();
	    			this.musicPlaying = true;
	    			this.setPrePhase(-1);
	    			this.setPhase(1);
	    		}
    		}
    		break;
    	}
    	
    	switch(this.getPhase())
    	{
    	
    	/**
		 * =================================================
		 * PHASE 1 
		 * =================================================
		**/	 
    	case 1:
    		this.AttackSoundDelay--;
			if(this.AttackSoundDelay <= 0)
			{
				this.AttackSoundDelay = _ConfigEntityCarapace.ATTACK_SOUND_DELAY;
				this.playAttackSound();
			}
			
    		if(!this.world.isRemote)
    		{
	    		this.setInvulState(false);
	    		this.P1TTimer--;
	    		if(this.P1TTimer <= 0) 
	    		{
	    			this.P1TTimer = _ConfigEntityCarapace.P1TIMER;
	    			this.P1explosionCD = _ConfigEntityCarapace.P1EXPLOSIONTIMER;
	    			this.setPhase(2);
	    		}
	    		
	    		this.P1explosionCD--;
	    		List<EntityPlayer> P1players = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
				for (EntityPlayer player : P1players)
		        {
		    		if(this.P1explosionCD <= 0)
		    		{
		    			this.P1explosionCD = _ConfigEntityCarapace.P1EXPLOSIONTIMER;
		    			this.spawnVoidExplosion(player, player.posX, player.posY, player.posZ);
		    		}
		    		
		    		for(int i = 0; i < this.explosionPointPlayers.size(); i++)
					{
		    			EntityPlayer activePlayer = (EntityPlayer)this.explosionPointPlayers.get(i);
						if(player == activePlayer)
						{
							List pos = new ArrayList();
							pos.add(player.posX);
							pos.add(player.posY);
							pos.add(player.posZ);
							this.explosionPoints.set(i, pos);
						}
					}
		        }
	    		
	    		if(this.getHealth() <= (this.getMaxHealth() / 10) * 6) this.setPhase(4);
    		}
    		break;
    		
    		
    		
    	/**
    	 * =================================================
    	 * PHASE 2 
    	 * =================================================
    	**/	 
    	case 2:
    		this.AttackSoundDelay--;
			if(this.AttackSoundDelay <= 0)
			{
				this.AttackSoundDelay = _ConfigEntityCarapace.ATTACK_SOUND_DELAY;
				this.playAttackSound();
			}
			
    		if(!this.world.isRemote)
    		{
    			this.setInvulState(false);
    			this.P2RunToMid--;
    			this.P2Timer--;
    			if(this.P2RunToMid <= 0 && !this.P2OrbsSpawned)
    			{
    				this.P2OrbsSpawned = true;
    				EntityBlueOrb blue = new EntityBlueOrb(this.world, this.posX, this.posY, this.posZ);
    				blue.enablePersistence();
    				blue.setPosition(this.posX, this.getPosition().getY(), this.posZ + 12.0D);
		            this.world.spawnEntity(blue);
		            
		            EntityPurpleOrb purple = new EntityPurpleOrb(this.world, this.posX, this.posY, this.posZ);
		            purple.enablePersistence();
		            purple.setPosition(this.posX, this.getPosition().getY(), this.posZ - 12.0D);
		            this.world.spawnEntity(purple);
    			}
		        
		        if(this.getBlueBuffActive() || this.getPurpleBuffActive())
		        {
		        	this.P2Timer = _ConfigEntityCarapace.P2TIMER;
		        	this.P2RunToMid = _ConfigEntityCarapace.RUNTOMIDTIMER;
		        	this.P2explosionCD = _ConfigEntityCarapace.P2EXPLOSIONTIMER;
		        	this.P2OrbsSpawned = false;
		        	this.setPhase(3);
		        }
		        
		        if(this.P2RunToMid <= 0)
    			{
    				PacketHandler.INSTANCE.sendToDimension(new PacketCarapacePortalParticles(this, this.world, 6, this.posX, this.posY, this.posZ - 12, 0.0D, 0.0D,0.0D, 3), this.dimension);
    				PacketHandler.INSTANCE.sendToDimension(new PacketCarapacePortalParticles(this, this.world, 8, this.posX, this.posY, this.posZ + 12, 0.0D, 0.0D,0.0D, 3), this.dimension);
    			}
		        
		        if(this.P2Timer <= 0 && (!this.getBlueBuffActive() || !this.getPurpleBuffActive())) // Fail safe
		        {
		        	this.P2Timer = _ConfigEntityCarapace.P2TIMER;
		        	this.P2RunToMid = _ConfigEntityCarapace.RUNTOMIDTIMER;
		        	this.P2explosionCD = _ConfigEntityCarapace.P2EXPLOSIONTIMER;
		        	this.P2OrbsSpawned = false;
		        	this.setPhase(1);
		       	}
		        
		        if(this.getHealth() <= (this.getMaxHealth() / 10) * 6) 
		        {
		        	List<EntityBlueOrb> BO = this.world.<EntityBlueOrb>getEntitiesWithinAABB(EntityBlueOrb.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
					for (EntityBlueOrb ent : BO)
			        {
						ent.setDead();
			        }
					
					List<EntityPurpleOrb> PO = this.world.<EntityPurpleOrb>getEntitiesWithinAABB(EntityPurpleOrb.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
					for (EntityPurpleOrb ent : PO)
			        {
						ent.setDead();
			        }
					
					List<EntityBlueGuardian> BG = this.world.<EntityBlueGuardian>getEntitiesWithinAABB(EntityBlueGuardian.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
					for (EntityBlueGuardian ent : BG)
			        {
						ent.setDead();
			        }
					
					List<EntityPurpleGuardian> PG = this.world.<EntityPurpleGuardian>getEntitiesWithinAABB(EntityPurpleGuardian.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
					for (EntityPurpleGuardian ent : PG)
			        {
						ent.setDead();
			        }
					
		        	this.setPhase(4);
		        }
		        
		        this.P2explosionCD--;
	    		List<EntityPlayer> P2players = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
				for (EntityPlayer player : P2players)
		        {
		    		if(this.P2explosionCD <= 0)
		    		{
		    			this.P2explosionCD = _ConfigEntityCarapace.P2EXPLOSIONTIMER;
		    			this.spawnVoidExplosion(player, player.posX, player.posY, player.posZ);
		    		}
		    		
		    		for(int i = 0; i < this.explosionPointPlayers.size(); i++)
					{
		    			EntityPlayer activePlayer = (EntityPlayer)this.explosionPointPlayers.get(i);
						if(player == activePlayer)
						{
							List pos = new ArrayList();
							pos.add(player.posX);
							pos.add(player.posY);
							pos.add(player.posZ);
							this.explosionPoints.set(i, pos);
						}
					}
		        }
    		}
    		break;
    		
    		
    		
    	/**
         * =================================================
         * PHASE 3 
         * =================================================
        **/
    	case 3:
    		this.AttackSoundDelay--;
			if(this.AttackSoundDelay <= 0)
			{
				this.AttackSoundDelay = _ConfigEntityCarapace.ATTACK_SOUND_DELAY;
				this.playAttackSound();
			}
			
    		if(!this.world.isRemote)
    		{
    			this.setInvulState(false);
    			this.P3Timer--;
    			if(this.getBlueBuffActive())
    			{
    				this.BlueBuffCD--;
    				
    				if(!this.BlueMinionSpawned)
    				{
    					this.BlueMinionSpawned = true;
    					EntityBlueGuardian entity = new EntityBlueGuardian(this.world);
    			       	entity.setPosition(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ() + 12D);
    			       	entity.enablePersistence();
    			   		this.world.spawnEntity(entity);
    				}
    				
    				if(this.BlueBuffCD <= 0)
    				{
    					this.BlueBuffSoakTimer--;
    					
    					if(!this.BlueBuffPosFound)
    					{
    						this.BlueBuffPosFound = true;
	    					int randX = (this.getPosition().getX() - 8) + this.rand.nextInt(16);
	    					int randZ = (this.getPosition().getZ() - 11) + this.rand.nextInt(22);
	    					this.BlueBuffSoakLocation = new BlockPos(randX, this.posY, randZ);
    					}
    					
    					if(this.BlueBuffSoakLocation != null)
    					{
    						boolean playerInAOE = false;
    						AxisAlignedBB AoePoint = new AxisAlignedBB(this.BlueBuffSoakLocation.getX() - 2.6D, this.BlueBuffSoakLocation.getY() - 0.5D, this.BlueBuffSoakLocation.getZ() - 2.6D, this.BlueBuffSoakLocation.getX() + 2.6D, this.BlueBuffSoakLocation.getY() + 7.5D, this.BlueBuffSoakLocation.getZ() + 2.6D);
							List<EntityPlayer> AABBPlayer = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint);
							if (!AABBPlayer.isEmpty())
						    {
						        for (EntityPlayer ent : AABBPlayer)
						        {
						        	playerInAOE = true;
						        }
						    }
							
    						if(this.BlueBuffSoakTimer % 3 == 1) 
    						{
    							PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceParticleCircle(this, this.world, 8, (double)this.BlueBuffSoakLocation.getX(), (double)this.BlueBuffSoakLocation.getY(), (double)this.BlueBuffSoakLocation.getZ(), 0.0D, 0.0D,0.0D, 3), this.dimension);
    							if(!playerInAOE)
    							{
    								PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceBlueBuffExplosion(this, this.world, 8, (double)this.BlueBuffSoakLocation.getX(), (double)this.BlueBuffSoakLocation.getY(), (double)this.BlueBuffSoakLocation.getZ(), 0.0D, 0.0D,0.0D, 1, 7), this.dimension);
            					}
    						}
    					}
    					
    					if(this.BlueBuffSoakTimer <= 0)
    					{
    						boolean playerInAOE = false;
    						this.playImpactSound(this.BlueBuffSoakLocation.getX(), this.BlueBuffSoakLocation.getY(), this.BlueBuffSoakLocation.getZ(), 3.5F);
    						AxisAlignedBB AoePoint = new AxisAlignedBB(this.BlueBuffSoakLocation.getX() - 2.6D, this.BlueBuffSoakLocation.getY() - 0.5D, this.BlueBuffSoakLocation.getZ() - 2.6D, this.BlueBuffSoakLocation.getX() + 2.6D, this.BlueBuffSoakLocation.getY() + 7.5D, this.BlueBuffSoakLocation.getZ() + 2.6D);
							List<EntityPlayer> AABBPlayer = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint);
							if (!AABBPlayer.isEmpty())
						    {
						        for (EntityPlayer ent : AABBPlayer)
						        {
						        	ent.attackEntityFrom(DamageSource.causeMobDamage(this), 35.0f);
						        	playerInAOE = true;
						        }
						    }
							if(playerInAOE)
							{
								PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceBlueBuffExplosion(this, this.world, 8, (double)this.BlueBuffSoakLocation.getX(), (double)this.BlueBuffSoakLocation.getY(), (double)this.BlueBuffSoakLocation.getZ(), 0.0D, 0.0D,0.0D, 3, 7), this.dimension);
            				}
							else
							{
								List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
						        for (EntityPlayer entity : list)
						        {
						        	entity.attackEntityFrom(DamageSource.causeMobDamage(this), 65.0f);
						        }
						        PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceParticleCircle(this, this.world, 8, (double)this.BlueBuffSoakLocation.getX(), (double)this.BlueBuffSoakLocation.getY(), (double)this.BlueBuffSoakLocation.getZ(), 0.0D, 0.0D,0.0D, 15), this.dimension);
    						}
    						this.BlueBuffPosFound = false;
    						this.BlueBuffSoakLocation = null;
    						this.BlueBuffCD = _ConfigEntityCarapace.BLUEBUFFCD;
    						this.BlueBuffSoakTimer = _ConfigEntityCarapace.BLUEBUFFSOAKTIMER;
    					}
    				}
    			}
    			
    			if(this.getPurpleBuffActive())
    			{
    				this.PurpleBuffCD--;
    				if(this.PurpleBuffCD <= 0)
    				{
    					if(!this.PurpleMinionSpawned)
        				{
        					this.PurpleMinionSpawned = true;
        					EntityPurpleGuardian entity = new EntityPurpleGuardian(this.world);
        			       	entity.setPosition(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ() - 12D);
        			       	entity.enablePersistence();
        			   		this.world.spawnEntity(entity);
        				}
    					
    					this.PurpleBuffTimer--;
    					if(!this.PurpleBuffPosFound)
						{
    						Random random = new Random();
							this.PurpleBuffPosFound = true;
							this.PurpleBuffPosition = random.nextInt(3);
							if(random.nextInt(99) >= 50)
							{
								this.PurpleBuffDoublePosition = true;
							}
							else
							{
								this.PurpleBuffDoublePosition = false;
							}
							
							if(this.PurpleBuffDoublePosition)
							{
								int i = this.PurpleBuffPosition;
								int j = random.nextInt(3);
								while(j != i)
								{
									j = random.nextInt(3);
								}
							}
						}
    					
    					switch(this.PurpleBuffPosition)
    					{
    					case 0:
    						PacketHandler.INSTANCE.sendToDimension(new PacketCarapacePurpleBuffArea1(this, this.world, 6, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D), this.dimension);
    						break;
    					case 1:
    						PacketHandler.INSTANCE.sendToDimension(new PacketCarapacePurpleBuffArea2(this, this.world, 6, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D), this.dimension);
    						break;
    					case 2:
    						PacketHandler.INSTANCE.sendToDimension(new PacketCarapacePurpleBuffArea3(this, this.world, 6, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D), this.dimension);
    						break;
    					}
    					
    					if(this.PurpleBuffDoublePosition)
    					{
    						switch(this.PurpleBuffPosition2)
        					{
        					case 0:
        						PacketHandler.INSTANCE.sendToDimension(new PacketCarapacePurpleBuffArea1(this, this.world, 6, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D), this.dimension);
        						break;
        					case 1:
        						PacketHandler.INSTANCE.sendToDimension(new PacketCarapacePurpleBuffArea2(this, this.world, 6, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D), this.dimension);
        						break;
        					case 2:
        						PacketHandler.INSTANCE.sendToDimension(new PacketCarapacePurpleBuffArea3(this, this.world, 6, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D), this.dimension);
        						break;
        					}
    					}
    					
    					if(this.PurpleBuffTimer <= 0)
    					{
    						switch(this.PurpleBuffPosition)
	    					{
	    					case 0:
	    						PacketHandler.INSTANCE.sendToDimension(new PacketCarapacePurpleBuffExplode1(this, this.world, 6, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D), this.dimension);
	    						this.playImpactSound(this.getPosition().getX() + 12, this.getPosition().getY(), this.getPosition().getZ(), 3.5F);
	    						AxisAlignedBB AoePoint1 = new AxisAlignedBB(this.posX + 6, this.posY, this.posZ - 15, this.posX + 15, this.posY + 20, this.posZ + 30);
								List<EntityPlayer> AABBPlayer1 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint1);
								if (!AABBPlayer1.isEmpty())
							    {
							        for (EntityPlayer ent : AABBPlayer1)
							        {
							        	ent.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100));
							        	ent.attackEntityFrom(DamageSource.causeMobDamage(this), 70.0f);
							        }
							    }
	    						break;
	    					case 1:
	    						PacketHandler.INSTANCE.sendToDimension(new PacketCarapacePurpleBuffExplode2(this, this.world, 6, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D), this.dimension);
	    						this.playImpactSound(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), 3.5F);
	    						AxisAlignedBB AoePoint2 = new AxisAlignedBB(this.posX - 5, this.posY, this.posZ - 15, this.posX + 6, this.posY + 20, this.posZ + 30);
								List<EntityPlayer> AABBPlayer2 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint2);
								if (!AABBPlayer2.isEmpty())
							    {
							        for (EntityPlayer ent : AABBPlayer2)
							        {
							        	ent.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100));
							        	ent.attackEntityFrom(DamageSource.causeMobDamage(this), 70.0f);
							        }
							    }
	    						break;
	    					case 2:
	    						PacketHandler.INSTANCE.sendToDimension(new PacketCarapacePurpleBuffExplode3(this, this.world, 6, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D), this.dimension);
	    						this.playImpactSound(this.getPosition().getX() - 12, this.getPosition().getY(), this.getPosition().getZ(), 3.5F);
	    						AxisAlignedBB AoePoint3 = new AxisAlignedBB(this.posX - 5, this.posY, this.posZ - 15, this.posX - 15, this.posY + 20, this.posZ + 30);
								List<EntityPlayer> AABBPlayer3 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint3);
								if (!AABBPlayer3.isEmpty())
							    {
							        for (EntityPlayer ent : AABBPlayer3)
							        {
							        	ent.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100));
							        	ent.attackEntityFrom(DamageSource.causeMobDamage(this), 70.0f);
							        }
							    }
	    						break;
	    					}
    						
    						if(this.PurpleBuffDoublePosition)
        					{
        						switch(this.PurpleBuffPosition2)
            					{
            					case 0:
            						PacketHandler.INSTANCE.sendToDimension(new PacketCarapacePurpleBuffExplode1(this, this.world, 6, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D), this.dimension);
            						this.playImpactSound(this.getPosition().getX() + 12, this.getPosition().getY(), this.getPosition().getZ(), 3.5F);
            						AxisAlignedBB AoePoint1 = new AxisAlignedBB(this.posX + 6, this.posY, this.posZ - 15, this.posX + 15, this.posY + 20, this.posZ + 30);
    								List<EntityPlayer> AABBPlayer1 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint1);
    								if (!AABBPlayer1.isEmpty())
    							    {
    							        for (EntityPlayer ent : AABBPlayer1)
    							        {
    							        	ent.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100));
    							        	ent.attackEntityFrom(DamageSource.causeMobDamage(this), 70.0f);
    							        }
    							    }
            						break;
            					case 1:
            						PacketHandler.INSTANCE.sendToDimension(new PacketCarapacePurpleBuffExplode2(this, this.world, 6, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D), this.dimension);
            						this.playImpactSound(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), 3.5F);
            						AxisAlignedBB AoePoint2 = new AxisAlignedBB(this.posX - 6, this.posY, this.posZ - 15, this.posX + 12, this.posY + 20, this.posZ + 30);
    								List<EntityPlayer> AABBPlayer2 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint2);
    								if (!AABBPlayer2.isEmpty())
    							    {
    							        for (EntityPlayer ent : AABBPlayer2)
    							        {
    							        	ent.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100));
    							        	ent.attackEntityFrom(DamageSource.causeMobDamage(this), 70.0f);
    							        }
    							    }
            						break;
            					case 2:
            						PacketHandler.INSTANCE.sendToDimension(new PacketCarapacePurpleBuffExplode3(this, this.world, 6, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D), this.dimension);
            						this.playImpactSound(this.getPosition().getX() - 12, this.getPosition().getY(), this.getPosition().getZ(), 3.5F);
            						AxisAlignedBB AoePoint3 = new AxisAlignedBB(this.posX - 5, this.posY, this.posZ - 15, this.posX - 15, this.posY + 20, this.posZ + 30);
    								List<EntityPlayer> AABBPlayer3 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint3);
    								if (!AABBPlayer3.isEmpty())
    							    {
    							        for (EntityPlayer ent : AABBPlayer3)
    							        {
    							        	ent.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100));
    							        	ent.attackEntityFrom(DamageSource.causeMobDamage(this), 70.0f);
    							        }
    							    }
            						break;
            					}
        					}
    						
    						this.PurpleBuffCD = _ConfigEntityCarapace.PURPLEBUFFCD;
    						this.PurpleBuffTimer = _ConfigEntityCarapace.PURPLEBUFFTIMER;
    						this.PurpleBuffPosition = 0;
    						this.PurpleBuffPosFound = false;
    						this.PurpleBuffDoublePosition = false;
    						this.PurpleBuffPosition2 = 0;
    					}
    				}
    			}
    			
    			if(this.P3Timer <= 0) 
    			{
    				this.BlueMinionSpawned = false;
    				this.PurpleMinionSpawned = false;
    				this.BlueBuffPosFound = false;
					this.BlueBuffSoakLocation = null;
					this.BlueBuffCD = _ConfigEntityCarapace.BLUEBUFFCD;
					this.BlueBuffSoakTimer = _ConfigEntityCarapace.BLUEBUFFSOAKTIMER;
					
    				this.PurpleBuffCD = _ConfigEntityCarapace.PURPLEBUFFCD;
					this.PurpleBuffTimer = _ConfigEntityCarapace.PURPLEBUFFTIMER;
					this.PurpleBuffPosition = 0;
					this.PurpleBuffPosFound = false;
					this.PurpleBuffDoublePosition = false;
					this.PurpleBuffPosition2 = 0;
					
    				this.P3Timer = _ConfigEntityCarapace.P3TIMER;
    				this.setBlueBuff(false);
    				this.setPurpleBuff(false);
    				this.setPhase(1);
    			}
    			
    			if(this.getHealth() <= (this.getMaxHealth() / 10) * 6) 
    			{
    				List<EntityBlueGuardian> BG = this.world.<EntityBlueGuardian>getEntitiesWithinAABB(EntityBlueGuardian.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
					for (EntityBlueGuardian ent : BG)
			        {
						ent.setDead();
			        }
					
					List<EntityPurpleGuardian> PG = this.world.<EntityPurpleGuardian>getEntitiesWithinAABB(EntityPurpleGuardian.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
					for (EntityPurpleGuardian ent : PG)
			        {
						ent.setDead();
			        }
					
    				this.setPhase(4);
    			}
    			
    			this.P3explosionCD--;
        		List<EntityPlayer> P3players = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
    			for (EntityPlayer player : P3players)
    	        {
    	    		if(this.P3explosionCD <= 0)
    	    		{
    	    			this.P3explosionCD = _ConfigEntityCarapace.P3EXPLOSIONTIMER;
    	    			this.spawnVoidExplosion(player, player.posX, player.posY, player.posZ);
    	    		}
    	    		
    	    		for(int i = 0; i < this.explosionPointPlayers.size(); i++)
    				{
    	    			EntityPlayer activePlayer = (EntityPlayer)this.explosionPointPlayers.get(i);
    					if(player == activePlayer)
    					{
    						List pos = new ArrayList();
    						pos.add(player.posX);
    						pos.add(player.posY);
    						pos.add(player.posZ);
    						this.explosionPoints.set(i, pos);
    					}
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
    			this.setSize(1.6F, 1F);
	    		this.setInvulState(true);
	    		if(this.getPosition().getX() == 30 || this.getPosition().getX() == 31)
	    		{
	    			if(this.getPosition().getZ() == -1 || this.getPosition().getZ() == 0 || this.getPosition().getZ() == 1)
		    		{
		    			this.setPhase(5);
		    		}
	    		}
    		}
    		break;
    		
    		
    		
    	/**
         * =================================================
         * PHASE 5 
         * =================================================
        **/
    	case 5:
    		this.AttackSoundDelay--;
			if(this.AttackSoundDelay <= 0)
			{
				this.AttackSoundDelay = _ConfigEntityCarapace.ATTACK_SOUND_DELAY;
				this.playAttackSound();
			}
			
    		if(!this.world.isRemote)
    		{
    			this.setInvulState(true);
    			this.P5Timer--;
    			if(this.getPosition().getX() == 46)
    			{
    				this.doorPhase = 0;
    			}
    			
    			switch(this.doorPhase)
    			{
    			case 0:
    				this.openDoor4(this.getSpawnLocation().getX() + 2, this.getSpawnLocation().getY() + 1, this.getSpawnLocation().getZ() - 1);
    				this.P5DoorAnimDelay--;
    				if(this.P5DoorAnimDelay <= 0)
    				{
    					this.P5DoorAnimDelay = _ConfigEntityCarapace.P5DOORANIMDELAY;
    					this.doorPhase = 1;
    				}
    				break;
    			case 1:
    				this.openDoor5(this.getSpawnLocation().getX() + 2, this.getSpawnLocation().getY() + 1, this.getSpawnLocation().getZ() - 1);
    				this.P5DoorAnimDelay--;
    				if(this.P5DoorAnimDelay <= 0)
    				{
    					this.P5DoorAnimDelay = _ConfigEntityCarapace.P5DOORANIMDELAY;
    					this.doorPhase = 2;
    				}
    				break;
    			case 2:
    				this.openDoor6(this.getSpawnLocation().getX() + 2, this.getSpawnLocation().getY() + 1, this.getSpawnLocation().getZ() - 1);
    				this.doorPhase = -1;
    				break;
    			}
    			
    			if(this.P5Timer <= 0)
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
    		this.AttackSoundDelay--;
			if(this.AttackSoundDelay <= 0)
			{
				this.AttackSoundDelay = _ConfigEntityCarapace.ATTACK_SOUND_DELAY;
				this.playAttackSound();
			}
			
			if(!this.world.isRemote)
			{
				this.setInvulState(true);
				this.heal((this.getMaxHealth() / 350) / 20);
				this.P6WeaknessTimer--;
				if(this.P6WeaknessTimer <= 0)
				{
					this.P6WeaknessCastTimer--;
					if(this.P6WeaknessCastTimer <= 0)
					{
						this.P6WeaknessTimer = _ConfigEntityCarapace.P6WEAKNESSTIMER;
						this.P6WeaknessCastTimer = _ConfigEntityCarapace.P6WEAKNESSCASTTIME;
						this.playImpactSound(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), 3.5F);
						PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceParticleCircle(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, 15), this.dimension);
						List<EntityPlayer> players = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
						for (EntityPlayer player : players)
				        {
							player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 140, 0));
				        }
					}
				}
				
				int numOfEyes = 0;
				List<EntityCarapaceEye> eyes = this.world.<EntityCarapaceEye>getEntitiesWithinAABB(EntityCarapaceEye.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
				for (EntityCarapaceEye entity : eyes)
		        {
					numOfEyes++;
		        }
				
				if(numOfEyes == 0)
				{
					this.WeaknessCasting = false;
					this.doorPhase = 0;
					this.setPhase(7);
				}
				
				this.P6explosionCD--;
        		List<EntityPlayer> P6players = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
    			for (EntityPlayer player : P6players)
    	        {
    	    		if(this.P6explosionCD <= 0)
    	    		{
    	    			this.P6explosionCD = _ConfigEntityCarapace.P6EXPLOSIONTIMER;
    	    			this.spawnVoidExplosion(player, player.posX, player.posY, player.posZ);
    	    		}
    	    		
    	    		for(int i = 0; i < this.explosionPointPlayers.size(); i++)
    				{
    	    			EntityPlayer activePlayer = (EntityPlayer)this.explosionPointPlayers.get(i);
    					if(player == activePlayer)
    					{
    						List pos = new ArrayList();
    						pos.add(player.posX);
    						pos.add(player.posY);
    						pos.add(player.posZ);
    						this.explosionPoints.set(i, pos);
    					}
    				}
    	        }
			}
			if(this.world.isRemote)
			{
				this.P6WeaknessTimer--;
				if(this.P6WeaknessTimer <= 0)
				{
					this.P6WeaknessCastTimer--;
					this.WeaknessCasting = true;
					for (int i = 0; i < 100; ++i)
		            {
		            	ParticleSpawner.spawnParticle(EnumCustomParticleTypes.DARK_PURPLE_SMOKE_BLOCK, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
		            }
					if(this.P6WeaknessCastTimer <= 0)
					{
						this.P6WeaknessTimer = _ConfigEntityCarapace.P6WEAKNESSTIMER;
						this.P6WeaknessCastTimer = _ConfigEntityCarapace.P6WEAKNESSCASTTIME;
						this.WeaknessCasting = false;
					}
				}
				
				int numOfEyes = 0;
				List<EntityCarapaceEye> eyes = this.world.<EntityCarapaceEye>getEntitiesWithinAABB(EntityCarapaceEye.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
				for (EntityCarapaceEye entity : eyes)
		        {
					numOfEyes++;
		        }
				
				if(numOfEyes == 0)
				{
					this.WeaknessCasting = false;
				}
			}
    		break;
    		
    		
    		
    	/**
         * =================================================
         * PHASE 7 
         * =================================================
        **/
    	case 7:
    		this.AttackSoundDelay--;
			if(this.AttackSoundDelay <= 0)
			{
				this.AttackSoundDelay = _ConfigEntityCarapace.ATTACK_SOUND_DELAY;
				this.playAttackSound();
			}
			
    		if(!this.world.isRemote)
			{
				this.setInvulState(false);
				this.P7WeaknessTimer--;
				if(this.P7WeaknessTimer <= 0)
				{
					this.P7WeaknessCastTimer--;
					if(this.P7WeaknessCastTimer <= 0)
					{
						this.P7WeaknessTimer = _ConfigEntityCarapace.P7WEAKNESSTIMER;
						this.P7WeaknessCastTimer = _ConfigEntityCarapace.P7WEAKNESSCASTTIME;
						this.playImpactSound(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), 3.5F);
						PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceParticleCircle(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, 15), this.dimension);
						List<EntityPlayer> players = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
						for (EntityPlayer player : players)
				        {
							player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 140, 0));
							player.attackEntityFrom(DamageSource.MAGIC, 20.0f);
				        }
					}
				}
				
				this.P7Timer--;
				if(this.P7Timer <= 0)
				{
					this.P7Timer = _ConfigEntityCarapace.P7TIMER;
					this.P7WeaknessTimer = _ConfigEntityCarapace.P7WEAKNESSTIMER;
					this.P7WeaknessCastTimer = _ConfigEntityCarapace.P7WEAKNESSCASTTIME;
					this.setPhase(8);
				}
				
				switch(this.doorPhase)
    			{
    			case 0:
    				this.closeDoor2(this.getSpawnLocation().getX() + 1, this.getSpawnLocation().getY() + 1, this.getSpawnLocation().getZ() - 1);
    				this.P7DoorAnimDelay--;
    				if(this.P7DoorAnimDelay <= 0)
    				{
    					this.P7DoorAnimDelay = _ConfigEntityCarapace.P7DOORANIMDELAY;
    					this.doorPhase = 1;
    				}
    				break;
    			case 1:
    				this.closeDoor3(this.getSpawnLocation().getX() + 1, this.getSpawnLocation().getY() + 1, this.getSpawnLocation().getZ() - 1);
    				this.P7DoorAnimDelay--;
    				if(this.P7DoorAnimDelay <= 0)
    				{
    					this.P7DoorAnimDelay = _ConfigEntityCarapace.P7DOORANIMDELAY;
    					this.doorPhase = 2;
    				}
    				break;
    			case 2:
    				this.closeDoor4(this.getSpawnLocation().getX() + 1, this.getSpawnLocation().getY() + 1, this.getSpawnLocation().getZ() - 1);
    				this.doorPhase = -1;
    				break;
    			}
				
				if(this.getHealth() <= (this.getMaxHealth() / 10) * 4) 
    			{
    				this.doorPhase = 0;
    				this.setPhase(9);
    			}
				
				this.P7explosionCD--;
        		List<EntityPlayer> P7players = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
    			for (EntityPlayer player : P7players)
    	        {
    	    		if(this.P7explosionCD <= 0)
    	    		{
    	    			this.P7explosionCD = _ConfigEntityCarapace.P7EXPLOSIONTIMER;
    	    			this.spawnVoidExplosion(player, player.posX, player.posY, player.posZ);
    	    		}
    	    		
    	    		for(int i = 0; i < this.explosionPointPlayers.size(); i++)
    				{
    	    			EntityPlayer activePlayer = (EntityPlayer)this.explosionPointPlayers.get(i);
    					if(player == activePlayer)
    					{
    						List pos = new ArrayList();
    						pos.add(player.posX);
    						pos.add(player.posY);
    						pos.add(player.posZ);
    						this.explosionPoints.set(i, pos);
    					}
    				}
    	        }
			}
			if(this.world.isRemote)
			{
				this.P7WeaknessTimer--;
				if(this.P7WeaknessTimer <= 0)
				{
					this.P7WeaknessCastTimer--;
					this.WeaknessCasting = true;
					for (int i = 0; i < 100; ++i)
		            {
		            	ParticleSpawner.spawnParticle(EnumCustomParticleTypes.DARK_PURPLE_SMOKE_BLOCK, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
		            }
					if(this.P7WeaknessCastTimer <= 0)
					{
						this.P7WeaknessTimer = _ConfigEntityCarapace.P7WEAKNESSTIMER;
						this.P7WeaknessCastTimer = _ConfigEntityCarapace.P7WEAKNESSCASTTIME;
						this.WeaknessCasting = false;
					}
				}
				
				this.P7Timer--;
				if(this.P7Timer <= 0)
				{
					this.P7Timer = _ConfigEntityCarapace.P7TIMER;
					this.P7WeaknessTimer = _ConfigEntityCarapace.P7WEAKNESSTIMER;
					this.P7WeaknessCastTimer = _ConfigEntityCarapace.P7WEAKNESSCASTTIME;
					this.WeaknessCasting = false;
				}
			}
    		break;
    		
    		
    		
    	/**
         * =================================================
         * PHASE 8 
         * =================================================
        **/
    	case 8:
    		this.AttackSoundDelay--;
			if(this.AttackSoundDelay <= 0)
			{
				this.AttackSoundDelay = _ConfigEntityCarapace.ATTACK_SOUND_DELAY;
				this.playAttackSound();
			}
			
    		if(!this.world.isRemote)
    		{
    			this.setInvulState(false);
    			
    			Random rand = new Random();
        		if(!this.P8FacingFound)
        		{
        			this.P8FacingFound = true;
        			this.playVoidMagicCastSound(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
        			List<EntityPlayer> players = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
    				for (EntityPlayer player : players)
    		        {
    					this.P8FacingPlayer = player;
    		        }
    				
    				AxisAlignedBB AoePoint1 = new AxisAlignedBB(this.posX, this.posY, this.posZ, this.posX + 100, this.posY + 50, this.posZ + 100);
    				List<EntityPlayer> AABBPlayer1 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint1);
    				if (!AABBPlayer1.isEmpty())
    			    {
    			        for (EntityPlayer ent : AABBPlayer1)
    			        {
    			        	if(ent == this.P8FacingPlayer)
    			        	{
    			        		this.P8PlayerFound = true;
    			        		this.P8Facing = 0;
    			        	}
    			        }
    			    }
    				AxisAlignedBB AoePoint2 = new AxisAlignedBB(this.posX, this.posY, this.posZ, this.posX - 100, this.posY + 50, this.posZ + 100);
    				List<EntityPlayer> AABBPlayer2 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint2);
    				if (!AABBPlayer2.isEmpty())
    			    {
    			        for (EntityPlayer ent : AABBPlayer2)
    			        {
    			        	if(ent == this.P8FacingPlayer)
    			        	{
    			        		this.P8PlayerFound = true;
    			        		this.P8Facing = 1;
    			        	}
    			        }
    			    }
    				AxisAlignedBB AoePoint3 = new AxisAlignedBB(this.posX, this.posY, this.posZ, this.posX - 100, this.posY + 50, this.posZ - 100);
    				List<EntityPlayer> AABBPlayer3 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint3);
    				if (!AABBPlayer3.isEmpty())
    			    {
    			        for (EntityPlayer ent : AABBPlayer3)
    			        {
    			        	if(ent == this.P8FacingPlayer)
    			        	{
    			        		this.P8PlayerFound = true;
    			        		this.P8Facing = 2;
    			        	}
    			        }
    			    }
    				AxisAlignedBB AoePoint4 = new AxisAlignedBB(this.posX, this.posY, this.posZ, this.posX + 100, this.posY + 50, this.posZ - 100);
    				List<EntityPlayer> AABBPlayer4 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint4);
    				if (!AABBPlayer4.isEmpty())
    			    {
    			        for (EntityPlayer ent : AABBPlayer4)
    			        {
    			        	if(ent == this.P8FacingPlayer)
    			        	{
    			        		this.P8PlayerFound = true;
    			        		this.P8Facing = 3;
    			        	}
    			        }
    			    }
        		}
    			
    			this.P8CastTime--;
    			switch(this.P8Facing)
    			{
    			case 0:
    				this.getLookHelper().setLookPosition(this.posX + 1, this.posY + (double)this.getEyeHeight(), this.posZ + 1, 100.0f, 100.0f);
    				PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceSightAttack(this, this.world, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, 0), this.dimension);
    				break;
    			case 1:
    				this.getLookHelper().setLookPosition(this.posX - 1, this.posY + (double)this.getEyeHeight(), this.posZ + 1, 100.0f, 100.0f);
    				PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceSightAttack(this, this.world, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, 1), this.dimension);
    				break;
    			case 2:
    				this.getLookHelper().setLookPosition(this.posX - 1, this.posY + (double)this.getEyeHeight(), this.posZ - 1, 100.0f, 100.0f);
    				PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceSightAttack(this, this.world, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, 2), this.dimension);
    				break;
    			case 3:
    				this.getLookHelper().setLookPosition(this.posX + 1, this.posY + (double)this.getEyeHeight(), this.posZ - 1, 100.0f, 100.0f);
    				PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceSightAttack(this, this.world, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, 3), this.dimension);
    				break;
    			}
    			if(this.P8CastTime <= 0)
    			{
    				switch(this.P8Facing)
        			{
        			case 0:
        				PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceSightExplode(this, this.world, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, 0, 11), this.dimension);
        				AxisAlignedBB AoePoint1 = new AxisAlignedBB(this.posX, this.posY, this.posZ, this.posX + 100, this.posY + 50, this.posZ + 100);
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
        				break;
        			case 1:
        				PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceSightExplode(this, this.world, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, 1, 11), this.dimension);
        				AxisAlignedBB AoePoint2 = new AxisAlignedBB(this.posX, this.posY, this.posZ, this.posX - 100, this.posY + 50, this.posZ + 100);
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
        				break;
        			case 2:
        				PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceSightExplode(this, this.world, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, 2, 11), this.dimension);
        				AxisAlignedBB AoePoint3 = new AxisAlignedBB(this.posX, this.posY, this.posZ, this.posX - 100, this.posY + 50, this.posZ - 100);
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
        			case 3:
        				PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceSightExplode(this, this.world, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, 3, 11), this.dimension);
        				AxisAlignedBB AoePoint4 = new AxisAlignedBB(this.posX, this.posY, this.posZ, this.posX + 100, this.posY + 50, this.posZ - 100);
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
    				this.P8CastTime = _ConfigEntityCarapace.P8CASTTIME;
    				this.P8FacingFound = false;
    				this.P8Facing = 0;
    				this.setPhase(7);
    			}
    			
    			if(this.getHealth() <= (this.getMaxHealth() / 10) * 4) 
    			{
    				this.doorPhase = 0;
    				this.setPhase(9);
    			}
    		}
    		break;
    		
    		
    		
    	/**
         * =================================================
         * PHASE 9 
         * =================================================
        **/
    	case 9:
    		this.AttackSoundDelay--;
			if(this.AttackSoundDelay <= 0)
			{
				this.AttackSoundDelay = _ConfigEntityCarapace.ATTACK_SOUND_DELAY;
				this.playAttackSound();
			}
			
    		if(!this.world.isRemote)
    		{
    			this.AttackSoundDelay--;
    			if(this.AttackSoundDelay <= 0)
    			{
    				this.AttackSoundDelay = _ConfigEntityCarapace.ATTACK_SOUND_DELAY;
    				this.playAttackSound();
    			}
    			
    			if(this.getPosition().getX() >= 85)
    			{
    				switch(this.doorPhase)
        			{
        			case 0:
        				this.openDoor7(this.getSpawnLocation().getX() + 40, this.getSpawnLocation().getY() + 1, this.getSpawnLocation().getZ() - 1);
        				this.P9DoorAnimDelay--;
        				if(this.P9DoorAnimDelay <= 0)
        				{
        					this.P9DoorAnimDelay = _ConfigEntityCarapace.P9DOORANIMDELAY;
        					this.doorPhase = 1;
        				}
        				break;
        			case 1:
        				this.openDoor8(this.getSpawnLocation().getX() + 40, this.getSpawnLocation().getY() + 1, this.getSpawnLocation().getZ() - 1);
        				this.P9DoorAnimDelay--;
        				if(this.P9DoorAnimDelay <= 0)
        				{
        					this.P9DoorAnimDelay = _ConfigEntityCarapace.P9DOORANIMDELAY;
        					this.doorPhase = 2;
        				}
        				break;
        			case 2:
        				this.openDoor9(this.getSpawnLocation().getX() + 40, this.getSpawnLocation().getY() + 1, this.getSpawnLocation().getZ() - 1);
        				this.doorPhase = -1;
        				break;
        			}
    			}
    			
    			if(this.getPosition().getX() > 90)
    			{
    				this.P9Timer--;
    				if(this.P9Timer <= 0)
    				{
    					this.P9Timer = _ConfigEntityCarapace.P9TIMER;
    					this.doorPhase = 0;
    					this.setPhase(10);
    				}
    			}
    		}
    		break;
    	case 10:
    		this.AttackSoundDelay--;
			if(this.AttackSoundDelay <= 0)
			{
				this.AttackSoundDelay = _ConfigEntityCarapace.ATTACK_SOUND_DELAY;
				this.playAttackSound();
			}
			
    		if(!this.world.isRemote)
    		{
    			switch(this.doorPhase)
    			{
    			case 0:
    				this.closeDoor5(this.getSpawnLocation().getX() + 47, this.getSpawnLocation().getY() - 2, this.getSpawnLocation().getZ() - 1);
    				this.P10DoorAnimDelay--;
    				if(this.P10DoorAnimDelay <= 0)
    				{
    					this.P10DoorAnimDelay = _ConfigEntityCarapace.P10DOORANIMDELAY;
    					this.doorPhase = 1;
    				}
    				break;
    			case 1:
    				this.closeDoor6(this.getSpawnLocation().getX() + 47, this.getSpawnLocation().getY() - 2, this.getSpawnLocation().getZ() - 1);
    				this.P10DoorAnimDelay--;
    				if(this.P10DoorAnimDelay <= 0)
    				{
    					this.P10DoorAnimDelay = _ConfigEntityCarapace.P10DOORANIMDELAY;
    					this.doorPhase = 2;
    				}
    				break;
    			case 2:
    				this.closeDoor7(this.getSpawnLocation().getX() + 47, this.getSpawnLocation().getY() - 2, this.getSpawnLocation().getZ() - 1);
    				this.doorPhase = -1;
    				break;
    			}
    			
    			this.PurpleBeamCD--;
    			if(this.PurpleBeamCD <= 0)
    			{
    				this.PurpleBeamTimer--;
    				if(!this.PurpleBeamPosFound)
    				{
    					this.PurpleBeamPosFound = true;
    					this.playVoidMagicCastSound(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
    					for(int i = 0; i <= 9; i++)
    					{
    						int randX = (this.getPosition().getX() - 12) + this.rand.nextInt(24);
	    					int randZ = (this.getPosition().getZ() - 12) + this.rand.nextInt(24);
	    					List pos = new ArrayList();
	    					pos.add(randX);
	    					pos.add(this.getPosition().getY());
	    					pos.add(randZ);
	    					this.PurpleBeamLocations.add(pos);
    					}
    				}
    				else
    				{
    					if(!this.PurpleBeamLocations.isEmpty())
    					{
    						for(int i = 0; i < this.PurpleBeamLocations.size(); i++)
    						{
    							ArrayList pos = (ArrayList)this.PurpleBeamLocations.get(i);
    							
    							if(this.PurpleBeamLocations.get(i) != null)
    							{
    								if(this.PurpleBeamTimer <= 20)
    								{
    									if(this.PurpleBeamTimer == 20) 
    									{
    										this.playImpactSound((Integer)pos.get(0), (Integer)pos.get(1), (Integer)pos.get(2), 1.0F);
    										AxisAlignedBB AoePoint = new AxisAlignedBB((Integer)pos.get(0) - 2.25D, (Integer)pos.get(1) - 0.5D, (Integer)pos.get(2) - 2.25D, (Integer)pos.get(0) + 2.25D, (Integer)pos.get(1) + 10.0D, (Integer)pos.get(2) + 2.25D);
    										List<EntityPlayer> AABBPlayer = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint);
    										if (!AABBPlayer.isEmpty())
    									    {
    									        for (EntityPlayer ent : AABBPlayer)
    									        {
    									        	ent.attackEntityFrom(DamageSource.MAGIC, 45.5F);
    									        	ent.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 150, 1));
    									        }
    									    }
    									}
    									Integer x = new Integer((int)pos.get(0)); 
    									double x1 = x.intValue();
    									Integer y = new Integer((int)pos.get(1)); 
    									double y1 = y.intValue();
    									Integer z = new Integer((int)pos.get(2)); 
    									double z1 = z.intValue();
    									PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceBeam(this, this.world, 9, x1, y1, z1, 0.0D, 0.0D, 0.0D, 3), this.dimension);
    									if(this.PurpleBeamTimer <= 0)
    									{
    										this.PurpleBeamCD = _ConfigEntityCarapace.PURPLEBEAMCD;
        									this.PurpleBeamTimer = _ConfigEntityCarapace.PURPLEBEAMTIMER;
        									this.PurpleBeamPosFound = false;
        									this.PurpleBeamLocations.clear();
    									}
    								}
    								else
    								{
    									Integer x = new Integer((int)pos.get(0)); 
    									double x1 = x.intValue();
    									Integer y = new Integer((int)pos.get(1)); 
    									double y1 = y.intValue();
    									Integer z = new Integer((int)pos.get(2)); 
    									double z1 = z.intValue();
    									PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceParticleRing(this, this.world, 9, x1, y1, z1, 0.0D, 0.0D, 0.0D, 3), this.dimension);
    									if(this.PurpleBeamTimer >= 60)
    									{
    										if(this.PurpleBeamTimer % 3 == 1) PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceBeamGrow(this, this.world, 9, x1, y1, z1, 0.0D, 0.0D, 0.0D, 1), this.dimension);
    									}
    									else if(this.PurpleBeamTimer <= 60 && this.PurpleBeamTimer >= 40)
    									{
    										if(this.PurpleBeamTimer % 3 == 1) PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceBeamGrow(this, this.world, 9, x1, y1, z1, 0.0D, 0.0D, 0.0D, 2), this.dimension);
    									}
    									else if(this.PurpleBeamTimer <= 40 && this.PurpleBeamTimer >= 20)
    									{
    										if(this.PurpleBeamTimer % 3 == 1) PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceBeamGrow(this, this.world, 9, x1, y1, z1, 0.0D, 0.0D, 0.0D, 3), this.dimension);
    									}
    								}
    							}
    						}
    					}
    				}
    			}
    			
    			this.P10BigGroundAOE--;
        		if(this.P10BigGroundAOE <= 0)
        		{
        			this.P10BigGroundAOE = _ConfigEntityCarapace.P10BIGGROUNDAOE;
        			this.spawnAnguish(this.posX, this.posY, this.posZ);
        			this.playVoidMagicCastSound(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
        		}
        		
        		this.P10ExplosionTimer--;
        		List<EntityPlayer> P10players = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
    			for (EntityPlayer player : P10players)
    	        {
    	    		if(this.P10ExplosionTimer <= 0)
    	    		{
    	    			this.P10ExplosionTimer = _ConfigEntityCarapace.P10EXPLOSIONTIMER;
    	    			this.spawnVoidExplosion(player, player.posX, player.posY, player.posZ);
    	    		}
    	    		
    	    		for(int i = 0; i < this.explosionPointPlayers.size(); i++)
    				{
    	    			EntityPlayer activePlayer = (EntityPlayer)this.explosionPointPlayers.get(i);
    					if(player == activePlayer)
    					{
    						List pos = new ArrayList();
    						pos.add(player.posX);
    						pos.add(player.posY);
    						pos.add(player.posZ);
    						this.explosionPoints.set(i, pos);
    					}
    				}
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
		switch(this.getPrePhase())
		{
		case 1:
			this.tasks.addTask(1, aiMoveToMainArea);
			break;
		case 2:
			this.tasks.removeTask(aiMoveToMainArea);
			this.tasks.addTask(2, aiWatchClosest);
			break;
		}
		
		switch(this.getPhase())
		{
		case 1:
			this.tasks.removeTask(aiMoveToRoom1Mid);
			this.tasks.addTask(1, aiMeleeAttack);
			break;
		case 2:
			this.tasks.removeTask(aiMeleeAttack);
			this.tasks.addTask(1, aiMoveToRoom1Mid);
			break;
		case 3:
			this.tasks.removeTask(aiMeleeAttack);
			this.tasks.addTask(1, aiMoveToRoom1Mid);
			break;
		case 4:
			this.tasks.removeTask(aiMoveToRoom1Mid);
			this.tasks.removeTask(aiMeleeAttack);
			this.tasks.addTask(1, aiMoveBackToStartRoom1);
			break;
		case 5:
			this.tasks.removeTask(aiMoveBackToStartRoom1);
			this.tasks.addTask(1, aiMoveToSpawnLocation);
			if(this.getPosition().getX() > 49)
			{
				this.tasks.removeTask(aiMoveToSpawnLocation);
				this.tasks.addTask(1, aiMoveToSecondArea);
			}
			break;
		case 6:
			this.tasks.removeTask(aiMoveToSecondArea);
			break;
		case 7:
			this.tasks.addTask(2, aiWatchClosest);
			if(this.P7WeaknessTimer <= 0)
			{
				this.tasks.removeTask(aiMeleeAttack);
			}
			else
			{
				this.tasks.addTask(1, aiMeleeAttack);
			}
			break;
		case 8:
			this.tasks.removeTask(aiMeleeAttack);
			this.tasks.removeTask(aiWatchClosest);
			break;
		case 9:
			if(!this.P9Teleported)
			{
				this.P9Teleported = true;
				if(this.getPosition().getY() > 53 || this.getPosition().getX() < 45)
				{
					this.setPosition(70, 50, 1);
				}
				this.tasks.addTask(1, aiMoveToSecondAreaDoor);
			}
			else
			{
				if(this.getPosition().getX() >= 85)
    			{
					this.tasks.removeTask(aiMoveToSecondAreaDoor);
					this.tasks.addTask(1, aiMoveToThroughSecondDoor);
					if(this.getPosition().getX() >= 97)
	    			{
						this.tasks.removeTask(aiMoveToThroughSecondDoor);
						this.tasks.addTask(1, aiMoveToThirdRoom);
	    			}
    			}
			}
			break;
		case 10:
			this.tasks.removeTask(aiMoveToThirdRoom);
			this.tasks.addTask(1, aiMeleeAttack);
			break;
		}
    }
	
	public void onUpdate()
    {
        super.onUpdate();

       
    }
	
	@Override
	public float getEyeHeight()
	{
		return 3.1F;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		Random rand = new Random();
    	int r = rand.nextInt(3);
    	switch(r)
    	{
    	default:
    		return EMSoundHandler.CARAPACE_WOUND_01;
    	case 0:
    		return EMSoundHandler.CARAPACE_WOUND_01;
    	case 1:
    		return EMSoundHandler.CARAPACE_WOUND_02;
    	case 2:
    		return EMSoundHandler.CARAPACE_WOUND_03;
    	}
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
    	List<EntityCarapaceAnguish> anguish = this.world.<EntityCarapaceAnguish>getEntitiesWithinAABB(EntityCarapaceAnguish.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
		for (EntityCarapaceAnguish ent : anguish)
        {
			ent.isDead = true;
        }
		
		this.openDoorDeath1(this.getSpawnLocation().getX() + 47, this.getSpawnLocation().getY() - 2, this.getSpawnLocation().getZ() - 1);
		this.openDoorDeath2(this.getSpawnLocation().getX() + 1, this.getSpawnLocation().getY() + 1, this.getSpawnLocation().getZ() - 1);
		if(!this.world.isRemote) PacketHandler.INSTANCE.sendToDimension(new PacketStopMusic(this, this.world), this.dimension);
		
        super.onDeath(cause);
    }
    
    public void resetFight()
    {
    	this.openDoorDeath1(this.getSpawnLocation().getX() + 47, this.getSpawnLocation().getY() - 2, this.getSpawnLocation().getZ() - 1);
		this.openDoorDeath2(this.getSpawnLocation().getX() + 1, this.getSpawnLocation().getY() + 1, this.getSpawnLocation().getZ() - 1);
		this.resetDoor1(this.getSpawnLocation().getX() - 8, this.getSpawnLocation().getY() + 3, this.getSpawnLocation().getZ() - 1);
		this.resetDoor2(this.getSpawnLocation().getX() + 2, this.getSpawnLocation().getY() + 1, this.getSpawnLocation().getZ() - 1);
		this.resetDoor3(this.getSpawnLocation().getX() + 40, this.getSpawnLocation().getY() + 1, this.getSpawnLocation().getZ() - 1);
    	this.setPosition(this.getSpawnLocation().getX() + 1.0D, this.getSpawnLocation().getY() + 0.5D, this.getSpawnLocation().getZ() + 1.0D);
    	this.setHealth(this.getMaxHealth());
    	this.setPrePhase(0);
    	this.setPhase(0);
    	this.setPurpleBuff(false);
    	this.setBlueBuff(false);
    	this.explosionPoints = new ArrayList();
    	this.explosionPointTimer = _ConfigEntityCarapace.EXPLOSION_TIMER;
    	this.explosionPointPlayers = new ArrayList();
    	this.musicDuration = _ConfigEntityCarapace.MUSIC_DURATION;
    	this.musicPlaying = false;
    	this.AttackSoundDelay = _ConfigEntityCarapace.ATTACK_SOUND_DELAY;
    	this.prefighttimer1 = _ConfigEntityCarapace.PREFIGHTTIMER1;
    	this.prefightdooranimdelay = _ConfigEntityCarapace.PREFIGHTDOORANIMDELAY;
    	this.doorPhase = 0;
    	this.P1explosionCD = _ConfigEntityCarapace.P1EXPLOSIONTIMER;
    	this.P1TTimer = _ConfigEntityCarapace.P1TIMER;
    	this.P2explosionCD = _ConfigEntityCarapace.P2EXPLOSIONTIMER;
    	this.P2Timer = _ConfigEntityCarapace.P2TIMER;
    	this.P2RunToMid = _ConfigEntityCarapace.RUNTOMIDTIMER;
    	this.P2OrbsSpawned = false;
    	this.P3explosionCD = _ConfigEntityCarapace.P3EXPLOSIONTIMER;
    	this.P3Timer = _ConfigEntityCarapace.P3TIMER;
    	this.BlueMinionSpawned = false;
    	this.BlueBuffCD = _ConfigEntityCarapace.BLUEBUFFCD;
    	this.BlueBuffSoakTimer = _ConfigEntityCarapace.BLUEBUFFSOAKTIMER;
    	this.BlueBuffSoakLocation = null;
    	this.BlueBuffPosFound = false;
    	this.PurpleMinionSpawned = false;
    	this.PurpleBuffCD = _ConfigEntityCarapace.PURPLEBUFFCD;
    	this.PurpleBuffTimer = _ConfigEntityCarapace.PURPLEBUFFTIMER;
    	this.PurpleBuffPosition = 0;
    	this.PurpleBuffDoublePosition = false;
    	this.PurpleBuffPosition2 = 0;
    	this.PurpleBuffPosFound = false;
    	this.P5DoorAnimDelay = _ConfigEntityCarapace.P5DOORANIMDELAY;
    	this.P5Timer = _ConfigEntityCarapace.P5TIMER;
    	this.P6WeaknessTimer = _ConfigEntityCarapace.P6WEAKNESSTIMER;
    	this.P6WeaknessCastTimer = _ConfigEntityCarapace.P6WEAKNESSCASTTIME;
    	this.WeaknessCasting = false;
    	this.P7DoorAnimDelay = _ConfigEntityCarapace.P7DOORANIMDELAY;
    	this.P7WeaknessTimer = _ConfigEntityCarapace.P7WEAKNESSTIMER;
    	this.P7WeaknessCastTimer = _ConfigEntityCarapace.P7WEAKNESSCASTTIME;
    	this.P7Timer = _ConfigEntityCarapace.P7TIMER;
    	this.P8CastTime = _ConfigEntityCarapace.P8CASTTIME;
    	this.P8FacingFound = false;
    	this.P8Facing = 0;
    	this.P8FacingPlayer = null;
    	this.P8PlayerFound = false;
    	this.P9Teleported = false;
    	this.P9DoorAnimDelay = _ConfigEntityCarapace.P9DOORANIMDELAY;
    	this.P9Timer = _ConfigEntityCarapace.P9TIMER;
    	this.P10DoorAnimDelay = _ConfigEntityCarapace.P10DOORANIMDELAY;
    	this.P10BigGroundAOE = _ConfigEntityCarapace.P10BIGGROUNDAOE;
    	this.P10ExplosionTimer = _ConfigEntityCarapace.P10EXPLOSIONTIMER;
    	this.PurpleBeamCD = _ConfigEntityCarapace.PURPLEBEAMCD;
    	this.PurpleBeamTimer = _ConfigEntityCarapace.PURPLEBEAMTIMER;
    	this.PurpleBeamLocations = new ArrayList();
    	this.PurpleBeamPosFound = false;
    	
    	List<EntityCarapaceEye> list = this.world.<EntityCarapaceEye>getEntitiesWithinAABB(EntityCarapaceEye.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
    	if(!list.isEmpty())
    	{
	    	for (EntityCarapaceEye entity : list)
	        {
	    		entity.isDead = true;
	        }
    	}
    	
    	List<EntityBlueGuardian> list2 = this.world.<EntityBlueGuardian>getEntitiesWithinAABB(EntityBlueGuardian.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
    	if(!list2.isEmpty())
    	{
	    	for (EntityBlueGuardian entity : list2)
	        {
	    		entity.isDead = true;
	        }
    	}
    	
    	List<EntityPurpleGuardian> list3 = this.world.<EntityPurpleGuardian>getEntitiesWithinAABB(EntityPurpleGuardian.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
    	if(!list3.isEmpty())
    	{
	    	for (EntityPurpleGuardian entity : list3)
	        {
	    		entity.isDead = true;
	        }
    	}
    	
    	List<EntityPurpleOrb> list4 = this.world.<EntityPurpleOrb>getEntitiesWithinAABB(EntityPurpleOrb.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
    	if(!list4.isEmpty())
    	{
	    	for (EntityPurpleOrb entity : list4)
	        {
	    		entity.isDead = true;
	        }
    	}
    	
    	List<EntityBlueOrb> list5 = this.world.<EntityBlueOrb>getEntitiesWithinAABB(EntityBlueOrb.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
    	if(!list5.isEmpty())
    	{
	    	for (EntityBlueOrb entity : list5)
	        {
	    		entity.isDead = true;
	        }
    	}
    	
    	List<EntityCarapaceAnguish> list6 = this.world.<EntityCarapaceAnguish>getEntitiesWithinAABB(EntityCarapaceAnguish.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
    	if(!list6.isEmpty())
    	{
	    	for (EntityCarapaceAnguish entity : list6)
	        {
	    		entity.isDead = true;
	        }
    	}
    	
    	EntityCarapaceEye entity = new EntityCarapaceEye(this.world);
       	entity.setPosition(85 + 1.0D, 55, -21);
       	entity.enablePersistence();
   		this.world.spawnEntity(entity);
   		
   		EntityCarapaceEye entity2 = new EntityCarapaceEye(this.world);
       	entity2.setPosition(80 + 1.0D, 55, -20);
       	entity2.enablePersistence();
   		this.world.spawnEntity(entity2);
   		
   		EntityCarapaceEye entity3 = new EntityCarapaceEye(this.world);
       	entity3.setPosition(81 + 1.0D, 55, -16);
       	entity3.enablePersistence();
   		this.world.spawnEntity(entity3);
   		
   		EntityCarapaceEye entity4 = new EntityCarapaceEye(this.world);
       	entity4.setPosition(85 + 1.0D, 55, -13);
       	entity4.enablePersistence();
   		this.world.spawnEntity(entity4);
   		
   		EntityCarapaceEye entity5 = new EntityCarapaceEye(this.world);
   		entity5.setPosition(75 + 1.0D, 55, -15);
   		entity5.enablePersistence();
   		this.world.spawnEntity(entity5);
   		
   		EntityCarapaceEye entity6 = new EntityCarapaceEye(this.world);
   		entity6.setPosition(80 + 1.0D, 55, -11);
   		entity6.enablePersistence();
   		this.world.spawnEntity(entity6);
   		
   		EntityCarapaceEye entity7 = new EntityCarapaceEye(this.world);
   		entity7.setPosition(84 + 1.0D, 55, -9);
   		entity7.enablePersistence();
   		this.world.spawnEntity(entity7);
   		
   		EntityCarapaceEye entity8 = new EntityCarapaceEye(this.world);
   		entity8.setPosition(86 + 1.0D, 55, 11);
   		entity8.enablePersistence();
   		this.world.spawnEntity(entity8);
   		
   		EntityCarapaceEye entity9 = new EntityCarapaceEye(this.world);
   		entity9.setPosition(81 + 1.0D, 55, 11);
   		entity9.enablePersistence();
   		this.world.spawnEntity(entity9);
   		
   		EntityCarapaceEye entity10 = new EntityCarapaceEye(this.world);
   		entity10.setPosition(83 + 1.0D, 55, 15);
   		entity10.enablePersistence();
   		this.world.spawnEntity(entity10);
   		
   		EntityCarapaceEye entity11 = new EntityCarapaceEye(this.world);
   		entity11.setPosition(76 + 1.0D, 55, 14);
   		entity11.enablePersistence();
   		this.world.spawnEntity(entity11);
   		
   		EntityCarapaceEye entity12 = new EntityCarapaceEye(this.world);
   		entity12.setPosition(79 + 1.0D, 55, 17);
   		entity12.enablePersistence();
   		this.world.spawnEntity(entity12);
   		
   		EntityCarapaceEye entity13 = new EntityCarapaceEye(this.world);
   		entity13.setPosition(86 + 1.0D, 55, 18);
   		entity13.enablePersistence();
   		this.world.spawnEntity(entity13);
   		
   		EntityCarapaceEye entity14 = new EntityCarapaceEye(this.world);
   		entity14.setPosition(84 + 1.0D, 55, 22);
   		entity14.enablePersistence();
   		this.world.spawnEntity(entity14);
    }
    
    public void openDoor1(int x, int y, int z)
    {
    	BlockPos doorpos = new BlockPos(x, y, z);
    	this.world.setBlockToAir(doorpos.add(0, 4, 1));
    	this.world.setBlockToAir(doorpos.add(0, 4, 2));
    }
    
    public void openDoor2(int x, int y, int z)
    {
    	BlockPos doorpos = new BlockPos(x, y, z);
    	this.world.setBlockToAir(doorpos.add(1, 4, 1));
    	this.world.setBlockToAir(doorpos.add(1, 4, 2));
    	this.world.setBlockToAir(doorpos.add(1, 3, 1));
    	this.world.setBlockToAir(doorpos.add(1, 3, 2));
    	this.world.setBlockToAir(doorpos.add(1, 2, 1));
    	this.world.setBlockToAir(doorpos.add(1, 2, 2));
    	this.world.setBlockToAir(doorpos.add(1, 1, 1));
    	this.world.setBlockToAir(doorpos.add(1, 1, 2));
    	this.world.setBlockToAir(doorpos.add(1, 0, 1));
    	this.world.setBlockToAir(doorpos.add(1, 0, 2));
    	this.world.setBlockToAir(doorpos.add(1, -1, 1));
    	this.world.setBlockToAir(doorpos.add(1, -1, 2));
    }
    
    public void openDoor3(int x, int y, int z)
    {
    	BlockPos doorpos = new BlockPos(x, y, z);
    	this.world.setBlockToAir(doorpos.add(0, 3, 0));
    	this.world.setBlockToAir(doorpos.add(0, 3, 3));
    	this.world.setBlockToAir(doorpos.add(0, 2, 0));
    	this.world.setBlockToAir(doorpos.add(0, 2, 3));
    	this.world.setBlockToAir(doorpos.add(0, 1, 0));
    	this.world.setBlockToAir(doorpos.add(0, 1, 3));
    	this.world.setBlockToAir(doorpos.add(0, 0, 0));
    	this.world.setBlockToAir(doorpos.add(0, 0, 3));
    	this.world.setBlockToAir(doorpos.add(0, -1, 0));
    	this.world.setBlockToAir(doorpos.add(0, -1, 3));
    }
    
    public void openDoor4(int x, int y, int z)
    {
    	BlockPos doorpos = new BlockPos(x, y, z);
    	this.world.setBlockToAir(doorpos.add(0, 3, 1));
    	this.world.setBlockToAir(doorpos.add(0, 3, 2));
    }
    
    public void openDoor5(int x, int y, int z)
    {
    	BlockPos doorpos = new BlockPos(x, y, z);
    	this.world.setBlockToAir(doorpos.add(1, 3, 1));
    	this.world.setBlockToAir(doorpos.add(1, 3, 2));
    	this.world.setBlockToAir(doorpos.add(1, 2, 1));
    	this.world.setBlockToAir(doorpos.add(1, 2, 2));
    	this.world.setBlockToAir(doorpos.add(1, 1, 1));
    	this.world.setBlockToAir(doorpos.add(1, 1, 2));
    	this.world.setBlockToAir(doorpos.add(1, 0, 1));
    	this.world.setBlockToAir(doorpos.add(1, 0, 2));
    	this.world.setBlockToAir(doorpos.add(1, -1, 1));
    	this.world.setBlockToAir(doorpos.add(1, -1, 2));
    }
    
    public void openDoor6(int x, int y, int z)
    {
    	BlockPos doorpos = new BlockPos(x, y, z);
    	this.world.setBlockToAir(doorpos.add(0, 2, 0));
    	this.world.setBlockToAir(doorpos.add(0, 2, 3));
    	this.world.setBlockToAir(doorpos.add(0, 1, 0));
    	this.world.setBlockToAir(doorpos.add(0, 1, 3));
    	this.world.setBlockToAir(doorpos.add(0, 0, 0));
    	this.world.setBlockToAir(doorpos.add(0, 0, 3));
    	this.world.setBlockToAir(doorpos.add(0, -1, 0));
    	this.world.setBlockToAir(doorpos.add(0, -1, 3));
    }
    
    public void openDoor7(int x, int y, int z)
    {
    	BlockPos doorpos = new BlockPos(x, y, z);
    	this.world.setBlockToAir(doorpos.add(0, 3, 1));
    	this.world.setBlockToAir(doorpos.add(0, 3, 2));
    }
    
    public void openDoor8(int x, int y, int z)
    {
    	BlockPos doorpos = new BlockPos(x, y, z);
    	this.world.setBlockToAir(doorpos.add(1, 3, 1));
    	this.world.setBlockToAir(doorpos.add(1, 3, 2));
    	this.world.setBlockToAir(doorpos.add(1, 2, 1));
    	this.world.setBlockToAir(doorpos.add(1, 2, 2));
    	this.world.setBlockToAir(doorpos.add(1, 1, 1));
    	this.world.setBlockToAir(doorpos.add(1, 1, 2));
    	this.world.setBlockToAir(doorpos.add(1, 0, 1));
    	this.world.setBlockToAir(doorpos.add(1, 0, 2));
    	this.world.setBlockToAir(doorpos.add(1, -1, 1));
    	this.world.setBlockToAir(doorpos.add(1, -1, 2));
    }
    
    public void openDoor9(int x, int y, int z)
    {
    	BlockPos doorpos = new BlockPos(x, y, z);
    	this.world.setBlockToAir(doorpos.add(0, 2, 0));
    	this.world.setBlockToAir(doorpos.add(0, 2, 3));
    	this.world.setBlockToAir(doorpos.add(0, 1, 0));
    	this.world.setBlockToAir(doorpos.add(0, 1, 3));
    	this.world.setBlockToAir(doorpos.add(0, 0, 0));
    	this.world.setBlockToAir(doorpos.add(0, 0, 3));
    	this.world.setBlockToAir(doorpos.add(0, -1, 0));
    	this.world.setBlockToAir(doorpos.add(0, -1, 3));
    }
    
    public void closeDoor1(int x, int y, int z)
    {
    	BlockPos doorpos = new BlockPos(x, y, z);
    	this.world.setBlockState(doorpos.add(0, 4, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 4, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());

    	this.world.setBlockState(doorpos.add(1, 4, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 4, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 3, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 3, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 2, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 2, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 1, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 1, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 0, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 0, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, -1, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, -1, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	
    	this.world.setBlockState(doorpos.add(0, 3, 0), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 3, 3), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 2, 0), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 2, 3), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 1, 0), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 1, 3), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 0, 0), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 0, 3), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, -1, 0), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, -1, 3), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    }
    
    public void closeDoor2(int x, int y, int z)
    {
    	BlockPos doorpos = new BlockPos(x, y, z);
    	this.world.setBlockState(doorpos.add(1, 3, 0), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 3, 3), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 2, 0), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 2, 3), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 1, 0), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 1, 3), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 0, 0), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 0, 3), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, -1, 0), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, -1, 3), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    }
    
    public void closeDoor3(int x, int y, int z)
    {
    	BlockPos doorpos = new BlockPos(x, y, z);
    	this.world.setBlockState(doorpos.add(0, 4, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 4, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 3, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 3, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 2, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 2, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 1, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 1, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 0, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 0, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, -1, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, -1, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    }
    
    public void closeDoor4(int x, int y, int z)
    {
    	BlockPos doorpos = new BlockPos(x, y, z);
    	this.world.setBlockState(doorpos.add(1, 3, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 3, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    }
    
    public void closeDoor5(int x, int y, int z)
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
    }
    
    public void closeDoor6(int x, int y, int z)
    {
    	BlockPos doorpos = new BlockPos(x, y, z);
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
    }
    
    public void closeDoor7(int x, int y, int z)
    {
    	BlockPos doorpos = new BlockPos(x, y, z);
    	this.world.setBlockState(doorpos.add(1, 4, 1), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 4, 2), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    }
    

    public void openDoorDeath1(int x, int y, int z)
    {
    	BlockPos doorpos = new BlockPos(x, y, z);
    	this.world.setBlockToAir(doorpos.add(1, 3, 0));
    	this.world.setBlockToAir(doorpos.add(1, 3, 3));
    	this.world.setBlockToAir(doorpos.add(1, 2, 0));
    	this.world.setBlockToAir(doorpos.add(1, 2, 3));
    	this.world.setBlockToAir(doorpos.add(1, 1, 0));
    	this.world.setBlockToAir(doorpos.add(1, 1, 3));
    	this.world.setBlockToAir(doorpos.add(1, 0, 0));
    	this.world.setBlockToAir(doorpos.add(1, 0, 3));
    	this.world.setBlockToAir(doorpos.add(0, 4, 1));
    	this.world.setBlockToAir(doorpos.add(0, 4, 2));
    	this.world.setBlockToAir(doorpos.add(0, 3, 1));
    	this.world.setBlockToAir(doorpos.add(0, 3, 2));
    	this.world.setBlockToAir(doorpos.add(0, 2, 1));
    	this.world.setBlockToAir(doorpos.add(0, 2, 2));
    	this.world.setBlockToAir(doorpos.add(0, 1, 1));
    	this.world.setBlockToAir(doorpos.add(0, 1, 2));
    	this.world.setBlockToAir(doorpos.add(0, 0, 1));
    	this.world.setBlockToAir(doorpos.add(0, 0, 2));
    	this.world.setBlockToAir(doorpos.add(1, 4, 1));
    	this.world.setBlockToAir(doorpos.add(1, 4, 2));
    }
    
    public void openDoorDeath2(int x, int y, int z)
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
    
    public void resetDoor1(int x, int y, int z)
    {
    	BlockPos doorpos = new BlockPos(x, y, z);
    	this.world.setBlockState(doorpos.add(0, 4, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 4, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 4, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 4, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 3, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 3, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 2, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 2, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 1, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 1, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 0, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 0, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, -1, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, -1, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 3, 0), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 3, 3), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 2, 0), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 2, 3), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 1, 0), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 1, 3), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 0, 0), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 0, 3), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, -1, 0), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, -1, 3), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    }
    
    public void resetDoor2(int x, int y, int z)
    {
    	BlockPos doorpos = new BlockPos(x, y, z);
    	this.world.setBlockState(doorpos.add(0, 3, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 3, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 3, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 3, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 2, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 2, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 1, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 1, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 0, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 0, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, -1, 1), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, -1, 2), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 2, 0), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 2, 3), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 1, 0), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 1, 3), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 0, 0), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 0, 3), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, -1, 0), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, -1, 3), EMCoreBlocks.CORRUPTED_FLESH2.getDefaultState());
    }
    
    public void resetDoor3(int x, int y, int z)
    {
    	BlockPos doorpos = new BlockPos(x, y, z);
    	this.world.setBlockState(doorpos.add(0, 3, 1), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 3, 2), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 3, 1), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 3, 2), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 2, 1), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 2, 2), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 1, 1), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 1, 2), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 0, 1), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, 0, 2), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, -1, 1), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(1, -1, 2), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 2, 0), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 2, 3), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 1, 0), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 1, 3), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 0, 0), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, 0, 3), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, -1, 0), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    	this.world.setBlockState(doorpos.add(0, -1, 3), EMCoreBlocks.CORRUPTED_VOID_FLESH2.getDefaultState());
    }
    
    public void spawnVoidExplosion(EntityPlayer player, double x, double y, double z)
	{
		List pos = new ArrayList();
		pos.add(x);
		pos.add(y);
		pos.add(z);
		this.explosionPoints.add(pos);
		this.explosionPointTimer = _ConfigEntityCarapace.EXPLOSION_TIMER;
		this.explosionPointPlayers.add(player);
	}
    
    public void spawnAnguish(double x, double y, double z)
	{
    	EntityCarapaceAnguish entity = new EntityCarapaceAnguish(this.world, true);
       	entity.setPosition(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
       	entity.enablePersistence();
   		this.world.spawnEntity(entity);
	}
    
    public void playAttackSound()
    {
    	Random rand = new Random();
    	int r = rand.nextInt(6);
    	switch(r)
    	{
    	case 0:
    		this.world.playSound((EntityPlayer)null, this.getPosition(), EMSoundHandler.CARAPACE_ATTACK_01, SoundCategory.HOSTILE, 3.5F, 1.0f);
    		break;
    	case 1:
    		this.world.playSound((EntityPlayer)null, this.getPosition(), EMSoundHandler.CARAPACE_ATTACK_02, SoundCategory.HOSTILE, 3.5F, 1.0f);
    		break;
    	case 2:
    		this.world.playSound((EntityPlayer)null, this.getPosition(), EMSoundHandler.CARAPACE_CRITATTACK_01, SoundCategory.HOSTILE, 3.5F, 1.0f);
    		break;
    	case 3:
    		this.world.playSound((EntityPlayer)null, this.getPosition(), EMSoundHandler.CARAPACE_CRITATTACK_02, SoundCategory.HOSTILE, 3.5F, 1.0f);
    		break;
    	case 4:
    		this.world.playSound((EntityPlayer)null, this.getPosition(), EMSoundHandler.CARAPACE_CRITATTACK_03, SoundCategory.HOSTILE, 3.5F, 1.0f);
    		break;
    	case 5:
    		this.world.playSound((EntityPlayer)null, this.getPosition(), EMSoundHandler.CARAPACE_CRITATTACK_04, SoundCategory.HOSTILE, 3.5F, 1.0f);
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
    
    private float updateRotation(float angle, float targetAngle, float maxIncrease)
    {
        float f = MathHelper.wrapDegrees(targetAngle - angle);

        if (f > maxIncrease)
        {
            f = maxIncrease;
        }

        if (f < -maxIncrease)
        {
            f = -maxIncrease;
        }

        return angle + f;
    }
    
    public void playBossMusic()
    {
    	PacketHandler.INSTANCE.sendToDimension(new PacketPlayMusic(this, this.world, 0), this.dimension);
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
    
    public int getPrePhase()
    {
        return ((Integer)this.dataManager.get(PRE_PHASE)).intValue();
    }
    
    public void setPrePhase(int state)
    {
        this.dataManager.set(PRE_PHASE, Integer.valueOf(state));
    }
    
    public int getPhase()
    {
        return ((Integer)this.dataManager.get(PHASE)).intValue();
    }
    
    public void setPhase(int state)
    {
        this.dataManager.set(PHASE, Integer.valueOf(state));
    }
    
    public void setBlueBuff(boolean state)
    {
        this.dataManager.set(BLUE_BUFF, Boolean.valueOf(state));
    }

    public boolean getBlueBuffActive()
    {
        return ((Boolean)this.dataManager.get(BLUE_BUFF));
    }
    
    public void setPurpleBuff(boolean state)
    {
        this.dataManager.set(PURPLE_BUFF, Boolean.valueOf(state));
    }

    public boolean getPurpleBuffActive()
    {
        return ((Boolean)this.dataManager.get(PURPLE_BUFF));
    }
    
    public void setSpawnLocation(BlockPos pos)
    {
    	this.spawn_location = pos;
    	aiMoveToMainArea = new EntityAIMoveTo(this, 0.55D, 20, (pos.getX() - 17.5D), pos.getY() + 4, (pos.getZ()));
		aiMoveToRoom1Mid = new EntityAIMoveTo(this, 0.625D, 20, (pos.getX() - 27.5D), pos.getY() + 4, (pos.getZ()));
		aiMoveBackToStartRoom1 = new EntityAIMoveTo(this, 0.6D, 20, pos.getX() - 15.5D, pos.getY(), (pos.getZ()));
		aiMoveToSpawnLocation = new EntityAIMoveTo(this, 0.65D, 30, pos.getX(), pos.getY(), pos.getZ());
		aiMoveToSecondArea = new EntityAIMoveTo(this, 0.65D, 50, pos.getX() + 22.5, pos.getY(), pos.getZ());
		aiMoveToSecondAreaDoor = new EntityAIMoveTo(this, 0.65D, 50, pos.getX() + 36.5, pos.getY(), pos.getZ());
		aiMoveToThroughSecondDoor = new EntityAIMoveTo(this, 0.65D, 15, pos.getX() + 48.5, pos.getY(), pos.getZ());
		aiMoveToThirdRoom = new EntityAIMoveTo(this, 0.65D, 15, pos.getX() + 63.5, pos.getY(), pos.getZ());
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
    
    @Override
    protected int getExperiencePoints(EntityPlayer player)
    {
    	return 750;
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
