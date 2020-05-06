package com.water.elementmod.util.handlers;

import com.water.elementmod.util.EMConfig;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
}