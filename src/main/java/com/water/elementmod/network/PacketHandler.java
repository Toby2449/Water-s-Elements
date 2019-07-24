package com.water.elementmod.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler 
{
	public static SimpleNetworkWrapper INSTANCE;
	
	private static int ID = 0;
	
	private static int nextID()
	{
		return ID++;
	}
	
	public static void registerMessages(String channelName)
	{
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
		
		//Client Packets
		INSTANCE.registerMessage(PacketParticleData.Handler.class, PacketParticleData.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketCustomParticleData.Handler.class, PacketCustomParticleData.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketAbilityReadyData.Handler.class, PacketAbilityReadyData.class, nextID(), Side.CLIENT);
	}
}
