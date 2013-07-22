package net.minecraft.client.gui.inventory;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;

import org.lo.d.commons.coords.Point2D;
import org.lo.d.commons.coords.Rect2D;
import org.lo.d.commons.gui.ContainerTab;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTabContainer extends GuiProxyContainer {
	public interface Drawer {
		public void draw();
	}

	public interface HandleInputListner {
		void handleInput();
	}

	public interface TabBase extends TabBaseDrawer {
		public Rect2D getHandleClickRect(Point2D tabDrawPoint, int tabIndex);
	}

	public interface TabBaseDrawer extends TabDrawer {
		public Point2D getFixedDrawPoint(Point2D tabDrawPoint, int tabIndex);

		public Point2D getForeGroundDrawPoint(Point2D tabDrawPoint, int tabIndex);
	}

	public interface TabDrawer {
		public void drawCurrentTab(Point2D tabDrawPoint, int tabIndex, TextureManager renderEngine);

		public void drawForcusTab(Point2D tabDrawPoint, int tabIndex, TextureManager renderEngine);

		public void drawTab(Point2D tabDrawPoint, int tabIndex, TextureManager renderEngine);
	}

	public interface TabEntry extends TabDrawer {
		public GuiContainer getContainer();
	}

	private interface TabDrawingWorker {
		void drawCurrentTab();

		void drawForcusTab();

		void drawTab();
	}

	protected final List<TabEntry> tabs = Lists.newArrayList();
	protected TabBase tabBase;

	private ContainerTab container;

	private Set<HandleInputListner> mouseInputListners = Sets.newHashSet();

	private Set<HandleInputListner> keyboardInputListners = Sets.newHashSet();

	public boolean isInitializing = false;

	public GuiTabContainer(ContainerTab par1Container) {
		super(par1Container);
		container = par1Container;
	}

	@Override
	public void actionPerformed(GuiButton par1GuiButton) {
		screen.actionPerformed(par1GuiButton);
	}

	public boolean addMouseInputListner(HandleInputListner e) {
		return mouseInputListners.add(e);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void drawDefaultBackground() {
	}

	@Override
	public void drawGuiContainerBackgroundLayer(final float f, final int mouseX, final int mouseY) {
		drawTabs(new Point2D(mouseX, mouseY), new Drawer() {
			@Override
			public void draw() {
				screen.drawScreen(mouseX, mouseY, f);
			}
		});
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float par3) {
		super.drawScreen(mouseX, mouseY, par3);
	}

	@Override
	public void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6) {
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(par1 + 0, par2 + par6, zLevel, (par3 + 0) * f, (par4 + par6) * f1);
		tessellator.addVertexWithUV(par1 + par5, par2 + par6, zLevel, (par3 + par5) * f, (par4 + par6) * f1);
		tessellator.addVertexWithUV(par1 + par5, par2 + 0, zLevel, (par3 + par5) * f, (par4 + 0) * f1);
		tessellator.addVertexWithUV(par1 + 0, par2 + 0, zLevel, (par3 + 0) * f, (par4 + 0) * f1);
		tessellator.draw();
	}

	@Override
	public void handleInput() {
		while (Mouse.next()) {
			handleMouseInput();
			for (HandleInputListner listner : mouseInputListners) {
				listner.handleInput();
			}
		}

		while (Keyboard.next()) {
			handleKeyboardInput();
			for (HandleInputListner listner : keyboardInputListners) {
				listner.handleInput();
			}
		}
	}

	@Override
	public void keyTyped(char par1, int par2) {
		screen.keyTyped(par1, par2);
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int buttonType) {
		Point2D mousePoint = new Point2D(mouseX, mouseY);

		boolean tabClicked = false;
		int tabIndex = 0;
		for (final TabEntry entry : tabs) {
			int x = screen.guiLeft;
			int y = screen.guiTop;
			final Point2D tabDrawPoint = new Point2D(x, y);
			if (tabBase.getHandleClickRect(tabDrawPoint, tabIndex).isInRect(mousePoint)) {
				if (buttonType == 0) {
					setCurrentScreen(entry.getContainer());
				}
				tabClicked = true;
				break;
			}
			tabIndex++;
		}
		if (!tabClicked) {
			screen.mouseClicked(mouseX, mouseY, buttonType);
		}
	}

	@Override
	public void mouseMovedOrUp(int mouseX, int mouseY, int buttonType) {
		Point2D mousePoint = new Point2D(mouseX, mouseY);

		boolean tabClicked = false;
		int tabIndex = 0;
		for (@SuppressWarnings("unused")
		final TabEntry entry : tabs) {
			int x = screen.guiLeft;
			int y = screen.guiTop;
			final Point2D tabDrawPoint = new Point2D(x, y);
			if (tabBase.getHandleClickRect(tabDrawPoint, tabIndex).isInRect(mousePoint)) {
				tabClicked = true;
				break;
			}
			tabIndex++;
		}
		if (!tabClicked) {
			screen.mouseMovedOrUp(mouseX, mouseY, buttonType);
		}
	}

	public void setCurrentScreen(GuiContainer container) {
		screen = container;
		mc.thePlayer.openContainer = screen.inventorySlots;
	}

	@Override
	public void setWorldAndResolution(Minecraft par1Minecraft, int par2, int par3) {
		isInitializing = true;
		super.setWorldAndResolution(par1Minecraft, par2, par3);
		for (TabEntry entry : tabs) {
			GuiContainer tabContainer = entry.getContainer();
			if (tabContainer != screen) {
				if (par1Minecraft.currentScreen == this) {
					par1Minecraft.currentScreen = tabContainer;
				}
				tabContainer.setWorldAndResolution(par1Minecraft, par2, par3);
				par1Minecraft.currentScreen = this;
			}
		}
		setCurrentScreen(screen);
		isInitializing = false;
	}

	protected void addTab(TabEntry tabEntry) {
		tabs.add(tabEntry);
		container.add(tabEntry.getContainer().inventorySlots);
	}

	protected void drawTabs(Point2D mousePoint, Drawer mainDrawer) {
		Deque<TabDrawingWorker> drawStack = new ArrayDeque<>();
		TabDrawingWorker hoverdTabDrawer = null;
		TabDrawingWorker currentTabDrawer = null;
		int i = 0;
		for (final TabEntry entry : tabs) {
			int x = screen.guiLeft;
			int y = screen.guiTop;
			final Point2D tabDrawPoint = new Point2D(x, y);
			final int tabIndex = i;
			TabDrawingWorker tabDrawingWorker = new TabDrawingWorker() {

				@Override
				public void drawCurrentTab() {
					Point2D drawPoint = tabBase.getFixedDrawPoint(tabDrawPoint, tabIndex);
					tabBase.drawCurrentTab(drawPoint, tabIndex, mc.renderEngine);
					entry.drawCurrentTab(tabBase.getForeGroundDrawPoint(drawPoint, tabIndex), tabIndex, mc.renderEngine);
				}

				@Override
				public void drawForcusTab() {
					Point2D drawPoint = tabBase.getFixedDrawPoint(tabDrawPoint, tabIndex);
					tabBase.drawForcusTab(drawPoint, tabIndex, mc.renderEngine);
					entry.drawForcusTab(tabBase.getForeGroundDrawPoint(drawPoint, tabIndex), tabIndex, mc.renderEngine);
				}

				@Override
				public void drawTab() {
					Point2D drawPoint = tabBase.getFixedDrawPoint(tabDrawPoint, tabIndex);
					tabBase.drawTab(drawPoint, tabIndex, mc.renderEngine);
					entry.drawTab(tabBase.getForeGroundDrawPoint(drawPoint, tabIndex), tabIndex, mc.renderEngine);

				}
			};
			if (entry.getContainer() == screen) {
				currentTabDrawer = tabDrawingWorker;
			} else if (tabBase.getHandleClickRect(tabDrawPoint, tabIndex).isInRect(mousePoint)) {
				hoverdTabDrawer = tabDrawingWorker;
			} else {
				drawStack.add(tabDrawingWorker);
			}
			i++;
		}
		mainDrawer.draw();

		zLevel = 150;
		TabDrawingWorker worker = null;
		while ((worker = drawStack.pollFirst()) != null) {
			worker.drawTab();
		}

		if (hoverdTabDrawer != null) {
			hoverdTabDrawer.drawForcusTab();
		}

		if (currentTabDrawer != null) {
			currentTabDrawer.drawCurrentTab();
		}
		zLevel = 0;

	}
}
