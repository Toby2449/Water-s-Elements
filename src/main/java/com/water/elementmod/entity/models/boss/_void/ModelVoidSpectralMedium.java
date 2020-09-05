package com.water.elementmod.entity.models.boss._void;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelVoidSpectralMedium extends ModelBase
{
	private final ModelRenderer main;
	private final ModelRenderer lower;
	private final ModelRenderer lower2;
	private final ModelRenderer head2;
	private final ModelRenderer head;
	private final ModelRenderer l_arm;
	private final ModelRenderer l_arm2;

	public ModelVoidSpectralMedium() {
		textureWidth = 64;
		textureHeight = 64;
		
		main = new ModelRenderer(this);
		main.setRotationPoint(0.0F, 24.0F, 0.0F);
		main.cubeList.add(new ModelBox(main, 0, 0, -6.0F, -20.0F, -4.0F, 11, 10, 7, 0.0F, false));

		lower = new ModelRenderer(this);
		lower.setRotationPoint(0.0F, -11.0F, -1.0F);
		setRotationAngle(lower, 0.3491F, 0.0F, 0.0F);
		main.addChild(lower);
		lower.cubeList.add(new ModelBox(lower, 0, 30, -4.5F, 0.0F, -2.0F, 8, 6, 5, 0.0F, false));

		lower2 = new ModelRenderer(this);
		lower2.setRotationPoint(0.0F, 6.6642F, 0.767F);
		setRotationAngle(lower2, 0.7854F, 0.0F, 0.0F);
		lower.addChild(lower2);
		lower2.cubeList.add(new ModelBox(lower2, 36, 0, -3.0F, -2.0642F, -1.517F, 5, 5, 3, 0.0F, false));

		head2 = new ModelRenderer(this);
		head2.setRotationPoint(0.0F, -20.0F, -3.0F);
		setRotationAngle(head2, -0.3491F, 0.0F, 0.0F);
		main.addChild(head2);
		head2.cubeList.add(new ModelBox(head2, 0, 17, -5.0F, -2.6F, -1.0F, 9, 3, 7, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -22.0F, -3.0F);
		setRotationAngle(head, 0.1745F, 0.0F, 0.0F);
		main.addChild(head);
		head.cubeList.add(new ModelBox(head, 24, 24, -4.0F, -1.0F, 0.0F, 7, 3, 8, 0.0F, false));

		l_arm = new ModelRenderer(this);
		l_arm.setRotationPoint(1.0F, -17.0F, -1.0F);
		setRotationAngle(l_arm, 0.0F, 0.0F, -0.7854F);
		main.addChild(l_arm);
		l_arm.cubeList.add(new ModelBox(l_arm, 26, 35, 0.0F, 0.0F, -2.0F, 5, 5, 5, 0.0F, false));

		l_arm2 = new ModelRenderer(this);
		l_arm2.setRotationPoint(-2.0F, -17.0F, -1.0F);
		setRotationAngle(l_arm2, 0.0F, 0.0F, 0.7854F);
		main.addChild(l_arm2);
		l_arm2.cubeList.add(new ModelBox(l_arm2, 31, 12, -5.0F, 0.0F, -2.0F, 5, 5, 5, 0.0F, false));
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
}
