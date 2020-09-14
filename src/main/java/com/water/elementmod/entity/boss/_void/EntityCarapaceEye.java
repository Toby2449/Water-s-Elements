package com.water.elementmod.entity.boss._void;

import java.util.List;

import com.water.elementmod.entity.EntityBossMob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.World;

public class EntityCarapaceEye extends EntityBossMob
{
	public static final float BASE_HP = 60.0F;
	public static final float HP_SCALE_AMOUNT = 60.0F;
	public EntityCarapaceEye(World worldIn) 
	{
		super(worldIn);
		this.isImmuneToFire = true;
		this.setSize(1.5F, 0.6F);
	}
	
	public static void registerFixesBoss(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntityCarapaceEye.class);
    }
	
	@Override
	protected void initEntityAI() {}
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(BASE_HP);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(9999999999999.0F);
	}
	
	@Override
	protected void entityInit()
    {
        super.entityInit();
    }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
    }
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
    }
	
    @Override
	public void onLivingUpdate()
    {   
    	super.onLivingUpdate();
		this.ticksExisted++;
		
		if(!this.world.isRemote)
		{
			// HP scaling
			int carapacePhase = 0;
			List<EntityCarapace> carapace = this.world.<EntityCarapace>getEntitiesWithinAABB(EntityCarapace.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
	        if(!carapace.isEmpty())
	        {
	        	for (EntityCarapace entity : carapace)
		        {
	        		carapacePhase = entity.getPhase();
		        }
	        	
	        	if(carapacePhase == 0)
		        {
					int numOfPlayers = 0;
		        	List<EntityPlayer> players = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE));
			        if(!players.isEmpty())
			        {
			        	for (EntityPlayer entity : players)
				        {
			        		numOfPlayers++;
				        }
			        }
			        
			        if(numOfPlayers > 1)
			        {
			        	float scaledHP = BASE_HP + (HP_SCALE_AMOUNT * numOfPlayers);
			        	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(scaledHP);
			        	this.setHealth(scaledHP);
			        }
		        }
	        }
		}
    }
	
	@Override
	protected void updateAITasks()
    {
		super.updateAITasks();
    }
	
	@Override
	public float getEyeHeight()
	{
		return 0.5F;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundEvents.ENTITY_PLAYER_ATTACK_CRIT;
	}
	
	@Override
	protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_SLIME_DEATH;
    }
    
    /**
     * Called when the entity is attacked.
     */
	@Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	return super.attackEntityFrom(source, amount);
    }
	
	@Override
	public boolean attackEntityAsMob(Entity entityIn)
    {
        return false;
    }
    
    @Override
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);
    }
    
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    @Override
    protected int getExperiencePoints(EntityPlayer player)
    {
    	return 0;
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
