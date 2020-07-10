package com.water.elementmod.entity.models.boss._void;

import com.water.elementmod.entity.boss.fire.EntityFireBoss;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelVoidBlob extends ModelBase {
	private final ModelRenderer main;

	public ModelVoidBlob() {
		textureWidth = 128;
		textureHeight = 128;

		main = new ModelRenderer(this);
		main.setRotationPoint(0.0F, 24.0F, -1.0F);
		main.cubeList.add(new ModelBox(main, 0, 0, -8.0F, 0.0F, -7.0F, 16, 0, 26, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 0, 26, -4.0F, -2.0F, -5.0F, 8, 2, 11, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 27, 27, -3.0F, -4.0F, -4.0F, 6, 2, 8, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 32, 37, -2.0F, -5.0F, -3.0F, 4, 1, 6, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 11, 2, -3.0F, -1.0F, -6.0F, 6, 1, 1, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 0, 20, -3.0F, -1.0F, 6.0F, 6, 1, 2, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 11, 13, -2.0F, -1.0F, 8.0F, 4, 1, 1, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 11, 0, -3.0F, -3.0F, 4.0F, 6, 1, 1, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 11, 6, -2.0F, -3.0F, 5.0F, 4, 1, 1, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 11, 4, -2.0F, -2.0F, 6.0F, 4, 1, 1, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 0, 0, -1.0F, -2.0F, 7.0F, 2, 1, 1, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 11, 11, -3.0F, -3.0F, -5.0F, 6, 1, 1, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 0, 10, -5.0F, -1.0F, -4.0F, 1, 1, 9, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 0, 0, 4.0F, -1.0F, -4.0F, 1, 1, 9, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 41, 44, 4.0F, -2.0F, -3.0F, 1, 1, 7, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 16, 39, 3.0F, -3.0F, -3.0F, 1, 1, 7, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 0, 39, -4.0F, -3.0F, -3.0F, 1, 1, 7, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 25, 44, -5.0F, -2.0F, -3.0F, 1, 1, 7, 0.0F, false));
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
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
		this.main.rotateAngleY = netHeadYaw * 0.017453292F;
		this.main.rotateAngleX = 0;
    }
}