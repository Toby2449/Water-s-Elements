package com.water.elementmod.entity.boss._void;

import java.util.List;

import com.water.elementmod.EMCorePotionEffects;
import com.water.elementmod.entity.EntityBossMob;
import com.water.elementmod.network.PacketCarapaceParticleCircle;
import com.water.elementmod.network.PacketHandler;
import com.water.elementmod.particle.EnumCustomParticleTypes;
import com.water.elementmod.particle.ParticleSpawner;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.World;

public class EntityVoidSpectralLarge extends EntityBossMob
{
	public EntityVoidSpectralLarge(World worldIn) 
	{
		super(worldIn);
		this.setSize(1.0F, 2.5F);
	}
	
	public static void registerFixesBoss(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntityVoidSpectralLarge.class);
    }
	
	@Override
	protected void initEntityAI()
    {
		this.tasks.addTask(0, new EntityAIAttackMelee(this, 0.55D, true));
		this.tasks.addTask(1, new EntityAIWander(this, 0.45D));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    }
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0F);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.55D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(9999999999999.0F);
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
	
    @Override
	public void onLivingUpdate()
    {   
    	super.onLivingUpdate();
    	
    	if (this.world.isRemote)
        {
        	if(this.rand.nextInt(100) > 20)
        	{
        		ParticleSpawner.spawnParticle(EnumCustomParticleTypes.DARK_PURPLE_SMOKE, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + 0.25D + this.rand.nextDouble() * ((double)this.height), this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
        		ParticleSpawner.spawnParticle(EnumCustomParticleTypes.DARK_PURPLE_SMOKE_BLOCK, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + 1.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
            }
        }
		
		this.ticksExisted++;
    }
	
	@Override
	protected void updateAITasks()
    {
		super.updateAITasks();
    }
	
	@Override
	public float getEyeHeight()
	{
		return 2.0F;
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
     * Called when the entity is attacked.
     */
	@Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	return super.attackEntityFrom(source, amount);
    }
	
    @Override
    public void onDeath(DamageSource cause)
    {
    	List<EntityPlayer> InsanityList = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE));
		for (EntityPlayer player : InsanityList)
        {
			if(player.isPotionActive(EMCorePotionEffects.POTION_INSANITY))
			{
				player.addPotionEffect(new PotionEffect(EMCorePotionEffects.POTION_INSANITY, player.getActivePotionEffect(EMCorePotionEffects.POTION_INSANITY).getDuration(), player.getActivePotionEffect(EMCorePotionEffects.POTION_INSANITY).getAmplifier() + 1));
			}
			else
			{
				player.addPotionEffect(new PotionEffect(EMCorePotionEffects.POTION_INSANITY, 300, 0));
			}
			PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceParticleCircle(this, this.world, 9, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, 15), this.dimension);
        }
		
        super.onDeath(cause);
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
