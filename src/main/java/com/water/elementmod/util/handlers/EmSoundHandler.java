package com.water.elementmod.util.handlers;

import com.water.elementmod.util.EMConfig;
import com.water.elementmod.util.Utils;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


public class EMSoundHandler {
	
	public static SoundEvent FIRE_BURN, WATER_FLOW, NATURE_LEAVES, FIRE_DOOR_CLOSE, SHADOW_IMPACT_01, SHADOW_IMPACT_02, SHADOW_IMPACT_03, CARAPACE_ATTACK_01, CARAPACE_ATTACK_02, CARAPACE_CRITATTACK_01, CARAPACE_CRITATTACK_02, CARAPACE_CRITATTACK_03, CARAPACE_CRITATTACK_04, CARAPACE_WOUND_01, CARAPACE_WOUND_02, CARAPACE_WOUND_03, SPELL_IMPACT_01, SPELL_IMPACT_02, SPELL_IMPACT_03, VOIDMAGIC_CAST_01, VOIDMAGIC_CAST_02, VOIDMAGIC_CAST_03, VOIDMAGIC_EXPLOSION_01, VOIDMAGIC_EXPLOSION_02, VOIDMAGIC_EXPLOSION_03, VE_BASE_SPAWN_CAST, VE_BASE_SPAWNED, CARAPACE_MUSIC, VOID_ENTITY_MUSIC, VO_VOIDENTITY_1, VO_VOIDENTITY_2, VO_VOIDENTITY_3, VO_VOIDENTITY_5, VO_VOIDENTITY_6, VO_VOIDENTITY_8, VO_VOIDENTITY_10, VO_VOIDENTITY_11, VO_VOIDENTITY_13, VO_VOIDENTITY_14, VO_VOIDENTITY_15;
	
	public static void init()
	{
		FIRE_BURN = registerSound("block.infuser.fire");
		WATER_FLOW = registerSound("block.infuser.water");
		NATURE_LEAVES = registerSound("block.infuser.nature");
		FIRE_DOOR_CLOSE = registerSound("ambient.fire_door_close");
		SHADOW_IMPACT_01 = registerSound("spells.shadow_impact_01");
		SHADOW_IMPACT_02 = registerSound("spells.shadow_impact_02");
		SHADOW_IMPACT_03 = registerSound("spells.shadow_impact_03");
		CARAPACE_ATTACK_01 = registerSound("entity.carapace_attack_01");
		CARAPACE_ATTACK_02 = registerSound("entity.carapace_attack_02");
		CARAPACE_CRITATTACK_01 = registerSound("entity.carapace_critattack_01");
		CARAPACE_CRITATTACK_02 = registerSound("entity.carapace_critattack_02");
		CARAPACE_CRITATTACK_03 = registerSound("entity.carapace_critattack_03");
		CARAPACE_CRITATTACK_04 = registerSound("entity.carapace_critattack_04");
		CARAPACE_WOUND_01 = registerSound("entity.carapace_wound_01");
		CARAPACE_WOUND_02 = registerSound("entity.carapace_wound_02");
		CARAPACE_WOUND_03 = registerSound("entity.carapace_wound_03");
		SPELL_IMPACT_01 = registerSound("spells.impact_01");
		SPELL_IMPACT_02 = registerSound("spells.impact_02");
		SPELL_IMPACT_03 = registerSound("spells.impact_03");
		VOIDMAGIC_CAST_01 = registerSound("spells.voidmagic_cast_01");
		VOIDMAGIC_CAST_02 = registerSound("spells.voidmagic_cast_02");
		VOIDMAGIC_CAST_03 = registerSound("spells.voidmagic_cast_03");
		VOIDMAGIC_EXPLOSION_01 = registerSound("spells.voidmagic_explosion_01");
		VOIDMAGIC_EXPLOSION_02 = registerSound("spells.voidmagic_explosion_02");
		VOIDMAGIC_EXPLOSION_03 = registerSound("spells.voidmagic_explosion_03");
		VE_BASE_SPAWN_CAST = registerSound("entity.ve_base_spawn_cast");
		VE_BASE_SPAWNED = registerSound("entity.ve_base_spawned");
		CARAPACE_MUSIC = registerSound("music.carapace_fight_music");
		VOID_ENTITY_MUSIC = registerSound("music.void_entity_fight_music");
		VO_VOIDENTITY_1 = registerSound("entity.vo_voidentity_1");
		VO_VOIDENTITY_2 = registerSound("entity.vo_voidentity_2");
		VO_VOIDENTITY_3 = registerSound("entity.vo_voidentity_3");
		VO_VOIDENTITY_5 = registerSound("entity.vo_voidentity_5");
		VO_VOIDENTITY_6 = registerSound("entity.vo_voidentity_6");
		VO_VOIDENTITY_8 = registerSound("entity.vo_voidentity_8");
		VO_VOIDENTITY_10 = registerSound("entity.vo_voidentity_10");
		VO_VOIDENTITY_11 = registerSound("entity.vo_voidentity_11");
		VO_VOIDENTITY_13 = registerSound("entity.vo_voidentity_13");
		VO_VOIDENTITY_14 = registerSound("entity.vo_voidentity_14");
		VO_VOIDENTITY_15 = registerSound("entity.vo_voidentity_15");
	}
	
	public static SoundEvent registerSound(String name)
	{
		 ResourceLocation location = new ResourceLocation(EMConfig.MOD_ID, name);
		 SoundEvent event = new SoundEvent(location);
		 event.setRegistryName(name);
		 ForgeRegistries.SOUND_EVENTS.register(event);
		 Utils.getLogger().info("Registered sound: " + name);
		 return event;
	}
}
