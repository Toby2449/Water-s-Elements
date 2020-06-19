package com.water.elementmod.blocks.valve;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.water.elementmod.EMCore;
import com.water.elementmod.EMCoreBlocks;
import com.water.elementmod.blocks.EMBlockBase;
import com.water.elementmod.blocks.synthesizer.TileEntitySynthesizer;
import com.water.elementmod.entity.boss.fire.EntityFireGuardian;
import com.water.elementmod.entity.boss.water.EntityWaterBoss;
import com.water.elementmod.util.EMConfig;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockValveFake extends EMBlockBase
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool USED = PropertyBool.create("used");
	
	public static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(0.5D, 0.375D, 0.3125D, 1.0D, 0.75D, 0.6875D);
	public static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(0.3125D, 0.375D, 0.5D, 0.6875D, 0.75D, 1.0D);
	public static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(0.0D, 0.375D, 0.3125D, 0.5D, 0.75D, 0.6875D);
	public static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(0.3125D, 0.375D, 0.0D, 0.6875D, 0.75D, 0.5D);
	
	public BlockValveFake(String name, Material material, SoundType soundtype, Float hardness, Float resistance, 
			String type, Integer level, Float lightlevel, Integer opacity) {
		super(name, material, soundtype, hardness, resistance, type, level, lightlevel, opacity);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(USED, false));
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

        switch (enumfacing)
        {
            case EAST:
                return AABB_EAST;
            case WEST:
            	return AABB_WEST;
            case SOUTH:
            	return AABB_SOUTH;
            case NORTH:
            default:
            	return AABB_NORTH;
            		
        }
    }
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(EMCoreBlocks.VALVE_FAKE);
	}
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(EMCoreBlocks.VALVE_FAKE);
		
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		if(!worldIn.isRemote)
		{
			IBlockState north = worldIn.getBlockState(pos.north());
			IBlockState south = worldIn.getBlockState(pos.south());
			IBlockState west = worldIn.getBlockState(pos.west());
			IBlockState east = worldIn.getBlockState(pos.east());
			EnumFacing face = (EnumFacing)state.getValue(FACING);
			
			if (face == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock()) face = EnumFacing.SOUTH;
			else if (face == EnumFacing.SOUTH && south.isFullBlock() && !north.isFullBlock()) face = EnumFacing.NORTH;
			else if (face == EnumFacing.WEST && west.isFullBlock() && !east.isFullBlock()) face = EnumFacing.EAST;
			else if (face == EnumFacing.EAST && east.isFullBlock() && !west.isFullBlock()) face = EnumFacing.WEST;
			worldIn.setBlockState(pos, state.withProperty(FACING, face), 2);
		}
	}
	
	public static void setState(boolean used, World worldIn, BlockPos pos)
	{
		IBlockState state = worldIn.getBlockState(pos);
		
		if(used) worldIn.setBlockState(pos, EMCoreBlocks.VALVE_FAKE.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(USED, true), 3);
		else worldIn.setBlockState(pos, EMCoreBlocks.VALVE_FAKE.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(USED, false), 3);
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		worldIn.setBlockState(pos, this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
		
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
		return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {USED,FACING});
	}
	
	@Override
    public IBlockState getStateFromMeta(int meta)
    {
		boolean blockIsActive = false;
        if(meta >= 6)
        {
            blockIsActive = true;
            meta = meta - 6;
        }
        EnumFacing facing = EnumFacing.getFront(meta);
        if(facing.getAxis() == EnumFacing.Axis.Y) {facing = EnumFacing.NORTH;}
        
        return this.getDefaultState().withProperty(FACING, facing).withProperty(USED, blockIsActive);
    }
    
    @Override
    public int getMetaFromState(IBlockState state)
    {
    	int r = state.getValue(USED) ? 1 : 0;
        return ((EnumFacing)state.getValue(FACING)).getIndex() + 6*r;
    }
    
    @SuppressWarnings("incomplete-switch")
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
    	EnumFacing enumfacing = (EnumFacing)stateIn.getValue(FACING);

    	if (stateIn.getValue(USED))
    	{
	        switch (enumfacing)
	        {
		        case EAST:
		        	for (int i = 0; i < 20; ++i) worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, pos.getX() + 0.0625D, pos.getY() + 0.675D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
		        	break;
		        case WEST:
		        	for (int i1 = 0; i1 < 20; ++i1) worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, pos.getX() + 0.9375D, pos.getY() + 0.675D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
		        	break;
		        case SOUTH:
		        	for (int i2 = 0; i2 < 20; ++i2) worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, pos.getX() + 0.5D, pos.getY() + 0.675D, pos.getZ() + 0.0625D, 0.0D, 0.0D, 0.0D);
		        	break;
		        case NORTH:
		        	for (int i3 = 0; i3 < 20; ++i3) worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, pos.getX() + 0.5D, pos.getY() + 0.675D, pos.getZ() + 0.9375D, 0.0D, 0.0D, 0.0D);
		        	break;
	        }
    	}
    }
    
    @SideOnly(Side.CLIENT)
	@Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
	
	/**
     * Get the geometry of the queried face at the given position and state. This is used to decide whether things like
     * buttons are allowed to be placed on the face, or how glass panes connect to the face, among other things.
     * <p>
     * Common values are {@code SOLID}, which is the default, and {@code UNDEFINED}, which represents something that
     * does not fit the other descriptions and will generally cause other things not to connect to the face.
     * 
     * @return an approximation of the form of the given face
     */
	@Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
