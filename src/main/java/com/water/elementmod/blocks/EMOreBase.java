package com.water.elementmod.blocks;

import java.util.Random;

import com.water.elementmod.EMCoreBlocks;
import com.water.elementmod.EMCoreItems;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EMOreBase extends EMBlockBase
{
	private Item item;
	private int min, max;
	private int expMin, expMax;
	
	public EMOreBase(String name, Item item, int min, int max, Material material, SoundType soundtype, Float hardness, Float resistance, String type, Integer level, Float lightlevel, Integer opacity, Integer expMin, Integer expMax)
	{
		super(name, material, soundtype, hardness, resistance, type, level, lightlevel, opacity);
		this.item = item;
		this.min = min;
		this.max = max;
		this.expMin = expMin;
		this.expMax = expMax;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return this.item;
	}
	
	@Override
	public int quantityDroppedWithBonus(int fortune, Random rand)
    {
		int quantityDiff = this.max - this.min;
		if(this.max == this.min) return this.max;
		return rand.nextInt(quantityDiff) + min;
    }
	
	@Override
	public int quantityDropped(Random rand) {
		int quantityDiff = this.max - this.min;
		if(this.max == this.min) return this.max;
		return rand.nextInt(quantityDiff) + min;
	}
	
	@Override
    public int getExpDrop(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
    {
        if (this.getItemDropped(state, RANDOM, fortune) != Item.getItemFromBlock(this))
        {
        	int expDiff = this.expMax - this.expMin;
            return this.expMin + RANDOM.nextInt(expDiff);
        }
        return 0;
    }
	
}
