package com.water.elementmod.entity.render.boss.fire;

import com.water.elementmod.entity.boss.fire.EntityFireGuardian;
import com.water.elementmod.entity.models.boss.fire.ModelGuardian;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFireGuardian extends RenderLiving<EntityFireGuardian>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/fire_guardian.png"); 
	
	public RenderFireGuardian(RenderManager manager) 
	{
		super(manager, new ModelGuardian(), 0.5F);
    }
	
    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }
	
	@Override
	protected ResourceLocation getEntityTexture(EntityFireGuardian entity)
	{
		return TEXTURE;
	}
	
	@Override
	protected void applyRotations(EntityFireGuardian entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
	
	protected void preRenderCallback(EntityFireGuardian entitylivingbaseIn, float partialTickTime)
    {
        float f = 1.25F;
        
        GlStateManager.scale(f, f, f);
    }
	
}
