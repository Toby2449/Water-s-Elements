package com.water.elementmod.world.gen;

import java.util.Random;

import com.water.elementmod.init.EmBlocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenOres implements IWorldGenerator
{
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		if(world.provider.getDimension() == 0)
		{
			generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		}
	}
	
	private void generateOverworld(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		runGenerator(EmBlocks.ORE_VOID_BEDROCK.getDefaultState(), world, rand, chunkX, chunkZ, 0, 1, 16, 1);
	}
	
	private void runGenerator(IBlockState ore, World world, Random rand, int x, int z, int minY, int maxY, int size, int chance)
	{
		int deltaY = maxY - minY;
		
		for (int i = 0; i < chance; i++)
		{
			BlockPos pos = new BlockPos(x * 16 + rand.nextInt(16), minY + rand.nextInt(deltaY), z * 16 + rand.nextInt(16));
			
			WorldGenMinable gen = new WorldGenMinable(ore, size, BlockMatcher.forBlock(Blocks.BEDROCK));
			gen.generate(world, rand, pos);
		}
	}

}
