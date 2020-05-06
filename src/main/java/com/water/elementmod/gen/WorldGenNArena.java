package com.water.elementmod.gen;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.water.elementmod.entity.boss.nature.EntityNatureBoss;
import com.water.elementmod.entity.boss.nature.EntityPhotoSynthesizerCrystal;
import com.water.elementmod.entity.monster.EntityNatureStalker;
import com.water.elementmod.util.EMConfig;
import com.water.elementmod.util.handlers.EMLootTableHandler;
import com.water.elementmod.util.interfaces.IStructure;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class WorldGenNArena extends WorldGenerator implements IStructure
{
	
	public WorldGenNArena() {}
	
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
		ResourceLocation location = new ResourceLocation(EMConfig.MOD_ID, "n_arena");
		Template template = manager.get(mcServer, location);
		
		if(template != null)
		{
			for(int k = pos.getX(); k < (pos.getX() + template.getSize().getX()); k++)
			{
				for(int j = pos.getZ(); j < (pos.getZ() + template.getSize().getZ()); j++)
				{
					replaceAirAndLiquidDownwards(template, world, Blocks.STONE.getDefaultState(), k, pos.getY(), j);
				}
			}
			IBlockState state = world.getBlockState(pos);
			world.notifyBlockUpdate(pos, state, state, 3);
			template.getDataBlocks(pos, settings);
			template.addBlocksToWorldChunk(world, pos, settings);
			Map<BlockPos, String> map = template.getDataBlocks(pos, settings);
			for (Entry<BlockPos, String> entry : map.entrySet())
			{
				if("chest1".equals(entry.getValue()))
				{
					BlockPos blockpos = entry.getKey();
					world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 3);
					TileEntity tileentity = world.getTileEntity(blockpos.down());
					
					if(tileentity instanceof TileEntityChest)
					{
						((TileEntityChest)tileentity).setLootTable(EMLootTableHandler.NATURE_STRUCTURE_CHEST1, random.nextLong());
					}
				}
				
				if("chest2".equals(entry.getValue()))
				{
					BlockPos blockpos = entry.getKey();
					world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 3);
					TileEntity tileentity = world.getTileEntity(blockpos.down());
					
					if(tileentity instanceof TileEntityChest)
					{
						((TileEntityChest)tileentity).setLootTable(EMLootTableHandler.NATURE_STRUCTURE_CHEST2, random.nextLong());
					}
				}
				
				if("chest3".equals(entry.getValue()))
				{
					BlockPos blockpos = entry.getKey();
					world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 3);
					TileEntity tileentity = world.getTileEntity(blockpos.down());
					
					if(tileentity instanceof TileEntityChest)
					{
						((TileEntityChest)tileentity).setLootTable(EMLootTableHandler.NATURE_STRUCTURE_CHEST3, random.nextLong());
					}
				}
				
				if("stalker_group_small".equals(entry.getValue()))
				{
					BlockPos blockpos = entry.getKey();
					world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 3);
					int randnum = new Random().nextInt(1);
					System.out.println(randnum);
					for(int i = 1 + randnum; i > 0; i--)
					{
						EntityNatureStalker stalker = new EntityNatureStalker(world);
						stalker.enablePersistence();
						stalker.setPosition(blockpos.getX() + 0.5D, blockpos.getY(), blockpos.getZ() + 0.5D);
						world.spawnEntity(stalker);
					}
				}
				
				if("stalker_group_large".equals(entry.getValue()))
				{
					BlockPos blockpos = entry.getKey();
					world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 3);
					int randnum = new Random().nextInt(2);
					System.out.println(randnum);
					for(int i = 2 + randnum; i > 0; i--)
					{
						EntityNatureStalker stalker = new EntityNatureStalker(world);
						stalker.enablePersistence();
						stalker.setPosition(blockpos.getX() + 0.5D, blockpos.getY(), blockpos.getZ() + 0.5D);
						world.spawnEntity(stalker);
					}
				}
				
				if("crystal_location".equals(entry.getValue()))
				{
					BlockPos blockpos = entry.getKey();
					world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 3);
					EntityPhotoSynthesizerCrystal crystal = new EntityPhotoSynthesizerCrystal(world);
					crystal.enablePersistence();
					crystal.setPosition(blockpos.getX() + 0.5D, blockpos.getY(), blockpos.getZ() + 0.5D);
					world.spawnEntity(crystal);
				}
				
				if("boss_spawn".equals(entry.getValue()))
				{
					BlockPos blockpos = entry.getKey();
					world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 3);
					EntityNatureBoss boss = new EntityNatureBoss(world, blockpos);
					boss.enablePersistence();
					boss.setPosition(blockpos.getX() + 0.5D, blockpos.getY(), blockpos.getZ() + 0.5D);
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
