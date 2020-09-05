package com.water.elementmod.entity.models.boss._void;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelVoidSpectralLarge extends ModelBase
{
	private final ModelRenderer main;
	private final ModelRenderer r_arm;
	private final ModelRenderer l_arm;
	private final ModelRenderer lower;
	private final ModelRenderer lower2;
	private final ModelRenderer lower3;
	private final ModelRenderer head;
	private final ModelRenderer head2;
	private final ModelRenderer chest_1;
	private final ModelRenderer chest;
	private final ModelRenderer chest2;

	public ModelVoidSpectralLarge() {
		textureWidth = 128;
		textureHeight = 128;

		main = new ModelRenderer(this);
		main.setRotationPoint(0.0F, 24.0F, 1.0F);
		main.cubeList.add(new ModelBox(main, 0, 0, -8.0F, -38.0F, -4.0F, 16, 22, 6, 0.0F, false));

		r_arm = new ModelRenderer(this);
		r_arm.setRotationPoint(-9.0F, -32.0F, -5.0F);
		setRotationAngle(r_arm, 0.0F, 0.0F, 0.7854F);
		main.addChild(r_arm);
		r_arm.cubeList.add(new ModelBox(r_arm, 0, 46, 6.7279F, -18.7279F, 1.5F, 11, 15, 5, 0.0F, false));

		l_arm = new ModelRenderer(this);
		l_arm.setRotationPoint(-9.0F, -32.0F, -1.0F);
		setRotationAngle(l_arm, 0.0F, 0.0F, 0.7854F);
		main.addChild(l_arm);
		l_arm.cubeList.add(new ModelBox(l_arm, 32, 59, -6.0F, -6.0F, -2.5F, 13, 11, 5, 0.0F, false));

		lower = new ModelRenderer(this);
		lower.setRotationPoint(0.0F, -17.0F, 0.0F);
		setRotationAngle(lower, -0.4363F, 0.0F, 0.0F);
		main.addChild(lower);
		lower.cubeList.add(new ModelBox(lower, 68, 68, -6.0F, 0.0F, -4.0F, 12, 10, 6, 0.0F, false));

		lower2 = new ModelRenderer(this);
		lower2.setRotationPoint(0.0F, 8.5183F, 0.662F);
		setRotationAngle(lower2, -0.5236F, 0.0F, 0.0F);
		lower.addChild(lower2);
		lower2.cubeList.add(new ModelBox(lower2, 69, 54, -4.0F, 0.7317F, -3.162F, 8, 8, 5, 0.0F, false));

		lower3 = new ModelRenderer(this);
		lower3.setRotationPoint(0.0F, 7.7828F, 0.6543F);
		setRotationAngle(lower3, -0.5236F, 0.0F, 0.0F);
		lower2.addChild(lower3);
		lower3.cubeList.add(new ModelBox(lower3, 69, 17, -2.0F, 0.2989F, -1.6163F, 4, 6, 3, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -39.0F, -1.0F);
		setRotationAngle(head, 0.8727F, 0.0F, 0.0F);
		main.addChild(head);
		head.cubeList.add(new ModelBox(head, 0, 28, -7.0F, -9.0F, -3.5F, 14, 12, 6, 0.0F, false));

		head2 = new ModelRenderer(this);
		head2.setRotationPoint(0.0F, -10.7511F, -1.1888F);
		setRotationAngle(head2, 0.5236F, 0.0F, 0.0F);
		head.addChild(head2);
		head2.cubeList.add(new ModelBox(head2, 0, 66, -5.0F, -5.4989F, -2.7112F, 10, 9, 5, 0.0F, false));

		chest_1 = new ModelRenderer(this);
		chest_1.setRotationPoint(0.0F, -17.0F, -2.0F);
		setRotationAngle(chest_1, -0.1745F, 0.0F, 0.0F);
		main.addChild(chest_1);
		chest_1.cubeList.add(new ModelBox(chest_1, 40, 40, -6.0F, -14.0F, -2.0F, 12, 14, 5, 0.0F, false));

		chest = new ModelRenderer(this);
		chest.setRotationPoint(4.0F, -29.0F, 0.0F);
		setRotationAngle(chest, 0.0F, 0.2618F, 0.0F);
		main.addChild(chest);
		chest.cubeList.add(new ModelBox(chest, 44, 0, -5.3F, -10.0F, -3.0F, 11, 10, 7, 0.0F, false));

		chest2 = new ModelRenderer(this);
		chest2.setRotationPoint(-4.0F, -29.0F, 0.0F);
		setRotationAngle(chest2, 0.0F, -0.2618F, 0.0F);
		main.addChild(chest2);
		chest2.cubeList.add(new ModelBox(chest2, 40, 21, -5.8F, -10.0F, -3.0F, 11, 10, 7, 0.0F, false));
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
