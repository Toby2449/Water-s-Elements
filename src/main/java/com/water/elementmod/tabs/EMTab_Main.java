package com.water.elementmod.tabs;

import com.water.elementmod.EMCoreItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EMTab_Main extends CreativeTabs 
{

	public EMTab_Main() 
	{
		super("em_main");
	}

	@Override
	public ItemStack getTabIconItem() 
	{
		return new ItemStack(EMCoreItems.FIRESMPL);
	}
}
