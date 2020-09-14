package com.water.elementmod.items.armor;

import java.util.List;

import javax.annotation.Nullable;

import com.water.elementmod.EMCore;
import com.water.elementmod.EMCoreItems;
import com.water.elementmod.util.Utils;
import com.water.elementmod.util.interfaces.IHasModel;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class VoidHelmet extends ItemArmor implements IHasModel {

	public VoidHelmet(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(EMCore.TAB_WEAPONS);
		
		EMCoreItems.ITEMS.add(this);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn)
	{
		list.add(I18n.format("tooltip.VoidEnchant"));
	    list.add(I18n.format("tooltip.MagicDamageReduction"));
	    list.add(I18n.format("tooltip.VoidDamageReduction"));
	}
	
	@Override
	public void registerModels() 
	{
		EMCore.proxy.registerItemRenderer(this, 0, "inventory");
		Utils.getLogger().info("Registered render for item." + this.getUnlocalizedName().substring(5));
	}

}
