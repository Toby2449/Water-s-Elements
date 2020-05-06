package com.water.elementmod.particle;

import com.water.elementmod.util.EMConfig;
import com.water.elementmod.util.handlers.EMEventHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleLava extends Particle
{
	float particleScaleOverTime;
	private int i;
	private int i2;
	
	protected ParticleLava(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double par8, double par10, double par12)
    {
        this(worldIn, xCoordIn, yCoordIn, zCoordIn, 1.0F, par8, par10, par12);
    }

    protected ParticleLava(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, float scale, double par8, double par10, double par12)
    {
    	super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
    	this.motionX *= 0D;
        this.motionY = Math.random() * 0.01800000298023224D + 0.00900000149011612D;
        this.motionZ *= 0D;
        this.particleScale *= this.rand.nextFloat();
        this.particleScale *= scale;
        particleMaxAge = 20 + rand.nextInt(15);
        TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(new ResourceLocation(EMConfig.MOD_ID, "particle/lava").toString());
        this.setParticleTexture(sprite);
    }
    
    /**
     * Renders the particle
     */
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
    {
    	float f = ((float)this.particleAge + partialTicks) / (float)this.particleMaxAge;
        super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
    }
    
    @Override
	public int getFXLayer()
	{
		return 1;
	}
    
    public void onUpdate()
    {
    	this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= (double)this.particleGravity;
        this.move(this.motionX, -this.motionY, this.motionZ);
        this.motionX *= 0D;
        this.motionY *= 1.2D;
        this.motionZ *= 0D;
        
        float f = (float)this.particleAge / (float)this.particleMaxAge;
        
        if (this.rand.nextFloat() > f)
        {
            this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        }
        
        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setExpired();
        }
    	
    	prevParticleAngle = particleAngle;
    }
    
    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory
        {
            public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_)
            {
                return new ParticleLava(worldIn, xCoordIn, yCoordIn, zCoordIn, (float)xSpeedIn, (float)ySpeedIn, (float)zSpeedIn);
            }
        }
}