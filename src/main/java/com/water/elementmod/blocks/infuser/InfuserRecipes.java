package com.water.elementmod.blocks.infuser;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.water.elementmod.init.EmItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class InfuserRecipes 
{
	private static final InfuserRecipes INSTANCE = new InfuserRecipes();
	private final Map<ItemStack, ItemStack> smeltingList = Maps.<ItemStack, ItemStack>newHashMap();
	private final Map<ItemStack, Integer> smeltPercentage = Maps.<ItemStack, Integer>newHashMap();
	
	public static InfuserRecipes getInstance()
	{
		return INSTANCE;
	}
	
	private InfuserRecipes() 
	{
		// input, output, success percentage
		addInfuserRecipe(new ItemStack(EmItems.FIRESMPL), new ItemStack(EmItems.EMBERS), 70);
	}

	
	public void addInfuserRecipe(ItemStack input, ItemStack result, int percent) 
	{
		if(getInfuserResult(input) != ItemStack.EMPTY) return;
		this.smeltingList.put(input, result);
		this.smeltPercentage.put(input, percent);
	}
	
	public ItemStack getInfuserResult(ItemStack input)
	{
		for (Entry<ItemStack, ItemStack> entry : this.smeltingList.entrySet())
		{
			if (this.compareItemStacks(input, entry.getKey()))
			{
				return entry.getValue();
			}
		}
		return ItemStack.EMPTY;
	}
	
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
	{
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}
	
	public Map<ItemStack, ItemStack> getSmeltingList() 
	{
		return this.smeltingList;
	}
	
	public int getInfuserPercentage(ItemStack stack)
	{
		for (Entry<ItemStack, Integer> entry : this.smeltPercentage.entrySet()) 
		{
			if(this.compareItemStacks(stack, (ItemStack)entry.getKey())) 
			{
				return ((Integer)entry.getValue()).intValue();
			}
		}
		return 100;
	}
}
