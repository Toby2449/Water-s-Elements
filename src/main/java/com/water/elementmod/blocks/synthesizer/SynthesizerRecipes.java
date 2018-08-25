package com.water.elementmod.blocks.synthesizer;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.water.elementmod.init.EmItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class SynthesizerRecipes 
{
	private static final SynthesizerRecipes INSTANCE = new SynthesizerRecipes();
	private final Table<ItemStack, ItemStack, ItemStack> smeltingList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();
	
	public static SynthesizerRecipes getInstance()
	{
		return INSTANCE;
	}
	
	private SynthesizerRecipes() 
	{
		addSynthesizerRecipe(new ItemStack(EmItems.FIRESMPL), new ItemStack(EmItems.FIRESMPL), new ItemStack(Items.QUARTZ), 5.0F);
		addSynthesizerRecipe(new ItemStack(EmItems.NATURESMPL), new ItemStack(EmItems.NATURESMPL), new ItemStack(Items.QUARTZ), 5.0F);
		addSynthesizerRecipe(new ItemStack(EmItems.WATERDRP), new ItemStack(EmItems.WATERDRP), new ItemStack(Items.QUARTZ), 5.0F);
	}

	
	public void addSynthesizerRecipe(ItemStack input1, ItemStack input2, ItemStack result, float experience) 
	{
		if(getSynthesizerResult(input1, input2) != ItemStack.EMPTY) return;
		this.smeltingList.put(input1, input2, result);
		this.experienceList.put(result, Float.valueOf(experience));
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
	
	public float getSynthesizerExperience(ItemStack stack)
	{
		for (Entry<ItemStack, Float> entry : this.experienceList.entrySet()) 
		{
			if(this.compareItemStacks(stack, (ItemStack)entry.getKey())) 
			{
				return ((Float)entry.getValue()).floatValue();
			}
		}
		return 0.0F;
	}
}
