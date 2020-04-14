package com.water.elementmod.entity.render.projectile;

import com.water.elementmod.entity.projectile.EntityFireArrow;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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
