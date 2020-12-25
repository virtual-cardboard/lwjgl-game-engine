package bundle.data.gui;

public class GameButton extends AbstractGUI {

	public GameButton(String text, int x, int y, int w, int h, Runnable onClick) {
		super(text, x, y, w, h);
		setOnClick(onClick);
	}

}
