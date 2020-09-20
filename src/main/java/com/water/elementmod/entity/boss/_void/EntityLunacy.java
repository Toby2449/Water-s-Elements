package com.water.elementmod.entity.boss._void;

import java.util.List;
import java.util.Random;

import com.water.elementmod.entity.EntityBossMob;
import com.water.elementmod.network.PacketCarapaceParticleCircle;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public class EntityLunacy extends EntityBossMob
{
	private static final DataParameter<Integer> RAGE = EntityDataManager.<Integer>createKey(EntityLunacy.class, DataSerializers.VARINT);
	private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.PINK, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);
	private static final DataParameter<Integer> NUM_OF_PLAYERS = EntityDataManager.<Integer>createKey(EntityLunacy.class, DataSerializers.VARINT);
	private final EntityAIAttackMelee aiMeleeAttack = new EntityAIAttackMelee(this, 0.5D, true);
	public boolean RageActivated = false;
	public boolean casting = false;
	private int castTimer = _ConfigEntityVoidEntity.LUNACY_CAST_TIMER;
	private int anguishTimer = _ConfigEntityVoidEntity.LUNACY_ANGUISH_TIMER;
	private boolean scaledHP = false;
	private int AttackSoundDelay = _ConfigEntityCarapace.ATTACK_SOUND_DELAY;
	
	public EntityLunacy(World worldIn)
	{
		super(worldIn);
		this.setSize(1.6F, 4.5F);
	}
	
	public static void registerFixesBoss(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntityLunacy.class);
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
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(9999999999999.0F);
	}
	
	@Override
	protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(RAGE, Integer.valueOf(_ConfigEntityVoidEntity.LUNACY_RAGE_TIMER));
        this.dataManager.register(NUM_OF_PLAYERS, Integer.valueOf(1));
    }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Rage", this.getRageTimer());
        compound.setInteger("Anguish", this.anguishTimer);
        compound.setInteger("NumOfPlayers", this.getNumOfPlayers());
    }
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setRageTimer(compound.getInteger("Rage"));
        this.anguishTimer = compound.getInteger("Anguish");
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
    	
    	this.AttackSoundDelay--;
		if(this.AttackSoundDelay <= 0)
		{
			this.AttackSoundDelay = _ConfigEntityCarapace.ATTACK_SOUND_DELAY;
			this.playAttackSound();
		}
		
    	if(!this.world.isRemote)
    	{
    		this.setRageTimer(this.getRageTimer() - 1);
    		if(this.getRageTimer() <= 0)
    		{
    			this.RageActivated = true;
    		}
    		
    		this.anguishTimer--;
    		if(this.anguishTimer <= 0)
    		{
    			this.anguishTimer = _ConfigEntityVoidEntity.LUNACY_ANGUISH_TIMER;
    			this.playVoidMagicCastSound(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
    			this.spawnAnguish(this.posX, this.posY, this.posZ);
    		}
    		
    		this.castTimer--;
    		if(this.castTimer == 30)
    		{
    			this.casting = true;
    			this.playImpactSound(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), 3.5F);
				PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceParticleCircle(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, 15), this.dimension);
				List<EntityPlayer> players = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(100, 100, 100));
				for (EntityPlayer player : players)
		        {
					player.attackEntityFrom(DamageSource.MAGIC, 4.0f);
		        }
    		}
    		else if(this.castTimer == 15)
    		{
    			this.playImpactSound(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), 3.5F);
				PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceParticleCircle(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, 15), this.dimension);
				List<EntityPlayer> players = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(100, 100, 100));
				for (EntityPlayer player : players)
		        {
					player.attackEntityFrom(DamageSource.MAGIC, 4.0f);
		        }
    		}
    		else if(this.castTimer <= 0)
    		{
    			this.casting = false;
    			this.castTimer = _ConfigEntityVoidEntity.LUNACY_CAST_TIMER;
    			this.playImpactSound(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), 3.5F);
				PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceParticleCircle(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, 15), this.dimension);
				List<EntityPlayer> players = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(100, 100, 100));
				for (EntityPlayer player : players)
		        {
					player.attackEntityFrom(DamageSource.MAGIC, 4.0f);
		        }
    		}
    	}
    	else
    	{
    		this.setRageTimer(this.getRageTimer() - 1);
    		if(this.getRageTimer() <= 0)
    		{
    			this.RageActivated = true;
    		}
    		
    		this.castTimer--;
    		if(this.castTimer <= 30 && this.castTimer >= 1)
    		{
    			this.casting = true;
    			for (int i = 0; i < 100; ++i)
	            {
	            	ParticleSpawner.spawnParticle(EnumCustomParticleTypes.DARK_PURPLE_SMOKE_BLOCK, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
	            }
    		}
    		if(this.castTimer <= 0)
    		{
    			this.casting = false;
    			this.castTimer = _ConfigEntityVoidEntity.LUNACY_CAST_TIMER;
    		}
    	}
    	
    	if(!this.world.isRemote)
    	{
    		if(!this.scaledHP)
    		{
    			this.scaledHP = true;
	    		int numOfPlayers = 0;
	        	List<EntityPlayer> players = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE).offset(0, -5, 0));
		        if(!players.isEmpty())
		        {
		        	for (EntityPlayer entity : players)
			        {
		        		numOfPlayers++;
			        }
		        }
		        
		        this.setNumOfPlayers(numOfPlayers);
    		}
    	}
    	
    	this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(this.getDamage(10.0D));
    	this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(this.getArmor(5.0D));
		this.ticksExisted++;
        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }
	
	@Override
	protected void updateAITasks()
    {
		if(this.casting)
		{
			this.tasks.removeTask(aiMeleeAttack);
		}
		else
		{
			this.tasks.addTask(1, aiMeleeAttack);
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
		return EMSoundHandler.CARAPACE_WOUND;
	}
	
	@Override
	protected SoundEvent getDeathSound()
    {
        return EMSoundHandler.CARAPACE_DEATH;
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
		return super.attackEntityFrom(source, this.calculateHealthReduction(amount));
    }
	
	public float calculateHealthReduction(float amount)
    {
    	return amount * (1000 / (_ConfigEntityVoidEntity.LUNACY_BASE_HP + (_ConfigEntityVoidEntity.LUNACY_HP_SCALE_AMOUNT * (this.getNumOfPlayers() - 1))));
    }
    
    @Override
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);
    }
    
    public double getDamage(double base)
    {
    	if(this.RageActivated)
    	{
    		return base * 5;
    	}
    	else
    	{
    		return base;
    	}
    }
    
    public double getArmor(double base)
    {
    	if(this.RageActivated)
    	{
    		return base * 5;
    	}
    	else
    	{
    		return base;
    	}
    }
    
    public void spawnAnguish(double x, double y, double z)
	{
    	EntityVEAnguish entity = new EntityVEAnguish(this.world);
       	entity.setPosition(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
       	entity.enablePersistence();
   		this.world.spawnEntity(entity);
	}
    
    public void playAttackSound()
    {
    	Random rand = new Random();
    	int r = rand.nextInt(100);
    	if(r < 70)
    	{
    		this.world.playSound((EntityPlayer)null, this.getPosition(), EMSoundHandler.CARAPACE_ATTACK, SoundCategory.HOSTILE, 3.5F, 1.0f);
    	}
    	else
    	{
    		this.world.playSound((EntityPlayer)null, this.getPosition(), EMSoundHandler.CARAPACE_SHOUT, SoundCategory.HOSTILE, 3.5F, 1.0f);
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
    
    public int getRageTimer()
    {
        return ((Integer)this.dataManager.get(RAGE)).intValue();
    }
    
    public void setRageTimer(int state)
    {
        this.dataManager.set(RAGE, Integer.valueOf(state));
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
    	return 0;
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
