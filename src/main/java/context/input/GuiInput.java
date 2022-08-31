package context.input;

import java.util.List;

import context.visuals.GameVisuals;
import context.visuals.gui.Gui;
import context.visuals.gui.RootGui;
import context.visuals.gui.effects.HasMoveEffect;
import context.visuals.gui.effects.HasPressEffect;
import context.visuals.gui.effects.HasReleaseEffect;
import engine.common.math.Vector2i;

/**
 * An extension of the {@link GameInput} class that handles gui input effects.
 * <p>
 * Guis must extend {@link HasPressEffect}, {@link HasReleaseEffect}, and/or {@link HasMoveEffect} to be recognized by
 * this class.
 * <p>
 * You must call {@link #initGuiFunctions()} in the {@link #init()} method to initialize the gui functions.
 */
public abstract class GuiInput extends GameInput {

	protected void initGuiFunctions() {
		GameVisuals visuals = context().visuals();
		addMousePressedFunction(event -> {
			HasPressEffect gui = (HasPressEffect) getGui(HasPressEffect.class, cursor().pos(), visuals.rootGui());
			return gui != null ? gui.doPressEffect(cursor()) : null;
		});
		addMouseReleasedFunction(event -> {
			releaseAllGuis(cursor().pos(), visuals.rootGui());
			HasReleaseEffect gui = (HasReleaseEffect) getGui(HasReleaseEffect.class, cursor().pos(), visuals.rootGui());
			return gui != null ? gui.doReleaseEffect(cursor()) : null;
		});
		addMouseMovedFunction(event -> {
			HasMoveEffect gui = (HasMoveEffect) getGui(HasMoveEffect.class, cursor().pos(), visuals.rootGui());
			return gui != null ? gui.doMoveEffect(cursor()) : null;
		});
	}

	private Gui getGui(Class<?> clazz, Vector2i coords, RootGui rootGui) {
		float width = rootGui.widthPx();
		float height = rootGui.heightPx();
		List<Gui> children = rootGui.getChildren();
		for (Gui child : children) {
			Gui g = doGetGui(clazz, coords, child, 0, 0, width, height);
			if (g != null) {
				return g;
			}
		}
		return null;
	}

	private Gui doGetGui(Class<?> clazz, Vector2i coords, Gui gui, float pX, float pY, float pW, float pH) {
		if (!gui.isEnabled()) {
			return null;
		}
		float x = gui.posX().get(pX, pX + pW);
		float y = gui.posY().get(pY, pY + pH);
		float w = gui.width().get(pX, pX + pW);
		float h = gui.height().get(pY, pY + pH);
		List<Gui> children = gui.getChildren();
		for (Gui child : children) {
			Gui g = doGetGui(clazz, coords, child, x, y, w, h);
			if (g != null) {
				return gui;
			}
		}
		if (clazz.isInstance(gui) && x <= coords.x() && coords.x() <= x + w && y <= coords.y() && coords.y() <= y + h) {
			return gui;
		}
		return null;
	}

	private void releaseAllGuis(Vector2i pos, Gui gui) {
		if (gui instanceof HasReleaseEffect) {
			((HasReleaseEffect) gui).setPressed(false);
		} else if (gui instanceof HasPressEffect) {
			((HasPressEffect) gui).setPressed(false);
		}
		List<Gui> children = gui.getChildren();
		for (Gui child : children) {
			releaseAllGuis(pos, child);
		}
	}

}
