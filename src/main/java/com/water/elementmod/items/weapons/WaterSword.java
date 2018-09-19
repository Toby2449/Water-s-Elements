package com.water.elementmod.items.weapons;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.water.elementmod.EMCore;
import com.water.elementmod.EMCoreItems;
import com.water.elementmod.util.interfaces.IHasModel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WaterSword extends ItemSword implements IHasModel
{
	private int level;
	private int abilityCD = 10;
	private ToolMaterial material;
	private List drowndingTime = new ArrayList();
	private List drowndingEntities = new ArrayList();
	private List abilityTimer = new ArrayList();
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
	
	public int getDrowndDuration(Boolean isRandom, Boolean isMax)
	{
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
				i = 2;
				return i;
			case 6:
				i = 3;
				return i;
			case 7:
				i = 3;
				return i;
			case 8:
				i = 4;
				return i;
			case 9:
				i = 4;
				return i;
			case 10:
				i = 4;
				return i;
		}
		return i;
	}
	
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
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn)
	{
		list.add(I18n.format("tooltip.WaterEnchant"));
	    list.add(I18n.format("tooltip.EnchantWaterLevel") + " " + intToNumeral() + I18n.format("tooltip.ResetFormatting"));
	    
	    if(GuiScreen.isAltKeyDown()){
	    	//list.remove(I18n.format("tooltip.PressAlt") + I18n.format("tooltip.ResetFormatting"));
	    	list.add("");
	    	list.add(I18n.format("tooltip.2xDamageFire"));
	    	list.add(I18n.format("tooltip.WaterFormatting") + "+" + this.getAddedDamage() + " " + I18n.format("tooltip.MoreAttackDamage") + I18n.format("tooltip.ResetFormatting"));
	    	list.add(I18n.format("tooltip.WaterDuration") + this.getDrowndDuration(false, false) + "-" + (this.getDrowndDuration(false, false) + this.getDrowndDuration(false, true) ) + "s" + I18n.format("tooltip.ResetFormatting"));
	    	if(getEliagibleForAbility())
	    	{
	    		list.add(I18n.format("tooltip.WaterAbilityDuration") + this.getAbilityDuration() + "s" + I18n.format("tooltip.ResetFormatting"));
	    		list.add(I18n.format("tooltip.WaterAbilityCDDuration") + this.abilityCD + "s" + I18n.format("tooltip.ResetFormatting"));
	    	}
	    } else {
	    	list.add(I18n.format("tooltip.PressAlt") + I18n.format("tooltip.ResetFormatting"));
	    }
	    
	    list.add(""); // Works the same way as \n
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		target.extinguish();
		setDrownding(target, getDrowndDuration(true, false));
		stack.damageItem(1, attacker);
		WaterParticleEffect(target);
	    return true;
	}
	
	private void setDrownding(EntityLivingBase target, Integer time) 
	{
		this.drowndingTime.add(time * 20);
		this.drowndingEntities.add(target);
		
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
	{
		if(!par2World.isRemote)
		{
			int p = 0;
			while(p < this.abilityPlayers.size())
			{
				int playerAbilityRemaining = (Integer)this.abilityTimer.get(p);
				int playerAbilityCDRemaining = (Integer)this.abilityPlayerCD.get(p);
				EntityPlayer currentPlayer = (EntityPlayer) this.abilityPlayers.get(p);
				if(currentPlayer != null)
				{
					if(!currentPlayer.isDead)
					{
						if((Integer)this.abilityTimer.get(p) > 0)
						{
							WaterAbilityParticleEffect(currentPlayer);
							
							this.abilityTimer.set(p, playerAbilityRemaining - 1);
						}
						else
						{
							if((Integer)this.abilityPlayerCD.get(p) > 0)
							{
								this.abilityPlayerCD.set(p, playerAbilityCDRemaining - 1);
							}
							else
							{
								this.abilityTimer.remove(p);
								this.abilityPlayers.remove(p);
								this.abilityPlayerCD.remove(p);
							}
						}
					}
					else
					{
						this.abilityTimer.remove(p);
						this.abilityPlayers.remove(p);
						this.abilityPlayerCD.remove(p);
					}
				}
				
				p++;
			}
			
			int i = 0;
			while(i < this.drowndingEntities.size())
			{
				int drowndingTimeInstance = (Integer)this.drowndingTime.get(i);
				EntityLivingBase currentEnt = (EntityLivingBase) this.drowndingEntities.get(i);
				if(currentEnt != null)
				{
					if(!currentEnt.isDead)
					{
						if((Integer)this.drowndingTime.get(i) > 0) 
						{
							if ((Integer)this.drowndingTime.get(i) % 20 == 0)
						    {
								WaterParticleEffect(currentEnt);
								currentEnt.attackEntityFrom(DamageSource.DROWN, 0.5F);
						    }
						
						    this.drowndingTime.set(i, drowndingTimeInstance - 1);
						}
					}
					else
					{
						this.drowndingTime.remove(i);
						this.drowndingEntities.remove(i);
					}
				}
				
				i++;
			}
		}
		
	}
	
	public boolean WaterParticleEffect(EntityLivingBase target)
	{
		World world = Minecraft.getMinecraft().world;
		if(world == null) return false;
		for(int countparticles = 0; countparticles <= 18 * this.level / 2; ++countparticles)
		{
			Random rand = new Random();
			world.spawnParticle(EnumParticleTypes.WATER_SPLASH, target.posX + (rand.nextDouble() - 0.5D) * (double)target.width, target.posY + rand.nextDouble() * (double)target.height - (double)target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * (double)target.width, 0.0D, 0.0D,0.0D);
			world.spawnParticle(EnumParticleTypes.WATER_DROP, target.posX + (rand.nextDouble() - 0.5D) * (double)target.width, target.posY + rand.nextDouble() * (double)target.height - (double)target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * (double)target.width, 0.0D, 0.0D,0.0D);
		}
		for(int countparticles = 0; countparticles <= 60 * this.level / 2; ++countparticles)
		{
			Random rand = new Random();
			world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, target.posX + (rand.nextDouble() - 0.5D) * (double)target.width, target.posY + rand.nextDouble() * (double)target.height - (double)target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * (double)target.width, 0.0D, 0.0D,0.0D);
		}
		return true;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn)
    {
		int i = 0;
		while (i < this.abilityPlayers.size())
		{
			EntityPlayer playercheck = (EntityPlayer) this.abilityPlayers.get(i);
			
			if(playercheck == player)
			{
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, player.getHeldItem(handIn));	
			}
			
			i++;
		}
		
		this.abilityTimer.add(this.getAbilityDuration() * 20);
		this.abilityPlayers.add(player);
		this.abilityPlayerCD.add(this.abilityCD * 20);
		
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(handIn));
	}
	
	public void WaterAbilityParticleEffect(EntityPlayer target) 
	{
		World world = Minecraft.getMinecraft().world;
		
		for(int countparticles = 0; countparticles <= 14; ++countparticles)
		{
			Random rand = new Random();
			world.spawnParticle(EnumParticleTypes.WATER_SPLASH, target.posX + (rand.nextDouble() - 0.5D) * (double)target.width, target.posY + rand.nextDouble() * (double)target.height - (double)target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * (double)target.width, 0.0D, 0.0D,0.0D);
		}
	}
	
	@Override
	public void registerModels()
	{
		EMCore.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
}