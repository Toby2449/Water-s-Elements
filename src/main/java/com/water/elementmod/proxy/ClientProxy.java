package com.water.elementmod.proxy;

import com.water.elementmod.util.References;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod.EventBusSubscriber(modid = References.MOD_ID)
public class ClientProxy extends ServerProxy
{
	@Override
	public void preInitalizationEvent(FMLPreInitializationEvent event)
	{
		super.preInitalizationEvent(event);
	}
	
	@Override
	public void initalizationEvent(FMLInitializationEvent event)
	{
		super.initalizationEvent(event);
	}
	
	@Override
	public void postInitalizationEvent(FMLPostInitializationEvent event)
	{
		super.postInitalizationEvent(event);
	}
	
	@Override
	public void registerItemRenderer(Item item, int meta, String id)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
}
