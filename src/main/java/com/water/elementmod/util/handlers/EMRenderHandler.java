package com.water.elementmod.util.handlers;

import com.water.elementmod.entity.boss.EntityPhotoSynthesizerCrystal;
import com.water.elementmod.entity.boss.EntityNatureBoss;
import com.water.elementmod.entity.boss.EntityNatureBossMinion;
import com.water.elementmod.entity.monster.EntityFireSkeleton;
import com.water.elementmod.entity.monster.EntityFireZombie;
import com.water.elementmod.entity.monster.EntityNatureSkeleton;
import com.water.elementmod.entity.monster.EntityNatureStalker;
import com.water.elementmod.entity.monster.EntityNatureZombie;
import com.water.elementmod.entity.monster.EntityWaterSkeleton;
import com.water.elementmod.entity.monster.EntityWaterZombie;
import com.water.elementmod.entity.projectile.EntityFireArrow;
import com.water.elementmod.entity.projectile.EntityNatureArrow;
import com.water.elementmod.entity.projectile.EntityPoisonBall;
import com.water.elementmod.entity.projectile.EntityWaterArrow;
import com.water.elementmod.entity.render.boss.RenderPhotoSynthesizerCrystal;
import com.water.elementmod.entity.render.boss.RenderNatureBoss;
import com.water.elementmod.entity.render.boss.RenderNatureBossMinion;
import com.water.elementmod.entity.render.monster.RenderFireSkeleton;
import com.water.elementmod.entity.render.monster.RenderFireZombie;
import com.water.elementmod.entity.render.monster.RenderNatureSkeleton;
import com.water.elementmod.entity.render.monster.RenderNatureStalker;
import com.water.elementmod.entity.render.monster.RenderNatureZombie;
import com.water.elementmod.entity.render.monster.RenderWaterSkeleton;
import com.water.elementmod.entity.render.monster.RenderWaterZombie;
import com.water.elementmod.entity.render.projectile.RenderFireArrow;
import com.water.elementmod.entity.render.projectile.RenderNatureArrow;
import com.water.elementmod.entity.render.projectile.RenderPoisonBall;
import com.water.elementmod.entity.render.projectile.RenderWaterArrow;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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
		RenderingRegistry.registerEntityRenderingHandler(EntityNatureBoss.class, new IRenderFactory<EntityNatureBoss>()
		{
			@Override
			public Render<? super EntityNatureBoss> createRenderFor(RenderManager manager)
			{
				return new RenderNatureBoss(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityPoisonBall.class, new IRenderFactory<EntityPoisonBall>()
		{
			@Override
			public Render<? super EntityPoisonBall> createRenderFor(RenderManager manager)
			{
				return new RenderPoisonBall(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityPhotoSynthesizerCrystal.class, new IRenderFactory<EntityPhotoSynthesizerCrystal>()
		{
			@Override
			public Render<? super EntityPhotoSynthesizerCrystal> createRenderFor(RenderManager manager)
			{
				return new RenderPhotoSynthesizerCrystal(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityNatureBossMinion.class, new IRenderFactory<EntityNatureBossMinion>()
		{
			@Override
			public Render<? super EntityNatureBossMinion> createRenderFor(RenderManager manager)
			{
				return new RenderNatureBossMinion(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityNatureStalker.class, new IRenderFactory<EntityNatureStalker>()
		{
			@Override
			public Render<? super EntityNatureStalker> createRenderFor(RenderManager manager)
			{
				return new RenderNatureStalker(manager);
			}
		});
	}
}
