package com.water.elementmod.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.water.elementmod.network.PacketAbilityReadyFireData;
import com.water.elementmod.network.PacketHandler;
import com.water.elementmod.network.PacketParticleData;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EventFireSwordAbility 
{
	private static List abilityTimer = new ArrayList();
	private static List abilityPlayers = new ArrayList();
	private static List abilityPlayerCD = new ArrayList();
	private static List abilityAoePoints = new ArrayList();
	private static List abilityAoeTimers = new ArrayList();
	private static List abilityAoePlayer = new ArrayList();
	
	@SubscribeEvent
	public void onLivingUpdateEvent(LivingUpdateEvent event)
	{
		if (event.getEntity() instanceof EntityPlayer)
		{
			if(!event.getEntity().world.isRemote)
			{
				for(int i = 0; i < this.abilityPlayers.size(); i++)
				{
					int playerAbilityRemaining = (Integer)this.abilityTimer.get(i);
					int playerAbilityRemainingCD = (Integer)this.abilityPlayerCD.get(i);
					EntityPlayer currentPlayer = (EntityPlayer)this.abilityPlayers.get(i);
					if(playerAbilityRemaining != 0)
					{
						if(playerAbilityRemaining % 4 == 0) spawnTrailNode(currentPlayer.posX, currentPlayer.posY, currentPlayer.posZ, playerAbilityRemaining);
					}
					
					if(playerAbilityRemainingCD == 0)
					{
						PacketHandler.INSTANCE.sendTo(new PacketAbilityReadyFireData(currentPlayer, event.getEntity().world), (EntityPlayerMP) event.getEntity());
					}
					
					if(currentPlayer != null)
					{
						if(!currentPlayer.isDead)
						{
							if((Integer)this.abilityTimer.get(i) > 0)
							{
								this.abilityTimer.set(i, playerAbilityRemaining - 1);
							}
							else
							{
								if((Integer)this.abilityPlayerCD.get(i) > 0)
								{
									this.abilityPlayerCD.set(i, playerAbilityRemainingCD - 1);
								}
								else
								{
									this.abilityTimer.remove(i);
									this.abilityPlayers.remove(i);
									this.abilityPlayerCD.remove(i);
								}
							}
						}
						else
						{
							this.abilityTimer.remove(i);
							this.abilityPlayers.remove(i);
							this.abilityPlayerCD.remove(i);
						}
					}
				}
				
				for(int i = 0; i < this.abilityAoePoints.size(); i++)
				{
					int CircleTimer = (Integer)this.abilityAoeTimers.get(i);
					ArrayList pos = (ArrayList)this.abilityAoePoints.get(i);
					
					AxisAlignedBB AoePoint = new AxisAlignedBB((double)pos.get(0) - 0.5D, (double)pos.get(1) - 0.5D, (double)pos.get(2) - 0.5D, (double)pos.get(0) + 1.0D, (double)pos.get(1) + 1.0D, (double)pos.get(2) + 1.0D);
					List<EntityLivingBase> AABB = event.getEntity().world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, AoePoint);
					if (!AABB.isEmpty())
			        {
			            for (EntityLivingBase ent : AABB)
			            {
			            	if(!(ent instanceof EntityPlayer))
			            	{
				            	ent.setFire(8);
				            	if(!ent.isPotionActive(MobEffects.SLOWNESS)) 
				            	{
				            		ent.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 50, 1));
				            	}
			            	}
			            }
			        }
					
					if((Integer)this.abilityAoeTimers.get(i) > 0)
					{
						if((Integer)this.abilityAoeTimers.get(i) % 4 == 0)
						{
							FireRingAnimation((double)pos.get(0), (double)pos.get(1), (double)pos.get(2), 1, event.getEntity().world, event.getEntity());
						}
						
						if((Integer)this.abilityAoeTimers.get(i) % 16 == 0)
						{
							FireSpitAnimation((double)pos.get(0), (double)pos.get(1), (double)pos.get(2), event.getEntity().world, event.getEntity());
						}
						this.abilityAoeTimers.set(i, CircleTimer - 1);
					}
					else
					{
						this.abilityAoePoints.remove(i);
						this.abilityAoeTimers.remove(i);
					}
				}
			}
		}
	}
	
	public static boolean playerActivateAbility(World worldIn, EntityPlayer player, int abilityDuration, int abilityCooldown)
	{
		for(int i = 0; i < abilityPlayers.size(); i++)
		{
			EntityPlayer entityPlayer = (EntityPlayer)abilityPlayers.get(i);
			
			if(entityPlayer == player)
			{
				return false;
			}
		}
		
		abilityPlayers.add(player);
		abilityTimer.add(abilityDuration * 20);
		abilityPlayerCD.add(abilityCooldown * 20);
		
		player.addPotionEffect(new PotionEffect(MobEffects.SPEED, abilityDuration * 20, 1));
		player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, abilityDuration * 20, 1));
        
        return true;
	}
	
	public void spawnTrailNode(double x, double y, double z, Integer time)
	{
		List pos = new ArrayList();
		pos.add(x);
		pos.add(y);
		pos.add(z);
		this.abilityAoePoints.add(pos);
		this.abilityAoeTimers.add(time);
	}
	
	public void FireRingAnimation(double x, double y, double z, double radius, World world, Entity ent)
	{
		Random rand = new Random();
		for(double r = 0.6D; r <= radius; r += 0.45D)
		{
			for(float i = 0.0F; i < 360.0F; i += 150.0F)
			{
				double deltaX = Math.cos(Math.toRadians(i))*r + rand.nextDouble();
				double deltaZ = -Math.sin(Math.toRadians(i))*r + rand.nextDouble();
				double finalX = x - 0.5D + deltaX;
				double finalZ = z - 0.5D + deltaZ;
			    
				PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(ent, world, 26, finalX, y + 0.15D, finalZ, 0.0D, 0.0D, 0.0D, -1), ent.dimension);
			}
		}
	}
	
	public void FireSpitAnimation(double x, double y, double z, World world, Entity ent)
	{
		PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(ent, world, 27, x, y + 0.15D, z, 0.0D, 0.0D, 0.0D, -1), ent.dimension);
	}
}
