package context.visuals.gui.renderer;

import java.util.List;

import common.math.Matrix4f;
import common.math.Vector2f;
import common.math.Vector3f;
import context.GLContext;
import context.visuals.gui.Gui;
import context.visuals.gui.RootGui;
import context.visuals.renderer.GameRenderer;

public final class RootGuiRenderer extends GameRenderer {

	public RootGuiRenderer() {
	}

	public void render(GLContext glContext, RootGui root) {
		Matrix4f matrix4f = new Matrix4f();
		matrix4f.translate(new Vector2f(-1, 1));
		matrix4f.scale(new Vector3f(2f / root.width(), -2f / root.height(), 1));

		List<Gui> children = root.getChildren();
		for (int i = 0; i < children.size(); i++) {
			Gui child = children.get(i);
			if (child.isEnabled()) {
				doRecursiveRender(glContext, child, root, matrix4f, 0, 0, root.width(), root.height());
			}
		}
	}

	private void doRecursiveRender(GLContext glContext, Gui gui, RootGui root, Matrix4f matrix4f, float parentX, float parentY, float parentWidth,
			float parentHeight) {
		float x = gui.getPosX().calculateValue(parentX, parentX + parentWidth);
		float y = gui.getPosY().calculateValue(parentY, parentY + parentHeight);
		float w = gui.getWidth().calculateValue(parentX, parentX + parentWidth);
		float h = gui.getHeight().calculateValue(parentY, parentY + parentHeight);
		gui.render(glContext, matrix4f.copy(), x, y, w, h);

		List<Gui> children = gui.getChildren();
		for (int i = 0; i < children.size(); i++) {
			Gui child = children.get(i);
			if (child.isEnabled()) {
				doRecursiveRender(glContext, child, root, matrix4f, x, y, w, h);
			}
		}
	}

}