package com.water.elementmod.network;

import com.water.elementmod.util.Utils;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketParticleData implements IMessage
{

	private boolean messageValid;
	
	private Entity ent;
	private Integer entID;
	private Integer type;
	private World world;
	private double xCoord;
    private double yCoord;
    private double zCoord;
    private double xOffset;
    private double yOffset;
    private double zOffset;
    private int parameter;
	
	public PacketParticleData()
	{
		this.messageValid = false;
	}
	
	public PacketParticleData(Entity ent, World world, Integer type, Double xCoord, Double yCoord, Double zCoord, Double xOffset, Double yOffset, Double zOffset, int parameter)
	{
		this.ent = ent;
		this.world = world;
		this.type = type;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zOffset = zOffset;
		this.parameter = parameter;
		this.messageValid = true;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		try
		{
			this.entID = buf.readInt();
			this.type = buf.readInt();
			this.xCoord = buf.readDouble();
			this.yCoord = buf.readDouble();
			this.zCoord = buf.readDouble();
			this.xOffset = buf.readDouble();
			this.yOffset = buf.readDouble();
			this.zOffset = buf.readDouble();
			this.parameter = buf.readInt();
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
		buf.writeDouble(xCoord);
		buf.writeDouble(yCoord);
		buf.writeDouble(zCoord);
		buf.writeDouble(xOffset);
		buf.writeDouble(yOffset);
		buf.writeDouble(zOffset);
		buf.writeInt(parameter);
	}
	
	public static class Handler implements IMessageHandler<PacketParticleData, IMessage>
	{

		@Override
		public IMessage onMessage(PacketParticleData message, MessageContext ctx) {
			if(message.messageValid && ctx.side != Side.CLIENT)
				return null;
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> processMessage(message, ctx));
			return null;
		}
		
		void processMessage(PacketParticleData message, MessageContext ctx)
		{
			Entity ent = Minecraft.getMinecraft().world.getEntityByID(message.entID);
			if(message.parameter == -1)
			{
				ent.world.spawnParticle(EnumParticleTypes.getParticleFromId(message.type), message.xCoord, message.yCoord, message.zCoord, message.xOffset, message.yOffset, message.zOffset);
			} 
			else
			{
				ent.world.spawnParticle(EnumParticleTypes.getParticleFromId(message.type), message.xCoord, message.yCoord, message.zCoord, message.xOffset, message.yOffset, message.zOffset, message.parameter);
			}
		}
		
	}
	
}
