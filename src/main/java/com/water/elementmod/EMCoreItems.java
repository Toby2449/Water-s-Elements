package com.water.elementmod;

import java.util.ArrayList;
import java.util.List;

import com.water.elementmod.items.EMItemBase;
import com.water.elementmod.items.EMShinyItemBase;
import com.water.elementmod.items.armor.FireArmor;
import com.water.elementmod.items.armor.LeafArmor;
import com.water.elementmod.items.armor.VoidHelmet;
import com.water.elementmod.items.armor.WaterArmor;
import com.water.elementmod.items.arrow.ItemFireArrow;
import com.water.elementmod.items.arrow.ItemNatureArrow;
import com.water.elementmod.items.arrow.ItemWaterArrow;
import com.water.elementmod.items.special.ItemEternalFire;
import com.water.elementmod.items.special.ItemHeartOfTheWild;
import com.water.elementmod.items.weapons.FireSword;
import com.water.elementmod.items.weapons.NatureSword;
import com.water.elementmod.items.weapons.WaterSword;
import com.water.elementmod.util.EMConfig;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;

public class EMCoreItems 
{
	
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	// Void Materials
	public static final ArmorMaterial MATERIAL_ARMOR_VOID = EnumHelper.addArmorMaterial("material_armor_void", EMConfig.MOD_ID + ":void_armor", 16, 
			new int[] {7, 0, 0, 7}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0F);
	
	// Fire Materials
	public static final ToolMaterial MATERIAL_FIRE1 = EnumHelper.addToolMaterial("material_fire1", 3, 1561, 8.0F, 5.0F, 10);
	public static final ToolMaterial MATERIAL_FIRE2 = EnumHelper.addToolMaterial("material_fire2", 3, 1561, 8.0F, 6.0F, 10);
	public static final ToolMaterial MATERIAL_FIRE3 = EnumHelper.addToolMaterial("material_fire3", 3, 1561, 8.0F, 6.0F, 10);
	public static final ToolMaterial MATERIAL_FIRE4 = EnumHelper.addToolMaterial("material_fire4", 3, 1561, 8.0F, 7.0F, 10);
	public static final ToolMaterial MATERIAL_FIRE5 = EnumHelper.addToolMaterial("material_fire5", 3, 1561, 8.0F, 8.0F, 10);
	public static final ToolMaterial MATERIAL_FIRE6 = EnumHelper.addToolMaterial("material_fire6", 3, 1561, 8.0F, 8.0F, 10);
	public static final ToolMaterial MATERIAL_FIRE7 = EnumHelper.addToolMaterial("material_fire7", 3, 1561, 8.0F, 9.0F, 10);
	public static final ToolMaterial MATERIAL_FIRE8 = EnumHelper.addToolMaterial("material_fire8", 3, 1561, 8.0F, 9.0F, 10);
	public static final ToolMaterial MATERIAL_FIRE9 = EnumHelper.addToolMaterial("material_fire9", 3, 1561, 8.0F, 10.0F, 10);
	public static final ToolMaterial MATERIAL_FIRE10 = EnumHelper.addToolMaterial("material_fire10", 3, 1561, 8.0F, 11.0F, 10);
	public static final ArmorMaterial MATERIAL_ARMOR_FIRE = EnumHelper.addArmorMaterial("material_armor_fire", EMConfig.MOD_ID + ":fire_armor", 16, 
			new int[] {0, 9, 9, 0}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0F);
	
	// Water Materials
	public static final ToolMaterial MATERIAL_WATER1 = EnumHelper.addToolMaterial("material_water1", 3, 1561, 8.0F, 3.0F, 10);
	public static final ToolMaterial MATERIAL_WATER2 = EnumHelper.addToolMaterial("material_water2", 3, 1561, 8.0F, 4.0F, 10);
	public static final ToolMaterial MATERIAL_WATER3 = EnumHelper.addToolMaterial("material_water3", 3, 1561, 8.0F, 4.0F, 10);
	public static final ToolMaterial MATERIAL_WATER4 = EnumHelper.addToolMaterial("material_water4", 3, 1561, 8.0F, 5.0F, 10);
	public static final ToolMaterial MATERIAL_WATER5 = EnumHelper.addToolMaterial("material_water5", 3, 1561, 8.0F, 6.0F, 10);
	public static final ToolMaterial MATERIAL_WATER6 = EnumHelper.addToolMaterial("material_water6", 3, 1561, 8.0F, 6.0F, 10);
	public static final ToolMaterial MATERIAL_WATER7 = EnumHelper.addToolMaterial("material_water7", 3, 1561, 8.0F, 7.0F, 10);
	public static final ToolMaterial MATERIAL_WATER8 = EnumHelper.addToolMaterial("material_water8", 3, 1561, 8.0F, 7.0F, 10);
	public static final ToolMaterial MATERIAL_WATER9 = EnumHelper.addToolMaterial("material_water9", 3, 1561, 8.0F, 8.0F, 10);
	public static final ToolMaterial MATERIAL_WATER10 = EnumHelper.addToolMaterial("material_water10", 3, 1561, 8.0F, 9.0F, 10);
	public static final ArmorMaterial MATERIAL_ARMOR_WATER = EnumHelper.addArmorMaterial("material_armor_water", EMConfig.MOD_ID + ":water_armor", 16, 
			new int[] {0, 9, 9, 0}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0F);
	
	// Nature Materials
	public static final ToolMaterial MATERIAL_NATURE1 = EnumHelper.addToolMaterial("material_nature1", 3, 1561, 8.0F, 4.0F, 10);
	public static final ToolMaterial MATERIAL_NATURE2 = EnumHelper.addToolMaterial("material_nature2", 3, 1561, 8.0F, 5.0F, 10);
	public static final ToolMaterial MATERIAL_NATURE3 = EnumHelper.addToolMaterial("material_nature3", 3, 1561, 8.0F, 5.0F, 10);
	public static final ToolMaterial MATERIAL_NATURE4 = EnumHelper.addToolMaterial("material_nature4", 3, 1561, 8.0F, 6.0F, 10);
	public static final ToolMaterial MATERIAL_NATURE5 = EnumHelper.addToolMaterial("material_nature5", 3, 1561, 8.0F, 7.0F, 10);
	public static final ToolMaterial MATERIAL_NATURE6 = EnumHelper.addToolMaterial("material_nature6", 3, 1561, 8.0F, 7.0F, 10);
	public static final ToolMaterial MATERIAL_NATURE7 = EnumHelper.addToolMaterial("material_nature7", 3, 1561, 8.0F, 8.0F, 10);
	public static final ToolMaterial MATERIAL_NATURE8 = EnumHelper.addToolMaterial("material_nature8", 3, 1561, 8.0F, 8.0F, 10);
	public static final ToolMaterial MATERIAL_NATURE9 = EnumHelper.addToolMaterial("material_nature9", 3, 1561, 8.0F, 9.0F, 10);
	public static final ToolMaterial MATERIAL_NATURE10 = EnumHelper.addToolMaterial("material_nature10", 3, 1561, 8.0F, 10.0F, 10);
	public static final ArmorMaterial MATERIAL_ARMOR_NATURE = EnumHelper.addArmorMaterial("material_armor_nature", EMConfig.MOD_ID + ":nature_armor", 16, 
			new int[] {0, 9, 9, 0}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0F);
	
	
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
	public static final Item FIRE_CHESTPLATE = new FireArmor("item_fire_chestplate", MATERIAL_ARMOR_FIRE, 1, EntityEquipmentSlot.CHEST);
	
	public static final ItemSword WATER_SWORD1 = new WaterSword("water_sword1", 1, MATERIAL_WATER1);
	public static final ItemSword WATER_SWORD2 = new WaterSword("water_sword2", 2, MATERIAL_WATER2);
	public static final ItemSword WATER_SWORD3 = new WaterSword("water_sword3", 3, MATERIAL_WATER3);
	public static final ItemSword WATER_SWORD4 = new WaterSword("water_sword4", 4, MATERIAL_WATER4);
	public static final ItemSword WATER_SWORD5 = new WaterSword("water_sword5", 5, MATERIAL_WATER5);
	public static final ItemSword WATER_SWORD6 = new WaterSword("water_sword6", 6, MATERIAL_WATER6);
	public static final ItemSword WATER_SWORD7 = new WaterSword("water_sword7", 7, MATERIAL_WATER7);
	public static final ItemSword WATER_SWORD8 = new WaterSword("water_sword8", 8, MATERIAL_WATER8);
	public static final ItemSword WATER_SWORD9 = new WaterSword("water_sword9", 9, MATERIAL_WATER9);
	public static final ItemSword WATER_SWORD10 = new WaterSword("water_sword10", 10, MATERIAL_WATER10);
	public static final Item WATER_ROBE = new WaterArmor("item_water_robe", MATERIAL_ARMOR_WATER, 1, EntityEquipmentSlot.CHEST);
	
	public static final ItemSword NATURE_SWORD1 = new NatureSword("nature_sword1", 1, MATERIAL_NATURE1);
	public static final ItemSword NATURE_SWORD2 = new NatureSword("nature_sword2", 2, MATERIAL_NATURE2);
	public static final ItemSword NATURE_SWORD3 = new NatureSword("nature_sword3", 3, MATERIAL_NATURE3);
	public static final ItemSword NATURE_SWORD4 = new NatureSword("nature_sword4", 4, MATERIAL_NATURE4);
	public static final ItemSword NATURE_SWORD5 = new NatureSword("nature_sword5", 5, MATERIAL_NATURE5);
	public static final ItemSword NATURE_SWORD6 = new NatureSword("nature_sword6", 6, MATERIAL_NATURE6);
	public static final ItemSword NATURE_SWORD7 = new NatureSword("nature_sword7", 7, MATERIAL_NATURE7);
	public static final ItemSword NATURE_SWORD8 = new NatureSword("nature_sword8", 8, MATERIAL_NATURE8);
	public static final ItemSword NATURE_SWORD9 = new NatureSword("nature_sword9", 9, MATERIAL_NATURE9);
	public static final ItemSword NATURE_SWORD10 = new NatureSword("nature_sword10", 10, MATERIAL_NATURE10);
	public static final Item LEAF_ARMOR = new LeafArmor("item_leaf_armor", MATERIAL_ARMOR_NATURE, 1, EntityEquipmentSlot.CHEST);
	
	public static final Item VOID_HELMET = new VoidHelmet("item_void_helmet", MATERIAL_ARMOR_VOID, 1, EntityEquipmentSlot.HEAD);
	
	public static final Item VOIDESS = new EMShinyItemBase("item_void_essence", 16);
	public static final Item VOIDSING = new EMShinyItemBase("item_void_singularity", 1);
	public static final Item VOIDCRY = new EMShinyItemBase("item_void_crystal", 16);
	
	public static final Item FIRESMPL_Z = new EMItemBase("item_zombieheart_fire", 64);
	public static final Item WATERSMPL_Z = new EMItemBase("item_zombieheart_water", 64);
	public static final Item NATURESMPL_Z = new EMItemBase("item_zombieheart_nature", 64);
	public static final Item FIRESMPL_S = new EMItemBase("item_skeletonfemur_fire", 64);
	public static final Item WATERSMPL_S = new EMItemBase("item_skeletonfemur_water", 64);
	public static final Item NATURESMPL_S = new EMItemBase("item_skeletonfemur_nature", 64);
	
	public static final Item VARIOUS_ORGANISMS = new EMItemBase("item_various_micro_organisms", 64);
	public static final Item THRIVING_CELLS = new EMItemBase("item_thriving_cells", 64);
	public static final Item RECONSTRUCTED_TISSUE = new EMItemBase("item_reconstructed_tissue", 64);
	public static final Item NATURE_ESSENCE = new EMShinyItemBase("item_nature_essence", 16);
	public static final Item HEART_OF_THE_WILD = new ItemHeartOfTheWild("item_heart_of_the_wild");
	
	public static final Item ASHES = new EMItemBase("item_ashes", 64);
	public static final Item BURNING_ASHES = new EMItemBase("item_burning_ashes", 64);
	public static final Item EMBERS = new EMItemBase("item_scolding_embers", 64);
	public static final Item FLAMING_EMBERS = new EMShinyItemBase("item_flaming_embers", 16);
	public static final Item ETERNAL_FIRE = new ItemEternalFire("item_eternal_fire");
	
	public static final Item MICRO_DRPS = new EMItemBase("item_micro_droplets", 64);
	public static final Item SMALL_DRPS = new EMItemBase("item_small_droplets", 64);
	public static final Item LARGE_DRPS = new EMItemBase("item_large_droplets", 64);
	public static final Item PERPETUAL_DRP = new EMShinyItemBase("item_perpetual_droplet", 1);
	
	public static final Item NATURE_ARROW = new ItemNatureArrow("item_nature_arrow", 64);
	public static final Item WATER_ARROW = new ItemWaterArrow("item_water_arrow", 64);
	public static final Item FIRE_ARROW = new ItemFireArrow("item_fire_arrow", 64);
	
}
