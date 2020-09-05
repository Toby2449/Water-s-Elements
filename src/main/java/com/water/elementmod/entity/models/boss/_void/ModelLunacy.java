package com.water.elementmod.entity.models.boss._void;

import com.water.elementmod.entity.boss._void.EntityLunacy;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelLunacy extends ModelBase
{
	private final ModelRenderer main;
	private final ModelRenderer head;
	private final ModelRenderer torso;
	private final ModelRenderer eye_holder;
	private final ModelRenderer torso2;
	private final ModelRenderer arm_base;
	private final ModelRenderer arm_base2;
	private final ModelRenderer tail;
	private final ModelRenderer tail2;
	private final ModelRenderer tail3;
	private final ModelRenderer l_leg;
	private final ModelRenderer l_leg2;
	private final ModelRenderer l_leg3;
	private final ModelRenderer l_leg4;
	private final ModelRenderer l_leg5;
	private final ModelRenderer r_leg;
	private final ModelRenderer r_leg2;
	private final ModelRenderer r_leg3;
	private final ModelRenderer r_leg4;
	private final ModelRenderer r_leg5;
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

	public ModelLunacy() {
		textureWidth = 256;
		textureHeight = 256;

		main = new ModelRenderer(this);
		main.setRotationPoint(0.0F, 17.0F, 0.0F);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -1.0F, -9.0F);
		main.addChild(head);
		head.cubeList.add(new ModelBox(head, 38, 169, -4.5F, -33.0F, -3.0F, 9, 10, 7, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 92, 173, -1.5F, -23.0F, -3.0F, 3, 10, 4, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 16, 52, 2.5F, -23.0F, -2.0F, 2, 8, 3, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 16, 41, -4.5F, -23.0F, -2.0F, 2, 8, 3, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 42, 49, -4.0F, -15.0F, -1.5F, 1, 5, 2, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 42, 42, 3.0F, -15.0F, -1.5F, 1, 5, 2, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 58, 0, -1.0F, -13.0F, -2.5F, 2, 5, 3, 0.0F, false));

		torso = new ModelRenderer(this);
		torso.setRotationPoint(0.0F, -16.0F, 4.0F);
		setRotationAngle(torso, 0.1745F, 0.0F, 0.0F);
		main.addChild(torso);
		torso.cubeList.add(new ModelBox(torso, 0, 0, -10.0F, -30.0F, -8.0F, 20, 23, 18, 0.0F, false));
		torso.cubeList.add(new ModelBox(torso, 100, 100, -8.0F, -7.0F, -8.0F, 16, 9, 18, 0.0F, false));
		torso.cubeList.add(new ModelBox(torso, 0, 83, -11.0F, -31.0F, -10.0F, 6, 24, 2, 0.0F, false));
		torso.cubeList.add(new ModelBox(torso, 0, 41, 5.0F, -31.0F, -10.0F, 6, 24, 2, 0.0F, false));

		eye_holder = new ModelRenderer(this);
		eye_holder.setRotationPoint(-1.0F, -30.0F, 2.0F);
		setRotationAngle(eye_holder, -0.9599F, 0.0F, 0.0F);
		torso.addChild(eye_holder);
		eye_holder.cubeList.add(new ModelBox(eye_holder, 144, 70, -3.0F, -2.8F, -5.75F, 8, 11, 11, 0.0F, false));

		torso2 = new ModelRenderer(this);
		torso2.setRotationPoint(-1.0F, -30.0F, 2.0F);
		setRotationAngle(torso2, -0.9599F, 0.0F, 0.0F);
		torso.addChild(torso2);
		torso2.cubeList.add(new ModelBox(torso2, 50, 15, 5.0F, -6.8F, -7.75F, 8, 16, 26, 0.0F, false));
		torso2.cubeList.add(new ModelBox(torso2, 0, 41, -11.0F, -6.8F, -7.75F, 8, 16, 26, 0.0F, false));
		torso2.cubeList.add(new ModelBox(torso2, 92, 0, -3.0F, -8.8F, -6.75F, 8, 13, 24, 0.0F, false));

		arm_base = new ModelRenderer(this);
		arm_base.setRotationPoint(-2.0F, -20.0F, 1.0F);
		setRotationAngle(arm_base, 0.0F, 0.0F, 0.7854F);
		torso.addChild(arm_base);
		arm_base.cubeList.add(new ModelBox(arm_base, 0, 93, 1.0F, -19.0F, -8.0F, 17, 26, 16, 0.0F, false));

		arm_base2 = new ModelRenderer(this);
		arm_base2.setRotationPoint(2.0F, -20.0F, 1.0F);
		setRotationAngle(arm_base2, 0.0F, 0.0F, -0.7854F);
		torso.addChild(arm_base2);
		arm_base2.cubeList.add(new ModelBox(arm_base2, 52, 67, -18.0F, -19.0F, -8.0F, 17, 26, 16, 0.0F, false));

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, -20.0F, 12.0F);
		setRotationAngle(tail, 0.8727F, 0.0F, 0.0F);
		main.addChild(tail);
		tail.cubeList.add(new ModelBox(tail, 105, 44, -6.0F, -5.0F, -7.0F, 12, 24, 13, 0.0F, false));

		tail2 = new ModelRenderer(this);
		tail2.setRotationPoint(0.0F, 21.0F, -1.0F);
		setRotationAngle(tail2, 0.2618F, 0.0F, 0.0F);
		tail.addChild(tail2);
		tail2.cubeList.add(new ModelBox(tail2, 66, 117, -5.0F, -3.7109F, -5.282F, 10, 21, 10, 0.0F, false));

		tail3 = new ModelRenderer(this);
		tail3.setRotationPoint(0.0F, 16.8036F, -8.1568F);
		setRotationAngle(tail3, 0.4363F, 0.0F, 0.0F);
		tail2.addChild(tail3);
		tail3.cubeList.add(new ModelBox(tail3, 78, 148, -4.0F, 1.6026F, 2.3251F, 8, 17, 8, 0.0F, false));
		tail3.cubeList.add(new ModelBox(tail3, 92, 7, -3.0F, 18.6026F, 2.3251F, 6, 10, 6, 0.0F, false));

		l_leg = new ModelRenderer(this);
		l_leg.setRotationPoint(5.0F, -14.0F, 4.0F);
		setRotationAngle(l_leg, -0.2618F, 0.0F, -0.4363F);
		main.addChild(l_leg);
		l_leg.cubeList.add(new ModelBox(l_leg, 132, 0, -5.0F, -2.0F, -5.0F, 10, 13, 11, 0.0F, false));

		l_leg2 = new ModelRenderer(this);
		l_leg2.setRotationPoint(0.4226F, 10.1246F, -0.2346F);
		setRotationAngle(l_leg2, 0.4363F, -0.3491F, 0.3491F);
		l_leg.addChild(l_leg2);
		l_leg2.cubeList.add(new ModelBox(l_leg2, 155, 49, -5.0F, 0.0F, -4.0F, 10, 10, 9, 0.0F, false));

		l_leg3 = new ModelRenderer(this);
		l_leg3.setRotationPoint(-0.2356F, 8.8402F, -0.7742F);
		setRotationAngle(l_leg3, -0.1745F, 0.0F, 0.0F);
		l_leg2.addChild(l_leg3);
		l_leg3.cubeList.add(new ModelBox(l_leg3, 148, 127, -5.0F, 0.0F, -5.0F, 10, 3, 10, 0.0F, false));
		l_leg3.cubeList.add(new ModelBox(l_leg3, 64, 57, -2.0F, 0.0F, -12.0F, 4, 3, 7, 0.0F, false));
		l_leg3.cubeList.add(new ModelBox(l_leg3, 88, 0, -1.5F, 1.0F, 5.0F, 3, 2, 4, 0.0F, false));

		l_leg4 = new ModelRenderer(this);
		l_leg4.setRotationPoint(4.0F, 1.0F, -4.0F);
		setRotationAngle(l_leg4, 0.0F, -0.4363F, 0.0F);
		l_leg3.addChild(l_leg4);
		l_leg4.cubeList.add(new ModelBox(l_leg4, 66, 109, -2.0F, -1.0F, -5.0F, 3, 3, 5, 0.0F, false));

		l_leg5 = new ModelRenderer(this);
		l_leg5.setRotationPoint(-4.0F, 1.0F, -4.0F);
		setRotationAngle(l_leg5, 0.0F, 0.4363F, 0.0F);
		l_leg3.addChild(l_leg5);
		l_leg5.cubeList.add(new ModelBox(l_leg5, 86, 57, -1.0F, -1.0F, -5.0F, 3, 3, 5, 0.0F, false));

		r_leg = new ModelRenderer(this);
		r_leg.setRotationPoint(-5.0F, -14.0F, 4.0F);
		setRotationAngle(r_leg, -0.2618F, 0.0F, 0.4363F);
		main.addChild(r_leg);
		r_leg.cubeList.add(new ModelBox(r_leg, 106, 127, -5.0F, -2.0F, -5.0F, 10, 13, 11, 0.0F, false));

		r_leg2 = new ModelRenderer(this);
		r_leg2.setRotationPoint(-0.4226F, 10.1246F, -0.2346F);
		setRotationAngle(r_leg2, 0.4363F, 0.3491F, -0.3491F);
		r_leg.addChild(r_leg2);
		r_leg2.cubeList.add(new ModelBox(r_leg2, 150, 92, -5.0F, 0.0F, -4.0F, 10, 10, 9, 0.0F, false));

		r_leg3 = new ModelRenderer(this);
		r_leg3.setRotationPoint(0.2356F, 8.8402F, -0.7742F);
		setRotationAngle(r_leg3, -0.1745F, 0.0F, 0.0F);
		r_leg2.addChild(r_leg3);
		r_leg3.cubeList.add(new ModelBox(r_leg3, 58, 0, -5.0F, 0.0F, -5.0F, 10, 3, 10, 0.0F, false));
		r_leg3.cubeList.add(new ModelBox(r_leg3, 42, 57, -2.0F, 0.0F, -12.0F, 4, 3, 7, 0.0F, false));
		r_leg3.cubeList.add(new ModelBox(r_leg3, 57, 57, -1.5F, 1.0F, 5.0F, 3, 2, 4, 0.0F, false));

		r_leg4 = new ModelRenderer(this);
		r_leg4.setRotationPoint(-4.0F, 1.0F, -4.0F);
		setRotationAngle(r_leg4, 0.0F, 0.4363F, 0.0F);
		r_leg3.addChild(r_leg4);
		r_leg4.cubeList.add(new ModelBox(r_leg4, 32, 83, -1.0F, -1.0F, -5.0F, 3, 3, 5, 0.0F, false));

		r_leg5 = new ModelRenderer(this);
		r_leg5.setRotationPoint(4.0F, 1.0F, -4.0F);
		setRotationAngle(r_leg5, 0.0F, -0.4363F, 0.0F);
		r_leg3.addChild(r_leg5);
		r_leg5.cubeList.add(new ModelBox(r_leg5, 16, 83, -2.0F, -1.0F, -5.0F, 3, 3, 5, 0.0F, false));

		l_arm = new ModelRenderer(this);
		l_arm.setRotationPoint(19.0F, -36.0F, 1.0F);
		setRotationAngle(l_arm, 0.0F, 0.0F, -0.5236F);
		main.addChild(l_arm);
		l_arm.cubeList.add(new ModelBox(l_arm, 40, 148, -5.0F, -1.0F, -4.0F, 10, 12, 9, 0.0F, false));

		l_arm2 = new ModelRenderer(this);
		l_arm2.setRotationPoint(-0.366F, 9.634F, 0.0F);
		setRotationAngle(l_arm2, 0.0F, 0.2618F, 0.4363F);
		l_arm.addChild(l_arm2);
		l_arm2.cubeList.add(new ModelBox(l_arm2, 0, 159, -5.0F, 0.0F, -4.0F, 10, 9, 9, 0.0F, false));

		l_arm3 = new ModelRenderer(this);
		l_arm3.setRotationPoint(0.0F, 8.0F, 1.0F);
		setRotationAngle(l_arm3, -0.2618F, 0.0F, 0.0F);
		l_arm2.addChild(l_arm3);
		l_arm3.cubeList.add(new ModelBox(l_arm3, 140, 143, -6.0F, 0.0F, -2.0F, 12, 16, 8, 0.0F, false));

		l_arm4 = new ModelRenderer(this);
		l_arm4.setRotationPoint(0.0F, 19.0F, 2.0F);
		setRotationAngle(l_arm4, -0.3491F, 0.0F, 0.0F);
		l_arm3.addChild(l_arm4);
		l_arm4.cubeList.add(new ModelBox(l_arm4, 168, 111, -5.0F, -4.8191F, -5.0261F, 10, 6, 8, 0.0F, false));

		l_arm5 = new ModelRenderer(this);
		l_arm5.setRotationPoint(0.0F, 0.0F, -3.0F);
		setRotationAngle(l_arm5, -0.4363F, 0.0F, 0.0F);
		l_arm3.addChild(l_arm5);
		l_arm5.cubeList.add(new ModelBox(l_arm5, 70, 173, -3.0F, 0.0F, -3.0F, 6, 9, 5, 0.0F, false));

		l_arm6 = new ModelRenderer(this);
		l_arm6.setRotationPoint(0.0F, 9.7189F, 1.2679F);
		setRotationAngle(l_arm6, 0.6981F, 0.0F, 0.0F);
		l_arm5.addChild(l_arm6);
		l_arm6.cubeList.add(new ModelBox(l_arm6, 40, 135, -3.0F, -3.0355F, -2.2173F, 6, 10, 3, 0.0F, false));

		r_arm = new ModelRenderer(this);
		r_arm.setRotationPoint(-19.0F, -36.0F, 1.0F);
		setRotationAngle(r_arm, 0.0F, 0.0F, 0.5236F);
		main.addChild(r_arm);
		r_arm.cubeList.add(new ModelBox(r_arm, 147, 28, -5.0F, -1.0F, -4.0F, 10, 12, 9, 0.0F, false));

		r_arm2 = new ModelRenderer(this);
		r_arm2.setRotationPoint(0.366F, 9.634F, 0.0F);
		setRotationAngle(r_arm2, 0.0F, -0.2618F, -0.4363F);
		r_arm.addChild(r_arm2);
		r_arm2.cubeList.add(new ModelBox(r_arm2, 110, 158, -5.0F, 0.0F, -4.0F, 10, 9, 9, 0.0F, false));

		r_arm3 = new ModelRenderer(this);
		r_arm3.setRotationPoint(0.0F, 8.0F, 1.0F);
		setRotationAngle(r_arm3, -0.2618F, 0.0F, 0.0F);
		r_arm2.addChild(r_arm3);
		r_arm3.cubeList.add(new ModelBox(r_arm3, 0, 135, -6.0F, 0.0F, -2.0F, 12, 16, 8, 0.0F, false));

		r_arm4 = new ModelRenderer(this);
		r_arm4.setRotationPoint(0.0F, 19.0F, 2.0F);
		setRotationAngle(r_arm4, -0.3491F, 0.0F, 0.0F);
		r_arm3.addChild(r_arm4);
		r_arm4.cubeList.add(new ModelBox(r_arm4, 148, 167, -5.0F, -4.8191F, -5.0261F, 10, 6, 8, 0.0F, false));

		r_arm5 = new ModelRenderer(this);
		r_arm5.setRotationPoint(0.0F, 0.0F, -3.0F);
		setRotationAngle(r_arm5, -0.4363F, 0.0F, 0.0F);
		r_arm3.addChild(r_arm5);
		r_arm5.cubeList.add(new ModelBox(r_arm5, 118, 81, -3.0F, 0.0F, -3.0F, 6, 9, 5, 0.0F, false));

		r_arm6 = new ModelRenderer(this);
		r_arm6.setRotationPoint(0.0F, 9.7189F, 1.2679F);
		setRotationAngle(r_arm6, 0.6981F, 0.0F, 0.0F);
		r_arm5.addChild(r_arm6);
		r_arm6.cubeList.add(new ModelBox(r_arm6, 0, 0, -3.0F, -3.0355F, -2.2173F, 6, 10, 3, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) 
	{
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
		EntityLunacy entity = (EntityLunacy)entityIn;
        if(entity.casting)
        {
        	this.l_arm.rotateAngleX = 0F;
        	this.l_arm.rotateAngleY = 0F;
        	this.l_arm.rotateAngleZ = 0F;
        	this.r_arm.rotateAngleX = 0F;
        	this.r_arm.rotateAngleY = 0F;
        	this.r_arm.rotateAngleZ = 0F;
        	this.l_arm.rotateAngleY = -1.5F;
        	this.l_arm.rotateAngleZ = -2.8F;
        	this.r_arm.rotateAngleY = 1.5F;
        	this.r_arm.rotateAngleZ = 2.85F;
        }
        else
        {
        	this.l_arm.rotateAngleX = 0F;
        	this.l_arm.rotateAngleY = 0F;
        	this.l_arm.rotateAngleZ = 0F;
        	this.r_arm.rotateAngleX = 0F;
        	this.r_arm.rotateAngleY = 0F;
        	this.r_arm.rotateAngleZ = 0F;
	        this.r_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F;
		    this.l_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
		    this.r_arm.rotateAngleZ = 0.5F;
		    this.l_arm.rotateAngleZ = -0.5F;
		    this.r_arm.rotateAngleX += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		    this.l_arm.rotateAngleX -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		    this.r_arm.rotateAngleZ += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		    this.l_arm.rotateAngleZ -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        }
	    
	    this.l_leg.rotateAngleX = -0.25F - MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
        this.r_leg.rotateAngleX = -0.25F + MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
        this.l_leg.rotateAngleY = 0.0F;
        this.r_leg.rotateAngleY = 0.0F;
    }
	
	private float triangleWave(float p_78172_1_, float p_78172_2_)
    {
        return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
    }
}
