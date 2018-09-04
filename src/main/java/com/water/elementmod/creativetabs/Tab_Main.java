package com.water.elementmod.creativetabs;

import com.water.elementmod.init.EmItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class Tab_Main extends CreativeTabs 
{

	public Tab_Main() 
	{
		super("emmain");
	}

	@Override
	public ItemStack getTabIconItem() 
	{
		return new ItemStack(EmItems.FIRESMPL);
	}
}
