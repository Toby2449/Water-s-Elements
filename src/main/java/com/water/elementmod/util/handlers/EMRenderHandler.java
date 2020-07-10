package com.water.elementmod.util.handlers;

import com.water.elementmod.EMCoreBlocks;
import com.water.elementmod.entity.boss.fire.EntityFireBoss;
import com.water.elementmod.entity.boss.fire.EntityFireBossMinion;
import com.water.elementmod.entity.boss.fire.EntityFireCrystal;
import com.water.elementmod.entity.boss.fire.EntityFireGuardian;
import com.water.elementmod.entity.boss.fire.EntityFireGuardianCastle;
import com.water.elementmod.entity.boss.nature.EntityNatureBoss;
import com.water.elementmod.entity.boss.nature.EntityNatureBossMinion;
import com.water.elementmod.entity.boss.nature.EntityPhotoSynthesizerCrystal;
import com.water.elementmod.entity.boss.overworld.EntityVoidBlob;
import com.water.elementmod.entity.boss.overworld.EntityVoidKnight;
import com.water.elementmod.entity.boss.overworld.EntityVoidSmasher;
import com.water.elementmod.entity.boss.water.EntityWaterBoss;
import com.water.elementmod.entity.boss.water.EntityWaterBossClone;
import com.water.elementmod.entity.boss.water.EntityWaterBossMeleeMinion;
import com.water.elementmod.entity.boss.water.EntityWaterBossRangedMinion;
import com.water.elementmod.entity.boss.water.EntityWaterTrash;
import com.water.elementmod.entity.friendly.EntityAlyxFire;
import com.water.elementmod.entity.friendly.EntityAlyxNature;
import com.water.elementmod.entity.friendly.EntityAlyxWater;
import com.water.elementmod.entity.monster.EntityFireSkeleton;
import com.water.elementmod.entity.monster.EntityFireZombie;
import com.water.elementmod.entity.monster.EntityNatureSkeleton;
import com.water.elementmod.entity.monster.EntityNatureStalker;
import com.water.elementmod.entity.monster.EntityNatureZombie;
import com.water.elementmod.entity.monster.EntityWaterSkeleton;
import com.water.elementmod.entity.monster.EntityWaterZombie;
import com.water.elementmod.entity.projectile.EntityFireArrow;
import com.water.elementmod.entity.projectile.EntityIceBall;
import com.water.elementmod.entity.projectile.EntityIceBall2;
import com.water.elementmod.entity.projectile.EntityNatureArrow;
import com.water.elementmod.entity.projectile.EntityPoisonBall;
import com.water.elementmod.entity.projectile.EntityWaterArrow;
import com.water.elementmod.entity.render.boss.fire.RenderFireBoss;
import com.water.elementmod.entity.render.boss.fire.RenderFireBossMinion;
import com.water.elementmod.entity.render.boss.fire.RenderFireCrystal;
import com.water.elementmod.entity.render.boss.fire.RenderFireGuardian;
import com.water.elementmod.entity.render.boss.fire.RenderFireGuardianCastle;
import com.water.elementmod.entity.render.boss.nature.RenderNatureBoss;
import com.water.elementmod.entity.render.boss.nature.RenderNatureBossMinion;
import com.water.elementmod.entity.render.boss.nature.RenderPhotoSynthesizerCrystal;
import com.water.elementmod.entity.render.boss.overworld.RenderVoidBlob;
import com.water.elementmod.entity.render.boss.overworld.RenderVoidKnight;
import com.water.elementmod.entity.render.boss.overworld.RenderVoidSmasher;
import com.water.elementmod.entity.render.boss.water.RenderWaterBoss;
import com.water.elementmod.entity.render.boss.water.RenderWaterBossClone;
import com.water.elementmod.entity.render.boss.water.RenderWaterBossMeleeMinion;
import com.water.elementmod.entity.render.boss.water.RenderWaterBossRangedMinion;
import com.water.elementmod.entity.render.boss.water.RenderWaterTrash;
import com.water.elementmod.entity.render.friendly.RenderAlyxFire;
import com.water.elementmod.entity.render.friendly.RenderAlyxNature;
import com.water.elementmod.entity.render.friendly.RenderAlyxWater;
import com.water.elementmod.entity.render.monster.RenderFireSkeleton;
import com.water.elementmod.entity.render.monster.RenderFireZombie;
import com.water.elementmod.entity.render.monster.RenderNatureSkeleton;
import com.water.elementmod.entity.render.monster.RenderNatureStalker;
import com.water.elementmod.entity.render.monster.RenderNatureZombie;
import com.water.elementmod.entity.render.monster.RenderWaterSkeleton;
import com.water.elementmod.entity.render.monster.RenderWaterZombie;
import com.water.elementmod.entity.render.projectile.RenderFireArrow;
import com.water.elementmod.entity.render.projectile.RenderIceBall;
import com.water.elementmod.entity.render.projectile.RenderIceBall2;
import com.water.elementmod.entity.render.projectile.RenderNatureArrow;
import com.water.elementmod.entity.render.projectile.RenderPoisonBall;
import com.water.elementmod.entity.render.projectile.RenderWaterArrow;
import com.water.elementmod.util.EMConfig;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
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
		
		RenderingRegistry.registerEntityRenderingHandler(EntityAlyxFire.class, new IRenderFactory<EntityAlyxFire>()
		{
			@Override
			public Render<? super EntityAlyxFire> createRenderFor(RenderManager manager)
			{
				return new RenderAlyxFire(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityFireBoss.class, new IRenderFactory<EntityFireBoss>()
		{
			@Override
			public Render<? super EntityFireBoss> createRenderFor(RenderManager manager)
			{
				return new RenderFireBoss(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityFireCrystal.class, new IRenderFactory<EntityFireCrystal>()
		{
			@Override
			public Render<? super EntityFireCrystal> createRenderFor(RenderManager manager)
			{
				return new RenderFireCrystal(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityFireBossMinion.class, new IRenderFactory<EntityFireBossMinion>()
		{
			@Override
			public Render<? super EntityFireBossMinion> createRenderFor(RenderManager manager)
			{
				return new RenderFireBossMinion(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityFireGuardian.class, new IRenderFactory<EntityFireGuardian>()
		{
			@Override
			public Render<? super EntityFireGuardian> createRenderFor(RenderManager manager)
			{
				return new RenderFireGuardian(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityFireGuardianCastle.class, new IRenderFactory<EntityFireGuardianCastle>()
		{
			@Override
			public Render<? super EntityFireGuardianCastle> createRenderFor(RenderManager manager)
			{
				return new RenderFireGuardianCastle(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityWaterBoss.class, new IRenderFactory<EntityWaterBoss>()
		{
			@Override
			public Render<? super EntityWaterBoss> createRenderFor(RenderManager manager)
			{
				return new RenderWaterBoss(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityWaterBossClone.class, new IRenderFactory<EntityWaterBossClone>()
		{
			@Override
			public Render<? super EntityWaterBossClone> createRenderFor(RenderManager manager)
			{
				return new RenderWaterBossClone(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityWaterBossRangedMinion.class, new IRenderFactory<EntityWaterBossRangedMinion>()
		{
			@Override
			public Render<? super EntityWaterBossRangedMinion> createRenderFor(RenderManager manager)
			{
				return new RenderWaterBossRangedMinion(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityIceBall.class, new IRenderFactory<EntityIceBall>()
		{
			@Override
			public Render<? super EntityIceBall> createRenderFor(RenderManager manager)
			{
				return new RenderIceBall(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityIceBall2.class, new IRenderFactory<EntityIceBall2>()
		{
			@Override
			public Render<? super EntityIceBall2> createRenderFor(RenderManager manager)
			{
				return new RenderIceBall2(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityWaterBossMeleeMinion.class, new IRenderFactory<EntityWaterBossMeleeMinion>()
		{
			@Override
			public Render<? super EntityWaterBossMeleeMinion> createRenderFor(RenderManager manager)
			{
				return new RenderWaterBossMeleeMinion(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityAlyxWater.class, new IRenderFactory<EntityAlyxWater>()
		{
			@Override
			public Render<? super EntityAlyxWater> createRenderFor(RenderManager manager)
			{
				return new RenderAlyxWater(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityAlyxNature.class, new IRenderFactory<EntityAlyxNature>()
		{
			@Override
			public Render<? super EntityAlyxNature> createRenderFor(RenderManager manager)
			{
				return new RenderAlyxNature(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityWaterTrash.class, new IRenderFactory<EntityWaterTrash>()
		{
			@Override
			public Render<? super EntityWaterTrash> createRenderFor(RenderManager manager)
			{
				return new RenderWaterTrash(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityVoidKnight.class, new IRenderFactory<EntityVoidKnight>()
		{
			@Override
			public Render<? super EntityVoidKnight> createRenderFor(RenderManager manager)
			{
				return new RenderVoidKnight(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityVoidBlob.class, new IRenderFactory<EntityVoidBlob>()
		{
			@Override
			public Render<? super EntityVoidBlob> createRenderFor(RenderManager manager)
			{
				return new RenderVoidBlob(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityVoidSmasher.class, new IRenderFactory<EntityVoidSmasher>()
		{
			@Override
			public Render<? super EntityVoidSmasher> createRenderFor(RenderManager manager)
			{
				return new RenderVoidSmasher(manager);
			}
		});
	}
	
	public static void registerCustomMeshesAndStates()
	{
		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(EMCoreBlocks.PURE_EVIL_BLOCK), new ItemMeshDefinition()
		{
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack)
			{
				return new ModelResourceLocation(EMConfig.MOD_ID + ":pure_evil", "fluid");
			}
		});
		
		ModelLoader.setCustomStateMapper(EMCoreBlocks.PURE_EVIL_BLOCK, new StateMapperBase()
		{
			@Override
			public ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				return new ModelResourceLocation(EMConfig.MOD_ID + ":pure_evil", "fluid");
			}
		});
	}
}
