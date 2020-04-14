package com.water.elementmod.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.water.elementmod.entity.monster.EntityWaterSkeleton;
import com.water.elementmod.entity.monster.EntityWaterZombie;
import com.water.elementmod.network.PacketAbilityReadyNatureData;
import com.water.elementmod.network.PacketCustomParticleData;
import com.water.elementmod.network.PacketHandler;
import com.water.elementmod.network.PacketParticleData;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
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
public class EventNatureSwordAbility 
{
	private static List abilityTimer = new ArrayList();
	private static List abilityPlayers = new ArrayList();
	private static List abilityPlayerCD = new ArrayList();
	private static List abilityAoePoints = new ArrayList();
	private static List abilityAoeRadius = new ArrayList();
	private static List abilityAoeRegenLevel = new ArrayList();
	private static List abilityAoeBaseDuration = new ArrayList();
	private static List abilityAoeTimers = new ArrayList();
	
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
					int playerAbilityCDRemaining = (Integer)this.abilityPlayerCD.get(i);
					EntityPlayer currentPlayer = (EntityPlayer) this.abilityPlayers.get(i);
					
					if(playerAbilityCDRemaining == 0)
					{
						PacketHandler.INSTANCE.sendTo(new PacketAbilityReadyNatureData(currentPlayer, event.getEntity().world), (EntityPlayerMP) event.getEntity());
					}
					
					if(currentPlayer != null)
					{
						if(!currentPlayer.isDead)
						{
							if((Integer)this.abilityTimer.get(i) > 0)
							{
								LesserNatureParticleEffect(currentPlayer, event.getEntity().world);
								this.abilityTimer.set(i, playerAbilityRemaining - 1);
							}
							else
							{
								if((Integer)this.abilityPlayerCD.get(i) > 0)
								{
									this.abilityPlayerCD.set(i, playerAbilityCDRemaining - 1);
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
					Double radius = (Double)this.abilityAoeRadius.get(i);
					int baseDuration = (Integer)this.abilityAoeBaseDuration.get(i);
					int regenLevel = (Integer)this.abilityAoeRegenLevel.get(i);
					ArrayList pos = (ArrayList)this.abilityAoePoints.get(i);
					if((Integer)this.abilityTimer.get(i) == (baseDuration * 20) - 1) HealingAoeAnimation((double)pos.get(0), (double)pos.get(1), (double)pos.get(2), radius, event.getEntity().world, event.getEntity());
					
					AxisAlignedBB AoePoint = new AxisAlignedBB((double)pos.get(0) - radius, (double)pos.get(1) - 0.5D, (double)pos.get(2) - radius, (double)pos.get(0) + radius, (double)pos.get(1) + 4.0D, (double)pos.get(2) + radius);
					List<EntityPlayer> AABBPlayer = event.getEntity().world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint);
					
					if (!AABBPlayer.isEmpty())
			        {
			            for (EntityPlayer ent : AABBPlayer)
			            {
			            	if(!ent.isPotionActive(MobEffects.REGENERATION)) 
			            	{
			            		ent.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 50, regenLevel));
			            	}
			            }
			        }
					
					if((Integer)this.abilityAoeTimers.get(i) > 0)
					{
						if((Integer)this.abilityAoeTimers.get(i) % 5 == 0)
						{
							HealingAoeAnimation((double)pos.get(0), (double)pos.get(1), (double)pos.get(2), radius, event.getEntity().world, event.getEntity());
						}
						if((Integer)this.abilityAoeTimers.get(i) % 5 == 0)
						{
							HeartParticleSpawner((double)pos.get(0), (double)pos.get(1), (double)pos.get(2), radius, event.getEntity().world, event.getEntity());
						}
						this.abilityAoeTimers.set(i, CircleTimer - 1);
					}
					else
					{
						this.abilityAoePoints.remove(i);
						this.abilityAoeTimers.remove(i);
						this.abilityAoeRadius.remove(i);
						this.abilityAoeBaseDuration.remove(i);
						this.abilityAoeRegenLevel.remove(i);
					}
				}
			}
		}
	}
	
	public static boolean playerActivateAbility(World worldIn, EntityPlayer player, double abilityRadius, int abilityDuration, int abilityCooldown, int regenLevel)
	{
		Vec3d playerpos = player.getPositionVector();
		
		// Checks if the player is still on cooldown
		for(int i = 0; i < abilityPlayers.size(); i++)
		{
			EntityPlayer playercheck = (EntityPlayer) abilityPlayers.get(i);
			
			// If player is in the table, return fail
			if(playercheck == player)
			{
				return false;	
			}
		}
		
		// Add the player to table so they can activate the ability
		abilityTimer.add(abilityDuration * 20);
		abilityPlayers.add(player);
		abilityPlayerCD.add(abilityCooldown * 20);

		List pos = new ArrayList();
		pos.add(player.posX);
		pos.add(player.posY);
		pos.add(player.posZ);
		abilityAoePoints.add(pos);
		abilityAoeTimers.add(abilityDuration * 20);
		abilityAoeBaseDuration.add(abilityDuration * 20);
		abilityAoeRadius.add(abilityRadius);
		abilityAoeRegenLevel.add(regenLevel);
		
		return true;
	}
	
	public void LesserNatureParticleEffect(EntityLivingBase target, World world)
	{
		Random rand = new Random();
		PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 21, target.posX + (rand.nextDouble() - 0.5D) * (double)target.width, target.posY + rand.nextDouble() * (double)target.height - 0.25D, target.posZ + (rand.nextDouble() - 0.5D) * (double)target.width, 0.0D, 1.47D, 0.09D, 1), target.dimension);
	}
	
	public void HealingAoeAnimation(double x, double y, double z, double radius, World world, Entity ent)
	{
		Random rand = new Random();
		for(double r = 0.6D; r <= radius; r += 0.2D)
		{
			for(float i = 0.0F; i < 360.0F; i += 15.0F)
			{
				double deltaX = Math.cos(Math.toRadians(i))*r + rand.nextDouble();
				double deltaZ = -Math.sin(Math.toRadians(i))*r + rand.nextDouble();
				double finalX = x - 0.5D + deltaX;
				double finalZ = z - 0.5D + deltaZ;
			    
				if(rand.nextDouble() < 0.1D) PacketHandler.INSTANCE.sendToDimension(new PacketCustomParticleData(ent, world, 4, finalX, y + 0.15D, finalZ, 0.0D, 0.0D, 0.0D, -1), ent.dimension);
			}
		}
	}
	
	public void HeartParticleSpawner(double x, double y, double z, double radius, World world, Entity ent)
	{
		Random rand = new Random();
		for(double r = 0.6D; r <= radius; r += 0.2D)
		{
			for(float i = 0.0F; i < 360.0F; i += 15.0F)
			{
				double deltaX = Math.cos(Math.toRadians(i))*r + rand.nextDouble();
				double deltaZ = -Math.sin(Math.toRadians(i))*r + rand.nextDouble();
				double finalX = x - 0.5D + deltaX;
				double finalZ = z - 0.5D + deltaZ;
			    
				if(rand.nextDouble() < 0.005D) PacketHandler.INSTANCE.sendToDimension(new PacketCustomParticleData(ent, world, 2, finalX, y + 0.15D, finalZ, 0.0D, 0.0D, 0.0D, -1), ent.dimension);
			}
		}
	}
}
