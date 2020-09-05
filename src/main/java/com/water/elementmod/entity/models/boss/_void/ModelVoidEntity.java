package com.water.elementmod.entity.models.boss._void;

import com.water.elementmod.entity.boss._void.EntityCarapace;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelVoidEntity extends ModelBase
{
	private final ModelRenderer main;
	private final ModelRenderer head;
	private final ModelRenderer horn1;
	private final ModelRenderer horn2;
	private final ModelRenderer horn3;
	private final ModelRenderer horn4;
	private final ModelRenderer horn5;
	private final ModelRenderer horn6;
	private final ModelRenderer helmet2;
	private final ModelRenderer helmet1;
	private final ModelRenderer torso;
	private final ModelRenderer belt;
	private final ModelRenderer torso2;
	private final ModelRenderer torso3;
	private final ModelRenderer torso4;
	private final ModelRenderer torso5;
	private final ModelRenderer r_arm;
	private final ModelRenderer r_arm2;
	private final ModelRenderer r_arm3;
	private final ModelRenderer l_arm;
	private final ModelRenderer pauldren;
	private final ModelRenderer l_arm2;
	private final ModelRenderer l_arm3;
	private final ModelRenderer spike1;
	private final ModelRenderer spike2;
	private final ModelRenderer lower2;
	private final ModelRenderer lower;

	public ModelVoidEntity() {
		textureWidth = 512;
		textureHeight = 512;

		main = new ModelRenderer(this);
		main.setRotationPoint(0.0F, -4.0F, 0.0F);
		setRotationAngle(main, -0.0873F, 0.3491F, 0.0F);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -44.0F, 1.0F);
		setRotationAngle(head, 0.0F, -0.3491F, -0.0175F);
		main.addChild(head);
		head.cubeList.add(new ModelBox(head, 148, 132, -8.0F, -16.0F, 8.0F, 16, 9, 2, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 64, 136, -8.0F, -16.0F, -9.0F, 16, 9, 2, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 149, 106, -10.0F, -16.0F, -8.0F, 2, 9, 17, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 142, 8.0F, -16.0F, -8.0F, 2, 9, 17, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 76, -8.0F, -16.0F, -7.0F, 16, 16, 15, 0.0F, false));

		horn1 = new ModelRenderer(this);
		horn1.setRotationPoint(-3.0F, -18.0F, 7.0F);
		setRotationAngle(horn1, 0.0F, 0.0F, -0.5236F);
		head.addChild(horn1);
		horn1.cubeList.add(new ModelBox(horn1, 116, 0, -3.0F, -11.0F, -2.0F, 5, 11, 3, 0.0F, false));

		horn2 = new ModelRenderer(this);
		horn2.setRotationPoint(-0.3301F, -9.4282F, 0.0F);
		setRotationAngle(horn2, 0.0F, 0.0F, 0.5236F);
		horn1.addChild(horn2);
		horn2.cubeList.add(new ModelBox(horn2, 86, 123, -3.1199F, -4.0F, -2.0F, 5, 4, 3, 0.0F, false));

		horn3 = new ModelRenderer(this);
		horn3.setRotationPoint(0.0F, -3.0F, 0.0F);
		setRotationAngle(horn3, 0.0F, 0.0F, 0.5236F);
		horn2.addChild(horn3);
		horn3.cubeList.add(new ModelBox(horn3, 21, 139, -3.4699F, -8.8218F, -2.0F, 5, 9, 3, 0.0F, false));

		horn4 = new ModelRenderer(this);
		horn4.setRotationPoint(3.0F, -18.0F, 7.0F);
		setRotationAngle(horn4, 0.0F, 0.0F, 0.5236F);
		head.addChild(horn4);
		horn4.cubeList.add(new ModelBox(horn4, 70, 0, -2.0F, -11.0F, -2.0F, 5, 11, 3, 0.0F, false));

		horn5 = new ModelRenderer(this);
		horn5.setRotationPoint(0.3301F, -9.4282F, 0.0F);
		setRotationAngle(horn5, 0.0F, 0.0F, -0.5236F);
		horn4.addChild(horn5);
		horn5.cubeList.add(new ModelBox(horn5, 94, 100, -1.8801F, -4.0F, -2.0F, 5, 4, 3, 0.0F, false));

		horn6 = new ModelRenderer(this);
		horn6.setRotationPoint(0.0F, -3.0F, 0.0F);
		setRotationAngle(horn6, 0.0F, 0.0F, -0.5236F);
		horn5.addChild(horn6);
		horn6.cubeList.add(new ModelBox(horn6, 0, 139, -1.5301F, -8.8218F, -2.0F, 5, 9, 3, 0.0F, false));

		helmet2 = new ModelRenderer(this);
		helmet2.setRotationPoint(-6.0F, -16.0F, 2.0F);
		setRotationAngle(helmet2, 0.0873F, 0.0F, 0.0F);
		head.addChild(helmet2);
		helmet2.cubeList.add(new ModelBox(helmet2, 33, 136, 2.0F, -6.9962F, -7.9128F, 8, 8, 15, 0.0F, false));

		helmet1 = new ModelRenderer(this);
		helmet1.setRotationPoint(0.0F, -15.0F, 2.0F);
		setRotationAngle(helmet1, -0.1745F, 0.0F, 0.0F);
		head.addChild(helmet1);
		helmet1.cubeList.add(new ModelBox(helmet1, 44, 91, -8.0F, -4.0F, -10.0F, 16, 5, 18, 0.0F, false));

		torso = new ModelRenderer(this);
		torso.setRotationPoint(-6.0F, 0.0F, 9.0F);
		main.addChild(torso);
		torso.cubeList.add(new ModelBox(torso, 0, 0, -5.0F, -30.0F, -15.0F, 22, 28, 13, 0.0F, false));
		torso.cubeList.add(new ModelBox(torso, 55, 35, -6.0F, -17.0F, -16.0F, 24, 4, 15, 0.0F, false));

		belt = new ModelRenderer(this);
		belt.setRotationPoint(6.5F, -15.5F, -2.0F);
		setRotationAngle(belt, 0.0F, 0.0F, -0.7854F);
		torso.addChild(belt);
		belt.cubeList.add(new ModelBox(belt, 50, 41, -3.5F, -3.5F, 0.0F, 7, 7, 2, 0.0F, false));

		torso2 = new ModelRenderer(this);
		torso2.setRotationPoint(-3.0F, -24.0F, 0.0F);
		setRotationAngle(torso2, 0.0F, 0.0F, -0.5236F);
		torso.addChild(torso2);
		torso2.cubeList.add(new ModelBox(torso2, 50, 58, -2.8038F, -14.0F, -16.4F, 17, 17, 16, 0.0F, false));

		torso3 = new ModelRenderer(this);
		torso3.setRotationPoint(15.0F, -24.0F, 0.0F);
		setRotationAngle(torso3, 0.0F, 0.0F, 0.5236F);
		torso.addChild(torso3);
		torso3.cubeList.add(new ModelBox(torso3, 0, 41, -14.1962F, -14.0F, -16.5F, 17, 17, 16, 0.0F, false));

		torso4 = new ModelRenderer(this);
		torso4.setRotationPoint(15.0F, -34.0F, -9.0F);
		setRotationAngle(torso4, 0.0F, 0.0F, 0.2618F);
		torso.addChild(torso4);
		torso4.cubeList.add(new ModelBox(torso4, 102, 77, -12.0F, -9.0F, -6.5F, 20, 9, 14, 0.0F, false));

		torso5 = new ModelRenderer(this);
		torso5.setRotationPoint(-3.0F, -34.0F, -9.0F);
		setRotationAngle(torso5, 0.0F, 0.0F, -0.2618F);
		torso.addChild(torso5);
		torso5.cubeList.add(new ModelBox(torso5, 98, 100, -8.0F, -9.0F, -6.5F, 20, 9, 14, 0.0F, false));

		r_arm = new ModelRenderer(this);
		r_arm.setRotationPoint(16.0F, -37.0F, 0.0F);
		setRotationAngle(r_arm, -0.4363F, 0.0F, -1.2217F);
		main.addChild(r_arm);
		r_arm.cubeList.add(new ModelBox(r_arm, 0, 107, -6.9397F, 0.31F, -5.8555F, 12, 20, 12, 0.0F, false));

		r_arm2 = new ModelRenderer(this);
		r_arm2.setRotationPoint(0.4912F, 17.3095F, -0.7209F);
		setRotationAngle(r_arm2, 1.309F, -0.2618F, 0.0F);
		r_arm.addChild(r_arm2);
		r_arm2.cubeList.add(new ModelBox(r_arm2, 104, 123, -6.3703F, 0.45F, -5.7003F, 11, 17, 11, 0.0F, false));

		r_arm3 = new ModelRenderer(this);
		r_arm3.setRotationPoint(0.0F, 13.0F, 0.0F);
		setRotationAngle(r_arm3, 0.1745F, -0.0873F, 0.0F);
		r_arm2.addChild(r_arm3);
		r_arm3.cubeList.add(new ModelBox(r_arm3, 132, 0, -6.8844F, 0.4217F, -6.2F, 12, 9, 12, 0.0F, false));

		l_arm = new ModelRenderer(this);
		l_arm.setRotationPoint(-16.0F, -37.0F, 0.0F);
		setRotationAngle(l_arm, 0.0873F, 0.2618F, 0.9599F);
		main.addChild(l_arm);
		l_arm.cubeList.add(new ModelBox(l_arm, 67, 147, -6.0F, 14.0F, -6.0F, 12, 6, 12, 0.0F, false));
		l_arm.cubeList.add(new ModelBox(l_arm, 70, 0, -9.0F, -5.0F, -8.0F, 15, 19, 16, 0.0F, false));
		l_arm.cubeList.add(new ModelBox(l_arm, 48, 114, -12.0F, 2.0F, -9.0F, 10, 4, 18, 0.0F, false));
		l_arm.cubeList.add(new ModelBox(l_arm, 156, 75, -11.0F, -7.0F, -3.0F, 9, 10, 6, 0.0F, false));

		pauldren = new ModelRenderer(this);
		pauldren.setRotationPoint(-8.0F, -4.0F, 0.0F);
		setRotationAngle(pauldren, 0.0F, 0.0F, -0.5236F);
		l_arm.addChild(pauldren);
		pauldren.cubeList.add(new ModelBox(pauldren, 0, 41, -3.0F, -7.0F, -2.0F, 4, 10, 4, 0.0F, false));

		l_arm2 = new ModelRenderer(this);
		l_arm2.setRotationPoint(-0.4912F, 17.3095F, -0.7209F);
		setRotationAngle(l_arm2, 0.6981F, 0.2618F, 0.0F);
		l_arm.addChild(l_arm2);
		l_arm2.cubeList.add(new ModelBox(l_arm2, 122, 47, -5.5F, 0.0F, -5.5F, 11, 17, 11, 0.0F, false));
		l_arm2.cubeList.add(new ModelBox(l_arm2, 141, 144, -6.5088F, 0.0F, -7.5F, 14, 17, 7, 0.0F, false));

		l_arm3 = new ModelRenderer(this);
		l_arm3.setRotationPoint(0.0F, 13.0F, 0.0F);
		setRotationAngle(l_arm3, 0.1745F, 0.0F, 0.0F);
		l_arm2.addChild(l_arm3);
		l_arm3.cubeList.add(new ModelBox(l_arm3, 120, 23, -7.5088F, 0.0F, -6.0F, 14, 12, 12, 0.0F, false));

		spike1 = new ModelRenderer(this);
		spike1.setRotationPoint(0.0F, 14.0F, -5.0F);
		setRotationAngle(spike1, 0.7854F, 0.0F, 0.0F);
		l_arm2.addChild(spike1);
		spike1.cubeList.add(new ModelBox(spike1, 48, 114, -2.1016F, -9.8751F, -2.2384F, 5, 10, 4, 0.0F, false));

		spike2 = new ModelRenderer(this);
		spike2.setRotationPoint(0.0F, 14.0F, -5.0F);
		setRotationAngle(spike2, 0.7854F, 0.0F, 0.0F);
		l_arm2.addChild(spike2);
		spike2.cubeList.add(new ModelBox(spike2, 100, 54, -1.8587F, -13.4069F, 4.206F, 5, 11, 4, 0.0F, false));

		lower2 = new ModelRenderer(this);
		lower2.setRotationPoint(1.0F, -22.0F, 0.0F);
		setRotationAngle(lower2, 0.0F, -0.7854F, -0.9599F);
		lower2.cubeList.add(new ModelBox(lower2, 0, 168, -69.7377F, 0.1472F, 1.1585F, 68, 68, 68, 0.0F, false));

		lower = new ModelRenderer(this);
		lower.setRotationPoint(1.0F, -32.0F, 0.0F);
		setRotationAngle(lower, -0.7854F, 0.0F, -0.6109F);
		lower.cubeList.add(new ModelBox(lower, 0, 168, -75.4565F, 6.6578F, 6.2436F, 68, 68, 68, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		main.render(f5);
		lower2.render(f5);
		lower.render(f5);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
		this.head.rotateAngleY = netHeadYaw * 0.017453292F + -0.35F;
        this.head.rotateAngleX = -(headPitch * 0.017453292F);
        
        this.r_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F;
	    this.l_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
	    this.r_arm.rotateAngleZ = -1.0F;
	    this.l_arm.rotateAngleZ = 1.0F;
	    this.r_arm.rotateAngleX += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
	    this.l_arm.rotateAngleX -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
	    this.r_arm.rotateAngleZ += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
	    this.l_arm.rotateAngleZ -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    }
}
