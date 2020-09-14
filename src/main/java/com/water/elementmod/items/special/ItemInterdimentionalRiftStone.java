package com.water.elementmod.items.special;

import java.util.List;

import com.water.elementmod.EMCore;
import com.water.elementmod.EMCoreBlocks;
import com.water.elementmod.EMCoreDimensions;
import com.water.elementmod.EMCoreItems;
import com.water.elementmod.entity.boss._void.EntityCarapace;
import com.water.elementmod.entity.boss._void.EntityVoidEntity;
import com.water.elementmod.entity.boss.overworld.EntityVoidKnight;
import com.water.elementmod.entity.boss.overworld._ConfigEntityVoidKnight;
import com.water.elementmod.util.Utils;
import com.water.elementmod.util.interfaces.IHasModel;
import com.water.elementmod.world.dimension.TeleportWithoutPortal;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemInterdimentionalRiftStone extends Item implements IHasModel
{

	public ItemInterdimentionalRiftStone(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		maxStackSize = 1;
		setCreativeTab(EMCore.TAB_MAIN);
		
		EMCoreItems.ITEMS.add(this);
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack)
	{
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn)
    {
		if(!worldIn.isRemote)
		{
			if(player.dimension == EMCoreDimensions.VOID.getId())
			{
				boolean GoodToTP = true;
				List<EntityCarapace> carapace = worldIn.<EntityCarapace>getEntitiesWithinAABB(EntityCarapace.class, player.getEntityBoundingBox().grow(1000, 1000, 1000).offset(0, -500, 0));
		        if(!carapace.isEmpty())
		        {
		        	for (EntityCarapace entity : carapace)
			        {
		        		if(entity.getPhase() != 0)
		        		{
		        			GoodToTP = false;
		        		}
			        }
		        }
		        
		        List<EntityVoidEntity> voidentity = worldIn.<EntityVoidEntity>getEntitiesWithinAABB(EntityVoidEntity.class, player.getEntityBoundingBox().grow(1000, 1000, 1000).offset(0, -500, 0));
		        if(!voidentity.isEmpty())
		        {
		        	for (EntityVoidEntity entity : voidentity)
			        {
		        		if(entity.getPhase() != 0)
		        		{
		        			GoodToTP = false;
		        		}
			        }
		        }
		        
		        if(GoodToTP)
		        {
					if(player.getBedLocation(0) != null)
					{
						((EntityPlayerMP) player).mcServer.getPlayerList().transferPlayerToDimension(
						(EntityPlayerMP) player, 0, new TeleportWithoutPortal(((EntityPlayerMP) player).mcServer.getWorld(0)));
						player.setPositionAndUpdate(player.getBedLocation(0).getX(), player.getBedLocation(0).getY(), player.getBedLocation(0).getZ());
					}
					else
					{
						MinecraftServer minecraftserver = worldIn.getMinecraftServer();
						WorldServer worldserver = minecraftserver.getWorld(0);
						BlockPos pos = worldserver.getTopSolidOrLiquidBlock(worldserver.getSpawnPoint());
						((EntityPlayerMP) player).mcServer.getPlayerList().transferPlayerToDimension(
						(EntityPlayerMP) player, 0, new TeleportWithoutPortal(((EntityPlayerMP) player).mcServer.getWorld(0)));
						player.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
					}
		        }
			}
			else
			{
				boolean GoodToTP = false;
				List<EntityVoidKnight> list = worldIn.<EntityVoidKnight>getEntitiesWithinAABB(EntityVoidKnight.class, player.getEntityBoundingBox().grow(_ConfigEntityVoidKnight.ARENA_SIZE_X, _ConfigEntityVoidKnight.ARENA_SIZE_Y, _ConfigEntityVoidKnight.ARENA_SIZE_Z).offset(0, -4, 0));
				for (EntityVoidKnight entity : list)
	        	{
					if(entity.getPhase() == 6)
					{
						GoodToTP = true;
					}
	        	}
				
				if(GoodToTP)
				{
					if(this.findBlockUnderEntity(player) == EMCoreBlocks.VOID_CRACK_15.getDefaultState() || this.findBlockUnderEntity(player) == EMCoreBlocks.VOID_CRACK_16.getDefaultState() || this.findBlockUnderEntity(player) == EMCoreBlocks.VOID_CRACK_19.getDefaultState() || this.findBlockUnderEntity(player) == EMCoreBlocks.VOID_CRACK_20.getDefaultState())
					{
						((EntityPlayerMP) player).mcServer.getPlayerList().transferPlayerToDimension(
						(EntityPlayerMP) player, 2, new TeleportWithoutPortal(((EntityPlayerMP) player).mcServer.getWorld(EMCoreDimensions.VOID.getId())));
						player.setPositionAndUpdate(1.0D, 58, 1.0D);
					}
				}
				
				((EntityPlayerMP) player).mcServer.getPlayerList().transferPlayerToDimension(
				(EntityPlayerMP) player, 2, new TeleportWithoutPortal(((EntityPlayerMP) player).mcServer.getWorld(EMCoreDimensions.VOID.getId())));
				player.setPositionAndUpdate(1.0D, 58, 1.0D);
			}
		}
		
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(handIn));
    }
	
	public IBlockState findBlockUnderEntity(EntityPlayer parEntity)
	{

	    int blockX = MathHelper.floor(parEntity.posX);
	    int blockY = MathHelper.floor(parEntity.posY-1.0D);
	    int blockZ = MathHelper.floor(parEntity.posZ);
	    return parEntity.world.getBlockState(new BlockPos(blockX, blockY, blockZ));

	}
	
	@Override
	public void registerModels() 
	{
		EMCore.proxy.registerItemRenderer(this, 0, "inventory");
		Utils.getLogger().info("Registered render for item." + this.getUnlocalizedName().substring(5));
	}
}
