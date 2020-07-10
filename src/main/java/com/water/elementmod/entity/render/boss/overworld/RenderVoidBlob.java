package com.water.elementmod.entity.render.boss.overworld;

import org.lwjgl.opengl.GL11;

import com.water.elementmod.entity.boss.overworld.EntityVoidBlob;
import com.water.elementmod.entity.models.boss._void.ModelVoidBlob;
import com.water.elementmod.entity.render.layers.LayerBlobTrail;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderVoidBlob extends RenderLiving<EntityVoidBlob>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/void_blob.png"); 
	
	public RenderVoidBlob(RenderManager manager) 
	{
		super(manager, new ModelVoidBlob(), 0.25F);
    }
	
	@Override
    public void doRender(EntityVoidBlob entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        
        GL11.glDisable(GL11.GL_BLEND);
    }
	
	@Override
	protected void applyRotations(EntityVoidBlob entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
	
	protected void preRenderCallback(EntityVoidBlob entitylivingbaseIn, float partialTickTime)
    {
        float f = 2.0F;

        GlStateManager.scale(f, f, f);
    }
	
	@Override
	protected ResourceLocation getEntityTexture(EntityVoidBlob entity)
	{
		return TEXTURE;
	}
}
