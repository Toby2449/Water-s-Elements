package com.water.elementmod.items;

import com.water.elementmod.Main;
import com.water.elementmod.init.EmItems;
import com.water.elementmod.util.IHasModel;
import com.water.elementmod.util.Utils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel
{

	public ItemBase(String name, Integer StackSize)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		maxStackSize = StackSize;
		setCreativeTab(Main.tab_everything);
		
		
		EmItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
		Utils.getLogger().info("Registered render for item." + this.getUnlocalizedName().substring(5));
	}
}
