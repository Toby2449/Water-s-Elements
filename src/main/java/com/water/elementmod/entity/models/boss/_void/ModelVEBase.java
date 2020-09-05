package com.water.elementmod.entity.models.boss._void;

import com.water.elementmod.entity.boss._void.EntityCarapace;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelVEBase extends ModelBase
{
	private final ModelRenderer main;
	private final ModelRenderer spike1;
	private final ModelRenderer spike2;
	private final ModelRenderer spike3;
	private final ModelRenderer spike19;
	private final ModelRenderer spike20;
	private final ModelRenderer spike21;
	private final ModelRenderer spike22;
	private final ModelRenderer spike23;
	private final ModelRenderer spike24;
	private final ModelRenderer spike13;
	private final ModelRenderer spike14;
	private final ModelRenderer spike15;
	private final ModelRenderer spike16;
	private final ModelRenderer spike17;
	private final ModelRenderer spike18;
	private final ModelRenderer spike4;
	private final ModelRenderer spike5;
	private final ModelRenderer spike6;
	private final ModelRenderer spike7;
	private final ModelRenderer spike8;
	private final ModelRenderer spike9;
	private final ModelRenderer spike10;
	private final ModelRenderer spike11;
	private final ModelRenderer spike12;

	public ModelVEBase() {
		textureWidth = 128;
		textureHeight = 128;

		main = new ModelRenderer(this);
		main.setRotationPoint(0.0F, 24.0F, 0.0F);

		spike1 = new ModelRenderer(this);
		spike1.setRotationPoint(32.0F, 0.0F, 0.0F);
		setRotationAngle(spike1, 0.0F, 0.0F, 0.7854F);
		main.addChild(spike1);
		spike1.cubeList.add(new ModelBox(spike1, 60, 0, -5.0F, -16.0F, -2.6706F, 9, 16, 6, 0.0F, false));

		spike2 = new ModelRenderer(this);
		spike2.setRotationPoint(0.0F, -16.0F, 0.0F);
		setRotationAngle(spike2, 0.0F, 0.0F, -0.3491F);
		spike1.addChild(spike2);
		spike2.cubeList.add(new ModelBox(spike2, 82, 39, -4.0F, -11.0F, -1.6706F, 7, 13, 4, 0.0F, false));

		spike3 = new ModelRenderer(this);
		spike3.setRotationPoint(0.0F, -10.0F, 0.0F);
		setRotationAngle(spike3, 0.0F, 0.0F, -0.3491F);
		spike2.addChild(spike3);
		spike3.cubeList.add(new ModelBox(spike3, 0, 97, -2.0F, -12.0F, -0.6706F, 4, 12, 2, 0.0F, false));

		spike19 = new ModelRenderer(this);
		spike19.setRotationPoint(25.0F, 0.0F, -23.0F);
		setRotationAngle(spike19, 0.5672F, 0.48F, 0.9599F);
		main.addChild(spike19);
		spike19.cubeList.add(new ModelBox(spike19, 0, 22, -6.5438F, -13.2572F, -2.9881F, 9, 16, 6, 0.0F, false));

		spike20 = new ModelRenderer(this);
		spike20.setRotationPoint(-2.5438F, -13.2572F, -3.3175F);
		setRotationAngle(spike20, 0.0F, 0.0F, -0.3491F);
		spike19.addChild(spike20);
		spike20.cubeList.add(new ModelBox(spike20, 60, 39, -2.0F, -11.0F, 1.3294F, 7, 13, 4, 0.0F, false));

		spike21 = new ModelRenderer(this);
		spike21.setRotationPoint(0.0F, -10.0F, 0.0F);
		setRotationAngle(spike21, 0.0F, 0.0F, -0.3491F);
		spike20.addChild(spike21);
		spike21.cubeList.add(new ModelBox(spike21, 88, 88, 1.0F, -12.0F, 2.3294F, 4, 12, 2, 0.0F, false));

		spike22 = new ModelRenderer(this);
		spike22.setRotationPoint(-25.0F, 0.0F, -23.0F);
		setRotationAngle(spike22, 0.5672F, -0.48F, -0.9599F);
		main.addChild(spike22);
		spike22.cubeList.add(new ModelBox(spike22, 0, 0, -2.4562F, -13.2572F, -2.9881F, 9, 16, 6, 0.0F, false));

		spike23 = new ModelRenderer(this);
		spike23.setRotationPoint(2.5438F, -13.2572F, -3.3175F);
		setRotationAngle(spike23, 0.0F, 0.0F, 0.3491F);
		spike22.addChild(spike23);
		spike23.cubeList.add(new ModelBox(spike23, 60, 22, -5.0F, -11.0F, 1.3294F, 7, 13, 4, 0.0F, false));

		spike24 = new ModelRenderer(this);
		spike24.setRotationPoint(0.0F, -10.0F, 0.0F);
		setRotationAngle(spike24, 0.0F, 0.0F, 0.3491F);
		spike23.addChild(spike24);
		spike24.cubeList.add(new ModelBox(spike24, 0, 83, -5.0F, -12.0F, 2.3294F, 4, 12, 2, 0.0F, false));

		spike13 = new ModelRenderer(this);
		spike13.setRotationPoint(-23.0F, 0.0F, 22.0F);
		setRotationAngle(spike13, -0.5672F, 0.48F, -0.9599F);
		main.addChild(spike13);
		spike13.cubeList.add(new ModelBox(spike13, 30, 0, -4.0F, -16.0F, -2.6706F, 9, 16, 6, 0.0F, false));

		spike14 = new ModelRenderer(this);
		spike14.setRotationPoint(0.0F, -16.0F, 0.0F);
		setRotationAngle(spike14, 0.0F, 0.0F, 0.3491F);
		spike13.addChild(spike14);
		spike14.cubeList.add(new ModelBox(spike14, 22, 74, -3.0F, -11.0F, -1.6706F, 7, 13, 4, 0.0F, false));

		spike15 = new ModelRenderer(this);
		spike15.setRotationPoint(0.0F, -10.0F, 0.0F);
		setRotationAngle(spike15, 0.0F, 0.0F, 0.3491F);
		spike14.addChild(spike15);
		spike15.cubeList.add(new ModelBox(spike15, 90, 0, -2.0F, -12.0F, -0.6706F, 4, 12, 2, 0.0F, false));

		spike16 = new ModelRenderer(this);
		spike16.setRotationPoint(23.0F, 0.0F, 22.0F);
		setRotationAngle(spike16, -0.5672F, -0.48F, 0.9599F);
		main.addChild(spike16);
		spike16.cubeList.add(new ModelBox(spike16, 30, 30, -5.0F, -16.0F, -2.6706F, 9, 16, 6, 0.0F, false));

		spike17 = new ModelRenderer(this);
		spike17.setRotationPoint(0.0F, -16.0F, 0.0F);
		setRotationAngle(spike17, 0.0F, 0.0F, -0.3491F);
		spike16.addChild(spike17);
		spike17.cubeList.add(new ModelBox(spike17, 0, 66, -4.0F, -11.0F, -1.6706F, 7, 13, 4, 0.0F, false));

		spike18 = new ModelRenderer(this);
		spike18.setRotationPoint(0.0F, -10.0F, 0.0F);
		setRotationAngle(spike18, 0.0F, 0.0F, -0.3491F);
		spike17.addChild(spike18);
		spike18.cubeList.add(new ModelBox(spike18, 12, 89, -2.0F, -12.0F, -0.6706F, 4, 12, 2, 0.0F, false));

		spike4 = new ModelRenderer(this);
		spike4.setRotationPoint(-32.0F, 0.0F, 0.0F);
		setRotationAngle(spike4, 0.0F, 0.0F, -0.7854F);
		main.addChild(spike4);
		spike4.cubeList.add(new ModelBox(spike4, 60, 60, -4.0F, -16.0F, -2.6706F, 9, 16, 6, 0.0F, false));

		spike5 = new ModelRenderer(this);
		spike5.setRotationPoint(0.0F, -16.0F, 0.0F);
		setRotationAngle(spike5, 0.0F, 0.0F, 0.3491F);
		spike4.addChild(spike5);
		spike5.cubeList.add(new ModelBox(spike5, 82, 22, -3.0F, -11.0F, -1.6706F, 7, 13, 4, 0.0F, false));

		spike6 = new ModelRenderer(this);
		spike6.setRotationPoint(0.0F, -10.0F, 0.0F);
		setRotationAngle(spike6, 0.0F, 0.0F, 0.3491F);
		spike5.addChild(spike6);
		spike6.cubeList.add(new ModelBox(spike6, 24, 91, -2.0F, -12.0F, -0.6706F, 4, 12, 2, 0.0F, false));

		spike7 = new ModelRenderer(this);
		spike7.setRotationPoint(0.0F, 19.0F, 0.0F);
		setRotationAngle(spike7, 1.5708F, -0.7854F, -1.5708F);
		main.addChild(spike7);
		spike7.cubeList.add(new ModelBox(spike7, 30, 52, -13.1924F, -52.1924F, -2.6706F, 9, 16, 6, 0.0F, false));

		spike8 = new ModelRenderer(this);
		spike8.setRotationPoint(0.0F, -16.0F, 0.0F);
		setRotationAngle(spike8, 0.0F, 0.0F, 0.3491F);
		spike7.addChild(spike8);
		spike8.cubeList.add(new ModelBox(spike8, 66, 82, -24.0165F, -41.8657F, -1.6706F, 7, 13, 4, 0.0F, false));

		spike9 = new ModelRenderer(this);
		spike9.setRotationPoint(0.0F, -10.0F, 0.0F);
		setRotationAngle(spike9, 0.0F, 0.0F, 0.3491F);
		spike8.addChild(spike9);
		spike9.cubeList.add(new ModelBox(spike9, 90, 70, -32.3058F, -33.8162F, -0.6706F, 4, 12, 2, 0.0F, false));

		spike10 = new ModelRenderer(this);
		spike10.setRotationPoint(0.0F, 19.0F, 0.0F);
		setRotationAngle(spike10, -1.5708F, 0.7854F, -1.5708F);
		main.addChild(spike10);
		spike10.cubeList.add(new ModelBox(spike10, 0, 44, -13.1924F, -52.1924F, -2.6706F, 9, 16, 6, 0.0F, false));

		spike11 = new ModelRenderer(this);
		spike11.setRotationPoint(0.0F, -16.0F, 0.0F);
		setRotationAngle(spike11, 0.0F, 0.0F, 0.3491F);
		spike10.addChild(spike11);
		spike11.cubeList.add(new ModelBox(spike11, 44, 82, -24.0165F, -41.8657F, -1.6706F, 7, 13, 4, 0.0F, false));

		spike12 = new ModelRenderer(this);
		spike12.setRotationPoint(0.0F, -10.0F, 0.0F);
		setRotationAngle(spike12, 0.0F, 0.0F, 0.3491F);
		spike11.addChild(spike12);
		spike12.cubeList.add(new ModelBox(spike12, 90, 56, -32.3058F, -33.8162F, -0.6706F, 4, 12, 2, 0.0F, false));
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
