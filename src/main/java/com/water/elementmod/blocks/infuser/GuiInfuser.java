package com.water.elementmod.blocks.infuser;

import com.water.elementmod.blocks.infuser.container.ContainerInfuser;
import com.water.elementmod.util.EMConfig;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiInfuser extends GuiContainer
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(EMConfig.MOD_ID + ":textures/gui/gui_infuser.png");
	private final InventoryPlayer player;
	private final TileEntityInfuser tileentity;
	
	public GuiInfuser(InventoryPlayer player, TileEntityInfuser tileentity)
	{
		super(new ContainerInfuser(player, tileentity));
		this.player = player;
		this.tileentity = tileentity;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String tileName = this.tileentity.getDisplayName().getFormattedText();
		this.fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2) + 3, 8, 4210752);
		this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 4, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		if(TileEntityInfuser.isBurning(tileentity))
		{
			int k = this.getBurnLeftScaled(11);
			this.drawTexturedModalRect(this.guiLeft + 139, this.guiTop + 50 + 10 - k, 176, 11 - k, 16, k + 1);
		}
		
		if(!TileEntityInfuser.isCompatible(tileentity))
		{
			this.drawTexturedModalRect(this.guiLeft + 75, this.guiTop + 33, 0, 184, 22, 15);
		}
		
		int l = this.getCookPorgressScaled(88);
		this.drawTexturedModalRect(this.guiLeft + 10, this.guiTop + 31, 0, 166, l, 18);
	}
	
	private int getBurnLeftScaled(int pixels)
	{
		int i = this.tileentity.getField(1);
		if(i == 0) i = 200;
		return this.tileentity.getField(0) * pixels / i;
	}
	
	private int getCookPorgressScaled(int pixels)
	{
		int i = this.tileentity.getField(2);
		int j = this.tileentity.getField(3);
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
