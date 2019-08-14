package com.water.elementmod.util;

import java.util.ArrayList;
import java.util.List;

import com.water.elementmod.entity.EntityFireSkeleton;
import com.water.elementmod.entity.EntityFireZombie;

public class EMConfig {
	
	public static final String MOD_ID = "em";
	public static final String NAME = "Element Mod";
	public static final String VERSION = "0.1u5";
	public static final String ACCEPTED_VERSIONS = "[1.12.2]";
	public static final String CLIENT_PROXY_CLASS = "com.water.elementmod.proxy.ClientProxy";
	public static final String COMMON_PROXY_CLASS = "com.water.elementmod.proxy.CommonProxy";
	
	//Blocks
	public static final int SYNTHESIZER = 0;
	public static final int EXTRACTOR = 1;
	public static final int INFUSER = 2;
	
	//Entites
	public static final int ENTITY_FIREZOMBIE = 120;
	public static final int ENTITY_WATERZOMBIE = 121;
	public static final int ENTITY_NATUREZOMBIE = 122;
	
	public static final int ENTITY_FIRESKELETON = 123;
	public static final int ENTITY_WATERSKELETON = 124;
	public static final int ENTITY_NATURESKELETON = 125;

}
