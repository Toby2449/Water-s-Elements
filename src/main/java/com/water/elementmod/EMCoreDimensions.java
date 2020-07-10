package com.water.elementmod;

import com.water.elementmod.world.dimension.WorldProviderVoid;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class EMCoreDimensions 
{
	public static final DimensionType VOID = DimensionType.register("Void", "_void", 2, WorldProviderVoid.class, false); // MAYBE SET TO TRUE?
	
	public static void register()
	{
		DimensionManager.registerDimension(2, VOID);
	}
}
