package com.water.elementmod.entity.models.boss._void;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelVoidSpectralSmall extends ModelBase
{
	private final ModelRenderer main;

	public ModelVoidSpectralSmall() {
		textureWidth = 128;
		textureHeight = 128;

		main = new ModelRenderer(this);
		main.setRotationPoint(0.0F, 27.0F, 0.0F);
		main.cubeList.add(new ModelBox(main, 0, 0, -5.0F, -15.0F, -6.0F, 10, 10, 12, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 26, 33, -3.0F, -13.0F, 6.0F, 6, 6, 1, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 0, 9, -1.0F, -11.0F, 7.0F, 2, 2, 1, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 12, 33, -3.0F, -13.0F, -7.0F, 6, 6, 1, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 30, 30, -5.0F, -16.0F, -5.0F, 10, 1, 10, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 32, 0, -3.0F, -17.0F, -3.0F, 6, 1, 6, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 0, 0, -1.0F, -18.0F, -1.0F, 2, 1, 2, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 30, 22, -3.0F, -4.0F, -3.0F, 6, 1, 6, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 0, 22, -5.0F, -5.0F, -5.0F, 10, 1, 10, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 22, 41, -6.0F, -15.0F, -5.0F, 1, 10, 10, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 44, 7, -7.0F, -13.0F, -3.0F, 1, 6, 6, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 4, 5, -8.0F, -11.0F, -1.0F, 1, 2, 2, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 44, 44, 6.0F, -13.0F, -3.0F, 1, 6, 6, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 0, 3, 7.0F, -11.0F, -1.0F, 1, 2, 2, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 0, 33, 5.0F, -15.0F, -5.0F, 1, 10, 10, 0.0F, false));
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
