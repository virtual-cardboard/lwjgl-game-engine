package context.visuals.displayer;

import context.data.gui.GUI;
import context.visuals.renderer.GameRenderer;

public class GUIDisplayer extends AbstractDisplayer<GUI> {

	public GUIDisplayer(GameRenderer renderer) {
		super(renderer);
	}

	@Override
	public void display(GUI displayable) {
		if (!displayable.isEnabled())
			return;
		renderer.outlineColour(displayable.getOutlineR(), displayable.getOutlineG(), displayable.getBackgroundB());
		renderer.fill(displayable.getBackgroundR(), displayable.getBackgroundG(), displayable.getBackgroundB());
		renderer.drawRectangle(displayable.getX(), displayable.getY(), displayable.getWidth(), displayable.getHeight());
		renderer.fill(displayable.getTextR(), displayable.getTextG(), displayable.getTextB());
		renderer.textSize(displayable.getTextSize());
		renderer.text(displayable.getText(), displayable.getX() + displayable.getWidth() / 2,
				displayable.getY() + displayable.getHeight() / 2);
		for (GUI child : displayable.getChildren()) {
			display(child);
		}
	}

}
