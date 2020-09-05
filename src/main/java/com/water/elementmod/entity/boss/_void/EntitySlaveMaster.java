package com.water.elementmod.entity.boss._void;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.water.elementmod.EMCoreBlocks;
import com.water.elementmod.EMCorePotionEffects;
import com.water.elementmod.entity.ai.EntityAIMoveTo;
import com.water.elementmod.network.PacketCarapaceBeam;
import com.water.elementmod.network.PacketCarapaceBeamGrow;
import com.water.elementmod.network.PacketCarapaceBlueBuffExplosion;
import com.water.elementmod.network.PacketCarapaceParticleCircle;
import com.water.elementmod.network.PacketCarapaceParticleRing;
import com.water.elementmod.network.PacketCarapacePortalParticles;
import com.water.elementmod.network.PacketCarapacePurpleBuffArea1;
import com.water.elementmod.network.PacketCarapacePurpleBuffArea2;
import com.water.elementmod.network.PacketCarapacePurpleBuffArea3;
import com.water.elementmod.network.PacketCarapacePurpleBuffExplode1;
import com.water.elementmod.network.PacketCarapacePurpleBuffExplode2;
import com.water.elementmod.network.PacketCarapacePurpleBuffExplode3;
import com.water.elementmod.network.PacketCarapaceRingExplosion;
import com.water.elementmod.network.PacketCarapaceSightAttack;
import com.water.elementmod.network.PacketCarapaceSightExplode;
import com.water.elementmod.network.PacketHandler;
import com.water.elementmod.network.PacketPlayMusic;
import com.water.elementmod.network.PacketStopMusic;
import com.water.elementmod.particle.EnumCustomParticleTypes;
import com.water.elementmod.particle.ParticleSpawner;
import com.water.elementmod.util.EMConfig;
import com.water.elementmod.util.handlers.EMSoundHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public class EntitySlaveMaster extends EntityMob
{
	private boolean scaledHP = false;
	
	public EntitySlaveMaster(World worldIn)
	{
		super(worldIn);
		this.setSize(1.6F, 4.5F);
	}
	
	public static void registerFixesBoss(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntitySlaveMaster.class);
    }
	
	@Override
	protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 0.5D, true));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    }
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(_ConfigEntityCarapace.BASE_HP / 3);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.5D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
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
		List<EntityPlayer> WeaknessList = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE, _ConfigEntityVoidEntity.ARENA_SIZE));
		for (EntityPlayer ent : WeaknessList)
        {
			ent.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 20, 0));
        }
		
		if(!this.world.isRemote)
    	{
    		if(!this.scaledHP)
    		{
    			this.scaledHP = true;
	    		int numOfPlayers = 0;
	        	List<EntityPlayer> players = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(_ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE, _ConfigEntityCarapace.ARENA_SIZE).offset(0, -5, 0));
		        if(!players.isEmpty())
		        {
		        	for (EntityPlayer entity : players)
			        {
		        		numOfPlayers++;
			        }
		        }
		        
		        if(numOfPlayers > 1)
		        {
		        	float scaledHP = (_ConfigEntityCarapace.BASE_HP / 3) + ((_ConfigEntityCarapace.HP_SCALE_AMOUNT / 3) * numOfPlayers);
		        	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(scaledHP);
		        	this.setHealth(scaledHP);
		        }
    		}
    	}
    }
	
	@Override
	public float getEyeHeight()
	{
		return 3.1F;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		Random rand = new Random();
    	int r = rand.nextInt(3);
    	switch(r)
    	{
    	default:
    		return EMSoundHandler.CARAPACE_WOUND_01;
    	case 0:
    		return EMSoundHandler.CARAPACE_WOUND_01;
    	case 1:
    		return EMSoundHandler.CARAPACE_WOUND_02;
    	case 2:
    		return EMSoundHandler.CARAPACE_WOUND_03;
    	}
	}
	
	@Override
	protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_SHULKER_DEATH;
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
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);
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
