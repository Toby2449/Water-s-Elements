package com.water.elementmod.entity.boss.fire;

import java.util.ArrayList;
import java.util.List;

import com.water.elementmod.entity.ai.EntityAIMoveTo;
import com.water.elementmod.entity.ai.EntityAIWanderNoCD;
import com.water.elementmod.particle.EnumCustomParticleTypes;
import com.water.elementmod.particle.ParticleSpawner;

import net.minecraft.block.BlockSlab.EnumBlockHalf;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStairs.EnumHalf;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlab.EnumType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public class EntityFireGuardian extends EntityMob
{
	private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.RED, BossInfo.Overlay.PROGRESS)).setDarkenSky(false);
	private BlockPos destination = null;
	private final double ARENA_SIZE_X = 50.0D;
	private final double ARENA_SIZE_Y = 11.0D;
	private final double ARENA_SIZE_Z = 40.0D;
	private int explodeTime = 1350; // 32 seconds
	
	public EntityFireGuardian(World worldIn) 
	{
		super(worldIn);
		this.isImmuneToFire = true;
		this.setSize(0.7F, 2.25F);
	}
	
	public EntityFireGuardian(World worldIn, double gotoX, double gotoY, double gotoZ) 
	{
		super(worldIn);
		this.isImmuneToFire = true;
		this.setSize(0.7F, 2.25F);
		this.destination = new BlockPos(gotoX, gotoY, gotoZ);
	}
	
	public static void registerFixesBoss(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntityFireGuardian.class);
    }
	
	@Override
	protected void initEntityAI()
    {
		this.tasks.addTask(0, new EntityAIAttackMelee(this, 0.55D, true));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityFireBoss.class, 20.0F));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    }
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.0F);//150
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.325D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(99999999999F);
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
        compound.setIntArray("Destination", new int[] {this.destination.getX(), this.destination.getY(), this.destination.getZ()});
    }
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        int[] pos = compound.getIntArray("Destination");
        BlockPos blockpos = new BlockPos(pos[0], pos[1], pos[2]);
        this.destination = blockpos;
        if (this.hasCustomName())
        {
            this.bossInfo.setName(this.getDisplayName());
        }
    }
	
	/**
     * Sets the custom name tag for this entity
     */
	@Override
    public void setCustomNameTag(String name)
    {
        super.setCustomNameTag(name);
        this.bossInfo.setName(this.getDisplayName());
    }
	
    @Override
	public void onLivingUpdate()
    {   
    	super.onLivingUpdate();
    	
    	if (this.world.isRemote)
        {
        	if(this.rand.nextInt(100) > 70)
        	{
        		this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + 0.25D + this.rand.nextDouble() * ((double)this.height), this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
            	
        		this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * 1.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
            	ParticleSpawner.spawnParticle(EnumCustomParticleTypes.LAVA, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + 1.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
            }
        }
    	
    	if(!this.world.isRemote)
    	{
    		boolean bossInRadius = false;
        	List<EntityFireBoss> list = this.world.<EntityFireBoss>getEntitiesWithinAABB(EntityFireBoss.class, this.getEntityBoundingBox().grow(1.5D, 11, 1.5D));
	        
        	for (EntityFireBoss guardians : list)
	        {
        		bossInRadius = true;
	        }
        	
	    	if(this.ticksExisted >= this.explodeTime || bossInRadius)
	    	{
	    		List<EntityPlayer> list1 = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(ARENA_SIZE_X, ARENA_SIZE_Y, ARENA_SIZE_Z));
		        
	    		for (EntityPlayer player : list1)
		        {
	    			player.attackEntityFrom(DamageSource.causeMobDamage(this), 999999999999999F); // KILL EVERYONE >:D
		        }
	    	}
    	}
		
		this.ticksExisted++;
		this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }
	
	@Override
	protected void updateAITasks()
    {
		super.updateAITasks();
		
		if(this.destination != null)
		{
			this.tasks.addTask(0, new EntityAIMoveTo(this, 0.325D, 50, destination.getX(), destination.getY(), destination.getZ()));
    	}
    }
	
	@Override
	public float getEyeHeight()
	{
		return 1.975F;
	}
	
	/**
     * Add the given player to the list of players tracking this entity. For instance, a player may track a boss in
     * order to view its associated boss bar.
     */
	@Override
    public void addTrackingPlayer(EntityPlayerMP player)
    {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    /**
     * Removes the given player from the list of players tracking this entity. See {@link Entity#addTrackingPlayer} for
     * more information on tracking.
     */
	@Override
    public void removeTrackingPlayer(EntityPlayerMP player)
    {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }
	
	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_BLAZE_AMBIENT;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundEvents.ENTITY_BLAZE_HURT;
	}
	
	@Override
	protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_BLAZE_DEATH;
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
