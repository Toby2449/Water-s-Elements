package com.water.elementmod.gen;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.water.elementmod.entity.boss.overworld.EntityVoidKnight;
import com.water.elementmod.util.EMConfig;
import com.water.elementmod.util.interfaces.IStructure;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class WorldGenKnightArena extends WorldGenerator implements IStructure
{
	
	public WorldGenKnightArena() {}
	
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) 
	{	
		this.generatorStructure(worldIn, position, rand);
		return true;
	}
	
	public static void generatorStructure(World world, BlockPos pos, Random random)
	{
		MinecraftServer mcServer = world.getMinecraftServer();
		TemplateManager manager = worldServer.getStructureTemplateManager();
		ResourceLocation location = new ResourceLocation(EMConfig.MOD_ID, "knight_arena");
		Template template = manager.get(mcServer, location);
		
		if(template != null)
		{
			for(int k = pos.getX(); k < (pos.getX() + template.getSize().getX()); k++)
			{
				for(int j = pos.getZ(); j < (pos.getZ() + template.getSize().getZ()); j++)
				{
					if(!world.isAirBlock(new BlockPos(k, pos.getY() + 1, j)))
					{
						replaceAirAndLiquidDownwards(template, world, Blocks.STONEBRICK.getDefaultState(), k, pos.getY(), j);
					}
				}
			}
			IBlockState state = world.getBlockState(pos);
			world.notifyBlockUpdate(pos, state, state, 3);
			template.getDataBlocks(pos, settings);
			template.addBlocksToWorldChunk(world, pos, settings);
			Map<BlockPos, String> map = template.getDataBlocks(pos, settings);
			for (Entry<BlockPos, String> entry : map.entrySet())
			{			
				if("knight_location".equals(entry.getValue()))
				{
					BlockPos blockpos = entry.getKey();
					world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 3);
					EntityVoidKnight boss = new EntityVoidKnight(world, blockpos);
					boss.enablePersistence();
					boss.setPosition(blockpos.getX() + 1.0D, blockpos.getY(), blockpos.getZ() + 1.0D);
		            world.spawnEntity(boss);
				}
			}
		}
	}
	
	protected static void replaceAirAndLiquidDownwards(Template template, World worldIn, IBlockState blockstateIn, int x, int y, int z)
    {

        while ((worldIn.isAirBlock(new BlockPos(x, y, z)) || worldIn.getBlockState(new BlockPos(x, y, z)).getMaterial().isLiquid()) && y > 1)
        {
            worldIn.setBlockState(new BlockPos(x, y, z), blockstateIn, 2);
            --y;
        }
    }
}
