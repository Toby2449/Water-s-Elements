package com.water.elementmod.blocks;

import com.water.elementmod.Main;
import com.water.elementmod.init.EmBlocks;
import com.water.elementmod.init.EmItems;
import com.water.elementmod.util.IHasModel;
import com.water.elementmod.util.Utils;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block implements IHasModel
{
	
	public BlockBase(String name, Material material, SoundType soundtype, Float hardness, Float resistance, String type, Integer level, Float lightlevel, Integer opacity)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.tab_main);
		setSoundType(soundtype);
		setHardness(hardness);
		setResistance(resistance); //Blast resistance
		setHarvestLevel(type, level);
		setLightLevel(lightlevel);
		setLightOpacity(opacity);
		
		
		
		EmBlocks.BLOCKS.add(this);
		EmItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
		Utils.getLogger().info("Registered render for block." + this.getUnlocalizedName().substring(5));
	}
	
}
