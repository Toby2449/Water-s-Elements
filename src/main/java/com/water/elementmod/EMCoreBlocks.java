package com.water.elementmod;

import java.util.ArrayList;
import java.util.List;

import com.water.elementmod.blocks.EMBlockBase;
import com.water.elementmod.blocks.EMBlockUnbreakableIce;
import com.water.elementmod.blocks.EMOreBase;
import com.water.elementmod.blocks.extractor.BlockExtractor;
import com.water.elementmod.blocks.infuser.BlockInfuser;
import com.water.elementmod.blocks.special.BlockVoidOre;
import com.water.elementmod.blocks.synthesizer.BlockSynthesizer;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class EMCoreBlocks 
{
	
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block ORE_VOID_BEDROCK = new BlockVoidOre("ore_void_bedrock", EMCoreItems.VOIDESS, 1, 4, Material.ROCK, SoundType.STONE, 35.0f, 100.0f, "pickaxe", 3, 0.0f, 15, 5, 11);
	
	public static final Block SYNTHESIZER = new BlockSynthesizer("synthesizer", Material.ROCK, SoundType.STONE, 11.0f, 24.0f, "pickaxe", 1, 0.0f, 15);
	public static final Block EXTRACTOR = new BlockExtractor("extractor", Material.ROCK, SoundType.STONE, 11.0f, 24.0f, "pickaxe", 1, 0.0f, 15);
	public static final Block INFUSER = new BlockInfuser("infuser", Material.ROCK, SoundType.STONE, 11.0f, 24.0f, "pickaxe", 1, 0.0f, 0);
	public static final Block ICECUBE_BLOCK = new EMBlockUnbreakableIce("icecube", Material.ICE, SoundType.GLASS, 10000.0f, 10000.0f, "pickaxe", 1, 0.0f, 0);
	
}
