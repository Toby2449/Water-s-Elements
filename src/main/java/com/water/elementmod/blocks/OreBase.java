package com.water.elementmod.blocks;

import java.util.Random;

import com.water.elementmod.init.EmBlocks;
import com.water.elementmod.init.EmItems;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class OreBase extends BlockBase
{
	private Item item;
	private int min, max;
	
	public OreBase(String name, Item item, int min, int max, Material material, SoundType soundtype, Float hardness, Float resistance, String type, Integer level, Float lightlevel, Integer opacity)
	{
		super(name, material, soundtype, hardness, resistance, type, level, lightlevel, opacity);
		this.item = item;
		this.min = min;
		this.max = max;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return this.item;
	}
	
	@Override
	public int quantityDropped(Random rand) {
		if(this.max == this.min) return this.max;
		return rand.nextInt(max) + min;
	}
	
}
