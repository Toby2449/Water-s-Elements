package com.water.elementmod.blocks.infuser.container;

import com.water.elementmod.blocks.infuser.InfuserRecipes;
import com.water.elementmod.blocks.infuser.TileEntityInfuser;
import com.water.elementmod.blocks.synthesizer.SynthesizerRecipes;
import com.water.elementmod.blocks.synthesizer.TileEntitySynthesizer;

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

public class ContainerInfuser extends Container
{
	private final TileEntityInfuser tileentity;
	private int cookTime, totalCookTime, burnTime, currentBurnTime, compatible;
	
	public ContainerInfuser(InventoryPlayer player, TileEntityInfuser tileentity)
	{
		this.tileentity = tileentity;
		
		this.addSlotToContainer(new SlotInfuserSword(tileentity, 0, 12, 32)); // input 1
		this.addSlotToContainer(new Slot(tileentity, 1, 55, 32)); // input 2
		this.addSlotToContainer(new SlotInfuserFuel(tileentity, 2, 139, 10)); // fuel
		this.addSlotToContainer(new SlotInfuserOutput(player.player, tileentity, 3, 101, 32)); // output
		
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
			if(this.compatible != this.tileentity.getField(4)) listener.sendWindowProperty(this, 4, this.tileentity.getField(4));
		}
		
		this.compatible = this.tileentity.getField(4);
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
			
			if(index == 3) 
			{
				if(!this.mergeItemStack(stack1, 4, 40, true)) return ItemStack.EMPTY;
				slot.onSlotChange(stack1, stack);
			}
			else if(index != 2 && index != 1 && index != 0) 
			{		
				Slot slot1 = (Slot)this.inventorySlots.get(index);
				
				if (!InfuserRecipes.getInstance().getInfuserResult(stack1, slot1.getStack()).isEmpty())
                {
                    if (!this.mergeItemStack(stack1, 0, 2, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (TileEntityInfuser.isItemFuel(stack1))
                {
                    if (!this.mergeItemStack(stack1, 2, 3, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
				
				else if (index >= 4 && index < 40)
                {
                    if (!this.mergeItemStack(stack1, 0, 2, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
			} 
			else if(!this.mergeItemStack(stack1, 4, 40, false)) 
			{
				return ItemStack.EMPTY;
			}
			if (stack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (stack1.getCount() == stack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack1);
		}
		return stack;
	}
}
