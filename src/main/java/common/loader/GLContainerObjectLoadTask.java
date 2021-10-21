package common.loader;

import java.util.concurrent.Future;

import context.visuals.lwjgl.GLContainerObject;

/**
 * A load task for a {@link GLContainerObject}. Submitting this load task to the
 * {@link GameLoader} immediately returns a completed {@link Future} to
 * {@link Future#get() get()} from. Only add this load task from the rendering
 * thread.
 * 
 * @author Jay
 *
 */
public abstract class GLContainerObjectLoadTask<T extends GLContainerObject> extends GLLoadTask<T> {

	@Override
	protected Future<T> accept(GameLoader loader) {
		return loader.submitAndReturnImmediately(this);
	}

}