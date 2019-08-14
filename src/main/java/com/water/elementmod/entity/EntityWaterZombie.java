package com.water.elementmod.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.water.elementmod.network.PacketHandler;
import com.water.elementmod.network.PacketParticleData;
import com.water.elementmod.util.handlers.EMLootTableHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIZombieAttack;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityWaterZombie extends EntityZombie
{
	
	private List drowndingTime = new ArrayList();
	private List drowndingEntities = new ArrayList();
	
	public EntityWaterZombie(World worldIn) 
	{
		super(worldIn);
		this.setSize(0.6F, 1.95F);
		this.isImmuneToFire = true;
	}
	
	@Override
	protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIZombieAttack(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0F);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
	}
	
	private void setDrownding(Entity target, Integer time) 
	{
		this.drowndingTime.add(time * 20);
		this.drowndingEntities.add(target);
	}
	
	public void onLivingUpdate()
    {
        if (this.world.isRemote)
        {
            for (int i = 0; i < 15; ++i)
            {
                this.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
            }
            for (int i = 0; i < 3; ++i)
            {
                this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
            }
        }
        
        if(!this.world.isRemote)
        {
        	for(int i = 0; i < this.drowndingEntities.size(); i++)
			{
				int drowndingTimeInstance = (Integer)this.drowndingTime.get(i);
				Entity currentEnt = (Entity) this.drowndingEntities.get(i);
				if(currentEnt != null)
				{
					if(!currentEnt.isDead)
					{
						if((Integer)this.drowndingTime.get(i) > 0)
						{
							if ((Integer)this.drowndingTime.get(i) % 20 == 0)
						    {
								WaterParticleEffect(currentEnt, this.world);
								currentEnt.attackEntityFrom(DamageSource.DROWN, 0.5F);
						    }
							
						    this.drowndingTime.set(i, drowndingTimeInstance - 1);
						}
					}
					else
					{
						this.drowndingTime.remove(i);
						this.drowndingEntities.remove(i);
					}
				}
			}
        }

        this.isJumping = false;
        super.onLivingUpdate();
    }
	
	public void WaterParticleEffect(Entity target, World world)
	{
		for(int countparticles = 0; countparticles <= 90; ++countparticles)
		{
			Random rand = new Random();
			PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 5, target.posX + (rand.nextDouble() - 0.5D) * target.width, target.posY + rand.nextDouble() * target.height - target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * target.width, 0.0D, 0.0D,0.0D, -1), target.dimension);
			PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 39, target.posX + (rand.nextDouble() - 0.5D) * target.width, target.posY + rand.nextDouble() * target.height - target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * target.width, 0.0D, 0.0D,0.0D, -1), target.dimension);
		}
		for(int countparticles = 0; countparticles <= 300; ++countparticles)
		{
			Random rand = new Random();
			PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 4, target.posX + (rand.nextDouble() - 0.5D) * target.width, target.posY + rand.nextDouble() * target.height - target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * target.width, 0.0D, 0.0D,0.0D, -1), target.dimension);
		}
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = super.attackEntityAsMob(entityIn);

        if (flag)
        {
            float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();

            if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < f * 0.3F)
            {
                entityIn.setFire(2 * (int)f);
            }
        }
        
       setDrownding(entityIn, 4);
        
        return flag;
    }
	
	@Override
	protected ResourceLocation getLootTable()
	{
		return EMLootTableHandler.WATER_ZOMBIE;
	}
	
	@Override
	protected boolean shouldBurnInDay()
    {
        return false;
    }
	
	@Override
	public float getEyeHeight()
	{
		return 1.74F;
	}
	
	@Override
	protected SoundEvent getAmbientSound()
	{
		return super.getAmbientSound();
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return super.getHurtSound(source);
	}
	
	@Override
	protected SoundEvent getDeathSound()
	{
		return super.getDeathSound();
	}
}
