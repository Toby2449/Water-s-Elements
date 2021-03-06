package com.water.elementmod.entity.render.boss._void;

import com.water.elementmod.entity.boss._void.EntityLunacy;
import com.water.elementmod.entity.models.boss._void.ModelLunacy;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderLunacy extends RenderLiving<EntityLunacy>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/carapace.png");
	public static final ResourceLocation ARMOR = new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/carapace_armor.png");
	
	public RenderLunacy(RenderManager manager)
	{
		super(manager, new ModelLunacy(), 1.5F);
    }
	
    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }
	
	@Override
	protected ResourceLocation getEntityTexture(EntityLunacy entity)
	{
		boolean raging = entity.RageActivated;
		return raging ? ARMOR : TEXTURE;
	}
	
	@Override
	protected void applyRotations(EntityLunacy entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
	
	protected void preRenderCallback(EntityLunacy entitylivingbaseIn, float partialTickTime)
    {
        float f = 1.3F;

        GlStateManager.scale(f, f, f);
    }
	
}
