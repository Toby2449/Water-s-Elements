package com.water.elementmod.entity.render.boss;

import com.water.elementmod.entity.boss.EntityNatureBoss;
import com.water.elementmod.entity.models.boss.ModelNatureBoss;
import com.water.elementmod.entity.models.monster.ModelFireSkeleton;
import com.water.elementmod.entity.models.monster.ModelFireZombie;
import com.water.elementmod.entity.monster.EntityFireSkeleton;
import com.water.elementmod.entity.monster.EntityFireZombie;
import com.water.elementmod.entity.render.layers.LayerNatureBossAura;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerWitherAura;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderNatureBoss extends RenderLiving<EntityNatureBoss>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/nature_tree.png"); 
	
	public RenderNatureBoss(RenderManager manager) 
	{
		super(manager, new ModelNatureBoss(), 0.5F);
		this.addLayer(new LayerNatureBossAura(this));
    }
	
    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }
	
	@Override
	protected ResourceLocation getEntityTexture(EntityNatureBoss entity)
	{
		return TEXTURE;
	}
	
	@Override
	protected void applyRotations(EntityNatureBoss entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
	
	protected void preRenderCallback(EntityNatureBoss entitylivingbaseIn, float partialTickTime)
    {
        float f = 1.4F;

        GlStateManager.scale(f, f, f);
    }
	
}
