package com.water.elementmod.world.dimension;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleportWithoutPortal extends Teleporter
{
	private final Long2ObjectMap<Teleporter.PortalPosition> destinationCache = new Long2ObjectOpenHashMap<>(4096);

    public TeleportWithoutPortal(WorldServer world) {
        super(world);
    }

    @Override
    public void placeInPortal(Entity entity, float rotationYaw) {
        this.placeInExistingPortal(entity, rotationYaw);
    }

    @Override
    public boolean placeInExistingPortal(Entity entity, float rotationYaw) {
        double distance = -1;
        BlockPos entityPos = entity.getPosition();
        long chunkPos = ChunkPos.asLong(MathHelper.floor(entity.posX), MathHelper.floor(entity.posZ));

        if (this.destinationCache.containsKey(chunkPos)) {
            Teleporter.PortalPosition portalPosition = this.destinationCache.get(chunkPos);
            distance = 0;
            entityPos = portalPosition;
            portalPosition.lastUpdateTime = this.world.getTotalWorldTime();
        } else {
            for (int x = -16; x <= 16; x++) {
                BlockPos portalPos;
                for (int z = -16; z <= 16; z++) {
                    for (BlockPos blockpos = entityPos.add(x, this.world.getActualHeight() - 1 - entityPos.getY(), z); blockpos.getY() >= 0; blockpos = portalPos) {
                        portalPos = blockpos.down();
                    }
                }
            }
            if (distance >= 0) {
                this.destinationCache.put(chunkPos, new Teleporter.PortalPosition(entityPos, this.world.getTotalWorldTime()));
            }
        }

        if (distance >= 0) {
            entity.motionX = 0;
            entity.motionZ = 0;

            if (entity instanceof EntityPlayerMP) {
                ((EntityPlayerMP) entity).connection.setPlayerLocation(entityPos.getX() + 0.5, entityPos.getY() + 0.5, entityPos.getZ() + 0.5, entity.rotationYaw, entity.rotationPitch);
            } else {
                entity.setLocationAndAngles(0, 10, 0, entity.rotationYaw, entity.rotationPitch);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean makePortal(Entity entity) {
        return false;
    }

    @Override
    public void removeStalePortalLocations(long worldTime) {
    }
}
