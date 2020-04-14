package com.water.elementmod.items.weapons;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.water.elementmod.EMCore;
import com.water.elementmod.EMCoreItems;
import com.water.elementmod.entity.monster.EntityNatureSkeleton;
import com.water.elementmod.entity.monster.EntityNatureZombie;
import com.water.elementmod.entity.monster.EntityWaterZombie;
import com.water.elementmod.events.EventFireSwordAbility;
import com.water.elementmod.network.PacketAbilityReadyFireData;
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
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.server.SPacketParticles;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FireSword extends ItemSword implements IHasModel
{
	private int level;
	private int abilityCD = 10;
	private ToolMaterial material;
	
	//For animation
	private List abilityTimer = new ArrayList();
	private List abilityPlayers = new ArrayList();
	private List abilityPlayerCD = new ArrayList();
	
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
				i = 5;
				return i;
		}
		return i;
	}
	
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
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn)
	{
		list.add(I18n.format("tooltip.FireEnchant"));
	    list.add(I18n.format("tooltip.EnchantFireLevel") + " " + intToNumeral() + I18n.format("tooltip.ResetFormatting"));
	    list.add(I18n.format("tooltip.FireFormatting") + "+" + this.getAddedDamage() + " " + I18n.format("tooltip.MoreAttackDamage") + I18n.format("tooltip.ResetFormatting"));
	    
	    if(GuiScreen.isAltKeyDown()){
	    	//list.remove(I18n.format("tooltip.PressAlt") + I18n.format("tooltip.ResetFormatting"));
	    	list.add("");
	    	list.add(I18n.format("tooltip.FireFormatting") + "+" + Math.round(this.getAttackDamage() / 3) + I18n.format("tooltip.NatureDamageBoost"));
	    	list.add(I18n.format("tooltip.FireDuration") + this.getFireDuration(false, false) + "-" + (this.getFireDuration(false, false) + this.getFireDuration(false, true) ) + "s" + I18n.format("tooltip.ResetFormatting"));
	    	if(this.getEliagibleForAbility())
	    	{
	    		list.add(I18n.format("tooltip.FireAbilityDuration") + this.getAbilityDuration() + "s" + I18n.format("tooltip.ResetFormatting"));
	    		list.add(I18n.format("tooltip.FireAbilityCDDuration") + this.getAbilityCooldown() + "s" + I18n.format("tooltip.ResetFormatting"));
	    	}
	    } else {
	    	list.add(I18n.format("tooltip.PressAlt") + I18n.format("tooltip.ResetFormatting"));
	    }
	    list.add(""); // Works the same way as \n
	}
	
	@Override
	public void onUpdate(ItemStack currentSword, World par2World, Entity par3Entity, int par4, boolean par5) 
	{
		if(!par2World.isRemote)
		{
			for(int i = 0; i < this.abilityPlayers.size(); i++)
			{
				int playerAbilityRemaining = (Integer)this.abilityTimer.get(i);
				int playerAbilityRemainingCD = (Integer)this.abilityPlayerCD.get(i);
				EntityLivingBase currentPlayer = (EntityLivingBase)this.abilityPlayers.get(i);
				
				if(playerAbilityRemainingCD == 0)
				{
					//currentSword.setItemDamage(1);
					System.out.println(currentSword.getItemDamage());
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
		}
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if(target instanceof EntityNatureZombie || target instanceof EntityNatureSkeleton)
		{
			target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), this.getAttackDamage() / 3);
		}
		target.setFire(getFireDuration(true, false));
		stack.damageItem(1, attacker);
		FireParticleEffect(target, target.world);
	    return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn)
    {
		if(!this.getEliagibleForAbility()) return new ActionResult<ItemStack>(EnumActionResult.FAIL, player.getHeldItem(handIn));
		EventFireSwordAbility.playerActivateAbility(worldIn, player, this.getAbilityDuration(), this.getAbilityCooldown());
		
		for(int i = 0; i < abilityPlayers.size(); i++)
		{
			EntityPlayer entityPlayer = (EntityPlayer)abilityPlayers.get(i);
			
			if(entityPlayer == player)
			{
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, player.getHeldItem(handIn));
			}
		}
		
		abilityPlayers.add(player);
		abilityTimer.add(this.getAbilityDuration() * 20);
		abilityPlayerCD.add(this.getAbilityCooldown() * 20);
		
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(handIn));
    }
	
	public void FireParticleEffect(EntityLivingBase target, World world)
	{
		for(int countparticles = 0; countparticles <= 7 * this.level / 2; ++countparticles)
		{
			Random rand = new Random();
			PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 26, target.posX + (rand.nextDouble() - 0.5D) * (double)target.width, target.posY + rand.nextDouble() * (double)target.height - 0.25D, target.posZ + (rand.nextDouble() - 0.5D) * (double)target.width, 0.0D, 0.0D, 0.0D, -1), target.dimension);
        }
		
		for(int countparticles = 0; countparticles <= 10 * this.level / 2; ++countparticles)
		{
			Random rand = new Random();
			PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 27, target.posX + (rand.nextDouble() - 0.5D) * (double)target.width, target.posY + rand.nextDouble() * (double)target.height - 0.25D, target.posZ + (rand.nextDouble() - 0.5D) * (double)target.width, 0.0D, 0.0D, 0.0D, -1), target.dimension);
	    }
    }
	
	@Override
	public void registerModels()
	{
		EMCore.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
}