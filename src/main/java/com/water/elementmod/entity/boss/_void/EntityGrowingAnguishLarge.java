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

public class EntityGrowingAnguishLarge extends EntityLiving
{
	private static final DataParameter<Integer> TIMER = EntityDataManager.<Integer>createKey(EntityGrowingAnguishLarge.class, DataSerializers.VARINT);
	
    public EntityGrowingAnguishLarge(World worldIn)
    {
        super(worldIn);
        this.setSize(11.0F, 1.25F);
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
    	this.dataManager.register(TIMER, Integer.valueOf(3600)); // 3 minutes
    }
    
    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
    	super.writeEntityToNBT(compound);
    	compound.setInteger("Timer", this.getTimer());
    }
	
    @Override
	public void readEntityFromNBT(NBTTagCompound compound)
    {
    	super.readEntityFromNBT(compound);
    	this.setTimer(compound.getInteger("Timer"));
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	if(!this.world.isRemote)
    	{
	    	this.setTimer(this.getTimer() - 1);
	    	if(this.getTimer() <= 0)
	    	{
	    		this.isDead = true;
	    	}
    	}
    	this.getLookHelper().setLookPosition(this.posX + 1, this.posY + (double)this.getEyeHeight(), this.posZ, 100.0f, 100.0f);
    	List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox());
        for (EntityPlayer entity : list)
        {
        	entity.addPotionEffect(new PotionEffect(EMCorePotionEffects.POTION_CORRUPTION, 150, 0));
        	if(this.getTimer() % 20 == 1) entity.attackEntityFrom(DamageSource.causeMobDamage(this), 10.0F);
        }
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	return false;
    }
	
	@Override
	protected SoundEvent getDeathSound()
    {
        return SoundEvents.EVOCATION_ILLAGER_CAST_SPELL;
    }
	
	public void goInvisible()
	{
		this.setInvisible(true);
		this.setSize(0.0F, 0.0F);
	}
	
	public void goVisible()
	{
		this.setInvisible(false);
		this.setSize(16.5F, 1.25F);
	}
	
	public int getTimer()
    {
        return ((Integer)this.dataManager.get(TIMER)).intValue();
    }
    
    public void setTimer(int state)
    {
        this.dataManager.set(TIMER, Integer.valueOf(state));
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
