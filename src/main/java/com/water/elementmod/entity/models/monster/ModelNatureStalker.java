package com.water.elementmod.entity.models.monster;

import org.lwjgl.opengl.GL11;

import com.water.elementmod.entity.boss.nature.EntityNatureBoss;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.util.math.MathHelper;

public class ModelNatureStalker extends ModelBase
{
	private final ModelRenderer Main;
	private final ModelRenderer Head;
	private final ModelRenderer Torso;
	private final ModelRenderer L_leg;
	private final ModelRenderer lower_leg_rot;
	private final ModelRenderer R_leg;
	private final ModelRenderer lower_leg_rot2;
	private final ModelRenderer L_arm;
	private final ModelRenderer R_arm;

	public ModelNatureStalker() {
		textureWidth = 32;
		textureHeight = 32;

		Main = new ModelRenderer(this);
		Main.setRotationPoint(0.0F, 25.0F, 0.0F);

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, -19.0F, -0.5F);
		Main.addChild(Head);
		Head.cubeList.add(new ModelBox(Head, 0, 0, -2.5F, -5.0F, -2.4531F, 5, 5, 5, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 15, 0, -1.5F, -7.0F, -0.4531F, 3, 2, 0, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 15, 2, 2.5F, -4.0F, -0.4531F, 2, 3, 0, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 20, 13, -4.5F, -5.0F, 0.5469F, 2, 3, 0, 0.0F, false));

		Torso = new ModelRenderer(this);
		Torso.setRotationPoint(0.0F, -11.0F, 0.0F);
		setRotationAngle(Torso, 0.1745F, 0.0F, 0.0F);
		Main.addChild(Torso);
		Torso.cubeList.add(new ModelBox(Torso, 0, 10, -3.5F, -8.0F, -0.9531F, 7, 4, 3, 0.0F, false));
		Torso.cubeList.add(new ModelBox(Torso, 0, 17, -2.5F, -4.0F, -0.9531F, 5, 4, 3, 0.0F, false));

		L_leg = new ModelRenderer(this);
		L_leg.setRotationPoint(-1.0F, -12.0F, -0.25F);
		setRotationAngle(L_leg, -0.2618F, 0.0F, 0.0F);
		Main.addChild(L_leg);
		L_leg.cubeList.add(new ModelBox(L_leg, 24, 13, -1.5F, 0.5F, 0.0469F, 2, 5, 2, 0.0F, false));
		L_leg.cubeList.add(new ModelBox(L_leg, 24, 20, -3.5F, 2.5F, 1.0469F, 2, 3, 0, 0.0F, false));

		lower_leg_rot = new ModelRenderer(this);
		lower_leg_rot.setRotationPoint(1.0F, 5.5F, 0.0F);
		setRotationAngle(lower_leg_rot, 0.2618F, 0.0F, 0.0F);
		L_leg.addChild(lower_leg_rot);
		lower_leg_rot.cubeList.add(new ModelBox(lower_leg_rot, 8, 24, -2.5F, 0.0F, 0.0469F, 2, 6, 2, 0.0F, false));

		R_leg = new ModelRenderer(this);
		R_leg.setRotationPoint(2.0F, -11.5F, -0.25F);
		setRotationAngle(R_leg, -0.2618F, 0.0F, 0.0F);
		Main.addChild(R_leg);
		R_leg.cubeList.add(new ModelBox(R_leg, 24, 24, -1.5F, 0.0F, 0.0469F, 2, 5, 2, 0.0F, false));

		lower_leg_rot2 = new ModelRenderer(this);
		lower_leg_rot2.setRotationPoint(-1.0F, 5.0F, 0.0F);
		setRotationAngle(lower_leg_rot2, 0.2618F, 0.0F, 0.0F);
		R_leg.addChild(lower_leg_rot2);
		lower_leg_rot2.cubeList.add(new ModelBox(lower_leg_rot2, 0, 24, -0.5F, 0.0F, 0.0469F, 2, 6, 2, 0.0F, false));
		lower_leg_rot2.cubeList.add(new ModelBox(lower_leg_rot2, 28, 6, 1.5F, 2.0F, 1.2969F, 2, 3, 0, 0.0F, false));

		L_arm = new ModelRenderer(this);
		L_arm.setRotationPoint(-3.0F, -18.0F, -1.0F);
		Main.addChild(L_arm);
		L_arm.cubeList.add(new ModelBox(L_arm, 20, 0, -2.5F, -1.0F, -0.9531F, 2, 11, 2, 0.0F, false));
		L_arm.cubeList.add(new ModelBox(L_arm, 0, 0, -4.5F, 2.0F, 0.0469F, 2, 5, 0, 0.0F, false));

		R_arm = new ModelRenderer(this);
		R_arm.setRotationPoint(4.0F, -18.0F, -1.0F);
		Main.addChild(R_arm);
		R_arm.cubeList.add(new ModelBox(R_arm, 16, 17, -0.5F, -1.0F, -0.9531F, 2, 11, 2, 0.0F, false));
		R_arm.cubeList.add(new ModelBox(R_arm, 28, 0, 1.5F, 2.0F, 0.0469F, 2, 6, 0, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Main.render(f5);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.Head.rotateAngleX = headPitch * 0.017453292F;
        this.L_leg.rotateAngleX = (this.triangleWave(limbSwing, 13.0F) * limbSwingAmount) - 0.2618F;
        this.R_leg.rotateAngleX = -(this.triangleWave(limbSwing, 13.0F) * limbSwingAmount) - 0.2618F;
        
        float f = MathHelper.sin(this.swingProgress * (float)Math.PI);
        float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float)Math.PI);
        this.R_arm.rotateAngleZ = 0.0F;
        this.L_arm.rotateAngleZ = 0.0F;
        this.R_arm.rotateAngleY = -(0.1F - f * 0.6F);
        this.L_arm.rotateAngleY = 0.1F - f * 0.6F;
        float f2 = (float)Math.PI / (180F);
        this.R_arm.rotateAngleX += f * 1.2F - f1 * 0.4F;
        this.L_arm.rotateAngleX += f * 1.2F - f1 * 0.4F;
        this.R_arm.rotateAngleX = -2.0F + 1.75F * this.triangleWave((float)this.swingProgress, 10.0F);
        this.L_arm.rotateAngleX = -2.0F + 1.75F * this.triangleWave((float)this.swingProgress, 10.0F);
    }
	
	private float triangleWave(float p_78172_1_, float p_78172_2_)
    {
        return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
    }
}
