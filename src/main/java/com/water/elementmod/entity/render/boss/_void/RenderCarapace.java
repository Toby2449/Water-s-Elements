package com.water.elementmod.entity.render.boss._void;

import com.water.elementmod.entity.boss._void.EntityCarapace;
import com.water.elementmod.entity.models.boss._void.ModelCarapace;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCarapace extends RenderLiving<EntityCarapace>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/carapace.png");
	
	public RenderCarapace(RenderManager manager)
	{
		super(manager, new ModelCarapace(), 1.5F);
    }
	
    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }
	
	@Override
	protected ResourceLocation getEntityTexture(EntityCarapace entity)
	{
		return TEXTURE;
	}
	
	@Override
	protected void applyRotations(EntityCarapace entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
	
	protected void preRenderCallback(EntityCarapace entitylivingbaseIn, float partialTickTime)
    {
        float f = 1.3F;

        GlStateManager.scale(f, f, f);
    }
	
}
