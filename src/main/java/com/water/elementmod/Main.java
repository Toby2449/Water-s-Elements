package com.water.elementmod;

import com.water.elementmod.creativetabs.Tab_Main;
import com.water.elementmod.creativetabs.Tab_Weapons;
import com.water.elementmod.proxy.CommonProxy;
import com.water.elementmod.util.References;
import com.water.elementmod.util.Utils;
import com.water.elementmod.util.handlers.EmSoundHandler;
import com.water.elementmod.util.handlers.GuiHandler;
import com.water.elementmod.util.handlers.RegistryHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = References.MOD_ID, name = References.NAME, version = References.VERSION)
public class Main 
{
	
	//Creative Tab
	public static final CreativeTabs tab_main = new Tab_Main();
	public static final CreativeTabs tab_weapons = new Tab_Weapons();
	
	@Mod.Instance(References.MOD_ID)
	public static Main instance;
	
	@SidedProxy(clientSide = References.CLIENT_PROXY_CLASS, serverSide = References.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event)
	{
		RegistryHandler.otherRegistries();
		Utils.getLogger().info("Pre Initialize");
	}
	
	@EventHandler
	public static void Init(FMLInitializationEvent event)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());
		EmSoundHandler.init();
		Utils.getLogger().info("Initialize");
	}
	
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event)
	{
		Utils.getLogger().info("Post Initialize");
	}
	
}
