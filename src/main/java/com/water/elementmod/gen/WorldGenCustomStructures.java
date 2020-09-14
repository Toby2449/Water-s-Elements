package com.water.elementmod.gen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.water.elementmod.EMCoreBlocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomeHell;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenCustomStructures implements IWorldGenerator
{
	public static final WorldGenNArena N_ARENA = new WorldGenNArena();
	public static final WorldGenFArena F_ARENA = new WorldGenFArena();
	public static final WorldGenWArena W_ARENA = new WorldGenWArena();
	public static final WorldGenKnightArena KNIGHT_ARENA = new WorldGenKnightArena();
	public static final WorldGenVoidArena VOID_ARENA = new WorldGenVoidArena();
	private boolean voidStructureSpawned = false;
	private long voidWorldSeed = 0;

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) 
	{
		switch(world.provider.getDimension())
		{
		case 2:
			// The Void
			
			if(voidWorldSeed != world.getSeed()) voidStructureSpawned = false;
			if(!voidStructureSpawned)
			{
				voidStructureSpawned = true;
				voidWorldSeed = world.getSeed();
				generatorStructureVoid(VOID_ARENA, world, random);
			}
			break;
		case 1: // The End
			break;
		case 0: // Over world
			generatorStructureOverworld(KNIGHT_ARENA, world, random, chunkX, chunkZ, 750, Blocks.GRASS, 0, 2, 0, BiomePlains.class);
			generatorStructureOverworld(N_ARENA, world, random, chunkX, chunkZ, 500, Blocks.GRASS, 0, -4, 0, BiomeForest.class);
			generatorStructureOverworldOcean(W_ARENA, world, random, chunkX, chunkZ, 500, Blocks.GRAVEL, 0, 0, 0, BiomeOcean.class);
			break;
			
		case -1: // Nether
			generatorStructureNether(F_ARENA, world, random, chunkX, chunkZ, 1000, Blocks.LAVA, 0, 5, 0, BiomeHell.class); //  was 2
			break;
			
		}
	}
	
	private void generatorStructureOverworld(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, int skewX, int skewY, int skewZ, Class<?>... classes)
	{
		ArrayList<Class<?>> classesList = new ArrayList<Class<?>>(Arrays.asList(classes));
		
		int x = (chunkX * 16) + random.nextInt(15) + 15;
		int z = (chunkZ * 16) + random.nextInt(15) + 15;
		int y = calculateGenerationHeight(world, x, z, topBlock);
		BlockPos pos = new BlockPos(x + skewX, y + skewY, z + skewZ);
		
		Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();
		
		if((x > 1500 && x < -1500) || (z > 1500 && z < -1500))
		{
			if(y >= 60) // So the structure doesn't generate underground
			{
				if(world.getWorldType() != WorldType.FLAT)
				{
					if(classesList.contains(biome))
					{
						if(random.nextInt(chance) == 0)
						{
							generator.generate(world, random, pos);
						}
					}
				}
			}
		}
	}
	
	private void generatorStructureOverworldOcean(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, int skewX, int skewY, int skewZ, Class<?>... classes)
	{
		ArrayList<Class<?>> classesList = new ArrayList<Class<?>>(Arrays.asList(classes));
		
		int x = (chunkX * 16) + random.nextInt(15) + 15;
		int z = (chunkZ * 16) + random.nextInt(15) + 15;
		int y = calculateGenerationHeight(world, x, z, topBlock);
		BlockPos pos = new BlockPos(x + skewX, y + skewY, z + skewZ);
		
		Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();
		
		if((x > 1500 && x < -1500) || (z > 1500 && z < -1500))
		{
			if(y >= 25 && y <= 41) // So the structure doesn't generate underground
			{
				if(world.getWorldType() != WorldType.FLAT)
				{
					if(classesList.contains(biome))
					{
						if(random.nextInt(chance) == 0)
						{
							generator.generate(world, random, pos);
						}
					}
				}
			}
		}
	}
	
	private void generatorStructureNether(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, int skewX, int skewY, int skewZ, Class<?>... classes)
	{
		ArrayList<Class<?>> classesList = new ArrayList<Class<?>>(Arrays.asList(classes));
		
		int x = (chunkX * 16) + random.nextInt(15);
		int z = (chunkZ * 16) + random.nextInt(15);
		int y = 30;
		BlockPos pos = new BlockPos(x + skewX, y + skewY, z + skewZ);
		
		Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();
		
		Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
		
		if(block == topBlock) // Make the structure only generate on lava
		{
			if(classesList.contains(biome))
			{
				if(random.nextInt(chance) == 0)
				{
					generator.generate(world, random, pos);
				}
			}
		}
	}
	
	private void generatorStructureVoid(WorldGenerator generator, World world, Random random)
	{
		Block block = world.getBlockState(new BlockPos(0, 43, 0)).getBlock();
		if(block != EMCoreBlocks.CORRUPTED_FLESH) // So it doesn't generate a thousand times
		{
			generator.generate(world, random, new BlockPos(-11, 43, -39));
		}
	}
	
	private static int calculateGenerationHeight(World world, int x, int z, Block topBlock)
	{
		int y = world.getHeight();
		boolean foundGround = false;
		
		while(!foundGround && y-- >= 0)
		{
			Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
			foundGround = block == topBlock;
		}
		
		return y;
	}
}
