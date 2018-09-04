package com.water.elementmod.init;

import java.util.ArrayList;
import java.util.List;

import com.water.elementmod.items.ItemBase;
import com.water.elementmod.items.ItemBaseShiny;
import com.water.elementmod.items.tools.FireSword;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;

public class EmItems 
{
	
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	// Fire Materials
	public static final ToolMaterial MATERIAL_FIRE1 = EnumHelper.addToolMaterial("material_fire1", 3, 1561, 8.0F, 4.0F, 10);
	public static final ToolMaterial MATERIAL_FIRE2 = EnumHelper.addToolMaterial("material_fire2", 3, 1561, 8.0F, 5.0F, 10);
	public static final ToolMaterial MATERIAL_FIRE3 = EnumHelper.addToolMaterial("material_fire3", 3, 1561, 8.0F, 5.0F, 10);
	public static final ToolMaterial MATERIAL_FIRE4 = EnumHelper.addToolMaterial("material_fire4", 3, 1561, 8.0F, 6.0F, 10);
	public static final ToolMaterial MATERIAL_FIRE5 = EnumHelper.addToolMaterial("material_fire5", 3, 1561, 8.0F, 7.0F, 10);
	public static final ToolMaterial MATERIAL_FIRE6 = EnumHelper.addToolMaterial("material_fire6", 3, 1561, 8.0F, 7.0F, 10);
	public static final ToolMaterial MATERIAL_FIRE7 = EnumHelper.addToolMaterial("material_fire7", 3, 1561, 8.0F, 8.0F, 10);
	public static final ToolMaterial MATERIAL_FIRE8 = EnumHelper.addToolMaterial("material_fire8", 3, 1561, 8.0F, 8.0F, 10);
	public static final ToolMaterial MATERIAL_FIRE9 = EnumHelper.addToolMaterial("material_fire9", 3, 1561, 8.0F, 9.0F, 10);
	public static final ToolMaterial MATERIAL_FIRE10 = EnumHelper.addToolMaterial("material_fire10", 3, 1561, 8.0F, 10.0F, 10);
	
	public static final ItemSword FIRE_SWORD1 = new FireSword("fire_sword1", 1, MATERIAL_FIRE1);
	public static final ItemSword FIRE_SWORD2 = new FireSword("fire_sword2", 2, MATERIAL_FIRE2);
	public static final ItemSword FIRE_SWORD3 = new FireSword("fire_sword3", 3, MATERIAL_FIRE3);
	public static final ItemSword FIRE_SWORD4 = new FireSword("fire_sword4", 4, MATERIAL_FIRE4);
	public static final ItemSword FIRE_SWORD5 = new FireSword("fire_sword5", 5, MATERIAL_FIRE5);
	public static final ItemSword FIRE_SWORD6 = new FireSword("fire_sword6", 6, MATERIAL_FIRE6);
	public static final ItemSword FIRE_SWORD7 = new FireSword("fire_sword7", 7, MATERIAL_FIRE7);
	public static final ItemSword FIRE_SWORD8 = new FireSword("fire_sword8", 8, MATERIAL_FIRE8);
	public static final ItemSword FIRE_SWORD9 = new FireSword("fire_sword9", 9, MATERIAL_FIRE9);
	public static final ItemSword FIRE_SWORD10 = new FireSword("fire_sword10", 10, MATERIAL_FIRE10);
	
	public static final Item VOIDESS = new ItemBaseShiny("item_void_essence", 16);
	public static final Item VOIDSING = new ItemBaseShiny("item_void_singularity", 1);
	public static final Item VOIDCRY = new ItemBaseShiny("item_void_crystal", 16);
	
	public static final Item FIRESMPL = new ItemBase("item_fire_sample", 64); // 1.5/0.5x damage to nature
	public static final Item WATERDRP = new ItemBase("item_water_drop", 64); // 1.5/0.5x damage to fire
	public static final Item NATURESMPL = new ItemBase("item_nature_sample", 64); // 1.5/0.5x damage to water
	
	
	public static final Item EMBERS = new ItemBase("item_scolding_embers", 64);

}
