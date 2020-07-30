package com.water.elementmod;

import java.util.Collection;

import com.water.elementmod.entity.boss._void.EntityCarapace;
import com.water.elementmod.entity.boss.fire.EntityFireBoss;
import com.water.elementmod.entity.boss.fire.EntityFireBossMinion;
import com.water.elementmod.entity.boss.fire.EntityFireCrystal;
import com.water.elementmod.entity.boss.fire.EntityFireGuardian;
import com.water.elementmod.entity.boss.fire.EntityFireGuardianCastle;
import com.water.elementmod.entity.boss.nature.EntityNatureBoss;
import com.water.elementmod.entity.boss.nature.EntityNatureBossMinion;
import com.water.elementmod.entity.boss.nature.EntityPhotoSynthesizerCrystal;
import com.water.elementmod.entity.boss.overworld.EntityVoidBlob;
import com.water.elementmod.entity.boss.overworld.EntityVoidKnight;
import com.water.elementmod.entity.boss.overworld.EntityVoidSmasher;
import com.water.elementmod.entity.boss.water.EntityWaterBoss;
import com.water.elementmod.entity.boss.water.EntityWaterBossClone;
import com.water.elementmod.entity.boss.water.EntityWaterBossMeleeMinion;
import com.water.elementmod.entity.boss.water.EntityWaterBossRangedMinion;
import com.water.elementmod.entity.boss.water.EntityWaterTrash;
import com.water.elementmod.entity.friendly.EntityAlyxFire;
import com.water.elementmod.entity.friendly.EntityAlyxNature;
import com.water.elementmod.entity.friendly.EntityAlyxWater;
import com.water.elementmod.entity.monster.EntityFireSkeleton;
import com.water.elementmod.entity.monster.EntityFireZombie;
import com.water.elementmod.entity.monster.EntityNatureSkeleton;
import com.water.elementmod.entity.monster.EntityNatureStalker;
import com.water.elementmod.entity.monster.EntityNatureZombie;
import com.water.elementmod.entity.monster.EntityWaterSkeleton;
import com.water.elementmod.entity.monster.EntityWaterZombie;
import com.water.elementmod.entity.projectile.EntityFireArrow;
import com.water.elementmod.entity.projectile.EntityIceBall;
import com.water.elementmod.entity.projectile.EntityIceBall2;
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
		registerEntity(EMConfig.ENTITY_NATURE_STALKER, "nature_stalker", EntityNatureStalker.class, 100, 3877140, 880148);
		registerEntity(EMConfig.ENTITY_ALYX_FIRE, "em_alyx_fire", EntityAlyxFire.class, 100, 3877140, 880148);
		registerEntity(EMConfig.ENTITY_FIRE_BOSS, "fire_boss", EntityFireBoss.class, 100, 16738048, 16711680);
		registerEntity(EMConfig.ENTITY_FIRE_CRYSTAL, "fire_crystal", EntityFireCrystal.class, 100, 16738048, 16711680);
		registerEntity(EMConfig.ENTITY_FIRE_BOSS_MINI, "fire_boss_mini", EntityFireBossMinion.class, 100, 16738048, 16711680);
		registerEntity(EMConfig.ENTITY_FIRE_GUARDIAN, "fire_guardian", EntityFireGuardian.class, 100, 16738048, 16711680);
		registerEntity(EMConfig.ENTITY_FIRE_GUARDIAN_CASTLE, "fire_guardian_castle", EntityFireGuardianCastle.class, 100, 16738048, 16711680);
		registerEntity(EMConfig.ENTITY_WATER_BOSS, "water_boss", EntityWaterBoss.class, 100, 4360181, 14481);
		registerEntity(EMConfig.ENTITY_WATER_BOSS_CLONE, "water_boss_clone", EntityWaterBossClone.class, 100, 4360181, 14481);
		registerEntity(EMConfig.ENTITY_WATER_BOSS_RANGED_MINION, "water_boss_ranged_minion", EntityWaterBossRangedMinion.class, 100, 4360181, 14481);
		registerEntity(EMConfig.ENTITY_WATER_BOSS_MELEE_MINION, "water_boss_melee_minion", EntityWaterBossMeleeMinion.class, 100, 4360181, 14481);
		registerEntity(EMConfig.ENTITY_ALYX_WATER, "em_alyx_water", EntityAlyxWater.class, 100, 3877140, 880148);
		registerEntity(EMConfig.ENTITY_ALYX_NATURE, "em_alyx_nature", EntityAlyxNature.class, 100, 3877140, 880148);
		registerEntity(EMConfig.ENTITY_WATER_TRASH, "water_trash", EntityWaterTrash.class, 100, 3877140, 880148);
		registerEntity(EMConfig.ENTITY_VOID_KNIGHT, "void_knight", EntityVoidKnight.class, 100, 3877140, 880148);
		registerEntity(EMConfig.ENTITY_VOID_BLOB, "void_blob", EntityVoidBlob.class, 100, 3877140, 880148);
		registerEntity(EMConfig.ENTITY_VOID_SMASHER, "void_smasher", EntityVoidSmasher.class, 100, 3877140, 880148);
		registerEntity(EMConfig.ENTITY_CARAPACE, "carapace", EntityCarapace.class, 100, 3877140, 880148);
		
		registerArrow("nature_arrow", EntityNatureArrow.class, EMConfig.ENTITY_NATUREARROW);
		registerArrow("water_arrow", EntityWaterArrow.class, EMConfig.ENTITY_WATERARROW);
		registerArrow("fire_arrow", EntityFireArrow.class, EMConfig.ENTITY_FIREARROW);
		
		registerArrow("poison_ball", EntityPoisonBall.class, EMConfig.ENTITY_POISONBALL);
		registerArrow("ice_ball", EntityIceBall.class, EMConfig.ENTITY_ICEBALL);
		registerArrow("ice_ball2", EntityIceBall2.class, EMConfig.ENTITY_ICEBALL2);
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
