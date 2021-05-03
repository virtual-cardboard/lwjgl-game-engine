package context.visuals.templates;

import context.visuals.displayer.Displayable;

public class GameBackground implements Displayable {

	private int r;
	private int g;
	private int b;

	public GameBackground(int r, int g, int b) {
		super();
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	@Override
	public String getDisplayerClass() {
		return getDefaultDisplayerClass();
	}

	public Object getColour() {
		return null;
	}

}
