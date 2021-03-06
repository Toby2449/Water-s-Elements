package com.water.elementmod.util.handlers;

import com.water.elementmod.blocks.extractor.GuiExtractor;
import com.water.elementmod.blocks.extractor.TileEntityExtractor;
import com.water.elementmod.blocks.extractor.container.ContainerExtractor;
import com.water.elementmod.blocks.infuser.GuiInfuser;
import com.water.elementmod.blocks.infuser.TileEntityInfuser;
import com.water.elementmod.blocks.infuser.container.ContainerInfuser;
import com.water.elementmod.blocks.synthesizer.GuiSynthesizer;
import com.water.elementmod.blocks.synthesizer.TileEntitySynthesizer;
import com.water.elementmod.blocks.synthesizer.container.ContainerSynthesizer;
import com.water.elementmod.util.EMConfig;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EMGuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		if(ID == EMConfig.SYNTHESIZER) return new ContainerSynthesizer(player.inventory, (TileEntitySynthesizer)world.getTileEntity(new BlockPos(x, y, z)));
		if(ID == EMConfig.EXTRACTOR) return new ContainerExtractor(player.inventory, (TileEntityExtractor)world.getTileEntity(new BlockPos(x, y, z)));
		if(ID == EMConfig.INFUSER) return new ContainerInfuser(player.inventory, (TileEntityInfuser)world.getTileEntity(new BlockPos(x, y, z)));
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == EMConfig.SYNTHESIZER) return new GuiSynthesizer(player.inventory, (TileEntitySynthesizer)world.getTileEntity(new BlockPos(x, y, z)));
		if(ID == EMConfig.EXTRACTOR) return new GuiExtractor(player.inventory, (TileEntityExtractor)world.getTileEntity(new BlockPos(x, y, z)));
		if(ID == EMConfig.INFUSER) return new GuiInfuser(player.inventory, (TileEntityInfuser)world.getTileEntity(new BlockPos(x, y, z)));
		return null;
	}

}
