package com.water.elementmod.tabs;

import com.water.elementmod.EMCoreItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class EMTab_Weapons extends CreativeTabs 
{

	public EMTab_Weapons() 
	{
		super("em_weapons");
	}

	@Override
	public ItemStack getTabIconItem() 
	{
		return new ItemStack(EMCoreItems.FIRE_SWORD1);
	}
}
