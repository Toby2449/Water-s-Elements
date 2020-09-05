package com.water.elementmod.entity.render.boss._void;

import com.water.elementmod.entity.boss._void.EntityVoidSpectralLarge;
import com.water.elementmod.entity.models.boss._void.ModelVoidSpectralLarge;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderVoidSpectralLarge extends RenderLiving<EntityVoidSpectralLarge>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/void_spectral_large.png");
	
	public RenderVoidSpectralLarge(RenderManager manager)
	{
		super(manager, new ModelVoidSpectralLarge(), 0.5F);
    }
	
    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }
	
	@Override
	protected ResourceLocation getEntityTexture(EntityVoidSpectralLarge entity)
	{
		return TEXTURE;
	}
	
	@Override
	protected void applyRotations(EntityVoidSpectralLarge entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
	
	protected void preRenderCallback(EntityVoidSpectralLarge entitylivingbaseIn, float partialTickTime)
    {
        float f = 1.0F;

        GlStateManager.scale(f, f, f);
        GlStateManager.rotate(180, 0.0f, 1.0f, 0.0f);
    }
	
}
