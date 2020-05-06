package com.water.elementmod.entity.render.boss.fire;

import com.water.elementmod.entity.boss.fire.EntityFireBoss;
import com.water.elementmod.entity.models.boss.fire.ModelFireBoss;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFireBoss extends RenderLiving<EntityFireBoss>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/fire_boss.png"); 
	
	public RenderFireBoss(RenderManager manager) 
	{
		super(manager, new ModelFireBoss(), 0.5F);
    }
	
    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }
	
	@Override
	protected ResourceLocation getEntityTexture(EntityFireBoss entity)
	{
		return TEXTURE;
	}
	
	@Override
	protected void applyRotations(EntityFireBoss entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
	
	protected void preRenderCallback(EntityFireBoss entitylivingbaseIn, float partialTickTime)
    {
        float f = 2.15F;

        GlStateManager.scale(f, f, f);
        GlStateManager.rotate(-90, 0.0f, 1.0f, 0.0f);
    }
	
}
