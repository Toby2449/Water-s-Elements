package com.water.elementmod.entity.projectile;

import java.util.List;

import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityPoisonBall extends EntityThrowable
{
    public EntityPoisonBall(World worldIn)
    {
        super(worldIn);
        this.setSize(1.0F, 1.0F);
    }

    public EntityPoisonBall(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }
    
    public EntityPoisonBall(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    public static void registerFixesPoisonBall(DataFixer fixer)
    {
    	EntityThrowable.registerFixesThrowable(fixer, "PoisonBall");
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
    	super.onUpdate();
    	if(this.world.isRemote)
    	{
    		this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX, this.posY, this.posZ, 0.0D, 0.5D, 0.0D);
    	}
    }
    
    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onImpact(RayTraceResult result)
    {
        if (result.entityHit == null || !result.entityHit.isEntityEqual(this.thrower))
        {
            if (!this.world.isRemote)
            {
                List<EntityLivingBase> list = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(4.0D, 4.0D, 4.0D));
                EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
                entityareaeffectcloud.setOwner(this.thrower);
                entityareaeffectcloud.setColor(24850);
                entityareaeffectcloud.setParticle(EnumParticleTypes.SPELL_MOB);
                entityareaeffectcloud.setRadius(1.0F);
                entityareaeffectcloud.setDuration(400);
                entityareaeffectcloud.setRadiusPerTick((7.0F - entityareaeffectcloud.getRadius()) / (float)entityareaeffectcloud.getDuration());
                entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.POISON, 100, 1));

                if (!list.isEmpty())
                {
                    for (EntityLivingBase entitylivingbase : list)
                    {
                        double d0 = this.getDistanceSq(entitylivingbase);

                        if (d0 < 16.0D)
                        {
                            entityareaeffectcloud.setPosition(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ);
                            break;
                        }
                    }
                }

                this.world.playEvent(2006, new BlockPos(this.posX, this.posY, this.posZ), 0);
                this.world.spawnEntity(entityareaeffectcloud);
                this.setDead();
            }
        }
    }
}
