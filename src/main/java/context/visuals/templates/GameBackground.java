package context.visuals.templates;

import static context.visuals.colour.Colour.colour;

import context.visuals.displayer.Displayable;

public class GameBackground implements Displayable {

	private int colour;

	public GameBackground(int r, int g, int b) {
		colour = colour(r, g, b, 255);
	}

	public GameBackground(int colour) {
		this.colour = colour;
	}

	public int getColour() {
		return colour;
	}

	public void setColour(int colour) {
		this.colour = colour;
	}

}
