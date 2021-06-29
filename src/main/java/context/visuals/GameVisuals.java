package context.visuals;

import context.ContextPart;
import context.data.GameData;
import context.visuals.gui.Gui;
import context.visuals.gui.RootGui;
import context.visuals.renderer.GameRenderer;
import context.visuals.renderer.rectrenderer.GuiDisplayer;

/**
 * A bundle part that displays visuals based on data from {@link GameData}.
 * 
 * @author Jay
 *
 */
public abstract class GameVisuals extends ContextPart {

	/**
	 * The root GUI to which all guis will be descendants of.
	 */
	private RootGui rootGui = new RootGui(1280, 720);

//	private List<Consumer<KeyPressedInputEvent>> onKeyPressed = new ArrayList<>();
//	private List<Consumer<KeyReleasedInputEvent>> onKeyReleased = new ArrayList<>();
//	private List<Consumer<KeyRepeatedInputEvent>> onKeyRepeated = new ArrayList<>();
//	private List<Consumer<MouseMovedInputEvent>> onMouseMoved = new ArrayList<>();
//	private List<Consumer<MousePressedInputEvent>> onMousePressed = new ArrayList<>();
//	private List<Consumer<MouseReleasedInputEvent>> onMouseReleased = new ArrayList<>();
//	private List<Consumer<MouseScrolledInputEvent>> onMouseScrolled = new ArrayList<>();

	public void addGui(Gui gui) {
		rootGui.addChild(gui);
	}

	public RootGui getRootGui() {
		return rootGui;
	}

	public void init() {
//		rootGui.setPosXConstraint(new PixelPositionConstraint(0));
//		rootGui.setPosYConstraint(new PixelPositionConstraint(0));
//		rootGui.setWidthConstraint(new PixelDimensionConstraint(1280));
//		rootGui.setHeightConstraint(new PixelDimensionConstraint(720));
	}

	public void render(GameRenderer gameRenderer) {
		GuiDisplayer guiDisplayer = gameRenderer.getGuiDisplayer();
		guiDisplayer.render(rootGui);
	}

}
