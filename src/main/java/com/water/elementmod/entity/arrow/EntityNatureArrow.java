package com.water.elementmod.entity.arrow;

import com.water.elementmod.EMCoreItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityNatureArrow extends EntityArrow
{

	public EntityNatureArrow(World worldIn) 
	{
		super(worldIn);
	}
	
	public EntityNatureArrow(World worldIn, double x, double y, double z) 
	{
		super(worldIn, x, y, z);
	}
	
	public EntityNatureArrow(World worldIn, EntityLivingBase shooter) 
	{
		super(worldIn, shooter);
	}

	@Override
	protected ItemStack getArrowStack() 
	{
		return new ItemStack(EMCoreItems.NATURE_ARROW);
	}
	
	@Override
	protected void arrowHit(EntityLivingBase living)
	{
		super.arrowHit(living);
	}
	
	@Override
	public void onUpdate()
	{
		
	}

}
