package com.water.elementmod.items.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.water.elementmod.Main;
import com.water.elementmod.init.EmItems;
import com.water.elementmod.util.IHasModel;

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
	private ToolMaterial material;
	private List drowndingTime = new ArrayList();
	private List drowndingEntities = new ArrayList();
	
	public WaterSword(String name, Integer level, ToolMaterial material) 
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.tab_weapons);
		canApplyAtEnchantingTable(new ItemStack(this), Enchantments.FIRE_ASPECT);
		
		this.level = level;
		this.material = material;
		EmItems.ITEMS.add(this);
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
		int Range = 2;
		Random rand = new Random();
		if(!isMax) 
		{
			switch(this.level)
			{
			case 1:
				if(!isRandom) return 2;
				return 2 + rand.nextInt(Range + 1);
			case 2:
				if(!isRandom) return 3;
				return 3 + rand.nextInt(Range + 1);
			case 3:
				if(!isRandom) return 4;
				return 4 + rand.nextInt(Range + 1);
			case 4:
				if(!isRandom) return 5;
				return 5 + rand.nextInt(Range + 1);
			case 5:
				if(!isRandom) return 5;
				return 5 + rand.nextInt(Range + 1);
			case 6:
				if(!isRandom) return 6;
				return 6 + rand.nextInt(Range + 1);
			case 7:
				if(!isRandom) return 7;
				return 7 + rand.nextInt(Range + 1);
			case 8:
				if(!isRandom) return 8;
				return 8 + rand.nextInt(Range + 1);
			case 9:
				if(!isRandom) return 9;
				return 9 + rand.nextInt(Range + 1);
			case 10:
				if(!isRandom) return 10;
				return 10 + rand.nextInt(Range + 1); // Have to add +1 because it goes from 0-1 (which is 2 numbers)
			}
		}
		return Range;
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
			int i = 0;
			while(i < this.drowndingEntities.size())
			{
				int drownding = i;
				int drowndingTimeInstance = (Integer)this.drowndingEntities.get(i);
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
								currentEnt.attackEntityFrom(DamageSource.DROWN, 1.0F);
						    }
						
						    this.drowndingTime.set(i, drowndingTimeInstance - 1);
						}
						else
						{
							this.drowndingEntities.remove(i);
						}
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
		for(int countparticles = 0; countparticles <= 14 * this.level / 2; ++countparticles)
		{
			Random rand = new Random();
			world.spawnParticle(EnumParticleTypes.WATER_SPLASH, target.posX + (rand.nextDouble() - 0.5D) * (double)target.width, target.posY + rand.nextDouble() * (double)target.height - (double)target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * (double)target.width, 0.0D, 0.0D,0.0D);
			world.spawnParticle(EnumParticleTypes.WATER_DROP, target.posX + (rand.nextDouble() - 0.5D) * (double)target.width, target.posY + rand.nextDouble() * (double)target.height - (double)target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * (double)target.width, 0.0D, 0.0D,0.0D);
		}
		for(int countparticles = 0; countparticles <= 40 * this.level / 2; ++countparticles)
		{
			Random rand = new Random();
			world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, target.posX + (rand.nextDouble() - 0.5D) * (double)target.width, target.posY + rand.nextDouble() * (double)target.height - (double)target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * (double)target.width, 0.0D, 0.0D,0.0D);
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
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
}
