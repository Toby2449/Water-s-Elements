package com.water.elementmod.init;

import java.util.ArrayList;
import java.util.List;

import com.water.elementmod.blocks.BlockBase;
import com.water.elementmod.blocks.extractor.BlockExtractor;
import com.water.elementmod.blocks.synthesizer.BlockSynthesizer;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class EmBlocks 
{
	
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block HARDENEDIRON = new BlockBase("hardened_iron", Material.IRON, SoundType.METAL, 11.0f, 24.0f, "pickaxe", 2, 0.0f, 15);
	
	public static final Block SYNTHESIZER = new BlockSynthesizer("synthesizer", Material.ROCK, SoundType.STONE, 11.0f, 24.0f, "pickaxe", 1, 0.0f, 15);
	public static final Block EXTRACTOR = new BlockExtractor("extractor", Material.ROCK, SoundType.STONE, 11.0f, 24.0f, "pickaxe", 1, 0.0f, 15);
	
}
