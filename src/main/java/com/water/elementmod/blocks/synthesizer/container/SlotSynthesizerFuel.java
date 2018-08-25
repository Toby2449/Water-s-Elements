package com.water.elementmod.blocks.synthesizer.container;

import com.water.elementmod.blocks.synthesizer.TileEntitySynthesizer;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSynthesizerFuel extends Slot 
{

	public SlotSynthesizerFuel(IInventory inventory, int index, int x, int y) 
	{
		super(inventory, index, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return TileEntitySynthesizer.isItemFuel(stack);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) 
	{
		return super.getItemStackLimit(stack);
	}

}
