package com.water.elementmod.entity.boss.fire;

import com.water.elementmod.network.PacketCustomParticleData;
import com.water.elementmod.network.PacketHandler;
import com.water.elementmod.particle.EnumCustomParticleTypes;
import com.water.elementmod.particle.ParticleSpawner;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityFireCrystal extends EntityLiving
{
    public int innerRotation;
    private AbstractAttributeMap attributeMap;

    public EntityFireCrystal(World worldIn)
    {
        super(worldIn);
        this.setSize(0.65F, 1.7F);
        this.innerRotation = this.rand.nextInt(100000);
    }
    
    public EntityFireCrystal(World worldIn, double x, double y, double z)
    {
        this(worldIn);
        this.setPosition(x, y, z);
    }
    
    @Override
    protected void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
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
    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	++this.innerRotation;
    	
    	if(this.rand.nextInt(100) > 92)
    	{
    		this.world.spawnParticle(EnumParticleTypes.LAVA, (this.posX + 0.01D) + (this.rand.nextDouble() - 0.5D) * ((double)this.width + 0.2D), (this.posY + 0.35D) + this.rand.nextDouble() * (double)this.height - 0.35D, (this.posZ - 0.15D) + (this.rand.nextDouble() - 0.3D) * ((double)this.width + 0.2D), 0.0D, 0.0D, 0.0D);
    	}
    	
    	if (this.world.isRemote)
        {
            ParticleSpawner.spawnParticle(EnumCustomParticleTypes.LAVA, (this.posX + 0.01D) + (this.rand.nextDouble() - 0.5D) * ((double)this.width + 0.2D), (this.posY + 0.35D) + this.rand.nextDouble() * (double)this.height - 0.35D, (this.posZ - 0.15D) + (this.rand.nextDouble() - 0.3D) * ((double)this.width + 0.2D), 0.0D, 0.0D, 0.0D);
        }
    }
    
    public void FireRingEffect()
    {
    	double radius = 4.0D;
    	for(double r = 0.6D; r <= radius; r += 0.2D)
		{
			for(float i = 0.0F; i < 360.0F; i += 15.0F)
			{
				double deltaX = Math.cos(Math.toRadians(i))*r + rand.nextDouble();
				double deltaZ = -Math.sin(Math.toRadians(i))*r + rand.nextDouble();
				double finalX = this.posX - 0.5D + deltaX;
				double finalZ = this.posZ - 0.5D + deltaZ;
			    
				if(rand.nextDouble() < 0.1D) this.world.spawnParticle(EnumParticleTypes.FLAME, finalX, this.posY + 0.15D, finalZ, 0.0D, 0.0D, 0.0D);
			}
		}
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	return false;
    }
    
    public void Explode()
    {
    	this.world.createExplosion(this, this.posX, this.posY + (double)(this.height / 16.0F), this.posZ, 0F, true);
    	this.isDead = true;
    }
    
    @Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.BLOCK_LAVA_POP;
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
