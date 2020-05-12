package com.water.elementmod.entity.models.boss.fire;

import com.water.elementmod.entity.boss.fire.EntityFireBoss;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelFireBossMinion extends ModelBase {
	private final ModelRenderer Main;
	private final ModelRenderer Head;
	private final ModelRenderer Torso;
	private final ModelRenderer Torso_armor;
	private final ModelRenderer L_arm;
	private final ModelRenderer R_arm;
	private final ModelRenderer Torso_Bottom;

	public ModelFireBossMinion() {
		textureWidth = 81;
		textureHeight = 80;

		Main = new ModelRenderer(this);
		Main.setRotationPoint(0.0F, -12.0F, 0.0F);

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, 8.5F, -0.5F);
		Main.addChild(Head);
		Head.cubeList.add(new ModelBox(Head, 40, 23, -3.2931F, -6.8707F, -3.0F, 7, 7, 7, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 66, 57, -4.2931F, -1.8707F, -3.0F, 1, 2, 7, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 13, 53, 3.7069F, -1.8707F, -3.0F, 1, 2, 7, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 59, 12, -4.2931F, -6.8707F, -4.0F, 1, 2, 9, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 8, 0, -1.2931F, -5.8707F, -4.0F, 2, 4, 1, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 0, 10, -3.2931F, -6.8707F, -4.0F, 2, 2, 1, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 5, 5, -3.2931F, -6.8707F, 4.0F, 2, 2, 1, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 8, 8, -1.2931F, -5.8707F, 4.0F, 2, 4, 1, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 9, 13, -1.2931F, -6.8707F, 4.0F, 1, 1, 1, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 0, 13, -1.2931F, -6.8707F, -4.0F, 1, 1, 1, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 61, 23, -4.2931F, -1.8707F, 4.0F, 9, 2, 1, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 44, 12, -4.2931F, -1.8707F, -4.0F, 9, 2, 1, 0.0F, false));

		Torso = new ModelRenderer(this);
		Torso.setRotationPoint(0.0F, -2.0F, 0.0F);
		Main.addChild(Torso);
		Torso.cubeList.add(new ModelBox(Torso, 0, 16, -2.7931F, 10.6293F, -6.5F, 6, 6, 13, 0.0F, false));
		Torso.cubeList.add(new ModelBox(Torso, 0, 35, -2.2931F, 16.6293F, -5.5F, 5, 7, 11, 0.0F, false));

		Torso_armor = new ModelRenderer(this);
		Torso_armor.setRotationPoint(0.0F, -6.0F, 0.0F);
		Torso.addChild(Torso_armor);
		Torso_armor.cubeList.add(new ModelBox(Torso_armor, 0, 0, -2.7931F, 16.1293F, -7.5F, 6, 1, 15, 0.0F, false));
		Torso_armor.cubeList.add(new ModelBox(Torso_armor, 27, 1, -3.7931F, 16.6293F, -7.5F, 1, 7, 15, 0.0F, false));
		Torso_armor.cubeList.add(new ModelBox(Torso_armor, 23, 23, 3.2069F, 16.6293F, -7.5F, 1, 7, 15, 0.0F, false));
		Torso_armor.cubeList.add(new ModelBox(Torso_armor, 0, 5, -3.2931F, 26.6293F, -1.5F, 1, 2, 3, 0.0F, false));
		Torso_armor.cubeList.add(new ModelBox(Torso_armor, 0, 0, 2.7069F, 26.6293F, -1.5F, 1, 2, 3, 0.0F, false));
		Torso_armor.cubeList.add(new ModelBox(Torso_armor, 0, 53, -3.2931F, 23.6293F, -5.5F, 1, 1, 11, 0.0F, false));
		Torso_armor.cubeList.add(new ModelBox(Torso_armor, 47, 47, 2.7069F, 23.6293F, -5.5F, 1, 1, 11, 0.0F, false));
		Torso_armor.cubeList.add(new ModelBox(Torso_armor, 0, 65, -3.2931F, 24.6293F, -4.5F, 1, 1, 9, 0.0F, false));
		Torso_armor.cubeList.add(new ModelBox(Torso_armor, 60, 47, 2.7069F, 24.6293F, -4.5F, 1, 1, 9, 0.0F, false));
		Torso_armor.cubeList.add(new ModelBox(Torso_armor, 11, 66, -3.2931F, 25.6293F, -4.5F, 1, 1, 9, 0.0F, false));
		Torso_armor.cubeList.add(new ModelBox(Torso_armor, 55, 64, 2.7069F, 25.6293F, -4.5F, 1, 1, 9, 0.0F, false));

		L_arm = new ModelRenderer(this);
		L_arm.setRotationPoint(0.0F, 12.0F, -7.0F);
		Main.addChild(L_arm);
		L_arm.cubeList.add(new ModelBox(L_arm, 44, 0, -3.2931F, -3.3707F, -5.5F, 7, 6, 6, 0.0F, false));
		L_arm.cubeList.add(new ModelBox(L_arm, 44, 59, -2.2931F, 2.6293F, -4.5F, 5, 9, 5, 0.0F, false));

		R_arm = new ModelRenderer(this);
		R_arm.setRotationPoint(0.0F, 12.0F, 6.0F);
		Main.addChild(R_arm);
		R_arm.cubeList.add(new ModelBox(R_arm, 32, 45, -3.2931F, -3.3707F, 0.5F, 7, 6, 6, 0.0F, false));
		R_arm.cubeList.add(new ModelBox(R_arm, 24, 57, -2.2931F, 2.6293F, 0.5F, 5, 9, 5, 0.0F, false));

		Torso_Bottom = new ModelRenderer(this);
		Torso_Bottom.setRotationPoint(0.0F, 0.0F, 0.0F);
		Main.addChild(Torso_Bottom);
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 34, 12, -2.2931F, 21.6293F, -4.5F, 1, 2, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 8, 21, -2.2931F, 21.6293F, -3.5F, 1, 4, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 0, 35, -2.2931F, 21.6293F, -2.5F, 1, 1, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 33, 23, -2.2931F, 21.6293F, -1.5F, 1, 2, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 34, 5, -2.2931F, 21.6293F, -0.5F, 1, 1, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 31, 3, -0.2931F, 21.6293F, 4.5F, 1, 2, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 0, 21, -0.2931F, 21.6293F, -5.5F, 1, 4, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 52, 37, -1.2931F, 21.6293F, -4.5F, 3, 1, 9, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 8, 16, 0.7069F, 21.6293F, 4.5F, 1, 4, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 31, 0, 0.7069F, 21.6293F, -5.5F, 1, 2, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 11, 5, 1.7069F, 21.6293F, -4.5F, 1, 2, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 25, 23, 1.7069F, 21.6293F, 3.5F, 1, 1, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 5, 12, 1.7069F, 21.6293F, 4.5F, 1, 2, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 0, 26, 1.7069F, 21.6293F, 2.5F, 1, 2, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 25, 25, 1.7069F, 21.6293F, 1.5F, 1, 3, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 4, 26, 1.7069F, 21.6293F, 0.5F, 1, 2, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 34, 2, 1.7069F, 21.6293F, -5.5F, 1, 1, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 0, 16, 1.7069F, 21.6293F, -3.5F, 1, 4, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 27, 0, 1.7069F, 21.6293F, -2.5F, 1, 3, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 8, 26, 1.7069F, 21.6293F, -1.5F, 1, 2, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 4, 16, 1.7069F, 21.6293F, -0.5F, 1, 4, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 29, 23, -2.2931F, 21.6293F, 0.5F, 1, 3, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 33, 9, -2.2931F, 21.6293F, 1.5F, 1, 2, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 4, 21, -2.2931F, 21.6293F, 2.5F, 1, 4, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 27, 8, -2.2931F, 21.6293F, 3.5F, 1, 3, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 32, 26, -2.2931F, 21.6293F, 4.5F, 1, 2, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 27, 4, -1.2931F, 21.6293F, 4.5F, 1, 3, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 30, 11, -2.2931F, 21.6293F, -5.5F, 1, 3, 1, 0.0F, false));
		Torso_Bottom.cubeList.add(new ModelBox(Torso_Bottom, 31, 6, -1.2931F, 21.6293F, -5.5F, 1, 2, 1, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) 
	{
		Main.render(f5);
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
        this.Head.rotateAngleZ = netHeadYaw * 0.017453292F;
        this.Head.rotateAngleZ = -(headPitch * 0.017453292F);
    }
}