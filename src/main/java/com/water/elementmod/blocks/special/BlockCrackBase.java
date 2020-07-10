package com.water.elementmod.blocks.special;

import java.util.Random;

import com.water.elementmod.EMCore;
import com.water.elementmod.EMCoreBlocks;
import com.water.elementmod.EMCoreItems;
import com.water.elementmod.particle.EnumCustomParticleTypes;
import com.water.elementmod.particle.ParticleSpawner;
import com.water.elementmod.util.Utils;
import com.water.elementmod.util.interfaces.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCrackBase extends Block implements IHasModel
{
	
	public BlockCrackBase(String name, Material material, SoundType soundtype, String type, Integer level)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(null);
		setSoundType(soundtype);
		setHardness(99999.0f);
		setResistance(99999.0f);
		setHarvestLevel(type, level);
		setLightLevel(0.0F);
		setLightOpacity(15);
		
		
		EMCoreBlocks.BLOCKS.add(this);
		EMCoreItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
		this.spawnParticles(worldIn, pos, rand);
    }
	
	private void spawnParticles(World worldIn, BlockPos pos, Random rand)
    {
        Random random = worldIn.rand;
        
        for (int i = 0; i < 6; ++i)
        {
            double d1 = (double)((float)pos.getX() + random.nextFloat());
            double d2 = (double)((float)pos.getY() + random.nextFloat());
            double d3 = (double)((float)pos.getZ() + random.nextFloat());
            
            if (i == 0 && !worldIn.getBlockState(pos.up()).isOpaqueCube())
            {
                d2 = (double)pos.getY() + 0.0625D + 1.0D;
            }

            if (i == 1 && !worldIn.getBlockState(pos.down()).isOpaqueCube())
            {
                d2 = (double)pos.getY() - 0.0625D;
            }

            if (i == 2 && !worldIn.getBlockState(pos.south()).isOpaqueCube())
            {
                d3 = (double)pos.getZ() + 0.0625D + 1.0D;
            }

            if (i == 3 && !worldIn.getBlockState(pos.north()).isOpaqueCube())
            {
                d3 = (double)pos.getZ() - 0.0625D;
            }

            if (i == 4 && !worldIn.getBlockState(pos.east()).isOpaqueCube())
            {
                d1 = (double)pos.getX() + 0.0625D + 1.0D;
            }

            if (i == 5 && !worldIn.getBlockState(pos.west()).isOpaqueCube())
            {
                d1 = (double)pos.getX() - 0.0625D;
            }

            if (d1 < (double)pos.getX() || d1 > (double)(pos.getX() + 1) || d2 < 0.0D || d2 > (double)(pos.getY() + 1) || d3 < (double)pos.getZ() || d3 > (double)(pos.getZ() + 1))
            {
            	for (int i1 = 0; i1 < 3; ++i1)
                {
            		ParticleSpawner.spawnParticle(EnumCustomParticleTypes.VOID_PORTAL_BLOCK, d1, d2, d3, (rand.nextDouble() - 0.5D) * 2.0D, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2.0D);
                }
            }
        }
    }
	
	@Override
	public void registerModels() 
	{
		EMCore.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
		Utils.getLogger().info("Registered render for block." + this.getUnlocalizedName().substring(5));
	}
	
}
