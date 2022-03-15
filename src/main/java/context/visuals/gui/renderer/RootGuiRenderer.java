package context.visuals.gui.renderer;

import java.util.List;

import common.math.Vector2f;
import context.GLContext;
import context.visuals.gui.Gui;
import context.visuals.gui.RootGui;
import context.visuals.renderer.GameRenderer;

public final class RootGuiRenderer extends GameRenderer {

	public RootGuiRenderer() {
	}

	public void render(GLContext glContext, RootGui root) {
		List<Gui> children = root.getChildren();
		for (int i = 0; i < children.size(); i++) {
			Gui child = children.get(i);
			if (child.isEnabled()) {
				recursiveRender(child, root, root.dimensions(), 0, 0, root.widthPx(), root.heightPx());
			}
		}
	}

	private void recursiveRender(Gui gui, RootGui root, Vector2f screenDim, float parentX, float parentY, float parentWidth, float parentHeight) {
		float x = gui.posX().calculateValue(parentX, parentX + parentWidth);
		float y = gui.posY().calculateValue(parentY, parentY + parentHeight);
		float w = gui.width().calculateValue(parentX, parentX + parentWidth);
		float h = gui.height().calculateValue(parentY, parentY + parentHeight);
		gui.render(glContext, screenDim, x, y, w, h);

		List<Gui> children = gui.getChildren();
		for (int i = 0; i < children.size(); i++) {
			Gui child = children.get(i);
			if (child.isEnabled()) {
				recursiveRender(child, root, screenDim, x, y, w, h);
			}
		}
	}

}