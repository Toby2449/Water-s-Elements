package com.water.elementmod.entity.boss._void;

import java.util.List;

import com.water.elementmod.EMCorePotionEffects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityCarapaceAnguish extends EntityLiving
{
	private static final DataParameter<Integer> TIMER = EntityDataManager.<Integer>createKey(EntityCarapaceAnguish.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> DESPAWN = EntityDataManager.<Boolean>createKey(EntityCarapaceAnguish.class, DataSerializers.BOOLEAN);
	
    public EntityCarapaceAnguish(World worldIn)
    {
        super(worldIn);
        this.setSize(16.5F, 0.1F);
        this.isImmuneToFire = true;
    }
    
    public EntityCarapaceAnguish(World worldIn, boolean despawn)
    {
        this(worldIn);
        this.setSize(16.5F, 0.1F);
        this.setDespawn(despawn);
        this.isImmuneToFire = true;
    }
    
    @Override
	protected void initEntityAI() {}
    
    @Override
    protected void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(999.0F);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(999999999D);
    }
    
    @Override
    protected void entityInit()
    {
    	super.entityInit();
    	this.dataManager.register(TIMER, Integer.valueOf(4200)); // 4 minutes
    	this.dataManager.register(DESPAWN, Boolean.valueOf(true));
    }
    
    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
    	super.writeEntityToNBT(compound);
    	compound.setInteger("Timer", this.getTimer());
    	compound.setBoolean("Despawn", this.isGoingToDespawn());
    }
	
    @Override
	public void readEntityFromNBT(NBTTagCompound compound)
    {
    	super.readEntityFromNBT(compound);
    	this.setTimer(compound.getInteger("Timer"));
    	this.setDespawn(compound.getBoolean("Despawn"));
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	this.setTimer(this.getTimer() - 1);
    	if(this.getTimer() <= 0 && this.isGoingToDespawn())
    	{
    		this.isDead = true;
    	}
    	this.getLookHelper().setLookPosition(this.posX + 1, this.posY + (double)this.getEyeHeight(), this.posZ, 100.0f, 100.0f);
    	if(!this.isInvisible())
    	{
	    	List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox());
	        for (EntityPlayer entity : list)
	        {
	        	entity.addPotionEffect(new PotionEffect(EMCorePotionEffects.POTION_CORRUPTION, 150, 0));
	        	if(this.getTimer() % 20 == 1) entity.attackEntityFrom(DamageSource.MAGIC, 7.5F);
	        }
    	}
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	return false;
    }
	
	public int getTimer()
    {
        return ((Integer)this.dataManager.get(TIMER)).intValue();
    }
    
    public void setTimer(int state)
    {
        this.dataManager.set(TIMER, Integer.valueOf(state));
    }
    
    public boolean isGoingToDespawn()
    {
        return ((Boolean)this.dataManager.get(DESPAWN)).booleanValue();
    }
    
    public void setDespawn(boolean state)
    {
        this.dataManager.set(DESPAWN, Boolean.valueOf(state));
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
