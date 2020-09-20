package com.water.elementmod.network;

import java.util.Random;

import com.water.elementmod.EMCorePotionEffects;
import com.water.elementmod.particle.EnumCustomParticleTypes;
import com.water.elementmod.particle.ParticleSpawner;
import com.water.elementmod.util.Utils;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketCustomPotionEffect implements IMessage
{

	private boolean messageValid;
	
	private EntityLivingBase ent;
	private Integer entID;
	private Integer type;
	private World world;
	private Integer time;
	private Integer level;
	
	public PacketCustomPotionEffect()
	{
		this.messageValid = false;
	}
	
	public PacketCustomPotionEffect(EntityLivingBase ent, World world, Integer type, Integer time, Integer level)
	{
		this.ent = ent;
		this.world = world;
		this.type = type;
		this.time = time;
		this.level = level;
		this.messageValid = true;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		try
		{
			this.entID = buf.readInt();
			this.type = buf.readInt();
			this.time = buf.readInt();
			this.level = buf.readInt();
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
		buf.writeInt(type);
		buf.writeInt(time);
		buf.writeInt(level);
	}
	
	public static class Handler implements IMessageHandler<PacketCustomPotionEffect, IMessage>
	{

		@Override
		public IMessage onMessage(PacketCustomPotionEffect message, MessageContext ctx) {
			if(message.messageValid && ctx.side != Side.CLIENT)
				return null;
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> processMessage(message, ctx));
			return null;
		}
		
		void processMessage(PacketCustomPotionEffect message, MessageContext ctx)
		{
			EntityLivingBase ent = (EntityLivingBase)Minecraft.getMinecraft().world.getEntityByID(message.entID);
			
			switch(message.type)
			{
			case 0:
				ent.addPotionEffect(new PotionEffect(EMCorePotionEffects.POTION_CORRUPTION, message.time, message.level));
				break;
			case 1:
				ent.addPotionEffect(new PotionEffect(EMCorePotionEffects.POTION_GLIMPSE, message.time, message.level));
				break;
			case 2:
				ent.addPotionEffect(new PotionEffect(EMCorePotionEffects.POTION_INSANITY, message.time, message.level));
				break;
			}
		}
		
	}
	
}
