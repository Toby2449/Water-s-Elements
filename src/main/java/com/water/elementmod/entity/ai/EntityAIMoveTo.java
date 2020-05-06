package com.water.elementmod.entity.ai;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAIMoveTo extends EntityAIBase
{
	private final EntityCreature creature;
    private final double movementSpeed;
    /** Controls task execution delay */
    protected int runDelay;
    private int timeoutCounter;
    private int maxStayTicks;
    /** Block to move to */
    protected BlockPos destinationBlock = BlockPos.ORIGIN;
    private boolean isAboveDestination;

    public EntityAIMoveTo(EntityCreature creature, double speedIn, int length, double x, double y, double z)
    {
        this.creature = creature;
        this.movementSpeed = speedIn;
        this.destinationBlock = new BlockPos(x, y, z);
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return true;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
    	return !this.creature.getNavigator().noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
    	this.creature.getNavigator().tryMoveToXYZ(this.destinationBlock.getX(), this.destinationBlock.getY() + 5, this.destinationBlock.getZ(), this.movementSpeed);
    }
}