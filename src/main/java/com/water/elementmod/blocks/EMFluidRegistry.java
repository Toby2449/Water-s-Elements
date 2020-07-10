package com.water.elementmod.blocks;

import com.water.elementmod.blocks.fluids.FluidPureEvil;
import com.water.elementmod.util.EMConfig;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class EMFluidRegistry 
{
	public static final Fluid PURE_EVIL = new FluidPureEvil("pure_evil", new ResourceLocation(EMConfig.MOD_ID + ":blocks/pure_evil_still"), new ResourceLocation(EMConfig.MOD_ID + ":blocks/pure_evil_flowing"));
	
	public static void registerFluids()
	{
		registerFluid(PURE_EVIL);
	}
	
	public static void registerFluid(Fluid fluid)
	{
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);
	}
}
