package com.water.elementmod.entity.render.boss._void;

import com.water.elementmod.entity.boss._void.EntityVoidEntity;
import com.water.elementmod.entity.boss._void.EntityVoidEntityChest;
import com.water.elementmod.entity.models.boss._void.ModelVoidEntity;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderVoidEntityChest extends RenderLiving<EntityVoidEntityChest>
{
	
public static final ResourceLocation TEXTURE = new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/void_entity.png");
	
	public RenderVoidEntityChest(RenderManager manager)
	{
		super(manager, new ModelVoidEntity(), 0F);
    }
	
    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }
	
	@Override
	protected ResourceLocation getEntityTexture(EntityVoidEntityChest entity)
	{
		return TEXTURE;
	}
	
	@Override
	protected void applyRotations(EntityVoidEntityChest entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
	
	protected void preRenderCallback(EntityVoidEntityChest entitylivingbaseIn, float partialTickTime)
    {
        float f = 0F;

        GlStateManager.scale(f, f, f);
    }
}
