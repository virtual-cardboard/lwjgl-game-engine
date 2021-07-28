package context.visuals.gui;

public class InvisibleGui extends Gui {

	@Override
	public int getBackgroundColour() {
		return 0;
	}

	@Override
	public void setBackgroundColour(int backgroundColour) {
		System.err.println("Setting the background colour of an invisible GUI does nothing.");
	}

}
