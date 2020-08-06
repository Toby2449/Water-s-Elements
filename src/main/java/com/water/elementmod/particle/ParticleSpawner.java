package com.water.elementmod.particle;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;

public class ParticleSpawner 
{
	private static Minecraft mc = Minecraft.getMinecraft();
	
	public static Particle spawnParticle(EnumCustomParticleTypes type, double par2, double par4, double par6, double par8, double par10, double par12)
	{
		if (mc != null && mc.getRenderViewEntity() != null && mc.effectRenderer != null)
		{
			int var14 = mc.gameSettings.particleSetting;

			if (var14 == 1 && mc.world.rand.nextInt(3) == 0)
			{
				var14 = 2;
			}

			double var15 = mc.getRenderViewEntity().posX - par2;
			double var17 = mc.getRenderViewEntity().posY - par4;
			double var19 = mc.getRenderViewEntity().posZ - par6;
			Particle var21 = null;
			double var22 = 50.0D;
			
			if (var15 * var15 + var17 * var17 + var19 * var19 > var22 * var22)
			{
				return null;
			}
			else if (var14 > 1)
			{
				return null;
			}
			else
			{
				if (type == EnumCustomParticleTypes.GREEN_POLLEN)
				{
					var21 = new ParticleGreenPollen(mc.world, par2, par4, par6, par8, par10, par12);
				} 
				else if (type == EnumCustomParticleTypes.DARKGREEN_POLLEN)
				{
					var21 = new ParticleDarkGreenPollen(mc.world, par2, par4, par6, par8, par10, par12);
				}
				else if (type == EnumCustomParticleTypes.SMALL_HEART)
				{
					var21 = new ParticleSmallHeart(mc.world, par2, par4, par6, par8, par10, par12);
				}
				else if (type == EnumCustomParticleTypes.LEAF)
				{
					var21 = new ParticleLeaf(mc.world, par2, par4, par6, par8, par10, par12);
				}
				else if (type == EnumCustomParticleTypes.LEAF_AURA)
				{
					var21 = new ParticleLeafAura(mc.world, par2, par4, par6, par8, par10, par12);
				}
				else if (type == EnumCustomParticleTypes.LAVA)
				{
					var21 = new ParticleLava(mc.world, par2, par4, par6, par8, par10, par12);
				}
				else if (type == EnumCustomParticleTypes.VOID_PORTAL)
				{
					var21 = new ParticleVoidPortal(mc.world, par2, par4, par6, par8, par10, par12);
				}
				else if (type == EnumCustomParticleTypes.VOID_PORTAL_BLOCK)
				{
					var21 = new ParticleVoidPortalBlock(mc.world, par2, par4, par6, par8, par10, par12);
				}
				else if (type == EnumCustomParticleTypes.BLUE_SMOKE)
				{
					var21 = new ParticleBlueSmoke(mc.world, par2, par4, par6, par8, par10, par12);
				}
				else if (type == EnumCustomParticleTypes.DARK_PURPLE_SMOKE)
				{
					var21 = new ParticleDarkPurpleSmoke(mc.world, par2, par4, par6, par8, par10, par12);
				}
				else if (type == EnumCustomParticleTypes.DARK_PURPLE_SMOKE_BLOCK)
				{
					var21 = new ParticleDarkPurpleSmokeBlock(mc.world, par2, par4, par6, par8, par10, par12);
				}
				else if (type == EnumCustomParticleTypes.ANGUISH)
				{
					var21 = new ParticleAnguish(mc.world, par2, par4, par6, Material.PORTAL);
				}

				mc.effectRenderer.addEffect(var21);
				return var21;
			}
		}
		return null;
	}
}
