package net.minecraft.client.gui.inventory;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiProxyContainer extends GuiContainer {
	protected GuiContainer screen;

	public GuiProxyContainer(Container par1Container) {
		super(par1Container);
	}

	@Override
	public void actionPerformed(GuiButton par1GuiButton) {
		screen.actionPerformed(par1GuiButton);
	}

	@Override
	public boolean checkHotbarKeys(int par1) {
		return screen.checkHotbarKeys(par1);
	}

	@Override
	public void confirmClicked(boolean par1, int par2) {
		screen.confirmClicked(par1, par2);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return screen.doesGuiPauseGame();
	}

	@Override
	public void drawBackground(int par1) {
		screen.drawBackground(par1);
	}

	@Override
	public void drawCenteredString(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5) {
		screen.drawCenteredString(par1FontRenderer, par2Str, par3, par4, par5);
	}

	@Override
	public void drawCreativeTabHoveringText(String par1Str, int par2, int par3) {
		screen.drawCreativeTabHoveringText(par1Str, par2, par3);
	}

	@Override
	public void drawDefaultBackground() {
		screen.drawDefaultBackground();
	}

	@Override
	public void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6) {
		screen.drawGradientRect(par1, par2, par3, par4, par5, par6);
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		super.drawGuiContainerForegroundLayer(par1, par2);
	}

	@Override
	public void drawHorizontalLine(int par1, int par2, int par3, int par4) {
		screen.drawHorizontalLine(par1, par2, par3, par4);
	}

	@Override
	public void drawHoveringText(@SuppressWarnings("rawtypes") List par1List, int par2, int par3, FontRenderer font) {
		screen.drawHoveringText(par1List, par2, par3, font);
	}

	@Override
	public void drawItemStackTooltip(ItemStack par1ItemStack, int par2, int par3) {
		screen.drawItemStackTooltip(par1ItemStack, par2, par3);
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
	}

	@Override
	public void drawSlotInventory(Slot par1Slot) {
		screen.drawSlotInventory(par1Slot);
	}

	@Override
	public void drawString(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5) {
		screen.drawString(par1FontRenderer, par2Str, par3, par4, par5);
	}

	@Override
	public void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6) {
		screen.drawTexturedModalRect(par1, par2, par3, par4, par5, par6);
	}

	@Override
	public void drawTexturedModelRectFromIcon(int par1, int par2, Icon par3Icon, int par4, int par5) {
		screen.drawTexturedModelRectFromIcon(par1, par2, par3Icon, par4, par5);
	}

	@Override
	public void drawVerticalLine(int par1, int par2, int par3, int par4) {
		screen.drawVerticalLine(par1, par2, par3, par4);
	}

	@Override
	public void drawWorldBackground(int par1) {
		screen.drawWorldBackground(par1);
	}

	@Override
	public void func_102021_a(@SuppressWarnings("rawtypes") List par1List, int par2, int par3) {
		screen.func_102021_a(par1List, par2, par3);
	}

	@Override
	public void func_85041_a(int par1, int par2, int par3, long par4) {
		screen.func_85041_a(par1, par2, par3, par4);
	}

	public GuiContainer getScreen() {
		return screen;
	}

	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	public boolean isPointInRegion(int par1, int par2, int par3, int par4, int par5, int par6) {
		return screen.isPointInRegion(par1, par2, par3, par4, par5, par6);
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		screen.onGuiClosed();
	}

	@Override
	public void setWorldAndResolution(Minecraft par1Minecraft, int par2, int par3) {
		super.setWorldAndResolution(par1Minecraft, par2, par3);
		if (par1Minecraft.currentScreen == this) {
			par1Minecraft.currentScreen = screen;
		}
		screen.setWorldAndResolution(par1Minecraft, par2, par3);
		par1Minecraft.currentScreen = this;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		screen.updateScreen();
	}

}
