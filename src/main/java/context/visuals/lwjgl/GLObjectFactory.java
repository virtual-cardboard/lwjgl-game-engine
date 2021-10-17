package context.visuals.lwjgl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import common.loader.GameLoader;
import common.loader.loadtask.BuildGLObjectLoadTask;
import context.visuals.lwjgl.builder.ContainerGLObjectBuilder;
import context.visuals.lwjgl.builder.GLObjectBuilder;
import context.visuals.lwjgl.builder.RegularGLObjectBuilder;

public final class GLObjectFactory {

	private GameLoader loader;

	public GLObjectFactory(GameLoader loader) {
		this.loader = loader;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T extends GLObject> Future<T> createUsingBuilder(GLObjectBuilder<T> builder) {
		if (builder instanceof RegularGLObjectBuilder) {
			return (Future<T>) CompletableFuture.supplyAsync(() -> {
				BuildGLObjectLoadTask t = new BuildGLObjectLoadTask<>((RegularGLObjectBuilder) builder);
				loader.add(t);
				t.await();
				return t.getGlObject();
			});
		} else if (builder instanceof ContainerGLObjectBuilder) {
			return CompletableFuture.completedFuture(builder.build());
		} else {
			throw new RuntimeException("Builder not a RegularGLObjectBuilder or a ContainerGLObjectBuilder: " + builder);
		}
	}

}
