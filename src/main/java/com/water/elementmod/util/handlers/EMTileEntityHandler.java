package com.water.elementmod.util.handlers;

import com.water.elementmod.blocks.extractor.TileEntityExtractor;
import com.water.elementmod.blocks.infuser.TileEntityInfuser;
import com.water.elementmod.blocks.synthesizer.TileEntitySynthesizer;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class EMTileEntityHandler 
{
	public static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntitySynthesizer.class, "synt");
		GameRegistry.registerTileEntity(TileEntityExtractor.class, "extr");
		GameRegistry.registerTileEntity(TileEntityInfuser.class, "inf");
	}

}
