package context.visuals.gui;

import common.math.Matrix4f;
import context.GLContext;

public class InvisibleGui extends Gui {

	@Override
	public final void render(GLContext glContext, Matrix4f matrix4f, float parentX, float parentY, float parentWidth, float parentHeight) {
	}

	@Override
	public int getBackgroundColour() {
		return 0;
	}

	@Override
	public void setBackground(int backgroundColour) {
		System.err.println("Setting the background colour of an invisible GUI does nothing.");
	}

}