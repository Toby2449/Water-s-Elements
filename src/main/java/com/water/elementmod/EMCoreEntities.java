package com.water.elementmod;

import java.sql.Types;
import java.util.Collection;
import java.util.LinkedList;

import com.water.elementmod.entity.EntityFireZombie;
import com.water.elementmod.entity.EntityNatureZombie;
import com.water.elementmod.entity.EntityWaterZombie;
import com.water.elementmod.util.EMConfig;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEnd;
import net.minecraft.world.biome.BiomeHell;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraft.world.biome.BiomeVoid;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class EMCoreEntities 
{
	public static void registerEntities()
	{
		registerEntity(EMConfig.ENTITY_FIREZOMBIE, "fire_zombie", EntityFireZombie.class, 100, 43433, 16736256);
		registerEntity(EMConfig.ENTITY_WATERZOMBIE, "water_zombie", EntityWaterZombie.class, 100, 43433, 2578864);
		registerEntity(EMConfig.ENTITY_NATUREZOMBIE, "nature_zombie", EntityNatureZombie.class, 100, 43433, 2457391);
	}
	
	public static void registerSpawns()
	{
		SpawnAllBiomesOverworldNoOcean(EntityFireZombie.class, 10, 1, 2, EnumCreatureType.MONSTER);
		SpawnAllBiomesOverworldNoOcean(EntityWaterZombie.class, 10, 1, 2, EnumCreatureType.MONSTER);
		SpawnAllBiomesOverworldNoOcean(EntityNatureZombie.class, 10, 1, 2, EnumCreatureType.MONSTER);
	}
	private static void registerEntity(int id, String name, Class<? extends Entity> entity, int range, int color1, int color2)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(EMConfig.MOD_ID + ":" + name), entity, name, id, EMCore.instance, range, 1, true, color1, color2);
    }
	
	private static void SpawnAllBiomesOverworldNoOcean(Class<? extends EntityLiving> entityClass, int weight, int min, int max, EnumCreatureType typeOfCreature) 
	{
        Collection<Biome> biomes = ForgeRegistries.BIOMES.getValuesCollection();
        for (Biome bgb : biomes) {
            if (bgb instanceof BiomeVoid) {
                continue;
            }
            if (bgb instanceof BiomeEnd) {
                continue;
            }
            if (bgb instanceof BiomeHell) {
                continue;
            }
            if (bgb instanceof BiomeOcean) {
                continue;
            }
            EntityRegistry.addSpawn(entityClass, weight, min, max, typeOfCreature, bgb);
        }
        
    }

}
