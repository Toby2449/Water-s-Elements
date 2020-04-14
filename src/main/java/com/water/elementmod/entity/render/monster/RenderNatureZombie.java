package com.water.elementmod.entity.render.monster;

import com.water.elementmod.entity.models.monster.ModelWaterZombie;
import com.water.elementmod.entity.monster.EntityNatureZombie;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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
