package com.water.elementmod.items.weapons;

import java.util.ArrayList;
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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
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
	private int abilityCD = 10;
	private ToolMaterial material;
	private List abilityTimer = new ArrayList();
	private List abilityPlayers = new ArrayList();
	private List abilityPlayerCD = new ArrayList();
	private List abilityAoePoints = new ArrayList();
	private List abilityAoeTimers = new ArrayList();
	
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
				i = 5;
				return i;
			case 6:
				i = 5;
				return i;
			case 7:
				i = 6;
				return i;
			case 8:
				i = 7;
				return i;
			case 9:
				i = 7;
				return i;
			case 10:
				i = 8;
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
	    	if(this.getEliagibleForAbility())
	    	{
	    		list.add(I18n.format("tooltip.FireAbilityDuration") + this.getAbilityDuration() + "s" + I18n.format("tooltip.ResetFormatting"));
	    	}
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
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
	{
		if(!par2World.isRemote)
		{
			for(int i = 0; i < this.abilityPlayers.size(); i++)
			{
				int playerAbilityRemaining = (Integer)this.abilityTimer.get(i);
				int playerAbilityRemainingCD = (Integer)this.abilityPlayerCD.get(i);
				EntityLivingBase currentPlayer = (EntityLivingBase)this.abilityPlayers.get(i);
				if(playerAbilityRemaining != 0)
				{
					if(playerAbilityRemaining % ((this.getAbilityDuration() * 20) / 4) == 0) spawnAoeCircle(currentPlayer.posX, currentPlayer.posY, currentPlayer.posZ, playerAbilityRemaining);
				}

				if(currentPlayer != null)
				{
					if(!currentPlayer.isDead)
					{
						if((Integer)this.abilityTimer.get(i) > 0)
						{
							this.abilityTimer.set(i, playerAbilityRemaining - 1);
						}
						else
						{
							if((Integer)this.abilityPlayerCD.get(i) > 0)
							{
								this.abilityPlayerCD.set(i, playerAbilityRemainingCD - 1);
							}
							else
							{
								this.abilityTimer.remove(i);
								this.abilityPlayers.remove(i);
								this.abilityPlayerCD.remove(i);
							}
						}
					}
					else
					{
						this.abilityTimer.remove(i);
						this.abilityPlayers.remove(i);
						this.abilityPlayerCD.remove(i);
					}
				}
			}
			
			for(int i = 0; i < this.abilityAoePoints.size(); i++)
			{
				int CircleTimer = (Integer)this.abilityAoeTimers.get(i);
				ArrayList pos = (ArrayList)this.abilityAoePoints.get(i);
				if((Integer)this.abilityAoeTimers.get(i) > 0)
				{
					if((Integer)this.abilityAoeTimers.get(i) % 20 == 0)
					{
						FireRingAnimation((double)pos.get(0), (double)pos.get(1), (double)pos.get(2), 4);
					}
					
					if((Integer)this.abilityAoeTimers.get(i) % 5 == 0)
					{
						FireSpitAnimation((double)pos.get(0), (double)pos.get(1), (double)pos.get(2));
					}
					this.abilityAoeTimers.set(i, CircleTimer - 1);
				}
				else
				{
					this.abilityAoePoints.remove(i);
					this.abilityAoeTimers.remove(i);
				}
			}
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn)
    {
		for(int i = 0; i < this.abilityPlayers.size(); i++)
		{
			EntityPlayer entityPlayer = (EntityPlayer)this.abilityPlayers.get(i);
			
			if(entityPlayer == player)
			{
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, player.getHeldItem(handIn));
			}
		}
		
		activateAbility(player, this.getAbilityDuration());
		
		player.addPotionEffect(new PotionEffect(MobEffects.SPEED, this.getAbilityDuration() * 20, 1));
		player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, this.getAbilityDuration() * 20, 1));
		
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(handIn));
    }
	
	public void activateAbility(EntityPlayer player, Integer time)
	{
		this.abilityPlayers.add(player);
		this.abilityTimer.add(time * 20);
		this.abilityPlayerCD.add(this.abilityCD * 20);
	}
	
	public void spawnAoeCircle(double x, double y, double z, Integer time)
	{
		List pos = new ArrayList();
		pos.add(x);
		pos.add(y);
		pos.add(z);
		this.abilityAoePoints.add(pos);
		this.abilityAoeTimers.add(time);
	}
	
	public boolean FireRingAnimation(double x, double y, double z, double radius)
	{
		World world = Minecraft.getMinecraft().world;
		if(world == null) return false;
		Random rand = new Random();
		for(double r = 0.6D; r <= radius; r += 0.2D)
		{
			for(float i = 0.0F; i < 360.0F; i += 15.0F)
			{
				double deltaX = Math.cos(Math.toRadians(i))*r + rand.nextDouble();
				double deltaZ = -Math.sin(Math.toRadians(i))*r + rand.nextDouble();
				double finalX = x - 0.5D + deltaX;
				double finalZ = z - 0.5D + deltaZ;
			    
				world.spawnParticle(EnumParticleTypes.FLAME, finalX, y + 0.15D, finalZ, 0.0D,0.0D,0.0D);
			}
			
			
			world.spawnParticle(EnumParticleTypes.LAVA, x, y + 0.15D, z, 0.0D,0.0D,0.0D);
		}
		
		for(float i = 0.0F; i < 360.0F; i += 2.0F)
		{
			double deltaX = Math.cos(Math.toRadians(i))*(radius + 0.4D) + rand.nextDouble();
			double deltaZ = -Math.sin(Math.toRadians(i))*(radius + 0.4D) + rand.nextDouble();
			double finalX = x - 0.5D + deltaX;
			double finalZ = z - 0.5D + deltaZ;
		    
			world.spawnParticle(EnumParticleTypes.DRIP_LAVA, finalX, y + 0.15D, finalZ, 0.0D,0.0D,0.0D);
		}
		return true;
	}
	
	public boolean FireSpitAnimation(double x, double y, double z)
	{
		World world = Minecraft.getMinecraft().world;
		if(world == null) return false;
		world.spawnParticle(EnumParticleTypes.LAVA, x, y + 0.15D, z, 0.0D,0.0D,0.0D);
		
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
	public void registerModels()
	{
		EMCore.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
}
