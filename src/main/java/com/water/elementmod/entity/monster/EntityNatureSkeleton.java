package com.water.elementmod.entity.monster;

import java.util.Calendar;

import javax.annotation.Nullable;

import com.water.elementmod.entity.AbstractElementalSkeleton;
import com.water.elementmod.entity.projectile.EntityFireArrow;
import com.water.elementmod.entity.projectile.EntityNatureArrow;
import com.water.elementmod.particle.EnumCustomParticleTypes;
import com.water.elementmod.particle.ParticleSpawner;
import com.water.elementmod.util.handlers.EMLootTableHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIZombieAttack;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntitySpectralArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityNatureSkeleton extends AbstractElementalSkeleton
{
	
	public EntityNatureSkeleton(World worldIn)
    {
        super(worldIn);
        this.setSize(0.6F, 1.99F);
    }
	
	public static void registerFixesSkeleton(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntityNatureSkeleton.class);
    }
    
    public void onLivingUpdate()
    {
    	if (this.world.isRemote)
        {
            ParticleSpawner.spawnParticle(EnumCustomParticleTypes.LEAF, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
        }

        super.onLivingUpdate();
    }
    
    @Override
    public boolean shouldBurnDuringDay()
    {
        return true;
    }
    
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0F);
    }

    @Override
    public float getEyeHeight()
    {
        return 1.74F;
    }
    
    @Override
    protected ResourceLocation getLootTable()
    {
        return EMLootTableHandler.NATURE_SKELETON;
    }
    
    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }

    @Override
    protected SoundEvent getStepSound()
    {
        return SoundEvents.ENTITY_SKELETON_STEP;
    }
    
    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);

        if (cause.getTrueSource() instanceof EntityCreeper)
        {
            EntityCreeper entitycreeper = (EntityCreeper)cause.getTrueSource();

            if (entitycreeper.getPowered() && entitycreeper.ableToCauseSkullDrop())
            {
                entitycreeper.incrementDroppedSkulls();
                this.entityDropItem(new ItemStack(Items.SKULL, 1, 0), 0.0F);
            }
        }
    }
    
    protected EntityArrow getArrow(float p_190726_1_)
    {
        ItemStack itemstack = this.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);

        EntityArrow entityarrow = new EntityNatureArrow(this.world, this);

        return entityarrow;
    }
}
