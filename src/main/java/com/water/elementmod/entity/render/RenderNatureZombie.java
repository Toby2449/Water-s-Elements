package com.water.elementmod.entity.render;

import com.water.elementmod.entity.EntityNatureZombie;
import com.water.elementmod.entity.models.ModelWaterZombie;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderNatureZombie extends RenderLiving<EntityNatureZombie>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/naturezombie.png"); 
	
	public RenderNatureZombie(RenderManager manager) 
	{
		super(manager, new ModelWaterZombie(), 0.5F);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityNatureZombie entity)
	{
		return TEXTURE;
	}
	
	@Override
	protected void applyRotations(EntityNatureZombie entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
	
}
