package context.visuals.gui;

import context.visuals.gui.renderer.GuiRenderer;

public class LabelGui extends Gui {

	private int textColour;
	private String text;

	public LabelGui(GuiRenderer<LabelGui> guiRenderer) {
		this(guiRenderer, 255, "");
	}

	public LabelGui(GuiRenderer<LabelGui> guiRenderer, int textColour, String text) {
		super(guiRenderer);
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
