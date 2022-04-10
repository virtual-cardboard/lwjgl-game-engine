package context.input;

import java.util.function.Function;

import context.GameContext;
import context.input.event.FrameResizedInputEvent;
import context.visuals.lwjgl.FrameBufferObject;
import context.visuals.lwjgl.Texture;
import engine.common.event.GameEvent;

public class FrameBufferObjectUpdaterFunction implements Function<FrameResizedInputEvent, GameEvent> {

	private FrameBufferObject fbo;
	private GameContext context;

	public FrameBufferObjectUpdaterFunction(GameContext context, FrameBufferObject fbo) {
		this.context = context;
		this.fbo = fbo;
	}

	@Override
	public GameEvent apply(FrameResizedInputEvent t) {
		Texture texture = fbo.texture();
		if (texture.width() < t.width() || texture.height() < t.height()) {
			texture.resize(context.glContext(), t.width(), t.height());
		}
		return null;
	}

}
