package com.water.elementmod;

import java.util.ArrayList;
import java.util.List;

import com.water.elementmod.blocks.EMBlockBase;
import com.water.elementmod.blocks.extractor.BlockExtractor;
import com.water.elementmod.blocks.infuser.BlockInfuser;
import com.water.elementmod.blocks.special.BlockVoidOre;
import com.water.elementmod.blocks.synthesizer.BlockSynthesizer;
import com.water.elementmod.blocks.valve.BlockValve;
import com.water.elementmod.blocks.valve.BlockValveFake;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class EMCoreBlocks 
{
	
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block ORE_VOID_BEDROCK = new BlockVoidOre("ore_void_bedrock", EMCoreItems.VOIDESS, 1, 4, Material.ROCK, SoundType.STONE, 70.0f, 100.0f, "pickaxe", 3, 0.0f, 15, 5, 11);
	
	public static final Block SYNTHESIZER = new BlockSynthesizer("synthesizer", Material.ROCK, SoundType.STONE, 11.0f, 24.0f, "pickaxe", 1, 0.0f, 15);
	public static final Block EXTRACTOR = new BlockExtractor("extractor", Material.ROCK, SoundType.STONE, 11.0f, 24.0f, "pickaxe", 1, 0.0f, 15);
	public static final Block INFUSER = new BlockInfuser("infuser", Material.ROCK, SoundType.STONE, 11.0f, 24.0f, "pickaxe", 1, 0.0f, 0);
	
	public static final Block VALVE = new BlockValve("valve_shutoff", Material.ROCK, SoundType.STONE, 99999.0f, 99999.0f, "pickaxe", 1, 0.0f, 0);
	public static final Block VALVE_FAKE = new BlockValveFake("valve_shutoff_fake", Material.ROCK, SoundType.STONE, 20.0f, 20.0f, "pickaxe", 1, 0.0f, 0);
	
	public static final Block VOID_BLOCK = new EMBlockBase("void_block", Material.ROCK, SoundType.STONE, 11.0f, 24.0f, "pickaxe", 1, 0.0f, 0);
	public static final Block VOID_BLOCK_2 = new EMBlockBase("void_block_2", Material.ROCK, SoundType.STONE, 11.0f, 24.0f, "pickaxe", 1, 0.0f, 0);
	
}
