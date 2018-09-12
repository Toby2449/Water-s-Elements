package com.water.elementmod.util.handlers;

import com.water.elementmod.util.References;
import com.water.elementmod.util.Utils;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


public class EMSoundHandler {
	
	public static SoundEvent FIRE_BURN, WATER_FLOW, NATURE_LEAVES;
	
	public static void init()
	{
		FIRE_BURN = registerSound("block.infuser.fire");
		WATER_FLOW = registerSound("block.infuser.water");
		NATURE_LEAVES = registerSound("block.infuser.nature");
	}
	
	public static SoundEvent registerSound(String name)
	{
		 ResourceLocation location = new ResourceLocation(References.MOD_ID, name);
		 SoundEvent event = new SoundEvent(location);
		 event.setRegistryName(name);
		 ForgeRegistries.SOUND_EVENTS.register(event);
		 Utils.getLogger().info("Registered sound: " + name);
		 return event;
	}
}
