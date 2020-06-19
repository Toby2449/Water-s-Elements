package com.water.elementmod.entity.boss.water;

import java.util.List;
import java.util.Random;

import com.water.elementmod.entity.projectile.EntityIceBall;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityWaterBossMeleeMinion extends EntityMob
{
	public EntityWaterBossMeleeMinion(World worldIn) 
	{
		super(worldIn);
		this.isImmuneToFire = true;
		this.setSize(0.75F, 1.5F);
	}
	
	public static void registerFixesBoss(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntityWaterBossMeleeMinion.class);
    }
	
	@Override
	protected void initEntityAI()
    {
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAILookIdle(this));
		this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 0.3D, true));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    }
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(17.5F);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.5D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.75D);
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
    	
    	this.ticksExisted++;
    }
	
	@Override
	protected void updateAITasks()
    {
		super.updateAITasks();
    }
	
	@Override
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);
        
        if(this.world.isRemote) WaterExplosion(this.posX, this.posY, this.posZ, 1, 3, this.world);
        
        if(!this.world.isRemote)
        {
	        List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(0.25D, 2.0D, 0.25D));
	        for (EntityPlayer ent : list)
	        {
	        	ent.attackEntityFrom(DamageSource.causeMobDamage(this), 5.0F);
	        	ent.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 0));
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
	
	/**
     * Called when the entity is attacked.
     */
	@Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	return super.attackEntityFrom(source, amount);
    }

	@Override
	public float getEyeHeight()
	{
		return 1.4F;
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
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    @Override
    protected int getExperiencePoints(EntityPlayer player)
    {
    	return 0;
    }
}
