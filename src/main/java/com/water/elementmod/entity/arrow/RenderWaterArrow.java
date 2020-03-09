package com.water.elementmod.entity.arrow;

import com.water.elementmod.util.EMConfig;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderWaterArrow extends RenderArrow<EntityWaterArrow>
{

	public RenderWaterArrow(RenderManager manager) 
	{
		super(manager);
	}
	
	@Override
	public ResourceLocation getEntityTexture(EntityWaterArrow entity)
	{
		return new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/water_arrow.png");
	}
}
