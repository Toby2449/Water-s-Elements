package com.water.elementmod.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.water.elementmod.entity.monster.EntityWaterSkeleton;
import com.water.elementmod.entity.monster.EntityWaterZombie;
import com.water.elementmod.network.PacketAbilityReadyWaterData;
import com.water.elementmod.network.PacketHandler;
import com.water.elementmod.network.PacketParticleData;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EventWaterSwordAbility 
{
	private static List itemLevel = new ArrayList();
	private static List drowndingTime = new ArrayList();
	private static List drowndingEntities = new ArrayList();
	private static List abilityTimer = new ArrayList();
	private static List abilityBaseDuration = new ArrayList();
	private static List abilityWaveRadius = new ArrayList();
	private static List abilityPlayers = new ArrayList();
	private static List abilityPlayerCD = new ArrayList();
	
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
					int abilityBaseDuration = (Integer)this.abilityBaseDuration.get(i);
					double abilityRadius = (Double)this.abilityWaveRadius.get(i);
					EntityPlayer currentPlayer = (EntityPlayer) this.abilityPlayers.get(i);
					if((Integer)this.abilityTimer.get(i) == (abilityBaseDuration * 20) - 3) WaveWallAnimation(currentPlayer, event.getEntity().world, abilityRadius);
					
					if(playerAbilityCDRemaining == 0)
					{
						PacketHandler.INSTANCE.sendTo(new PacketAbilityReadyWaterData(currentPlayer, event.getEntity().world), (EntityPlayerMP) event.getEntity());
					}
					
					if(currentPlayer != null)
					{
						if(!currentPlayer.isDead)
						{
							if((Integer)this.abilityTimer.get(i) > 0)
							{
								WaterAbilityParticleEffect(currentPlayer, event.getEntity().world);
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
									this.abilityBaseDuration.remove(i);
									this.abilityWaveRadius.remove(i);
								}
							}
						}
						else
						{
							this.abilityTimer.remove(i);
							this.abilityPlayers.remove(i);
							this.abilityPlayerCD.remove(i);
							this.abilityBaseDuration.remove(i);
							this.abilityWaveRadius.remove(i);
						}
					}
				}
			}
		}
	}
	
	public static boolean playerActivateAbility(World worldIn, EntityPlayer player, double abilityRadius, int abilityDuration, int abilityCooldown, int iLevel, float knockbackStrength)
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
		abilityWaveRadius.add(abilityRadius);
		abilityBaseDuration.add(abilityDuration);
		WaveAnimation(player, worldIn, abilityRadius);
		
		// Extend the players hitbox
		AxisAlignedBB e = player.getEntityBoundingBox().grow(abilityRadius, 4.0D, abilityRadius);
		
		List<EntityMob> listMobs = worldIn.<EntityMob>getEntitiesWithinAABB(EntityMob.class, e);
		List<EntityPlayer> listPlayers = worldIn.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, e);
		
		// Get current mobs inside the player's ability aabb
        if (!listMobs.isEmpty())
        {
            for (EntityMob entitymob : listMobs)
            {
            	// Knockback the entity and set a potion effect
            	LesserWaterParticleEffect(entitymob, worldIn, iLevel);
            	Vec3d targetpos = entitymob.getPositionVector();
            	entitymob.attackEntityFrom(DamageSource.DROWN, 0.5F);
	    		entitymob.knockBack(player, knockbackStrength, playerpos.x - targetpos.x, playerpos.z - targetpos.z);
	    		int potionstrength = 0;
        		if(iLevel >= 8) potionstrength = 1;
            	entitymob.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, abilityDuration * 20 / 2, potionstrength));
            }
        }
        
        // Get all current players inside the player's ability aabb
        if (!listPlayers.isEmpty())
        {
            for (EntityPlayer entityplayer : listPlayers)
            {
            	// Check if the player is the player that activates the ability
            	if(entityplayer == player)
            	{
            		entityplayer.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, abilityDuration * 20, 2));
            		entityplayer.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, abilityDuration * 20, 2));
            		entityplayer.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, abilityDuration * 20, iLevel / 2 + 1));
            		
            	}
            }
        }
        
        return true;
	}
	
	public void WaterAbilityParticleEffect(EntityPlayer target, World world)
	{
		for(int countparticles = 0; countparticles <= 14; ++countparticles)
		{
			Random rand = new Random();
			PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 5, target.posX + (rand.nextDouble() - 0.5D) * target.width, target.posY + rand.nextDouble() * target.height - target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * target.width, 0.0D, 0.0D,0.0D, -1), target.dimension);
		}
	}
	
	public static void LesserWaterParticleEffect(EntityLivingBase target, World world, int level)
	{
		for(int countparticles = 0; countparticles <= 13 * level / 2; ++countparticles)
		{
			Random rand = new Random();
			PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 5, target.posX + (rand.nextDouble() - 0.5D) * target.width, target.posY + rand.nextDouble() * target.height - target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * target.width, 0.0D, 0.0D, 0.0D, -1), target.dimension);
			PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 39, target.posX + (rand.nextDouble() - 0.5D) * target.width, target.posY + rand.nextDouble() * target.height - target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * target.width, 0.0D, 0.0D, 0.0D, -1), target.dimension);
		}
		for(int countparticles = 0; countparticles <= 20 * level / 2; ++countparticles)
		{
			Random rand = new Random();
			world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, target.posX + (rand.nextDouble() - 0.5D) * target.width, target.posY + rand.nextDouble() * target.height - target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * target.width, 0.0D, 0.0D,0.0D);
			PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 4, target.posX + (rand.nextDouble() - 0.5D) * target.width, target.posY + rand.nextDouble() * target.height - target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * target.width, 0.0D, 0.0D, 0.0D, -1), target.dimension);
		}
	}
	
	public static void WaveAnimation(EntityLivingBase target, World world, double abilityRadius)
	{
		Random rand = new Random();
		
		for(double r = 0.6D; r <= abilityRadius; r += 0.2D)
		{
			for(float i = 0.0F; i < 360.0F; i += 5.0F)
			{
				double deltaX = Math.cos(Math.toRadians(i))*r;
				double deltaZ = -Math.sin(Math.toRadians(i))*r;
				double finalX = target.posX + deltaX;
				double finalZ = target.posZ + deltaZ;
			    
				PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 39, finalX, target.posY + rand.nextDouble(), finalZ, 0.0D, 0.0D, 0.0D, -1), target.dimension);
			}
		}
	}
	
	public void WaveWallAnimation(EntityLivingBase target, World world, double abilityRadius)
	{
		Random rand = new Random();
		for(float i = 0.0F; i < 360.0F; i += 2.0F)
		{
			double radius = abilityRadius;
			double deltaX = Math.cos(Math.toRadians(i))*radius;
			double deltaZ = -Math.sin(Math.toRadians(i))*radius;
			double finalX = target.posX + deltaX;
			double finalZ = target.posZ + deltaZ;
		    
			for(double p = abilityRadius; p >= 0.0D; p -= 0.5D)
			{
				PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 18, finalX, target.posY + p + rand.nextDouble(), finalZ, 0.0D, 0.0D, 0.0D, -1), target.dimension);
				PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 4, finalX, target.posY + p + rand.nextDouble(), finalZ, 0.0D, 0.0D, 0.0D, -1), target.dimension);
			}
		}
	}
}
