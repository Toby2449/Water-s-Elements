package com.water.elementmod.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.water.elementmod.network.PacketHandler;
import com.water.elementmod.network.PacketParticleData;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EventDrownDebuff 
{
	private static List itemLevel = new ArrayList();
	private static List drowndingTime = new ArrayList();
	private static List drowndingEntities = new ArrayList();
	
	@SubscribeEvent
	public void onLivingUpdateEvent(LivingUpdateEvent event)
	{
		if (event.getEntity() instanceof EntityPlayer)
		{
			if(!event.getEntity().world.isRemote)
			{
				for(int i = 0; i < this.drowndingEntities.size(); i++)
				{
					int drowndingTimeInstance = (Integer)this.drowndingTime.get(i);
					int itemLevel = (Integer)this.itemLevel.get(i);
					EntityLivingBase currentEnt = (EntityLivingBase) this.drowndingEntities.get(i);
					if(currentEnt != null)
					{
						if(!currentEnt.isDead)
						{
							if((Integer)this.drowndingTime.get(i) > 0) 
							{
								if ((Integer)this.drowndingTime.get(i) % 20 == 0)
							    {
									WaterParticleEffect(currentEnt, event.getEntity().world, itemLevel);
									currentEnt.attackEntityFrom(DamageSource.DROWN, 0.5F);
							    }
							
							    this.drowndingTime.set(i, drowndingTimeInstance - 1);
							}
						}
						else
						{
							this.drowndingTime.remove(i);
							this.drowndingEntities.remove(i);
							this.itemLevel.remove(i);
						}
					}
				}
			}
		}
	}
	
	public static void playerHitEntity(EntityLivingBase target, Integer drownDuration, int iLevel)
	{
		for(int i = 0; i < drowndingEntities.size(); i++)
		{
			if(drowndingEntities.get(i) == target) 
			{
				drowndingTime.remove(i);
				drowndingEntities.remove(i);
				itemLevel.remove(i);
			}
		}
		
		drowndingTime.add(drownDuration * 20);
		drowndingEntities.add(target);
		itemLevel.add(iLevel);
	}
	
	public void WaterParticleEffect(EntityLivingBase target, World world, int level)
	{
		for(int countparticles = 0; countparticles <= 18 * level / 2; ++countparticles)
		{
			Random rand = new Random();
			PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 5, target.posX + (rand.nextDouble() - 0.5D) * target.width, target.posY + rand.nextDouble() * target.height - target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * target.width, 0.0D, 0.0D,0.0D, -1), target.dimension);
			PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 39, target.posX + (rand.nextDouble() - 0.5D) * target.width, target.posY + rand.nextDouble() * target.height - target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * target.width, 0.0D, 0.0D,0.0D, -1), target.dimension);
		}
		for(int countparticles = 0; countparticles <= 30 * level / 2; ++countparticles)
		{
			Random rand = new Random();
			PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 4, target.posX + (rand.nextDouble() - 0.5D) * target.width, target.posY + rand.nextDouble() * target.height - target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * target.width, 0.0D, 0.0D,0.0D, -1), target.dimension);
		}
	}
}
