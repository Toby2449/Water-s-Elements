package com.water.elementmod.particle;

import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

public enum EnumCustomParticleTypes 
{
	GREEN_POLLEN("green_pollen", 0, false),
	DARKGREEN_POLLEN("darkgreen_pollen", 1, false),
	SMALL_HEART("small_heart", 2, false),
	LEAF("leaf", 3, false),
	LEAF_AURA("leaf_aura", 4, false),
	LAVA("lava_fall", 5, false);
	
	private final String particleName;
    private final int particleID;
    private final boolean shouldIgnoreRange;
    private final int argumentCount;
    private static final Map<Integer, EnumCustomParticleTypes> PARTICLES = Maps.<Integer, EnumCustomParticleTypes>newHashMap();
    private static final Map<String, EnumCustomParticleTypes> BY_NAME = Maps.<String, EnumCustomParticleTypes>newHashMap();
    
    private EnumCustomParticleTypes(String particleNameIn, int particleIDIn, boolean shouldIgnoreRangeIn, int argumentCountIn)
    {
        this.particleName = particleNameIn;
        this.particleID = particleIDIn;
        this.shouldIgnoreRange = shouldIgnoreRangeIn;
        this.argumentCount = argumentCountIn;
    }
    
    private EnumCustomParticleTypes(String particleNameIn, int particleIDIn, boolean shouldIgnoreRangeIn)
    {
        this(particleNameIn, particleIDIn, shouldIgnoreRangeIn, 0);
    }

    public static Set<String> getParticleNames()
    {
        return BY_NAME.keySet();
    }

    public String getParticleName()
    {
        return this.particleName;
    }

    public int getParticleID()
    {
        return this.particleID;
    }

    public int getArgumentCount()
    {
        return this.argumentCount;
    }

    public boolean getShouldIgnoreRange()
    {
        return this.shouldIgnoreRange;
    }

    /**
     * Gets the relative EnumParticleTypes by id.
     */
    @Nullable
    public static EnumCustomParticleTypes getParticleFromId(int particleId)
    {
        return PARTICLES.get(Integer.valueOf(particleId));
    }

    @Nullable
    public static EnumCustomParticleTypes getByName(String nameIn)
    {
        return BY_NAME.get(nameIn);
    }

    static
    {
        for (EnumCustomParticleTypes enumparticletypes : values())
        {
            PARTICLES.put(Integer.valueOf(enumparticletypes.getParticleID()), enumparticletypes);
            BY_NAME.put(enumparticletypes.getParticleName(), enumparticletypes);
        }
    }
}
