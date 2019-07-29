package com.water.elementmod.util.handlers;

import com.water.elementmod.entity.EntityFireZombie;
import com.water.elementmod.entity.render.RenderFireZombie;

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
	}
}
