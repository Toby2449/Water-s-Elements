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

public class PacketCarapaceBeamGrow implements IMessage
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
	
	public PacketCarapaceBeamGrow()
	{
		this.messageValid = false;
	}
	
	public PacketCarapaceBeamGrow(Entity ent, World world, Integer type, Double xCoord, Double yCoord, Double zCoord, Double xOffset, Double yOffset, Double zOffset, Integer radius)
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
	}
	
	public static class Handler implements IMessageHandler<PacketCarapaceBeamGrow, IMessage>
	{

		@Override
		public IMessage onMessage(PacketCarapaceBeamGrow message, MessageContext ctx) {
			if(message.messageValid && ctx.side != Side.CLIENT)
				return null;
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> processMessage(message, ctx));
			return null;
		}
		
		void processMessage(PacketCarapaceBeamGrow message, MessageContext ctx)
		{
			Entity ent = Minecraft.getMinecraft().world.getEntityByID(message.entID);
			
			Random rand = new Random();
			for(double r = 0.6D; r <= message.radius; r += 0.45D)
			{
				for(float i = 0.0F; i < 360.0F; i += 15.0F)
				{
					double deltaX = Math.cos(Math.toRadians(i))*r + rand.nextDouble();
					double deltaZ = -Math.sin(Math.toRadians(i))*r + rand.nextDouble();
					double finalX = message.xCoord - 0.5D + deltaX;
					double finalZ = message.zCoord - 0.5D + deltaZ;
				    
					if(rand.nextInt(99) > 50) ParticleSpawner.spawnParticle(EnumCustomParticleTypes.getParticleFromId(message.type), finalX, message.yCoord + 0.15D, finalZ, 0.0D, 0.0D, 0.0D);
				}
			}
		}
		
	}
	
}
