package com.water.elementmod.items.weapons;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.water.elementmod.EMCore;
import com.water.elementmod.EMCoreItems;
import com.water.elementmod.entity.monster.EntityFireSkeleton;
import com.water.elementmod.entity.monster.EntityFireZombie;
import com.water.elementmod.events.EventDrownDebuff;
import com.water.elementmod.events.EventWaterSwordAbility;
import com.water.elementmod.network.PacketAbilityReadyFireData;
import com.water.elementmod.network.PacketAbilityReadyWaterData;
import com.water.elementmod.network.PacketHandler;
import com.water.elementmod.network.PacketParticleData;
import com.water.elementmod.util.EMConfig;
import com.water.elementmod.util.interfaces.IHasModel;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WaterSword extends ItemSword implements IHasModel
{
	private int level;
	private ToolMaterial material;
	private List drowndingTime = new ArrayList();
	private List drowndingEntities = new ArrayList();
	private List abilityTimer = new ArrayList();
	private List abilityTimerTotal = new ArrayList();
	private List abilityPlayers = new ArrayList();
	private List abilityPlayerCD = new ArrayList();
	
	public WaterSword(String name, Integer level, ToolMaterial material) 
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
	
	/**
	* Returns the Roman Numeral for the sword's level
	*/
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
	
	//@SideOnly(Side.CLIENT)
	//public boolean hasEffect(ItemStack par1ItemStack)
	//{
	//	return true;
	//}
	
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
	
	/**
	* Returns how long the entity will drownd for. Both parameters cannot be true.
	* @param isRandom returns the random version of the drownd duration of the sword's level
	* @param isMax returns the maximum possible drownd duration for the sword's level
	*/
	public int getDrowndDuration(Boolean isRandom, Boolean isMax)
	{
		if(isRandom && isMax)
		{
			throw new IllegalArgumentException("isRandom and isMax cannot both be true");
		}
		
		int i = 0;
		int Range = 4;
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
					i = 7;
					if(!isRandom) return i;
					return i + rand.nextInt(Range + 1);
				case 10:
					i = 8;
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
				i = 3;
				return i;
			case 6:
				i = 4;
				return i;
			case 7:
				i = 4;
				return i;
			case 8:
				i = 5;
				return i;
			case 9:
				i = 5;
				return i;
			case 10:
				i = 6;
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
	* Checks if the current level's knockback strength
	*/
	public float getKnockbackStrength()
	{
		float i = 0.0F;
		switch(this.level)
		{
			case 1:
				i = 0.0F;
				return i;
			case 2:
				i = 0.0F;
				return i;
			case 3:
				i = 0.0F;
				return i;
			case 4:
				i = 0.0F;
				return i;
			case 5:
				i = 0.5F;
				return i;
			case 6:
				i = 0.6F;
				return i;
			case 7:
				i = 0.7F;
				return i;
			case 8:
				i = 0.8F;
				return i;
			case 9:
				i = 0.9F;
				return i;
			case 10:
				i = 1.0F;
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
		list.add(I18n.format("tooltip.WaterEnchant"));
	    list.add(I18n.format("tooltip.EnchantWaterLevel") + " " + intToNumeral() + I18n.format("tooltip.ResetFormatting"));
	    list.add(I18n.format("tooltip.WaterFormatting") + "+" + this.getAddedDamage() + " " + I18n.format("tooltip.MoreAttackDamage") + I18n.format("tooltip.ResetFormatting"));
	    
	    if(GuiScreen.isAltKeyDown()){
	    	//list.remove(I18n.format("tooltip.PressAlt") + I18n.format("tooltip.ResetFormatting"));
	    	list.add("");
	    	list.add(I18n.format("tooltip.WaterFormatting") + "+" + Math.round(this.getAttackDamage() / 3) + I18n.format("tooltip.FireDamageBoost"));
	    	list.add(I18n.format("tooltip.WaterDuration") + this.getDrowndDuration(false, false) + "-" + (this.getDrowndDuration(false, false) + this.getDrowndDuration(false, true) ) + "s" + I18n.format("tooltip.ResetFormatting"));
	    	if(getEliagibleForAbility())
	    	{
	    		list.add(I18n.format("tooltip.WaterAbilityDuration") + this.getAbilityDuration() + "s" + I18n.format("tooltip.ResetFormatting"));
	    		list.add(I18n.format("tooltip.WaterAbilityRadius") + this.getAbilityRadius() + " blocks" + I18n.format("tooltip.ResetFormatting"));
	    		list.add(I18n.format("tooltip.WaterAbilityCDDuration") + this.getAbilityCooldown() + "s" + I18n.format("tooltip.ResetFormatting"));
	    		list.add(I18n.format("tooltip.WaterKnockbackStrength") + "x" + (this.getKnockbackStrength() * 2) + I18n.format("tooltip.ResetFormatting"));
	    	}
	    } else {
	    	list.add(I18n.format("tooltip.PressAlt") + I18n.format("tooltip.ResetFormatting"));
	    }
	    
	    list.add(""); // Works the same way as \n
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if(target instanceof EntityFireZombie || target instanceof EntityFireSkeleton)
		{
			target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), this.getAttackDamage() / 3);
		}
		target.extinguish();
		EventDrownDebuff.playerHitEntity(target, getDrowndDuration(true, false), this.level);
		stack.damageItem(1, attacker);
		WaterParticleEffect(target, target.world);
	    return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn)
    {
		if(!this.getEliagibleForAbility()) return new ActionResult<ItemStack>(EnumActionResult.FAIL, player.getHeldItem(handIn));
		EventWaterSwordAbility.playerActivateAbility(worldIn, player, this.getAbilityRadius(), this.getAbilityDuration(), this.getAbilityCooldown(), this.level, this.getKnockbackStrength());
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(handIn));
	}
	
	public void WaterParticleEffect(EntityLivingBase target, World world)
	{
		for(int countparticles = 0; countparticles <= 18 * this.level / 2; ++countparticles)
		{
			Random rand = new Random();
			PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 5, target.posX + (rand.nextDouble() - 0.5D) * target.width, target.posY + rand.nextDouble() * target.height - target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * target.width, 0.0D, 0.0D,0.0D, -1), target.dimension);
			PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 39, target.posX + (rand.nextDouble() - 0.5D) * target.width, target.posY + rand.nextDouble() * target.height - target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * target.width, 0.0D, 0.0D,0.0D, -1), target.dimension);
		}
		for(int countparticles = 0; countparticles <= 60 * this.level / 2; ++countparticles)
		{
			Random rand = new Random();
			PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 4, target.posX + (rand.nextDouble() - 0.5D) * target.width, target.posY + rand.nextDouble() * target.height - target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * target.width, 0.0D, 0.0D,0.0D, -1), target.dimension);
		}
	}
	
	@Override
	public void registerModels()
	{
		EMCore.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
}