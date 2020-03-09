package com.water.elementmod.entity.arrow;

import java.util.Random;

import com.water.elementmod.EMCoreItems;
import com.water.elementmod.entity.EntityNatureSkeleton;
import com.water.elementmod.entity.EntityNatureZombie;
import com.water.elementmod.entity.EntityWaterSkeleton;
import com.water.elementmod.entity.EntityWaterZombie;
import com.water.elementmod.network.PacketHandler;
import com.water.elementmod.network.PacketParticleData;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityFireArrow extends EntityArrow
{

	public EntityFireArrow(World worldIn) 
	{
		super(worldIn);
	}
	
	public EntityFireArrow(World worldIn, double x, double y, double z) 
	{
		super(worldIn, x, y, z);
	}
	
	public EntityFireArrow(World worldIn, EntityLivingBase shooter) 
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
		if(living instanceof EntityNatureZombie || living instanceof EntityNatureSkeleton)
		{
			living.attackEntityFrom(DamageSource.ON_FIRE, 1.5f);
		}
		living.setFire(4);
		FireParticleEffect(living, living.world);
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
		for (int i = 0; i < 6; ++i)
        {
            this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
        }
        
		for (int i = 0; i < 4; ++i)
        {
        	this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
        }
	}
	
	public void FireParticleEffect(EntityLivingBase target, World world)
	{
		for(int countparticles = 0; countparticles <= 7 * 2.5; ++countparticles)
		{
			Random rand = new Random();
			PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 26, target.posX + (rand.nextDouble() - 0.5D) * (double)target.width, target.posY + rand.nextDouble() * (double)target.height - 0.25D, target.posZ + (rand.nextDouble() - 0.5D) * (double)target.width, 0.0D, 0.0D, 0.0D, -1), target.dimension);
        }
		
		for(int countparticles = 0; countparticles <= 10 * 2.5; ++countparticles)
		{
			Random rand = new Random();
			PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 27, target.posX + (rand.nextDouble() - 0.5D) * (double)target.width, target.posY + rand.nextDouble() * (double)target.height - 0.25D, target.posZ + (rand.nextDouble() - 0.5D) * (double)target.width, 0.0D, 0.0D, 0.0D, -1), target.dimension);
	    }
    }

}
