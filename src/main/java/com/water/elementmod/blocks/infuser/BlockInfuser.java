package com.water.elementmod.blocks.infuser;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.water.elementmod.Main;
import com.water.elementmod.blocks.BlockBase;
import com.water.elementmod.init.EmBlocks;
import com.water.elementmod.util.References;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockInfuser extends BlockBase implements ITileEntityProvider
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	public static final PropertyInteger ELEMENT = PropertyInteger.create("element", 0, 2);
	
	public static final AxisAlignedBB INFUSER_AABB = new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.9375, 0.8125D);
	public static final AxisAlignedBB INFUSER_BASE_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);

	public BlockInfuser(String name, Material material, SoundType soundtype, Float hardness, Float resistance,
			String type, Integer level, Float lightlevel, Integer opacity) 
	{
		super(name, material, soundtype, hardness, resistance, type, level, lightlevel, opacity);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, false).withProperty(ELEMENT, 0));
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
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, INFUSER_AABB);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, INFUSER_BASE_AABB);
    }
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return INFUSER_BASE_AABB;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(EmBlocks.INFUSER);
	}
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(EmBlocks.INFUSER);
		
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
			float hitX, float hitY, float hit2)
	{
		if(!worldIn.isRemote)
		{
			playerIn.openGui(Main.instance, References.INFUSER, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		
		return true;
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
	
	public static void setState(boolean active, Integer element, World worldIn, BlockPos pos)
	{
		IBlockState state = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		if(active) worldIn.setBlockState(pos, EmBlocks.INFUSER.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(ACTIVE, true).withProperty(ELEMENT, element), 3);
		else worldIn.setBlockState(pos, EmBlocks.INFUSER.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(ACTIVE, false).withProperty(ELEMENT, element), 3);
		
		if(tileentity != null)
		{
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return new TileEntityInfuser();
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
		TileEntityInfuser tileentity = (TileEntityInfuser)worldIn.getTileEntity(pos);
		InventoryHelper.dropInventoryItems(worldIn, pos, tileentity);
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
		return new BlockStateContainer(this, new IProperty[] {ACTIVE,FACING,ELEMENT});
	}
	
	@Override
    public IBlockState getStateFromMeta(int meta)
    {
		boolean blockIsActive = false;
		int element = 0;
		
        if(meta >= 6)
        {
            blockIsActive = true;
            meta -= 6;
        }
        
        if(meta > 3)
        {
        	element = meta;
        }
        
        EnumFacing facing = EnumFacing.getFront(meta);
        if(facing.getAxis() == EnumFacing.Axis.Y) {facing = EnumFacing.NORTH;}
        return this.getDefaultState().withProperty(FACING, facing).withProperty(ACTIVE, blockIsActive).withProperty(ELEMENT, element);
    }
    
    @Override
    public int getMetaFromState(IBlockState state)
    {
    	//System.out.println(state);
    	int r = state.getValue(ACTIVE) ? 1 : 0;
    	int e = state.getValue(ELEMENT).intValue();
    	
    	int facing = ((EnumFacing)state.getValue(FACING)).getIndex();
    	
        return ((EnumFacing)state.getValue(FACING)).getIndex() + 6*r + e;
    }
    
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("incomplete-switch")
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
		this.spawnParticles(worldIn, pos, rand);
		if (stateIn.getValue(ACTIVE))
        {
            EnumFacing enumfacing = (EnumFacing)stateIn.getValue(FACING);
            double d0 = (double)pos.getX() + 5.0D / 16.0D + rand.nextDouble() * 5.0D / 16.0D;
            double d1 = (double)pos.getY() + 1.0D;
            double d2 = (double)pos.getZ() + rand.nextDouble();
            
            double f3 = (double)pos.getX() + rand.nextDouble();
            double f4 = (double)pos.getZ() + 5.0D / 16.0D + rand.nextDouble() * 5.0D / 16.0D;
            
            switch (enumfacing)
            {
                case WEST:
                	if(stateIn.getValue(ELEMENT) == 0)
                	{
                		for (int i1 = 0; i1 < 8; ++i1)
	                    {
                			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                			worldIn.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	                    }
                		
	                    worldIn.spawnParticle(EnumParticleTypes.LAVA, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                	}
                	if(stateIn.getValue(ELEMENT) == 1)
                	{
                		for (int i1 = 0; i1 < 3; ++i1)
	                    {
	                		worldIn.spawnParticle(EnumParticleTypes.WATER_DROP, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	                		worldIn.spawnParticle(EnumParticleTypes.WATER_WAKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	                    }
	                    for (int i1 = 0; i1 < 10; ++i1)
	                    {
		                    worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	                    }
                	}
                	
                	if(stateIn.getValue(ELEMENT) == 2)
                	{
                		for (int i1 = 0; i1 < 7; ++i1)
	                    {
                			worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	                    }
                		
                		for (int i1 = 0; i1 < 5; ++i1)
	                    {
                			worldIn.spawnParticle(EnumParticleTypes.SPELL_MOB_AMBIENT, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	                    }
                	}
                    break;
                case EAST:
                	if(stateIn.getValue(ELEMENT) == 0)
                	{
                		for (int i1 = 0; i1 < 8; ++i1)
	                    {
                			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                			worldIn.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	                    }
                		
	                    worldIn.spawnParticle(EnumParticleTypes.LAVA, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                	}
                	if(stateIn.getValue(ELEMENT) == 1)
                	{
                		for (int i1 = 0; i1 < 3; ++i1)
	                    {
	                		worldIn.spawnParticle(EnumParticleTypes.WATER_DROP, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	                		worldIn.spawnParticle(EnumParticleTypes.WATER_WAKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	                    }
	                    for (int i1 = 0; i1 < 10; ++i1)
	                    {
		                    worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	                    }
                	}
                	
                	if(stateIn.getValue(ELEMENT) == 2)
                	{
                		for (int i1 = 0; i1 < 7; ++i1)
	                    {
                			worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	                    }
                		
                		for (int i1 = 0; i1 < 5; ++i1)
	                    {
                			worldIn.spawnParticle(EnumParticleTypes.SPELL_MOB_AMBIENT, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	                    }
                	}
                    break;
                case NORTH:
                	if(stateIn.getValue(ELEMENT) == 0)
                	{
                		for (int i1 = 0; i1 < 8; ++i1)
	                    {
                			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, f3, d1, f4, 0.0D, 0.0D, 0.0D);
                			worldIn.spawnParticle(EnumParticleTypes.FLAME, f3, d1, f4, 0.0D, 0.0D, 0.0D);
	                    }
                		
	                    worldIn.spawnParticle(EnumParticleTypes.LAVA, f3, d1, f4, 0.0D, 0.0D, 0.0D);
                	}
                	if(stateIn.getValue(ELEMENT) == 1)
                	{
                		for (int i1 = 0; i1 < 3; ++i1)
	                    {
	                		worldIn.spawnParticle(EnumParticleTypes.WATER_DROP, f3, d1, f4, 0.0D, 0.0D, 0.0D);
	                		worldIn.spawnParticle(EnumParticleTypes.WATER_WAKE, f3, d1, f4, 0.0D, 0.0D, 0.0D);
	                    }
	                    for (int i1 = 0; i1 < 10; ++i1)
	                    {
		                    worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, f3, d1, f4, 0.0D, 0.0D, 0.0D);
	                    }
                	}
                	
                	if(stateIn.getValue(ELEMENT) == 2)
                	{
                		for (int i1 = 0; i1 < 7; ++i1)
	                    {
                			worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, f3, d1, f4, 0.0D, 0.0D, 0.0D);
	                    }
                		
                		for (int i1 = 0; i1 < 5; ++i1)
	                    {
                			worldIn.spawnParticle(EnumParticleTypes.SPELL_MOB_AMBIENT, f3, d1, f4, 0.0D, 0.0D, 0.0D);
	                    }
                	}
                    break;
                case SOUTH:
                	if(stateIn.getValue(ELEMENT) == 0)
                	{
                		for (int i1 = 0; i1 < 8; ++i1)
	                    {
                			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, f3, d1, f4, 0.0D, 0.0D, 0.0D);
                			worldIn.spawnParticle(EnumParticleTypes.FLAME, f3, d1, f4, 0.0D, 0.0D, 0.0D);
	                    }
                		
	                    worldIn.spawnParticle(EnumParticleTypes.LAVA, f3, d1, f4, 0.0D, 0.0D, 0.0D);
                	}
                	if(stateIn.getValue(ELEMENT) == 1)
                	{
                		for (int i1 = 0; i1 < 3; ++i1)
	                    {
	                		worldIn.spawnParticle(EnumParticleTypes.WATER_DROP, f3, d1, f4, 0.0D, 0.0D, 0.0D);
	                		worldIn.spawnParticle(EnumParticleTypes.WATER_WAKE, f3, d1, f4, 0.0D, 0.0D, 0.0D);
	                    }
	                    for (int i1 = 0; i1 < 10; ++i1)
	                    {
		                    worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, f3, d1, f4, 0.0D, 0.0D, 0.0D);
	                    }
                	}
                	
                	if(stateIn.getValue(ELEMENT) == 2)
                	{
                		for (int i1 = 0; i1 < 7; ++i1)
	                    {
                			worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, f3, d1, f4, 0.0D, 0.0D, 0.0D);
	                    }
                		
                		for (int i1 = 0; i1 < 5; ++i1)
	                    {
                			worldIn.spawnParticle(EnumParticleTypes.SPELL_MOB_AMBIENT, f3, d1, f4, 0.0D, 0.0D, 0.0D);
	                    }
                	}
                	break;
            }
        }
    }
	
	private void spawnParticles(World worldIn, BlockPos pos, Random rand)
    {
        Random random = worldIn.rand;
        double d0 = 0.0625D;

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
            	for (int i1 = 0; i1 < 1; ++i1)
                {
            		worldIn.spawnParticle(EnumParticleTypes.PORTAL, d1, d2, d3, (rand.nextDouble() - 0.5D) * 2.0D, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2.0D);
                }
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
