package com.water.elementmod.entity.projectile;

import java.util.List;

import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityIceBall extends EntityThrowable
{
    public EntityIceBall(World worldIn)
    {
        super(worldIn);
        this.setSize(1.0F, 1.0F);
    }

    public EntityIceBall(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }
    
    public EntityIceBall(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    public static void registerFixesIceBall(DataFixer fixer)
    {
    	EntityThrowable.registerFixesThrowable(fixer, "IceBall");
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
    	super.onUpdate();
    	if(this.world.isRemote)
    	{
    		this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.5D);
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
                List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(3.0D, 5.0D, 3.0D));
                EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
                entityareaeffectcloud.setOwner(this.thrower);
                entityareaeffectcloud.setColor(5230079);
                entityareaeffectcloud.setParticle(EnumParticleTypes.SPELL_MOB);
                entityareaeffectcloud.setRadius(2.0F);
                entityareaeffectcloud.setDuration(40);
                entityareaeffectcloud.setRadiusPerTick((2.0F - entityareaeffectcloud.getRadius()) / (float)entityareaeffectcloud.getDuration());
                entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.WEAKNESS, 50, 1));
                entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 50, 1));
                
                if (!list.isEmpty())
                {
                    for (EntityPlayer entitylivingbase : list)
                    {
                        double d0 = this.getDistanceSq(entitylivingbase);

                        if (d0 < 16.0D)
                        {
                            entityareaeffectcloud.setPosition(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ);
                            break;
                        }
                    }
                }

                this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 1.5F, (1.5F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
                this.world.spawnEntity(entityareaeffectcloud);
                this.setDead();
            }
        }
    }
}
