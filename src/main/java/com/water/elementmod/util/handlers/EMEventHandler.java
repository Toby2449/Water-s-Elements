package com.water.elementmod.util.handlers;

import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.water.elementmod.EMCoreBlocks;
import com.water.elementmod.EMCoreDimensions;
import com.water.elementmod.EMCorePotionEffects;
import com.water.elementmod.entity.boss._void.EntityLunacy;
import com.water.elementmod.entity.boss._void.EntitySlaveMaster;
import com.water.elementmod.entity.boss._void.EntityVoidEntity;
import com.water.elementmod.entity.boss._void.EntityVoidSpectralLarge;
import com.water.elementmod.entity.boss._void.EntityVoidSpectralMedium;
import com.water.elementmod.entity.boss._void.EntityVoidSpectralSmall;
import com.water.elementmod.entity.boss.fire.EntityFireBoss;
import com.water.elementmod.entity.boss.fire.EntityFireBossMinion;
import com.water.elementmod.entity.boss.fire.EntityFireGuardian;
import com.water.elementmod.entity.boss.fire._ConfigEntityFireBoss;
import com.water.elementmod.entity.boss.nature.EntityNatureBoss;
import com.water.elementmod.entity.boss.nature.EntityNatureBossMinion;
import com.water.elementmod.entity.boss.nature._ConfigEntityNatureBoss;
import com.water.elementmod.entity.boss.overworld.EntityVoidBlob;
import com.water.elementmod.entity.boss.overworld.EntityVoidKnight;
import com.water.elementmod.entity.boss.overworld.EntityVoidSmasher;
import com.water.elementmod.entity.boss.overworld._ConfigEntityVoidKnight;
import com.water.elementmod.entity.boss.water.EntityWaterBoss;
import com.water.elementmod.entity.boss.water.EntityWaterBossMeleeMinion;
import com.water.elementmod.entity.boss.water.EntityWaterBossRangedMinion;
import com.water.elementmod.entity.boss.water.EntityWaterTrash;
import com.water.elementmod.entity.boss.water._ConfigEntityWaterBoss;
import com.water.elementmod.items.armor.FireArmor;
import com.water.elementmod.items.armor.LeafArmor;
import com.water.elementmod.items.armor.VoidHelmet;
import com.water.elementmod.items.armor.WaterArmor;
import com.water.elementmod.particle.EnumCustomParticleTypes;
import com.water.elementmod.particle.ParticleSpawner;
import com.water.elementmod.util.EMConfig;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class EMEventHandler {
	public static TextureAtlasSprite textureAtlasSprite;
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerParticleMap(TextureStitchEvent.Pre event) {
        	ResourceLocation lava = new ResourceLocation(EMConfig.MOD_ID, "particle/lava");
            event.getMap().registerSprite(lava);
            
            ResourceLocation leaf = new ResourceLocation(EMConfig.MOD_ID, "particle/leaf");
            event.getMap().registerSprite(leaf);
	}
	
	@SubscribeEvent
	public static void onPlayerAttack(AttackEntityEvent event) 
	{
		// Eliminates PvP in the void
		EntityPlayer player = event.getEntityPlayer();
		Entity target = event.getTarget();
		if (player.dimension == 2) 
		{
			if(target instanceof EntityPlayer)
			{
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) 
	{
		if (event.getEntityPlayer() != null) 
		{
			if(event.getEntityPlayer().dimension == EMCoreDimensions.VOID.getId())
			{
				event.setCanceled(true);
			}
			
			List<EntityNatureBoss> NatureBoss = event.getEntityPlayer().world.<EntityNatureBoss>getEntitiesWithinAABB(EntityNatureBoss.class, event.getEntityPlayer().getEntityBoundingBox().grow(_ConfigEntityNatureBoss.ARENA_SIZE_X, _ConfigEntityNatureBoss.ARENA_SIZE_Y, _ConfigEntityNatureBoss.ARENA_SIZE_Z));
			if(NatureBoss.size() > 0)
			{
				event.setCanceled(true);
			}
			
			List<EntityFireBoss> FireBoss = event.getEntityPlayer().world.<EntityFireBoss>getEntitiesWithinAABB(EntityFireBoss.class, event.getEntityPlayer().getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z));
			if(FireBoss.size() > 0)
			{
				event.setCanceled(true);
			}
			
			List<EntityWaterBoss> WaterBoss = event.getEntityPlayer().world.<EntityWaterBoss>getEntitiesWithinAABB(EntityWaterBoss.class, event.getEntityPlayer().getEntityBoundingBox().grow(_ConfigEntityWaterBoss.ARENA_SIZE_X, _ConfigEntityWaterBoss.ARENA_SIZE_Y, _ConfigEntityWaterBoss.ARENA_SIZE_Z));
			if(WaterBoss.size() > 0)
			{
				event.setCanceled(true);
			}
			
			List<EntityVoidKnight> KnightBoss = event.getEntityPlayer().world.<EntityVoidKnight>getEntitiesWithinAABB(EntityVoidKnight.class, event.getEntityPlayer().getEntityBoundingBox().grow(_ConfigEntityVoidKnight.ARENA_SIZE_X, _ConfigEntityVoidKnight.ARENA_SIZE_Y, _ConfigEntityVoidKnight.ARENA_SIZE_Z));
			if(KnightBoss.size() > 0)
			{
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) 
	{
		IBlockState traceBlockState = event.getEntityPlayer().world.getBlockState(event.getPos());
		
		if (event.getEntityPlayer() != null) 
		{
			if(traceBlockState != Blocks.CHEST.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.NORTH) && traceBlockState != Blocks.CHEST.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.SOUTH) && traceBlockState != Blocks.CHEST.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.WEST) && traceBlockState != Blocks.CHEST.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.EAST))
			{
				if(event.getEntityPlayer().dimension == EMCoreDimensions.VOID.getId())
				{
					event.setCanceled(true);
				}
				
				List<EntityNatureBoss> NatureBoss = event.getEntityPlayer().world.<EntityNatureBoss>getEntitiesWithinAABB(EntityNatureBoss.class, event.getEntityPlayer().getEntityBoundingBox().grow(_ConfigEntityNatureBoss.ARENA_SIZE_X, _ConfigEntityNatureBoss.ARENA_SIZE_Y, _ConfigEntityNatureBoss.ARENA_SIZE_Z));
				if(NatureBoss.size() > 0)
				{
					event.setCanceled(true);
				}
				
				List<EntityFireBoss> FireBoss = event.getEntityPlayer().world.<EntityFireBoss>getEntitiesWithinAABB(EntityFireBoss.class, event.getEntityPlayer().getEntityBoundingBox().grow(_ConfigEntityFireBoss.ARENA_SIZE_X, _ConfigEntityFireBoss.ARENA_SIZE_Y, _ConfigEntityFireBoss.ARENA_SIZE_Z));
				if(FireBoss.size() > 0)
				{
					event.setCanceled(true);
				}
				
				if(traceBlockState != EMCoreBlocks.VALVE.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.SOUTH))
				{
					List<EntityWaterBoss> WaterBoss = event.getEntityPlayer().world.<EntityWaterBoss>getEntitiesWithinAABB(EntityWaterBoss.class, event.getEntityPlayer().getEntityBoundingBox().grow(_ConfigEntityWaterBoss.ARENA_SIZE_X, _ConfigEntityWaterBoss.ARENA_SIZE_Y, _ConfigEntityWaterBoss.ARENA_SIZE_Z));
					if(WaterBoss.size() > 0)
					{
						event.setCanceled(true);
					}
				}
				
				List<EntityVoidKnight> KnightBoss = event.getEntityPlayer().world.<EntityVoidKnight>getEntitiesWithinAABB(EntityVoidKnight.class, event.getEntityPlayer().getEntityBoundingBox().grow(_ConfigEntityVoidKnight.ARENA_SIZE_X, _ConfigEntityVoidKnight.ARENA_SIZE_Y, _ConfigEntityVoidKnight.ARENA_SIZE_Z));
				if(KnightBoss.size() > 0)
				{
					event.setCanceled(true);
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void drawVoidBossOverlay(RenderGameOverlayEvent.Chat event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		boolean isActive = false;
		if(mc.player.isPotionActive(EMCorePotionEffects.POTION_GLIMPSE)) isActive = true;
		
		if(isActive)
		{
			ScaledResolution scaledRes = new ScaledResolution(mc);
			int xPos = 0;
			int yPos = 0;
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(EMConfig.MOD_ID + ":textures/gui/ve_overlay.png"));
			Minecraft.getMinecraft().ingameGUI.drawModalRectWithCustomSizedTexture(xPos, yPos, 0, 0, scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), scaledRes.getScaledWidth(), scaledRes.getScaledHeight());
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
			
			if(mc.player.getActivePotionEffect(EMCorePotionEffects.POTION_GLIMPSE).getDuration() <= 0)
			{
				mc.player.removeActivePotionEffect(EMCorePotionEffects.POTION_GLIMPSE);
			}
		}
	}
	
	@SubscribeEvent
	public static void corruptionActive(LivingUpdateEvent event)
	{
		boolean isActive = false;
		EntityLivingBase living = event.getEntityLiving();
		if(living.isPotionActive(EMCorePotionEffects.POTION_CORRUPTION)) isActive = true;
		
		if(isActive)
		{
			if(!event.getEntity().world.isRemote)
			{
				if (living.ticksExisted % 40 == 1) living.attackEntityFrom(DamageSource.MAGIC, 1.0f);
			}
			if(event.getEntity().world.isRemote)
			{
				Random rand = new Random();
				for (int i = 0; i < 2; i++)
	            {
	            	ParticleSpawner.spawnParticle(EnumCustomParticleTypes.VOID_PORTAL_BLOCK, living.posX + (rand.nextDouble() - 0.5D) * (double)living.width, living.posY + rand.nextDouble() * (double)living.height - 0.25D, living.posZ + (rand.nextDouble() - 0.5D) * (double)living.width, (rand.nextDouble() - 0.5D) * 2.0D, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2.0D);
	            	ParticleSpawner.spawnParticle(EnumCustomParticleTypes.VOID_PORTAL, living.posX + (rand.nextDouble() - 0.5D) * (double)living.width, living.posY + rand.nextDouble() * (double)living.height - 0.25D, living.posZ + (rand.nextDouble() - 0.5D) * (double)living.width, (rand.nextDouble() - 0.5D) * 2.0D, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2.0D);
		        }
			}
			
			if(living.getActivePotionEffect(EMCorePotionEffects.POTION_CORRUPTION).getDuration() <= 0)
			{
				living.removeActivePotionEffect(EMCorePotionEffects.POTION_CORRUPTION);
			}
		}
	}
	
	@SubscribeEvent
	public static void insanityActive(PlayerTickEvent event)
	{
		boolean isActive = false;
		if(event.player.isPotionActive(EMCorePotionEffects.POTION_INSANITY)) isActive = true;
		
		if(isActive)
		{
			if(event.player.world.isRemote)
			{
				Random rand = new Random();
				for (int i = 0; i < 2; i++)
	            {
	            	ParticleSpawner.spawnParticle(EnumCustomParticleTypes.DARK_PURPLE_SMOKE, event.player.posX + (rand.nextDouble() - 0.5D) * (double)event.player.width, event.player.posY + rand.nextDouble() * (double)event.player.height - 0.25D, event.player.posZ + (rand.nextDouble() - 0.5D) * (double)event.player.width, 0.0D, 0.0D, 0.0D);
	            	ParticleSpawner.spawnParticle(EnumCustomParticleTypes.DARK_PURPLE_SMOKE_BLOCK, event.player.posX + (rand.nextDouble() - 0.5D) * (double)event.player.width, event.player.posY + rand.nextDouble() * (double)event.player.height - 0.25D, event.player.posZ + (rand.nextDouble() - 0.5D) * (double)event.player.width, (rand.nextDouble() - 0.5D) * 2.0D, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2.0D);
		        }
			}
			
			if(!event.player.world.isRemote)
			{
				if(event.player.getActivePotionEffect(EMCorePotionEffects.POTION_INSANITY).getDuration() == 1)
				{
					if(event.player.getActivePotionEffect(EMCorePotionEffects.POTION_INSANITY).getAmplifier() > 4)
					{
						event.player.attackEntityFrom(DamageSource.MAGIC, 999999999F);
					}
					else
					{
						event.player.attackEntityFrom(DamageSource.MAGIC, 10.0F * event.player.getActivePotionEffect(EMCorePotionEffects.POTION_INSANITY).getAmplifier());
					}
				}
			}
			
			if(event.player.getActivePotionEffect(EMCorePotionEffects.POTION_INSANITY).getDuration() <= 0)
			{
				event.player.removeActivePotionEffect(EMCorePotionEffects.POTION_INSANITY);
			}
		}
	}
	
	@SubscribeEvent
	public static void fireArmorEffect(LivingHurtEvent event)
	{
		if(!event.getEntityLiving().world.isRemote)
		{
			ItemStack stack = event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.CHEST);
            Item item = stack != null ? stack.getItem() : null;
            if(item instanceof FireArmor)
            {
            	if(event.getSource() == DamageSource.ON_FIRE || event.getSource() == DamageSource.IN_FIRE || event.getSource() == DamageSource.LAVA || event.getSource() == DamageSource.HOT_FLOOR)
            	{
            		event.setCanceled(true);
            	}
            	
            	if(event.getSource().getTrueSource() instanceof EntityFireBoss || event.getSource().getTrueSource() instanceof EntityFireBossMinion || event.getSource().getTrueSource() instanceof EntityFireGuardian)
            	{
            		event.setAmount((event.getAmount() / 4) * 3);
            	}
            }
		}
	}
	
	@SubscribeEvent
	public static void leafArmorEffect(LivingHurtEvent event)
	{
		if(!event.getEntityLiving().world.isRemote)
		{
			ItemStack stack = event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.CHEST);
            Item item = stack != null ? stack.getItem() : null;
            if(item instanceof LeafArmor)
            {
            	if(event.getSource() == DamageSource.CACTUS)
            	{
            		event.setCanceled(true);
            	}
            	
            	if(event.getSource().getTrueSource() instanceof EntityNatureBoss || event.getSource().getTrueSource() instanceof EntityNatureBossMinion)
            	{
            		event.setAmount((event.getAmount() / 4) * 3);
            	}
            }
		}
	}
	
	@SubscribeEvent
	public static void leafHealingArmorEffect(LivingHealEvent event)
	{
		if(!event.getEntityLiving().world.isRemote)
		{
			ItemStack stack = event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.CHEST);
            Item item = stack != null ? stack.getItem() : null;
            if(item instanceof LeafArmor)
            {
            	event.setAmount(event.getAmount() + (event.getAmount() / 4));
            }
		}
	}
	
	@SubscribeEvent
	public static void waterArmorEffect(LivingHurtEvent event)
	{
		if(!event.getEntityLiving().world.isRemote)
		{
			ItemStack stack = event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.CHEST);
            Item item = stack != null ? stack.getItem() : null;
            if(item instanceof WaterArmor)
            {
            	if(event.getSource() == DamageSource.DROWN)
            	{
            		event.setCanceled(true);
            	}
            	
            	if(event.getSource().getTrueSource() instanceof EntityWaterBoss || event.getSource().getTrueSource() instanceof EntityWaterBossMeleeMinion || event.getSource().getTrueSource() instanceof EntityWaterBossRangedMinion || event.getSource().getTrueSource() instanceof EntityWaterTrash)
            	{
            		event.setAmount((event.getAmount() / 4) * 3);
            	}
            }
		}
	}
	
	@SubscribeEvent
	public static void voidArmorEffect(LivingHurtEvent event)
	{
		if(!event.getEntityLiving().world.isRemote)
		{
			ItemStack stack = event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.HEAD);
            Item item = stack != null ? stack.getItem() : null;
            if(item instanceof VoidHelmet)
            {
            	if(event.getSource() == DamageSource.MAGIC)
            	{
            		event.setAmount((event.getAmount() / 4) * 3);
            	}
            	
            	if(event.getSource().getTrueSource() instanceof EntityVoidEntity || event.getSource().getTrueSource() instanceof EntityLunacy || event.getSource().getTrueSource() instanceof EntitySlaveMaster || event.getSource().getTrueSource() instanceof EntityVoidSpectralSmall || event.getSource().getTrueSource() instanceof EntityVoidSpectralMedium || event.getSource().getTrueSource() instanceof EntityVoidSpectralLarge || event.getSource().getTrueSource() instanceof EntityVoidBlob || event.getSource().getTrueSource() instanceof EntityVoidSmasher || event.getSource().getTrueSource() instanceof EntityVoidKnight)
            	{
            		event.setAmount((event.getAmount() / 4) * 3);
            	}
            }
		}
	}
}