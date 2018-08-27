package com.water.elementmod.init;

import java.util.ArrayList;
import java.util.List;

import com.water.elementmod.items.ItemBase;
import com.water.elementmod.items.ItemBaseShiny;

import net.minecraft.item.Item;

public class EmItems 
{
	
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	public static final Item VOIDESS = new ItemBaseShiny("item_void_essence", 16);
	public static final Item VOIDSING = new ItemBaseShiny("item_void_singularity", 1);
	public static final Item VOIDCRY = new ItemBaseShiny("item_void_crystal", 16);
	
	public static final Item FIRESMPL = new ItemBase("item_fire_sample", 64); // 1.5/0.5x damage to nature
	public static final Item WATERDRP = new ItemBase("item_water_drop", 64); // 1.5/0.5x damage to fire
	public static final Item NATURESMPL = new ItemBase("item_nature_sample", 64); // 1.5/0.5x damage to water
	
	
	public static final Item EMBERS = new ItemBase("item_scolding_embers", 64);

}
