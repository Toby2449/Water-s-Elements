package com.water.elementmod.entity.models.boss.overworld;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelVoidKnight extends ModelBase {
	private final ModelRenderer main;
	private final ModelRenderer head;
	private final ModelRenderer inner_head;
	private final ModelRenderer outter_head;
	private final ModelRenderer spike1;
	private final ModelRenderer spike2;
	private final ModelRenderer torso;
	private final ModelRenderer l_arm;
	private final ModelRenderer l_arm2;
	private final ModelRenderer l_arm3;
	private final ModelRenderer sword;
	private final ModelRenderer back_sword;
	private final ModelRenderer r_arm;
	private final ModelRenderer r_arm2;
	private final ModelRenderer r_arm3;
	private final ModelRenderer shield;
	private final ModelRenderer l_leg;
	private final ModelRenderer r_leg;

	public ModelVoidKnight() {
		textureWidth = 128;
		textureHeight = 128;

		main = new ModelRenderer(this);
		main.setRotationPoint(0.0F, 14.0F, 0.0F);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -31.0F, 0.5F);
		main.addChild(head);

		inner_head = new ModelRenderer(this);
		inner_head.setRotationPoint(0.0F, 8.0F, -0.5F);
		head.addChild(inner_head);
		inner_head.cubeList.add(new ModelBox(inner_head, 61, 15, -3.5858F, -15.0F, -3.4763F, 7, 8, 7, 0.0F, false));

		outter_head = new ModelRenderer(this);
		outter_head.setRotationPoint(0.0F, 8.0F, -0.5F);
		head.addChild(outter_head);
		outter_head.cubeList.add(new ModelBox(outter_head, 64, 84, -3.5858F, -16.0F, -3.4763F, 7, 1, 7, 0.0F, false));
		outter_head.cubeList.add(new ModelBox(outter_head, 68, 92, -3.5858F, -15.0F, 3.5237F, 7, 7, 1, 0.0F, false));
		outter_head.cubeList.add(new ModelBox(outter_head, 43, 0, -3.5858F, -15.0F, -4.4763F, 7, 2, 1, 0.0F, false));
		outter_head.cubeList.add(new ModelBox(outter_head, 30, 51, 0.4142F, -12.0F, -4.4763F, 3, 4, 1, 0.0F, false));
		outter_head.cubeList.add(new ModelBox(outter_head, 17, 19, -3.5858F, -12.0F, -4.4763F, 3, 4, 1, 0.0F, false));
		outter_head.cubeList.add(new ModelBox(outter_head, 4, 6, -3.5858F, -13.0F, -4.4763F, 1, 1, 1, 0.0F, false));
		outter_head.cubeList.add(new ModelBox(outter_head, 0, 6, 2.4142F, -13.0F, -4.4763F, 1, 1, 1, 0.0F, false));
		outter_head.cubeList.add(new ModelBox(outter_head, 91, 12, -4.5858F, -15.0F, -3.4763F, 1, 7, 7, 0.0F, false));
		outter_head.cubeList.add(new ModelBox(outter_head, 0, 90, 3.4142F, -15.0F, -3.4763F, 1, 7, 7, 0.0F, false));

		spike1 = new ModelRenderer(this);
		spike1.setRotationPoint(3.0F, -17.0F, -3.0F);
		setRotationAngle(spike1, -0.3491F, -0.3491F, 0.0F);
		outter_head.addChild(spike1);
		spike1.cubeList.add(new ModelBox(spike1, 8, 46, -0.931F, -2.4471F, -0.1391F, 1, 5, 1, 0.0F, false));

		spike2 = new ModelRenderer(this);
		spike2.setRotationPoint(-2.0F, -17.0F, -3.0F);
		setRotationAngle(spike2, -0.3491F, 0.3491F, 0.0F);
		outter_head.addChild(spike2);
		spike2.cubeList.add(new ModelBox(spike2, 36, 30, -1.1209F, -2.4471F, -0.55F, 1, 5, 1, 0.0F, false));

		torso = new ModelRenderer(this);
		torso.setRotationPoint(0.0F, 0.0F, 0.0F);
		main.addChild(torso);
		torso.cubeList.add(new ModelBox(torso, 0, 0, -8.5858F, -30.0F, -4.4763F, 17, 10, 9, 0.0F, false));
		torso.cubeList.add(new ModelBox(torso, 36, 30, -6.5858F, -20.0F, -3.4763F, 13, 8, 7, 0.0F, false));
		torso.cubeList.add(new ModelBox(torso, 17, 19, -6.5858F, -12.0F, -4.4763F, 13, 2, 9, 0.0F, false));

		l_arm = new ModelRenderer(this);
		l_arm.setRotationPoint(8.5F, -28.0F, 0.0F);
		main.addChild(l_arm);
		l_arm.cubeList.add(new ModelBox(l_arm, 0, 61, -0.0858F, -3.0F, -3.4763F, 7, 8, 7, 0.0F, false));

		l_arm2 = new ModelRenderer(this);
		l_arm2.setRotationPoint(-8.5F, 37.0F, 0.0F);
		l_arm.addChild(l_arm2);
		l_arm2.cubeList.add(new ModelBox(l_arm2, 90, 44, 8.4142F, -32.6F, -2.4763F, 5, 3, 5, 0.0F, false));

		l_arm3 = new ModelRenderer(this);
		l_arm3.setRotationPoint(12.0F, -31.0F, 0.0F);
		setRotationAngle(l_arm3, -0.0873F, 0.0F, 0.0F);
		l_arm2.addChild(l_arm3);
		l_arm3.cubeList.add(new ModelBox(l_arm3, 87, 87, -3.5858F, 1.1705F, -2.3815F, 5, 7, 5, 0.0F, false));
		l_arm3.cubeList.add(new ModelBox(l_arm3, 44, 86, -4.0858F, 8.1705F, -2.8815F, 6, 5, 6, 0.0F, false));

		sword = new ModelRenderer(this);
		sword.setRotationPoint(-12.0F, 31.0F, 0.0F);
		l_arm3.addChild(sword);
		sword.cubeList.add(new ModelBox(sword, 16, 30, 9.4142F, -22.3295F, -20.8815F, 2, 5, 16, 0.0F, false));
		sword.cubeList.add(new ModelBox(sword, 0, 0, 9.4142F, -22.3295F, -22.85F, 2, 4, 2, 0.0F, false));
		sword.cubeList.add(new ModelBox(sword, 17, 24, 9.4142F, -22.3295F, -24.85F, 2, 2, 2, 0.0F, false));
		sword.cubeList.add(new ModelBox(sword, 0, 46, 9.4142F, -24.8295F, -4.8815F, 2, 9, 2, 0.0F, false));

		back_sword = new ModelRenderer(this);
		back_sword.setRotationPoint(12.0F, -20.5F, 5.0F);
		setRotationAngle(back_sword, 0.7854F, 0.0F, 0.0F);
		sword.addChild(back_sword);
		back_sword.cubeList.add(new ModelBox(back_sword, 0, 19, -2.0858F, -2.2535F, -2.3797F, 2, 5, 5, 0.0F, false));

		r_arm = new ModelRenderer(this);
		r_arm.setRotationPoint(-8.5F, -28.0F, 0.0F);
		main.addChild(r_arm);
		r_arm.cubeList.add(new ModelBox(r_arm, 52, 0, -7.0858F, -3.0F, -3.4763F, 7, 8, 7, 0.0F, false));

		r_arm2 = new ModelRenderer(this);
		r_arm2.setRotationPoint(9.5F, 37.0F, 0.0F);
		r_arm.addChild(r_arm2);
		r_arm2.cubeList.add(new ModelBox(r_arm2, 15, 51, -14.5858F, -32.5F, -2.4763F, 5, 3, 5, 0.0F, false));

		r_arm3 = new ModelRenderer(this);
		r_arm3.setRotationPoint(-11.0F, -31.0F, 0.0F);
		setRotationAngle(r_arm3, -0.1745F, 0.0F, 0.0F);
		r_arm2.addChild(r_arm3);
		r_arm3.cubeList.add(new ModelBox(r_arm3, 86, 31, -3.5858F, 1.3321F, -2.2722F, 5, 7, 5, 0.0F, false));
		r_arm3.cubeList.add(new ModelBox(r_arm3, 84, 64, -4.0858F, 8.3321F, -2.7722F, 6, 5, 6, 0.0F, false));

		shield = new ModelRenderer(this);
		shield.setRotationPoint(11.0F, 32.0F, 0.0F);
		r_arm3.addChild(shield);
		shield.cubeList.add(new ModelBox(shield, 54, 56, -15.5858F, -30.6679F, -6.2722F, 1, 1, 13, 0.0F, false));
		shield.cubeList.add(new ModelBox(shield, 78, 19, -16.5858F, -29.6679F, -5.2722F, 1, 1, 11, 0.0F, false));
		shield.cubeList.add(new ModelBox(shield, 0, 19, -15.5858F, -29.6679F, -7.2722F, 1, 12, 15, 0.0F, false));
		shield.cubeList.add(new ModelBox(shield, 39, 45, -16.5858F, -28.6679F, -6.2722F, 1, 11, 13, 0.0F, false));
		shield.cubeList.add(new ModelBox(shield, 0, 46, -15.5858F, -17.6679F, -6.2722F, 1, 2, 13, 0.0F, false));
		shield.cubeList.add(new ModelBox(shield, 71, 71, -16.5858F, -17.6679F, -5.2722F, 1, 2, 11, 0.0F, false));
		shield.cubeList.add(new ModelBox(shield, 80, 0, -15.5858F, -15.6679F, -5.2722F, 1, 1, 11, 0.0F, false));
		shield.cubeList.add(new ModelBox(shield, 21, 59, -16.5858F, -15.6679F, -3.2722F, 1, 1, 7, 0.0F, false));
		shield.cubeList.add(new ModelBox(shield, 35, 51, -16.5858F, -14.6679F, -2.2722F, 1, 1, 5, 0.0F, false));
		shield.cubeList.add(new ModelBox(shield, 54, 45, -15.5858F, -14.6679F, -3.2722F, 1, 2, 7, 0.0F, false));

		l_leg = new ModelRenderer(this);
		l_leg.setRotationPoint(4.0F, -10.0F, 0.5F);
		main.addChild(l_leg);
		l_leg.cubeList.add(new ModelBox(l_leg, 47, 70, -2.5858F, 0.0F, -3.9763F, 5, 8, 7, 0.0F, false));
		l_leg.cubeList.add(new ModelBox(l_leg, 17, 30, -2.5858F, 8.0F, -4.9763F, 5, 2, 2, 0.0F, false));
		l_leg.cubeList.add(new ModelBox(l_leg, 52, 15, -2.5858F, 6.0F, -4.9763F, 5, 2, 1, 0.0F, false));
		l_leg.cubeList.add(new ModelBox(l_leg, 22, 80, -2.5858F, 8.0F, -2.9763F, 5, 8, 6, 0.0F, false));
		l_leg.cubeList.add(new ModelBox(l_leg, 67, 36, -2.5858F, 16.0F, -5.9763F, 5, 4, 9, 0.0F, false));

		r_leg = new ModelRenderer(this);
		r_leg.setRotationPoint(-4.0F, -10.0F, 0.5F);
		main.addChild(r_leg);
		r_leg.cubeList.add(new ModelBox(r_leg, 69, 49, -2.5858F, 0.0F, -3.9763F, 5, 8, 7, 0.0F, false));
		r_leg.cubeList.add(new ModelBox(r_leg, 0, 29, -2.5858F, 8.0F, -4.9763F, 5, 2, 2, 0.0F, false));
		r_leg.cubeList.add(new ModelBox(r_leg, 43, 3, -2.5858F, 6.0F, -4.9763F, 5, 2, 1, 0.0F, false));
		r_leg.cubeList.add(new ModelBox(r_leg, 0, 76, -2.5858F, 8.0F, -2.9763F, 5, 8, 6, 0.0F, false));
		r_leg.cubeList.add(new ModelBox(r_leg, 19, 67, -2.5858F, 16.0F, -5.9763F, 5, 4, 9, 0.0F, false));
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
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
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
