package com.water.elementmod.entity.boss._void;

import java.util.List;

import com.water.elementmod.EMCorePotionEffects;
import com.water.elementmod.util.handlers.EMLootTableHandler;

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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityVoidEntityChest extends EntityLiving
{
    public EntityVoidEntityChest(World worldIn)
    {
        super(worldIn);
        this.setSize(1.0F, 1.0F);
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
    }
    
    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
    	super.writeEntityToNBT(compound);
    }
	
    @Override
	public void readEntityFromNBT(NBTTagCompound compound)
    {
    	super.readEntityFromNBT(compound);
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(500, 100, 500));
    	for (EntityPlayer entity : list)
        {
    		this.attackEntityFrom(DamageSource.causePlayerDamage(entity), 999999999.9F);
        }
    }
    
    @Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return null;
	}
	
	@Override
	protected SoundEvent getDeathSound()
    {
        return null;
    }
	
	@Override
    protected ResourceLocation getLootTable()
    {
        return EMLootTableHandler.VOID_ENTITY;
    }
    
    @Override
    protected int getExperiencePoints(EntityPlayer player)
    {
    	return 750;
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
