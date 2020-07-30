package com.water.elementmod.blocks.slab;

import java.util.Random;

import com.water.elementmod.EMCore;
import com.water.elementmod.EMCoreBlocks;
import com.water.elementmod.EMCoreItems;
import com.water.elementmod.util.Utils;
import com.water.elementmod.util.interfaces.IHasModel;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;

public class EMBlockHalfSlabBase extends EMBlockSlabBase implements IHasModel
{
	public EMBlockHalfSlabBase(String name, Material material, CreativeTabs tab, BlockSlab half, BlockSlab doubleSlab) {
		super(name, material, half);
		setCreativeTab(tab);
		EMCoreItems.ITEMS.add(new ItemSlab(this, this, doubleSlab).setRegistryName(this.getRegistryName()));
	}

	@Override
	public boolean isDouble() 
	{
		return false;
	}
	
	@Override
	public void registerModels() 
	{
		EMCore.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
		Utils.getLogger().info("Registered render for block." + this.getUnlocalizedName().substring(5));
	}
}
