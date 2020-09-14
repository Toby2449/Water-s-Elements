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
public class EventSuperPoison 
{
	private static List superPoisonTime = new ArrayList();
	private static List superPoisonEntities = new ArrayList();
	
	@SubscribeEvent
	public void onLivingUpdateEvent(LivingUpdateEvent event)
	{
		if (event.getEntity() instanceof EntityPlayer)
		{
			if(!event.getEntity().world.isRemote)
			{
				for(int i = 0; i < this.superPoisonEntities.size(); i++)
				{
					int superPoisonTimeInstance = (Integer)this.superPoisonTime.get(i);
					EntityLivingBase currentEnt = (EntityLivingBase) this.superPoisonEntities.get(i);
					if(currentEnt != null)
					{
						if(!currentEnt.isDead)
						{
							if((Integer)this.superPoisonTime.get(i) > 0) 
							{
								if ((Integer)this.superPoisonTime.get(i) % 25 == 0)
							    {
									if(currentEnt.getHealth() > 0.5)
									{
										currentEnt.attackEntityFrom(DamageSource.MAGIC, 0.5F);
									}
							    }
								
								NatureParticleEffect(currentEnt, event.getEntity().world);
								this.superPoisonTime.set(i, superPoisonTimeInstance - 1);
								
							}
							else
							{
								this.superPoisonTime.remove(i);
								this.superPoisonEntities.remove(i);
							}
						}
						else
						{
							this.superPoisonTime.remove(i);
							this.superPoisonEntities.remove(i);
						}
					}
					
					i++;
				}
			}
		}
	}
	
	public static void playerHitEntity(EntityLivingBase target, int posionDuration)
	{
		for(int i = 0; i < superPoisonEntities.size(); i++)
		{
			if(superPoisonEntities.get(i) == target) 
			{
				superPoisonTime.remove(i);
				superPoisonEntities.remove(i);
			}
		}
		superPoisonEntities.add(target);
		superPoisonTime.add(posionDuration * 25);
	}
	
	public void NatureParticleEffect(EntityLivingBase target, World world)
	{
		Random rand = new Random();
		PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 15, target.posX + (rand.nextDouble() - 0.5D) * (double)target.width, target.posY + rand.nextDouble() * (double)target.height - 0.25D, target.posZ + (rand.nextDouble() - 0.5D) * (double)target.width, 0.0D, 1.47D, 0.09D, 1), target.dimension);
	}
}
