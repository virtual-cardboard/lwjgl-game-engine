package context.input;

import java.util.function.Function;

import context.input.event.MouseReleasedInputEvent;
import context.input.mouse.GameCursor;
import engine.common.event.GameEvent;

class GameCursorReleasedUpdaterFunction implements Function<MouseReleasedInputEvent, GameEvent> {

	private GameCursor cursor;

	public GameCursorReleasedUpdaterFunction(GameCursor cursor) {
		this.cursor = cursor;
	}

	@Override
	public GameEvent apply(MouseReleasedInputEvent inputEvent) {
		cursor.getPressedButtons()[inputEvent.button()] = false;
		return null;
	}

}
