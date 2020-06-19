package com.water.elementmod.entity.models.boss.water;

import com.water.elementmod.entity.boss.fire.EntityFireBoss;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelWaterBoss extends ModelBase {
	private final ModelRenderer main;
	private final ModelRenderer head;
	private final ModelRenderer head_spike1;
	private final ModelRenderer head_inner;
	private final ModelRenderer head_spike2;
	private final ModelRenderer head_inner2;
	private final ModelRenderer head_spike3;
	private final ModelRenderer head_inner3;
	private final ModelRenderer left_arm;
	private final ModelRenderer lower_left_arm;
	private final ModelRenderer right_arm;
	private final ModelRenderer lower_right_arm;
	private final ModelRenderer torso;
	private final ModelRenderer upper_torso3;
	private final ModelRenderer upper_torso4;
	private final ModelRenderer upper_torso;
	private final ModelRenderer upper_torso2;
	private final ModelRenderer lower_torso;

	public ModelWaterBoss() {
		textureWidth = 64;
		textureHeight = 64;

		main = new ModelRenderer(this);
		main.setRotationPoint(0.0F, 20.0F, 0.0F);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -16.0F, -0.5F);
		main.addChild(head);
		head.cubeList.add(new ModelBox(head, 0, 18, -2.9988F, -6.0F, -2.4F, 6, 6, 5, 0.0F, false));

		head_spike1 = new ModelRenderer(this);
		head_spike1.setRotationPoint(0.0F, -5.0F, 2.0F);
		setRotationAngle(head_spike1, -0.3491F, 0.0F, 0.0F);
		head.addChild(head_spike1);
		head_spike1.cubeList.add(new ModelBox(head_spike1, 28, 33, -1.0012F, -2.0F, -0.5F, 2, 2, 1, 0.0F, false));

		head_inner = new ModelRenderer(this);
		head_inner.setRotationPoint(0.0F, -2.2214F, 0.2557F);
		setRotationAngle(head_inner, 0.7854F, 0.0F, 0.0F);
		head_spike1.addChild(head_inner);
		head_inner.cubeList.add(new ModelBox(head_inner, 31, 11, -1.0012F, -1.5786F, -0.9752F, 2, 2, 1, 0.0F, false));

		head_spike2 = new ModelRenderer(this);
		head_spike2.setRotationPoint(-2.0F, -5.0F, 2.0F);
		setRotationAngle(head_spike2, -0.1745F, 0.0F, -1.1345F);
		head.addChild(head_spike2);
		head_spike2.cubeList.add(new ModelBox(head_spike2, 25, 11, -1.0012F, -2.0F, -0.5F, 2, 2, 1, 0.0F, false));

		head_inner2 = new ModelRenderer(this);
		head_inner2.setRotationPoint(0.0F, -2.2214F, 0.2557F);
		setRotationAngle(head_inner2, 0.7854F, 0.0F, 0.0F);
		head_spike2.addChild(head_inner2);
		head_inner2.cubeList.add(new ModelBox(head_inner2, 12, 29, -1.0012F, -1.5786F, -1.1752F, 2, 2, 1, 0.0F, false));

		head_spike3 = new ModelRenderer(this);
		head_spike3.setRotationPoint(2.0F, -5.0F, 2.0F);
		setRotationAngle(head_spike3, -0.1745F, 0.0F, 1.1345F);
		head.addChild(head_spike3);
		head_spike3.cubeList.add(new ModelBox(head_spike3, 19, 0, -1.0012F, -2.0F, -0.5F, 2, 2, 1, 0.0F, false));

		head_inner3 = new ModelRenderer(this);
		head_inner3.setRotationPoint(0.0F, -2.2214F, 0.2557F);
		setRotationAngle(head_inner3, 0.7854F, 0.0F, 0.0F);
		head_spike3.addChild(head_inner3);
		head_inner3.cubeList.add(new ModelBox(head_inner3, 19, 10, -1.0012F, -1.5786F, -1.1752F, 2, 2, 1, 0.0F, false));

		left_arm = new ModelRenderer(this);
		left_arm.setRotationPoint(-6.0F, -14.0F, 0.0F);
		setRotationAngle(left_arm, -0.0873F, 0.0F, 0.0F);
		main.addChild(left_arm);
		left_arm.cubeList.add(new ModelBox(left_arm, 16, 33, -3.5012F, -2.0F, -2.7195F, 4, 6, 4, 0.0F, false));

		lower_left_arm = new ModelRenderer(this);
		lower_left_arm.setRotationPoint(-2.0F, 3.0F, 0.0F);
		setRotationAngle(lower_left_arm, 0.1745F, 0.0F, 0.0F);
		left_arm.addChild(lower_left_arm);
		lower_left_arm.cubeList.add(new ModelBox(lower_left_arm, 41, 43, -1.0012F, 0.0F, -2.2195F, 3, 5, 3, 0.0F, false));
		lower_left_arm.cubeList.add(new ModelBox(lower_left_arm, 39, 21, -1.5012F, 2.0F, -2.7195F, 4, 2, 4, 0.0F, false));
		lower_left_arm.cubeList.add(new ModelBox(lower_left_arm, 28, 39, -1.5012F, 5.0F, -2.7195F, 4, 3, 4, 0.0F, false));

		right_arm = new ModelRenderer(this);
		right_arm.setRotationPoint(9.0F, -14.0F, 0.0F);
		setRotationAngle(right_arm, -0.0873F, 0.0F, 0.0F);
		main.addChild(right_arm);
		right_arm.cubeList.add(new ModelBox(right_arm, 0, 29, -3.5012F, -2.0F, -2.7195F, 4, 6, 4, 0.0F, false));

		lower_right_arm = new ModelRenderer(this);
		lower_right_arm.setRotationPoint(-2.0F, 3.0F, 0.0F);
		setRotationAngle(lower_right_arm, 0.1745F, 0.0F, 0.0F);
		right_arm.addChild(lower_right_arm);
		lower_right_arm.cubeList.add(new ModelBox(lower_right_arm, 13, 43, -1.0012F, 0.0F, -2.2195F, 3, 5, 3, 0.0F, false));
		lower_right_arm.cubeList.add(new ModelBox(lower_right_arm, 40, 11, -1.5012F, 2.0F, -2.7195F, 4, 2, 4, 0.0F, false));
		lower_right_arm.cubeList.add(new ModelBox(lower_right_arm, 0, 39, -1.5012F, 5.0F, -2.7195F, 4, 3, 4, 0.0F, false));

		torso = new ModelRenderer(this);
		torso.setRotationPoint(0.0F, 0.0F, 0.0F);
		main.addChild(torso);

		upper_torso3 = new ModelRenderer(this);
		upper_torso3.setRotationPoint(-3.0F, -13.0F, 0.0F);
		setRotationAngle(upper_torso3, 0.0F, 0.0F, -0.2618F);
		torso.addChild(upper_torso3);
		upper_torso3.cubeList.add(new ModelBox(upper_torso3, 0, 9, -2.9012F, -3.5F, -2.9695F, 7, 4, 5, 0.0F, false));

		upper_torso4 = new ModelRenderer(this);
		upper_torso4.setRotationPoint(3.0F, -13.0F, 0.0F);
		setRotationAngle(upper_torso4, 0.0F, 0.0F, 0.2618F);
		torso.addChild(upper_torso4);
		upper_torso4.cubeList.add(new ModelBox(upper_torso4, 0, 0, -4.1012F, -3.5F, -2.9695F, 7, 4, 5, 0.0F, false));

		upper_torso = new ModelRenderer(this);
		upper_torso.setRotationPoint(0.0F, -10.0F, 0.0F);
		setRotationAngle(upper_torso, 0.0F, 0.0F, 0.5236F);
		torso.addChild(upper_torso);
		upper_torso.cubeList.add(new ModelBox(upper_torso, 24, 0, -2.0012F, -6.0F, -2.7195F, 6, 6, 5, 0.0F, false));

		upper_torso2 = new ModelRenderer(this);
		upper_torso2.setRotationPoint(0.0F, -10.0F, 0.0F);
		setRotationAngle(upper_torso2, 0.0F, 0.0F, -0.5236F);
		torso.addChild(upper_torso2);
		upper_torso2.cubeList.add(new ModelBox(upper_torso2, 22, 22, -4.0012F, -6.0F, -2.7095F, 6, 6, 5, 0.0F, false));

		lower_torso = new ModelRenderer(this);
		lower_torso.setRotationPoint(0.0F, 0.0F, 0.0F);
		torso.addChild(lower_torso);
		lower_torso.cubeList.add(new ModelBox(lower_torso, 20, 14, -4.0012F, -10.0F, -2.2195F, 8, 3, 4, 0.0F, false));
		lower_torso.cubeList.add(new ModelBox(lower_torso, 32, 33, -3.6012F, -7.0F, -2.0F, 7, 3, 3, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		main.render(f5);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) 
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = -(headPitch * 0.017453292F);
        
        this.right_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F;
	    this.left_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
	    this.right_arm.rotateAngleZ = 0.0F;
	    this.left_arm.rotateAngleZ = 0.0F;
	    this.right_arm.rotateAngleX += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
	    this.left_arm.rotateAngleX -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
	    this.right_arm.rotateAngleZ += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
	    this.left_arm.rotateAngleZ -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    }
}
