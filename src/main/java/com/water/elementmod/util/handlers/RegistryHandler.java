package com.water.elementmod.util.handlers;

import com.water.elementmod.init.EmBlocks;
import com.water.elementmod.init.EmItems;
import com.water.elementmod.util.IHasModel;
import com.water.elementmod.util.Utils;
import com.water.elementmod.world.gen.WorldGenOres;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@EventBusSubscriber
public class RegistryHandler 
{	
	// Items
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(EmItems.ITEMS.toArray(new Item[0]));
	}
	
	// Blocks
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(EmBlocks.BLOCKS.toArray(new Block[0]));
		TileEntityHandler.registerTileEntities();
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		for(Item item : EmItems.ITEMS)
		{
			if(item instanceof IHasModel)
			{
				((IHasModel)item).registerModels();
			}
		}
		
		for(Block block : EmBlocks.BLOCKS)
		{
			if(block instanceof IHasModel)
			{
				((IHasModel)block).registerModels();
			}
		}
	}
	
	public static void otherRegistries()
	{
		GameRegistry.registerWorldGenerator(new WorldGenOres(), 3);
		Utils.getLogger().info("World gens intialized");
	}
}
