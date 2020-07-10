package com.water.elementmod.potion;

import com.water.elementmod.util.EMConfig;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class CustomPotionEffect extends Potion
{
	public CustomPotionEffect(String name, boolean isBadPotion, int color, int iconIndexX, int iconIndexY)
	{
		super(isBadPotion, color);
		setPotionName("effect." + name);
		setIconIndex(iconIndexX, iconIndexY);
		setRegistryName(new ResourceLocation(EMConfig.MOD_ID + ":" + name));
	}
	
	@Override
	public boolean hasStatusIcon()
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(EMConfig.MOD_ID + ":textures/gui/potion_effects.png"));
		return true;
	}
}
