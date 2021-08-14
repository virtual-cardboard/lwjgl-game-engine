package context.visuals.gui.renderer;

import java.util.List;

import common.math.Matrix4f;
import common.math.Vector2f;
import common.math.Vector3f;
import context.visuals.gui.Gui;
import context.visuals.gui.RootGui;

public final class RootGuiRenderer extends GuiRenderer<RootGui> {

	@Override
	public void render(RootGui root, Matrix4f matrix4f, float parentX, float parentY, float parentWidth, float parentHeight) {
		throw new IllegalStateException("Should not be calling this render function in RootGuiRenderer.");
	}

	public void render(RootGui root) {
		Matrix4f matrix4f = new Matrix4f();
		matrix4f.translate(new Vector2f(-1, 1));
		matrix4f.scale(new Vector3f(2f / root.getWidth(), -2f / root.getHeight(), 1));

		List<Gui> children = root.getChildren();
		for (int i = 0; i < children.size(); i++) {
			Gui child = children.get(i);
			doRecursiveRender(child, root, matrix4f, 0, 0, root.getWidth(), root.getHeight());
		}
	}

	private void doRecursiveRender(Gui gui, RootGui root, Matrix4f matrix4f, float parentX, float parentY, float parentWidth, float parentHeight) {
		gui.getGuiRenderer().renderGui(gui, matrix4f, parentX, parentY, parentWidth, parentHeight);
		float x = gui.getPosXConstraint().calculateValue(parentX, parentX + parentWidth);
		float y = gui.getPosYConstraint().calculateValue(parentY, parentY + parentHeight);
		float w = gui.getWidthConstraint().calculateValue(parentX, parentX + parentWidth);
		float h = gui.getHeightConstraint().calculateValue(parentY, parentY + parentHeight);

		List<Gui> children = gui.getChildren();
		for (int i = 0; i < children.size(); i++) {
			Gui child = children.get(i);
			if (child.isEnabled()) {
				doRecursiveRender(child, root, matrix4f, x, y, w, h);
			}
		}
	}

}