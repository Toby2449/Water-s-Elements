package com.water.elementmod.creativetabs;

import com.water.elementmod.init.EmItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Tab_Everything extends CreativeTabs 
{

	public Tab_Everything() 
	{
		super("emeverything");
	}

	@Override
	public ItemStack getTabIconItem() 
	{
		return new ItemStack(EmItems.FIRESMPL);
	}

}
