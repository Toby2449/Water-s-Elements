package com.water.elementmod.util.handlers;

import com.water.elementmod.entity.EntityFireSkeleton;
import com.water.elementmod.entity.EntityFireZombie;
import com.water.elementmod.entity.EntityNatureSkeleton;
import com.water.elementmod.entity.EntityNatureZombie;
import com.water.elementmod.entity.EntityWaterSkeleton;
import com.water.elementmod.entity.EntityWaterZombie;
import com.water.elementmod.entity.arrow.EntityFireArrow;
import com.water.elementmod.entity.arrow.EntityNatureArrow;
import com.water.elementmod.entity.arrow.EntityWaterArrow;
import com.water.elementmod.entity.arrow.RenderFireArrow;
import com.water.elementmod.entity.arrow.RenderNatureArrow;
import com.water.elementmod.entity.arrow.RenderWaterArrow;
import com.water.elementmod.entity.render.RenderFireSkeleton;
import com.water.elementmod.entity.render.RenderFireZombie;
import com.water.elementmod.entity.render.RenderNatureSkeleton;
import com.water.elementmod.entity.render.RenderNatureZombie;
import com.water.elementmod.entity.render.RenderWaterSkeleton;
import com.water.elementmod.entity.render.RenderWaterZombie;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class EMRenderHandler
{
	public static void registerEntityRenders()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityFireZombie.class, new IRenderFactory<EntityFireZombie>()
		{
			@Override
			public Render<? super EntityFireZombie> createRenderFor(RenderManager manager)
			{
				return new RenderFireZombie(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityWaterZombie.class, new IRenderFactory<EntityWaterZombie>()
		{
			@Override
			public Render<? super EntityWaterZombie> createRenderFor(RenderManager manager)
			{
				return new RenderWaterZombie(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityNatureZombie.class, new IRenderFactory<EntityNatureZombie>()
		{
			@Override
			public Render<? super EntityNatureZombie> createRenderFor(RenderManager manager)
			{
				return new RenderNatureZombie(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityFireSkeleton.class, new IRenderFactory<EntityFireSkeleton>()
		{
			@Override
			public Render<? super EntityFireSkeleton> createRenderFor(RenderManager manager)
			{
				return new RenderFireSkeleton(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityWaterSkeleton.class, new IRenderFactory<EntityWaterSkeleton>()
		{
			@Override
			public Render<? super EntityWaterSkeleton> createRenderFor(RenderManager manager)
			{
				return new RenderWaterSkeleton(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityNatureSkeleton.class, new IRenderFactory<EntityNatureSkeleton>()
		{
			@Override
			public Render<? super EntityNatureSkeleton> createRenderFor(RenderManager manager)
			{
				return new RenderNatureSkeleton(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityNatureArrow.class, new IRenderFactory<EntityNatureArrow>()
		{
			@Override
			public Render<? super EntityNatureArrow> createRenderFor(RenderManager manager)
			{
				return new RenderNatureArrow(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityWaterArrow.class, new IRenderFactory<EntityWaterArrow>()
		{
			@Override
			public Render<? super EntityWaterArrow> createRenderFor(RenderManager manager)
			{
				return new RenderWaterArrow(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityFireArrow.class, new IRenderFactory<EntityFireArrow>()
		{
			@Override
			public Render<? super EntityFireArrow> createRenderFor(RenderManager manager)
			{
				return new RenderFireArrow(manager);
			}
		});
	}
}
