package engine.common;

/**
 * A pair of objects.
 * 
 * @author Jay
 *
 * @param <A>
 * @param <B>
 */
public final class Pair<A, B> {

	public A a;
	public B b;

	public Pair(A a, B b) {
		this.a = a;
		this.b = b;
	}

}
