package com.water.elementmod.util.handlers;

import java.util.Random;

import com.water.elementmod.EMCorePotionEffects;
import com.water.elementmod.particle.EnumCustomParticleTypes;
import com.water.elementmod.particle.ParticleSpawner;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
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
				for (int i = 0; i < 2; ++i)
	            {
	            	ParticleSpawner.spawnParticle(EnumCustomParticleTypes.VOID_PORTAL_BLOCK, event.player.posX + (rand.nextDouble() - 0.5D) * (double)event.player.width, event.player.posY + rand.nextDouble() * (double)event.player.height - 0.25D, event.player.posZ + (rand.nextDouble() - 0.5D) * (double)event.player.width, (rand.nextDouble() - 0.5D) * 2.0D, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2.0D);
	            	ParticleSpawner.spawnParticle(EnumCustomParticleTypes.VOID_PORTAL, event.player.posX + (rand.nextDouble() - 0.5D) * (double)event.player.width, event.player.posY + rand.nextDouble() * (double)event.player.height - 0.25D, event.player.posZ + (rand.nextDouble() - 0.5D) * (double)event.player.width, (rand.nextDouble() - 0.5D) * 2.0D, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2.0D);
		        }
			}
		}
	}
}