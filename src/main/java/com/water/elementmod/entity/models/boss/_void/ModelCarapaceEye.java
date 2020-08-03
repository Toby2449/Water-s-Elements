package com.water.elementmod.entity.models.boss._void;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCarapaceEye extends ModelBase
{
	private final ModelRenderer main;

	public ModelCarapaceEye() {
		textureWidth = 64;
		textureHeight = 64;

		main = new ModelRenderer(this);
		main.setRotationPoint(0.0F, 24.0F, 0.0F);
		main.cubeList.add(new ModelBox(main, 0, 0, -8.0F, -2.0F, -7.0F, 16, 2, 14, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 0, 16, -5.0F, -3.5F, -5.0F, 10, 1, 10, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 30, 19, -7.0F, -2.0F, -8.0F, 14, 2, 1, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 30, 16, -7.0F, -2.0F, 7.0F, 14, 2, 1, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 28, 28, -7.0F, -4.0F, -6.0F, 2, 2, 12, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 0, 27, 5.0F, -4.0F, -6.0F, 2, 2, 12, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 30, 22, -5.0F, -4.0F, 5.0F, 10, 2, 2, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 16, 27, -5.0F, -4.0F, -7.0F, 10, 2, 2, 0.0F, false));
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
