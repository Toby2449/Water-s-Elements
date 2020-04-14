package com.water.elementmod.items.weapons;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.water.elementmod.EMCore;
import com.water.elementmod.EMCoreItems;
import com.water.elementmod.entity.monster.EntityFireZombie;
import com.water.elementmod.entity.monster.EntityWaterSkeleton;
import com.water.elementmod.entity.monster.EntityWaterZombie;
import com.water.elementmod.events.EventNatureSwordAbility;
import com.water.elementmod.events.EventSuperPoison;
import com.water.elementmod.network.PacketAbilityReadyFireData;
import com.water.elementmod.network.PacketAbilityReadyNatureData;
import com.water.elementmod.network.PacketCustomParticleData;
import com.water.elementmod.network.PacketHandler;
import com.water.elementmod.network.PacketParticleData;
import com.water.elementmod.util.EMConfig;
import com.water.elementmod.util.Utils;
import com.water.elementmod.util.interfaces.IHasModel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NatureSword extends ItemSword implements IHasModel
{
	private int level;
	private ToolMaterial material;
	
	public NatureSword(String name, Integer level, ToolMaterial material) 
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
	
	public int getPosionDuration(Boolean isRandom, Boolean isMax)
	{
		int i = 0;
		int Range = 3;
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
					i = 3;
					if(!isRandom) return i;
					return i + rand.nextInt(Range + 1);
				case 4:
					i = 4;
					if(!isRandom) return i;
					return i + rand.nextInt(Range + 1);
				case 5:
					i = 4;
					if(!isRandom) return i;
					return i + rand.nextInt(Range + 1);
				case 6:
					i = 5;
					if(!isRandom) return i;
					return i + rand.nextInt(Range + 1);
				case 7:
					i = 5;
					if(!isRandom) return i;
					return i + rand.nextInt(Range + 1);
				case 8:
					i = 6;
					if(!isRandom) return i;
					return i + rand.nextInt(Range + 1);
				case 9:
					i = 6;
					if(!isRandom) return i;
					return i + rand.nextInt(Range + 1);
				case 10:
					i = 7;
					if(!isRandom) return i;
					return i + rand.nextInt(Range + 1); // Have to add +1 because it goes from 0-1 (which is 2 numbers)
			}
		}
		return Range;
	}
	
	/**
	* Gets how long the ability lasts for the sword's level
	*/
	public int getAbilityDuration()
	{
		int i = 0;
		switch(this.level)
		{
			case 1:
				i = 0;
				return i;
			case 2:
				i = 0;
				return i;
			case 3:
				i = 0;
				return i;
			case 4:
				i = 0;
				return i;
			case 5:
				i = 6;
				return i;
			case 6:
				i = 6;
				return i;
			case 7:
				i = 7;
				return i;
			case 8:
				i = 7;
				return i;
			case 9:
				i = 8;
				return i;
			case 10:
				i = 8;
				return i;
		}
		return i;
	}
	
	/**
	* Returns the cooldown time
	*/
	public int getAbilityCooldown()
	{
		int i = 0;
		switch(this.level)
		{
			case 1:
				i = 0;
				return i;
			case 2:
				i = 0;
				return i;
			case 3:
				i = 0;
				return i;
			case 4:
				i = 0;
				return i;
			case 5:
				i = 5;
				return i;
			case 6:
				i = 8;
				return i;
			case 7:
				i = 11;
				return i;
			case 8:
				i = 14;
				return i;
			case 9:
				i = 17;
				return i;
			case 10:
				i = 20;
				return i;
		}
		return i;
	}
	
	/**
	* Checks the ability radius for the level of the sword
	*/
	public double getAbilityRadius()
	{
		double i = 0.0D;
		switch(this.level)
		{
			case 1:
				i = 0.0D;
				return i;
			case 2:
				i = 0.0D;
				return i;
			case 3:
				i = 0.0D;
				return i;
			case 4:
				i = 0.0D;
				return i;
			case 5:
				i = 2.0D;
				return i;
			case 6:
				i = 2.2D;
				return i;
			case 7:
				i = 2.4D;
				return i;
			case 8:
				i = 2.6D;
				return i;
			case 9:
				i = 2.8D;
				return i;
			case 10:
				i = 3.0D;
				return i;
		}
		return i;
	}
	
	/**
	* Returns the regeneration level
	*/
	public int getRegenerationLevel()
	{
		int i = 0;
		switch(this.level)
		{
			case 1:
				i = 0;
				return i;
			case 2:
				i = 0;
				return i;
			case 3:
				i = 0;
				return i;
			case 4:
				i = 0;
				return i;
			case 5:
				i = 0;
				return i;
			case 6:
				i = 1;
				return i;
			case 7:
				i = 1;
				return i;
			case 8:
				i = 2;
				return i;
			case 9:
				i = 2;
				return i;
			case 10:
				i = 3;
				return i;
		}
		return i;
	}
	
	/**
	* Returns the regeneration level as a roman numberal
	*/
	public String getRegenerationLevelRN()
	{
		switch(this.level)
		{
			case 1:
				return null;
			case 2:
				return null;
			case 3:
				return null;
			case 4:
				return null;
			case 5:
				return "I";
			case 6:
				return "II";
			case 7:
				return "II";
			case 8:
				return "III";
			case 9:
				return "III";
			case 10:
				return "IV";
		}
		return null;
	}
	
	/**
	* Checks if the current level of the sword is eliagible for the ability
	*/
	public boolean getEliagibleForAbility()
	{
		switch(this.level)
		{
			case 1:
				return false;
			case 2:
				return false;
			case 3:
				return false;
			case 4:
				return false;
			case 5:
				return true;
			case 6:
				return true;
			case 7:
				return true;
			case 8:
				return true;
			case 9:
				return true;
			case 10:
				return true;
		}
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn)
	{
		list.add(I18n.format("tooltip.NatureEnchant"));
	    list.add(I18n.format("tooltip.EnchantNatureLevel") + " " + intToNumeral() + I18n.format("tooltip.ResetFormatting"));
	    list.add(I18n.format("tooltip.NatureFormatting") + "+" + this.getAddedDamage() + " " + I18n.format("tooltip.MoreAttackDamage") + I18n.format("tooltip.ResetFormatting"));
	    
	    if(GuiScreen.isAltKeyDown()){
	    	//list.remove(I18n.format("tooltip.PressAlt") + I18n.format("tooltip.ResetFormatting"));
	    	list.add("");
	    	list.add(I18n.format("tooltip.NatureFormatting") + "+" + Math.round(this.getAttackDamage() / 3) + I18n.format("tooltip.WaterDamageBoost"));
	    	list.add(I18n.format("tooltip.NatureDuration") + this.getPosionDuration(false, false) + "-" + (this.getPosionDuration(false, false) + this.getPosionDuration(false, true) ) + "s" + I18n.format("tooltip.ResetFormatting"));
	    	if(this.getEliagibleForAbility())
	    	{
	    		list.add(I18n.format("tooltip.NatureAbilityDuration") + this.getAbilityDuration() + "s" + I18n.format("tooltip.ResetFormatting"));
	    		list.add(I18n.format("tooltip.NatureAbilityRadius") + (this.getAbilityRadius() * 2) + " blocks" + I18n.format("tooltip.ResetFormatting"));
	    		list.add(I18n.format("tooltip.NatureAbilityCDDuration") + this.getAbilityCooldown() + "s" + I18n.format("tooltip.ResetFormatting"));
	    		list.add(I18n.format("tooltip.NatureRegenerationLevel") + this.getRegenerationLevelRN() + I18n.format("tooltip.ResetFormatting"));
	    	}
	    } else {
	    	list.add(I18n.format("tooltip.PressAlt") + I18n.format("tooltip.ResetFormatting"));
	    }
	    
	    list.add(""); // Works the same way as \n
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if(target instanceof EntityWaterZombie || target instanceof EntityWaterSkeleton)
		{
			target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), this.getAttackDamage() / 3);
		}
		EventSuperPoison.playerHitEntity(target, this.getPosionDuration(true, false));
		NatureParticleHitEffect(target, target.world);
		stack.damageItem(1, attacker);
	    return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn)
    {
		if(!this.getEliagibleForAbility()) return new ActionResult<ItemStack>(EnumActionResult.FAIL, player.getHeldItem(handIn));
		EventNatureSwordAbility.playerActivateAbility(worldIn, player, this.getAbilityRadius(), this.getAbilityDuration(), this.getAbilityCooldown(), this.getRegenerationLevel());
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(handIn));
	}
	
	public boolean NatureParticleHitEffect(EntityLivingBase target, World world)
	{
		for(int countparticles = 0; countparticles <= 15 * this.level / 2; ++countparticles)
		{
			Random rand = new Random();
			PacketHandler.INSTANCE.sendToDimension(new PacketCustomParticleData(target, world, 0, target.posX + (rand.nextDouble() - 0.5D) * (double)target.width, target.posY + rand.nextDouble() * (double)target.height - 0.25D, target.posZ + (rand.nextDouble() - 0.5D) * (double)target.width, 0.0D, 0.0D, 0.0D, -1), target.dimension);
		}
		return true;
	}
	
	@Override
	public void registerModels()
	{
		EMCore.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
}
