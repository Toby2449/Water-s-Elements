package com.water.elementmod.entity.boss._void;

import java.util.List;
import java.util.Random;

import com.water.elementmod.entity.ai.EntityAIMoveTo;
import com.water.elementmod.network.PacketCarapaceParticleCircle;
import com.water.elementmod.network.PacketCarapacePortalParticles;
import com.water.elementmod.network.PacketCustomParticleData;
import com.water.elementmod.network.PacketHandler;
import com.water.elementmod.particle.EnumCustomParticleTypes;
import com.water.elementmod.particle.ParticleSpawner;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityBlueOrb extends EntityMob
{
    public int innerRotation;
    private AbstractAttributeMap attributeMap;
    private BlockPos destination = null;

    public EntityBlueOrb(World worldIn)
    {
        super(worldIn);
        this.setSize(1.25F, 2.2F);
        this.innerRotation = this.rand.nextInt(100000);
        this.isImmuneToFire = true;
    }
    
    public EntityBlueOrb(World worldIn, double x, double y, double z)
    {
        this(worldIn);
        this.setSize(1.25F, 2.2F);
        this.destination = new BlockPos(x, y, z);
        this.innerRotation = this.rand.nextInt(100000);
        this.isImmuneToFire = true;
    }
    
    @Override
    protected void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(125.0F);
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
    	compound.setIntArray("Destination", new int[] {this.destination.getX(), this.destination.getY(), this.destination.getZ()});
    }
	
    @Override
	public void readEntityFromNBT(NBTTagCompound compound)
    {
    	super.readEntityFromNBT(compound);
    	int[] pos = compound.getIntArray("Destination");
        BlockPos blockpos = new BlockPos(pos[0], pos[1], pos[2]);
        this.destination = blockpos;
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	++this.innerRotation;
    	List<EntityCarapace> list = this.world.<EntityCarapace>getEntitiesWithinAABB(EntityCarapace.class, this.getEntityBoundingBox());
        for (EntityCarapace entity : list)
        {
        	entity.setBlueBuff(true);
        	SoundEvent soundevent = this.getDeathSound();
            this.playSound(soundevent, this.getSoundVolume(), this.getSoundPitch());
            PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceParticleCircle(this, this.world, 8, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 15), this.dimension);
        	this.setDead();
        }
    }
    
    @Override
	protected void updateAITasks()
    {
		super.updateAITasks();
		
		if(this.destination != null)
		{
			this.tasks.addTask(0, new EntityAIMoveTo(this, 0.175D, 50, destination.getX(), destination.getY(), destination.getZ()));
    	}
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	Entity entity = source.getImmediateSource();
    	if(entity instanceof EntityPlayer)
    	{
    		this.setHealth(this.getHealth() - amount);
    		this.BlueSmokeParticleEffect(this, this.world);
        	if(this.getHealth() <= 0.0F)
        	{
        		SoundEvent soundevent = this.getDeathSound();
                this.playSound(soundevent, this.getSoundVolume(), this.getSoundPitch());
                this.orderDeathToPurple();
                this.setDead();
        	}
        	else
        	{
	        	SoundEvent soundevent = this.getHurtSound(source);
	            this.playSound(soundevent, this.getSoundVolume(), this.getSoundPitch());
        	}
    	}
    	return false;
    }
    
    public void die()
    {
    	List<EntityCarapace> list = this.world.<EntityCarapace>getEntitiesWithinAABB(EntityCarapace.class, this.getEntityBoundingBox().grow(100.0D, 100.0D, 100.0D));
        for (EntityCarapace entity : list)
        {
        	entity.setBlueBuff(true);
        }
    	SoundEvent soundevent = this.getDeathSound();
        this.playSound(soundevent, this.getSoundVolume(), this.getSoundPitch());
        PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceParticleCircle(this, this.world, 8, this.posX, this.posY, this.posZ, 0.0D, 0.0D,0.0D, 15), this.dimension);
    	this.setDead();
    }
    
    public void orderDeathToPurple()
    {
    	List<EntityPurpleOrb> list = this.world.<EntityPurpleOrb>getEntitiesWithinAABB(EntityPurpleOrb.class, this.getEntityBoundingBox().grow(100.0D, 100.0D, 100.0D));
        for (EntityPurpleOrb entity : list)
        {
        	entity.die();
        }
    }
    
    @Override
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);
    }
    
    public void BlueSmokeParticleEffect(EntityLivingBase target, World world)
	{
    	for(int countparticles = 0; countparticles <= 30; ++countparticles)
    	{
    		Random rand = new Random();
    		PacketHandler.INSTANCE.sendToDimension(new PacketCustomParticleData(target, world, 8, target.posX + (rand.nextDouble() - 0.5D) * (double)target.width, target.posY + rand.nextDouble() * (double)target.height - 0.25D, target.posZ + (rand.nextDouble() - 0.5D) * (double)target.width, 0.0D, 0.0D, 0.0D, -1), target.dimension);
    	}
    }
    
    public void RingExplosionAnimation(double x, double y, double z, double radius, World world)
	{
		Random rand = new Random();
		for(int m = 0; m < 5; m++)
		{
			for(double r = 0.6D; r <= radius; r += 0.45D)
			{
				for(float i = 0.0F; i < 360.0F; i += 15.0F)
				{
					double deltaX = Math.cos(Math.toRadians(i))*r + rand.nextDouble();
					double deltaZ = -Math.sin(Math.toRadians(i))*r + rand.nextDouble();
					double finalX = x - 0.5D + deltaX;
					double finalZ = z - 0.5D + deltaZ;
				    
					ParticleSpawner.spawnParticle(EnumCustomParticleTypes.BLUE_SMOKE, finalX, y + 0.15D, finalZ, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}
    
    @Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundEvents.EVOCATION_ILLAGER_CAST_SPELL;
	}
	
	@Override
	protected SoundEvent getDeathSound()
    {
        return SoundEvents.BLOCK_GLASS_BREAK;
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
