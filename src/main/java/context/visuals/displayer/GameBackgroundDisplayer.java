package context.visuals.displayer;

import context.visuals.renderer.GameRenderer;
import context.visuals.templates.GameBackground;

public class GameBackgroundDisplayer extends AbstractDisplayer<GameBackground> {

	public GameBackgroundDisplayer(GameRenderer renderer) {
		super(renderer);
	}

	@Override
	public void display(GameBackground displayable) {
		renderer.drawRectangle(0, 0, renderer.getWidth(), renderer.getHeight(), displayable.getR(), displayable.getG(), displayable.getB());
	}

}
