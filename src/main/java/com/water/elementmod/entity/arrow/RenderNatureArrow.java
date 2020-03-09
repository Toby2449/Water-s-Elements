package com.water.elementmod.entity.arrow;

import com.water.elementmod.util.EMConfig;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderNatureArrow extends RenderArrow<EntityNatureArrow>
{

	public RenderNatureArrow(RenderManager manager) 
	{
		super(manager);
	}
	
	@Override
	public ResourceLocation getEntityTexture(EntityNatureArrow entity)
	{
		return new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/nature_arrow.png");
	}
}
