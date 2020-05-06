package com.water.elementmod.entity.friendly;

import com.water.elementmod.entity.ai.EntityAIMoveTo;
import com.water.elementmod.entity.projectile.EntityFireArrow;
import com.water.elementmod.particle.EnumCustomParticleTypes;
import com.water.elementmod.particle.ParticleSpawner;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.World;

public class EntityAlyx extends EntityCreature
{
	public EntityAlyx(World worldIn) 
	{
		super(worldIn);
		this.setSize(0.7F, 1.875F);
		this.setCustomNameTag("Aylx");
	}
	
	public EntityAlyx(World worldIn, double x, double y, double z, double goToX, double goToY, double goToZ) 
	{
		super(worldIn);
		this.setSize(0.7F, 1.875F);
		this.setCustomNameTag("Aylx");
		System.out.println(x + " " +y + " " +z);
		System.out.println(goToX + " " +goToY + " " +goToZ);
		this.setPosition(x, y, z);
		this.move(MoverType.PLAYER, goToX, goToY, goToZ);
	}
	
	public static void registerFixesAlyx(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntityAlyx.class);
    }
	
	@Override
	protected void initEntityAI()
    {
		this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(3, new EntityAILookIdle(this));
    }
	
	@Override
	protected void entityInit()
    {
        super.entityInit();
    }
	
	public void moveEntityTo(double x, double y, double z) {

	this.getNavigator().tryMoveToXYZ(x, y, z, 0.2D);

    }
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0F);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2000000417232513D);
	}
	
	@Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	return false;
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
	
    @Override
	public void onLivingUpdate()
    {   
        super.onLivingUpdate();
    }
	
	@Override
	protected void updateAITasks()
    {
		super.updateAITasks();
    }
	
	@Override
	public float getEyeHeight()
	{
		return 1.625F;
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
