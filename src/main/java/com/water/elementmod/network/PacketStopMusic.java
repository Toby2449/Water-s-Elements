package com.water.elementmod.network;

import java.lang.reflect.Field;

import com.water.elementmod.util.Utils;
import com.water.elementmod.util.handlers.EMSoundHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketStopMusic implements IMessage
{

	private boolean messageValid;
	
	private Entity ent;
	private Integer entID;
	private World world;
	
	public PacketStopMusic()
	{
		this.messageValid = false;
	}
	
	public PacketStopMusic(Entity ent, World world)
	{
		this.ent = ent;
		this.world = world;
		this.messageValid = true;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		try
		{
			this.entID = buf.readInt();
		} catch (IndexOutOfBoundsException ioe) {
			Utils.getLogger().catching(ioe);
			return;
		}
		this.messageValid = true;
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		if(!this.messageValid)
			return;
		buf.writeInt(ent.getEntityId());
	}
	
	public static class Handler implements IMessageHandler<PacketStopMusic, IMessage>
	{

		@Override
		public IMessage onMessage(PacketStopMusic message, MessageContext ctx) {
			if(message.messageValid && ctx.side != Side.CLIENT)
				return null;
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> processMessage(message, ctx));
			return null;
		}
		
		void processMessage(PacketStopMusic message, MessageContext ctx)
		{
			Minecraft mc = Minecraft.getMinecraft();
			MusicTicker musicTicker = mc.getMusicTicker();
			mc.getSoundHandler().stopSounds();
		}
		
	}
	
}
