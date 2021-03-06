package com.water.elementmod.blocks.extractor.container;

import com.water.elementmod.blocks.extractor.ExtractorRecipes;
import com.water.elementmod.blocks.extractor.TileEntityExtractor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerExtractor extends Container
{
	private final TileEntityExtractor tileentity;
	private int cookTime, totalCookTime, burnTime, currentBurnTime;
	
	public ContainerExtractor(InventoryPlayer player, TileEntityExtractor tileentity)
	{
		this.tileentity = tileentity;
		
		this.addSlotToContainer(new Slot(tileentity, 0, 26, 34)); // input 1
		this.addSlotToContainer(new SlotExtractorFuel(tileentity, 1, 132, 10)); // fuel
		this.addSlotToContainer(new SlotExtractorOutput(player.player, tileentity, 2, 80, 34)); // output
		
		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 9; x++)
			{
				this.addSlotToContainer(new Slot(player, x+y*9+9, 8 + x * 18, 84 + y*18));
			}
		}
		
		for(int x = 0; x < 9; x++)
		{
			this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
		}
	}
	
	@Override
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.tileentity);
	}
	
	@Override
	public void detectAndSendChanges() 
	{
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); ++i) 
		{
			IContainerListener listener = (IContainerListener)this.listeners.get(i);
			
			if(this.cookTime != this.tileentity.getField(2)) listener.sendWindowProperty(this, 2, this.tileentity.getField(2));
			if(this.burnTime != this.tileentity.getField(0)) listener.sendWindowProperty(this, 0, this.tileentity.getField(0));
			if(this.currentBurnTime != this.tileentity.getField(1)) listener.sendWindowProperty(this, 1, this.tileentity.getField(1));
			if(this.totalCookTime != this.tileentity.getField(3)) listener.sendWindowProperty(this, 3, this.tileentity.getField(3));
		}
		
		this.cookTime = this.tileentity.getField(2);
		this.burnTime = this.tileentity.getField(0);
		this.currentBurnTime = this.tileentity.getField(1);
		this.totalCookTime = this.tileentity.getField(3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) 
	{
		this.tileentity.setField(id, data);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) 
	{
		return this.tileentity.isUsableByPlayer(playerIn);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) 
	{
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot)this.inventorySlots.get(index);
		
		if(slot != null && slot.getHasStack()) 
		{
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			
			if(index == 2) 
			{
				if(!this.mergeItemStack(stack1, 3, 39, true)) return ItemStack.EMPTY;
				slot.onSlotChange(stack1, stack);
			}
			else if(index != 1 && index != 0) 
			{		
				Slot slot1 = (Slot)this.inventorySlots.get(index);
				
				if (!ExtractorRecipes.getInstance().getExtractorResult(stack1).isEmpty())
                {
                    if (!this.mergeItemStack(stack1, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (TileEntityExtractor.isItemFuel(stack1))
                {
                    if (!this.mergeItemStack(stack1, 1, 2, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
				
				if(!this.mergeItemStack(stack1, 0, 1, false)) 
				{
					return ItemStack.EMPTY;
				}
				else if(TileEntityExtractor.isItemFuel(stack1))
				{
					if(!this.mergeItemStack(stack1, 1, 2, false)) return ItemStack.EMPTY;
				}
				else if (index >= 3 && index < 30)
                {
                    if (!this.mergeItemStack(stack1, 30, 39, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 30 && index < 39 && !this.mergeItemStack(stack1, 3, 30, false))
                {
                    return ItemStack.EMPTY;
                }
			} 
			else if(!this.mergeItemStack(stack1, 3, 39, false)) 
			{
				return ItemStack.EMPTY;
			}
			if(stack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();

			}
			if(stack1.getCount() == stack.getCount()) return ItemStack.EMPTY;
			slot.onTake(playerIn, stack1);
		}
		return stack;
	}
}
