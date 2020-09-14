package com.water.elementmod.entity.boss._void;

import java.util.List;
import java.util.Random;

import com.water.elementmod.entity.EntityBossMob;
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
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public class EntitySlaveMaster extends EntityBossMob
{
	private static final DataParameter<Integer> NUM_OF_PLAYERS = EntityDataManager.<Integer>createKey(EntitySlaveMaster.class, DataSerializers.VARINT);
	private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.RED, BossInfo.Overlay.PROGRESS)).setDarkenSky(false);
	private boolean scaledHP = false;
	
	public EntitySlaveMaster(World worldIn)
	{
		super(worldIn);
		this.setSize(1.6F, 4.5F);
	}
	
	public static void registerFixesBoss(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntitySlaveMaster.class);
    }
	
	@Override
	protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 0.5D, true));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    }
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1000.0F);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.5D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(9999999999999.0F);
	}
	
	@Override
	protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(NUM_OF_PLAYERS, Integer.valueOf(1));
    }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("NumOfPlayers", this.getNumOfPlayers());
    }
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
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
		this.ticksExisted++;
		List<EntityPlayer> WeaknessList = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE));
		for (EntityPlayer ent : WeaknessList)
        {
			ent.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 20, 0));
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
		
		this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
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
     * Called when the entity is attacked.
     */
	@Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
		return super.attackEntityFrom(source, this.calculateHealthReduction(amount));
    }
	
	public float calculateHealthReduction(float amount)
    {
    	return amount * (1000 / (200.0F + (200.0F * (this.getNumOfPlayers() - 1))));
    }
    
    @Override
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);
    }
    
    @Override
    protected int getExperiencePoints(EntityPlayer player)
    {
    	return 0;
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
