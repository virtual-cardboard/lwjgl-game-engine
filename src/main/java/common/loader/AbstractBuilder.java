package common.loader;

public abstract class AbstractBuilder<T> {

	private T t;

	protected final void build(T t) {
		this.t = t;
	}

	public final T get() {
		return t;
	}

	public final void set(T t) {
		this.t = t;
	}

	public boolean isBuilt() {
		return t != null;
	}

}
