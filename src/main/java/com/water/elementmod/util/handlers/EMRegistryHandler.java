package com.water.elementmod.util.handlers;

import com.water.elementmod.EMCoreBlocks;
import com.water.elementmod.EMCoreEntities;
import com.water.elementmod.EMCoreItems;
import com.water.elementmod.blocks.EMFluidRegistry;
import com.water.elementmod.gen.WorldGenCustomStructures;
import com.water.elementmod.gen.WorldGenOres;
import com.water.elementmod.util.EMConfig;
import com.water.elementmod.util.Utils;
import com.water.elementmod.util.interfaces.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = EMConfig.MOD_ID)
public class EMRegistryHandler
{
	// Items
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(EMCoreItems.ITEMS.toArray(new Item[0]));
	}
	
	// Blocks
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(EMCoreBlocks.BLOCKS.toArray(new Block[0]));
		EMTileEntityHandler.registerTileEntities();
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onModelRegister(ModelRegistryEvent event)
	{
		EMRenderHandler.registerEntityRenders();
		
		for(Item item : EMCoreItems.ITEMS)
		{
			if(item instanceof IHasModel)
			{
				((IHasModel)item).registerModels();
			}
		}
		
		for(Block block : EMCoreBlocks.BLOCKS)
		{
			if(block instanceof IHasModel)
			{
				((IHasModel)block).registerModels();
			}
		}
	}
	
	public static void otherRegistries()
	{
		EMFluidRegistry.registerFluids();
		GameRegistry.registerWorldGenerator(new WorldGenOres(), 3);
		GameRegistry.registerWorldGenerator(new WorldGenCustomStructures(), 0);
		EMCoreEntities.registerEntities();
		EMCoreEntities.registerSpawns();
		EMRenderHandler.registerCustomMeshesAndStates();
		Utils.getLogger().info("World gens intialized");
	}
}
