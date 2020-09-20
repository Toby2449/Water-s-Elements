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

public class EntityVEAnguish extends EntityLiving
{
	private static final DataParameter<Boolean> INVISIBLE = EntityDataManager.<Boolean>createKey(EntityVEAnguish.class, DataSerializers.BOOLEAN);
	
    public EntityVEAnguish(World worldIn)
    {
        super(worldIn);
        this.setSize(11.0F, 0.1F);
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
    	this.dataManager.register(INVISIBLE, Boolean.valueOf(false));
    }
    
    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
    	super.writeEntityToNBT(compound);
    	compound.setBoolean("Invisble", this.isInvisible());
    }
	
    @Override
	public void readEntityFromNBT(NBTTagCompound compound)
    {
    	super.readEntityFromNBT(compound);
    	this.setInvisble(compound.getBoolean("Invisible"));
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	this.getLookHelper().setLookPosition(this.posX + 1, this.posY + (double)this.getEyeHeight(), this.posZ, 100.0f, 100.0f);
    	if(!this.isInvisible())
    	{
	    	List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox());
	        for (EntityPlayer entity : list)
	        {
	        	entity.addPotionEffect(new PotionEffect(EMCorePotionEffects.POTION_CORRUPTION, 150, 0));
	        	if(this.ticksExisted % 20 == 1) entity.attackEntityFrom(DamageSource.MAGIC, 5.0F);
	        }
    	}
        
    	List<EntityVoidEntity> boss = this.world.<EntityVoidEntity>getEntitiesWithinAABB(EntityVoidEntity.class, this.getEntityBoundingBox().grow(100.0D, 100.0D, 100.0D));
        if(!boss.isEmpty())
        {
        	for (EntityVoidEntity entity : boss)
            {
        		if(entity.getPhase() == 1 || entity.getPhase() == 4)
        		{
        			this.setSize(11.0F, 0.1F);
        			this.setInvisble(false);
        		}
        		else
        		{
        			this.setSize(11.0F, 0.1F);
        			this.setInvisble(true);
        		}
            }
        }
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	return false;
    }
    
    public boolean isInvisible()
    {
        return ((Boolean)this.dataManager.get(INVISIBLE)).booleanValue();
    }
    
    public void setInvisble(boolean state)
    {
        this.dataManager.set(INVISIBLE, Boolean.valueOf(state));
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
