package com.water.elementmod;

import com.water.elementmod.items.weapons.FireSword;
import com.water.elementmod.items.weapons.NatureSword;
import com.water.elementmod.items.weapons.WaterSword;
import com.water.elementmod.proxy.CommonProxy;
import com.water.elementmod.tabs.EMTab_Main;
import com.water.elementmod.tabs.EMTab_Weapons;
import com.water.elementmod.util.References;
import com.water.elementmod.util.Utils;
import com.water.elementmod.util.handlers.EMGuiHandler;
import com.water.elementmod.util.handlers.EMRegistryHandler;
import com.water.elementmod.util.handlers.EMSoundHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = References.MOD_ID, name = References.NAME, version = References.VERSION)
public class EMCore 
{
	
	public static final CreativeTabs TAB_MAIN = new EMTab_Main();
	public static final CreativeTabs TAB_WEAPONS = new EMTab_Weapons();
	
	@Mod.Instance(References.MOD_ID)
	public static EMCore instance;
	
	@SidedProxy(clientSide = References.CLIENT_PROXY_CLASS, serverSide = References.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event)
	{
		EMRegistryHandler.otherRegistries();
		Utils.getLogger().info("Pre Initialize");
	}
	
	@EventHandler
	public static void Init(FMLInitializationEvent event)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(EMCore.instance, new EMGuiHandler());
		MinecraftForge.EVENT_BUS.register(FireSword.class);
		MinecraftForge.EVENT_BUS.register(WaterSword.class);
		MinecraftForge.EVENT_BUS.register(NatureSword.class);
		EMSoundHandler.init();
		Utils.getLogger().info("Initialize");
	}
	
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event)
	{
		Utils.getLogger().info("Post Initialize");
	}
	
}
