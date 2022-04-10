package context.input;

import java.util.function.Function;

import context.input.event.MouseMovedInputEvent;
import context.input.mouse.GameCursor;
import engine.common.event.GameEvent;

class GameCursorMovedUpdaterFunction implements Function<MouseMovedInputEvent, GameEvent> {

	private GameCursor cursor;

	public GameCursorMovedUpdaterFunction(GameCursor cursor) {
		this.cursor = cursor;
	}

	@Override
	public GameEvent apply(MouseMovedInputEvent inputEvent) {
		cursor.setPos(inputEvent.x(), inputEvent.y());
		return null;
	}

}
