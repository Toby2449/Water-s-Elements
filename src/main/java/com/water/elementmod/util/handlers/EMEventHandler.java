package com.water.elementmod.util.handlers;

import com.water.elementmod.util.EMConfig;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EMEventHandler {
	public static TextureAtlasSprite textureAtlasSprite;
	
	@SubscribeEvent
	public static void registerParticleMap(TextureStitchEvent.Pre event) {
			System.out.println("nigger");
        	ResourceLocation flameRL = new ResourceLocation(EMConfig.MOD_ID, "particle/particles");
            event.getMap().registerSprite(flameRL);
	}
}
