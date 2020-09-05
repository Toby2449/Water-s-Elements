package com.water.elementmod.entity.render.boss._void;

import com.water.elementmod.entity.boss._void.EntityGrowingAnguishMedium;
import com.water.elementmod.entity.models.boss._void.ModelAnguish;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderGrowingAnguishMedium extends RenderLiving<EntityGrowingAnguishMedium>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/anguish.png");
    
    public RenderGrowingAnguishMedium(RenderManager manager) 
	{
		super(manager, new ModelAnguish(), 0F);
    }
	
    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }
	
	@Override
	protected ResourceLocation getEntityTexture(EntityGrowingAnguishMedium entity)
	{
		return TEXTURE;
	}
	
	protected void preRenderCallback(EntityGrowingAnguishMedium entitylivingbaseIn, float partialTickTime)
    {
        float f = 1F;

        GlStateManager.scale(f, f, f);
    }
}
