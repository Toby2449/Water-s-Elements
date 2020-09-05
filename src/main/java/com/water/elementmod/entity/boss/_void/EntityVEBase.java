package com.water.elementmod.entity.boss._void;

import java.util.List;

import com.water.elementmod.network.PacketHandler;
import com.water.elementmod.network.PacketVEBaseParticles;
import com.water.elementmod.particle.EnumCustomParticleTypes;
import com.water.elementmod.particle.ParticleSpawner;
import com.water.elementmod.util.handlers.EMSoundHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityVEBase extends EntityLiving
{
	private static final DataParameter<Boolean> SPAWNED = EntityDataManager.<Boolean>createKey(EntityVEBase.class, DataSerializers.BOOLEAN);
	private int ve_spawn_anim = 0;
	private boolean playedSound = false;
	
    public EntityVEBase(World worldIn)
    {
        super(worldIn);
        this.setSize(7.25F, 5.0F);
    }
    
    public EntityVEBase(World worldIn, double x, double y, double z)
    {
        this(worldIn);
        this.setPosition(x, y, z);
        this.setSize(7.25F, 5.0F);
    }
    
    @Override
	protected void initEntityAI() {}
    
    @Override
    protected void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(999.0F);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(999999999D);
    }
    
    @Override
    protected void entityInit()
    {
    	super.entityInit();
    	this.dataManager.register(SPAWNED, Boolean.valueOf(false));
    }
    
    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
    	super.writeEntityToNBT(compound);
    	compound.setBoolean("Spawned", this.getSpawnedStatus());
    }
	
    @Override
	public void readEntityFromNBT(NBTTagCompound compound)
    {
    	super.readEntityFromNBT(compound);
    	this.setSpawnedStatus(compound.getBoolean("Spawned"));
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	if(!this.world.isRemote)
    	{
	    	List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox());
	        for (EntityPlayer entity : list)
	        {
	        	if(this.ticksExisted % 10 == 1) entity.attackEntityFrom(DamageSource.MAGIC, 8.0f);
	        }
	        
	        List<EntityCarapace> carapace = this.world.<EntityCarapace>getEntitiesWithinAABB(EntityCarapace.class, this.getEntityBoundingBox().grow(500, 100, 500));
	        if(carapace.isEmpty())
	        {
	        	List<EntityVoidEntity> ve = this.world.<EntityVoidEntity>getEntitiesWithinAABB(EntityVoidEntity.class, this.getEntityBoundingBox().grow(500, 100, 500));
	        	if(ve.isEmpty())
	        	{
	        		if(!this.getSpawnedStatus())
	        		{
		        		this.ve_spawn_anim++;
		        		PacketHandler.INSTANCE.sendToDimension(new PacketVEBaseParticles(this, this.world, 11, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, this.width), this.dimension);
		        		
						if(!this.playedSound)
						{
							this.playedSound = true;
							this.world.playSound((EntityPlayer)null, new BlockPos(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ()), EMSoundHandler.VE_BASE_SPAWN_CAST, SoundCategory.HOSTILE, 3.5F, 1.0f);
						}
						
		        		if(this.ve_spawn_anim >= 140)
		        		{
		        			this.setSpawnedStatus(true);
		        			this.ve_spawn_anim = 0;
		        			this.world.playSound((EntityPlayer)null, new BlockPos(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ()), EMSoundHandler.VE_BASE_SPAWNED, SoundCategory.HOSTILE, 3.5F, 1.0f);
		        			EntityVoidEntity VE = new EntityVoidEntity(world);
				            VE.enablePersistence();
				            VE.setPosition(this.getPosition().getX() + 0.5D, this.getPosition().getY(), this.getPosition().getZ() + 0.5D);
				            world.spawnEntity(VE);
		        		}
	        		}
	        	}
	        	else
	        	{
	        		this.playedSound = false;
	        	}
	        }
    	}
    	
        if(this.world.isRemote) 
        {
        	for(int i = 0; i < 20; i++)
        	{
        		ParticleSpawner.spawnParticle(EnumCustomParticleTypes.DARK_PURPLE_SMOKE, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.175D, 0.0D);
        	}
        	
        	for(int i = 0; i < 20; i++)
        	{
        		ParticleSpawner.spawnParticle(EnumCustomParticleTypes.DARK_PURPLE_SMOKE_VE_SPAWN, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.15D, 0.0D);
        	}
        }
        
        List<EntityVoidEntity> ve = this.world.<EntityVoidEntity>getEntitiesWithinAABB(EntityVoidEntity.class, this.getEntityBoundingBox().grow(500, 100, 500));
    	if(!ve.isEmpty())
    	{
    		this.setSize(7.25F, 0.75F);
    	}
    	else
    	{
    		this.setSize(7.25F, 5.0F);
    	}
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	return false;
    }
	
	@Override
	protected SoundEvent getDeathSound()
    {
        return SoundEvents.EVOCATION_ILLAGER_CAST_SPELL;
    }
    
    public boolean getSpawnedStatus()
    {
        return ((Boolean)this.dataManager.get(SPAWNED)).booleanValue();
    }
    
    public void setSpawnedStatus(boolean state)
    {
        this.dataManager.set(SPAWNED, Boolean.valueOf(state));
    }
    
    @Override
    public void onCollideWithPlayer(EntityPlayer entityIn) {}

    @Override
    public boolean canBeCollidedWith() {
    	return true;
    }
    
    @Override
    public boolean canBePushed() {
    	return false; 
    }
    
    @Override
    protected void collideWithEntity(Entity p_82167_1_) {}

    @Override
    protected void collideWithNearbyEntities() {}
}
