package com.water.elementmod;

import java.util.ArrayList;
import java.util.List;

import com.water.elementmod.blocks.EMBlockBase;
import com.water.elementmod.blocks.EMFluidRegistry;
import com.water.elementmod.blocks.extractor.BlockExtractor;
import com.water.elementmod.blocks.fluids.BlockFluid;
import com.water.elementmod.blocks.infuser.BlockInfuser;
import com.water.elementmod.blocks.special.BlockCrackBase;
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
	
	public static final Block SYNTHESIZER = new BlockSynthesizer("synthesizer", Material.ROCK, SoundType.STONE, 11.0f, 24.0f, "pickaxe", 1, 0.0f, 0);
	public static final Block EXTRACTOR = new BlockExtractor("extractor", Material.ROCK, SoundType.STONE, 11.0f, 24.0f, "pickaxe", 1, 0.0f, 0);
	public static final Block INFUSER = new BlockInfuser("infuser", Material.ROCK, SoundType.STONE, 11.0f, 24.0f, "pickaxe", 1, 0.0f, 15);
	
	public static final Block VALVE = new BlockValve("valve_shutoff", Material.ROCK, SoundType.STONE, 99999.0f, 99999.0f, "pickaxe", 1, 0.0f, 0);
	public static final Block VALVE_FAKE = new BlockValveFake("valve_shutoff_fake", Material.ROCK, SoundType.STONE, 20.0f, 20.0f, "pickaxe", 1, 0.0f, 0);
	
	public static final Block VOID_BLOCK = new EMBlockBase("void_block", Material.ROCK, SoundType.STONE, 11.0f, 24.0f, "pickaxe", 1, 0.0f, 0);
	public static final Block VOID_BLOCK_2 = new EMBlockBase("void_block_2", Material.ROCK, SoundType.STONE, 11.0f, 24.0f, "pickaxe", 1, 0.0f, 0);
	
	public static final Block PURE_EVIL_BLOCK = new BlockFluid("pure_evil", EMFluidRegistry.PURE_EVIL, Material.LAVA);
	
	
	public static final Block VOID_CRACK_1 = new BlockCrackBase("void_crack_1", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_2 = new BlockCrackBase("void_crack_2", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_3 = new BlockCrackBase("void_crack_3", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_4 = new BlockCrackBase("void_crack_4", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_5 = new BlockCrackBase("void_crack_5", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_6 = new BlockCrackBase("void_crack_6", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_7 = new BlockCrackBase("void_crack_7", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_8 = new BlockCrackBase("void_crack_8", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_9 = new BlockCrackBase("void_crack_9", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_10 = new BlockCrackBase("void_crack_10", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_11 = new BlockCrackBase("void_crack_11", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_12 = new BlockCrackBase("void_crack_12", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_13 = new BlockCrackBase("void_crack_13", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_14 = new BlockCrackBase("void_crack_14", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_15 = new BlockCrackBase("void_crack_15", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_16 = new BlockCrackBase("void_crack_16", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_17 = new BlockCrackBase("void_crack_17", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_18 = new BlockCrackBase("void_crack_18", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_19 = new BlockCrackBase("void_crack_19", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_20 = new BlockCrackBase("void_crack_20", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_21 = new BlockCrackBase("void_crack_21", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_22 = new BlockCrackBase("void_crack_22", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_23 = new BlockCrackBase("void_crack_23", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_24 = new BlockCrackBase("void_crack_24", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_25 = new BlockCrackBase("void_crack_25", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_26 = new BlockCrackBase("void_crack_26", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_27 = new BlockCrackBase("void_crack_27", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_28 = new BlockCrackBase("void_crack_28", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_29 = new BlockCrackBase("void_crack_29", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_30 = new BlockCrackBase("void_crack_30", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_31 = new BlockCrackBase("void_crack_31", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_32 = new BlockCrackBase("void_crack_32", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_33 = new BlockCrackBase("void_crack_33", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_34 = new BlockCrackBase("void_crack_34", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_35 = new BlockCrackBase("void_crack_35", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block VOID_CRACK_36 = new BlockCrackBase("void_crack_36", Material.ROCK, SoundType.STONE, "pickaxe", 1);
}
