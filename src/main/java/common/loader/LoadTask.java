package common.loader;

import java.util.concurrent.Future;

/**
 * Use a LoadTask for I/O operations.
 * 
 * @author Lunkle
 * @param <T> the type loaded
 * @see IOLoader
 * @see GLLoader
 *
 */
public interface LoadTask<T> {

	public Future<T> accept(GameLoader loader);

}