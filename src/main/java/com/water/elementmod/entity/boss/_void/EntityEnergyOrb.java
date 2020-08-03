package com.water.elementmod.entity.boss._void;

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
import net.minecraft.init.MobEffects;
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
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.end.DragonFightManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityEnergyOrb extends EntityLiving
{
    public int innerRotation;
    private AbstractAttributeMap attributeMap;

    public EntityEnergyOrb(World worldIn)
    {
        super(worldIn);
        this.setSize(1.0F, 1.0F);
        this.innerRotation = this.rand.nextInt(100000);
    }
    
    public EntityEnergyOrb(World worldIn, double x, double y, double z)
    {
        this(worldIn);
        this.setPosition(x, y, z);
    }
    
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
    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	++this.innerRotation;
    	this.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 999999999));
    	
    	List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox());
        for (EntityPlayer entity : list)
        {
        	SoundEvent soundevent = this.getDeathSound();
            this.playSound(soundevent, this.getSoundVolume(), this.getSoundPitch());
            this.setDead();
        }
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	return false;
    }
    
    @Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundEvents.BLOCK_GRASS_BREAK;
	}
	
	@Override
	protected SoundEvent getDeathSound()
    {
        return SoundEvents.EVOCATION_ILLAGER_CAST_SPELL;
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
