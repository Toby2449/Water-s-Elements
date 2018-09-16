package com.water.elementmod.items.weapons;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.water.elementmod.EMCore;
import com.water.elementmod.EMCoreItems;
import com.water.elementmod.util.Utils;
import com.water.elementmod.util.interfaces.IHasModel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
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
	private ToolMaterial material;
	
	public FireSword(String name, Integer level, ToolMaterial material) 
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(EMCore.TAB_WEAPONS);
		canApplyAtEnchantingTable(new ItemStack(this), Enchantments.FIRE_ASPECT);
		
		this.level = level;
		this.material = material;
		EMCoreItems.ITEMS.add(this);
	}
	
	public String intToNumeral()
	{
		switch(this.level)
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
			case 6:
				return  "VI";
			case 7:
				return  "VII";
			case 8:
				return  "VIII";
			case 9:
				return  "IX";
			case 10:
				return  "X";
		}
		
		return "??";
		
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack)
	{
		return true;
	}
	
	public int getAddedDamage() {
		return (int)this.material.getAttackDamage() - (int)ToolMaterial.DIAMOND.getAttackDamage();
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
    {
		if(!enchantment.equals(Enchantments.FIRE_ASPECT))
		{
        	return enchantment.type.canEnchantItem(stack.getItem());
		}
		return false;
    }
	
	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		if(EnchantmentHelper.getEnchantments(book).containsKey(Enchantments.FIRE_ASPECT)){
			return false;
		}
		return true;
	}
	
	public int getFireDuration(Boolean isRandom, Boolean isMax)
	{
		int i = 0;
		int Range = 2;
		Random rand = new Random();
		if(!isMax) 
		{
			switch(this.level)
			{
				case 1:
					i = 2;
					if(!isRandom) return i;
					return i + rand.nextInt(Range + 1);
				case 2:
					i = 3;
					if(!isRandom) return i;
					return i + rand.nextInt(Range + 1);
				case 3:
					i = 4;
					if(!isRandom) return i;
					return i + rand.nextInt(Range + 1);
				case 4:
					i = 5;
					if(!isRandom) return i;
					return i + rand.nextInt(Range + 1);
				case 5:
					i = 5;
					if(!isRandom) return i;
					return i + rand.nextInt(Range + 1);
				case 6:
					i = 6;
					if(!isRandom) return i;
					return i + rand.nextInt(Range + 1);
				case 7:
					i = 7;
					if(!isRandom) return i;
					return i + rand.nextInt(Range + 1);
				case 8:
					i = 8;
					if(!isRandom) return i;
					return i + rand.nextInt(Range + 1);
				case 9:
					i = 9;
					if(!isRandom) return i;
					return i + rand.nextInt(Range + 1);
				case 10:
					i = 10;
					if(!isRandom) return i;
					return i + rand.nextInt(Range + 1); // Have to add +1 because it goes from 0-1 (which is 2 numbers)
			}
		}
		return Range;
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn)
	{
		list.add(I18n.format("tooltip.FireEnchant"));
	    list.add(I18n.format("tooltip.EnchantFireLevel") + " " + intToNumeral() + I18n.format("tooltip.ResetFormatting"));
	    
	    if(GuiScreen.isAltKeyDown()){
	    	//list.remove(I18n.format("tooltip.PressAlt") + I18n.format("tooltip.ResetFormatting"));
	    	list.add("");
	    	list.add(I18n.format("tooltip.2xDamageNature"));
	    	list.add(I18n.format("tooltip.FireFormatting") + "+" + this.getAddedDamage() + " " + I18n.format("tooltip.MoreAttackDamage") + I18n.format("tooltip.ResetFormatting"));
	    	list.add(I18n.format("tooltip.FireDuration") + this.getFireDuration(false, false) + "-" + (this.getFireDuration(false, false) + this.getFireDuration(false, true) ) + "s" + I18n.format("tooltip.ResetFormatting"));
	    } else {
	    	list.add(I18n.format("tooltip.PressAlt") + I18n.format("tooltip.ResetFormatting"));
	    }
	    
	    list.add(""); // Works the same way as \n
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		target.setFire(getFireDuration(true, false));
		stack.damageItem(1, attacker);
		FireParticleEffect(target);
	    return true;
	}
	
	public boolean FireParticleEffect(EntityLivingBase target)
	{
		World world = Minecraft.getMinecraft().world;
		if(world == null) return false;
		for(int countparticles = 0; countparticles <= 7 * this.level / 2; ++countparticles)
		{
			Random rand = new Random();
			world.spawnParticle(EnumParticleTypes.FLAME, target.posX + (rand.nextDouble() - 0.5D) * (double)target.width, target.posY + rand.nextDouble() * (double)target.height - (double)target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * (double)target.width, 0.0D, 0.0D,0.0D);
		}
		for(int countparticles = 0; countparticles <= 20 * this.level / 2; ++countparticles)
		{
			Random rand = new Random();
			world.spawnParticle(EnumParticleTypes.LAVA, target.posX + (rand.nextDouble() - 0.5D) * (double)target.width, target.posY + rand.nextDouble() * (double)target.height - (double)target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * (double)target.width, 0.0D, 0.0D,0.0D);
		}
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer target, EnumHand handIn)
    {
		
		return new ActionResult<ItemStack>(EnumActionResult.PASS, target.getHeldItem(handIn));
	}
	
	@Override
	public void registerModels()
	{
		EMCore.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
}
