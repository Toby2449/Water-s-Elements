package com.water.elementmod.network;

import java.util.Random;

import com.water.elementmod.particle.EnumCustomParticleTypes;
import com.water.elementmod.particle.ParticleSpawner;
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

public class PacketVEGlimpseAnimation implements IMessage
{

	private boolean messageValid;
	
	private Entity ent;
	private Integer entID;
	private World world;
	private int type;
	private double xCoord;
    private double yCoord;
    private double zCoord;
    private double xOffset;
    private double yOffset;
    private double zOffset;
    private int radius;
    private int height;
	
	public PacketVEGlimpseAnimation()
	{
		this.messageValid = false;
	}
	
	public PacketVEGlimpseAnimation(Entity ent, World world, Integer type, Double xCoord, Double yCoord, Double zCoord, Double xOffset, Double yOffset, Double zOffset, Integer radius, Integer height)
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
		this.radius = radius;
		this.height = height;
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
			this.radius = buf.readInt();
			this.height = buf.readInt();
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
		buf.writeInt(radius);
		buf.writeInt(height);
	}
	
	public static class Handler implements IMessageHandler<PacketVEGlimpseAnimation, IMessage>
	{

		@Override
		public IMessage onMessage(PacketVEGlimpseAnimation message, MessageContext ctx) {
			if(message.messageValid && ctx.side != Side.CLIENT)
				return null;
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> processMessage(message, ctx));
			return null;
		}
		
		void processMessage(PacketVEGlimpseAnimation message, MessageContext ctx)
		{
			Entity ent = Minecraft.getMinecraft().world.getEntityByID(message.entID);
			Random rand = new Random();
			for(float i = 0.0F; i < 360.0F; i += 2.0F)
			{
				double deltaX = Math.cos(Math.toRadians(i))*message.radius;
				double deltaZ = -Math.sin(Math.toRadians(i))*message.radius;
				double finalX = message.xCoord + deltaX;
				double finalZ = message.zCoord + deltaZ;
			    
				for(double p = message.radius; p >= 0.0D; p -= 0.5D)
				{
					ParticleSpawner.spawnParticle(EnumCustomParticleTypes.getParticleFromId(message.type), finalX + rand.nextDouble() + rand.nextDouble(), (message.yCoord + rand.nextInt(message.height) + rand.nextDouble()) + 0.15D, finalZ + rand.nextDouble() + rand.nextDouble(), 0.0D, 0.0D, 0.0D);
				}
			}
		}
		
	}
	
}
