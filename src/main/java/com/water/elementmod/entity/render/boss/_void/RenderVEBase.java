package com.water.elementmod.entity.render.boss._void;

import com.water.elementmod.entity.boss._void.EntityVEBase;
import com.water.elementmod.entity.models.boss._void.ModelVEBase;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderVEBase extends RenderLiving<EntityVEBase>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/ve_base.png");
    
    public RenderVEBase(RenderManager manager) 
	{
		super(manager, new ModelVEBase(), 0F);
    }
	
    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }
	
	@Override
	protected ResourceLocation getEntityTexture(EntityVEBase entity)
	{
		return TEXTURE;
	}
	
	protected void preRenderCallback(EntityVEBase entitylivingbaseIn, float partialTickTime)
    {
        float f = 1.6F;

        GlStateManager.scale(f, f, f);
    }
}
