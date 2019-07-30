package com.water.elementmod;

import com.water.elementmod.entity.EntityFireZombie;
import com.water.elementmod.util.References;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EMCoreEntities 
{
	public static void registerEntities()
	{
		registerEntity(References.ENTITY_FIREZOMBIE, "fire_zombie", EntityFireZombie.class, 100, 43433, 16736256);
		
		EntityRegistry.addSpawn(EntityFireZombie.class, 1000, 1, 2, EnumCreatureType.MONSTER);
	}
	
	private static void registerEntity(int id, String name, Class<? extends Entity> entity, int range, int color1, int color2)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(References.MOD_ID + ":" + name), entity, name, id, EMCore.instance, range, 1, true, color1, color2);
	}
}
