package com.water.elementmod.creativetabs;

import com.water.elementmod.init.EmItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class Tab_Weapons extends CreativeTabs 
{

	public Tab_Weapons() 
	{
		super("emweapons");
	}

	@Override
	public ItemStack getTabIconItem() 
	{
		return new ItemStack(EmItems.FIRE_SWORD1);
	}
}
