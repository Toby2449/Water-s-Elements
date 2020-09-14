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

public class PacketVESightAttack implements IMessage
{

	private boolean messageValid;
	
	private Entity ent;
	private Integer entID;
	private World world;
	private double xCoord;
    private double yCoord;
    private double zCoord;
    private double xOffset;
    private double yOffset;
    private double zOffset;
    private int facing;
	
	public PacketVESightAttack()
	{
		this.messageValid = false;
	}
	
	public PacketVESightAttack(Entity ent, World world, Double xCoord, Double yCoord, Double zCoord, Double xOffset, Double yOffset, Double zOffset, Integer facing)
	{
		this.ent = ent;
		this.world = world;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zOffset = zOffset;
		this.facing = facing;
		this.messageValid = true;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		try
		{
			this.entID = buf.readInt();
			this.xCoord = buf.readDouble();
			this.yCoord = buf.readDouble();
			this.zCoord = buf.readDouble();
			this.xOffset = buf.readDouble();
			this.yOffset = buf.readDouble();
			this.zOffset = buf.readDouble();
			this.facing = buf.readInt();
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
		buf.writeDouble(xCoord);
		buf.writeDouble(yCoord);
		buf.writeDouble(zCoord);
		buf.writeDouble(xOffset);
		buf.writeDouble(yOffset);
		buf.writeDouble(zOffset);
		buf.writeInt(facing);
	}
	
	public static class Handler implements IMessageHandler<PacketVESightAttack, IMessage>
	{

		@Override
		public IMessage onMessage(PacketVESightAttack message, MessageContext ctx) {
			if(message.messageValid && ctx.side != Side.CLIENT)
				return null;
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> processMessage(message, ctx));
			return null;
		}
		
		void processMessage(PacketVESightAttack message, MessageContext ctx)
		{
			Entity ent = Minecraft.getMinecraft().world.getEntityByID(message.entID);
			
			Random rand = new Random();
			switch(message.facing)
			{
			case 0:
				for(int m = 0; m < 425; m++)
				{
					ParticleSpawner.spawnParticle(EnumCustomParticleTypes.DARK_PURPLE_SMOKE, message.xCoord + rand.nextInt(30) + rand.nextDouble(), message.yCoord, message.zCoord + rand.nextInt(30) + rand.nextDouble(), 0.0D, 0.0D, 0.0D);
					ParticleSpawner.spawnParticle(EnumCustomParticleTypes.DARK_PURPLE_SMOKE_BLOCK, message.xCoord + rand.nextInt(30) + rand.nextDouble(), message.yCoord, message.zCoord + rand.nextInt(30) + rand.nextDouble(), 0.0D, 0.0D, 0.0D);
					
				}
				break;
			case 1:
				for(int m = 0; m < 425; m++)
				{
					ParticleSpawner.spawnParticle(EnumCustomParticleTypes.DARK_PURPLE_SMOKE, message.xCoord - rand.nextInt(30) + rand.nextDouble(), message.yCoord, message.zCoord + rand.nextInt(30) + rand.nextDouble(), 0.0D, 0.0D, 0.0D);
					ParticleSpawner.spawnParticle(EnumCustomParticleTypes.DARK_PURPLE_SMOKE_BLOCK, message.xCoord - rand.nextInt(30) + rand.nextDouble(), message.yCoord, message.zCoord + rand.nextInt(30) + rand.nextDouble(), 0.0D, 0.0D, 0.0D);
				}
				break;
			case 2:
				for(int m = 0; m < 425; m++)
				{
					ParticleSpawner.spawnParticle(EnumCustomParticleTypes.DARK_PURPLE_SMOKE, message.xCoord - rand.nextInt(30) + rand.nextDouble(), message.yCoord, message.zCoord - rand.nextInt(30) + rand.nextDouble(), 0.0D, 0.0D, 0.0D);
					ParticleSpawner.spawnParticle(EnumCustomParticleTypes.DARK_PURPLE_SMOKE_BLOCK, message.xCoord - rand.nextInt(30) + rand.nextDouble(), message.yCoord, message.zCoord - rand.nextInt(30) + rand.nextDouble(), 0.0D, 0.0D, 0.0D);
				}
				break;
			case 3:
				for(int m = 0; m < 425; m++)
				{
					ParticleSpawner.spawnParticle(EnumCustomParticleTypes.DARK_PURPLE_SMOKE, message.xCoord + rand.nextInt(30) + rand.nextDouble(), message.yCoord, message.zCoord - rand.nextInt(30) + rand.nextDouble(), 0.0D, 0.0D, 0.0D);
					ParticleSpawner.spawnParticle(EnumCustomParticleTypes.DARK_PURPLE_SMOKE_BLOCK, message.xCoord + rand.nextInt(30) + rand.nextDouble(), message.yCoord, message.zCoord - rand.nextInt(30) + rand.nextDouble(), 0.0D, 0.0D, 0.0D);
				}
				break;
			}
		}
		
	}
	
}
