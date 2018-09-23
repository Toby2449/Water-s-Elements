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
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
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
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
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
		target.extinguish();
		setDrownding(target, getDrowndDuration(true, false));
		stack.damageItem(1, attacker);
		WaterParticleEffect(target);
	    return true;
	}
	
	/**
	* Adds an entity to the array so they drownd
	*/
	private void setDrownding(EntityLivingBase target, Integer time) 
	{
		for(int i = 0; i < this.drowndingEntities.size(); i++)
		{
			if(this.drowndingEntities.get(i) == target)
			{
				// Remove old timer
				this.drowndingTime.remove(i);
				this.drowndingEntities.remove(i);
			}
			
			// Add a new one
			this.drowndingTime.add(time * 20);
			this.drowndingEntities.add(target);
		}
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
	{
		if(!par2World.isRemote)
		{
			for(int i = 0; i < this.abilityPlayers.size(); i++)
			{
				int playerAbilityRemaining = (Integer)this.abilityTimer.get(i);
				int playerAbilityCDRemaining = (Integer)this.abilityPlayerCD.get(i);
				EntityPlayer currentPlayer = (EntityPlayer) this.abilityPlayers.get(i);
				if(currentPlayer != null)
				{
					if(!currentPlayer.isDead)
					{
						if((Integer)this.abilityTimer.get(i) > 0)
						{
							WaterAbilityParticleEffect(currentPlayer);
							
							this.abilityTimer.set(i, playerAbilityRemaining - 1);
						}
						else
						{
							if((Integer)this.abilityPlayerCD.get(i) > 0)
							{
								this.abilityPlayerCD.set(i, playerAbilityCDRemaining - 1);
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
			
			for(int i = 0; i < this.drowndingEntities.size(); i++)
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
		Vec3d playerpos = player.getPositionVector();
		
		// Checks if the player is still on cooldown
		for(int i = 0; i < this.abilityPlayers.size(); i++)
		{
			EntityPlayer playercheck = (EntityPlayer) this.abilityPlayers.get(i);
			
			// If player is in the table, return fail
			if(playercheck == player)
			{
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, player.getHeldItem(handIn));	
			}
		}
		
		// Add the player to table so they can activate the ability
		this.abilityTimer.add(this.getAbilityDuration() * 20);
		this.abilityPlayers.add(player);
		this.abilityPlayerCD.add(this.abilityCD * 20);
		WaveAnimation((EntityLivingBase)player);
		
		
		// Extend the players hitbox
		AxisAlignedBB e = player.getEntityBoundingBox().grow(4.0D, 4.0D, 4.0D);
		
		List<EntityMob> listMobs = worldIn.<EntityMob>getEntitiesWithinAABB(EntityMob.class, e);
		List<EntityPlayer> listPlayers = worldIn.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, e);

		// Get current mobs inside the player's ability aabb
        if (!listMobs.isEmpty())
        {
            for (EntityMob entitymob : listMobs)
            {
            	// Knockback the entity and set a potion effect
            	LesserWaterParticleEffect((EntityLivingBase)entitymob);
            	Vec3d targetpos = entitymob.getPositionVector();
	    		entitymob.knockBack(player, this.getKnockbackStrength(), playerpos.x - targetpos.x, playerpos.z - targetpos.z);
	    		int potionstrength = 0;
        		if(this.level >= 8) potionstrength = 1;
            	entitymob.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, this.getAbilityDuration() * 20 / 2, potionstrength));
            }
        }
        
        // Get all current players inside the player's ability aabb
        if (!listPlayers.isEmpty())
        {
            for (EntityPlayer entityplayer : listPlayers)
            {
            	// Check if the player is the player that activates the ability
            	if(entityplayer == player)
            	{
            		entityplayer.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 30, 1)); // 1.5 seconds
            		entityplayer.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, this.getAbilityDuration() * 20, 1));
            	}
            	else
            	{
            		LesserWaterParticleEffect((EntityLivingBase)entityplayer);
            		Vec3d targetpos = entityplayer.getPositionVector();
            		entityplayer.knockBack(player, this.getKnockbackStrength() / 2, playerpos.x - targetpos.x, playerpos.z - targetpos.z);
            		int potionstrength = 0;
            		if(this.level >= 8) potionstrength = 1;
            		entityplayer.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, this.getAbilityDuration() * 20 / 2, potionstrength));
            	}
            }
        }
		
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(handIn));
	}
	
	
	public boolean WaterAbilityParticleEffect(EntityPlayer target) 
	{
		World world = Minecraft.getMinecraft().world;
		if(world == null) return false;
		for(int countparticles = 0; countparticles <= 14; ++countparticles)
		{
			Random rand = new Random();
			world.spawnParticle(EnumParticleTypes.WATER_SPLASH, target.posX + (rand.nextDouble() - 0.5D) * (double)target.width, target.posY + rand.nextDouble() * (double)target.height - (double)target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * (double)target.width, 0.0D, 0.0D,0.0D);
		}
		return true;
	}
	
	public boolean WaveAnimation(EntityLivingBase target)
	{
		World world = Minecraft.getMinecraft().world;
		if(world == null) return false;
		
		for(double r = 0.6D; r <= 4.0D; r += 0.2D)
		{
			for(float i = 0.0F; i < 360.0F; i += 5.0F)
			{
				double deltaX = Math.cos(Math.toRadians(i))*r;
				double deltaZ = -Math.sin(Math.toRadians(i))*r;
				double finalX = target.posX + deltaX;
				double finalZ = target.posZ + deltaZ;
			    
				world.spawnParticle(EnumParticleTypes.WATER_SPLASH, finalX, target.posY, finalZ, 0.0D,0.0D,0.0D);
			}
		}
		
		for(float i = 0.0F; i < 360.0F; i += 2.0F)
		{
			double radius = 4.0D;
			double deltaX = Math.cos(Math.toRadians(i))*radius;
			double deltaZ = -Math.sin(Math.toRadians(i))*radius;
			double finalX = target.posX + deltaX;
			double finalZ = target.posZ + deltaZ;
		    
			for(double p = 4.0D; p >= 0.0D; p -= 0.2D)
			{
				world.spawnParticle(EnumParticleTypes.WATER_SPLASH, finalX, target.posY + p, finalZ, 0.0D,0.0D,0.0D);
			}
		}
		
		return true;
	}
	
	public boolean LesserWaterParticleEffect(EntityLivingBase target)
	{
		World world = Minecraft.getMinecraft().world;
		if(world == null) return false;
		for(int countparticles = 0; countparticles <= 13 * this.level / 2; ++countparticles)
		{
			Random rand = new Random();
			world.spawnParticle(EnumParticleTypes.WATER_SPLASH, target.posX + (rand.nextDouble() - 0.5D) * (double)target.width, target.posY + rand.nextDouble() * (double)target.height - (double)target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * (double)target.width, 0.0D, 0.0D,0.0D);
			world.spawnParticle(EnumParticleTypes.WATER_DROP, target.posX + (rand.nextDouble() - 0.5D) * (double)target.width, target.posY + rand.nextDouble() * (double)target.height - (double)target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * (double)target.width, 0.0D, 0.0D,0.0D);
		}
		for(int countparticles = 0; countparticles <= 50 * this.level / 2; ++countparticles)
		{
			Random rand = new Random();
			world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, target.posX + (rand.nextDouble() - 0.5D) * (double)target.width, target.posY + rand.nextDouble() * (double)target.height - (double)target.getYOffset(), target.posZ + (rand.nextDouble() - 0.5D) * (double)target.width, 0.0D, 0.0D,0.0D);
		}
		return true;
	}
	
	@Override
	public void registerModels()
	{
		EMCore.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
}
