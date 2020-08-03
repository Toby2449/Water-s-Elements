package com.water.elementmod.entity.boss._void;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.water.elementmod.EMCorePotionEffects;
import com.water.elementmod.entity.ai.EntityAIMoveTo;
import com.water.elementmod.network.PacketCustomParticleData;
import com.water.elementmod.network.PacketHandler;
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
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public class EntityCarapace extends EntityMob
{
	private static final DataParameter<Boolean> INITIATE = EntityDataManager.<Boolean>createKey(EntityCarapace.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_INVURNERABLE = EntityDataManager.<Boolean>createKey(EntityCarapace.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> PHASE = EntityDataManager.<Integer>createKey(EntityCarapace.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> PRE_PHASE = EntityDataManager.<Integer>createKey(EntityCarapace.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> BLUE_BUFF = EntityDataManager.<Boolean>createKey(EntityCarapace.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> PURPLE_BUFF = EntityDataManager.<Boolean>createKey(EntityCarapace.class, DataSerializers.BOOLEAN);
	private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.PINK, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);
	private EntityAIMoveTo aiMoveToMainArea = null;
	private EntityAIMoveTo aiMoveToRoom1Mid = null;
	private EntityAIMoveTo aiMoveBackToStartRoom1 = null;
	private EntityAIMoveTo aiMoveToSpawnLocation = null;
	private EntityAIMoveTo aiMoveToSecondArea = null;
	private final EntityAIAttackMelee aiMeleeAttack = new EntityAIAttackMelee(this, 0.5D, true);
	private BlockPos spawn_location;
	private static List explosionPoints = new ArrayList();
	private static List explosionPointTimers = new ArrayList();
	private static List explosionPointPlayers = new ArrayList();
	
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
	private int P3Timer = _ConfigEntityCarapace.P3TIMER;
	private int BlueBuffCD = _ConfigEntityCarapace.BLUEBUFFCD;
	private int BlueBuffSoakTimer = _ConfigEntityCarapace.BLUEBUFFSOAKTIMER;
	private BlockPos BlueBuffSoakLocation = null;
	private boolean BlueBuffPosFound = false;
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
	private int P7WeaknessTimer = _ConfigEntityCarapace.P7WEAKNESSTIMER;
	private int P7WeaknessCastTimer = _ConfigEntityCarapace.P7WEAKNESSCASTTIME;
	private int P7Timer = _ConfigEntityCarapace.P7TIMER;
	private int P8CastTime = _ConfigEntityCarapace.P8CASTTIME;
	private boolean P8FacingFound = false;
	private int P8Facing = 0;
	
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
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 20.0F));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    }
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0F);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
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
						this.DarkPurpleRingAnimation((double)pos.get(0), (double)pos.get(1), (double)pos.get(2), 2, this.world);
						
						if((Integer)this.explosionPointTimers.get(i) <= 1)
						{
							this.DarkPurpleExplosionAnimation((double)pos.get(0), (double)pos.get(1), (double)pos.get(2), 2, 8, this.world);
							AxisAlignedBB AoePoint = new AxisAlignedBB((double)pos.get(0) - 0.5D, (double)pos.get(1) - 0.5D, (double)pos.get(2) - 0.5D, (double)pos.get(0) + 1.0D, (double)pos.get(1) + 1.0D, (double)pos.get(2) + 1.0D);
							List<EntityPlayer> AABBPlayer = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint);
							if (!AABBPlayer.isEmpty())
						    {
						        for (EntityPlayer ent : AABBPlayer)
						        {
						        	ent.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 150, 1));
						        }
						    }
						}
						
						this.explosionPointTimers.set(i, CircleTimer - 1);
					}
					else
					{
						this.explosionPoints.remove(i);
						this.explosionPointTimers.remove(i);
						this.explosionPointPlayers.remove(i);
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
							Double x = new Double((double)pos.get(0)); 
							int x1 = x.intValue();
							Double y = new Double((double)pos.get(1)); 
							int y1 = y.intValue();
							Double z = new Double((double)pos.get(2)); 
							int z1 = z.intValue();
							this.playImpactSound((int)x1, (int)y1, (int)z1);
							AxisAlignedBB AoePoint = new AxisAlignedBB((double)pos.get(0) - 0.5D, (double)pos.get(1) - 0.5D, (double)pos.get(2) - 0.5D, (double)pos.get(0) + 1.0D, (double)pos.get(1) + 1.0D, (double)pos.get(2) + 1.0D);
							List<EntityPlayer> AABBPlayer = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint);
							if (!AABBPlayer.isEmpty())
						    {
						        for (EntityPlayer ent : AABBPlayer)
						        {
						        	ent.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 150, 1));
						        	ent.attackEntityFrom(DamageSource.MAGIC, 1.5F);
						        }
						    }
						}
					}
					else
					{
						this.explosionPoints.remove(i);
						this.explosionPointTimers.remove(i);
						this.explosionPointPlayers.remove(i);
					}
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
    		this.setInvulState(false);
    		if(this.getHealth() != this.getMaxHealth())
    		{
    			this.setPrePhase(-1);
    			this.setPhase(1);
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
	    		
	    		if(this.getHealth() <= (this.getMaxHealth() / 10) * 6) this.setPhase(4);
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
		        
		        if(this.P2Timer <= 0 && (!this.getBlueBuffActive() || !this.getPurpleBuffActive())) // Fail safe
		        {
		        	this.P2Timer = _ConfigEntityCarapace.P2TIMER;
		        	this.P2RunToMid = _ConfigEntityCarapace.RUNTOMIDTIMER;
		        	this.P2explosionCD = _ConfigEntityCarapace.P2EXPLOSIONTIMER;
		        	this.P2OrbsSpawned = false;
		        	this.setPhase(1);
		       	}
		        
		        if(this.getHealth() <= (this.getMaxHealth() / 10) * 6) this.setPhase(4);
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
    							this.BlueBuffRingAnimation(this.BlueBuffSoakLocation.getX(), this.BlueBuffSoakLocation.getY(), this.BlueBuffSoakLocation.getZ(), 3, this.world);
    							if(!playerInAOE)
    							{
    								this.BlueBuffRingAnimation(this.BlueBuffSoakLocation.getX(), this.BlueBuffSoakLocation.getY() + 7, this.BlueBuffSoakLocation.getZ(), 3, this.world);
    								this.BlueBuffExplosionAnimation(this.BlueBuffSoakLocation.getX(), this.BlueBuffSoakLocation.getY(), this.BlueBuffSoakLocation.getZ(), 1, 7, this.world);
    							}
    						}
    					}
    					
    					if(this.BlueBuffSoakTimer <= 0)
    					{
    						boolean playerInAOE = false;
    						this.playImpactSound(this.BlueBuffSoakLocation.getX(), this.BlueBuffSoakLocation.getY(), this.BlueBuffSoakLocation.getZ());
    						AxisAlignedBB AoePoint = new AxisAlignedBB(this.BlueBuffSoakLocation.getX() - 2.6D, this.BlueBuffSoakLocation.getY() - 0.5D, this.BlueBuffSoakLocation.getZ() - 2.6D, this.BlueBuffSoakLocation.getX() + 2.6D, this.BlueBuffSoakLocation.getY() + 7.5D, this.BlueBuffSoakLocation.getZ() + 2.6D);
							List<EntityPlayer> AABBPlayer = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint);
							if (!AABBPlayer.isEmpty())
						    {
						        for (EntityPlayer ent : AABBPlayer)
						        {
						        	ent.attackEntityFrom(DamageSource.causeMobDamage(this), 20.0f);
						        	playerInAOE = true;
						        }
						    }
							if(playerInAOE)
							{
								this.BlueBuffExplosionAnimation(this.BlueBuffSoakLocation.getX(), this.BlueBuffSoakLocation.getY(), this.BlueBuffSoakLocation.getZ(), 3, 7, this.world);
							}
							else
							{
								List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
						        for (EntityPlayer entity : list)
						        {
						        	entity.attackEntityFrom(DamageSource.causeMobDamage(this), 20.0f);
						        }
								this.BlueBuffRingAnimation(this.BlueBuffSoakLocation.getX(), this.BlueBuffSoakLocation.getY(), this.BlueBuffSoakLocation.getZ(), 15, this.world);
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
    						this.PurpleBuffArea1(this.posX, this.posY, this.posZ, this.world);
    						break;
    					case 1:
    						this.PurpleBuffArea2(this.posX, this.posY, this.posZ, this.world);
    						break;
    					case 2:
    						this.PurpleBuffArea3(this.posX, this.posY, this.posZ, this.world);
    						break;
    					}
    					
    					if(this.PurpleBuffDoublePosition)
    					{
    						switch(this.PurpleBuffPosition2)
        					{
        					case 0:
        						this.PurpleBuffArea1(this.posX, this.posY, this.posZ, this.world);
        						break;
        					case 1:
        						this.PurpleBuffArea2(this.posX, this.posY, this.posZ, this.world);
        						break;
        					case 2:
        						this.PurpleBuffArea3(this.posX, this.posY, this.posZ, this.world);
        						break;
        					}
    					}
    					
    					if(this.PurpleBuffTimer <= 0)
    					{
    						switch(this.PurpleBuffPosition)
	    					{
	    					case 0:
	    						this.PurpleBuffExplode1(this.posX, this.posY, this.posZ, this.world);
	    						this.playImpactSound(this.getPosition().getX() + 12, this.getPosition().getY(), this.getPosition().getZ());
	    						AxisAlignedBB AoePoint1 = new AxisAlignedBB(this.posX + 6, this.posY, this.posZ - 15, this.posX + 15, this.posY + 20, this.posZ + 30);
								List<EntityPlayer> AABBPlayer1 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint1);
								if (!AABBPlayer1.isEmpty())
							    {
							        for (EntityPlayer ent : AABBPlayer1)
							        {
							        	ent.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100));
							        	ent.addPotionEffect(new PotionEffect(EMCorePotionEffects.POTION_CORRUPTION, 100, 0));
							        }
							    }
	    						break;
	    					case 1:
	    						this.PurpleBuffExplode2(this.posX, this.posY, this.posZ, this.world);
	    						this.playImpactSound(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
	    						AxisAlignedBB AoePoint2 = new AxisAlignedBB(this.posX - 5, this.posY, this.posZ - 15, this.posX + 6, this.posY + 20, this.posZ + 30);
								List<EntityPlayer> AABBPlayer2 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint2);
								if (!AABBPlayer2.isEmpty())
							    {
							        for (EntityPlayer ent : AABBPlayer2)
							        {
							        	ent.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100));
							        	ent.addPotionEffect(new PotionEffect(EMCorePotionEffects.POTION_CORRUPTION, 100, 0));
							        }
							    }
	    						break;
	    					case 2:
	    						this.PurpleBuffExplode3(this.posX, this.posY, this.posZ, this.world);
	    						this.playImpactSound(this.getPosition().getX() - 12, this.getPosition().getY(), this.getPosition().getZ());
	    						AxisAlignedBB AoePoint3 = new AxisAlignedBB(this.posX - 5, this.posY, this.posZ - 15, this.posX - 15, this.posY + 20, this.posZ + 30);
								List<EntityPlayer> AABBPlayer3 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint3);
								if (!AABBPlayer3.isEmpty())
							    {
							        for (EntityPlayer ent : AABBPlayer3)
							        {
							        	ent.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100));
							        	ent.addPotionEffect(new PotionEffect(EMCorePotionEffects.POTION_CORRUPTION, 100, 0));
							        }
							    }
	    						break;
	    					}
    						
    						if(this.PurpleBuffDoublePosition)
        					{
        						switch(this.PurpleBuffPosition2)
            					{
            					case 0:
            						this.PurpleBuffExplode1(this.posX, this.posY, this.posZ, this.world);
            						this.playImpactSound(this.getPosition().getX() + 12, this.getPosition().getY(), this.getPosition().getZ());
            						AxisAlignedBB AoePoint1 = new AxisAlignedBB(this.posX + 6, this.posY, this.posZ - 15, this.posX + 15, this.posY + 20, this.posZ + 30);
    								List<EntityPlayer> AABBPlayer1 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint1);
    								if (!AABBPlayer1.isEmpty())
    							    {
    							        for (EntityPlayer ent : AABBPlayer1)
    							        {
    							        	ent.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100));
    							        	ent.addPotionEffect(new PotionEffect(EMCorePotionEffects.POTION_CORRUPTION, 100, 0));
    							        }
    							    }
            						break;
            					case 1:
            						this.PurpleBuffExplode2(this.posX, this.posY, this.posZ, this.world);
            						this.playImpactSound(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
            						AxisAlignedBB AoePoint2 = new AxisAlignedBB(this.posX - 6, this.posY, this.posZ - 15, this.posX + 12, this.posY + 20, this.posZ + 30);
    								List<EntityPlayer> AABBPlayer2 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint2);
    								if (!AABBPlayer2.isEmpty())
    							    {
    							        for (EntityPlayer ent : AABBPlayer2)
    							        {
    							        	ent.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100));
    							        	ent.addPotionEffect(new PotionEffect(EMCorePotionEffects.POTION_CORRUPTION, 100, 0));
    							        }
    							    }
            						break;
            					case 2:
            						this.PurpleBuffExplode3(this.posX, this.posY, this.posZ, this.world);
            						this.playImpactSound(this.getPosition().getX() - 12, this.getPosition().getY(), this.getPosition().getZ());
            						AxisAlignedBB AoePoint3 = new AxisAlignedBB(this.posX - 5, this.posY, this.posZ - 15, this.posX - 15, this.posY + 20, this.posZ + 30);
    								List<EntityPlayer> AABBPlayer3 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint3);
    								if (!AABBPlayer3.isEmpty())
    							    {
    							        for (EntityPlayer ent : AABBPlayer3)
    							        {
    							        	ent.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100));
    							        	ent.addPotionEffect(new PotionEffect(EMCorePotionEffects.POTION_CORRUPTION, 100, 0));
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
    			
    			if(this.getHealth() <= (this.getMaxHealth() / 10) * 6) this.setPhase(4);
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
				this.heal((this.getMaxHealth() / 350) / 20);
				this.P6WeaknessTimer--;
				if(this.P6WeaknessTimer <= 0)
				{
					this.P6WeaknessCastTimer--;
					if(this.P6WeaknessCastTimer <= 0)
					{
						this.P6WeaknessTimer = _ConfigEntityCarapace.P6WEAKNESSTIMER;
						this.P6WeaknessCastTimer = _ConfigEntityCarapace.P6WEAKNESSCASTTIME;
						this.playImpactSound(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
						this.WeaknessAnimation(this.posX, this.posY, this.posZ, 15, this.world);
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
					this.setPhase(7);
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
			}
    		break;
    		
    		
    		
    	/**
         * =================================================
         * PHASE 7 
         * =================================================
        **/
    	case 7:
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
						this.playImpactSound(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
						this.WeaknessAnimation(this.posX, this.posY, this.posZ, 15, this.world);
						List<EntityPlayer> players = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
						for (EntityPlayer player : players)
				        {
							player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 140, 0));
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
    		Random rand = new Random();
    		if(!this.P8FacingFound)
    		{
    			this.P8FacingFound = true;
    			this.P8Facing = rand.nextInt(4);
    		}
    		if(!this.world.isRemote)
    		{
    			this.setInvulState(false);
    			this.P8CastTime--;
    			switch(this.P8Facing)
    			{
    			case 0:
    				this.rotationYaw = this.updateRotation(this.rotationYaw, 0.0f, 10.0f);
    				break;
    			case 1:
    				this.rotationYaw = this.updateRotation(this.rotationYaw, 90.0f, 10.0f);
    				break;
    			case 2:
    				this.rotationYaw = this.updateRotation(this.rotationYaw, 180.0f, 10.0f);
    				break;
    			case 3:
    				this.rotationYaw = this.updateRotation(this.rotationYaw, 270.0f, 10.0f);
    				break;
    			}
    			if(this.P8CastTime <= 0)
    			{
    				this.P8CastTime = _ConfigEntityCarapace.P8CASTTIME;
    				this.P8FacingFound = false;
    				this.P8Facing = 0;
    				this.setPhase(7);
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
			this.tasks.removeTask(aiMoveToRoom1Mid);
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
			break;
		}
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
			return super.attackEntityFrom(source, amount);
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
    
    public void BlueBuffRingAnimation(double x, double y, double z, double radius, World world)
	{
		Random rand = new Random();
		for(double r = 0.6D; r <= radius; r += 0.45D)
		{
			for(float i = 0.0F; i < 360.0F; i += 15.0F)
			{
				double deltaX = Math.cos(Math.toRadians(i))*r + rand.nextDouble();
				double deltaZ = -Math.sin(Math.toRadians(i))*r + rand.nextDouble();
				double finalX = x - 0.5D + deltaX;
				double finalZ = z - 0.5D + deltaZ;
			    
				PacketHandler.INSTANCE.sendToDimension(new PacketCustomParticleData(this, world, 8, finalX, y + 0.15D, finalZ, 0.0D, 0.0D, 0.0D, -1), this.dimension);
			}
		}
	}
    
    public void BlueBuffExplosionAnimation(double x, double y, double z, double radius, int height, World world)
	{
		Random rand = new Random();
		for(int m = 0; m < 20; m++)
		{
			for(double r = 0.6D; r <= radius; r += 0.45D)
			{
				for(float i = 0.0F; i < 360.0F; i += 15.0F)
				{
					double deltaX = Math.cos(Math.toRadians(i))*r + rand.nextDouble();
					double deltaZ = -Math.sin(Math.toRadians(i))*r + rand.nextDouble();
					double finalX = x - 0.5D + deltaX;
					double finalZ = z - 0.5D + deltaZ;
				    
					PacketHandler.INSTANCE.sendToDimension(new PacketCustomParticleData(this, world, 8, finalX, (y + this.rand.nextInt(height) + this.rand.nextDouble()) + 0.15D, finalZ, 0.0D, 0.0D, 0.0D, -1), this.dimension);
				}
			}
		}
	}
    
    public void DarkPurpleRingAnimation(double x, double y, double z, double radius, World world)
	{
    	Random rand = new Random();
		for(float i = 0.0F; i < 360.0F; i += 2.0F)
		{
			double deltaX = Math.cos(Math.toRadians(i))*radius;
			double deltaZ = -Math.sin(Math.toRadians(i))*radius;
			double finalX = x + deltaX;
			double finalZ = z + deltaZ;
		    
			for(double p = radius; p >= 0.0D; p -= 0.5D)
			{
				if(rand.nextInt(99) > 92)
		    	{
					PacketHandler.INSTANCE.sendToDimension(new PacketCustomParticleData(this, world, 9, finalX, y + 0.15D, finalZ, 0.0D, 0.0D, 0.0D, -1), this.dimension);
		    	}
			}
		}
	}
    
    public void DarkPurpleExplosionAnimation(double x, double y, double z, double radius, int height, World world)
	{
		Random rand = new Random();
		for(int m = 0; m < 20; m++)
		{
			for(double r = 0.6D; r <= radius; r += 0.45D)
			{
				for(float i = 0.0F; i < 360.0F; i += 15.0F)
				{
					double deltaX = Math.cos(Math.toRadians(i))*r + rand.nextDouble();
					double deltaZ = -Math.sin(Math.toRadians(i))*r + rand.nextDouble();
					double finalX = x - 0.5D + deltaX;
					double finalZ = z - 0.5D + deltaZ;
				    
					PacketHandler.INSTANCE.sendToDimension(new PacketCustomParticleData(this, world, 9, finalX, (y + this.rand.nextInt(height) + this.rand.nextDouble()) + 0.15D, finalZ, 0.0D, 0.0D, 0.0D, -1), this.dimension);
				}
			}
		}
	}
    
    public void PurpleBuffArea1(double x, double y, double z, World world)
	{
		Random rand = new Random();
		for(int m = 0; m < 200; m++)
		{
			PacketHandler.INSTANCE.sendToDimension(new PacketCustomParticleData(this, world, 6, (x + 6) + rand.nextInt(15) + rand.nextDouble(), y, (z - 15) + rand.nextInt(30) + rand.nextDouble(), 0.0D, 0.0D, 0.0D, -1), this.dimension);
		}
	}
    
    public void PurpleBuffArea2(double x, double y, double z, World world)
	{
		Random rand = new Random();
		for(int m = 0; m < 200; m++)
		{
			PacketHandler.INSTANCE.sendToDimension(new PacketCustomParticleData(this, world, 6, (x - 6) + rand.nextInt(12) + rand.nextDouble(), y, (z - 15) + rand.nextInt(30) + rand.nextDouble(), 0.0D, 0.0D, 0.0D, -1), this.dimension);
		}
	}
    
    public void PurpleBuffArea3(double x, double y, double z, World world)
	{
		Random rand = new Random();
		for(int m = 0; m < 200; m++)
		{
			PacketHandler.INSTANCE.sendToDimension(new PacketCustomParticleData(this, world, 6, (x - 6) - rand.nextInt(12) + rand.nextDouble(), y, (z - 15) + rand.nextInt(30) + rand.nextDouble(), 0.0D, 0.0D, 0.0D, -1), this.dimension);
		}
	}
	
	public void PurpleBuffExplode1(double x, double y, double z, World world)
	{
		Random rand = new Random();
		for(int m = 0; m < 6000; m++)
		{
			PacketHandler.INSTANCE.sendToDimension(new PacketCustomParticleData(this, world, 6, (x + 6) + rand.nextInt(15) + rand.nextDouble(), y + rand.nextInt(11) + rand.nextDouble(), (z - 15) + rand.nextInt(30) + rand.nextDouble(), 0.0D, 0.0D, 0.0D, -1), this.dimension);
		}
	}
	
	public void PurpleBuffExplode2(double x, double y, double z, World world)
	{
		Random rand = new Random();
		for(int m = 0; m < 6000; m++)
		{
			PacketHandler.INSTANCE.sendToDimension(new PacketCustomParticleData(this, world, 6, (x - 6) + rand.nextInt(12) + rand.nextDouble(), y + rand.nextInt(11) + rand.nextDouble(), (z - 15) + rand.nextInt(30) + rand.nextDouble(), 0.0D, 0.0D, 0.0D, -1), this.dimension);
		}
	}
	
	public void PurpleBuffExplode3(double x, double y, double z, World world)
	{
		Random rand = new Random();
		for(int m = 0; m < 6000; m++)
		{
			PacketHandler.INSTANCE.sendToDimension(new PacketCustomParticleData(this, world, 6, (x - 6) - rand.nextInt(12) + rand.nextDouble(), y + rand.nextInt(11) + rand.nextDouble(), (z - 15) + rand.nextInt(30) + rand.nextDouble(), 0.0D, 0.0D, 0.0D, -1), this.dimension);
		}
	}
	
	public void WeaknessAnimation(double x, double y, double z, double radius, World world)
	{
		Random rand = new Random();
		for(int m = 0; m < 10; m++)
		{
			for(double r = 0.6D; r <= radius; r += 0.45D)
			{
				for(float i = 0.0F; i < 360.0F; i += 15.0F)
				{
					double deltaX = Math.cos(Math.toRadians(i))*r + rand.nextDouble();
					double deltaZ = -Math.sin(Math.toRadians(i))*r + rand.nextDouble();
					double finalX = x - 0.5D + deltaX;
					double finalZ = z - 0.5D + deltaZ;
				    
					PacketHandler.INSTANCE.sendToDimension(new PacketCustomParticleData(this, world, 9, finalX, y + 0.15D, finalZ, 0.0D, 0.0D, 0.0D, -1), this.dimension);
				}
			}
		}
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
    
    public void spawnVoidExplosion(EntityPlayer player, double x, double y, double z)
	{
		List pos = new ArrayList();
		pos.add(x);
		pos.add(y);
		pos.add(z);
		this.explosionPoints.add(pos);
		this.explosionPointTimers.add(_ConfigEntityCarapace.EXPLOSION_TIMER);
		this.explosionPointPlayers.add(player);
	}
    
    public void playAttackSound()
    {
    	Random rand = new Random();
    	int r = rand.nextInt(7);
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
    	case 6:
    		this.world.playSound((EntityPlayer)null, this.getPosition(), EMSoundHandler.CARAPACE_CRITATTACK_05, SoundCategory.HOSTILE, 3.5F, 1.0f);
    		break;
    	}
    }
    
    public void playImpactSound(int x, int y, int z)
    {
    	Random rand = new Random();
    	int r = rand.nextInt(3);
    	switch(r)
    	{
    	case 0:
    		this.world.playSound((EntityPlayer)null, new BlockPos(x, y, z), EMSoundHandler.SPELL_IMPACT_01, SoundCategory.HOSTILE, 3.5F, 1.0f);
    		break;
    	case 1:
    		this.world.playSound((EntityPlayer)null, new BlockPos(x, y, z), EMSoundHandler.SPELL_IMPACT_02, SoundCategory.HOSTILE, 3.5F, 1.0f);
    		break;
    	case 2:
    		this.world.playSound((EntityPlayer)null, new BlockPos(x, y, z), EMSoundHandler.SPELL_IMPACT_03, SoundCategory.HOSTILE, 3.5F, 1.0f);
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
