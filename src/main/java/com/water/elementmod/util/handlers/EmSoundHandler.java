package com.water.elementmod.util.handlers;

import com.water.elementmod.util.EMConfig;
import com.water.elementmod.util.Utils;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


public class EMSoundHandler {
	
	public static SoundEvent FIRE_BURN, WATER_FLOW, NATURE_LEAVES, FIRE_DOOR_CLOSE;
	
	public static void init()
	{
		FIRE_BURN = registerSound("block.infuser.fire");
		WATER_FLOW = registerSound("block.infuser.water");
		NATURE_LEAVES = registerSound("block.infuser.nature");
		FIRE_DOOR_CLOSE = registerSound("ambient.fire_door_close");
	}
	
	public static SoundEvent registerSound(String name)
	{
		 ResourceLocation location = new ResourceLocation(EMConfig.MOD_ID, name);
		 SoundEvent event = new SoundEvent(location);
		 event.setRegistryName(name);
		 ForgeRegistries.SOUND_EVENTS.register(event);
		 Utils.getLogger().info("Registered sound: " + name);
		 return event;
	}
}
