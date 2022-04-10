package context.input;

import java.util.function.Function;

import context.input.event.MousePressedInputEvent;
import context.input.mouse.GameCursor;
import engine.common.event.GameEvent;

class GameCursorPressedUpdaterFunction implements Function<MousePressedInputEvent, GameEvent> {

	private GameCursor cursor;

	public GameCursorPressedUpdaterFunction(GameCursor cursor) {
		this.cursor = cursor;
	}

	@Override
	public GameEvent apply(MousePressedInputEvent inputEvent) {
		cursor.getPressedButtons()[inputEvent.button()] = true;
		return null;
	}

}
