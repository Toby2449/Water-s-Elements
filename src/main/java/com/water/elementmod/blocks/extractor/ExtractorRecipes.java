package com.water.elementmod.blocks.extractor;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.water.elementmod.EMCoreItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ExtractorRecipes 
{
	private static final ExtractorRecipes INSTANCE = new ExtractorRecipes();
	private final Map<ItemStack, ItemStack> smeltingList = Maps.<ItemStack, ItemStack>newHashMap();
	private final Map<ItemStack, Integer> smeltPercentage = Maps.<ItemStack, Integer>newHashMap();
	
	public static ExtractorRecipes getInstance()
	{
		return INSTANCE;
	}
	
	private ExtractorRecipes() 
	{
		// input, output, success percentage
		addExtractorRecipe(new ItemStack(EMCoreItems.FIRESMPL_Z), new ItemStack(EMCoreItems.ASHES), 80);
		addExtractorRecipe(new ItemStack(EMCoreItems.WATERSMPL_Z), new ItemStack(EMCoreItems.MICRO_DRPS), 80);
		addExtractorRecipe(new ItemStack(EMCoreItems.NATURESMPL_Z), new ItemStack(EMCoreItems.VARIOUS_ORGANISMS), 80);
		addExtractorRecipe(new ItemStack(EMCoreItems.FIRESMPL_S), new ItemStack(EMCoreItems.ASHES), 80);
		addExtractorRecipe(new ItemStack(EMCoreItems.WATERSMPL_S), new ItemStack(EMCoreItems.MICRO_DRPS), 80);
		addExtractorRecipe(new ItemStack(EMCoreItems.NATURESMPL_S), new ItemStack(EMCoreItems.VARIOUS_ORGANISMS), 80);
	}

	
	public void addExtractorRecipe(ItemStack input, ItemStack result, int percent) 
	{
		if(getExtractorResult(input) != ItemStack.EMPTY) return;
		this.smeltingList.put(input, result);
		this.smeltPercentage.put(input, percent);
	}
	
	public ItemStack getExtractorResult(ItemStack input)
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
	
	public int getExtractorPercentage(ItemStack stack)
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
