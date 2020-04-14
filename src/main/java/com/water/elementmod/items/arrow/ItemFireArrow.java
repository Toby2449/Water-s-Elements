package com.water.elementmod.items.arrow;

import com.water.elementmod.EMCore;
import com.water.elementmod.EMCoreItems;
import com.water.elementmod.entity.projectile.EntityFireArrow;
import com.water.elementmod.entity.projectile.EntityNatureArrow;
import com.water.elementmod.util.Utils;
import com.water.elementmod.util.interfaces.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFireArrow extends ItemArrow implements IHasModel
{

	public ItemFireArrow(String name, Integer StackSize)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		maxStackSize = StackSize;
		setCreativeTab(EMCore.TAB_WEAPONS);
		
		EMCoreItems.ITEMS.add(this);
	}
	
	@Override
	public EntityFireArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter)
	{
		EntityFireArrow entityCustomArrow = new EntityFireArrow(worldIn, shooter);
		return entityCustomArrow;
	}
	
	public void registerModels() 
	{
		EMCore.proxy.registerItemRenderer(this, 0, "inventory");
		Utils.getLogger().info("Registered render for item." + this.getUnlocalizedName().substring(5));
	}

}
