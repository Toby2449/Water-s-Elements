package com.water.elementmod.items.weapons;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.water.elementmod.EMCore;
import com.water.elementmod.EMCoreItems;
import com.water.elementmod.EMCorePotionEffects;
import com.water.elementmod.entity.boss._void.EntityLunacy;
import com.water.elementmod.entity.boss._void.EntitySlaveMaster;
import com.water.elementmod.entity.boss._void.EntityVoidEntity;
import com.water.elementmod.entity.boss._void.EntityVoidSpectralLarge;
import com.water.elementmod.entity.boss._void.EntityVoidSpectralMedium;
import com.water.elementmod.entity.boss._void.EntityVoidSpectralSmall;
import com.water.elementmod.entity.boss.overworld.EntityVoidBlob;
import com.water.elementmod.entity.boss.overworld.EntityVoidKnight;
import com.water.elementmod.entity.boss.overworld.EntityVoidSmasher;
import com.water.elementmod.entity.monster.EntityNatureSkeleton;
import com.water.elementmod.entity.monster.EntityNatureZombie;
import com.water.elementmod.entity.monster.EntityWaterZombie;
import com.water.elementmod.events.EventFireSwordAbility;
import com.water.elementmod.network.PacketAbilityReadyFireData;
import com.water.elementmod.network.PacketHandler;
import com.water.elementmod.network.PacketParticleData;
import com.water.elementmod.particle.EnumCustomParticleTypes;
import com.water.elementmod.particle.ParticleSpawner;
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

public class VoidSword extends ItemSword implements IHasModel
{
	private ToolMaterial material;
	
	public VoidSword(String name, ToolMaterial material) 
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(EMCore.TAB_WEAPONS);
		this.material = material;
		EMCoreItems.ITEMS.add(this);
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack)
	{
		return true;
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
    {
		return false;
    }
	
	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) 
	{
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn)
	{
		list.add(I18n.format("tooltip.VoidEnchant"));
		list.add(I18n.format("tooltip.CorruptionEffect"));
		list.add(I18n.format("tooltip.DamageBoostVoid"));
	    list.add(""); // Works the same way as \n
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if(target instanceof EntityVoidEntity || target instanceof EntityLunacy || target instanceof EntitySlaveMaster || target instanceof EntityVoidSpectralSmall || target instanceof EntityVoidSpectralMedium || target instanceof EntityVoidSpectralLarge || target instanceof EntityVoidBlob || target instanceof EntityVoidSmasher || target instanceof EntityVoidKnight)
		{
			target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), this.getAttackDamage() + (this.getAttackDamage() / 4));
		}
		VoidParticleEffect(target);
		target.addPotionEffect(new PotionEffect(EMCorePotionEffects.POTION_CORRUPTION, 100, 0));
		stack.damageItem(1, attacker);
	    return true;
	}
	
	public void VoidParticleEffect(EntityLivingBase entity)
	{
		Random rand = new Random();
		for (int i = 0; i < 120; i++)
        {
        	ParticleSpawner.spawnParticle(EnumCustomParticleTypes.DARK_PURPLE_SMOKE, entity.posX + (rand.nextDouble() - 0.5D) * (double)entity.width, entity.posY + rand.nextDouble() * (double)entity.height, entity.posZ + (rand.nextDouble() - 0.5D) * (double)entity.width, 0.0D, 0.0D, 0.0D);
        	}
		
		for (int i = 0; i < 60; i++)
        {
			ParticleSpawner.spawnParticle(EnumCustomParticleTypes.DARK_PURPLE_SMOKE_BLOCK, entity.posX + (rand.nextDouble() - 0.5D) * (double)entity.width, entity.posY + rand.nextDouble() * (double)entity.height, entity.posZ + (rand.nextDouble() - 0.5D) * (double)entity.width, (rand.nextDouble() - 0.5D) * 2.0D, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2.0D);
	    }
	}
	
	@Override
	public void registerModels()
	{
		EMCore.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
}