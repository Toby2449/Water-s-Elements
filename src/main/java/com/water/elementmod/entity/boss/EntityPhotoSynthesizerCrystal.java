package com.water.elementmod.entity.boss;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.base.Optional;
import com.water.elementmod.network.PacketCustomParticleData;
import com.water.elementmod.network.PacketHandler;
import com.water.elementmod.particle.EnumCustomParticleTypes;
import com.water.elementmod.particle.ParticleSpawner;

import ibxm.Player;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.end.DragonFightManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityPhotoSynthesizerCrystal extends EntityLiving
{
	private static final DataParameter<Boolean> IS_INVURNERABLE = EntityDataManager.<Boolean>createKey(EntityPhotoSynthesizerCrystal.class, DataSerializers.BOOLEAN);
    public int innerRotation;
    private AbstractAttributeMap attributeMap;

    public EntityPhotoSynthesizerCrystal(World worldIn)
    {
        super(worldIn);
        this.setSize(2.0F, 2.0F);
        this.innerRotation = this.rand.nextInt(100000);
    }
    
    public EntityPhotoSynthesizerCrystal(World worldIn, double x, double y, double z)
    {
        this(worldIn);
        this.setPosition(x, y, z);
    }
    
    @Override
    protected void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(35.0F);
    }
    
    @Override
    protected void entityInit()
    {
    	super.entityInit();
    	this.dataManager.register(IS_INVURNERABLE, Boolean.valueOf(true));
    }
    
    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
    	compound.setBoolean("Invul", this.getInvulState());
    }
	
    @Override
	public void readEntityFromNBT(NBTTagCompound compound)
    {
    	this.setInvulState(compound.getBoolean("Invul"));
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	++this.innerRotation;
    	
    	if (this.world.isRemote)
        {
            ParticleSpawner.spawnParticle(EnumCustomParticleTypes.LEAF, this.posX + (this.rand.nextDouble() - 0.5D) * ((double)this.width - 0.35D), this.posY + this.rand.nextDouble() * (double)this.height - 0.8D, (this.posZ - 0.25D) + (this.rand.nextDouble() - 0.3D) * ((double)this.width - 0.5D), 0.0D, 0.0D, 0.0D);
        }
    	
    	List<EntityNatureBoss> list = this.world.<EntityNatureBoss>getEntitiesWithinAABB(EntityNatureBoss.class, this.getEntityBoundingBox().grow(50.0D, 50.0D, 50.0D));
        
        for (EntityNatureBoss entity : list)
        {
        	if(entity.isFightActivated())
        	{
        		this.setInvulState(false);
        	}
        	else
        	{
        		this.setInvulState(true);
        	}
        }
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	Entity entity = source.getImmediateSource();
    	if(this.getInvulState() == false)
    	{
	    	if(entity instanceof EntityPlayer)
	    	{
	    		this.setHealth(this.getHealth() - amount);
	        	NatureParticleHitEffect(this, this.getEntityWorld());
	        	if(this.getHealth() <= 0.0F)
	        	{
	        		SoundEvent soundevent = this.getDeathSound();
	                this.playSound(soundevent, this.getSoundVolume(), this.getSoundPitch());
	                this.setDead();
	        	}
	        	else
	        	{
		        	SoundEvent soundevent = this.getHurtSound(source);
		            this.playSound(soundevent, this.getSoundVolume(), this.getSoundPitch());
	        	}
	    	}
    	}
    	return false;
    }
    
    public boolean NatureParticleHitEffect(EntityLivingBase target, World world)
	{
		for(int countparticles = 0; countparticles <= 30; ++countparticles)
		{
			Random rand = new Random();
			PacketHandler.INSTANCE.sendToDimension(new PacketCustomParticleData(target, world, 0, target.posX + (rand.nextDouble() - 0.5D) * (double)target.width, target.posY + rand.nextDouble() * (double)target.height - 0.25D, target.posZ + (rand.nextDouble() - 0.5D) * (double)target.width, 0.0D, 0.0D, 0.0D, -1), target.dimension);
		}
		return true;
	}
    
    @Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundEvents.BLOCK_GRASS_BREAK;
	}
	
	@Override
	protected SoundEvent getDeathSound()
    {
        return SoundEvents.BLOCK_GLASS_BREAK;
    }
	
	public boolean getInvulState()
    {
        return ((Boolean)this.dataManager.get(IS_INVURNERABLE)).booleanValue();
    }

    public void setInvulState(boolean state)
    {
        this.dataManager.set(IS_INVURNERABLE, Boolean.valueOf(state));
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
