package com.water.elementmod.entity.render.boss._void;

import com.water.elementmod.entity.boss._void.EntityVoidEntity;
import com.water.elementmod.entity.models.boss._void.ModelVoidEntity;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderVoidEntity extends RenderLiving<EntityVoidEntity>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/void_entity.png");
	
	public RenderVoidEntity(RenderManager manager)
	{
		super(manager, new ModelVoidEntity(), 1.5F);
    }
	
    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }
	
	@Override
	protected ResourceLocation getEntityTexture(EntityVoidEntity entity)
	{
		return TEXTURE;
	}
	
	@Override
	protected void applyRotations(EntityVoidEntity entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
	
	protected void preRenderCallback(EntityVoidEntity entitylivingbaseIn, float partialTickTime)
    {
        float f = 1.65F;

        GlStateManager.scale(f, f, f);
        GlStateManager.rotate(180, 0.0f, 1.0f, 0.0f);
    }
	
}
