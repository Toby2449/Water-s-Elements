package com.water.elementmod.entity.models.boss._void;

import com.water.elementmod.entity.boss._void.EntityCarapace;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelAnguish extends ModelBase
{
	private final ModelRenderer main;
	private final ModelRenderer corner1;
	private final ModelRenderer corner4;
	private final ModelRenderer corner3;
	private final ModelRenderer corner2;

	public ModelAnguish() 
	{
		textureWidth = 512;
		textureHeight = 512;

		main = new ModelRenderer(this);
		main.setRotationPoint(17.0F, 24.0F, 17.0F);
		main.cubeList.add(new ModelBox(main, 70, 70, 8.0F, 0.0F, -42.0F, 35, 0, 50, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 0, 50, -77.0F, 0.0F, -42.0F, 35, 0, 50, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 115, 35, -42.0F, 0.0F, -77.0F, 50, 0, 35, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 115, 0, -42.0F, 0.0F, 8.0F, 50, 0, 35, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 0, 0, -42.0F, 0.0F, -42.0F, 50, 0, 50, 0.0F, false));

		corner1 = new ModelRenderer(this);
		corner1.setRotationPoint(0.0F, 0.0F, 1.0F);
		setRotationAngle(corner1, 0.0F, 0.7854F, 0.0F);
		main.addChild(corner1);
		corner1.cubeList.add(new ModelBox(corner1, 125, 150, -24.25F, 0.0F, 10.2F, 50, 0, 25, 0.0F, false));

		corner4 = new ModelRenderer(this);
		corner4.setRotationPoint(-35.0F, 0.0F, 0.0F);
		setRotationAngle(corner4, 0.0F, 0.7854F, 0.0F);
		main.addChild(corner4);
		corner4.cubeList.add(new ModelBox(corner4, 0, 100, -35.2F, 0.0F, -24.25F, 25, 0, 50, 0.0F, false));

		corner3 = new ModelRenderer(this);
		corner3.setRotationPoint(1.0F, 0.0F, -34.0F);
		setRotationAngle(corner3, 0.0F, 0.7854F, 0.0F);
		main.addChild(corner3);
		corner3.cubeList.add(new ModelBox(corner3, 50, 120, 10.2F, 0.0F, -25.75F, 25, 0, 50, 0.0F, false));

		corner2 = new ModelRenderer(this);
		corner2.setRotationPoint(-34.0F, 0.0F, -35.0F);
		setRotationAngle(corner2, 0.0F, 0.7854F, 0.0F);
		main.addChild(corner2);
		corner2.cubeList.add(new ModelBox(corner2, 125, 125, -25.75F, 0.0F, -35.2F, 50, 0, 25, 0.0F, false));
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
