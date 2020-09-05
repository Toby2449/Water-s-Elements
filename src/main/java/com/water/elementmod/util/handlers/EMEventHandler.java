package com.water.elementmod.util.handlers;

import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.water.elementmod.EMCoreDimensions;
import com.water.elementmod.EMCorePotionEffects;
import com.water.elementmod.entity.boss.fire.EntityFireBoss;
import com.water.elementmod.entity.boss.fire._ConfigEntityFireBoss;
import com.water.elementmod.entity.boss.nature.EntityNatureBoss;
import com.water.elementmod.entity.boss.nature._ConfigEntityNatureBoss;
import com.water.elementmod.entity.boss.overworld.EntityVoidKnight;
import com.water.elementmod.entity.boss.overworld._ConfigEntityVoidKnight;
import com.water.elementmod.entity.boss.water.EntityWaterBoss;
import com.water.elementmod.entity.boss.water._ConfigEntityWaterBoss;
import com.water.elementmod.particle.EnumCustomParticleTypes;
import com.water.elementmod.particle.ParticleSpawner;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class EMEventHandler {
	public static TextureAtlasSprite textureAtlasSprite;
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerParticleMap(TextureStitchEvent.Pre event) {
        	ResourceLocation lava = new ResourceLocation(EMConfig.MOD_ID, "particle/lava");
            event.getMap().registerSprite(lava);
            
            ResourceLocation leaf = new ResourceLocation(EMConfig.MOD_ID, "particle/leaf");
            event.getMap().registerSprite(leaf);
	}
	
	@SubscribeEvent
	public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) 
	{
		if (event.getEntityPlayer() != null) 
		{
			if(event.getEntityPlayer().dimension == EMCoreDimensions.VOID.getId())
			{
				event.setCanceled(true);
			}
			
			List<EntityNatureBoss> NatureBoss = event.getEntityPlayer().world.<EntityNatureBoss>getEntitiesWithinAABB(EntityNatureBoss.class, event.getEntityPlayer().getEntityBoundingBox().grow(_ConfigEntityNatureBoss.ARENA_SIZE_X, _ConfigEntityNatureBoss.ARENA_SIZE_Y, _ConfigEntityNatureBoss.ARENA_SIZE_Z));
			if(NatureBoss.size() > 0)
			{
				event.setCanceled(true);
			}
			
			List<EntityFireBoss> FireBoss = event.getEntityPlayer().world.<EntityFireBoss>getEntitiesWithinAABB(EntityFireBoss.class, event.getEntityPlayer().getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z));
			if(FireBoss.size() > 0)
			{
				event.setCanceled(true);
			}
			
			List<EntityWaterBoss> WaterBoss = event.getEntityPlayer().world.<EntityWaterBoss>getEntitiesWithinAABB(EntityWaterBoss.class, event.getEntityPlayer().getEntityBoundingBox().grow(_ConfigEntityWaterBoss.ARENA_SIZE_X, _ConfigEntityWaterBoss.ARENA_SIZE_Y, _ConfigEntityWaterBoss.ARENA_SIZE_Z));
			if(WaterBoss.size() > 0)
			{
				event.setCanceled(true);
			}
			
			List<EntityVoidKnight> KnightBoss = event.getEntityPlayer().world.<EntityVoidKnight>getEntitiesWithinAABB(EntityVoidKnight.class, event.getEntityPlayer().getEntityBoundingBox().grow(_ConfigEntityVoidKnight.ARENA_SIZE_X, _ConfigEntityVoidKnight.ARENA_SIZE_Y, _ConfigEntityVoidKnight.ARENA_SIZE_Z));
			if(KnightBoss.size() > 0)
			{
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) 
	{
		if (event.getEntityPlayer() != null) 
		{
			if(event.getEntityPlayer().dimension == EMCoreDimensions.VOID.getId())
			{
				event.setCanceled(true);
			}
			
			List<EntityNatureBoss> NatureBoss = event.getEntityPlayer().world.<EntityNatureBoss>getEntitiesWithinAABB(EntityNatureBoss.class, event.getEntityPlayer().getEntityBoundingBox().grow(_ConfigEntityNatureBoss.ARENA_SIZE_X, _ConfigEntityNatureBoss.ARENA_SIZE_Y, _ConfigEntityNatureBoss.ARENA_SIZE_Z));
			if(NatureBoss.size() > 0)
			{
				event.setCanceled(true);
			}
			
			List<EntityFireBoss> FireBoss = event.getEntityPlayer().world.<EntityFireBoss>getEntitiesWithinAABB(EntityFireBoss.class, event.getEntityPlayer().getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z));
			if(FireBoss.size() > 0)
			{
				event.setCanceled(true);
			}
			
			List<EntityWaterBoss> WaterBoss = event.getEntityPlayer().world.<EntityWaterBoss>getEntitiesWithinAABB(EntityWaterBoss.class, event.getEntityPlayer().getEntityBoundingBox().grow(_ConfigEntityWaterBoss.ARENA_SIZE_X, _ConfigEntityWaterBoss.ARENA_SIZE_Y, _ConfigEntityWaterBoss.ARENA_SIZE_Z));
			if(WaterBoss.size() > 0)
			{
				event.setCanceled(true);
			}
			
			List<EntityVoidKnight> KnightBoss = event.getEntityPlayer().world.<EntityVoidKnight>getEntitiesWithinAABB(EntityVoidKnight.class, event.getEntityPlayer().getEntityBoundingBox().grow(_ConfigEntityVoidKnight.ARENA_SIZE_X, _ConfigEntityVoidKnight.ARENA_SIZE_Y, _ConfigEntityVoidKnight.ARENA_SIZE_Z));
			if(KnightBoss.size() > 0)
			{
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public static void drawVoidBossOverlay(RenderGameOverlayEvent.Chat event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		boolean isActive = false;
		if(mc.player.isPotionActive(EMCorePotionEffects.POTION_GLIMPSE)) isActive = true;
		
		if(isActive)
		{
			ScaledResolution scaledRes = new ScaledResolution(mc);
			int xPos = 0;
			int yPos = 0;
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(EMConfig.MOD_ID + ":textures/gui/ve_overlay.png"));
			Minecraft.getMinecraft().ingameGUI.drawModalRectWithCustomSizedTexture(xPos, yPos, 0, 0, scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), scaledRes.getScaledWidth(), scaledRes.getScaledHeight());
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
	}
	
	@SubscribeEvent
	public static void corruptionActive(PlayerTickEvent event)
	{
		boolean isActive = false;
		if(event.player.isPotionActive(EMCorePotionEffects.POTION_CORRUPTION)) isActive = true;
		
		if(isActive)
		{
			if (event.player.ticksExisted % 20 == 1) event.player.attackEntityFrom(DamageSource.WITHER, 1.0f);
			if(event.player.world.isRemote)
			{
				Random rand = new Random();
				for (int i = 0; i < 2; i++)
	            {
	            	ParticleSpawner.spawnParticle(EnumCustomParticleTypes.VOID_PORTAL_BLOCK, event.player.posX + (rand.nextDouble() - 0.5D) * (double)event.player.width, event.player.posY + rand.nextDouble() * (double)event.player.height - 0.25D, event.player.posZ + (rand.nextDouble() - 0.5D) * (double)event.player.width, (rand.nextDouble() - 0.5D) * 2.0D, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2.0D);
	            	ParticleSpawner.spawnParticle(EnumCustomParticleTypes.VOID_PORTAL, event.player.posX + (rand.nextDouble() - 0.5D) * (double)event.player.width, event.player.posY + rand.nextDouble() * (double)event.player.height - 0.25D, event.player.posZ + (rand.nextDouble() - 0.5D) * (double)event.player.width, (rand.nextDouble() - 0.5D) * 2.0D, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2.0D);
		        }
			}
		}
	}
	
	@SubscribeEvent
	public static void insanityActive(PlayerTickEvent event)
	{
		boolean isActive = false;
		if(event.player.isPotionActive(EMCorePotionEffects.POTION_INSANITY)) isActive = true;
		
		if(isActive)
		{
			if(event.player.world.isRemote)
			{
				Random rand = new Random();
				for (int i = 0; i < 2; i++)
	            {
	            	ParticleSpawner.spawnParticle(EnumCustomParticleTypes.DARK_PURPLE_SMOKE, event.player.posX + (rand.nextDouble() - 0.5D) * (double)event.player.width, event.player.posY + rand.nextDouble() * (double)event.player.height - 0.25D, event.player.posZ + (rand.nextDouble() - 0.5D) * (double)event.player.width, 0.0D, 0.0D, 0.0D);
	            	ParticleSpawner.spawnParticle(EnumCustomParticleTypes.DARK_PURPLE_SMOKE_BLOCK, event.player.posX + (rand.nextDouble() - 0.5D) * (double)event.player.width, event.player.posY + rand.nextDouble() * (double)event.player.height - 0.25D, event.player.posZ + (rand.nextDouble() - 0.5D) * (double)event.player.width, (rand.nextDouble() - 0.5D) * 2.0D, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2.0D);
		        }
			}
			
			if(!event.player.world.isRemote)
			{
				if(event.player.getActivePotionEffect(EMCorePotionEffects.POTION_INSANITY).getDuration() == 1)
				{
					if(event.player.getActivePotionEffect(EMCorePotionEffects.POTION_INSANITY).getAmplifier() > 4)
					{
						event.player.attackEntityFrom(DamageSource.MAGIC, 999999999);
					}
					else
					{
						event.player.attackEntityFrom(DamageSource.MAGIC, 7.5F * event.player.getActivePotionEffect(EMCorePotionEffects.POTION_INSANITY).getAmplifier());
					}
				}
			}
		}
	}
}