package com.water.elementmod.entity.models.boss.water;

import com.water.elementmod.entity.boss.fire.EntityFireBoss;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelWaterTrash extends ModelBase {
	private final ModelRenderer main;
	private final ModelRenderer head;
	private final ModelRenderer torso;
	private final ModelRenderer l_arm;
	private final ModelRenderer l_arm2;
	private final ModelRenderer l_arm3;
	private final ModelRenderer l_arm4;
	private final ModelRenderer l_arm5;
	private final ModelRenderer l_arm6;
	private final ModelRenderer r_arm;
	private final ModelRenderer r_arm2;
	private final ModelRenderer r_arm3;
	private final ModelRenderer r_arm4;
	private final ModelRenderer r_arm5;
	private final ModelRenderer r_arm6;
	private final ModelRenderer l_leg;
	private final ModelRenderer l_leg2;
	private final ModelRenderer l_leg3;
	private final ModelRenderer r_leg;
	private final ModelRenderer r_leg2;
	private final ModelRenderer r_leg3;

	public ModelWaterTrash() {
		textureWidth = 128;
		textureHeight = 128;

		main = new ModelRenderer(this);
		main.setRotationPoint(0.0F, 12.0F, 0.0F);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -27.0F, 0.0F);
		main.addChild(head);
		head.cubeList.add(new ModelBox(head, 44, 0, -5.0F, -1.0F, -5.0F, 10, 1, 10, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 50, -4.5F, -10.0F, -4.5F, 9, 9, 9, 0.0F, false));

		torso = new ModelRenderer(this);
		torso.setRotationPoint(0.0F, 0.0F, 0.0F);
		main.addChild(torso);
		torso.cubeList.add(new ModelBox(torso, 0, 0, -6.0F, -27.0F, -5.0F, 12, 18, 10, 0.0F, false));
		torso.cubeList.add(new ModelBox(torso, 31, 39, -6.5F, -9.0F, -5.5F, 13, 2, 11, 0.0F, false));
		torso.cubeList.add(new ModelBox(torso, 32, 16, 0.5F, -27.0F, -6.0F, 9, 10, 12, 0.0F, false));
		torso.cubeList.add(new ModelBox(torso, 0, 28, -9.5F, -27.0F, -6.0F, 9, 10, 12, 0.0F, false));

		l_arm = new ModelRenderer(this);
		l_arm.setRotationPoint(9.0F, -25.0F, 0.0F);
		setRotationAngle(l_arm, 0.0873F, 0.0F, 0.0F);
		main.addChild(l_arm);
		l_arm.cubeList.add(new ModelBox(l_arm, 60, 60, 0.5F, -2.0038F, -4.0872F, 5, 10, 7, 0.0F, false));

		l_arm2 = new ModelRenderer(this);
		l_arm2.setRotationPoint(3.0F, 6.9962F, -0.0872F);
		setRotationAngle(l_arm2, -0.2618F, 0.0F, 0.0F);
		l_arm.addChild(l_arm2);
		l_arm2.cubeList.add(new ModelBox(l_arm2, 19, 80, -2.5F, 0.0F, -2.0F, 5, 10, 5, 0.0F, false));
		l_arm2.cubeList.add(new ModelBox(l_arm2, 77, 60, -3.0F, 9.3F, -2.5F, 6, 1, 6, 0.0F, false));

		l_arm3 = new ModelRenderer(this);
		l_arm3.setRotationPoint(0.0F, 11.0F, 1.0F);
		setRotationAngle(l_arm3, 0.0F, 0.0F, 0.5236F);
		l_arm2.addChild(l_arm3);
		l_arm3.cubeList.add(new ModelBox(l_arm3, 63, 52, -3.0F, -0.5F, -1.75F, 3, 4, 2, 0.0F, false));

		l_arm4 = new ModelRenderer(this);
		l_arm4.setRotationPoint(-1.0F, 3.0F, 0.0F);
		setRotationAngle(l_arm4, 0.0F, 0.0F, -0.6109F);
		l_arm3.addChild(l_arm4);
		l_arm4.cubeList.add(new ModelBox(l_arm4, 53, 53, -2.0F, -0.5F, -1.75F, 3, 4, 2, 0.0F, false));

		l_arm5 = new ModelRenderer(this);
		l_arm5.setRotationPoint(0.0F, 11.0F, 1.0F);
		setRotationAngle(l_arm5, 0.0F, 0.0F, -0.5236F);
		l_arm2.addChild(l_arm5);
		l_arm5.cubeList.add(new ModelBox(l_arm5, 27, 52, 0.0F, -0.4F, -1.75F, 3, 4, 2, 0.0F, false));

		l_arm6 = new ModelRenderer(this);
		l_arm6.setRotationPoint(1.0F, 3.0F, 0.0F);
		setRotationAngle(l_arm6, 0.0F, 0.0F, 0.6109F);
		l_arm5.addChild(l_arm6);
		l_arm6.cubeList.add(new ModelBox(l_arm6, 42, 4, -1.0F, -0.5F, -1.75F, 3, 4, 2, 0.0F, false));

		r_arm = new ModelRenderer(this);
		r_arm.setRotationPoint(-9.0F, -25.0F, 0.0F);
		setRotationAngle(r_arm, 0.0873F, 0.0F, 0.0F);
		main.addChild(r_arm);
		r_arm.cubeList.add(new ModelBox(r_arm, 36, 52, -5.5F, -2.0038F, -4.0872F, 5, 10, 7, 0.0F, false));

		r_arm2 = new ModelRenderer(this);
		r_arm2.setRotationPoint(-3.0F, 6.9962F, -0.0872F);
		setRotationAngle(r_arm2, -0.2618F, 0.0F, 0.0F);
		r_arm.addChild(r_arm2);
		r_arm2.cubeList.add(new ModelBox(r_arm2, 66, 77, -2.5F, 0.0F, -2.0F, 5, 10, 5, 0.0F, false));
		r_arm2.cubeList.add(new ModelBox(r_arm2, 74, 0, -3.0F, 9.3F, -2.5F, 6, 1, 6, 0.0F, false));

		r_arm3 = new ModelRenderer(this);
		r_arm3.setRotationPoint(0.0F, 11.0F, 1.0F);
		setRotationAngle(r_arm3, 0.0F, 0.0F, -0.5236F);
		r_arm2.addChild(r_arm3);
		r_arm3.cubeList.add(new ModelBox(r_arm3, 34, 0, 0.0F, -0.5F, -1.75F, 3, 4, 2, 0.0F, false));

		r_arm4 = new ModelRenderer(this);
		r_arm4.setRotationPoint(1.0F, 3.0F, 0.0F);
		setRotationAngle(r_arm4, 0.0F, 0.0F, 0.6109F);
		r_arm3.addChild(r_arm4);
		r_arm4.cubeList.add(new ModelBox(r_arm4, 0, 34, -1.0F, -0.5F, -1.75F, 3, 4, 2, 0.0F, false));

		r_arm5 = new ModelRenderer(this);
		r_arm5.setRotationPoint(0.0F, 11.0F, 1.0F);
		setRotationAngle(r_arm5, 0.0F, 0.0F, 0.5236F);
		r_arm2.addChild(r_arm5);
		r_arm5.cubeList.add(new ModelBox(r_arm5, 0, 28, -3.0F, -0.5F, -1.75F, 3, 4, 2, 0.0F, false));

		r_arm6 = new ModelRenderer(this);
		r_arm6.setRotationPoint(-1.0F, 3.0F, 0.0F);
		setRotationAngle(r_arm6, 0.0F, 0.0F, -0.6109F);
		r_arm5.addChild(r_arm6);
		r_arm6.cubeList.add(new ModelBox(r_arm6, 0, 0, -2.0F, -0.5F, -1.75F, 3, 4, 2, 0.0F, false));

		l_leg = new ModelRenderer(this);
		l_leg.setRotationPoint(4.0F, -7.0F, 0.0F);
		setRotationAngle(l_leg, -0.1745F, 0.0F, 0.0F);
		main.addChild(l_leg);
		l_leg.cubeList.add(new ModelBox(l_leg, 0, 68, -3.0F, -1.0266F, -3.3899F, 5, 9, 7, 0.0F, false));

		l_leg2 = new ModelRenderer(this);
		l_leg2.setRotationPoint(-1.0F, 8.9734F, 0.6101F);
		setRotationAngle(l_leg2, 0.0873F, 0.0F, 0.0F);
		l_leg.addChild(l_leg2);
		l_leg2.cubeList.add(new ModelBox(l_leg2, 44, 74, -2.0F, -1.5F, -3.0F, 5, 8, 6, 0.0F, false));

		l_leg3 = new ModelRenderer(this);
		l_leg3.setRotationPoint(1.0F, 10.9886F, -0.2615F);
		l_leg2.addChild(l_leg3);
		l_leg3.cubeList.add(new ModelBox(l_leg3, 24, 69, -3.0F, -4.5F, -5.0F, 5, 3, 8, 0.0F, false));

		r_leg = new ModelRenderer(this);
		r_leg.setRotationPoint(-3.0F, -7.0F, 0.0F);
		setRotationAngle(r_leg, -0.1745F, 0.0F, 0.0F);
		main.addChild(r_leg);
		r_leg.cubeList.add(new ModelBox(r_leg, 62, 11, -3.0F, -1.0266F, -3.3899F, 5, 9, 7, 0.0F, false));

		r_leg2 = new ModelRenderer(this);
		r_leg2.setRotationPoint(0.0F, 8.9734F, 0.6101F);
		setRotationAngle(r_leg2, 0.0873F, 0.0F, 0.0F);
		r_leg.addChild(r_leg2);
		r_leg2.cubeList.add(new ModelBox(r_leg2, 73, 46, -3.0F, -1.5F, -3.0F, 5, 8, 6, 0.0F, false));

		r_leg3 = new ModelRenderer(this);
		r_leg3.setRotationPoint(-1.0F, 10.9886F, -0.2615F);
		r_leg2.addChild(r_leg3);
		r_leg3.cubeList.add(new ModelBox(r_leg3, 68, 30, -2.0F, -4.5F, -5.0F, 5, 3, 8, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		main.render(f5);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        this.l_leg.rotateAngleX = -1.5F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
        this.r_leg.rotateAngleX = 1.5F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
        this.l_leg.rotateAngleY = 0.0F;
        this.r_leg.rotateAngleY = 0.0F;
        
        this.l_arm.rotateAngleX = -1.0F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
        this.r_arm.rotateAngleX = 1.5F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
    }
	
	private float triangleWave(float p_78172_1_, float p_78172_2_)
    {
        return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
    }
}
