package com.water.elementmod.blocks.slab;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class EMBlockDoubleSlabBase extends EMBlockSlabBase
{
	public EMBlockDoubleSlabBase(String name, Material material, CreativeTabs tab, BlockSlab half) 
	{
		super(name, material, half);
		setCreativeTab(tab);
	}

	@Override
	public boolean isDouble() 
	{
		return true;
	}
}
