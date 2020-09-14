package com.water.elementmod.items.special;

import java.util.List;

import com.water.elementmod.EMCore;
import com.water.elementmod.EMCoreDimensions;
import com.water.elementmod.EMCoreItems;
import com.water.elementmod.entity.boss._void.EntityVEBase;
import com.water.elementmod.entity.boss._void.EntityVoidEntity;
import com.water.elementmod.network.PacketCarapaceParticleCircle;
import com.water.elementmod.network.PacketHandler;
import com.water.elementmod.util.Utils;
import com.water.elementmod.util.interfaces.IHasModel;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemVoidEntitySoul extends Item implements IHasModel
{

	public ItemVoidEntitySoul(String name)
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
				List<EntityVoidEntity> ve = worldIn.<EntityVoidEntity>getEntitiesWithinAABB(EntityVoidEntity.class, player.getEntityBoundingBox().grow(1000, 1000, 1000).offset(0, -500, 0));
				if(ve.isEmpty())
				{
					List<EntityVEBase> vebase = worldIn.<EntityVEBase>getEntitiesWithinAABB(EntityVEBase.class, player.getEntityBoundingBox().grow(1000, 1000, 1000).offset(0, -500, 0));
			        if(!vebase.isEmpty())
			        {
			        	for (EntityVEBase entity : vebase)
				        {
			        		entity.spawnVE();
				        }
			        	
			        	PacketHandler.INSTANCE.sendToDimension(new PacketCarapaceParticleCircle(player, worldIn, 9, player.posX, player.posY, player.posZ, 0.0D, 0.0D, 0.0D, 15), player.dimension);
			        	return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, ItemStack.EMPTY);
			        }
				}
			}
		}
		
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, player.getHeldItem(handIn));
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
