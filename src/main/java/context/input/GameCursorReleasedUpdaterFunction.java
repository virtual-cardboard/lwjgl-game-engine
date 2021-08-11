package context.input;

import java.util.function.Function;

import common.event.GameEvent;
import context.input.event.MouseReleasedInputEvent;
import context.input.mouse.GameCursor;

class GameCursorReleasedUpdaterFunction implements Function<MouseReleasedInputEvent, GameEvent> {

	private GameCursor cursor;

	public GameCursorReleasedUpdaterFunction(GameCursor cursor) {
		this.cursor = cursor;
	}

	@Override
	public GameEvent apply(MouseReleasedInputEvent inputEvent) {
		cursor.getPressedButtons()[inputEvent.getMouseButton()] = false;
		return null;
	}

}
