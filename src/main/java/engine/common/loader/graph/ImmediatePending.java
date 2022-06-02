package engine.common.loader.graph;

import context.ResourcePack;
import engine.common.loader.GameLoader;
import engine.common.loader.LoadTask;

public class ImmediatePending<T> extends Pending<T> {

	protected ImmediatePending(T data) {
		super(data);
	}

	@Override
	public LoadTask<T> generateLoadTask(GameLoader loader, ResourcePack pack) {
		throw new RuntimeException("Should not generate a load task for an immediate pending because there is nothing to load.");
	}

}
