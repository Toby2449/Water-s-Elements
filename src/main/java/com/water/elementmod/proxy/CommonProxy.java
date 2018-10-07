package com.water.elementmod.proxy;

import com.water.elementmod.EMCore;
import com.water.elementmod.items.weapons.FireSword;
import com.water.elementmod.items.weapons.NatureSword;
import com.water.elementmod.items.weapons.WaterSword;
import com.water.elementmod.util.References;
import com.water.elementmod.util.handlers.EMGuiHandler;
import com.water.elementmod.util.handlers.EMRegistryHandler;
import com.water.elementmod.util.handlers.EMSoundHandler;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy 
{
	
	public void registerItemRenderer(Item item, int meta, String id) {}
}
