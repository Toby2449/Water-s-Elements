package com.water.elementmod.init;

import java.util.ArrayList;
import java.util.List;

import com.water.elementmod.items.ItemBase;

import net.minecraft.item.Item;

public class EmItems 
{
	
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	public static final Item FIRESMPL = new ItemBase("fire_sample", 64); // 1.5/0.5x damage to nature
	public static final Item WATERDRP = new ItemBase("water_drop", 64); // 1.5/0.5x damage to fire
	public static final Item NATURESMPL = new ItemBase("nature_sample", 64); // 1.5/0.5x damage to water
	
	// Coming later maybe
	//public static final Item ENERGYSMPL = new ItemBase("energy_sample");
	//public static final Item WINDSMPL = new ItemBase("wind_sample");
	//public static final Item LIGHTINGSMPL = new ItemBase("lightning_sample");
}
