package com.water.elementmod.entity.projectile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.water.elementmod.EMCoreItems;
import com.water.elementmod.entity.monster.EntityFireSkeleton;
import com.water.elementmod.entity.monster.EntityFireZombie;
import com.water.elementmod.events.EventDrownDebuff;
import com.water.elementmod.network.PacketHandler;
import com.water.elementmod.network.PacketParticleData;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityWaterArrow extends EntityArrow
{

	public EntityWaterArrow(World worldIn) 
	{
		super(worldIn);
	}
	
	public EntityWaterArrow(World worldIn, double x, double y, double z) 
	{
		super(worldIn, x, y, z);
	}
	
	public EntityWaterArrow(World worldIn, EntityLivingBase shooter) 
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
		if(living instanceof EntityFireZombie || living instanceof EntityFireSkeleton)
		{
			living.attackEntityFrom(DamageSource.DROWN, 1.5f);
		}
		living.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 4 * 20, 1));
		living.extinguish();
		EventDrownDebuff.playerHitEntity(living, 4, 10);
		WaterParticleEffect(living, living.world);
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
					this.spawnParticles();
				}
			}
			else
			{
				this.spawnParticles();
			}
		}
	}
	
	private void spawnParticles()
	{
		for (int i = 0; i < 25; ++i)
        {
            this.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
        }
        for (int i = 0; i < 5; ++i)
        {
            this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
        }
	}
	
	public void WaterParticleEffect(EntityLivingBase target, World world)
	{
		for(int countparticles = 0; countparticles <= 18 * 2.5; ++countparticles)
		{
			Random rand = new Random();
			PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 5, target.posX + (rand.nextDouble() - 0.5D) * target.width, target.posY + rand.nextDouble() * target.height - target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * target.width, 0.0D, 0.0D,0.0D, -1), target.dimension);
			PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 39, target.posX + (rand.nextDouble() - 0.5D) * target.width, target.posY + rand.nextDouble() * target.height - target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * target.width, 0.0D, 0.0D,0.0D, -1), target.dimension);
		}
		for(int countparticles = 0; countparticles <= 60 * 2.5; ++countparticles)
		{
			Random rand = new Random();
			PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 4, target.posX + (rand.nextDouble() - 0.5D) * target.width, target.posY + rand.nextDouble() * target.height - target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * target.width, 0.0D, 0.0D,0.0D, -1), target.dimension);
		}
	}

}
