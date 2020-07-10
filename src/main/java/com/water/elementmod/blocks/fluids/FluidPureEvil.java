package com.water.elementmod.blocks.fluids;

import java.awt.Color;

import com.water.elementmod.util.EMConfig;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidPureEvil extends Fluid
{
	public FluidPureEvil(String name, ResourceLocation still, ResourceLocation flowing)
	{
		super(name, still, flowing);
		this.setUnlocalizedName(name);
		this.setColor(new Color(0.306F, 0.024F, 0.533F, 1.0F));
	}
}
