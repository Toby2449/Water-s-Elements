package com.water.elementmod.blocks.synthesizer;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.water.elementmod.EMCoreItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class SynthesizerRecipes 
{
	private static final SynthesizerRecipes INSTANCE = new SynthesizerRecipes();
	private final Table<ItemStack, ItemStack, ItemStack> smeltingList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
	
	public static SynthesizerRecipes getInstance()
	{
		return INSTANCE;
	}
	
	private SynthesizerRecipes() 
	{
		addSynthesizerRecipe(new ItemStack(EMCoreItems.LARGE_DRPS), new ItemStack(EMCoreItems.LARGE_DRPS), new ItemStack(EMCoreItems.PERPETUAL_DRP));
		addSynthesizerRecipe(new ItemStack(EMCoreItems.EMBERS), new ItemStack(EMCoreItems.EMBERS), new ItemStack(EMCoreItems.ETERNAL_FIRE));
		
		addSynthesizerRecipe(new ItemStack(EMCoreItems.RECONSTRUCTED_TISSUE), new ItemStack(EMCoreItems.HEART_OF_THE_WILD), new ItemStack(EMCoreItems.NATURE_ESSENCE));
		addSynthesizerRecipe(new ItemStack(EMCoreItems.HEART_OF_THE_WILD), new ItemStack(EMCoreItems.RECONSTRUCTED_TISSUE), new ItemStack(EMCoreItems.NATURE_ESSENCE));
		for(int i = 0; i <= 30; i++)
		{
			addSynthesizerRecipe(new ItemStack(EMCoreItems.RECONSTRUCTED_TISSUE), new ItemStack(EMCoreItems.HEART_OF_THE_WILD, 1, i), new ItemStack(EMCoreItems.NATURE_ESSENCE));
			addSynthesizerRecipe(new ItemStack(EMCoreItems.HEART_OF_THE_WILD, 1, i), new ItemStack(EMCoreItems.RECONSTRUCTED_TISSUE), new ItemStack(EMCoreItems.NATURE_ESSENCE));
		}
		
		addSynthesizerRecipe(new ItemStack(EMCoreItems.EMBERS), new ItemStack(EMCoreItems.ETERNAL_FIRE), new ItemStack(EMCoreItems.FLAMING_EMBERS));
		addSynthesizerRecipe(new ItemStack(EMCoreItems.ETERNAL_FIRE), new ItemStack(EMCoreItems.EMBERS), new ItemStack(EMCoreItems.FLAMING_EMBERS));
		for(int i = 0; i <= 30; i++)
		{
			addSynthesizerRecipe(new ItemStack(EMCoreItems.EMBERS), new ItemStack(EMCoreItems.ETERNAL_FIRE, 1, i), new ItemStack(EMCoreItems.FLAMING_EMBERS));
			addSynthesizerRecipe(new ItemStack(EMCoreItems.ETERNAL_FIRE, 1, i), new ItemStack(EMCoreItems.EMBERS), new ItemStack(EMCoreItems.FLAMING_EMBERS));
		}
	}
	
	
	public void addSynthesizerRecipe(ItemStack input1, ItemStack input2, ItemStack result) 
	{
		if(getSynthesizerResult(input1, input2) != ItemStack.EMPTY) return;
		this.smeltingList.put(input1, input2, result);
	}
	
	public ItemStack getSynthesizerResult(ItemStack input1, ItemStack input2) 
	{
		for(Entry<ItemStack, Map<ItemStack, ItemStack>> entry : this.smeltingList.columnMap().entrySet()) 
		{
			if(this.compareItemStacks(input1, (ItemStack)entry.getKey())) 
			{
				for(Entry<ItemStack, ItemStack> ent : entry.getValue().entrySet()) 
				{
					if(this.compareItemStacks(input2, (ItemStack)ent.getKey())) 
					{
						return (ItemStack)ent.getValue();
					}
				}
			}
		}
		return ItemStack.EMPTY;
	}
	
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
	{
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}
	
	public Table<ItemStack, ItemStack, ItemStack> getDualSmeltingList() 
	{
		return this.smeltingList;
	}
}
