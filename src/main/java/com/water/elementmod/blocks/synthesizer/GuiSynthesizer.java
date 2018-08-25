package com.water.elementmod.blocks.synthesizer;

import com.water.elementmod.blocks.synthesizer.container.ContainerSynthesizer;
import com.water.elementmod.util.References;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiSynthesizer extends GuiContainer
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(References.MOD_ID + ":textures/gui/gui_synthesizer.png");
	private final InventoryPlayer player;
	private final TileEntitySynthesizer tileentity;
	
	public GuiSynthesizer(InventoryPlayer player, TileEntitySynthesizer tileentity)
	{
		super(new ContainerSynthesizer(player, tileentity));
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
		
		if(TileEntitySynthesizer.isBurning(tileentity))
		{
			int k = this.getBurnLeftScaled(11);
			this.drawTexturedModalRect(this.guiLeft + 132, this.guiTop + 62 + 10 - k, 176, 29 - k, 16, k + 1);
		}
		
		int l = this.getCookPorgressScaled(38);
		this.drawTexturedModalRect(this.guiLeft + 31, this.guiTop + 33, 176, 0, l, 18);
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
