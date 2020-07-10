package com.water.elementmod.entity.models.boss.overworld;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelVoidSmasher extends ModelBase {
	private final ModelRenderer main;
	private final ModelRenderer head;
	private final ModelRenderer body;
	private final ModelRenderer r_arm;
	private final ModelRenderer r_arm2;
	private final ModelRenderer r_arm3;
	private final ModelRenderer r_arm4;
	private final ModelRenderer r_arm5;
	private final ModelRenderer l_arm;
	private final ModelRenderer l_arm2;
	private final ModelRenderer l_arm3;
	private final ModelRenderer r_leg;
	private final ModelRenderer l_leg;

	public ModelVoidSmasher() {
		textureWidth = 128;
		textureHeight = 128;

		main = new ModelRenderer(this);
		main.setRotationPoint(-1.0F, 9.0F, 1.0F);

		head = new ModelRenderer(this);
		head.setRotationPoint(-5.5F, -30.0F, -5.0F);
		main.addChild(head);
		head.cubeList.add(new ModelBox(head, 0, 53, -0.0179F, -7.0F, -4.4464F, 7, 8, 8, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 44, 81, 3.9821F, -8.0F, -5.4464F, 4, 4, 4, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 86, -1.0179F, -3.0F, -5.4464F, 3, 3, 3, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 24, 3.9821F, -6.0F, 3.5536F, 3, 2, 1, 0.0F, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);
		main.addChild(body);
		body.cubeList.add(new ModelBox(body, 0, 0, -8.5179F, -31.0F, -7.4464F, 12, 11, 13, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 38, 38, 3.4821F, -28.0F, -5.4464F, 9, 11, 10, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 18, 69, 7.4821F, -21.0F, 4.5536F, 4, 4, 2, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 37, 0, -5.5179F, -17.0F, -4.4464F, 14, 5, 8, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 69, 13, -7.5179F, -17.0F, -6.4464F, 6, 5, 5, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 22, 53, -2.5179F, -17.0F, 3.5536F, 5, 4, 2, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 24, -6.0179F, -12.0F, -4.9464F, 15, 6, 9, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 58, 59, -5.5179F, -20.0F, -4.4464F, 9, 3, 8, 0.0F, false));

		r_arm = new ModelRenderer(this);
		r_arm.setRotationPoint(-12.0F, -25.0F, -0.5F);
		setRotationAngle(r_arm, 0.0524F, 0.0F, 0.0F);
		main.addChild(r_arm);
		r_arm.cubeList.add(new ModelBox(r_arm, 48, 17, -3.5179F, -4.0F, -3.9464F, 7, 11, 7, 0.0F, false));

		r_arm2 = new ModelRenderer(this);
		r_arm2.setRotationPoint(-4.0F, 7.0F, 0.5F);
		setRotationAngle(r_arm2, -0.0873F, 0.0F, 0.0F);
		r_arm.addChild(r_arm2);
		r_arm2.cubeList.add(new ModelBox(r_arm2, 0, 69, 0.9821F, -0.25F, -3.9464F, 6, 11, 6, 0.0F, false));

		r_arm3 = new ModelRenderer(this);
		r_arm3.setRotationPoint(0.0F, 11.0F, 0.0F);
		setRotationAngle(r_arm3, -0.1745F, 0.0F, 0.0F);
		r_arm2.addChild(r_arm3);
		r_arm3.cubeList.add(new ModelBox(r_arm3, 73, 0, 1.4821F, -0.6F, -3.4464F, 5, 3, 5, 0.0F, false));

		r_arm4 = new ModelRenderer(this);
		r_arm4.setRotationPoint(0.0F, 3.0F, 0.0F);
		setRotationAngle(r_arm4, -0.1745F, 0.0F, 0.0F);
		r_arm3.addChild(r_arm4);
		r_arm4.cubeList.add(new ModelBox(r_arm4, 84, 33, 1.9821F, -0.9F, -2.9464F, 4, 4, 4, 0.0F, false));

		r_arm5 = new ModelRenderer(this);
		r_arm5.setRotationPoint(0.5F, 4.0F, -0.5F);
		setRotationAngle(r_arm5, -0.2618F, 0.0F, 0.0F);
		r_arm4.addChild(r_arm5);
		r_arm5.cubeList.add(new ModelBox(r_arm5, 0, 0, 1.9821F, -1.35F, -1.9464F, 3, 5, 3, 0.0F, false));

		l_arm = new ModelRenderer(this);
		l_arm.setRotationPoint(9.0F, -25.0F, -0.5F);
		main.addChild(l_arm);
		l_arm.cubeList.add(new ModelBox(l_arm, 58, 70, 3.4821F, -1.0F, -1.9464F, 6, 10, 5, 0.0F, false));

		l_arm2 = new ModelRenderer(this);
		l_arm2.setRotationPoint(0.0F, 0.0F, 0.0F);
		l_arm.addChild(l_arm2);
		l_arm2.cubeList.add(new ModelBox(l_arm2, 80, 80, 3.9821F, 9.0F, -1.4464F, 4, 7, 4, 0.0F, false));

		l_arm3 = new ModelRenderer(this);
		l_arm3.setRotationPoint(0.0F, 0.0F, 0.0F);
		l_arm2.addChild(l_arm3);
		l_arm3.cubeList.add(new ModelBox(l_arm3, 76, 23, 3.4821F, 16.0F, -1.9464F, 5, 5, 5, 0.0F, false));

		r_leg = new ModelRenderer(this);
		r_leg.setRotationPoint(-6.5F, -6.0F, 0.5F);
		main.addChild(r_leg);
		r_leg.cubeList.add(new ModelBox(r_leg, 24, 76, 0.9821F, 0.0F, -2.9464F, 5, 10, 5, 0.0F, false));
		r_leg.cubeList.add(new ModelBox(r_leg, 76, 47, 1.2321F, 10.0F, -2.4464F, 4, 8, 4, 0.0F, false));
		r_leg.cubeList.add(new ModelBox(r_leg, 51, 59, 0.25F, 12.0F, -1.4464F, 3, 4, 3, 0.0F, false));
		r_leg.cubeList.add(new ModelBox(r_leg, 23, 39, 0.4821F, 18.0F, -4.1964F, 6, 3, 6, 0.0F, false));

		l_leg = new ModelRenderer(this);
		l_leg.setRotationPoint(1.5F, -6.0F, 0.5F);
		main.addChild(l_leg);
		l_leg.cubeList.add(new ModelBox(l_leg, 30, 59, -0.0179F, 0.0F, -3.9464F, 7, 10, 7, 0.0F, false));
		l_leg.cubeList.add(new ModelBox(l_leg, 80, 70, 3.9821F, 3.0F, -4.9464F, 4, 5, 4, 0.0F, false));
		l_leg.cubeList.add(new ModelBox(l_leg, 66, 35, 0.4821F, 10.0F, -3.4464F, 6, 6, 6, 0.0F, false));
		l_leg.cubeList.add(new ModelBox(l_leg, 0, 39, -0.0179F, 16.0F, -5.6964F, 7, 5, 9, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		main.render(f5);
	}
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        this.head.rotateAngleY = 0F;
        this.head.rotateAngleX = (headPitch * 0.017453292F);
        
        this.r_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F;
	    this.l_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
	    this.r_arm.rotateAngleZ = 0.0F;
	    this.l_arm.rotateAngleZ = 0.0F;
	    this.r_arm.rotateAngleX += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
	    this.l_arm.rotateAngleX -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
	    this.r_arm.rotateAngleZ += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
	    this.l_arm.rotateAngleZ -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
	    
	    this.l_leg.rotateAngleX = -1.5F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
        this.r_leg.rotateAngleX = 1.5F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
        this.l_leg.rotateAngleY = 0.0F;
        this.r_leg.rotateAngleY = 0.0F;
    }
	
	private float triangleWave(float p_78172_1_, float p_78172_2_)
    {
        return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
    }
}
