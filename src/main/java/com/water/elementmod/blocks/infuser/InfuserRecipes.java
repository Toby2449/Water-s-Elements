package com.water.elementmod.blocks.infuser;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.water.elementmod.EMCoreItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InfuserRecipes 
{
	private static final InfuserRecipes INSTANCE = new InfuserRecipes();
	private final Table<ItemStack, ItemStack, ItemStack> smeltingList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
	
	public static InfuserRecipes getInstance()
	{
		return INSTANCE;
	}
	
	private InfuserRecipes() 
	{
		addInfuserItemRecipe(Items.DIAMOND_SWORD, new ItemStack(EMCoreItems.ETERNAL_FIRE), EMCoreItems.FIRE_SWORD1);
		addInfuserItemRecipe(EMCoreItems.FIRE_SWORD1, new ItemStack(EMCoreItems.ETERNAL_FIRE), EMCoreItems.FIRE_SWORD2);
		addInfuserItemRecipe(EMCoreItems.FIRE_SWORD2, new ItemStack(EMCoreItems.ETERNAL_FIRE), EMCoreItems.FIRE_SWORD3);
		addInfuserItemRecipe(EMCoreItems.FIRE_SWORD3, new ItemStack(EMCoreItems.ETERNAL_FIRE), EMCoreItems.FIRE_SWORD4);
		addInfuserItemRecipe(EMCoreItems.FIRE_SWORD4, new ItemStack(EMCoreItems.ETERNAL_FIRE), EMCoreItems.FIRE_SWORD5);
		addInfuserItemRecipe(EMCoreItems.FIRE_SWORD5, new ItemStack(EMCoreItems.ETERNAL_FIRE), EMCoreItems.FIRE_SWORD6);
		addInfuserItemRecipe(EMCoreItems.FIRE_SWORD6, new ItemStack(EMCoreItems.ETERNAL_FIRE), EMCoreItems.FIRE_SWORD7);
		addInfuserItemRecipe(EMCoreItems.FIRE_SWORD7, new ItemStack(EMCoreItems.ETERNAL_FIRE), EMCoreItems.FIRE_SWORD8);
		addInfuserItemRecipe(EMCoreItems.FIRE_SWORD8, new ItemStack(EMCoreItems.ETERNAL_FIRE), EMCoreItems.FIRE_SWORD9);
		addInfuserItemRecipe(EMCoreItems.FIRE_SWORD9, new ItemStack(EMCoreItems.ETERNAL_FIRE), EMCoreItems.FIRE_SWORD10);
		
		addInfuserItemRecipe(Items.DIAMOND_SWORD, new ItemStack(EMCoreItems.PERPETUAL_DRP), EMCoreItems.WATER_SWORD1);
		addInfuserItemRecipe(EMCoreItems.WATER_SWORD1, new ItemStack(EMCoreItems.PERPETUAL_DRP), EMCoreItems.WATER_SWORD2);
		addInfuserItemRecipe(EMCoreItems.WATER_SWORD2, new ItemStack(EMCoreItems.PERPETUAL_DRP), EMCoreItems.WATER_SWORD3);
		addInfuserItemRecipe(EMCoreItems.WATER_SWORD3, new ItemStack(EMCoreItems.PERPETUAL_DRP), EMCoreItems.WATER_SWORD4);
		addInfuserItemRecipe(EMCoreItems.WATER_SWORD4, new ItemStack(EMCoreItems.PERPETUAL_DRP), EMCoreItems.WATER_SWORD5);
		addInfuserItemRecipe(EMCoreItems.WATER_SWORD5, new ItemStack(EMCoreItems.PERPETUAL_DRP), EMCoreItems.WATER_SWORD6);
		addInfuserItemRecipe(EMCoreItems.WATER_SWORD6, new ItemStack(EMCoreItems.PERPETUAL_DRP), EMCoreItems.WATER_SWORD7);
		addInfuserItemRecipe(EMCoreItems.WATER_SWORD7, new ItemStack(EMCoreItems.PERPETUAL_DRP), EMCoreItems.WATER_SWORD8);
		addInfuserItemRecipe(EMCoreItems.WATER_SWORD8, new ItemStack(EMCoreItems.PERPETUAL_DRP), EMCoreItems.WATER_SWORD9);
		addInfuserItemRecipe(EMCoreItems.WATER_SWORD9, new ItemStack(EMCoreItems.PERPETUAL_DRP), EMCoreItems.WATER_SWORD10);
		
		addInfuserItemRecipe(Items.DIAMOND_SWORD, new ItemStack(EMCoreItems.NATURE_ESSENCE), EMCoreItems.NATURE_SWORD1);
		addInfuserItemRecipe(EMCoreItems.NATURE_SWORD1, new ItemStack(EMCoreItems.NATURE_ESSENCE), EMCoreItems.NATURE_SWORD2);
		addInfuserItemRecipe(EMCoreItems.NATURE_SWORD2, new ItemStack(EMCoreItems.NATURE_ESSENCE), EMCoreItems.NATURE_SWORD3);
		addInfuserItemRecipe(EMCoreItems.NATURE_SWORD3, new ItemStack(EMCoreItems.NATURE_ESSENCE), EMCoreItems.NATURE_SWORD4);
		addInfuserItemRecipe(EMCoreItems.NATURE_SWORD4, new ItemStack(EMCoreItems.NATURE_ESSENCE), EMCoreItems.NATURE_SWORD5);
		addInfuserItemRecipe(EMCoreItems.NATURE_SWORD5, new ItemStack(EMCoreItems.NATURE_ESSENCE), EMCoreItems.NATURE_SWORD6);
		addInfuserItemRecipe(EMCoreItems.NATURE_SWORD6, new ItemStack(EMCoreItems.NATURE_ESSENCE), EMCoreItems.NATURE_SWORD7);
		addInfuserItemRecipe(EMCoreItems.NATURE_SWORD7, new ItemStack(EMCoreItems.NATURE_ESSENCE), EMCoreItems.NATURE_SWORD8);
		addInfuserItemRecipe(EMCoreItems.NATURE_SWORD8, new ItemStack(EMCoreItems.NATURE_ESSENCE), EMCoreItems.NATURE_SWORD9);
		addInfuserItemRecipe(EMCoreItems.NATURE_SWORD9, new ItemStack(EMCoreItems.NATURE_ESSENCE), EMCoreItems.NATURE_SWORD10);
	}
	
	public void addInfuserItemRecipe(Item input, ItemStack stack, Item result)
    {
        addInfuserRecipe(stack, new ItemStack(input, 1), new ItemStack(result, 1));
    }
	
	public void addInfuserRecipe(ItemStack input, ItemStack input2, ItemStack result) 
	{
		if(getInfuserResult(input, input2) != ItemStack.EMPTY) return;
		this.smeltingList.put(input, input2, result);
	}
	
	public ItemStack getInfuserResult(ItemStack input1, ItemStack input2) 
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
		return stack2.getItem() == stack1.getItem();
	}
	
	public Table<ItemStack, ItemStack, ItemStack> getDualSmeltingList() 
	{
		return this.smeltingList;
	}
}
