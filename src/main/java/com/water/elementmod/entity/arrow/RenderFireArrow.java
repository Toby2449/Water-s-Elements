package com.water.elementmod.entity.arrow;

import com.water.elementmod.util.EMConfig;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderFireArrow extends RenderArrow<EntityFireArrow>
{

	public RenderFireArrow(RenderManager manager) 
	{
		super(manager);
	}
	
	@Override
	public ResourceLocation getEntityTexture(EntityFireArrow entity)
	{
		return new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/fire_arrow.png");
	}
}
