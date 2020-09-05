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

public class PacketPlayMusic implements IMessage
{

	private boolean messageValid;
	
	private Entity ent;
	private Integer entID;
	private World world;	
	private int sound;
	private static Field timeUntilNextMusic = null;
	
	public PacketPlayMusic()
	{
		this.messageValid = false;
	}
	
	public PacketPlayMusic(Entity ent, World world, Integer sound)
	{
		this.ent = ent;
		this.world = world;
		this.sound = sound;
		this.messageValid = true;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		try
		{
			this.entID = buf.readInt();
			this.sound = buf.readInt();
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
		buf.writeInt(sound);
	}
	
	public static class Handler implements IMessageHandler<PacketPlayMusic, IMessage>
	{

		@Override
		public IMessage onMessage(PacketPlayMusic message, MessageContext ctx) {
			if(message.messageValid && ctx.side != Side.CLIENT)
				return null;
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> processMessage(message, ctx));
			return null;
		}
		
		void processMessage(PacketPlayMusic message, MessageContext ctx)
		{
			Minecraft mc = Minecraft.getMinecraft();
			MusicTicker musicTicker = mc.getMusicTicker();
			if(timeUntilNextMusic == null) 
			{
	            timeUntilNextMusic = ObfuscationReflectionHelper.findField(MusicTicker.class, "field_147676_d");
	        }
			
			mc.getSoundHandler().stopSounds();
			
	        try 
	        {
	            timeUntilNextMusic.setInt(musicTicker, 10000);
	        } catch (IllegalAccessException e) 
	        {
	            e.printStackTrace();
	        }
			
			switch(message.sound)
			{
			case 0:
				mc.getSoundHandler().playSound(PositionedSoundRecord.getMusicRecord(EMSoundHandler.CARAPACE_MUSIC));
				break;
			case 1:
				mc.getSoundHandler().playSound(PositionedSoundRecord.getMusicRecord(EMSoundHandler.VOID_ENTITY_MUSIC));
				break;
			}
		}
		
	}
	
}
