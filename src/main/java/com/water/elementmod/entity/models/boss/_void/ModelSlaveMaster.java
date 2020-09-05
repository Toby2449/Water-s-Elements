package com.water.elementmod.entity.models.boss._void;

import com.water.elementmod.entity.boss._void.EntityLunacy;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelSlaveMaster extends ModelBase
{
	private final ModelRenderer main;
	private final ModelRenderer l_arm_base;
	private final ModelRenderer l_arm;
	private final ModelRenderer l_arm2;
	private final ModelRenderer l_arm3;
	private final ModelRenderer pincher;
	private final ModelRenderer pincher2;
	private final ModelRenderer l_arm4;
	private final ModelRenderer r_arm_base;
	private final ModelRenderer r_arm;
	private final ModelRenderer r_arm2;
	private final ModelRenderer r_arm3;
	private final ModelRenderer pincher3;
	private final ModelRenderer pincher4;
	private final ModelRenderer r_arm4;
	private final ModelRenderer torso;
	private final ModelRenderer torso2;
	private final ModelRenderer torso3;
	private final ModelRenderer head;
	private final ModelRenderer armor;
	private final ModelRenderer armor2;
	private final ModelRenderer armor3;
	private final ModelRenderer armor4;
	private final ModelRenderer l_leg;
	private final ModelRenderer l_leg2;
	private final ModelRenderer l_foot;
	private final ModelRenderer l_foot2;
	private final ModelRenderer l_foot3;
	private final ModelRenderer l_foot4;
	private final ModelRenderer r_leg;
	private final ModelRenderer r_leg2;
	private final ModelRenderer r_foot;
	private final ModelRenderer r_foot2;
	private final ModelRenderer r_foot3;
	private final ModelRenderer r_foot4;

	public ModelSlaveMaster() {
		textureWidth = 256;
		textureHeight = 256;

		main = new ModelRenderer(this);
		main.setRotationPoint(1.0F, 23.0F, -4.0F);

		l_arm_base = new ModelRenderer(this);
		l_arm_base.setRotationPoint(14.5F, -52.0F, 2.0F);
		setRotationAngle(l_arm_base, 0.0F, -0.2618F, 0.9599F);
		main.addChild(l_arm_base);
		l_arm_base.cubeList.add(new ModelBox(l_arm_base, 122, 77, -5.5F, -5.3054F, -4.0F, 11, 11, 9, 0.0F, false));

		l_arm = new ModelRenderer(this);
		l_arm.setRotationPoint(1.6516F, -0.3804F, 0.0F);
		setRotationAngle(l_arm, 1.1345F, 0.1745F, -0.2618F);
		l_arm_base.addChild(l_arm);
		l_arm.cubeList.add(new ModelBox(l_arm, 84, 48, 0.2734F, -3.3879F, -5.0F, 13, 8, 10, 0.0F, false));

		l_arm2 = new ModelRenderer(this);
		l_arm2.setRotationPoint(10.9757F, -0.4736F, 2.4647F);
		setRotationAngle(l_arm2, 0.4363F, 0.7854F, 0.0F);
		l_arm.addChild(l_arm2);
		l_arm2.cubeList.add(new ModelBox(l_arm2, 82, 30, 0.8093F, -3.7415F, -5.0F, 14, 8, 10, 0.0F, false));

		l_arm3 = new ModelRenderer(this);
		l_arm3.setRotationPoint(13.4114F, 0.3664F, 0.0F);
		setRotationAngle(l_arm3, -1.0472F, 0.2618F, 0.0F);
		l_arm2.addChild(l_arm3);
		l_arm3.cubeList.add(new ModelBox(l_arm3, 60, 0, 0.3947F, -5.7415F, -3.0F, 17, 12, 8, 0.0F, false));

		pincher = new ModelRenderer(this);
		pincher.setRotationPoint(0.9063F, -0.4226F, -1.0F);
		setRotationAngle(pincher, 0.0F, 0.5236F, 0.0F);
		l_arm3.addChild(pincher);
		pincher.cubeList.add(new ModelBox(pincher, 70, 109, 0.3947F, -1.7415F, -4.0F, 12, 4, 4, 0.0F, false));

		pincher2 = new ModelRenderer(this);
		pincher2.setRotationPoint(8.9344F, 0.1832F, -1.0687F);
		setRotationAngle(pincher2, 0.0F, -0.9599F, 0.0F);
		pincher.addChild(pincher2);
		pincher2.cubeList.add(new ModelBox(pincher2, 74, 141, -0.446F, -1.7415F, -4.0F, 8, 4, 4, 0.0F, false));

		l_arm4 = new ModelRenderer(this);
		l_arm4.setRotationPoint(16.5529F, 8.0049F, 0.0F);
		setRotationAngle(l_arm4, 0.0F, 0.3491F, 0.0F);
		l_arm3.addChild(l_arm4);
		l_arm4.cubeList.add(new ModelBox(l_arm4, 130, 0, -1.1582F, -13.7415F, -2.0F, 6, 12, 6, 0.0F, false));

		r_arm_base = new ModelRenderer(this);
		r_arm_base.setRotationPoint(-14.5F, -52.0F, 2.0F);
		setRotationAngle(r_arm_base, 0.0F, 0.2618F, -0.9599F);
		main.addChild(r_arm_base);
		r_arm_base.cubeList.add(new ModelBox(r_arm_base, 121, 57, -5.5F, -5.3054F, -4.0F, 11, 11, 9, 0.0F, false));

		r_arm = new ModelRenderer(this);
		r_arm.setRotationPoint(-1.6516F, -0.3804F, 0.0F);
		setRotationAngle(r_arm, 1.1345F, -0.1745F, 0.2618F);
		r_arm_base.addChild(r_arm);
		r_arm.cubeList.add(new ModelBox(r_arm, 32, 84, -13.2734F, -3.3879F, -5.0F, 13, 8, 10, 0.0F, false));

		r_arm2 = new ModelRenderer(this);
		r_arm2.setRotationPoint(-10.9757F, -0.4736F, 2.4647F);
		setRotationAngle(r_arm2, 0.4363F, -0.7854F, 0.0F);
		r_arm.addChild(r_arm2);
		r_arm2.cubeList.add(new ModelBox(r_arm2, 74, 74, -14.8093F, -3.7415F, -5.0F, 14, 8, 10, 0.0F, false));

		r_arm3 = new ModelRenderer(this);
		r_arm3.setRotationPoint(-13.4114F, 0.3664F, 0.0F);
		setRotationAngle(r_arm3, -1.0472F, -0.2618F, 0.0F);
		r_arm2.addChild(r_arm3);
		r_arm3.cubeList.add(new ModelBox(r_arm3, 42, 20, -17.3947F, -5.7415F, -3.0F, 17, 12, 8, 0.0F, false));

		pincher3 = new ModelRenderer(this);
		pincher3.setRotationPoint(-0.9063F, -0.4226F, -1.0F);
		setRotationAngle(pincher3, 0.0F, -0.5236F, 0.0F);
		r_arm3.addChild(pincher3);
		pincher3.cubeList.add(new ModelBox(pincher3, 84, 66, -12.3947F, -1.7415F, -4.0F, 12, 4, 4, 0.0F, false));

		pincher4 = new ModelRenderer(this);
		pincher4.setRotationPoint(-8.9344F, 0.1832F, -1.0687F);
		setRotationAngle(pincher4, 0.0F, 0.9599F, 0.0F);
		pincher3.addChild(pincher4);
		pincher4.cubeList.add(new ModelBox(pincher4, 98, 92, -8.3947F, -1.7415F, -4.0F, 8, 4, 4, 0.0F, false));

		r_arm4 = new ModelRenderer(this);
		r_arm4.setRotationPoint(-16.5529F, 8.0049F, 0.0F);
		setRotationAngle(r_arm4, 0.0F, -0.3491F, 0.0F);
		r_arm3.addChild(r_arm4);
		r_arm4.cubeList.add(new ModelBox(r_arm4, 34, 128, -5.3947F, -13.7415F, -2.0F, 6, 12, 6, 0.0F, false));

		torso = new ModelRenderer(this);
		torso.setRotationPoint(0.0F, -26.0F, 10.0F);
		setRotationAngle(torso, 0.2618F, 0.0F, 0.0F);
		main.addChild(torso);
		torso.cubeList.add(new ModelBox(torso, 0, 58, -6.0F, -35.0F, -5.0F, 12, 27, 9, 0.0F, false));
		torso.cubeList.add(new ModelBox(torso, 0, 0, -10.0F, -9.0F, -5.5F, 20, 10, 10, 0.0F, false));

		torso2 = new ModelRenderer(this);
		torso2.setRotationPoint(-6.0F, -8.0F, -2.0F);
		setRotationAngle(torso2, 0.0F, 0.0F, -0.1745F);
		torso.addChild(torso2);
		torso2.cubeList.add(new ModelBox(torso2, 42, 42, -5.0F, -27.0F, -4.0F, 10, 27, 11, 0.0F, false));

		torso3 = new ModelRenderer(this);
		torso3.setRotationPoint(10.0F, -8.0F, -2.0F);
		setRotationAngle(torso3, 0.0F, 0.0F, 0.1745F);
		torso.addChild(torso3);
		torso3.cubeList.add(new ModelBox(torso3, 0, 20, -8.9392F, -26.3054F, -4.0F, 10, 27, 11, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -58.0F, 1.0F);
		main.addChild(head);
		head.cubeList.add(new ModelBox(head, 45, 20, -0.5F, 14.0F, -7.5F, 1, 3, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 31, 26, -3.0F, 12.0F, -6.5F, 1, 4, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 35, 26, 2.0F, 12.0F, -6.5F, 1, 4, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 0, 1.5F, 5.0F, -7.0F, 2, 7, 2, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 42, 42, -3.5F, 5.0F, -7.0F, 2, 7, 2, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 20, -1.0F, 5.0F, -8.0F, 2, 9, 2, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 120, -4.5F, -5.0F, -8.5F, 9, 8, 8, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 120, 24, -4.0F, -3.0F, -8.25F, 8, 8, 8, 0.0F, false));

		armor = new ModelRenderer(this);
		armor.setRotationPoint(0.0F, -4.0F, -3.0F);
		setRotationAngle(armor, -0.3491F, 0.0F, 0.0F);
		head.addChild(armor);
		armor.cubeList.add(new ModelBox(armor, 108, 126, -4.0F, -10.0F, -5.0359F, 8, 11, 7, 0.0F, false));

		armor2 = new ModelRenderer(this);
		armor2.setRotationPoint(0.0F, -8.5566F, 0.4485F);
		setRotationAngle(armor2, -0.5236F, 0.0F, 0.0F);
		armor.addChild(armor2);
		armor2.cubeList.add(new ModelBox(armor2, 124, 42, -3.5F, -7.6934F, -5.0359F, 7, 9, 6, 0.0F, false));

		armor3 = new ModelRenderer(this);
		armor3.setRotationPoint(1.0F, -7.6711F, 0.4837F);
		setRotationAngle(armor3, -0.2618F, 0.0F, 0.0F);
		armor2.addChild(armor3);
		armor3.cubeList.add(new ModelBox(armor3, 93, 144, -3.5F, -4.2724F, -5.0359F, 5, 6, 5, 0.0F, false));

		armor4 = new ModelRenderer(this);
		armor4.setRotationPoint(1.0F, -4.6732F, 0.766F);
		setRotationAngle(armor4, -0.1745F, 0.0F, 0.0F);
		armor3.addChild(armor4);
		armor4.cubeList.add(new ModelBox(armor4, 113, 144, -3.5F, -3.5992F, -5.0359F, 3, 5, 4, 0.0F, false));

		l_leg = new ModelRenderer(this);
		l_leg.setRotationPoint(6.0F, -25.0F, 9.0F);
		setRotationAngle(l_leg, -0.4363F, -0.5236F, 0.0F);
		main.addChild(l_leg);
		l_leg.cubeList.add(new ModelBox(l_leg, 70, 117, -5.0F, -1.8126F, -5.3452F, 10, 15, 9, 0.0F, false));

		l_leg2 = new ModelRenderer(this);
		l_leg2.setRotationPoint(0.0F, 14.0937F, -0.4226F);
		setRotationAngle(l_leg2, 0.6632F, 0.4363F, 0.192F);
		l_leg.addChild(l_leg2);
		l_leg2.cubeList.add(new ModelBox(l_leg2, 30, 106, -5.068F, -1.9611F, -4.1133F, 10, 12, 10, 0.0F, false));

		l_foot = new ModelRenderer(this);
		l_foot.setRotationPoint(0.0508F, 9.0484F, 0.6354F);
		setRotationAngle(l_foot, -0.1745F, 0.0F, 0.0F);
		l_leg2.addChild(l_foot);
		l_foot.cubeList.add(new ModelBox(l_foot, 97, 7, -5.0F, 0.0F, -7.5F, 10, 4, 13, 0.0F, false));
		l_foot.cubeList.add(new ModelBox(l_foot, 50, 0, -2.0F, 1.0F, 5.5F, 4, 3, 3, 0.0F, false));

		l_foot2 = new ModelRenderer(this);
		l_foot2.setRotationPoint(0.0F, 2.0F, -9.0F);
		l_foot.addChild(l_foot2);
		l_foot2.cubeList.add(new ModelBox(l_foot2, 131, 117, -2.0F, -2.0F, -7.5F, 4, 4, 9, 0.0F, false));

		l_foot3 = new ModelRenderer(this);
		l_foot3.setRotationPoint(-3.0F, 2.0F, -9.0F);
		setRotationAngle(l_foot3, 0.0F, 0.2618F, 0.0F);
		l_foot.addChild(l_foot3);
		l_foot3.cubeList.add(new ModelBox(l_foot3, 16, 139, -2.2588F, -1.0F, -5.5341F, 4, 3, 8, 0.0F, false));

		l_foot4 = new ModelRenderer(this);
		l_foot4.setRotationPoint(4.0F, 2.0F, -9.0F);
		setRotationAngle(l_foot4, 0.0F, -0.2618F, 0.0F);
		l_foot.addChild(l_foot4);
		l_foot4.cubeList.add(new ModelBox(l_foot4, 50, 138, -2.7071F, -1.0F, -5.2753F, 4, 3, 8, 0.0F, false));

		r_leg = new ModelRenderer(this);
		r_leg.setRotationPoint(-6.0F, -25.0F, 9.0F);
		setRotationAngle(r_leg, -0.4363F, 0.5236F, 0.0F);
		main.addChild(r_leg);
		r_leg.cubeList.add(new ModelBox(r_leg, 102, 102, -5.0F, -1.8126F, -5.3452F, 10, 15, 9, 0.0F, false));

		r_leg2 = new ModelRenderer(this);
		r_leg2.setRotationPoint(0.0F, 14.0937F, -0.4226F);
		setRotationAngle(r_leg2, 0.6632F, -0.4363F, -0.192F);
		r_leg.addChild(r_leg2);
		r_leg2.cubeList.add(new ModelBox(r_leg2, 0, 94, -4.932F, -1.9611F, -4.1133F, 10, 12, 10, 0.0F, false));

		r_foot = new ModelRenderer(this);
		r_foot.setRotationPoint(-0.0508F, 9.0484F, 0.6354F);
		setRotationAngle(r_foot, -0.1745F, 0.0F, 0.0F);
		r_leg2.addChild(r_foot);
		r_foot.cubeList.add(new ModelBox(r_foot, 65, 92, -5.0F, 0.0F, -7.5F, 10, 4, 13, 0.0F, false));
		r_foot.cubeList.add(new ModelBox(r_foot, 31, 20, -2.0F, 1.0F, 5.5F, 4, 3, 3, 0.0F, false));

		r_foot2 = new ModelRenderer(this);
		r_foot2.setRotationPoint(0.0F, 2.0F, -9.0F);
		r_foot.addChild(r_foot2);
		r_foot2.cubeList.add(new ModelBox(r_foot2, 131, 97, -2.0F, -2.0F, -7.5F, 4, 4, 9, 0.0F, false));

		r_foot3 = new ModelRenderer(this);
		r_foot3.setRotationPoint(3.0F, 2.0F, -9.0F);
		setRotationAngle(r_foot3, 0.0F, -0.2618F, 0.0F);
		r_foot.addChild(r_foot3);
		r_foot3.cubeList.add(new ModelBox(r_foot3, 130, 136, -1.7412F, -1.0F, -5.5341F, 4, 3, 8, 0.0F, false));

		r_foot4 = new ModelRenderer(this);
		r_foot4.setRotationPoint(-4.0F, 2.0F, -9.0F);
		setRotationAngle(r_foot4, 0.0F, 0.2618F, 0.0F);
		r_foot.addChild(r_foot4);
		r_foot4.cubeList.add(new ModelBox(r_foot4, 0, 136, -1.2929F, -1.0F, -5.2753F, 4, 3, 8, 0.0F, false));
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
        this.r_arm.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
	    this.l_arm.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;

	    this.l_leg.rotateAngleX = -0.5F - (MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F);
	    this.r_leg.rotateAngleX = -0.5F + (MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F);
    }
	
	private float triangleWave(float p_78172_1_, float p_78172_2_)
    {
        return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
    }
}
