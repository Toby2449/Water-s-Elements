package com.water.elementmod.blocks.infuser;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.water.elementmod.init.EmItems;

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
		addInfuserItemRecipe(Items.DIAMOND_SWORD, new ItemStack(EmItems.FIRESMPL), EmItems.FIRE_SWORD1);
		addInfuserItemRecipe(EmItems.FIRE_SWORD1, new ItemStack(EmItems.FIRESMPL), EmItems.FIRE_SWORD2);
		addInfuserItemRecipe(EmItems.FIRE_SWORD2, new ItemStack(EmItems.FIRESMPL), EmItems.FIRE_SWORD3);
		addInfuserItemRecipe(EmItems.FIRE_SWORD3, new ItemStack(EmItems.FIRESMPL), EmItems.FIRE_SWORD4);
		addInfuserItemRecipe(EmItems.FIRE_SWORD4, new ItemStack(EmItems.FIRESMPL), EmItems.FIRE_SWORD5);
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
