package com.water.elementmod.world.gen;

import java.util.Random;

import com.water.elementmod.init.EmBlocks;
import com.water.elementmod.world.gen.predicates.VoidOrePredicate;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenOres implements IWorldGenerator
{
	
	private WorldGenerator void_bedrock;
	
	public WorldGenOres()
	{
		void_bedrock = new WorldGenMinable(EmBlocks.ORE_VOID_BEDROCK.getDefaultState(), 8, new VoidOrePredicate());
	}
	
	private void runGenerator(WorldGenerator generator, World world, Random rand, int x, int z, int minY, int maxY, int chance)
	{
		if (minY < 0 || maxY > 256 || minY > maxY) throw new IllegalArgumentException("World generation 'Y' axis fail");
		
		int heightDiff = maxY - minY + 1;
		for (int i = 0; i < chance; i++)
		{
			int axisX = x * 16 + rand.nextInt(16);
			int axisY = minY + rand.nextInt(heightDiff);
			int axisZ = z * 16 + rand.nextInt(16);
			generator.generate(world, rand, new BlockPos(axisX, axisY, axisZ));
		}
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		switch(world.provider.getDimension())
		{
		case -1: //Nether
			break;
			
		case 0: //Overworld
			this.runGenerator(void_bedrock, world, random, chunkX, chunkZ, 0, 6, 20);
			break;
			
		case 1: //The End
			break;
		}
	}

}
