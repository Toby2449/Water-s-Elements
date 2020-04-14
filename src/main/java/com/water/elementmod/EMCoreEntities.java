package com.water.elementmod;

import java.sql.Types;
import java.util.Collection;
import java.util.LinkedList;

import com.water.elementmod.entity.boss.EntityPhotoSynthesizerCrystal;
import com.water.elementmod.entity.boss.EntityNatureBoss;
import com.water.elementmod.entity.boss.EntityNatureBossMinion;
import com.water.elementmod.entity.monster.EntityFireSkeleton;
import com.water.elementmod.entity.monster.EntityFireZombie;
import com.water.elementmod.entity.monster.EntityNatureSkeleton;
import com.water.elementmod.entity.monster.EntityNatureZombie;
import com.water.elementmod.entity.monster.EntityWaterSkeleton;
import com.water.elementmod.entity.monster.EntityWaterZombie;
import com.water.elementmod.entity.projectile.EntityFireArrow;
import com.water.elementmod.entity.projectile.EntityNatureArrow;
import com.water.elementmod.entity.projectile.EntityPoisonBall;
import com.water.elementmod.entity.projectile.EntityWaterArrow;
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
		registerEntity(EMConfig.ENTITY_FIRESKELETON, "fire_skeleton", EntityFireSkeleton.class, 100, 15263976, 16736256);
		registerEntity(EMConfig.ENTITY_WATERSKELETON, "water_skeleton", EntityWaterSkeleton.class, 100, 15263976, 2578864);
		registerEntity(EMConfig.ENTITY_NATURESKELETON, "nature_skeleton", EntityNatureSkeleton.class, 100, 15263976, 2457391);
		registerEntity(EMConfig.ENTITY_NATURE_BOSS, "nature_boss", EntityNatureBoss.class, 100, 15263976, 2457391);
		registerEntity(EMConfig.ENTITY_PHOTOSYNCRYSTAL, "photo_synthetic_crystal", EntityPhotoSynthesizerCrystal.class, 100, 15263976, 2457391);
		registerEntity(EMConfig.ENTITY_NATURE_BOSS_MINION, "nature_boss_minion", EntityNatureBossMinion.class, 100, 15263976, 2457391);
		
		registerArrow("nature_arrow", EntityNatureArrow.class, EMConfig.ENTITY_NATUREARROW);
		registerArrow("water_arrow", EntityWaterArrow.class, EMConfig.ENTITY_WATERARROW);
		registerArrow("fire_arrow", EntityFireArrow.class, EMConfig.ENTITY_FIREARROW);
		
		registerArrow("poison_ball", EntityPoisonBall.class, EMConfig.ENTITY_POISONBALL);
	}
	
	public static void registerSpawns()
	{
		SpawnAllBiomesOverworldNoOcean(EntityFireZombie.class, 20, 1, 2, EnumCreatureType.MONSTER);
		SpawnAllBiomesOverworldNoOcean(EntityWaterZombie.class, 20, 1, 2, EnumCreatureType.MONSTER);
		SpawnAllBiomesOverworldNoOcean(EntityNatureZombie.class, 20, 1, 2, EnumCreatureType.MONSTER);
		SpawnAllBiomesOverworldNoOcean(EntityFireSkeleton.class, 20, 1, 2, EnumCreatureType.MONSTER);
		SpawnAllBiomesOverworldNoOcean(EntityWaterSkeleton.class, 20, 1, 2, EnumCreatureType.MONSTER);
		SpawnAllBiomesOverworldNoOcean(EntityNatureSkeleton.class, 20, 1, 2, EnumCreatureType.MONSTER);
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
	
	private static void registerArrow(String name, Class<? extends Entity> entity, int id)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(EMConfig.MOD_ID + ":" + name), entity, name, id, EMCore.instance, 64, 20, true);
	}

}
