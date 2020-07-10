package com.water.elementmod.entity.models.boss.nature;

import org.lwjgl.opengl.GL11;

import com.water.elementmod.entity.boss.nature.EntityNatureBoss;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;

public class ModelNatureBoss extends ModelBase
{
	private final ModelRenderer main;
	private final ModelRenderer head;
	private final ModelRenderer torso;
	private final ModelRenderer upper_torso;
	private final ModelRenderer upper_head;
	private final ModelRenderer decal;
	private final ModelRenderer lower_torso;
	private final ModelRenderer r_arm;
	private final ModelRenderer r_lower_arm;
	private final ModelRenderer r_hand;
	private final ModelRenderer l_arm;
	private final ModelRenderer l_lower_arm;
	private final ModelRenderer l_hand;
	private final ModelRenderer l_hand2;
	private final ModelRenderer l_leg;
	private final ModelRenderer r_leg;

	public ModelNatureBoss() {
		textureWidth = 128;
		textureHeight = 128;

		main = new ModelRenderer(this);
		main.setRotationPoint(0.0F, 24.0F, 0.0F);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		main.addChild(head);
		head.cubeList.add(new ModelBox(head, 24, 53, 1.0F, -53.0F, -7.0F, 5, 6, 3, 0.0F, false));

		torso = new ModelRenderer(this);
		torso.setRotationPoint(0.0F, 0.0F, 0.0F);
		main.addChild(torso);

		upper_torso = new ModelRenderer(this);
		upper_torso.setRotationPoint(0.0F, -31.0F, 0.0F);
		torso.addChild(upper_torso);
		upper_torso.cubeList.add(new ModelBox(upper_torso, 0, 21, -10.0F, -18.0F, -6.0F, 20, 4, 12, 0.0F, false));
		upper_torso.cubeList.add(new ModelBox(upper_torso, 0, 0, -9.0F, -14.0F, -6.0F, 18, 9, 12, 0.0F, false));
		upper_torso.cubeList.add(new ModelBox(upper_torso, 0, 37, -8.0F, -5.0F, -6.0F, 16, 4, 12, 0.0F, false));
		upper_torso.cubeList.add(new ModelBox(upper_torso, 48, 0, -6.0F, -24.0F, -4.0F, 15, 4, 8, 0.0F, false));
		upper_torso.cubeList.add(new ModelBox(upper_torso, 44, 37, -10.0F, -20.0F, -5.0F, 20, 2, 10, 0.0F, false));

		upper_head = new ModelRenderer(this);
		upper_head.setRotationPoint(5.5F, -19.0F, -0.5F);
		setRotationAngle(upper_head, 0.0F, 0.0F, 0.6981F);
		upper_torso.addChild(upper_head);
		upper_head.cubeList.add(new ModelBox(upper_head, 87, 58, -8.5F, -6.0F, -2.5F, 8, 8, 5, 0.0F, false));
		upper_head.cubeList.add(new ModelBox(upper_head, 37, 53, -8.5265F, -8.8102F, 0.5F, 8, 3, 0, 0.0F, false));

		decal = new ModelRenderer(this);
		decal.setRotationPoint(0.0F, 0.0F, 0.0F);
		upper_torso.addChild(decal);
		decal.cubeList.add(new ModelBox(decal, 92, 49, -10.0F, -29.0F, 0.0F, 13, 9, 0, 0.0F, false));

		lower_torso = new ModelRenderer(this);
		lower_torso.setRotationPoint(0.0F, -26.0F, 0.0F);
		torso.addChild(lower_torso);
		lower_torso.cubeList.add(new ModelBox(lower_torso, 48, 49, -7.0F, -6.0F, -4.0F, 14, 6, 8, 0.0F, false));

		r_arm = new ModelRenderer(this);
		r_arm.setRotationPoint(-9.0F, -48.0F, -1.0F);
		setRotationAngle(r_arm, 0.4363F, 0.0F, 0.6109F);
		main.addChild(r_arm);
		r_arm.cubeList.add(new ModelBox(r_arm, 0, 74, -4.0F, 0.0F, -3.0F, 7, 12, 7, 0.0F, false));
		r_arm.cubeList.add(new ModelBox(r_arm, 34, 62, -6.3617F, 1.1097F, 0.4848F, 3, 8, 0, 0.0F, false));

		r_lower_arm = new ModelRenderer(this);
		r_lower_arm.setRotationPoint(-0.8263F, 10.7487F, -0.0987F);
		setRotationAngle(r_lower_arm, -0.6981F, -0.1745F, -0.6109F);
		r_arm.addChild(r_lower_arm);
		r_lower_arm.cubeList.add(new ModelBox(r_lower_arm, 92, 71, -3.1737F, 0.0F, -3.5F, 5, 10, 5, 0.0F, false));
		r_lower_arm.cubeList.add(new ModelBox(r_lower_arm, 0, 53, -8.1564F, 2.0577F, -0.9112F, 5, 7, 0, 0.0F, false));

		r_hand = new ModelRenderer(this);
		r_hand.setRotationPoint(0.0F, 0.0F, 0.0F);
		r_lower_arm.addChild(r_hand);
		r_hand.cubeList.add(new ModelBox(r_hand, 86, 86, -4.1737F, 9.4842F, -3.7973F, 7, 8, 6, 0.0F, false));

		l_arm = new ModelRenderer(this);
		l_arm.setRotationPoint(9.0F, -48.0F, -1.0F);
		setRotationAngle(l_arm, 0.1745F, 0.0F, -0.4363F);
		main.addChild(l_arm);
		l_arm.cubeList.add(new ModelBox(l_arm, 66, 66, -3.0F, 0.0F, -3.0F, 6, 16, 7, 0.0F, false));
		l_arm.cubeList.add(new ModelBox(l_arm, 0, 0, 2.3768F, 2.2959F, 1.0F, 6, 11, 0, 0.0F, false));

		l_lower_arm = new ModelRenderer(this);
		l_lower_arm.setRotationPoint(-0.5026F, 15.3568F, -0.1067F);
		setRotationAngle(l_lower_arm, -0.1745F, 0.0873F, 0.4363F);
		l_arm.addChild(l_lower_arm);
		l_lower_arm.cubeList.add(new ModelBox(l_lower_arm, 50, 84, -1.7292F, -0.7524F, -2.0734F, 4, 16, 5, 0.0F, false));
		l_lower_arm.cubeList.add(new ModelBox(l_lower_arm, 0, 37, 2.2831F, 1.2316F, 0.1069F, 4, 11, 0, 0.0F, false));

		l_hand = new ModelRenderer(this);
		l_hand.setRotationPoint(1.9478F, 15.2468F, 0.9496F);
		l_lower_arm.addChild(l_hand);
		l_hand.cubeList.add(new ModelBox(l_hand, 0, 21, -1.348F, -0.0019F, -1.7577F, 2, 10, 2, 0.0F, false));

		l_hand2 = new ModelRenderer(this);
		l_hand2.setRotationPoint(-1.0522F, 15.2468F, 1.9496F);
		setRotationAngle(l_hand2, 0.0F, 0.0F, 0.0873F);
		l_lower_arm.addChild(l_hand2);
		l_hand2.cubeList.add(new ModelBox(l_hand2, 8, 21, -0.3735F, 0.0071F, -0.7957F, 1, 5, 1, 0.0F, false));

		l_leg = new ModelRenderer(this);
		l_leg.setRotationPoint(5.0F, -26.0F, 0.0F);
		main.addChild(l_leg);
		l_leg.cubeList.add(new ModelBox(l_leg, 52, 12, -4.0F, -1.0F, -5.0F, 8, 10, 10, 0.0F, false));
		l_leg.cubeList.add(new ModelBox(l_leg, 88, 6, -3.0F, 9.0F, -3.0F, 6, 6, 6, 0.0F, false));
		l_leg.cubeList.add(new ModelBox(l_leg, 0, 53, -3.0F, 15.0F, -5.0F, 7, 11, 10, 0.0F, false));
		l_leg.cubeList.add(new ModelBox(l_leg, 44, 37, 4.0F, 17.0F, 0.0F, 5, 7, 0, 0.0F, false));

		r_leg = new ModelRenderer(this);
		r_leg.setRotationPoint(-6.0F, -26.0F, 0.0F);
		main.addChild(r_leg);
		r_leg.cubeList.add(new ModelBox(r_leg, 34, 63, -3.0F, -1.0F, -5.0F, 6, 10, 10, 0.0F, false));
		r_leg.cubeList.add(new ModelBox(r_leg, 28, 83, -2.0F, 9.0F, -3.0F, 5, 13, 6, 0.0F, false));
		r_leg.cubeList.add(new ModelBox(r_leg, 48, 0, -6.0F, 11.0F, 0.0F, 4, 8, 0, 0.0F, false));
		r_leg.cubeList.add(new ModelBox(r_leg, 80, 24, -3.0F, 22.0F, -4.0F, 7, 4, 8, 0.0F, false));
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
