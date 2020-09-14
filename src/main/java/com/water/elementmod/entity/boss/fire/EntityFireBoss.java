package com.water.elementmod.entity.boss.fire;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.water.elementmod.EMCoreItems;
import com.water.elementmod.network.PacketFlameTrail;
import com.water.elementmod.network.PacketFlameTrailSpit;
import com.water.elementmod.network.PacketHandler;
import com.water.elementmod.particle.EnumCustomParticleTypes;
import com.water.elementmod.particle.ParticleSpawner;
import com.water.elementmod.util.handlers.EMSoundHandler;

import net.minecraft.block.BlockSlab.EnumBlockHalf;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStairs.EnumHalf;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlab.EnumType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityFireBoss extends EntityMob
{
	private static final DataParameter<Boolean> INITIATE = EntityDataManager.<Boolean>createKey(EntityFireBoss.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_INVURNERABLE = EntityDataManager.<Boolean>createKey(EntityFireBoss.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> PHASE = EntityDataManager.<Integer>createKey(EntityFireBoss.class, DataSerializers.VARINT);
	private final EntityAIAttackMelee aiMeleeAttack = new EntityAIAttackMelee(this, 0.5D, true);
	private final EntityAIWander aiWander = new EntityAIWander(this, 0.75D);
	private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.RED, BossInfo.Overlay.PROGRESS)).setDarkenSky(false);
	private boolean fightActive = false;
	private boolean minionsSpawned = false;
	private BlockPos spawn_location;
	private static List crystal_locations = new ArrayList();
	private boolean arenaInitalized = false;
	
	private final int MINION_SPAWN_COOLDOWN = _ConfigEntityFireBoss.MINION_SPAWN_COOLDOWN;
	private int minionsAlive = 0;
	private int spawnMinionCooldownP1 = 0;
	private static List trailPoints = new ArrayList();
	private static List trailPointTimers = new ArrayList();
	private static List trailPointEntity = new ArrayList();
	private int spawnMinionCooldownP2 = 0;
	private int minionWaves;
	private int spawnMinionCooldownP3 = 0;
	private int guardianTimer = _ConfigEntityFireBoss.GUARDIAN_RESET_TIMER;
	private boolean spawnGurdianP4 = false;
	private int spawnMinionCooldownP5 = 0;
	private boolean spawnGurdianP6 = false;
	private int spawnMinionCooldownP7 = 0;
	private boolean spawnGurdianP8 = false;
	private int spawnMinionCooldownP9 = 0;
	private boolean spawnGurdianP10 = false;
	private int spawnMinionCooldownRage = 0;
	
	private boolean chatted1 = false;
	private boolean chatted2 = false;
	private boolean chatted3 = false;
	private boolean chatted4 = false;
	private boolean chatted5 = false;
	private boolean chatted6 = false;
	private boolean chatted7 = false;
	private boolean chatted8 = false;
	private boolean chatted9 = false;
	private boolean chatted10 = false;
	
	public EntityFireBoss(World worldIn) 
	{
		super(worldIn);
		this.isImmuneToFire = true;
		this.setSpawnLocation(this.getPosition());
		this.setSize(1.3F, 4.6F);
	}
	
	public EntityFireBoss(World worldIn, BlockPos pos) 
	{
		super(worldIn);
		this.isImmuneToFire = true;
		this.setSpawnLocation(pos);
		this.setSize(1.5F, 5.0F);
	}
	
	public static void registerFixesBoss(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntityFireBoss.class);
    }
	
	@Override
	protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(2, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    }
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(400.0F);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.75D);
	}
	
	@Override
	protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(IS_INVURNERABLE, Boolean.valueOf(true));
        this.dataManager.register(INITIATE, Boolean.valueOf(false));
        this.dataManager.register(PHASE, Integer.valueOf(0));
    }
	
	public void arenaInit()
	{
		this.crystal_locations.clear();
		List<EntityFireCrystal> list = this.world.<EntityFireCrystal>getEntitiesWithinAABB(EntityFireCrystal.class, this.getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z));
		
		for (EntityFireCrystal entity : list)
	    {
			if(this.crystal_locations.size() < 4)
			{
				Double x = new Double(entity.posX); 
				int x1 = x.intValue();
				Double y = new Double(entity.posY); 
				int y1 = y.intValue();
				Double z = new Double(entity.posZ); 
				int z1 = z.intValue();
				List pos = new ArrayList();
				pos.add(x1);
				pos.add(y1);
				pos.add(z1);
				this.crystal_locations.add(pos);
			}
	    }
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setIntArray("SpawnPos", new int[] {this.getSpawnLocation().getX(), this.getSpawnLocation().getY(), this.getSpawnLocation().getZ()});
        compound.setBoolean("Invul", this.getInvulState());
        compound.setBoolean("FightState", this.isFightActivated());
        compound.setInteger("Phase", this.getPhase());
        if(this.isFightActivated())
        {
	        if(!this.crystal_locations.isEmpty())
			{
	        	for(int i = 0; i < this.crystal_locations.size(); i++) 
	        	{
		        	ArrayList position = (ArrayList) this.crystal_locations.get(i);
		        	
		        	int x = (int) position.get(0);
		        	int y = (int) position.get(1);
		        	int z = (int) position.get(2);
		        	
		        	compound.setIntArray("Crystal_" + i, new int[] {(int)x, (int)y, (int)z});
	        	}
			}
        }
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
        if(this.isFightActivated())
        {
	        for(int i = 0; i < 3; i++)
	        {
	        	int[] compoundData = compound.getIntArray("Crystal_" + i);
		        List list = new ArrayList();
		        list.add(compoundData[0]);
		        list.add(compoundData[1]);
		        list.add(compoundData[2]);
				this.crystal_locations.add(list);
	        }
        }
		
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
	    	this.world.playBroadcastSound(1023, new BlockPos(this), 0);
	    	this.fightActive = true;
	    	this.placeDoor();
	    	this.setPhase(1);
	    	return super.processInteract(player, hand);
    	}
    	return false;
    }
	
    @Override
	public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	
    	if (this.world.isRemote)
        {
        	this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + 0.25D + this.rand.nextDouble() * ((double)this.height), this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
        	ParticleSpawner.spawnParticle(EnumCustomParticleTypes.LAVA, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + 1.7D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
        	
        	if(this.rand.nextInt(100) > 85)
        	{
        		this.world.spawnParticle(EnumParticleTypes.LAVA, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + 0.25D + this.rand.nextDouble() * (double)this.height + 0.4D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
        	}
        	
        	if(this.rand.nextInt(100) < 50)
        	{
        		this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * 1.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
            	this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + 1.25D + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
        	}
        }
        
    	if(!this.world.isRemote)
    	{
    		if(!this.trailPoints.isEmpty())
    		{
    			boolean isCurrentEntity = false;
    			for(int i = 0; i < this.trailPointEntity.size(); i++)
				{
    				if(this.trailPointEntity.get(i) == this) isCurrentEntity = true;
				}
    			if(isCurrentEntity)
    			{
	    			for(int i = 0; i < this.trailPoints.size(); i++)
					{
	    				ArrayList pos = (ArrayList)this.trailPoints.get(i);
	    				
	    				if(this.trailPoints.get(i) != null && this.trailPointEntity.get(i) != null)
						{
	    					int instanced_timer = (Integer)this.trailPointTimers.get(i);
	    					this.trailPointTimers.set(i, instanced_timer - 1);
	    					
	    					if(instanced_timer - 1 > 0)
	    					{
	    						if((Integer)this.trailPointTimers.get(i) % 8 == 0) PacketHandler.INSTANCE.sendToDimension(new PacketFlameTrail(this, this.world, (double)pos.get(0), (double)pos.get(1), (double)pos.get(2), 0.0D, 0.0D, 0.0D, 1), this.dimension);
	    						if((Integer)this.trailPointTimers.get(i) % 50 == 0) PacketHandler.INSTANCE.sendToDimension(new PacketFlameTrailSpit(this, this.world, (double)pos.get(0), (double)pos.get(1), (double)pos.get(2), 0.0D, 0.0D, 0.0D), this.dimension);
	    						
	    						AxisAlignedBB AoePoint = new AxisAlignedBB((double)pos.get(0) - 0.5D, (double)pos.get(1) - 0.5D, (double)pos.get(2) - 0.5D, (double)pos.get(0) + 1.0D, (double)pos.get(1) + 1.0D, (double)pos.get(2) + 1.0D);
	    						List<EntityPlayer> AABBPlayer = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint);
	    						if (!AABBPlayer.isEmpty())
	    						{
	    						    for (EntityPlayer ent : AABBPlayer)
	    						    {
	    						        ent.setFire(8);
	    						        if(!ent.isPotionActive(MobEffects.SLOWNESS)) 
	    						        {
	    						        	ent.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 250, 1));
	    						        }
	    						    }
	    						}
	    					}
	    					else
	    					{
	    						this.trailPoints.remove(i);
	    						this.trailPointTimers.remove(i);
	    						this.trailPointEntity.remove(i);
	    					}
						}
					}
    			}
    		}
    	}
        
        
        // Phases and fight mechanics
	    if(this.isFightActivated())
	    {
	    	switch(this.getPhase())
	    	{
	        case 1: // Phase 1
	        	if(!this.world.isRemote) // Server
	        	{
		        	this.setInvulState(false);
		        	
		        	if(this.ticksExisted % 10 == 1) this.spawnTrailNode(this.posX, this.posY, this.posZ, this);
		        	
		        	this.minionsAlive = 0;
		        	List<EntityFireBossMinion> list = this.world.<EntityFireBossMinion>getEntitiesWithinAABB(EntityFireBossMinion.class, this.getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z));
			        
			        for (EntityFireBossMinion entity : list)
			        {
			        	this.minionsAlive++;
			        }
			        
			        if(this.minionsAlive == 0)
			        {
			        	if(this.spawnMinionCooldownP1 == 0)
			        	{
			        		this.spawnMinionCooldownP1 = _ConfigEntityFireBoss.MINION_SPAWN_COOLDOWN;
			        		int i = 0;
			        		if(this.world.getDifficulty() == EnumDifficulty.EASY) i = _ConfigEntityFireBoss.EASY_MINION_COUNT;
			        		if(this.world.getDifficulty() == EnumDifficulty.NORMAL) i = _ConfigEntityFireBoss.NORMAL_MINION_COUNT;
			        		if(this.world.getDifficulty() == EnumDifficulty.HARD) i = _ConfigEntityFireBoss.HARD_MINION_COUNT;
			        		for(int j = 0; j <= i; j++)
			        		{
				        		EntityFireBossMinion entity = new EntityFireBossMinion(this.world);
						       	entity.setPosition(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
						   		this.world.spawnEntity(entity);
			        		}
			        	}
			        	this.spawnMinionCooldownP1--;
	        		}
		        	
		        	// Goto next phase
		        	if(this.getHealth() <= this.getMaxHealth() - (this.getMaxHealth() / 10)) // 9/10
		        	{
		        		this.setPhase(2);
		        	}
	        	}
	        	break;
	        		
	        		
	        		
	        		
	        case 2: // Phase 2
	        	if(!this.world.isRemote) // Server
	        	{
		        	this.setInvulState(true);
		        	
		        	this.minionsAlive = 0;
		        	List<EntityFireBossMinion> list = this.world.<EntityFireBossMinion>getEntitiesWithinAABB(EntityFireBossMinion.class, this.getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z));
			        
			        for (EntityFireBossMinion entity : list)
			        {
			        	this.minionsAlive++;
			        }
		        	
			        if(this.minionWaves <= _ConfigEntityFireBoss.MINION_WAVES)
			        {
				        if(this.minionsAlive == 0)
				        {
				        	if(this.spawnMinionCooldownP2 <= 0)
				        	{
				        		this.spawnMinionCooldownP2 = _ConfigEntityFireBoss.MINION_SPAWN_COOLDOWN;
				        		List<EntityFireCrystal> list2 = this.world.<EntityFireCrystal>getEntitiesWithinAABB(EntityFireCrystal.class, this.getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z));
						        
						        for (EntityFireCrystal crystal : list2)
						        {
						        	BlockPos position = crystal.getPosition();
			    				    
			    			      	EntityFireBossMinion entity = new EntityFireBossMinion(this.world);
			    				    entity.enablePersistence();
			    				    entity.setPosition(position.getX() + 0.5D, position.getY(), position.getZ() + 0.5D);
			    				   	this.world.spawnEntity(entity);
						        }
						        this.minionWaves++;
				        	}
				        	this.spawnMinionCooldownP2--;
				        }
			        }
			        
			        if(this.minionWaves == _ConfigEntityFireBoss.MINION_WAVES)
			        {
			        	this.minionWaves = 0;
			        	this.setPhase(3);
			        }
	        	}
	        	else
	        	{
	        		if(!this.chatted1)
	        		{
	        			this.chatted1 = true;
	        			this.sayChatLine(1);
	        		}
	        	}
	        	break;
	        		
	        		
	        		
	        case 3: // Phase 3
	        	if(!this.world.isRemote) // Server
	        	{
		        	this.setInvulState(false);
		        	
		        	if(this.ticksExisted % 10 == 1) this.spawnTrailNode(this.posX, this.posY, this.posZ, this);
		        	
		        	this.minionsAlive = 0;
		        	List<EntityFireBossMinion> list = this.world.<EntityFireBossMinion>getEntitiesWithinAABB(EntityFireBossMinion.class, this.getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z).offset(0, -1, 0));
			        
			        for (EntityFireBossMinion entity : list)
			        {
			        	this.minionsAlive++;
			        }
			        
			        if(this.minionsAlive == 0)
			        {
			        	if(this.spawnMinionCooldownP3 == 0)
			        	{
			        		this.spawnMinionCooldownP3 = _ConfigEntityFireBoss.MINION_SPAWN_COOLDOWN;
			        		int i = 0;
			        		if(this.world.getDifficulty() == EnumDifficulty.EASY) i = _ConfigEntityFireBoss.EASY_MINION_COUNT;
			        		if(this.world.getDifficulty() == EnumDifficulty.NORMAL) i = _ConfigEntityFireBoss.NORMAL_MINION_COUNT;
			        		if(this.world.getDifficulty() == EnumDifficulty.HARD) i = _ConfigEntityFireBoss.HARD_MINION_COUNT;
			        		for(int j = 0; j <= i; j++)
			        		{
				        		EntityFireBossMinion entity = new EntityFireBossMinion(this.world);
						       	entity.setPosition(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
						   		this.world.spawnEntity(entity);
			        		}
			        	}
			        	this.spawnMinionCooldownP3--;
	        		}
		        	
		        	// Goto next phase
		        	if(this.getHealth() <= this.getMaxHealth() - (this.getMaxHealth() / 4)) // 3/4
		        	{
		        		this.setPhase(4);
		        	}
	        	}
	        	else
	        	{
	        		if(!this.chatted2)
	        		{
	        			this.chatted2 = true;
	        			this.sayChatLine(2);
	        		}
	        	}
	        	break;
	        
	        	
	        	
	        case 4: // Phase 4
	        	if(!this.world.isRemote) // Server
	        	{
		        	this.setInvulState(true);
		        	
		        	if(!this.spawnGurdianP4)
		        	{
		        		this.spawnGurdianP4 = true;
		        		int crystalsInArena = 0;
		        		int randomnumber = this.rand.nextInt(3);
		        		List<EntityFireCrystal> list = this.world.<EntityFireCrystal>getEntitiesWithinAABB(EntityFireCrystal.class, this.getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z));
				        
				        for (EntityFireCrystal crystalFound : list)
				        {
				        	crystalsInArena++;
				        	if(crystalsInArena - 1 == randomnumber)
				        	{
				        		crystalFound.Explode();
				        		
				        		EntityFireGuardian entity = new EntityFireGuardian(this.world, this.posX, this.posY, this.posZ);
						       	entity.setPosition(crystalFound.getPosition().getX() + 0.5D, crystalFound.getPosition().getY(), crystalFound.getPosition().getZ() + 0.5D);
						   		this.world.spawnEntity(entity);
				        	}
				        }
		        	}
		        	
		        	boolean guardianInRadius = false;
		        	List<EntityFireGuardian> list = this.world.<EntityFireGuardian>getEntitiesWithinAABB(EntityFireGuardian.class, this.getEntityBoundingBox().grow(1.5D, 11, 1.5D));
			        
		        	for (EntityFireGuardian guardians : list)
			        {
		        		guardianInRadius = true;
			        }
		        	
		        	this.guardianTimer--;
		        	if(this.guardianTimer <= 0 || guardianInRadius)
		        	{
		        		this.resetFight();
		        		this.fightActive = false;
		        	}
		        	
		        	int guardiansLeft = 0;
		        	List<EntityFireGuardian> list1 = this.world.<EntityFireGuardian>getEntitiesWithinAABB(EntityFireGuardian.class, this.getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z));
			        
			        for (EntityFireGuardian guardians : list1)
			        {
			        	guardiansLeft++;
			        }
			        
			        if(guardiansLeft == 0) 
			        {
			        	this.guardianTimer = _ConfigEntityFireBoss.GUARDIAN_RESET_TIMER;
			        	this.setPhase(5);
			        }
	        	}
	        	else // Client
	        	{
	        		if(!this.chatted3)
	        		{
	        			this.chatted3 = true;
	        			this.sayChatLine(3);
	        		}
	        		
	        		if(this.ticksExisted % 32 == 1)
	        		{
	        			this.FireRingAnimation(this.getPosition().getX() + 0.5D, this.getPosition().getY(), this.getPosition().getZ() + 0.5D, 3, this.world);
	        		}
	        	}
	        	break;
	        
	        case 5: // Phase 5
	        	if(!this.world.isRemote) // Server
	        	{
		        	this.setInvulState(false);
		        	
		        	if(this.ticksExisted % 10 == 1) this.spawnTrailNode(this.posX, this.posY, this.posZ, this);
		        	
		        	this.minionsAlive = 0;
		        	List<EntityFireBossMinion> list = this.world.<EntityFireBossMinion>getEntitiesWithinAABB(EntityFireBossMinion.class, this.getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z));
			        
			        for (EntityFireBossMinion entity : list)
			        {
			        	this.minionsAlive++;
			        }
			        
			        if(this.minionsAlive == 0)
			        {
			        	if(this.spawnMinionCooldownP5 == 0)
			        	{
			        		this.spawnMinionCooldownP5 = _ConfigEntityFireBoss.MINION_SPAWN_COOLDOWN;
			        		int i = 0;
			        		if(this.world.getDifficulty() == EnumDifficulty.EASY) i = _ConfigEntityFireBoss.EASY_MINION_COUNT;
			        		if(this.world.getDifficulty() == EnumDifficulty.NORMAL) i = _ConfigEntityFireBoss.NORMAL_MINION_COUNT;
			        		if(this.world.getDifficulty() == EnumDifficulty.HARD) i = _ConfigEntityFireBoss.HARD_MINION_COUNT;
			        		for(int j = 0; j <= i; j++)
			        		{
				        		EntityFireBossMinion entity = new EntityFireBossMinion(this.world);
						       	entity.setPosition(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
						   		this.world.spawnEntity(entity);
			        		}
			        	}
			        	this.spawnMinionCooldownP5--;
	        		}
		        	
		        	// Goto next phase
		        	if(this.getHealth() <= (this.getMaxHealth() / 5) * 3) // 3/5
		        	{
		        		this.setPhase(6);
		        	}
	        	}
	        	else
	        	{
	        		if(!this.chatted4)
	        		{
	        			this.chatted4 = true;
	        			this.sayChatLine(7);
	        		}
	        	}
	        	break;	
	        
	        case 6: // Phase 6
	        	if(!this.world.isRemote) // Server
	        	{
		        	this.setInvulState(true);
		        	
		        	if(!this.spawnGurdianP6)
		        	{
		        		this.spawnGurdianP6 = true;
		        		int crystalsInArena = 0;
		        		int randomnumber = this.rand.nextInt(2);
		        		List<EntityFireCrystal> list = this.world.<EntityFireCrystal>getEntitiesWithinAABB(EntityFireCrystal.class, this.getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z));
				        
				        for (EntityFireCrystal crystalFound : list)
				        {
				        	crystalsInArena++;
				        	if(crystalsInArena - 1 == randomnumber)
				        	{
				        		crystalFound.Explode();
				        		
				        		EntityFireGuardian entity = new EntityFireGuardian(this.world, this.posX, this.posY, this.posZ);
						       	entity.setPosition(crystalFound.getPosition().getX() + 0.5D, crystalFound.getPosition().getY(), crystalFound.getPosition().getZ() + 0.5D);
						   		this.world.spawnEntity(entity);
				        	}
				        }
		        	}
		        	
		        	boolean guardianInRadius = false;
		        	List<EntityFireGuardian> list = this.world.<EntityFireGuardian>getEntitiesWithinAABB(EntityFireGuardian.class, this.getEntityBoundingBox().grow(1.5D, 11, 1.5D));
			        
		        	for (EntityFireGuardian guardians : list)
			        {
		        		guardianInRadius = true;
			        }
		        	
		        	this.guardianTimer--;
		        	if(this.guardianTimer <= 0 || guardianInRadius)
		        	{
		        		this.resetFight();
		        		this.fightActive = false;
		        	}
		        	
		        	int guardiansLeft = 0;
		        	List<EntityFireGuardian> list1 = this.world.<EntityFireGuardian>getEntitiesWithinAABB(EntityFireGuardian.class, this.getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z));
			        
			        for (EntityFireGuardian guardians : list1)
			        {
			        	guardiansLeft++;
			        }
			        
			        if(guardiansLeft == 0) 
			        {
			        	this.guardianTimer = _ConfigEntityFireBoss.GUARDIAN_RESET_TIMER;
			        	this.setPhase(7);
			        }
	        	}
	        	else // Client
	        	{
	        		if(!this.chatted5)
	        		{
	        			this.chatted5 = true;
	        			this.sayChatLine(4);
	        		}
	        		
	        		if(this.ticksExisted % 32 == 1)
	        		{
	        			this.FireRingAnimation(this.getPosition().getX() + 0.5D, this.getPosition().getY(), this.getPosition().getZ() + 0.5D, 3, this.world);
	        		}
	        	}
	        	break;
	        	
	        case 7:
	        	if(!this.world.isRemote) // Server
	        	{
		        	this.setInvulState(false);
		        	
		        	if(this.ticksExisted % 10 == 1) this.spawnTrailNode(this.posX, this.posY, this.posZ, this);
		        	
		        	this.minionsAlive = 0;
		        	List<EntityFireBossMinion> list = this.world.<EntityFireBossMinion>getEntitiesWithinAABB(EntityFireBossMinion.class, this.getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z));
			        
			        for (EntityFireBossMinion entity : list)
			        {
			        	this.minionsAlive++;
			        }
			        
			        if(this.minionsAlive == 0)
			        {
			        	if(this.spawnMinionCooldownP7 == 0)
			        	{
			        		this.spawnMinionCooldownP7 = _ConfigEntityFireBoss.MINION_SPAWN_COOLDOWN;
			        		int i = 0;
			        		if(this.world.getDifficulty() == EnumDifficulty.EASY) i = _ConfigEntityFireBoss.EASY_MINION_COUNT;
			        		if(this.world.getDifficulty() == EnumDifficulty.NORMAL) i = _ConfigEntityFireBoss.NORMAL_MINION_COUNT;
			        		if(this.world.getDifficulty() == EnumDifficulty.HARD) i = _ConfigEntityFireBoss.HARD_MINION_COUNT;
			        		for(int j = 0; j <= i; j++)
			        		{
				        		EntityFireBossMinion entity = new EntityFireBossMinion(this.world);
						       	entity.setPosition(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
						   		this.world.spawnEntity(entity);
			        		}
			        	}
			        	this.spawnMinionCooldownP7--;
	        		}
		        	
		        	// Goto next phase
			        if(this.getHealth() <= (this.getMaxHealth() / 5) * 2) // 2/5
		        	{
		        		this.setPhase(8);
		        	}
	        	}
	        	else
	        	{
	        		if(!this.chatted6)
	        		{
	        			this.chatted6 = true;
	        			this.sayChatLine(8);
	        		}
	        	}
	        	break;
	        	
	        
	        case 8: // Phase 8
	        	if(!this.world.isRemote) // Server
	        	{
		        	this.setInvulState(true);
		        	
		        	if(!this.spawnGurdianP8)
		        	{
		        		this.spawnGurdianP8 = true;
		        		int crystalsInArena = 0;
		        		int randomnumber = this.rand.nextInt(1);
		        		List<EntityFireCrystal> list = this.world.<EntityFireCrystal>getEntitiesWithinAABB(EntityFireCrystal.class, this.getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z));
				        
				        for (EntityFireCrystal crystalFound : list)
				        {
				        	crystalsInArena++;
				        	if(crystalsInArena - 1 == randomnumber)
				        	{
				        		crystalFound.Explode();
				        		
				        		EntityFireGuardian entity = new EntityFireGuardian(this.world, this.posX, this.posY, this.posZ);
						       	entity.setPosition(crystalFound.getPosition().getX() + 0.5D, crystalFound.getPosition().getY(), crystalFound.getPosition().getZ() + 0.5D);
						   		this.world.spawnEntity(entity);
				        	}
				        }
		        	}
		        	
		        	boolean guardianInRadius = false;
		        	List<EntityFireGuardian> list = this.world.<EntityFireGuardian>getEntitiesWithinAABB(EntityFireGuardian.class, this.getEntityBoundingBox().grow(1.5D, 11, 1.5D));
			        
		        	for (EntityFireGuardian guardians : list)
			        {
		        		guardianInRadius = true;
			        }
		        	
		        	this.guardianTimer--;
		        	if(this.guardianTimer <= 0 || guardianInRadius)
		        	{
		        		this.resetFight();
		        		this.fightActive = false;
		        	}
		        	
		        	int guardiansLeft = 0;
		        	List<EntityFireGuardian> list1 = this.world.<EntityFireGuardian>getEntitiesWithinAABB(EntityFireGuardian.class, this.getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z));
			        
			        for (EntityFireGuardian guardians : list1)
			        {
			        	guardiansLeft++;
			        }
			        
			        if(guardiansLeft == 0) 
			        {
			        	this.guardianTimer = _ConfigEntityFireBoss.GUARDIAN_RESET_TIMER;
			        	this.setPhase(9);
			        }
	        	}
	        	else // Client
	        	{
	        		if(!this.chatted7)
	        		{
	        			this.chatted7 = true;
	        			this.sayChatLine(5);
	        		}
	        		
	        		if(this.ticksExisted % 32 == 1)
	        		{
	        			this.FireRingAnimation(this.getPosition().getX() + 0.5D, this.getPosition().getY(), this.getPosition().getZ() + 0.5D, 3, this.world);
	        		}
	        	}
	        	break;
	        
	        case 9: // Phase 5
	        	if(!this.world.isRemote) // Server
	        	{
		        	this.setInvulState(false);
		        	
		        	if(this.ticksExisted % 10 == 1) this.spawnTrailNode(this.posX, this.posY, this.posZ, this);
		        	
		        	this.minionsAlive = 0;
		        	List<EntityFireBossMinion> list = this.world.<EntityFireBossMinion>getEntitiesWithinAABB(EntityFireBossMinion.class, this.getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z));
			        
			        for (EntityFireBossMinion entity : list)
			        {
			        	this.minionsAlive++;
			        }
			        
			        if(this.minionsAlive == 0)
			        {
			        	if(this.spawnMinionCooldownP9 == 0)
			        	{
			        		this.spawnMinionCooldownP9 = _ConfigEntityFireBoss.MINION_SPAWN_COOLDOWN;
			        		int i = 0;
			        		if(this.world.getDifficulty() == EnumDifficulty.EASY) i = _ConfigEntityFireBoss.EASY_MINION_COUNT;
			        		if(this.world.getDifficulty() == EnumDifficulty.NORMAL) i = _ConfigEntityFireBoss.NORMAL_MINION_COUNT;
			        		if(this.world.getDifficulty() == EnumDifficulty.HARD) i = _ConfigEntityFireBoss.HARD_MINION_COUNT;
			        		for(int j = 0; j <= i; j++)
			        		{
				        		EntityFireBossMinion entity = new EntityFireBossMinion(this.world);
						       	entity.setPosition(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
						   		this.world.spawnEntity(entity);
			        		}
			        	}
			        	this.spawnMinionCooldownP9--;
	        		}
		        	
		        	// Goto next phase
		        	if(this.getHealth() <= this.getMaxHealth() / 5) // 1/5
		        	{
		        		this.setPhase(10);
		        	}
	        	}
	        	else
	        	{
	        		if(!this.chatted8)
	        		{
	        			this.chatted8 = true;
	        			this.sayChatLine(9);
	        		}
	        	}
	        	break;	
	        	
	        case 10: // Phase 10
	        	if(!this.world.isRemote) // Server
	        	{
		        	this.setInvulState(true);
		        	
		        	if(!this.spawnGurdianP10)
		        	{
		        		this.spawnGurdianP10 = true;
		        		List<EntityFireCrystal> list = this.world.<EntityFireCrystal>getEntitiesWithinAABB(EntityFireCrystal.class, this.getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z));
				        
				        for (EntityFireCrystal crystalFound : list)
				        {
				        	crystalFound.Explode();
			        		
			        		EntityFireGuardian entity = new EntityFireGuardian(this.world, this.posX, this.posY, this.posZ);
					       	entity.setPosition(crystalFound.getPosition().getX() + 0.5D, crystalFound.getPosition().getY(), crystalFound.getPosition().getZ() + 0.5D);
					   		this.world.spawnEntity(entity);
				        }
		        	}
		        	
		        	boolean guardianInRadius = false;
		        	List<EntityFireGuardian> list = this.world.<EntityFireGuardian>getEntitiesWithinAABB(EntityFireGuardian.class, this.getEntityBoundingBox().grow(1.5D, 11, 1.5D));
			        
		        	for (EntityFireGuardian guardians : list)
			        {
		        		guardianInRadius = true;
			        }
		        	
		        	this.guardianTimer--;
		        	if(this.guardianTimer <= 0 || guardianInRadius)
		        	{
		        		this.resetFight();
		        		this.fightActive = false;
		        	}
		        	
		        	int guardiansLeft = 0;
		        	List<EntityFireGuardian> list1 = this.world.<EntityFireGuardian>getEntitiesWithinAABB(EntityFireGuardian.class, this.getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z));
			        
			        for (EntityFireGuardian guardians : list1)
			        {
			        	guardiansLeft++;
			        }
		        	
		        	if(guardiansLeft == 0) 
			        {
			        	this.guardianTimer = _ConfigEntityFireBoss.GUARDIAN_RESET_TIMER;
			        	this.setPhase(11);
			        }
	        	}
	        	else // Client
	        	{
	        		if(!this.chatted9)
	        		{
	        			this.chatted9 = true;
	        			this.sayChatLine(6);
	        		}
	        		
	        		if(this.ticksExisted % 32 == 1)
	        		{
	        			this.FireRingAnimation(this.getPosition().getX() + 0.5D, this.getPosition().getY(), this.getPosition().getZ() + 0.5D, 3, this.world);
	        		}
	        	}
	        	break;
	        	
	        case 11: // Phase 11
	        	if(!this.world.isRemote) // Server
	        	{
		        	this.setInvulState(false);
		        	
		        	if(this.ticksExisted % 10 == 1) this.spawnTrailNode(this.posX, this.posY, this.posZ, this);
		        	
		        	this.minionsAlive = 0;
		        	List<EntityFireBossMinion> list = this.world.<EntityFireBossMinion>getEntitiesWithinAABB(EntityFireBossMinion.class, this.getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z));
			        
			        for (EntityFireBossMinion entity : list)
			        {
			        	this.minionsAlive++;
			        }
			        
			        if(this.minionsAlive == 0)
			        {
			        	if(this.spawnMinionCooldownRage == 0)
			        	{
			        		this.spawnMinionCooldownRage = _ConfigEntityFireBoss.MINION_SPAWN_COOLDOWN;
			        		int i = 0;
			        		if(this.world.getDifficulty() == EnumDifficulty.EASY) i = _ConfigEntityFireBoss.EASY_RAGE_MINION_COUNT;
			        		if(this.world.getDifficulty() == EnumDifficulty.NORMAL) i = _ConfigEntityFireBoss.NORMAL_RAGE_MINION_COUNT;
			        		if(this.world.getDifficulty() == EnumDifficulty.HARD) i = _ConfigEntityFireBoss.HARD_RAGE_MINION_COUNT;
			        		for(int j = 0; j <= i; j++)
			        		{
				        		EntityFireBossMinion entity = new EntityFireBossMinion(this.world);
						       	entity.setPosition(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
						   		this.world.spawnEntity(entity);
			        		}
			        	}
			        	this.spawnMinionCooldownRage--;
	        		}
	        	}
	        	else
	        	{
	        		if(!this.chatted10)
	        		{
	        			this.chatted10 = true;
	        			this.sayChatLine(10);
	        		}
	        	}
	        	break;
	        }
	    	
	    	if(!this.world.isRemote)
	    	{
		    	// If nobodies in the arena
		        if(this.isFightActivated() && this.fightActive)
		        {
		        	List<EntityPlayer> players = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z));
			        
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
	    }
		
		this.ticksExisted++;
        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }
	
	@Override
	protected void updateAITasks()
    {
		super.updateAITasks();
		
		if(!this.isFightActivated())
		{
			this.tasks.removeTask(this.aiMeleeAttack);
    		this.tasks.removeTask(this.aiWander);
    		if(this.getSpawnLocation() != null)
    		{
    			this.setPosition(this.getSpawnLocation().getX() + 0.5D, this.getSpawnLocation().getY(), this.getSpawnLocation().getZ() + 0.5D);
    		}
		}
		
		switch(this.getPhase())
    	{
    	case 1: // Phase 1
    		this.tasks.addTask(0, this.aiMeleeAttack);
    		this.tasks.addTask(1, aiWander);
    		break;
    	case 2: // Phase 2
    		this.tasks.removeTask(this.aiMeleeAttack);
    		this.tasks.removeTask(this.aiWander);
    		if(this.getSpawnLocation() != null)
    		{
    			this.setPosition(this.getSpawnLocation().getX() + 0.5D, this.getSpawnLocation().getY(), this.getSpawnLocation().getZ() + 0.5D);
    		}
    		break;
    	case 3: // Phase 3
    		this.tasks.addTask(0, this.aiMeleeAttack);
    		this.tasks.addTask(1, aiWander);
    		break;
    	case 4: // Phase 4
    		this.tasks.removeTask(this.aiMeleeAttack);
    		this.tasks.removeTask(this.aiWander);
    		if(this.getSpawnLocation() != null)
    		{
    			this.setPosition(this.getSpawnLocation().getX() + 0.5D, this.getSpawnLocation().getY(), this.getSpawnLocation().getZ() + 0.5D);
    		}
    		break;
    	case 5: // Phase 5
    		this.tasks.addTask(0, this.aiMeleeAttack);
    		this.tasks.addTask(1, aiWander);
    		break;
    	case 6: // Phase 6
    		this.tasks.removeTask(this.aiMeleeAttack);
    		this.tasks.removeTask(this.aiWander);
    		if(this.getSpawnLocation() != null)
    		{
    			this.setPosition(this.getSpawnLocation().getX() + 0.5D, this.getSpawnLocation().getY(), this.getSpawnLocation().getZ() + 0.5D);
    		}
    		break;
    	case 7: // Phase 7
    		this.tasks.addTask(0, this.aiMeleeAttack);
    		this.tasks.addTask(1, aiWander);
    		break;
    	case 8: // Phase 8
    		this.tasks.removeTask(this.aiMeleeAttack);
    		this.tasks.removeTask(this.aiWander);
    		if(this.getSpawnLocation() != null)
    		{
    			this.setPosition(this.getSpawnLocation().getX() + 0.5D, this.getSpawnLocation().getY(), this.getSpawnLocation().getZ() + 0.5D);
    		}
    		break;
    	case 9: // Phase 9
    		this.tasks.addTask(0, this.aiMeleeAttack);
    		this.tasks.addTask(1, aiWander);
    		break;
    	case 10: // Phase 10
    		this.tasks.removeTask(this.aiMeleeAttack);
    		this.tasks.removeTask(this.aiWander);
    		if(this.getSpawnLocation() != null)
    		{
    			this.setPosition(this.getSpawnLocation().getX() + 0.5D, this.getSpawnLocation().getY(), this.getSpawnLocation().getZ() + 0.5D);
    		}
    		break;
    	case 11: // Rage
    		this.tasks.addTask(0, this.aiMeleeAttack);
    		this.tasks.addTask(1, aiWander);
    		break;
    	}
    }
	
	public void spawnTrailNode(double x, double y, double z, EntityFireBoss entity)
	{
		List pos = new ArrayList();
		pos.add(x);
		pos.add(y);
		pos.add(z);
		this.trailPoints.add(pos);
		this.trailPointTimers.add(_ConfigEntityFireBoss.FIRE_TRAIL_DESPAWN); // 1 Minute
		this.trailPointEntity.add(entity);
	}
	
	public void FireTrail(double x, double y, double z, double radius, World world)
	{
		Random rand = new Random();
		for(double r = 0.6D; r <= radius; r += 0.45D)
		{
			for(float i = 0.0F; i < 360.0F; i += 150.0F)
			{
				double deltaX = Math.cos(Math.toRadians(i))*r + rand.nextDouble();
				double deltaZ = -Math.sin(Math.toRadians(i))*r + rand.nextDouble();
				double finalX = x - 0.5D + deltaX;
				double finalZ = z - 0.5D + deltaZ;
			    
				world.spawnParticle(EnumParticleTypes.FLAME, finalX, y + 0.15D, finalZ, 0.0D, 0.0D, 0.0D);
			}
		}
	}
	
	public void FireSpitAnimation(double x, double y, double z, World world)
	{
		world.spawnParticle(EnumParticleTypes.LAVA, x, y + 0.15D, z, 0.0D, 0.0D, 0.0D);
	}
	
	public void FireRingAnimation(double x, double y, double z, double radius, World world)
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
			    
				world.spawnParticle(EnumParticleTypes.FLAME, finalX, y + 0.15D, finalZ, 0.0D, 0.0D, 0.0D);
			}
		}
	}
	
	public void resetFight()
    {
    	this.setFightState(false);
    	this.setInvulState(true);
    	this.setPhase(0);
    	this.minionsAlive = 0;
    	this.spawnMinionCooldownP1 = 0;
    	this.trailPoints.clear();
    	this.trailPointTimers.clear();
    	this.trailPointEntity.clear();
    	this.spawnMinionCooldownP2 = 0;
    	this.minionWaves = 0;
    	this.spawnMinionCooldownP3 = 0;
    	this.guardianTimer = _ConfigEntityFireBoss.GUARDIAN_RESET_TIMER;
    	this.spawnGurdianP4 = false;
    	this.spawnMinionCooldownP5 = 0;
    	this.spawnGurdianP6 = false;
    	this.spawnMinionCooldownP7 = 0;
    	this.spawnGurdianP8 = false;
    	this.spawnMinionCooldownP9 = 0;
    	this.spawnGurdianP10 = false;
    	this.spawnMinionCooldownRage = 0;
    	this.chatted1 = false;
    	this.chatted2 = false;
    	this.chatted3 = false;
    	this.chatted4 = false;
    	this.chatted5 = false;
    	this.chatted6 = false;
    	this.chatted7 = false;
    	this.chatted8 = false;
    	this.chatted9 = false;
    	this.chatted10 = false;
    	this.openDoor();
    	
    	List<EntityFireCrystal> list = this.world.<EntityFireCrystal>getEntitiesWithinAABB(EntityFireCrystal.class, this.getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z));
    	
    	if(!list.isEmpty())
    	{
	    	for (EntityFireCrystal entity : list)
	        {
	    		entity.setDead();
	        }
    	}
    	
    	List<EntityFireBossMinion> list1 = this.world.<EntityFireBossMinion>getEntitiesWithinAABB(EntityFireBossMinion.class, this.getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z));
    	
    	if(!list1.isEmpty())
    	{
	    	for (EntityFireBossMinion entity : list1)
	        {
	    		entity.setDead();
	        }
    	}
    	
    	List<EntityFireGuardian> list2 = this.world.<EntityFireGuardian>getEntitiesWithinAABB(EntityFireGuardian.class, this.getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z));
    	
    	if(!list2.isEmpty())
    	{
	    	for (EntityFireGuardian entity : list2)
	        {
	    		entity.setDead();
	        }
    	}
    	
    	if(!this.crystal_locations.isEmpty())
		{
        	for(int l = 0; l < this.crystal_locations.size(); l++)
        	{
	        	ArrayList position = (ArrayList) this.crystal_locations.get(l);
	        	
	        	EntityFireCrystal entity = new EntityFireCrystal(this.world, (int)position.get(0) - 0.5D, (int)position.get(1) + 1.0D, (int)position.get(2) - 0.5D);
	        	entity.enablePersistence();
	            this.world.spawnEntity(entity);
        	}
		}
    }
	
	public void sayChatLine(int message)
	{
		if(this.world.isRemote)
		{
			List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z));
	    	
	    	if(!list.isEmpty())
	    	{
		    	for (EntityPlayer player : list)
		        {
		    		switch(message)
		    		{
		    		case 0:
		    			player.sendMessage(new TextComponentTranslation("message.em.fireboss.fight_initiate"));
		    			break;
		    		case 1:
		    			player.sendMessage(new TextComponentTranslation("message.em.fireboss.chat1"));
		    			break;
		    		case 2:
		    			player.sendMessage(new TextComponentTranslation("message.em.fireboss.chat2"));
		    			break;
		    		case 3:
		    			player.sendMessage(new TextComponentTranslation("message.em.fireboss.chat3"));
		    			break;
		    		case 4:
		    			player.sendMessage(new TextComponentTranslation("message.em.fireboss.chat4"));
		    			break;
		    		case 5:
		    			player.sendMessage(new TextComponentTranslation("message.em.fireboss.chat5"));
		    			break;
		    		case 6:
		    			player.sendMessage(new TextComponentTranslation("message.em.fireboss.chat6"));
		    			break;
		    		case 7:
		    			player.sendMessage(new TextComponentTranslation("message.em.fireboss.chat7"));
		    			break;
		    		case 8:
		    			player.sendMessage(new TextComponentTranslation("message.em.fireboss.chat8"));
		    			break;
		    		case 9:
		    			player.sendMessage(new TextComponentTranslation("message.em.fireboss.chat9"));
		    			break;
		    		case 10:
		    			player.sendMessage(new TextComponentTranslation("message.em.fireboss.chat10"));
		    			break;
		    		case 11:
		    			player.sendMessage(new TextComponentTranslation("message.em.fireboss.chat11"));
		    			break;
		    		case 12:
		    			player.sendMessage(new TextComponentTranslation("message.em.fireboss.voidentitydeathmessagefire"));
		    			break;
		    		}
		        }
	    	}
		}
	}
	
	@Override
	public float getEyeHeight()
	{
		return 4.15F;
	}
	
	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_BLAZE_AMBIENT;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundEvents.ENTITY_BLAZE_HURT;
	}
	
	@Override
	protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_BLAZE_DEATH;
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
    		return super.attackEntityFrom(source, amount);
    	}
	    else
	    {
	    	return false;
	    }
    }
	
	@Override
	public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = super.attackEntityAsMob(entityIn);

        if (flag)
        {
            float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();

            entityIn.setFire(2 * (int)f);
        }
        
        entityIn.setFire(3);
        return flag;
    }
    
    @Override
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);
        
        this.sayChatLine(11);
        this.sayChatLine(12);
        this.openDoor();
        this.trailPoints.clear();
        this.trailPointTimers.clear();
    }
    
    public void placeDoor()
    {
    	if(this.getSpawnLocation() != null)
    	{
	    	BlockPos doorpos = this.getSpawnLocation().add(0, 1, 16);
	    	
	    	// top x3
	    	this.world.setBlockState(doorpos.add(1, 3, 0), Blocks.NETHER_BRICK.getDefaultState());
	    	this.world.setBlockState(doorpos.add(0, 3, 0), Blocks.NETHER_BRICK.getDefaultState());
	    	this.world.setBlockState(doorpos.add(-1, 3, 0), Blocks.NETHER_BRICK.getDefaultState());
	    	
	    	// top x2
	    	this.world.setBlockState(doorpos.add(1, 2, 0), Blocks.NETHER_BRICK.getDefaultState());
	    	this.world.setBlockState(doorpos.add(0, 2, 0), Blocks.NETHER_BRICK.getDefaultState());
	    	this.world.setBlockState(doorpos.add(-1, 2, 0), Blocks.NETHER_BRICK.getDefaultState());
	    	
	    	// top
	    	this.world.setBlockState(doorpos.add(1, 1, 0), Blocks.NETHER_BRICK.getDefaultState());
	    	this.world.setBlockState(doorpos.add(0, 1, 0), Blocks.NETHER_BRICK.getDefaultState());
	    	this.world.setBlockState(doorpos.add(-1, 1, 0), Blocks.NETHER_BRICK.getDefaultState());
	    	
	    	// bottom
	    	this.world.setBlockState(doorpos, Blocks.NETHER_BRICK.getDefaultState());
	    	this.world.setBlockState(doorpos.add(1, 0, 0), Blocks.NETHER_BRICK.getDefaultState());
	    	this.world.setBlockState(doorpos.add(-1, 0, 0), Blocks.NETHER_BRICK.getDefaultState());
	    	
	    	// bottom x2
	    	this.world.setBlockState(doorpos.add(1, -1, 0), Blocks.NETHER_BRICK.getDefaultState());
	    	this.world.setBlockState(doorpos.add(0, -1, 0), Blocks.NETHER_BRICK.getDefaultState());
	    	this.world.setBlockState(doorpos.add(-1, -1, 0), Blocks.NETHER_BRICK.getDefaultState());
	    	
	    	// bottom x3
	    	this.world.setBlockState(doorpos.add(1, -2, 0), Blocks.NETHER_BRICK.getDefaultState());
	    	this.world.setBlockState(doorpos.add(0, -2, 0), Blocks.NETHER_BRICK.getDefaultState());
	    	this.world.setBlockState(doorpos.add(-1, -2, 0), Blocks.NETHER_BRICK.getDefaultState());
    	}
    }
    
    public void openDoor()
    {
    	if(this.getSpawnLocation() != null)
    	{
    		BlockPos doorpos = this.getSpawnLocation().add(0, 1, 16);
    		
	    	// top x3
    		this.world.setBlockState(doorpos.add(1, 3, 0), Blocks.NETHER_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST).withProperty(BlockStairs.HALF, EnumHalf.TOP));
	    	this.world.setBlockState(doorpos.add(0, 3, 0), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, EnumType.NETHERBRICK).withProperty(BlockStoneSlab.HALF, EnumBlockHalf.TOP));
	    	this.world.setBlockState(doorpos.add(-1, 3, 0), Blocks.NETHER_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST).withProperty(BlockStairs.HALF, EnumHalf.TOP));
	    	
	    	// top x2
	    	this.world.setBlockToAir(doorpos.add(1, 2, 0));
	    	this.world.setBlockToAir(doorpos.add(0, 2, 0));
	    	this.world.setBlockToAir(doorpos.add(-1, 2, 0));
	    	
	    	// top
	    	this.world.setBlockToAir(doorpos.add(1, 1, 0));
	    	this.world.setBlockToAir(doorpos.add(0, 1, 0));
	    	this.world.setBlockToAir(doorpos.add(-1, 1, 0));
	    	
	    	// bottom
	    	this.world.setBlockToAir(doorpos);
	    	this.world.setBlockToAir(doorpos.add(1, 0, 0));
	    	this.world.setBlockToAir(doorpos.add(-1, 0, 0));
	    	
	    	// bottom x2
	    	this.world.setBlockToAir(doorpos.add(1, -1, 0));
	    	this.world.setBlockToAir(doorpos.add(0, -1, 0));
	    	this.world.setBlockToAir(doorpos.add(-1, -1, 0));
	    	
	    	// bottom x3
	    	this.world.setBlockState(doorpos.add(1, -2, 0), Blocks.NETHER_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST).withProperty(BlockStairs.HALF, EnumHalf.BOTTOM));
	    	this.world.setBlockState(doorpos.add(0, -2, 0), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, EnumType.NETHERBRICK).withProperty(BlockStoneSlab.HALF, EnumBlockHalf.BOTTOM));
	    	this.world.setBlockState(doorpos.add(-1, -2, 0), Blocks.NETHER_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST).withProperty(BlockStairs.HALF, EnumHalf.BOTTOM));
	    	
	    	this.world.playSound((EntityPlayer)null, doorpos, EMSoundHandler.FIRE_DOOR_CLOSE, SoundCategory.BLOCKS, 10.0F, 1.0F);
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
    
    @Override
    protected int getExperiencePoints(EntityPlayer player)
    {
    	return 750;
    }
    
    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier)
    {
        EntityItem entityitem = this.dropItem(EMCoreItems.ETERNAL_FIRE, 1);
        
        if (entityitem != null)
        {
            entityitem.setNoDespawn();
            entityitem.setEntityInvulnerable(true);
        }
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
