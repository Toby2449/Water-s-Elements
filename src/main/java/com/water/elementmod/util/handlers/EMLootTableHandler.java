package com.water.elementmod.util.handlers;

import com.water.elementmod.util.EMConfig;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class EMLootTableHandler 
{
	public static final ResourceLocation FIRE_ZOMBIE = LootTableList.register(new ResourceLocation(EMConfig.MOD_ID, "entities/firezombie"));
	public static final ResourceLocation WATER_ZOMBIE = LootTableList.register(new ResourceLocation(EMConfig.MOD_ID, "entities/waterzombie"));
	public static final ResourceLocation NATURE_ZOMBIE = LootTableList.register(new ResourceLocation(EMConfig.MOD_ID, "entities/naturezombie"));
	
	public static final ResourceLocation FIRE_SKELETON = LootTableList.register(new ResourceLocation(EMConfig.MOD_ID, "entities/fireskeleton"));
	public static final ResourceLocation WATER_SKELETON = LootTableList.register(new ResourceLocation(EMConfig.MOD_ID, "entities/waterskeleton"));
	public static final ResourceLocation NATURE_SKELETON = LootTableList.register(new ResourceLocation(EMConfig.MOD_ID, "entities/natureskeleton"));
	
	public static final ResourceLocation NATURE_STRUCTURE_CHEST1 = LootTableList.register(new ResourceLocation(EMConfig.MOD_ID, "structure_loot/nature_structure_chest1"));
	public static final ResourceLocation NATURE_STRUCTURE_CHEST2 = LootTableList.register(new ResourceLocation(EMConfig.MOD_ID, "structure_loot/nature_structure_chest2"));
	public static final ResourceLocation NATURE_STRUCTURE_CHEST3 = LootTableList.register(new ResourceLocation(EMConfig.MOD_ID, "structure_loot/nature_structure_chest3"));
	
	public static final ResourceLocation FIRE_STRUCTURE_CHEST = LootTableList.register(new ResourceLocation(EMConfig.MOD_ID, "structure_loot/fire_structure_chest"));
	public static final ResourceLocation FIRE_STRUCTURE_CHEST_TOWER = LootTableList.register(new ResourceLocation(EMConfig.MOD_ID, "structure_loot/fire_structure_chest_tower"));
	
	public static final ResourceLocation WATER_STRUCTURE_CHEST = LootTableList.register(new ResourceLocation(EMConfig.MOD_ID, "structure_loot/water_structure_chest"));
}
