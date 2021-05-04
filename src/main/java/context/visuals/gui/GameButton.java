package context.visuals.gui;

public class GameButton extends Gui {

	public GameButton(String text, int x, int y, int w, int h, Runnable onClick) {
		super(text, x, y, w, h);
		setOnClick(onClick);
	}

}
