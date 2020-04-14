package com.water.elementmod.items.arrow;

import com.water.elementmod.EMCore;
import com.water.elementmod.EMCoreItems;
import com.water.elementmod.entity.projectile.EntityNatureArrow;
import com.water.elementmod.util.Utils;
import com.water.elementmod.util.interfaces.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemNatureArrow extends ItemArrow implements IHasModel
{

	public ItemNatureArrow(String name, Integer StackSize)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		maxStackSize = StackSize;
		setCreativeTab(EMCore.TAB_WEAPONS);
		
		EMCoreItems.ITEMS.add(this);
	}
	
	@Override
	public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter)
	{
		EntityNatureArrow entityCustomArrow = new EntityNatureArrow(worldIn, shooter);
		return entityCustomArrow;
	}
	
	@Override
	public void registerModels() 
	{
		EMCore.proxy.registerItemRenderer(this, 0, "inventory");
		Utils.getLogger().info("Registered render for item." + this.getUnlocalizedName().substring(5));
	}

}
