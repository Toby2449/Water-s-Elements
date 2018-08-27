package com.water.elementmod.blocks;

import java.util.Random;

import com.water.elementmod.init.EmBlocks;
import com.water.elementmod.init.EmItems;

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
	
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
		this.spawnParticles(worldIn, pos, rand);
    }
	
	private void spawnParticles(World worldIn, BlockPos pos, Random rand)
    {
        Random random = worldIn.rand;
        double d0 = 0.0625D;

        for (int i = 0; i < 6; ++i)
        {
            double d1 = (double)((float)pos.getX() + random.nextFloat());
            double d2 = (double)((float)pos.getY() + random.nextFloat());
            double d3 = (double)((float)pos.getZ() + random.nextFloat());

            if (i == 0 && !worldIn.getBlockState(pos.up()).isOpaqueCube())
            {
                d2 = (double)pos.getY() + 0.0625D + 1.0D;
            }

            if (i == 1 && !worldIn.getBlockState(pos.down()).isOpaqueCube())
            {
                d2 = (double)pos.getY() - 0.0625D;
            }

            if (i == 2 && !worldIn.getBlockState(pos.south()).isOpaqueCube())
            {
                d3 = (double)pos.getZ() + 0.0625D + 1.0D;
            }

            if (i == 3 && !worldIn.getBlockState(pos.north()).isOpaqueCube())
            {
                d3 = (double)pos.getZ() - 0.0625D;
            }

            if (i == 4 && !worldIn.getBlockState(pos.east()).isOpaqueCube())
            {
                d1 = (double)pos.getX() + 0.0625D + 1.0D;
            }

            if (i == 5 && !worldIn.getBlockState(pos.west()).isOpaqueCube())
            {
                d1 = (double)pos.getX() - 0.0625D;
            }

            if (d1 < (double)pos.getX() || d1 > (double)(pos.getX() + 1) || d2 < 0.0D || d2 > (double)(pos.getY() + 1) || d3 < (double)pos.getZ() || d3 > (double)(pos.getZ() + 1))
            {
            	for (int i1 = 0; i1 < 10; ++i1)
                {
            		worldIn.spawnParticle(EnumParticleTypes.PORTAL, d1, d2, d3, (rand.nextDouble() - 0.5D) * 2.0D, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2.0D);
                }
            }
        }
    }
	
}
