package com.water.elementmod.items;

import com.water.elementmod.Main;
import com.water.elementmod.init.EmItems;
import com.water.elementmod.util.IHasModel;
import com.water.elementmod.util.Utils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBaseShiny extends Item implements IHasModel
{

	public ItemBaseShiny(String name, Integer StackSize)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		maxStackSize = StackSize;
		setCreativeTab(Main.tab_main);
				
		EmItems.ITEMS.add(this);
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack)
	{
		return true;
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
		Utils.getLogger().info("Registered render for item." + this.getUnlocalizedName().substring(5));
	}
}
