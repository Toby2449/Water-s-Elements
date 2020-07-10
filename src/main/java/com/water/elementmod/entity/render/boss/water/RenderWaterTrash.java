package com.water.elementmod.entity.render.boss.water;

import com.water.elementmod.entity.boss.water.EntityWaterTrash;
import com.water.elementmod.entity.models.boss.water.ModelWaterTrash;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWaterTrash extends RenderLiving<EntityWaterTrash>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/water_trash.png"); 
	
	public RenderWaterTrash(RenderManager manager) 
	{
		super(manager, new ModelWaterTrash(), 0.5F);
    }
	
    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }
	
	@Override
	protected ResourceLocation getEntityTexture(EntityWaterTrash entity)
	{
		return TEXTURE;
	}
	
	@Override
	protected void applyRotations(EntityWaterTrash entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
	
	protected void preRenderCallback(EntityWaterTrash entitylivingbaseIn, float partialTickTime)
    {
        float f = 0.90F;

        GlStateManager.scale(f, f, f);
        GlStateManager.translate(0, 0.1D, 0);
    }
	
}
