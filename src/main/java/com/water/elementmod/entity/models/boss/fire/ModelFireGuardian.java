package com.water.elementmod.entity.models.boss.fire;

import com.water.elementmod.entity.boss.fire.EntityFireBoss;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelFireGuardian extends ModelBase {
	private final ModelRenderer Main;
	private final ModelRenderer Head;
	private final ModelRenderer Torso;
	private final ModelRenderer L_arm;
	private final ModelRenderer R_arm;

	public ModelFireGuardian() {
		textureWidth = 128;
		textureHeight = 128;

		Main = new ModelRenderer(this);
		Main.setRotationPoint(0.0F, 0.0F, 0.0F);

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, -3.0F, 0.0F);
		Main.addChild(Head);
		Head.cubeList.add(new ModelBox(Head, 34, 0, -4.0F, -8.1667F, -4.0F, 8, 8, 8, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 32, 32, -4.0F, -8.1667F, -4.0F, 8, 8, 8, 1.0F, false));

		Torso = new ModelRenderer(this);
		Torso.setRotationPoint(0.0F, 0.0F, 0.0F);
		Main.addChild(Torso);
		Torso.cubeList.add(new ModelBox(Torso, 0, 23, -7.0F, -3.1667F, -3.0F, 14, 8, 6, 0.0F, false));
		Torso.cubeList.add(new ModelBox(Torso, 0, 0, -6.0F, -3.1667F, -2.5F, 12, 18, 5, 0.0F, false));

		L_arm = new ModelRenderer(this);
		L_arm.setRotationPoint(7.0F, 0.0F, 0.0F);
		Main.addChild(L_arm);
		L_arm.cubeList.add(new ModelBox(L_arm, 0, 37, 0.0F, -3.1667F, -3.0F, 5, 14, 6, 0.0F, false));

		R_arm = new ModelRenderer(this);
		R_arm.setRotationPoint(-7.0F, 0.0F, 0.0F);
		Main.addChild(R_arm);
		R_arm.cubeList.add(new ModelBox(R_arm, 22, 48, -5.0F, -3.1667F, -3.0F, 5, 14, 6, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		Main.render(f5);
	}
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
		this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.Head.rotateAngleX = headPitch * 0.017453292F;
        float f = MathHelper.sin(this.swingProgress * (float)Math.PI);
        float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float)Math.PI);
        this.R_arm.rotateAngleZ = 0.0F;
        this.L_arm.rotateAngleZ = 0.0F;
        this.R_arm.rotateAngleY = -(0.1F - f * 0.6F);
        this.L_arm.rotateAngleY = 0.1F - f * 0.6F;
        float f2 = -(float)Math.PI / 2.1F;
        this.R_arm.rotateAngleX = f2;
        this.L_arm.rotateAngleX = f2;
        this.R_arm.rotateAngleX += f * 1.2F - f1 * 0.4F;
        this.L_arm.rotateAngleX += f * 1.2F - f1 * 0.4F;
        this.R_arm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.L_arm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.R_arm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.L_arm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    }
}