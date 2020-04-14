package com.water.elementmod.gen;

import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import com.water.elementmod.entity.boss.EntityPhotoSynthesizerCrystal;
import com.water.elementmod.entity.boss.EntityNatureBoss;
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
import net.minecraft.world.storage.loot.LootTableList;

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
			IBlockState state = world.getBlockState(pos);
			world.notifyBlockUpdate(pos, state, state, 3);
			template.getDataBlocks(pos, settings);
			template.addBlocksToWorldChunk(world, pos, settings);
			
			for(int y = pos.getY(); y < pos.getY(); y--)
			{
				BlockPos pos2 = new BlockPos(pos.getX(), y, pos.getZ());
				if (world.isAirBlock(pos2))
	            {
					world.setBlockState(pos2, Blocks.COBBLESTONE.getDefaultState(), 2);
	            }
			}
			
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
}
