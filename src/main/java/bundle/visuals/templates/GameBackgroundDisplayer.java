package bundle.visuals.templates;

import bundle.visuals.display.AbstractDisplayer;
import bundle.visuals.renderer.AbstractGameRenderer;

public class GameBackgroundDisplayer extends AbstractDisplayer<GameBackground> {

	public GameBackgroundDisplayer(AbstractGameRenderer renderer) {
		super(renderer);
	}

	@Override
	public void display(GameBackground displayable) {
		renderer.clear(displayable.getR(), displayable.getG(), displayable.getB());
	}

}
