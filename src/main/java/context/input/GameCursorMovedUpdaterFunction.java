package context.input;

import java.util.function.Function;

import common.event.GameEvent;
import context.input.event.MouseMovedInputEvent;
import context.input.mouse.GameCursor;

class GameCursorMovedUpdaterFunction implements Function<MouseMovedInputEvent, GameEvent> {

	private GameCursor cursor;

	public GameCursorMovedUpdaterFunction(GameCursor cursor) {
		this.cursor = cursor;
	}

	@Override
	public GameEvent apply(MouseMovedInputEvent inputEvent) {
		cursor.setCursorCoordinates(inputEvent.getMouseX(), inputEvent.getMouseY());
		return null;
	}

}
