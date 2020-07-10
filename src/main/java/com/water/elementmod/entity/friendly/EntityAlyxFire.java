package com.water.elementmod.entity.friendly;

import java.util.List;

import com.water.elementmod.entity.ai.EntityAIMoveTo;
import com.water.elementmod.entity.boss.fire.EntityFireBossMinion;
import com.water.elementmod.entity.projectile.EntityFireArrow;
import com.water.elementmod.particle.EnumCustomParticleTypes;
import com.water.elementmod.particle.ParticleSpawner;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class EntityAlyxFire extends EntityCreature
{
	private BlockPos destination = null;
	private boolean spoken = false;
	private int spokenCD = 0;
	
	public EntityAlyxFire(World worldIn) 
	{
		super(worldIn);
		this.setSize(0.7F, 1.875F);
		this.setCustomNameTag("Aylx");
	}
	
	public EntityAlyxFire(World worldIn, double gotoX, double gotoY, double gotoZ) 
	{
		super(worldIn);
		this.setSize(0.7F, 1.875F);
		this.setCustomNameTag("Aylx");
		this.destination = new BlockPos(gotoX, gotoY, gotoZ);
	}
	
	public static void registerFixesAlyx(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntityAlyxFire.class);
    }
	
	@Override
	protected void initEntityAI()
    {
		this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(3, new EntityAILookIdle(this));
    }
	
	@Override
	protected void entityInit()
    {
        super.entityInit();
    }
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0F);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(999999999999999D);
	}
	
	@Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	return false;
    }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setBoolean("Spoken", this.hasSpoken());
    }
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setSpoken(compound.getBoolean("Spoken"));
    }
	
    @Override
	public void onLivingUpdate()
    {   
        super.onLivingUpdate();
        
        if(this.world.isRemote)
        {
        	if(!this.hasSpoken())
        	{
        		this.spokenCD++;
        		if(this.spokenCD >= 80)
        		{
        			this.setSpoken(true);
            		List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(100, 70, 100));
    		        
    		        for (EntityPlayer player : list)
    		        {
    		        	player.sendMessage(new TextComponentTranslation("message.em.alyx.naturebossdeath"));
    		        }
        		}
        	}
        }
    }
	
	@Override
	protected void updateAITasks()
    {
		super.updateAITasks();
		if(this.destination != null)
		{
			this.tasks.addTask(1, new EntityAIMoveTo(this, 0.5D, 30, destination.getX(), destination.getY(), destination.getZ()));
		}
    }
	
	@Override
	public float getEyeHeight()
	{
		return 1.625F;
	}
	
	public void setSpoken(boolean spoken)
	{
		this.spoken = spoken;
	}
	
	public boolean hasSpoken()
	{
		return this.spoken;
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
