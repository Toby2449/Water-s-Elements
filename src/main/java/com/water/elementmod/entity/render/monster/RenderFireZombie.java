package com.water.elementmod.entity.render.monster;

import com.water.elementmod.entity.models.monster.ModelFireZombie;
import com.water.elementmod.entity.monster.EntityFireZombie;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFireZombie extends RenderLiving<EntityFireZombie>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/firezombie.png"); 
	
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
