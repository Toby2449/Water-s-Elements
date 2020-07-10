package com.water.elementmod.entity.render.layers;

import com.water.elementmod.entity.boss.overworld.EntityVoidBlob;
import com.water.elementmod.entity.models.boss._void.ModelVoidBlob;
import com.water.elementmod.entity.render.boss.overworld.RenderVoidBlob;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerBlobTrail implements LayerRenderer<EntityVoidBlob>
{
	private final RenderVoidBlob blobRenderer;
	private final ModelBase blobModel = new ModelVoidBlob();
	
	public LayerBlobTrail(RenderVoidBlob blobRendererIn)
	{
		this.blobRenderer = blobRendererIn;
	}
	
	public void doRenderLayer(EntityVoidBlob entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		if (!entitylivingbaseIn.isInvisible())
		{
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableNormalize();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			this.blobModel.setModelAttributes(this.blobRenderer.getMainModel());
			this.blobModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
			GlStateManager.disableBlend();
			GlStateManager.disableNormalize();
		}
	}

	public boolean shouldCombineTextures()
	{
		return true;
	}
}
