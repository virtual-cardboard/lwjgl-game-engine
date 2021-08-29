package context.visuals.gui;

import common.math.Matrix4f;
import context.visuals.gui.renderer.GuiRenderer;

public class InvisibleGui extends Gui {

	public InvisibleGui() {
		super(InvisibleGuiRenderer.INSTANCE);
	}

	@Override
	public int getBackgroundColour() {
		return 0;
	}

	@Override
	public void setBackground(int backgroundColour) {
		System.err.println("Setting the background colour of an invisible GUI does nothing.");
	}

	private static class InvisibleGuiRenderer extends GuiRenderer<InvisibleGui> {

		private static final InvisibleGuiRenderer INSTANCE = new InvisibleGuiRenderer();

		@Override
		public void render(InvisibleGui gui, Matrix4f matrix4f, float parentX, float parentY, float parentWidth, float parentHeight) {
		}

	}

}