package com.water.elementmod.entity.render.boss.nature;

import com.water.elementmod.entity.boss.nature.EntityNatureBoss;
import com.water.elementmod.entity.boss.nature.EntityNatureBossMinion;
import com.water.elementmod.entity.models.boss.nature.ModelNatureBoss;
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
public class RenderNatureBossMinion extends RenderLiving<EntityNatureBossMinion>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/nature_boss.png"); 
	
	public RenderNatureBossMinion(RenderManager manager) 
	{
		super(manager, new ModelNatureBoss(), 0.5F);
    }
	
    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }
	
	@Override
	protected ResourceLocation getEntityTexture(EntityNatureBossMinion entity)
	{
		return TEXTURE;
	}
	
	@Override
	protected void applyRotations(EntityNatureBossMinion entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
	
	protected void preRenderCallback(EntityNatureBossMinion entitylivingbaseIn, float partialTickTime)
    {
        float f = 0.4F;

        GlStateManager.scale(f, f, f);
    }
	
}
