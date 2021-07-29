package context.visuals.gui;

import java.util.function.Consumer;

import common.coordinates.IntCoordinates;
import context.input.GameInput;
import context.visuals.gui.traits.HasClickEffect;
import context.visuals.gui.traits.HasMoveEffect;

/**
 * A Gui with an effect when the cursor moves, presses, or releases on it. The
 * game engine, by default, does not handle buttons being pressed. The user will
 * have to implement their own input handler.
 * 
 * @author Jay
 * 
 * @see Gui
 * @see GameInput
 *
 */
public class GuiButton extends Gui implements HasMoveEffect, HasClickEffect {

	private Consumer<IntCoordinates> moveEffect;
	private Consumer<IntCoordinates> pressEffect;
	private Consumer<IntCoordinates> releaseEffect;
	private boolean pressed;
	private int textColour;
	private String text;

	public GuiButton(Consumer<IntCoordinates> moveEffect, Consumer<IntCoordinates> pressEffect, Consumer<IntCoordinates> releaseEffect) {
		this.moveEffect = moveEffect != null ? moveEffect : GuiButton::doNothing;
		this.pressEffect = pressEffect != null ? pressEffect : GuiButton::doNothing;
		this.releaseEffect = releaseEffect != null ? releaseEffect : GuiButton::doNothing;
	}

	@Override
	public Consumer<IntCoordinates> getMoveEffect() {
		return moveEffect;
	}

	@Override
	public Consumer<IntCoordinates> getPressEffect() {
		return pressEffect;
	}

	@Override
	public Consumer<IntCoordinates> getReleaseEffect() {
		return releaseEffect;
	}

	@Override
	public boolean isPressed() {
		return pressed;
	}

	@Override
	public void setPressed(boolean pressed) {
		this.pressed = pressed;
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

	private static void doNothing(IntCoordinates cursorCoordinates) {
	}

}
