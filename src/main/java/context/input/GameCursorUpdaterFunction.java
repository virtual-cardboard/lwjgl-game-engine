package context.input;

import java.util.function.Function;

import common.event.GameEvent;
import context.input.event.MouseMovedInputEvent;
import context.input.mouse.GameCursor;

class GameCursorUpdaterFunction implements Function<MouseMovedInputEvent, GameEvent> {

	private GameCursor cursor;

	public GameCursorUpdaterFunction(GameCursor cursor) {
		this.cursor = cursor;
	}

	@Override
	public GameEvent apply(MouseMovedInputEvent inputEvent) {
		cursor.setCursorCoordinates(inputEvent.getMouseX(), inputEvent.getMouseY());
		return null;
	}

}
