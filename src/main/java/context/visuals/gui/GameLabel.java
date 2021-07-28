package context.visuals.gui;

public class GameLabel extends Gui {

	private int textColour;
	private String text;

	public GameLabel() {
		this(255, "");
	}

	public GameLabel(int textColour, String text) {
		this.textColour = textColour;
		this.text = text;
	}

	public int getTextColour() {
		return textColour;
	}

	public void setTextColour(int textColour) {
		this.textColour = textColour;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
