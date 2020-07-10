package com.water.elementmod.entity.render.friendly;

import com.water.elementmod.entity.friendly.EntityAlyxNature;
import com.water.elementmod.entity.models.friendly.ModelAlyx;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAlyxNature extends RenderLiving<EntityAlyxNature>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/Alex_skin.png"); 
	
	public RenderAlyxNature(RenderManager manager) 
	{
		super(manager, new ModelAlyx(0f, true), 0.5F);
    }
	
    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }
	
	@Override
	protected ResourceLocation getEntityTexture(EntityAlyxNature entity)
	{
		return TEXTURE;
	}
	
	@Override
	protected void applyRotations(EntityAlyxNature entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
	
}
