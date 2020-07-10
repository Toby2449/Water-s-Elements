package com.water.elementmod.entity.boss.overworld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.water.elementmod.EMCorePotionEffects;
import com.water.elementmod.entity.ai.EntityAIMoveTo;
import com.water.elementmod.entity.ai.EntityAIWanderNoCD;
import com.water.elementmod.entity.boss.fire.EntityFireBoss;
import com.water.elementmod.network.PacketCustomParticleData;
import com.water.elementmod.network.PacketHandler;
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
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
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
import net.minecraftforge.client.event.GuiScreenEvent.PotionShiftEvent;

public class EntityVoidBlob extends EntityMob
{
	private BlockPos destination = null;
	private double speed = 0.0D;
	private int explodeTime = 560; // 28 seconds
	
	public EntityVoidBlob(World worldIn) 
	{
		super(worldIn);
		this.setSize(1.25F, 0.75F);
	}
	
	public EntityVoidBlob(World worldIn, double gotoX, double gotoY, double gotoZ, double speed) 
	{
		super(worldIn);
		this.setSize(1.25F, 0.75F);
		this.destination = new BlockPos(gotoX, gotoY, gotoZ);
		this.speed = speed;
	}
	
	public static void registerFixesBoss(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntityVoidBlob.class);
    }
	
	@Override
	protected void initEntityAI()
    {
		this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityVoidKnight.class, 20.0F));
        this.tasks.addTask(3, new EntityAILookIdle(this));
    }
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0F);
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
        compound.setDouble("Speed", this.speed);
    }
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        int[] pos = compound.getIntArray("Destination");
        BlockPos blockpos = new BlockPos(pos[0], pos[1], pos[2]);
        this.destination = blockpos;
        this.speed = compound.getDouble("Speed");
    }
	
	/**
     * Sets the custom name tag for this entity
     */
	@Override
    public void setCustomNameTag(String name)
    {
        super.setCustomNameTag(name);
    }
	
    @Override
	public void onLivingUpdate()
	{
    	super.onLivingUpdate();
    	
    	if (this.world.isRemote)
        {
            for (int i = 0; i < 2; ++i)
            {
            	ParticleSpawner.spawnParticle(EnumCustomParticleTypes.VOID_PORTAL_BLOCK, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
            }
        }
    	
    	if(!this.world.isRemote)
    	{
    		boolean bossInRadius = false;
        	List<EntityVoidKnight> list = this.world.<EntityVoidKnight>getEntitiesWithinAABB(EntityVoidKnight.class, this.getEntityBoundingBox());
	        
        	for (EntityVoidKnight entity : list)
	        {
        		bossInRadius = true;
	        }
        	
	    	if(this.ticksExisted >= this.explodeTime || bossInRadius)
	    	{
	    		List<EntityVoidKnight> list1 = this.world.<EntityVoidKnight>getEntitiesWithinAABB(EntityVoidKnight.class, this.getEntityBoundingBox());
		        
	    		for (EntityVoidKnight entity : list1)
		        {
	    			if(entity.getPhase() == 4)
	    			{
	    				entity.heal(15.0F);
	    			}
	    			else
	    			{
	    				entity.attackEntityFrom(DamageSource.causeMobDamage(this), 25.0F);
	    			}
	    			this.isDead = true;
		        }
	    	}
    	}
		
		this.ticksExisted++;
    }
	
	@Override
	protected void updateAITasks()
    {
		super.updateAITasks();
		
		if(this.destination != null)
		{
			this.tasks.addTask(0, new EntityAIMoveTo(this, (Double)this.speed, 50, destination.getX(), destination.getY(), destination.getZ()));
    	}
    }
	
	@Override
	public float getEyeHeight()
	{
		return 0.5F;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundEvents.ENTITY_SLIME_HURT;
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
        
    	if(this.world.isRemote) VoidRingAnimation(this.posX, this.posY, this.posZ, 3, this.world);
    	
        List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(1.75D, 6.0D, 1.75D));
        
		for (EntityPlayer entity : list)
        {
			entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 80, 1));
        }
    }
    
    public void VoidRingAnimation(double x, double y, double z, double radius, World world)
	{
		Random rand = new Random();
		for(double r = 0.6D; r <= radius; r += 0.2D)
		{
			for(float i = 0.0F; i < 360.0F; i += 15.0F)
			{
				double deltaX = Math.cos(Math.toRadians(i))*r + rand.nextDouble();
				double deltaZ = -Math.sin(Math.toRadians(i))*r + rand.nextDouble();
				double finalX = x - 0.5D + deltaX;
				double finalZ = z - 0.5D + deltaZ;
			    
				ParticleSpawner.spawnParticle(EnumCustomParticleTypes.VOID_PORTAL, finalX, y + 0.15D, finalZ, 0.0D, 0.0D, 0.0D);
			}
		}
	}
    
    @Override
    protected int getExperiencePoints(EntityPlayer player)
    {
    	return 0;
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
