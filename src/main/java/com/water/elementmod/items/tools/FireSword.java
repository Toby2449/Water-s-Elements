package com.water.elementmod.items.tools;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.water.elementmod.Main;
import com.water.elementmod.init.EmItems;
import com.water.elementmod.util.IHasModel;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FireSword extends ItemSword implements IHasModel
{
	private int level;
	
	public FireSword(String name, Integer level, ToolMaterial material) 
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.tab_everything);
		
		this.level = level;
		EmItems.ITEMS.add(this);
	}
	
	public String intToNumeral(Integer level)
	{
		switch(level)
		{
		case 1:
			return "I";
		case 2:
			return "II";
		case 3:
			return "III";
		case 4:
			return "IV";
		case 5:
			return  "V";
		}
		
		return "??";
		
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack)
	{
		return true;
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn)
	{
		list.add(I18n.format("tooltip.FireEnchant"));
	    list.add(I18n.format("tooltip.EnchantFireLevel") + " " + intToNumeral(this.level) + I18n.format("tooltip.ResetFormatting"));
	    list.add(" ");
	}
	
	@Override

	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		switch(this.level)
		{
		case 1:
			target.setFire(2);
			break;
		case 2:
			target.setFire(3);
			break;
		case 3:
			target.setFire(3);
			break;
		case 4:
			target.setFire(4);
			break;
		case 5:
			target.setFire(5);
			break;
		}
	    stack.damageItem(1, attacker);
	    return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
		Random rand = new Random();
		for(int countparticles = 0; countparticles <= 10; ++countparticles)
		{
			worldIn.spawnParticle(EnumParticleTypes.FLAME, playerIn.posX + 1.0D * (double)playerIn.width, playerIn.posY + rand.nextDouble() * (double)playerIn.height - (double)playerIn.getYOffset(), playerIn.posZ + 1.0D, 0.0D, 0.0D, 0.0D);
		}
		
		return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}
	
	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
}
