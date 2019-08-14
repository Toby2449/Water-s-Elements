package com.water.elementmod.entity.render;

import com.water.elementmod.entity.EntityFireSkeleton;
import com.water.elementmod.entity.EntityFireZombie;
import com.water.elementmod.entity.EntityNatureSkeleton;
import com.water.elementmod.entity.EntityWaterSkeleton;
import com.water.elementmod.entity.models.ModelFireSkeleton;
import com.water.elementmod.entity.models.ModelFireZombie;
import com.water.elementmod.entity.models.ModelNatureSkeleton;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;

public class RenderNatureSkeleton extends RenderLiving<EntityNatureSkeleton>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/natureskeleton.png"); 
	
	public RenderNatureSkeleton(RenderManager manager) 
	{
		super(manager, new ModelNatureSkeleton(), 0.5F);
		this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerBipedArmor(this)
        {
            protected void initArmor()
            {
                this.modelLeggings = new ModelSkeleton(0.5F, true);
                this.modelArmor = new ModelSkeleton(1.0F, true);
            }
        });
    }

    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }
	
	@Override
	protected ResourceLocation getEntityTexture(EntityNatureSkeleton entity)
	{
		return TEXTURE;
	}
	
	@Override
	protected void applyRotations(EntityNatureSkeleton entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
	
}