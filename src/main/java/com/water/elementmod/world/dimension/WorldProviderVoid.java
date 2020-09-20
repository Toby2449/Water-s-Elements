package com.water.elementmod.world.dimension;

import javax.annotation.Nullable;

import com.water.elementmod.EMCoreDimensions;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderVoid extends WorldProvider
{
	@Override
	protected void init()
	{
		this.biomeProvider = new BiomeProviderSingle(Biomes.VOID);
		this.hasSkyLight = false;
		this.doesWaterVaporize = true;
	}
	
	@Override
	public DimensionType getDimensionType() 
	{
		return EMCoreDimensions.VOID;
	}
	
	@Override
	public Vec3d getFogColor(float p_76562_1_, float p_76562_2_)
	{
		return new Vec3d(0.184, 0, 0.651);
	}
	
	/**
     * Creates the light to brightness table
     */
    protected void generateLightBrightnessTable()
    {
        for (int i = 0; i <= 15; ++i)
        {
            float f1 = 1.3F - (float)i / 15.0F;
            this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * 0.9F + 0.1F;
        }
    }
	
	@Override
	public boolean isSurfaceWorld()
	{
		return false;
	}
	
	@Override
	public boolean canRespawnHere()
	{
		return false;
	}
	
	@Override
	public boolean canCoordinateBeSpawn(int x, int z)
    {
        return true;
    }
	
	@Override
	public BlockPos getSpawnCoordinate()
    {
        return new BlockPos(1, 58, 1);
    }
	
	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks)
    {
        return 0.0F;
    }
	
	@Override
	@Nullable
    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks)
    {
        return null;
    }
	
	@Override
	public boolean doesXZShowFog(int x, int z)
	{
		return super.doesXZShowFog(x, z);
	}
	
	@Override
	public IChunkGenerator createChunkGenerator()
	{
		return new ChunkGeneratorVoid(world, this.getSeed(), false, "3;minecraft:bedrock,37*em:void_block,5*em:pure_evil;");
	}
}
