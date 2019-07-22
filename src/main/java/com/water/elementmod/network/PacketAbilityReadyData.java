package com.water.elementmod.network;

import java.util.Random;

import com.water.elementmod.util.References;
import com.water.elementmod.util.Utils;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketAbilityReadyData implements IMessage
{

	private boolean messageValid;
	
	private Entity ent;
	private Integer entID;
	private World world;
	
	public PacketAbilityReadyData()
	{
		this.messageValid = false;
	}
	
	public PacketAbilityReadyData(Entity ent, World world)
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
	
	public static class Handler implements IMessageHandler<PacketAbilityReadyData, IMessage>
	{

		@Override
		public IMessage onMessage(PacketAbilityReadyData message, MessageContext ctx) {
			if(message.messageValid && ctx.side != Side.CLIENT)
				return null;
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> processMessage(message, ctx));
			return null;
		}
		
		void processMessage(PacketAbilityReadyData message, MessageContext ctx)
		{
			Entity ent = Minecraft.getMinecraft().world.getEntityByID(message.entID);

			ent.world.playSound((EntityPlayer) ent, ent.getPosition(), SoundEvents.ENTITY_ENDEREYE_DEATH, SoundCategory.BLOCKS, 1.0F, 1.0F);;
			for(int countparticles = 0; countparticles <= 5 * 4; ++countparticles)
			{
				Random rand = new Random();
				ent.world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, ent.posX + (rand.nextDouble() - 0.5D) * (double)ent.width, ent.posY + rand.nextDouble() * (double)ent.height - 0.25D, ent.posZ + (rand.nextDouble() - 0.5D) * (double)ent.width, 0.0D, 0.0D, 0.0D);
			}
		}
		
	}
	
}
