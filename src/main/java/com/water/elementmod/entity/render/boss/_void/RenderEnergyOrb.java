package com.water.elementmod.entity.render.boss._void;

import com.water.elementmod.entity.boss._void.EntityCarapace;
import com.water.elementmod.entity.boss._void.EntityEnergyOrb;
import com.water.elementmod.entity.models.boss._void.ModelEnergyOrb;
import com.water.elementmod.entity.models.boss.fire.ModelFireCrystal;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderEnergyOrb extends Render<EntityEnergyOrb>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(EMConfig.MOD_ID + ":textures/entity/carapace_energy_orb.png");
    private final ModelBase model = new ModelEnergyOrb(0.0F);
    
    public RenderEnergyOrb(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
        this.shadowSize = 0.2F;
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityEnergyOrb entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        float f = (float)entity.innerRotation + partialTicks;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        this.bindTexture(TEXTURE);
        float f1 = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
        f1 = f1 * f1 + f1;

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        this.model.render(entity, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);

        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();
        
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(EntityEnergyOrb entity)
    {
        return TEXTURE;
    }

    public boolean shouldRender(EntityEnergyOrb livingEntity, ICamera camera, double camX, double camY, double camZ)
    {
        return super.shouldRender(livingEntity, camera, camX, camY, camZ);
    }
}
