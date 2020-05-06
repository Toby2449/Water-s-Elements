package com.water.elementmod.entity.render.boss.fire;

import com.water.elementmod.entity.boss.fire.EntityFireBossMini;
import com.water.elementmod.entity.models.boss.fire.ModelFireBossMini;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFireBossMini extends RenderLiving<EntityFireBossMini>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/fire_boss.png"); 
	
	public RenderFireBossMini(RenderManager manager) 
	{
		super(manager, new ModelFireBossMini(), 0.5F);
    }
	
    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }
	
	@Override
	protected ResourceLocation getEntityTexture(EntityFireBossMini entity)
	{
		return TEXTURE;
	}
	
	@Override
	protected void applyRotations(EntityFireBossMini entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
	
	protected void preRenderCallback(EntityFireBossMini entitylivingbaseIn, float partialTickTime)
    {
        float f = 0.65F;

        GlStateManager.scale(f, f, f);
        GlStateManager.rotate(-90, 0.0f, 1.0f, 0.0f);
    }
	
}
