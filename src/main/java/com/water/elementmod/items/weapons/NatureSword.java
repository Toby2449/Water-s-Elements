package com.water.elementmod.items.weapons;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.water.elementmod.EMCore;
import com.water.elementmod.EMCoreItems;
import com.water.elementmod.entity.EntityFireZombie;
import com.water.elementmod.entity.EntityWaterSkeleton;
import com.water.elementmod.entity.EntityWaterZombie;
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
	private List superPoisonTime = new ArrayList();
	private List superPoisonEntities = new ArrayList();
	private List abilityTimer = new ArrayList();
	private List abilityPlayers = new ArrayList();
	private List abilityPlayerCD = new ArrayList();
	private List abilityAoePoints = new ArrayList();
	private List abilityAoeTimers = new ArrayList();
	
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
				i = 5;
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
		this.superPoisonEntities.add(target);
		this.superPoisonTime.add(this.getPosionDuration(true, false) * 25);
		NatureParticleHitEffect(target, target.world);
		stack.damageItem(1, attacker);
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
				int playerAbilityCDRemaining = (Integer)this.abilityPlayerCD.get(i);
				EntityPlayer currentPlayer = (EntityPlayer) this.abilityPlayers.get(i);
				
				if(playerAbilityCDRemaining == 0)
				{
					PacketHandler.INSTANCE.sendTo(new PacketAbilityReadyNatureData(currentPlayer, par2World), (EntityPlayerMP) par3Entity);
				}
				
				if(currentPlayer != null)
				{
					if(!currentPlayer.isDead)
					{
						if((Integer)this.abilityTimer.get(i) > 0)
						{
							LesserNatureParticleEffect(currentPlayer, par2World);
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
			
			for(int i = 0; i < this.superPoisonEntities.size(); i++)
			{
				int superPoisonTimeInstance = (Integer)this.superPoisonTime.get(i);
				EntityLivingBase currentEnt = (EntityLivingBase) this.superPoisonEntities.get(i);
				if(currentEnt != null)
				{
					if(!currentEnt.isDead)
					{
						if((Integer)this.superPoisonTime.get(i) > 0) 
						{
							if ((Integer)this.superPoisonTime.get(i) % 25 == 0)
						    {
								if(currentEnt.getHealth() > 0.5)
								{
									currentEnt.attackEntityFrom(DamageSource.MAGIC, 0.5F);
								}
						    }
							
							NatureParticleEffect(currentEnt, par2World);
							this.superPoisonTime.set(i, superPoisonTimeInstance - 1);
							
						}
						else
						{
							this.superPoisonTime.remove(i);
							this.superPoisonEntities.remove(i);
						}
					}
					else
					{
						this.superPoisonTime.remove(i);
						this.superPoisonEntities.remove(i);
					}
				}
				
				i++;
			}
			
			for(int i = 0; i < this.abilityAoePoints.size(); i++)
			{
				int CircleTimer = (Integer)this.abilityAoeTimers.get(i);
				ArrayList pos = (ArrayList)this.abilityAoePoints.get(i);
				if((Integer)this.abilityTimer.get(i) == (this.getAbilityDuration() * 20) - 1) HealingAoeAnimation((double)pos.get(0), (double)pos.get(1), (double)pos.get(2), this.getAbilityRadius(), par2World, par3Entity);
				
				AxisAlignedBB AoePoint = new AxisAlignedBB((double)pos.get(0) - this.getAbilityRadius(), (double)pos.get(1) - 0.5D, (double)pos.get(2) - this.getAbilityRadius(), (double)pos.get(0) + this.getAbilityRadius(), (double)pos.get(1) + 4.0D, (double)pos.get(2) + this.getAbilityRadius());
				List<EntityPlayer> AABBPlayer = par2World.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, AoePoint);
				
				if (!AABBPlayer.isEmpty())
		        {
		            for (EntityPlayer ent : AABBPlayer)
		            {
		            	if(!ent.isPotionActive(MobEffects.REGENERATION)) 
		            	{
		            		ent.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 50, this.getRegenerationLevel()));
		            	}
		            }
		        }
				
				if((Integer)this.abilityAoeTimers.get(i) > 0)
				{
					if((Integer)this.abilityAoeTimers.get(i) % 5 == 0)
					{
						HealingAoeAnimation((double)pos.get(0), (double)pos.get(1), (double)pos.get(2), this.getAbilityRadius(), par2World, par3Entity);
					}
					if((Integer)this.abilityAoeTimers.get(i) % 5 == 0)
					{
						HeartParticleSpawner((double)pos.get(0), (double)pos.get(1), (double)pos.get(2), this.getAbilityRadius(), par2World, par3Entity);
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
		if(!this.getEliagibleForAbility()) return new ActionResult<ItemStack>(EnumActionResult.FAIL, player.getHeldItem(handIn));
		
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
		this.abilityPlayerCD.add(this.getAbilityCooldown() * 20);

		List pos = new ArrayList();
		pos.add(player.posX);
		pos.add(player.posY);
		pos.add(player.posZ);
		this.abilityAoePoints.add(pos);
		this.abilityAoeTimers.add(this.getAbilityDuration() * 20);
		
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(handIn));
	}
	
	public void NatureParticleEffect(EntityLivingBase target, World world)
	{
		Random rand = new Random();
		PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 15, target.posX + (rand.nextDouble() - 0.5D) * (double)target.width, target.posY + rand.nextDouble() * (double)target.height - 0.25D, target.posZ + (rand.nextDouble() - 0.5D) * (double)target.width, 0.0D, 1.47D, 0.09D, 1), target.dimension);
	}
	
	public void LesserNatureParticleEffect(EntityLivingBase target, World world)
	{
		Random rand = new Random();
		PacketHandler.INSTANCE.sendToDimension(new PacketParticleData(target, world, 21, target.posX + (rand.nextDouble() - 0.5D) * (double)target.width, target.posY + rand.nextDouble() * (double)target.height - 0.25D, target.posZ + (rand.nextDouble() - 0.5D) * (double)target.width, 0.0D, 1.47D, 0.09D, 1), target.dimension);
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
	
	public void HealingAoeAnimation(double x, double y, double z, double radius, World world, Entity ent)
	{
		Random rand = new Random();
		for(double r = 0.6D; r <= radius; r += 0.2D)
		{
			for(float i = 0.0F; i < 360.0F; i += 15.0F)
			{
				double deltaX = Math.cos(Math.toRadians(i))*r + rand.nextDouble();
				double deltaZ = -Math.sin(Math.toRadians(i))*r + rand.nextDouble();
				double finalX = x - 0.5D + deltaX;
				double finalZ = z - 0.5D + deltaZ;
			    
				if(rand.nextDouble() < 0.1D) PacketHandler.INSTANCE.sendToDimension(new PacketCustomParticleData(ent, world, 4, finalX, y + 0.15D, finalZ, 0.0D, 0.0D, 0.0D, -1), ent.dimension);
			}
		}
	}
	
	public void HeartParticleSpawner(double x, double y, double z, double radius, World world, Entity ent)
	{
		Random rand = new Random();
		for(double r = 0.6D; r <= radius; r += 0.2D)
		{
			for(float i = 0.0F; i < 360.0F; i += 15.0F)
			{
				double deltaX = Math.cos(Math.toRadians(i))*r + rand.nextDouble();
				double deltaZ = -Math.sin(Math.toRadians(i))*r + rand.nextDouble();
				double finalX = x - 0.5D + deltaX;
				double finalZ = z - 0.5D + deltaZ;
			    
				if(rand.nextDouble() < 0.005D) PacketHandler.INSTANCE.sendToDimension(new PacketCustomParticleData(ent, world, 2, finalX, y + 0.15D, finalZ, 0.0D, 0.0D, 0.0D, -1), ent.dimension);
			}
		}
	}
	
	@Override
	public void registerModels()
	{
		EMCore.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
}
