package com.water.elementmod.entity.render.boss.overworld;

import com.water.elementmod.entity.boss.overworld.EntityVoidKnight;
import com.water.elementmod.entity.models.boss.overworld.ModelVoidKnight;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderVoidKnight extends RenderLiving<EntityVoidKnight>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/void_knight.png");
	public static final ResourceLocation TEXTURE2 = new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/void_knight2.png");
	public static final ResourceLocation TEXTURE_END = new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/void_knight3.png");
	
	public RenderVoidKnight(RenderManager manager)
	{
		super(manager, new ModelVoidKnight(), 0.5F);
    }
	
    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }
	
	@Override
	protected ResourceLocation getEntityTexture(EntityVoidKnight entity)
	{
		int phase = entity.getPhase();
		switch(phase)
		{
		case 0:
			return TEXTURE;
		case 1:
			return TEXTURE;
		case 2:
			return TEXTURE;
		case 3:
			return TEXTURE2;
		case 4:
			return TEXTURE2;
		case 5:
			return TEXTURE_END;
		case 6:
			return TEXTURE_END;
		}
		
		return TEXTURE; // In case the phase is something other than 0-6
	}
	
	@Override
	protected void applyRotations(EntityVoidKnight entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
	
	protected void preRenderCallback(EntityVoidKnight entitylivingbaseIn, float partialTickTime)
    {
        float f = 1.65F;

        GlStateManager.scale(f, f, f);
    }
	
}
