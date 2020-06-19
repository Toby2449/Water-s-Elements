package com.water.elementmod.blocks.valve;

import java.util.List;

import com.water.elementmod.entity.boss.water.EntityWaterBoss;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class TileEntityValve extends TileEntity implements ITickable
{
	public void activate(World world)
	{
		if(!this.world.isRemote)
		{
			List<EntityWaterBoss> AABBBoss = world.<EntityWaterBoss>getEntitiesWithinAABB(EntityWaterBoss.class, BlockValve.FULL_BLOCK_AABB.grow(750D, 100D, 750D).offset(0, -2.0D, 0));
			if (!AABBBoss.isEmpty())
		    {
		    	for (EntityWaterBoss ent : AABBBoss)
		        {
		    		int prephase = ent.getPrePhase();
		    		
		    		switch(prephase)
		    		{
		    		case 0:
		    			ent.ShutOffNear();
		    			ent.setPrePhase(prephase + 1);
		    			break;
		    		case 1:
		    			ent.ShutOffFar();
		    			ent.setPrePhase(prephase + 1);
		    			break;
		    		}
		        }
		    }
		}
	}
	
	@Override
	public void update() 
	{
		
	}

}
