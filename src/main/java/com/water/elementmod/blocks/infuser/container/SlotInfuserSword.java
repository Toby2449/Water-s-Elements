package com.water.elementmod.blocks.infuser.container;

import com.water.elementmod.blocks.infuser.TileEntityInfuser;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class SlotInfuserSword extends Slot 
{

	public SlotInfuserSword(IInventory inventory, int index, int x, int y) 
	{
		super(inventory, index, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return stack.getItem() instanceof ItemSword;
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) 
	{
		return 1;
	}

}
