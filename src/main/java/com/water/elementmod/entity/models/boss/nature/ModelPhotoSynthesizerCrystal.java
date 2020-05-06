package com.water.elementmod.entity.models.boss.nature;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelPhotoSynthesizerCrystal extends ModelBase
{
    /** The cube model for the Ender Crystal. */
    private final ModelRenderer cube;
    /** The glass model for the Ender Crystal. */
    private final ModelRenderer glass = new ModelRenderer(this, "glass");
    /** The base model for the Ender Crystal. */
    private ModelRenderer base;

    public ModelPhotoSynthesizerCrystal(float p_i1170_1_)
    {
        this.glass.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
        this.cube = new ModelRenderer(this, "cube");
        this.cube.setTextureOffset(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        GlStateManager.pushMatrix();
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        GlStateManager.translate(0.0F, -0.5F, 0.0F);

        GlStateManager.rotate(limbSwingAmount, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0.0F, 0.8F + ageInTicks, 0.0F);
        GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
        this.glass.render(scale);
        float f = 0.875F;
        GlStateManager.scale(0.875F, 0.875F, 0.875F);
        GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
        GlStateManager.rotate(limbSwingAmount, 0.0F, 1.0F, 0.0F);
        this.glass.render(scale);
        GlStateManager.scale(0.875F, 0.875F, 0.875F);
        GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
        GlStateManager.rotate(limbSwingAmount, 0.0F, 1.0F, 0.0F);
        this.cube.render(scale);
        GlStateManager.popMatrix();
    }
}