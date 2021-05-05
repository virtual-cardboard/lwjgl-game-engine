package context.visuals.displayable;

import static context.visuals.colour.Colour.colour;

public class GameBackground {

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
