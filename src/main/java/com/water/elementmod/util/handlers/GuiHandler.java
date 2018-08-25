package com.water.elementmod.util.handlers;

import com.water.elementmod.blocks.synthesizer.GuiSynthesizer;
import com.water.elementmod.blocks.synthesizer.TileEntitySynthesizer;
import com.water.elementmod.blocks.synthesizer.container.ContainerSynthesizer;
import com.water.elementmod.util.References;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		if(ID == References.SYNTHESIZER) return new ContainerSynthesizer(player.inventory, (TileEntitySynthesizer)world.getTileEntity(new BlockPos(x, y, z)));
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == References.SYNTHESIZER) return new GuiSynthesizer(player.inventory, (TileEntitySynthesizer)world.getTileEntity(new BlockPos(x, y, z)));
		return null;
	}

}
