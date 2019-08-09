package com.water.elementmod.entity.render;

import com.water.elementmod.entity.EntityFireZombie;
import com.water.elementmod.entity.EntityWaterZombie;
import com.water.elementmod.entity.models.ModelWaterZombie;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderWaterZombie extends RenderLiving<EntityWaterZombie>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/waterzombie.png"); 
	
	public RenderWaterZombie(RenderManager manager) 
	{
		super(manager, new ModelWaterZombie(), 0.5F);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityWaterZombie entity)
	{
		return TEXTURE;
	}
	
	@Override
	protected void applyRotations(EntityWaterZombie entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
	
}
