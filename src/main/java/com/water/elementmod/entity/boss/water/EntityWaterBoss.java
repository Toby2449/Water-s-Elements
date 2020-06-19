package com.water.elementmod.entity.boss.water;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.water.elementmod.EMCoreItems;
import com.water.elementmod.entity.ai.EntityAIMoveTo;
import com.water.elementmod.entity.friendly.EntityAlyxWater;
import com.water.elementmod.entity.projectile.EntityIceBall;
import com.water.elementmod.entity.projectile.EntityIceBall2;

import net.minecraft.block.BlockSlab.EnumBlockHalf;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStairs.EnumHalf;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlab.EnumType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityWaterBoss extends EntityMob implements IRangedAttackMob
{
	private static final DataParameter<Boolean> INITIATE = EntityDataManager.<Boolean>createKey(EntityWaterBoss.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_INVURNERABLE = EntityDataManager.<Boolean>createKey(EntityWaterBoss.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> PHASE = EntityDataManager.<Integer>createKey(EntityWaterBoss.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> PRE_PHASE = EntityDataManager.<Integer>createKey(EntityWaterBoss.class, DataSerializers.VARINT);
	private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.BLUE, BossInfo.Overlay.PROGRESS)).setDarkenSky(false);
	private final EntityAIWatchClosest aiWatchClosest = new EntityAIWatchClosest(this, EntityPlayer.class, 50.0F);
	private EntityAIMoveTo aiMoveToValve = null;
	private final EntityAIAttackRanged aiAttackRanged = new EntityAIAttackRanged(this, 0D, 90, 20.0F);
	private BlockPos spawn_location;
	private final double ARENA_SIZE_X = 30.0D;
	private final double ARENA_SIZE_Y = 15.0D;
	private final double ARENA_SIZE_Z = 30.0D;
	private final double MF_SIZE_X = 60.0D;
	private final double MF_SIZE_Y = 60.0D;
	private final double MF_SIZE_Z = 100.0D;
	private boolean fightActive = false;
	private static List explosionPoints = new ArrayList();
	private static List explosionPointTimers = new ArrayList();
	private int explosionCD = 0;
	private int wallCD = 105;
	
	private int p1timer = 0;
	private int p2timer = 0;
	private int p3spacertimer = 50;
	private int p4minionCD = 250;
	private int p5attackCD = 100;
	private boolean p5attackpatternchosen = false;
	private int p5attacklocations = 0;
	private int p5attackspacer = 60;
	private int p5minionCD = 0;
	private int p6minionCD = 0;
	private int p6minionbossCD = 0;
	private int p7attackCD = 100;
	private boolean p7attackpatternchosen = false;
	private int p7attacklocations = 0;
	private int p7attackspacer = 60;
	private int p7minionbossCD = 0;
	
	private boolean chatted1 = false;
	private boolean chatted2 = false;
	private int chatted2CD = 0;
	private boolean chatted3 = false;
	private boolean chatted4 = false;
	private boolean chatted5 = false;
	private boolean chatted6 = false;
	
	public EntityWaterBoss(World worldIn) 
	{
		super(worldIn);
		this.isImmuneToFire = true;
		this.setSpawnLocation(this.getPosition());
		this.setSize(1.3F, 5.3F);
	}
	
	public EntityWaterBoss(World worldIn, BlockPos pos) 
	{
		super(worldIn);
		this.isImmuneToFire = true;
		this.setSpawnLocation(pos);
		this.setSize(1.3F, 5.3F);
	}
	
	public static void registerFixesBoss(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntityWaterBoss.class);
    }
	
	@Override
	protected void initEntityAI()
    {
        this.tasks.addTask(2, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    }
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(450.0F);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.75D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(99999999999F);
	}
	
	@Override
	protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(IS_INVURNERABLE, Boolean.valueOf(true));
        this.dataManager.register(INITIATE, Boolean.valueOf(false));
        this.dataManager.register(PHASE, Integer.valueOf(0));
        this.dataManager.register(PRE_PHASE, Integer.valueOf(0));
    }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setIntArray("SpawnPos", new int[] {this.getSpawnLocation().getX(), this.getSpawnLocation().getY(), this.getSpawnLocation().getZ()});
        compound.setBoolean("Invul", this.getInvulState());
        compound.setBoolean("FightState", this.isFightActivated());
        compound.setInteger("Phase", this.getPhase());
        compound.setInteger("Pre_Phase", this.getPrePhase());
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
        this.setPrePhase(compound.getInteger("Pre_Phase"));
        
        if (this.hasCustomName())
        {
            this.bossInfo.setName(this.getDisplayName());
        }
    }
	
	@Override
    public void setCustomNameTag(String name)
    {
        super.setCustomNameTag(name);
        this.bossInfo.setName(this.getDisplayName());
    }
	
	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
    	if(!this.isFightActivated()) 
    	{
    		if(this.getPrePhase() == 2)
    		{
    			if(this.getSpawnLocation() != null)
    			{
    				aiMoveToValve = new EntityAIMoveTo(this, 0.36D, 7, (this.getSpawnLocation().getX() + 0.5D), this.getSpawnLocation().getY(), (this.getSpawnLocation().getZ() + 8D));
			    	this.activateFight();
			    	this.world.playBroadcastSound(1023, new BlockPos(this), 0);
			    	this.fightActive = true;
			    	this.sayChatLine(0);
			    	this.placedoors();
			    	this.setPhase(1);
			    	return super.processInteract(player, hand);
    			}
    		}
    	}
    	return false;
    }

	@Override
	public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	
    	if (this.world.isRemote)
        {
    		for(int i = 0; i < 10; i++)
    		{
    			this.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + 0.25D + this.rand.nextDouble() * ((double)this.height), this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
    		}
        	
        	if(this.rand.nextInt(100) < 50)
        	{
        		for(int i = 0; i < 30; i++)
        		{
        			this.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + 0.25D + this.rand.nextDouble() * ((double)this.height), this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
                }
        	}
        	
        	this.world.spawnParticle(EnumParticleTypes.WATER_DROP, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + 0.25D + this.rand.nextDouble() * (double)this.height + 0.4D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
        	this.world.spawnParticle(EnumParticleTypes.DRIP_WATER, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * 1.75D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
        
        	for(int i = 0; i < this.explosionPoints.size(); i++)
			{
				int CircleTimer = (Integer)this.explosionPointTimers.get(i);
				ArrayList pos = (ArrayList)this.explosionPoints.get(i);
				
				if((Integer)this.explosionPointTimers.get(i) > 0)
				{
					if((Integer)this.explosionPointTimers.get(i) % 8 == 1)
					{
						this.WaterCircle((double)pos.get(0), (double)pos.get(1), (double)pos.get(2), 2, this.world);
					}
						
					if((Integer)this.explosionPointTimers.get(i) <= 1)
					{
						this.WaterExplosion((double)pos.get(0), (double)pos.get(1), (double)pos.get(2), 2, 8, this.world);
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
    	
    	if(!this.world.isRemote)
    	{
    		// If nobodies in the arena
	        if(this.isFightActivated() && this.fightActive)
	        {
	        	List<EntityPlayer> players = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(ARENA_SIZE_X, ARENA_SIZE_Y, ARENA_SIZE_Z));
		        
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
					        	ent.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100, 0));
					        	ent.attackEntityFrom(DamageSource.causeMobDamage(this), 16.0F);
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
	        
	        List<EntityMob> list = this.world.<EntityMob>getEntitiesWithinAABB(EntityMob.class, this.getEntityBoundingBox().grow(ARENA_SIZE_X, ARENA_SIZE_Y, ARENA_SIZE_Z));
	        
	        for (EntityMob entity : list)
	        {
	        	if(!(entity instanceof EntityWaterBossClone || entity instanceof EntityWaterBoss || entity instanceof EntityWaterBossRangedMinion || entity instanceof EntityWaterBossMeleeMinion))
	        	{
	        		entity.isDead = true;
	        	}
	        }
	        
	        if(this.ticksExisted % 20 == 1)
        	{
	        	List<EntityPlayer> playerlist = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(MF_SIZE_X, MF_SIZE_Y, MF_SIZE_Z).offset(0, -20, 0));
		        
		        for (EntityPlayer player : playerlist)
		        {
		        	player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 100, 2));
		        	player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 100, 1));
		        }
        	}
    	}    
    	
    	switch(this.getPhase())
    	{
    	case 1:
    		if(!this.world.isRemote)
    		{
	    		this.p1timer++;
	    		if(this.p1timer >= 160) // 8 seconds
	    		{
	    			this.setPhase(2);
	    		}
    		}
    		break;
    	case 2:
    		if(this.world.isRemote)
    		{
    			if(this.chatted1 == false)
    			{
    				this.sayChatLine(1);
    				this.chatted1 = true;
    			}
    			
    			this.chatted2CD++;
    			if(this.chatted2CD >= 100)
    			{
    				if(this.chatted2 == false)
    				{
    					this.sayChatLine(2);
        				this.chatted2 = true;
    				}
    			}
    			
    			this.explosionCD++;
	    		List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(ARENA_SIZE_X, ARENA_SIZE_Y, ARENA_SIZE_Z));
		        
		        for (EntityPlayer entity : list)
		        {
		        	if(this.explosionCD >= 120)
		        	{
		        		this.spawnWaterExplosion(entity.posX, entity.posY, entity.posZ);
		        		this.explosionCD = 0;
		        	}
		        }
    		}
    		if(!this.world.isRemote)
    		{
    			this.explosionCD++;
    			this.wallCD--;
	    		List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(ARENA_SIZE_X, ARENA_SIZE_Y, ARENA_SIZE_Z));
		        
		        for (EntityPlayer entity : list)
		        {
		        	if(this.explosionCD >= 120)
		        	{
		        		this.spawnWaterExplosion(entity.posX, entity.posY, entity.posZ);
		        		this.explosionCD = 0;
		        	}
		        }
		        
		        if(this.wallCD <= 0)
	        	{
	        		int gap = this.rand.nextInt(9) + 1;
	        		int gap2 = gap + 1;
	        		
	        		for(int i = 10; i > 0; i--)
	        		{
	        			if(i != gap)
	        			{
	        				if(i != gap2)
		        			{
			        			BlockPos startingPos = null;
			        			
			        			switch(i)
			        			{
			        			case 10:
			        				startingPos = this.getSpawnLocation().add(-9, 1, -8);
			        				break;
			        			case 9:
			        				startingPos = this.getSpawnLocation().add(-7, 1, -8);
			        				break;
			        			case 8:
			        				startingPos = this.getSpawnLocation().add(-5, 1, -8);
			        				break;
			        			case 7:
			        				startingPos = this.getSpawnLocation().add(-3, 1, -8);
			        				break;
			        			case 6:
			        				startingPos = this.getSpawnLocation().add(-1, 1, -8);
			        				break;
			        			case 5:
			        				startingPos = this.getSpawnLocation().add(1, 1, -8);
			        				break;
			        			case 4:
			        				startingPos = this.getSpawnLocation().add(3, 1, -8);
			        				break;
			        			case 3:
			        				startingPos = this.getSpawnLocation().add(5, 1, -8);
			        				break;
			        			case 2:
			        				startingPos = this.getSpawnLocation().add(7, 1, -8);
			        				break;
			        			case 1:
			        				startingPos = this.getSpawnLocation().add(9, 1, -8);
			        				break;
			        			}
			        			EntityWaterBossClone wallminion = new EntityWaterBossClone(world, startingPos.getX(), startingPos.getY(), startingPos.getZ() + 18.5D);
			        			wallminion.enablePersistence();
			        			wallminion.setPosition(startingPos.getX() + 0.5D, startingPos.getY(), startingPos.getZ() + 0.5D);
					            world.spawnEntity(wallminion);
	        				}
	        			}
	        		}
	        		this.wallCD = 105;
	        	}
		        
		        this.p2timer++;
	    		if(this.p2timer >= 45 * 20) // 8 seconds
	    		{
	    			this.setPhase(3);
	    		}
    		}
    		break;
    	case 3:
    		if(!this.world.isRemote)
    		{
    			this.p3spacertimer--;
    			if(this.p3spacertimer <= 0)
    			{
    				this.setPhase(4);
    			}
    		}
    		break;
    	case 4:
    		if(!this.world.isRemote)
    		{
    			this.setInvulState(false);
    			this.p4minionCD--;
    			if(this.p4minionCD <= 0)
    			{
    				BlockPos spawnlocation1 = this.getSpawnLocation().add(-7, 10, 5);
        			BlockPos spawnlocation2 = this.getSpawnLocation().add(-7, 10, -5);
        			BlockPos spawnlocation3 = this.getSpawnLocation().add(7, 10, 5);
        			BlockPos spawnlocation4 = this.getSpawnLocation().add(7, 10, -5);
        			
        			EntityWaterBossRangedMinion minion1 = new EntityWaterBossRangedMinion(world);
        			minion1.enablePersistence();
        			minion1.setPosition(spawnlocation1.getX(), spawnlocation1.getY(), spawnlocation1.getZ() + 1.0D);
		            world.spawnEntity(minion1);
		            
		            EntityWaterBossRangedMinion minion2 = new EntityWaterBossRangedMinion(world);
		            minion2.enablePersistence();
		            minion2.setPosition(spawnlocation2.getX(), spawnlocation2.getY(), spawnlocation2.getZ());
		            world.spawnEntity(minion2);
		            
		            EntityWaterBossRangedMinion minion3 = new EntityWaterBossRangedMinion(world);
		            minion3.enablePersistence();
		            minion3.setPosition(spawnlocation3.getX() + 1.0D, spawnlocation3.getY(), spawnlocation3.getZ() + 1.0D);
		            world.spawnEntity(minion3);
		            
		            EntityWaterBossRangedMinion minion4 = new EntityWaterBossRangedMinion(world);
		            minion4.enablePersistence();
        			minion4.setPosition(spawnlocation4.getX() + 1.0D, spawnlocation4.getY(), spawnlocation4.getZ());
		            world.spawnEntity(minion4);
		            
		            this.p4minionCD = 320;
    			}
    			if(this.getHealth() <= (this.getMaxHealth() / 3) * 2) // 2/3
    			{
    				this.setPhase(5);
    				
    				BlockPos spawnlocation1 = this.getSpawnLocation().add(-7, 10, 5);
        			BlockPos spawnlocation2 = this.getSpawnLocation().add(-7, 10, -5);
        			BlockPos spawnlocation3 = this.getSpawnLocation().add(7, 10, 5);
        			BlockPos spawnlocation4 = this.getSpawnLocation().add(7, 10, -5);
        			
        			EntityWaterBossRangedMinion minion1 = new EntityWaterBossRangedMinion(world);
        			minion1.enablePersistence();
        			minion1.setPosition(spawnlocation1.getX(), spawnlocation1.getY(), spawnlocation1.getZ() + 1.0D);
		            world.spawnEntity(minion1);
		            
		            EntityWaterBossRangedMinion minion2 = new EntityWaterBossRangedMinion(world);
		            minion2.enablePersistence();
		            minion2.setPosition(spawnlocation2.getX(), spawnlocation2.getY(), spawnlocation2.getZ());
		            world.spawnEntity(minion2);
		            
		            EntityWaterBossRangedMinion minion3 = new EntityWaterBossRangedMinion(world);
		            minion3.enablePersistence();
		            minion3.setPosition(spawnlocation3.getX() + 1.0D, spawnlocation3.getY(), spawnlocation3.getZ() + 1.0D);
		            world.spawnEntity(minion3);
		            
		            EntityWaterBossRangedMinion minion4 = new EntityWaterBossRangedMinion(world);
		            minion4.enablePersistence();
        			minion4.setPosition(spawnlocation4.getX() + 1.0D, spawnlocation4.getY(), spawnlocation4.getZ());
		            world.spawnEntity(minion4);
    			}
    		}
    		else
    		{
    			if(this.chatted3 == false)
    			{
    				this.sayChatLine(3);
    				this.chatted3 = true;
    			}
    		}
    		break;
    	case 5:
    		if(!this.world.isRemote)
    		{
    			this.setInvulState(false);
    			
    			if(this.p5attackpatternchosen == false)
    			{
    				switch(this.p5attacklocations)
    				{
    				case 0:
    					this.p5attacklocations = 1;
    					this.p5attackspacer = 60;
    					break;
    				case 1:
    					this.p5attacklocations = 0;
    					this.p5attackspacer = 60;
    					break;
    				}
    				this.p5attackpatternchosen = true;
    			}
    			
    			if(this.p5attackpatternchosen == true)
    			{
    				this.p5attackspacer--;
    				if(this.p5attackspacer <= 0)
    				{
	    				this.p5attackCD--;
	    				
	    				switch(this.p5attacklocations)
	    				{
	    				case 0:
	            			if(this.p5attackCD <= 0)
	            			{
	            				List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(this.posX,this.posY,this.posZ, this.posX + 12D, this.posY + 11D, this.posZ + 11D));
	            				for (EntityPlayer entity : list)
	            		        {
	            					entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20, 1));
	            					entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 120, 1));
	            					entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 250, 1));
	            					entity.attackEntityFrom(DamageSource.causeMobDamage(this), 30.0F);
	            		        }
	            				List<EntityPlayer> list2 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(this.posX - 1.0D,this.posY,this.posZ - 1.0D, this.posX - 12D, this.posY + 11D, this.posZ - 11D));
	            				for (EntityPlayer entity : list2)
	            		        {
	            					entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20, 1));
	            					entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 120, 1));
	            					entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 250, 1));
	            					entity.attackEntityFrom(DamageSource.causeMobDamage(this), 30.0F);
	            		        }
	            				this.p5attackCD = 100;
	            				this.p5attackpatternchosen = false;
	            			}
	    					break;
	    				case 1:
	            			if(this.p5attackCD <= 0)
	            			{
	            				List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(this.posX - 1.0D,this.posY,this.posZ, this.posX - 12D, this.posY + 11D, this.posZ + 11D));
	            				for (EntityPlayer entity : list)
	            		        {
	            					entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20, 1));
	            					entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 120, 1));
	            					entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 250, 1));
	            					entity.attackEntityFrom(DamageSource.causeMobDamage(this), 30.0F);
	            		        }
	            				List<EntityPlayer> list2 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(this.posX,this.posY,this.posZ - 1.0D, this.posX + 12D, this.posY + 11D, this.posZ - 11D));
	            				for (EntityPlayer entity : list2)
	            		        {
	            					entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20, 1));
	            					entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 120, 1));
	            					entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 250, 1));
	            					entity.attackEntityFrom(DamageSource.causeMobDamage(this), 30.0F);
	            		        }
	            				this.p5attackCD = 100;
	            				this.p5attackpatternchosen = false;
	            			}
	    					break;
	    				}
    				}
    			}
    			
    			this.p5minionCD++;
    			if(this.p5minionCD >= 400)
    			{
    				BlockPos spawnlocation1 = this.getSpawnLocation().add(-7, 10, 5);
        			BlockPos spawnlocation2 = this.getSpawnLocation().add(-7, 10, -5);
        			BlockPos spawnlocation3 = this.getSpawnLocation().add(7, 10, 5);
        			BlockPos spawnlocation4 = this.getSpawnLocation().add(7, 10, -5);
        			
        			EntityWaterBossRangedMinion minion1 = new EntityWaterBossRangedMinion(world);
        			minion1.enablePersistence();
        			minion1.setPosition(spawnlocation1.getX(), spawnlocation1.getY(), spawnlocation1.getZ() + 1.0D);
		            world.spawnEntity(minion1);
		            
		            EntityWaterBossRangedMinion minion2 = new EntityWaterBossRangedMinion(world);
		            minion2.enablePersistence();
		            minion2.setPosition(spawnlocation2.getX(), spawnlocation2.getY(), spawnlocation2.getZ());
		            world.spawnEntity(minion2);
		            
		            EntityWaterBossRangedMinion minion3 = new EntityWaterBossRangedMinion(world);
		            minion3.enablePersistence();
		            minion3.setPosition(spawnlocation3.getX() + 1.0D, spawnlocation3.getY(), spawnlocation3.getZ() + 1.0D);
		            world.spawnEntity(minion3);
		            
		            EntityWaterBossRangedMinion minion4 = new EntityWaterBossRangedMinion(world);
		            minion4.enablePersistence();
        			minion4.setPosition(spawnlocation4.getX() + 1.0D, spawnlocation4.getY(), spawnlocation4.getZ());
		            world.spawnEntity(minion4);
		            
		            this.p5minionCD = 0;
    			}
    			
    			if(this.getHealth() <= this.getMaxHealth() / 3) // 1/3
    			{
    				this.setPhase(6);
    				
    				BlockPos spawnlocation1 = this.getSpawnLocation().add(-7, 10, 5);
        			BlockPos spawnlocation2 = this.getSpawnLocation().add(-7, 10, -5);
        			BlockPos spawnlocation3 = this.getSpawnLocation().add(7, 10, 5);
        			BlockPos spawnlocation4 = this.getSpawnLocation().add(7, 10, -5);
        			
        			EntityWaterBossRangedMinion minion1 = new EntityWaterBossRangedMinion(world);
        			minion1.enablePersistence();
        			minion1.setPosition(spawnlocation1.getX(), spawnlocation1.getY(), spawnlocation1.getZ() + 1.0D);
		            world.spawnEntity(minion1);
		            
		            EntityWaterBossRangedMinion minion2 = new EntityWaterBossRangedMinion(world);
		            minion2.enablePersistence();
		            minion2.setPosition(spawnlocation2.getX(), spawnlocation2.getY(), spawnlocation2.getZ());
		            world.spawnEntity(minion2);
		            
		            EntityWaterBossRangedMinion minion3 = new EntityWaterBossRangedMinion(world);
		            minion3.enablePersistence();
		            minion3.setPosition(spawnlocation3.getX() + 1.0D, spawnlocation3.getY(), spawnlocation3.getZ() + 1.0D);
		            world.spawnEntity(minion3);
		            
		            EntityWaterBossRangedMinion minion4 = new EntityWaterBossRangedMinion(world);
		            minion4.enablePersistence();
        			minion4.setPosition(spawnlocation4.getX() + 1.0D, spawnlocation4.getY(), spawnlocation4.getZ());
		            world.spawnEntity(minion4);
    			}
    		}
    		
    		
    		
    		if(this.world.isRemote)
    		{
    			if(this.chatted4 == false)
    			{
    				this.sayChatLine(4);
    				this.chatted4 = true;
    			}
    			if(this.p5attackpatternchosen == false)
    			{
    				switch(this.p5attacklocations)
    				{
    				case 0:
    					this.p5attacklocations = 1;
    					this.p5attackspacer = 60;
    					break;
    				case 1:
    					this.p5attacklocations = 0;
    					this.p5attackspacer = 60;
    					break;
    				}
    				this.p5attackpatternchosen = true;
    			}
    			
    			if(this.p5attackpatternchosen == true)
    			{
    				this.p5attackspacer--;
    				if(this.p5attackspacer <= 0)
    				{
	    				this.p5attackCD--;
	    				
	    				switch(this.p5attacklocations)
	    				{
	    				case 0:
	    					WaterArea1(this.getPosition().getX(), this.getPosition().getY() + 0.15D, this.getPosition().getZ(), this.world);
	    					WaterArea2(this.getPosition().getX(), this.getPosition().getY() + 0.15D, this.getPosition().getZ(), this.world);
	            			if(this.p5attackCD <= 0)
	            			{
	            				WaterAreaExplode1(this.getPosition().getX(), this.getPosition().getY() + 0.15D, this.getPosition().getZ(), this.world);
	            				WaterAreaExplode2(this.getPosition().getX(), this.getPosition().getY() + 0.15D, this.getPosition().getZ(), this.world);
	            				this.p5attackCD = 120;
	            				this.p5attackpatternchosen = false;
	            			}
	    					break;
	    				case 1:
	    					WaterArea3(this.getPosition().getX(), this.getPosition().getY() + 0.15D, this.getPosition().getZ(), this.world);
	    					WaterArea4(this.getPosition().getX(), this.getPosition().getY() + 0.15D, this.getPosition().getZ(), this.world);
	            			if(this.p5attackCD <= 0)
	            			{
	            				WaterAreaExplode3(this.getPosition().getX(), this.getPosition().getY() + 0.15D, this.getPosition().getZ(), this.world);
	            				WaterAreaExplode4(this.getPosition().getX(), this.getPosition().getY() + 0.15D, this.getPosition().getZ(), this.world);
	            				this.p5attackCD = 120;
	            				this.p5attackpatternchosen = false;
	            			}
	    					break;
	    				}
    				}
    			}
    		}
    		break;
    	case 6:
    		if(!this.world.isRemote)
    		{
    			this.setInvulState(false);
    			this.p6minionCD++;
    			if(this.p6minionCD >= 400)
    			{
    				BlockPos spawnlocation1 = this.getSpawnLocation().add(-7, 10, 5);
        			BlockPos spawnlocation2 = this.getSpawnLocation().add(-7, 10, -5);
        			BlockPos spawnlocation3 = this.getSpawnLocation().add(7, 10, 5);
        			BlockPos spawnlocation4 = this.getSpawnLocation().add(7, 10, -5);
        			
        			EntityWaterBossRangedMinion minion1 = new EntityWaterBossRangedMinion(world);
        			minion1.enablePersistence();
        			minion1.setPosition(spawnlocation1.getX(), spawnlocation1.getY(), spawnlocation1.getZ() + 1.0D);
		            world.spawnEntity(minion1);
		            
		            EntityWaterBossRangedMinion minion2 = new EntityWaterBossRangedMinion(world);
		            minion2.enablePersistence();
		            minion2.setPosition(spawnlocation2.getX(), spawnlocation2.getY(), spawnlocation2.getZ());
		            world.spawnEntity(minion2);
		            
		            EntityWaterBossRangedMinion minion3 = new EntityWaterBossRangedMinion(world);
		            minion3.enablePersistence();
		            minion3.setPosition(spawnlocation3.getX() + 1.0D, spawnlocation3.getY(), spawnlocation3.getZ() + 1.0D);
		            world.spawnEntity(minion3);
		            
		            EntityWaterBossRangedMinion minion4 = new EntityWaterBossRangedMinion(world);
		            minion4.enablePersistence();
        			minion4.setPosition(spawnlocation4.getX() + 1.0D, spawnlocation4.getY(), spawnlocation4.getZ());
		            world.spawnEntity(minion4);
		            
		            this.p6minionCD = 0;
    			}
    			
    			int p6numberofmelees = 0;
    			
    			List<EntityWaterBossMeleeMinion> minioncheck = this.world.<EntityWaterBossMeleeMinion>getEntitiesWithinAABB(EntityWaterBossMeleeMinion.class, this.getEntityBoundingBox().grow(this.ARENA_SIZE_X, this.ARENA_SIZE_Y, this.ARENA_SIZE_Z));
				for (EntityWaterBossMeleeMinion entity : minioncheck)
		        {
					p6numberofmelees++;
		        }
				
				if(p6numberofmelees == 0)
				{
					this.p6minionbossCD++;
	    			if(this.p6minionbossCD >= 80)
	    			{
	    				if(this.world.getDifficulty() == EnumDifficulty.EASY)
		        		{
	    					for(int i = 2; i > 0; i--)
	    					{
	    						EntityWaterBossMeleeMinion minion = new EntityWaterBossMeleeMinion(world);
	    						minion.enablePersistence();
	    	        			minion.setPosition(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ() + 1.0D);
	    			            world.spawnEntity(minion);
	    					}
		        		}
	    				
	    				if(this.world.getDifficulty() == EnumDifficulty.NORMAL)
		        		{
	    					for(int i = 3; i > 0; i--)
	    					{
	    						EntityWaterBossMeleeMinion minion = new EntityWaterBossMeleeMinion(world);
	    						minion.enablePersistence();
	    	        			minion.setPosition(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ() + 1.0D);
	    			            world.spawnEntity(minion);
	    					}
		        		}
	    				
	    				if(this.world.getDifficulty() == EnumDifficulty.HARD)
		        		{
	    					for(int i = 4; i > 0; i--)
	    					{
	    						EntityWaterBossMeleeMinion minion = new EntityWaterBossMeleeMinion(world);
	    						minion.enablePersistence();
	    	        			minion.setPosition(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ() + 1.0D);
	    			            world.spawnEntity(minion);
	    					}
		        		}
			            
			            this.p6minionbossCD = 0;
	    			}
				}
				
				if(this.getHealth() <= (this.getMaxHealth() / 4))
    			{
    				this.setPhase(7);
    			}
    		}
    		else
    		{
    			if(this.chatted5 == false)
    			{
    				this.sayChatLine(5);
    				this.chatted5 = true;
    			}
    		}
    		break;
    	case 7:
    		if(!this.world.isRemote)
    		{
    			if(this.p7attackpatternchosen == false)
    			{
    				switch(this.p7attacklocations)
    				{
    				case 0:
    					this.p7attacklocations = 1;
    					this.p7attackspacer = 60;
    					break;
    				case 1:
    					this.p7attacklocations = 0;
    					this.p7attackspacer = 60;
    					break;
    				}
    				this.p7attackpatternchosen = true;
    			}
    			
    			if(this.p7attackpatternchosen == true)
    			{
    				this.p7attackspacer--;
    				if(this.p7attackspacer <= 0)
    				{
	    				this.p7attackCD--;
	    				
	    				switch(this.p7attacklocations)
	    				{
	    				case 0:
	            			if(this.p7attackCD <= 0)
	            			{
	            				List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(this.posX,this.posY,this.posZ, this.posX + 12D, this.posY + 11D, this.posZ + 11D));
	            				for (EntityPlayer entity : list)
	            		        {
	            					entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20, 1));
	            					entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 120, 1));
	            					entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 250, 1));
	            					entity.attackEntityFrom(DamageSource.causeMobDamage(this), 30.0F);
	            		        }
	            				List<EntityPlayer> list2 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(this.posX - 1.0D,this.posY,this.posZ - 1.0D, this.posX - 12D, this.posY + 11D, this.posZ - 11D));
	            				for (EntityPlayer entity : list2)
	            		        {
	            					entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20, 1));
	            					entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 120, 1));
	            					entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 250, 1));
	            					entity.attackEntityFrom(DamageSource.causeMobDamage(this), 30.0F);
	            		        }
	            				this.p7attackCD = 100;
	            				this.p7attackpatternchosen = false;
	            			}
	    					break;
	    				case 1:
	            			if(this.p7attackCD <= 0)
	            			{
	            				List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(this.posX - 1.0D,this.posY,this.posZ, this.posX - 12D, this.posY + 11D, this.posZ + 11D));
	            				for (EntityPlayer entity : list)
	            		        {
	            					entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20, 1));
	            					entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 120, 1));
	            					entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 250, 1));
	            					entity.attackEntityFrom(DamageSource.causeMobDamage(this), 30.0F);
	            		        }
	            				List<EntityPlayer> list2 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(this.posX,this.posY,this.posZ - 1.0D, this.posX + 12D, this.posY + 11D, this.posZ - 11D));
	            				for (EntityPlayer entity : list2)
	            		        {
	            					entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20, 1));
	            					entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 120, 1));
	            					entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 250, 1));
	            					entity.attackEntityFrom(DamageSource.causeMobDamage(this), 30.0F);
	            		        }
	            				this.p7attackCD = 100;
	            				this.p7attackpatternchosen = false;
	            			}
	    					break;
	    				}
    				}
    			}
    			
    			this.p7minionbossCD++;
    			if(this.p7minionbossCD >= 300)
    			{
    				if(this.world.getDifficulty() == EnumDifficulty.EASY)
	        		{
    					for(int i = 2; i > 0; i--)
    					{
    						EntityWaterBossMeleeMinion minion = new EntityWaterBossMeleeMinion(world);
    						minion.enablePersistence();
    	        			minion.setPosition(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ() + 1.0D);
    			            world.spawnEntity(minion);
    					}
	        		}
    				
    				if(this.world.getDifficulty() == EnumDifficulty.NORMAL)
	        		{
    					for(int i = 3; i > 0; i--)
    					{
    						EntityWaterBossMeleeMinion minion = new EntityWaterBossMeleeMinion(world);
    						minion.enablePersistence();
    	        			minion.setPosition(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ() + 1.0D);
    			            world.spawnEntity(minion);
    					}
	        		}
    				
    				if(this.world.getDifficulty() == EnumDifficulty.HARD)
	        		{
    					for(int i = 4; i > 0; i--)
    					{
    						EntityWaterBossMeleeMinion minion = new EntityWaterBossMeleeMinion(world);
    						minion.enablePersistence();
    	        			minion.setPosition(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ() + 1.0D);
    			            world.spawnEntity(minion);
    					}
	        		}
		            
		            this.p7minionbossCD = 0;
    			}
    		}
    		else
    		{
    			if(this.world.isRemote)
        		{
        			if(this.chatted6 == false)
        			{
        				this.sayChatLine(6);
        				this.chatted6 = true;
        			}
        			if(this.p7attackpatternchosen == false)
        			{
        				switch(this.p7attacklocations)
        				{
        				case 0:
        					this.p7attacklocations = 1;
        					this.p7attackspacer = 60;
        					break;
        				case 1:
        					this.p7attacklocations = 0;
        					this.p7attackspacer = 60;
        					break;
        				}
        				this.p7attackpatternchosen = true;
        			}
        			
        			if(this.p7attackpatternchosen == true)
        			{
        				this.p7attackspacer--;
        				if(this.p7attackspacer <= 0)
        				{
    	    				this.p7attackCD--;
    	    				
    	    				switch(this.p7attacklocations)
    	    				{
    	    				case 0:
    	    					WaterArea1(this.getPosition().getX(), this.getPosition().getY() + 0.15D, this.getPosition().getZ(), this.world);
    	    					WaterArea2(this.getPosition().getX(), this.getPosition().getY() + 0.15D, this.getPosition().getZ(), this.world);
    	            			if(this.p7attackCD <= 0)
    	            			{
    	            				WaterAreaExplode1(this.getPosition().getX(), this.getPosition().getY() + 0.15D, this.getPosition().getZ(), this.world);
    	            				WaterAreaExplode2(this.getPosition().getX(), this.getPosition().getY() + 0.15D, this.getPosition().getZ(), this.world);
    	            				this.p7attackCD = 120;
    	            				this.p7attackpatternchosen = false;
    	            			}
    	    					break;
    	    				case 1:
    	    					WaterArea3(this.getPosition().getX(), this.getPosition().getY() + 0.15D, this.getPosition().getZ(), this.world);
    	    					WaterArea4(this.getPosition().getX(), this.getPosition().getY() + 0.15D, this.getPosition().getZ(), this.world);
    	            			if(this.p7attackCD <= 0)
    	            			{
    	            				WaterAreaExplode3(this.getPosition().getX(), this.getPosition().getY() + 0.15D, this.getPosition().getZ(), this.world);
    	            				WaterAreaExplode4(this.getPosition().getX(), this.getPosition().getY() + 0.15D, this.getPosition().getZ(), this.world);
    	            				this.p7attackCD = 120;
    	            				this.p7attackpatternchosen = false;
    	            			}
    	    					break;
    	    				}
        				}
        			}
        		}
    			
    			if(this.chatted6 == false)
    			{
    				this.sayChatLine(6);
    				this.chatted6 = true;
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
		super.updateAITasks();
		
		switch(this.getPhase())
		{
		case 0:
			if(this.getSpawnLocation() != null)
			{
				this.setPosition(this.getSpawnLocation().getX() + 0.5D, this.getSpawnLocation().getY(), this.getSpawnLocation().getZ() + 0.5D);
			}
			this.tasks.addTask(1, aiWatchClosest);
			this.tasks.removeTask(aiMoveToValve);
			this.tasks.removeTask(aiAttackRanged);
			break;
		case 1:
			this.tasks.removeTask(aiWatchClosest);
			this.tasks.addTask(0, aiMoveToValve);
			this.getLookHelper().setLookPosition(this.posX, (this.posY + (double)this.getEyeHeight()) - 4, this.posZ + (this.posZ + 2), (float)this.getHorizontalFaceSpeed(), (float)this.getVerticalFaceSpeed());
			break;
		case 2:
			this.tasks.addTask(0, aiWatchClosest);
			this.tasks.removeTask(aiMoveToValve);
			if(this.getSpawnLocation() != null)
			{
				this.setPosition(this.getSpawnLocation().getX() + 1.0D, this.getSpawnLocation().getY(), this.getSpawnLocation().getZ() + 7D);
			}
			break;
		case 3:
			this.tasks.addTask(0, aiWatchClosest);
			if(this.getSpawnLocation() != null)
			{
				this.setPosition(this.getSpawnLocation().getX() + 1.0D, this.getSpawnLocation().getY(), this.getSpawnLocation().getZ() + 7D);
			}
			break;
		case 4:
			this.tasks.addTask(0, aiWatchClosest);
			if(this.getSpawnLocation() != null)
			{
				this.setPosition(this.getSpawnLocation().getX() + 1.0D, this.getSpawnLocation().getY(), this.getSpawnLocation().getZ() + 7D);
			}
			break;
		case 5:
			this.tasks.addTask(0, aiWatchClosest);
			this.tasks.addTask(1, aiAttackRanged);
			if(this.getSpawnLocation() != null)
			{
				this.setPosition(this.getSpawnLocation().getX() + 0.5D, this.getSpawnLocation().getY(), this.getSpawnLocation().getZ() + 0.5D);
			}
			break;
    	case 6:
			this.tasks.removeTask(aiAttackRanged);
			if(this.getSpawnLocation() != null)
			{
				this.setPosition(this.getSpawnLocation().getX() + 0.5D, this.getSpawnLocation().getY(), this.getSpawnLocation().getZ() + 0.5D);
			}
			break;
    	case 7:
    		this.tasks.addTask(1, aiAttackRanged);
			if(this.getSpawnLocation() != null)
			{
				this.setPosition(this.getSpawnLocation().getX() + 0.5D, this.getSpawnLocation().getY(), this.getSpawnLocation().getZ() + 0.5D);
			}
			break;
    	}
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
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) 
	{
		if(this.getPhase() == 7) 
		{
			this.throwIceBall1(target);
		}
		else
		{
			this.throwIceBall2(target);
		}
		this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.AMBIENT, 2.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
	}
	
	private void throwIceBall1(EntityLivingBase p_82216_2_)
    {
    	EntityIceBall entityiceball = new EntityIceBall(this.world, this);
        double d0 = p_82216_2_.posY - 1.100000023841858D;
        double d1 = p_82216_2_.posX - this.posX;
        double d2 = d0 - entityiceball.posY;
        double d3 = p_82216_2_.posZ - this.posZ;
        float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        entityiceball.shoot(d1, d2 + (double)f, d3, 1.6F, 12.0F);
        this.world.spawnEntity(entityiceball);
    }
	private void throwIceBall2(EntityLivingBase p_82216_2_)
    {
    	EntityIceBall2 entityiceball = new EntityIceBall2(this.world, this);
        double d0 = p_82216_2_.posY - 1.100000023841858D;
        double d1 = p_82216_2_.posX - this.posX;
        double d2 = d0 - entityiceball.posY;
        double d3 = p_82216_2_.posZ - this.posZ;
        float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        entityiceball.shoot(d1, d2 + (double)f, d3, 1.6F, 12.0F);
        this.world.spawnEntity(entityiceball);
    }
	
	public void spawnWaterExplosion(double x, double y, double z)
	{
		List pos = new ArrayList();
		pos.add(x);
		pos.add(y);
		pos.add(z);
		this.explosionPoints.add(pos);
		this.explosionPointTimers.add(5 * 20); // 5 seconds
	}
	
	public void WaterCircle(double x, double y, double z, double radius, World world)
	{
		Random rand = new Random();
		for(double r = 0.6D; r <= radius; r += 0.45D)
		{
			for(float i = 0.0F; i < 360.0F; i += 30.0F)
			{
				double deltaX = Math.cos(Math.toRadians(i))*r + rand.nextDouble();
				double deltaZ = -Math.sin(Math.toRadians(i))*r + rand.nextDouble();
				double finalX = x - 0.5D + deltaX;
				double finalZ = z - 0.5D + deltaZ;
			    
				world.spawnParticle(EnumParticleTypes.DRIP_WATER, finalX, y + 0.15D, finalZ, 0.0D, 0.0D, 0.0D);
			}
		}
	}
	
	public void WaterExplosion(double x, double y, double z, double radius, int height, World world)
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
				    
					world.spawnParticle(EnumParticleTypes.DRIP_WATER, finalX, (y + this.rand.nextInt(height) + this.rand.nextDouble()) + 0.15D, finalZ, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}
	
	public void WaterArea1(double x, double y, double z, World world)
	{
		Random rand = new Random();
		for(int m = 0; m < 30; m++)
		{
			world.spawnParticle(EnumParticleTypes.DRIP_WATER, x + rand.nextInt(12) + rand.nextDouble(), y, z + rand.nextInt(11) + rand.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
	}
	
	public void WaterArea2(double x, double y, double z, World world)
	{
		Random rand = new Random();
		for(int m = 0; m < 30; m++)
		{
			world.spawnParticle(EnumParticleTypes.DRIP_WATER, x - rand.nextInt(12) + rand.nextDouble(), y, z - rand.nextInt(11) + rand.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
	}
	
	public void WaterArea3(double x, double y, double z, World world)
	{
		Random rand = new Random();
		for(int m = 0; m < 30; m++)
		{
			world.spawnParticle(EnumParticleTypes.DRIP_WATER, x - rand.nextInt(12) + rand.nextDouble(), y, z + rand.nextInt(11) + rand.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
	}
	
	public void WaterArea4(double x, double y, double z, World world)
	{
		Random rand = new Random();
		for(int m = 0; m < 30; m++)
		{
			world.spawnParticle(EnumParticleTypes.DRIP_WATER, x + rand.nextInt(12) + rand.nextDouble(), y, z - rand.nextInt(11) + rand.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
	}
	
	public void WaterAreaExplode1(double x, double y, double z, World world)
	{
		Random rand = new Random();
		for(int m = 0; m < 3000; m++)
		{
			world.spawnParticle(EnumParticleTypes.DRIP_WATER, x + rand.nextInt(12) + rand.nextDouble(), y + rand.nextInt(11) + rand.nextDouble(), z + rand.nextInt(11) + rand.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
	}
	
	public void WaterAreaExplode2(double x, double y, double z, World world)
	{
		Random rand = new Random();
		for(int m = 0; m < 3000; m++)
		{
			world.spawnParticle(EnumParticleTypes.DRIP_WATER, x - rand.nextInt(12) + rand.nextDouble(), y + rand.nextInt(11) + rand.nextDouble(), z - rand.nextInt(11) + rand.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
	}
	
	public void WaterAreaExplode3(double x, double y, double z, World world)
	{
		Random rand = new Random();
		for(int m = 0; m < 3000; m++)
		{
			world.spawnParticle(EnumParticleTypes.DRIP_WATER, x - rand.nextInt(12) + rand.nextDouble(), y + rand.nextInt(11) + rand.nextDouble(), z + rand.nextInt(11) + rand.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
	}
	
	public void WaterAreaExplode4(double x, double y, double z, World world)
	{
		Random rand = new Random();
		for(int m = 0; m < 3000; m++)
		{
			world.spawnParticle(EnumParticleTypes.DRIP_WATER, x + rand.nextInt(12) + rand.nextDouble(), y + rand.nextInt(11) + rand.nextDouble(), z - rand.nextInt(11) + rand.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
	}
	
	@Override
	public float getEyeHeight()
	{
		return 4.8F;
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
	
	@Override
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);
        this.opendoors();
        
        BlockPos spawnPos = this.getSpawnLocation().add(0, 1, -15);
        EntityAlyxWater alyx = new EntityAlyxWater(this.world, spawnPos.getX(), spawnPos.getY() - 1, spawnPos.getZ() + 9);
        alyx.setPosition(spawnPos.getX() + 0.5D, spawnPos.getY(), spawnPos.getZ() + 0.5D);
        this.world.spawnEntity(alyx);
        
        this.sayChatLine(7);
        this.sayChatLine(8);
    }
	
	public void resetFight()
    {
    	this.setFightState(false);
    	this.setInvulState(true);
    	this.setPhase(0);
    	this.explosionPoints.clear();
    	this.explosionPointTimers.clear();
    	this.explosionCD = 0;
    	this.wallCD = 105;
    	
    	this.p1timer = 0;
    	this.p2timer = 0;
    	this.p3spacertimer = 50;
    	this.p4minionCD = 250;
    	this.p5attackCD = 100;
    	this.p5attackpatternchosen = false;
    	this.p5attacklocations = 0;
    	this.p5attackspacer = 60;
    	this.p5minionCD = 0;
    	this.p6minionCD = 0;
    	this.p6minionbossCD = 0;
    	this.p7minionbossCD = 0;
    	this.p7attackCD = 100;
    	this.p7attackpatternchosen = false;
    	this.p7attacklocations = 0;
    	this.p7attackspacer = 60;
    	
    	this.chatted1 = false;
    	this.chatted2 = false;
    	this.chatted2CD = 0;
    	this.chatted3 = false;
    	this.chatted4 = false;
    	this.chatted5 = false;
    	this.chatted6 = false;
    	this.opendoors();
    	
    	List<EntityWaterBossMeleeMinion> list = this.world.<EntityWaterBossMeleeMinion>getEntitiesWithinAABB(EntityWaterBossMeleeMinion.class, this.getEntityBoundingBox().grow(ARENA_SIZE_X, ARENA_SIZE_Y, ARENA_SIZE_Z));
    	
    	if(!list.isEmpty())
    	{
	    	for (EntityWaterBossMeleeMinion entity : list)
	        {
	    		entity.setDead();
	        }
    	}
    	
    	List<EntityWaterBossRangedMinion> list1 = this.world.<EntityWaterBossRangedMinion>getEntitiesWithinAABB(EntityWaterBossRangedMinion.class, this.getEntityBoundingBox().grow(ARENA_SIZE_X, ARENA_SIZE_Y, ARENA_SIZE_Z));
    	
    	if(!list1.isEmpty())
    	{
	    	for (EntityWaterBossRangedMinion entity : list1)
	        {
	    		entity.setDead();
	        }
    	}
    	
    	List<EntityWaterBossClone> list2 = this.world.<EntityWaterBossClone>getEntitiesWithinAABB(EntityWaterBossClone.class, this.getEntityBoundingBox().grow(ARENA_SIZE_X, ARENA_SIZE_Y, ARENA_SIZE_Z));
    	
    	if(!list2.isEmpty())
    	{
	    	for (EntityWaterBossClone entity : list2)
	        {
	    		entity.setDead();
	        }
    	}
    }
	
	public void sayChatLine(int message)
	{
		if(this.world.isRemote)
		{
			List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(ARENA_SIZE_X, ARENA_SIZE_Y, ARENA_SIZE_Z));
	    	
	    	if(!list.isEmpty())
	    	{
		    	for (EntityPlayer player : list)
		        {
		    		switch(message)
		    		{
		    		case 0:
		    			player.sendMessage(new TextComponentTranslation("message.em.waterboss.fight_initiate"));
		    			break;
		    		case 1:
		    			player.sendMessage(new TextComponentTranslation("message.em.waterboss.chat1"));
		    			break;
		    		case 2:
		    			player.sendMessage(new TextComponentTranslation("message.em.waterboss.chat2"));
		    			break;
		    		case 3:
		    			player.sendMessage(new TextComponentTranslation("message.em.waterboss.chat3"));
		    			break;
		    		case 4:
		    			player.sendMessage(new TextComponentTranslation("message.em.waterboss.chat4"));
		    			break;
		    		case 5:
		    			player.sendMessage(new TextComponentTranslation("message.em.waterboss.chat5"));
		    			break;
		    		case 6:
		    			player.sendMessage(new TextComponentTranslation("message.em.waterboss.chat6"));
		    			break;
		    		case 7:
		    			player.sendMessage(new TextComponentTranslation("message.em.waterboss.chat7"));
		    			break;
		    		case 8:
		    			player.sendMessage(new TextComponentTranslation("message.em.waterboss.voidentitydeathmessagewater"));
		    			break;
		    		}
		        }
	    	}
		}
	}
	
	public void placedoors()
    {
    	if(this.getSpawnLocation() != null)
    	{
    		BlockPos doorpos1 = this.getSpawnLocation().add(-12, 2, 0);
    		
    		this.world.setBlockState(doorpos1.add(0, -1, 2), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos1.add(0, 0, 2), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos1.add(0, 1, 2), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos1.add(0, 2, 2), Blocks.PRISMARINE.getDefaultState());
    		
    		this.world.setBlockState(doorpos1.add(0, -1, 1), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos1.add(0, 0, 1), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos1.add(0, 1, 1), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos1.add(0, 2, 1), Blocks.PRISMARINE.getDefaultState());
    		
    		this.world.setBlockState(doorpos1.add(0, -1, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos1.add(0, 0, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos1.add(0, 1, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos1.add(0, 2, 0), Blocks.PRISMARINE.getDefaultState());
    		
    		this.world.setBlockState(doorpos1.add(0, -1, -1), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos1.add(0, 0, -1), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos1.add(0, 1, -1), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos1.add(0, 2, -1), Blocks.PRISMARINE.getDefaultState());
    		
    		this.world.setBlockState(doorpos1.add(0, -1, -2), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos1.add(0, 0, -2), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos1.add(0, 1, -2), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos1.add(0, 2, -2), Blocks.PRISMARINE.getDefaultState());
    		
    		BlockPos doorpos2 = this.getSpawnLocation().add(12, 2, 0);
    		
    		this.world.setBlockState(doorpos2.add(0, -1, 2), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos2.add(0, 0, 2), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos2.add(0, 1, 2), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos2.add(0, 2, 2), Blocks.PRISMARINE.getDefaultState());
    		
    		this.world.setBlockState(doorpos2.add(0, -1, 1), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos2.add(0, 0, 1), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos2.add(0, 1, 1), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos2.add(0, 2, 1), Blocks.PRISMARINE.getDefaultState());
    		
    		this.world.setBlockState(doorpos2.add(0, -1, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos2.add(0, 0, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos2.add(0, 1, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos2.add(0, 2, 0), Blocks.PRISMARINE.getDefaultState());
    		
    		this.world.setBlockState(doorpos2.add(0, -1, -1), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos2.add(0, 0, -1), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos2.add(0, 1, -1), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos2.add(0, 2, -1), Blocks.PRISMARINE.getDefaultState());
    		
    		this.world.setBlockState(doorpos2.add(0, -1, -2), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos2.add(0, 0, -2), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos2.add(0, 1, -2), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos2.add(0, 2, -2), Blocks.PRISMARINE.getDefaultState());
    		
    		BlockPos doorpos3 = this.getSpawnLocation().add(0, 2, -12);
    		
    		this.world.setBlockState(doorpos3.add(2, -1, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos3.add(2, 0, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos3.add(2, 1, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos3.add(2, 2, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos3.add(2, 3, 0), Blocks.PRISMARINE.getDefaultState());
    		
    		this.world.setBlockState(doorpos3.add(1, -1, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos3.add(1, 0, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos3.add(1, 1, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos3.add(1, 2, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos3.add(1, 3, 0), Blocks.PRISMARINE.getDefaultState());
    		
    		this.world.setBlockState(doorpos3.add(0, -1, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos3.add(0, 0, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos3.add(0, 1, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos3.add(0, 2, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos3.add(0, 3, 0), Blocks.PRISMARINE.getDefaultState());
    		
    		this.world.setBlockState(doorpos3.add(-1, -1, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos3.add(-1, 0, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos3.add(-1, 1, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos3.add(-1, 2, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos3.add(-1, 3, 0), Blocks.PRISMARINE.getDefaultState());
    		
    		this.world.setBlockState(doorpos3.add(-2, -1, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos3.add(-2, 0, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos3.add(-2, 1, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos3.add(-2, 2, 0), Blocks.PRISMARINE.getDefaultState());
    		this.world.setBlockState(doorpos3.add(-2, 3, 0), Blocks.PRISMARINE.getDefaultState());
    	}
    }
	
	public void opendoors()
    {
    	if(this.getSpawnLocation() != null)
    	{
    		BlockPos doorpos1 = this.getSpawnLocation().add(-12, 2, 0);
    		
    		this.world.setBlockState(doorpos1.add(0, -1, 2), Blocks.STONE_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST).withProperty(BlockStairs.HALF, EnumHalf.BOTTOM));
    		this.world.setBlockToAir(doorpos1.add(0, 0, 2));
    		this.world.setBlockToAir(doorpos1.add(0, 1, 2));
    		this.world.setBlockState(doorpos1.add(0, 2, 2), Blocks.SEA_LANTERN.getDefaultState());
    		
    		this.world.setBlockToAir(doorpos1.add(0, -1, 1));
    		this.world.setBlockToAir(doorpos1.add(0, 0, 1));
    		this.world.setBlockToAir(doorpos1.add(0, 1, 1));
    		this.world.setBlockState(doorpos1.add(0, 2, 1), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, EnumType.SMOOTHBRICK).withProperty(BlockStoneSlab.HALF, EnumBlockHalf.TOP));
    		
    		this.world.setBlockToAir(doorpos1.add(0, -1, 0));
    		this.world.setBlockToAir(doorpos1.add(0, 0, 0));
    		this.world.setBlockToAir(doorpos1.add(0, 1, 0));
    		this.world.setBlockState(doorpos1.add(0, 2, 0), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, EnumType.SMOOTHBRICK).withProperty(BlockStoneSlab.HALF, EnumBlockHalf.TOP));
    		
    		this.world.setBlockToAir(doorpos1.add(0, -1, -1));
    		this.world.setBlockToAir(doorpos1.add(0, 0, -1));
    		this.world.setBlockToAir(doorpos1.add(0, 1, -1));
    		this.world.setBlockState(doorpos1.add(0, 2, -1), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, EnumType.SMOOTHBRICK).withProperty(BlockStoneSlab.HALF, EnumBlockHalf.TOP));
    		
    		this.world.setBlockState(doorpos1.add(0, -1, -2), Blocks.STONE_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST).withProperty(BlockStairs.HALF, EnumHalf.BOTTOM));
    		this.world.setBlockToAir(doorpos1.add(0, 0, -2));
    		this.world.setBlockToAir(doorpos1.add(0, 1, -2));
    		this.world.setBlockState(doorpos1.add(0, 2, -2), Blocks.SEA_LANTERN.getDefaultState());
    		
    		BlockPos doorpos2 = this.getSpawnLocation().add(12, 2, 0);
    		
    		this.world.setBlockState(doorpos2.add(0, -1, 2), Blocks.STONE_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST).withProperty(BlockStairs.HALF, EnumHalf.BOTTOM));
    		this.world.setBlockToAir(doorpos2.add(0, 0, 2));
    		this.world.setBlockToAir(doorpos2.add(0, 1, 2));
    		this.world.setBlockState(doorpos2.add(0, 2, 2), Blocks.SEA_LANTERN.getDefaultState());
    		
    		this.world.setBlockToAir(doorpos2.add(0, -1, 1));
    		this.world.setBlockToAir(doorpos2.add(0, 0, 1));
    		this.world.setBlockToAir(doorpos2.add(0, 1, 1));
    		this.world.setBlockState(doorpos2.add(0, 2, 1), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, EnumType.SMOOTHBRICK).withProperty(BlockStoneSlab.HALF, EnumBlockHalf.TOP));
    		
    		this.world.setBlockToAir(doorpos2.add(0, -1, 0));
    		this.world.setBlockToAir(doorpos2.add(0, 0, 0));
    		this.world.setBlockToAir(doorpos2.add(0, 1, 0));
    		this.world.setBlockState(doorpos2.add(0, 2, 0), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, EnumType.SMOOTHBRICK).withProperty(BlockStoneSlab.HALF, EnumBlockHalf.TOP));
    		
    		this.world.setBlockToAir(doorpos2.add(0, -1, -1));
    		this.world.setBlockToAir(doorpos2.add(0, 0, -1));
    		this.world.setBlockToAir(doorpos2.add(0, 1, -1));
    		this.world.setBlockState(doorpos2.add(0, 2, -1), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, EnumType.SMOOTHBRICK).withProperty(BlockStoneSlab.HALF, EnumBlockHalf.TOP));
    		
    		this.world.setBlockState(doorpos2.add(0, -1, -2), Blocks.STONE_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST).withProperty(BlockStairs.HALF, EnumHalf.BOTTOM));
    		this.world.setBlockToAir(doorpos2.add(0, 0, -2));
    		this.world.setBlockToAir(doorpos2.add(0, 1, -2));
    		this.world.setBlockState(doorpos2.add(0, 2, -2), Blocks.SEA_LANTERN.getDefaultState());
    		
    		BlockPos doorpos3 = this.getSpawnLocation().add(0, 2, -12);
    		
    		this.world.setBlockToAir(doorpos3.add(2, -1, 0));
    		this.world.setBlockToAir(doorpos3.add(2, 0, 0));
    		this.world.setBlockToAir(doorpos3.add(2, 1, 0));
    		this.world.setBlockToAir(doorpos3.add(2, 2, 0));
    		this.world.setBlockState(doorpos3.add(2, 3, 0), Blocks.STONE_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH).withProperty(BlockStairs.HALF, EnumHalf.TOP));
    		
    		this.world.setBlockToAir(doorpos3.add(1, -1, 0));
    		this.world.setBlockToAir(doorpos3.add(1, 0, 0));
    		this.world.setBlockToAir(doorpos3.add(1, 1, 0));
    		this.world.setBlockToAir(doorpos3.add(1, 2, 0));
    		this.world.setBlockState(doorpos3.add(1, 3, 0), Blocks.STONE_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH).withProperty(BlockStairs.HALF, EnumHalf.TOP));
    		
    		this.world.setBlockToAir(doorpos3.add(0, -1, 0));
    		this.world.setBlockToAir(doorpos3.add(0, 0, 0));
    		this.world.setBlockToAir(doorpos3.add(0, 1, 0));
    		this.world.setBlockToAir(doorpos3.add(0, 2, 0));
    		this.world.setBlockState(doorpos3.add(0, 3, 0), Blocks.STONE_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH).withProperty(BlockStairs.HALF, EnumHalf.TOP));
    		
    		this.world.setBlockToAir(doorpos3.add(-1, -1, 0));
    		this.world.setBlockToAir(doorpos3.add(-1, 0, 0));
    		this.world.setBlockToAir(doorpos3.add(-1, 1, 0));
    		this.world.setBlockToAir(doorpos3.add(-1, 2, 0));
    		this.world.setBlockState(doorpos3.add(-1, 3, 0), Blocks.STONE_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH).withProperty(BlockStairs.HALF, EnumHalf.TOP));
    		
    		this.world.setBlockToAir(doorpos3.add(-2, -1, 0));
    		this.world.setBlockToAir(doorpos3.add(-2, 0, 0));
    		this.world.setBlockToAir(doorpos3.add(-2, 1, 0));
    		this.world.setBlockToAir(doorpos3.add(-2, 2, 0));
    		this.world.setBlockState(doorpos3.add(-2, 3, 0), Blocks.STONE_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH).withProperty(BlockStairs.HALF, EnumHalf.TOP));
    	}
    }
	
	public void ShutOffNear()
    {
    	if(this.getSpawnLocation() != null)
    	{
    		BlockPos doorpos = this.getSpawnLocation().add(0, 10, -5);
    		
    		this.world.setBlockToAir(doorpos.add(-7, 0, 0));
	    	this.world.setBlockToAir(doorpos.add(-7, 0, -1));
	    	this.world.setBlockToAir(doorpos.add(-8, 0, 0));
	    	this.world.setBlockToAir(doorpos.add(-8, 0, -1));
    		
	    	this.world.setBlockToAir(doorpos.add(7, 0, 0));
	    	this.world.setBlockToAir(doorpos.add(7, 0, -1));
	    	this.world.setBlockToAir(doorpos.add(8, 0, 0));
	    	this.world.setBlockToAir(doorpos.add(8, 0, -1));
    	}
    }
	
	public void ShutOffFar()
    {
    	if(this.getSpawnLocation() != null)
    	{
    		BlockPos doorpos = this.getSpawnLocation().add(0, 10, 5);
	    	
    		this.world.setBlockToAir(doorpos.add(-7, 0, 0));
	    	this.world.setBlockToAir(doorpos.add(-7, 0, 1));
	    	this.world.setBlockToAir(doorpos.add(-8, 0, 0));
	    	this.world.setBlockToAir(doorpos.add(-8, 0, 1));
    		
	    	this.world.setBlockToAir(doorpos.add(7, 0, 0));
	    	this.world.setBlockToAir(doorpos.add(7, 0, 1));
	    	this.world.setBlockToAir(doorpos.add(8, 0, 0));
	    	this.world.setBlockToAir(doorpos.add(8, 0, 1));
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
    
    public int getPrePhase()
    {
        return ((Integer)this.dataManager.get(PRE_PHASE)).intValue();
    }
    
    public void setPrePhase(int state)
    {
        this.dataManager.set(PRE_PHASE, Integer.valueOf(state));
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
        EntityItem entityitem = this.dropItem(EMCoreItems.CORRODED_HARDWARE, 1);
        
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

	@Override
	public void setSwingingArms(boolean swingingArms) {}
}
