package com.water.elementmod.entity.render.layers;

import com.water.elementmod.entity.boss.EntityNatureBoss;
import com.water.elementmod.entity.models.boss.ModelNatureBoss;
import com.water.elementmod.entity.render.boss.RenderNatureBoss;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelWither;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderWither;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerNatureBossAura implements LayerRenderer<EntityNatureBoss>
{
    private static final ResourceLocation ARMOR = new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/boss_armor.png");
    private final RenderNatureBoss renderer;
    private final ModelNatureBoss model = new ModelNatureBoss();

    public LayerNatureBossAura(RenderNatureBoss witherRendererIn)
    {
        this.renderer = witherRendererIn;
    }

    public void doRenderLayer(EntityNatureBoss entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (entitylivingbaseIn.getInvulState() == true)
        {
            GlStateManager.depthMask(!entitylivingbaseIn.isInvisible());
            this.renderer.bindTexture(ARMOR);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float f = (float)entitylivingbaseIn.ticksExisted + partialTicks;
            float f1 = MathHelper.cos(f * 0.02F) * 3.0F / 2F;
            float f2 = f * 0.01F / 2F;
            GlStateManager.translate(f1, f2, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            float f3 = 0.5F;
            GlStateManager.color(0.5F, 0.5F, 0.5F, 1.0F);
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            this.model.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
            this.model.setModelAttributes(this.renderer.getMainModel());
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            this.model.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
        }
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}
