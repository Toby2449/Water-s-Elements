package com.water.elementmod.util.handlers;

import com.water.elementmod.util.EMConfig;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class EMLootTableHandler 
{
	public static final ResourceLocation FIRE_ZOMBIE = LootTableList.register(new ResourceLocation(EMConfig.MOD_ID, "entities/firezombie"));
	public static final ResourceLocation WATER_ZOMBIE = LootTableList.register(new ResourceLocation(EMConfig.MOD_ID, "entities/waterzombie"));
	public static final ResourceLocation NATURE_ZOMBIE = LootTableList.register(new ResourceLocation(EMConfig.MOD_ID, "entities/naturezombie"));
}
