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
		INSTANCE.registerMessage(PacketAbilityReadyFireData.Handler.class, PacketAbilityReadyFireData.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketAbilityReadyWaterData.Handler.class, PacketAbilityReadyWaterData.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketAbilityReadyNatureData.Handler.class, PacketAbilityReadyNatureData.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketCarapacePortalParticles.Handler.class, PacketCarapacePortalParticles.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketCarapaceParticleCircle.Handler.class, PacketCarapaceParticleCircle.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketCarapaceBlueBuffExplosion.Handler.class, PacketCarapaceBlueBuffExplosion.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketCarapacePurpleBuffArea1.Handler.class, PacketCarapacePurpleBuffArea1.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketCarapacePurpleBuffArea2.Handler.class, PacketCarapacePurpleBuffArea2.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketCarapacePurpleBuffArea3.Handler.class, PacketCarapacePurpleBuffArea3.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketCarapacePurpleBuffExplode1.Handler.class, PacketCarapacePurpleBuffExplode1.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketCarapacePurpleBuffExplode2.Handler.class, PacketCarapacePurpleBuffExplode2.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketCarapacePurpleBuffExplode3.Handler.class, PacketCarapacePurpleBuffExplode3.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketCarapaceSightExplode.Handler.class, PacketCarapaceSightExplode.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketCarapaceSightAttack.Handler.class, PacketCarapaceSightAttack.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketCarapaceParticleRing.Handler.class, PacketCarapaceParticleRing.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketCarapaceBeamGrow.Handler.class, PacketCarapaceBeamGrow.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketCarapaceBeam.Handler.class, PacketCarapaceBeam.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketCarapaceBeam.Handler.class, PacketCarapaceBeam.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketCarapaceRingExplosion.Handler.class, PacketCarapaceRingExplosion.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketCarapaceRingEffects.Handler.class, PacketCarapaceRingEffects.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketVEBaseParticles.Handler.class, PacketVEBaseParticles.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketPlayMusic.Handler.class, PacketPlayMusic.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketStopMusic.Handler.class, PacketStopMusic.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketVEGlimpseAnimation.Handler.class, PacketVEGlimpseAnimation.class, nextID(), Side.CLIENT);
	}
}
