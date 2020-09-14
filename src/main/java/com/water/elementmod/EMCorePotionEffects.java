package com.water.elementmod;

import com.water.elementmod.potion.CustomPotionEffect;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.MathHelper;

public class EMCorePotionEffects 
{
	public static final Potion POTION_CORRUPTION = new CustomPotionEffect("corruption", true, 7348689, 0, 0);
	public static final Potion POTION_GLIMPSE = new CustomPotionEffect("glimpse", true, 7348689, 0, 1);
	public static final Potion POTION_INSANITY = new CustomPotionEffect("insanity", true, 7348689, 0, 2);
}
