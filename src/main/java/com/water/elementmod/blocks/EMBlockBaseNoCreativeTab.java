package com.water.elementmod.blocks;

import com.water.elementmod.EMCore;
import com.water.elementmod.EMCoreBlocks;
import com.water.elementmod.EMCoreItems;
import com.water.elementmod.util.Utils;
import com.water.elementmod.util.interfaces.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class EMBlockBaseNoCreativeTab extends Block implements IHasModel
{
	
	public EMBlockBaseNoCreativeTab(String name, Material material, SoundType soundtype, Float hardness, Float resistance, String type, Integer level, Float lightlevel, Integer opacity)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(null);
		setSoundType(soundtype);
		setHardness(hardness);
		setResistance(resistance); //Blast resistance
		setHarvestLevel(type, level);
		setLightLevel(lightlevel);
		setLightOpacity(opacity);
		
		
		EMCoreBlocks.BLOCKS.add(this);
		EMCoreItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public void registerModels() 
	{
		EMCore.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
		Utils.getLogger().info("Registered render for block." + this.getUnlocalizedName().substring(5));
	}
	
}
