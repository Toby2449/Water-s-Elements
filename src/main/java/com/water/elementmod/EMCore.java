package com.water.elementmod;

import com.water.elementmod.events.EventDrownDebuff;
import com.water.elementmod.events.EventFireSwordAbility;
import com.water.elementmod.events.EventNatureSwordAbility;
import com.water.elementmod.events.EventSuperPoison;
import com.water.elementmod.events.EventWaterSwordAbility;
import com.water.elementmod.items.weapons.FireSword;
import com.water.elementmod.items.weapons.NatureSword;
import com.water.elementmod.items.weapons.WaterSword;
import com.water.elementmod.network.PacketHandler;
import com.water.elementmod.proxy.ClientProxy;
import com.water.elementmod.proxy.CommonProxy;
import com.water.elementmod.tabs.EMTab_Main;
import com.water.elementmod.tabs.EMTab_Weapons;
import com.water.elementmod.util.EMConfig;
import com.water.elementmod.util.Utils;
import com.water.elementmod.util.VersionChecker;
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

@Mod(modid = EMConfig.MOD_ID, name = EMConfig.NAME, version = EMConfig.VERSION)
public class EMCore 
{
	public static final CreativeTabs TAB_MAIN = new EMTab_Main();
	public static final CreativeTabs TAB_WEAPONS = new EMTab_Weapons();
	
	@Mod.Instance(EMConfig.MOD_ID)
	public static EMCore instance;
	
	@SidedProxy(clientSide = EMConfig.CLIENT_PROXY_CLASS, serverSide = EMConfig.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event)
	{
		EMRegistryHandler.otherRegistries();
		PacketHandler.registerMessages(EMConfig.MOD_ID);
		Utils.getLogger().info("Pre Initialized");
	}
	
	@EventHandler
	public static void Init(FMLInitializationEvent event)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(EMCore.instance, new EMGuiHandler());
		MinecraftForge.EVENT_BUS.register(new EventWaterSwordAbility());
		MinecraftForge.EVENT_BUS.register(new EventNatureSwordAbility());
		MinecraftForge.EVENT_BUS.register(new EventFireSwordAbility());
		MinecraftForge.EVENT_BUS.register(new EventSuperPoison());
		MinecraftForge.EVENT_BUS.register(new EventDrownDebuff());
		EMSoundHandler.init();
		Utils.getLogger().info("Initialized");
	}
	
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event)
	{
		Utils.getLogger().info("Post Initialized");
	}
	
}
