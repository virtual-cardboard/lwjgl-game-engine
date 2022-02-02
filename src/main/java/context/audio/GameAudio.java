package context.audio;

import common.loader.GameLoader;
import context.ContextPart;

public abstract class GameAudio extends ContextPart {

	private GameLoader loader;

	public abstract void update();

	public final void setComponents(GameLoader loader) {
		this.loader = loader;
	}

	public final GameLoader loader() {
		return loader;
	}

}
