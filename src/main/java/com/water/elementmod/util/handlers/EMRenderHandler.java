package com.water.elementmod.util.handlers;

import com.water.elementmod.entity.EntityFireZombie;
import com.water.elementmod.entity.EntityNatureZombie;
import com.water.elementmod.entity.EntityWaterZombie;
import com.water.elementmod.entity.render.RenderFireZombie;
import com.water.elementmod.entity.render.RenderNatureZombie;
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
	}
}
