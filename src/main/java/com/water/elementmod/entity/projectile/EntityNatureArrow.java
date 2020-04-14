package com.water.elementmod.entity.projectile;

import java.util.Random;

import com.water.elementmod.EMCoreItems;
import com.water.elementmod.entity.monster.EntityFireSkeleton;
import com.water.elementmod.entity.monster.EntityFireZombie;
import com.water.elementmod.entity.monster.EntityWaterSkeleton;
import com.water.elementmod.entity.monster.EntityWaterZombie;
import com.water.elementmod.network.PacketCustomParticleData;
import com.water.elementmod.network.PacketHandler;
import com.water.elementmod.network.PacketParticleData;
import com.water.elementmod.particle.EnumCustomParticleTypes;
import com.water.elementmod.particle.ParticleSpawner;
import com.water.elementmod.events.EventSuperPoison;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityNatureArrow extends EntityArrow
{

	public EntityNatureArrow(World worldIn) 
	{
		super(worldIn);
	}
	
	public EntityNatureArrow(World worldIn, double x, double y, double z) 
	{
		super(worldIn, x, y, z);
	}
	
	public EntityNatureArrow(World worldIn, EntityLivingBase shooter) 
	{
		super(worldIn, shooter);
	}

	@Override
	protected ItemStack getArrowStack() 
	{
		return new ItemStack(EMCoreItems.NATURE_ARROW);
	}
	
	@Override
	protected void arrowHit(EntityLivingBase living)
	{
		super.arrowHit(living);
		if(living instanceof EntityWaterZombie || living instanceof EntityWaterSkeleton)
		{
			living.attackEntityFrom(DamageSource.MAGIC, 1.5f);
		}
		EventSuperPoison.playerHitEntity(living, 4);
		NatureParticleHitEffect(living, living.world);
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if(this.world.isRemote)
		{
			if(this.inGround)
			{
				if(this.timeInGround % 5 == 0)
				{
					this.spawnParticles(1);
				}
			}
			else
			{
				this.spawnParticles(2);
			}
		}
	}
	
	private void spawnParticles(int particleCount)
	{
		for (int i = 0; i < 4 * particleCount; ++i)
        {
			ParticleSpawner.spawnParticle(EnumCustomParticleTypes.LEAF, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
        }	
    }
	
	public boolean NatureParticleHitEffect(EntityLivingBase target, World world)
	{
		for(int countparticles = 0; countparticles <= 15 * 2.5; ++countparticles)
		{
			Random rand = new Random();
			PacketHandler.INSTANCE.sendToDimension(new PacketCustomParticleData(target, world, 0, target.posX + (rand.nextDouble() - 0.5D) * (double)target.width, target.posY + rand.nextDouble() * (double)target.height - 0.25D, target.posZ + (rand.nextDouble() - 0.5D) * (double)target.width, 0.0D, 0.0D, 0.0D, -1), target.dimension);
		}
		return true;
	}
	
	@Override
	public void setEnchantmentEffectsFromEntity(EntityLivingBase p_190547_1_, float p_190547_2_)
    {
        int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER, p_190547_1_);
        int j = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PUNCH, p_190547_1_);
        this.setDamage((double)(p_190547_2_ * 2.0F) + this.rand.nextGaussian() * 0.25D + (double)((float)this.world.getDifficulty().getDifficultyId() * 0.11F));

        if (i > 0)
        {
            this.setDamage(this.getDamage() + (double)i * 0.5D + 0.5D);
        }

        if (j > 0)
        {
            this.setKnockbackStrength(j);
        }
    }

}
