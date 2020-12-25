package bundle.visuals.display;

import bundle.data.gui.GameButton;
import bundle.visuals.renderer.AbstractGameRenderer;

public class ButtonDisplayer extends AbstractDisplayer<GameButton> {

	public ButtonDisplayer(AbstractGameRenderer renderer) {
		super(renderer);
	}

	@Override
	public void display(GameButton displayable) {
		if (!displayable.isEnabled())
			return;
		renderer.outlineColour(displayable.getOutlineR(), displayable.getOutlineG(), displayable.getBackgroundB());
		renderer.fill(displayable.getBackgroundR(), displayable.getBackgroundG(), displayable.getBackgroundB());
		renderer.drawRectangle(displayable.getX(), displayable.getY(), displayable.getWidth(), displayable.getHeight());
		renderer.fill(displayable.getTextR(), displayable.getTextG(), displayable.getTextB());
		renderer.textSize(displayable.getTextSize());
		renderer.text(displayable.getText(), displayable.getX() + displayable.getWidth() / 2,
				displayable.getY() + displayable.getHeight() / 2);
	}

}
