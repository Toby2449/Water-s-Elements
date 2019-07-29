package com.water.elementmod.entity.render;

import com.water.elementmod.entity.EntityFireZombie;
import com.water.elementmod.entity.models.ModelFireZombie;
import com.water.elementmod.util.References;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderFireZombie extends RenderLiving<EntityFireZombie>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(References.MOD_ID + ":textures/entity/firezombie.png"); 
	
	public RenderFireZombie(RenderManager manager) 
	{
		super(manager, new ModelFireZombie(), 0.5F);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityFireZombie entity)
	{
		return TEXTURE;
	}
	
	@Override
	protected void applyRotations(EntityFireZombie entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
	
}
