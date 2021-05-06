package common;

import java.util.function.Function;
import java.util.function.Predicate;

public class ConditionalFunction<T, R> {

	private final Predicate<T> predicate;
	private final Function<T, R> function;

	private final boolean consumes;

	public ConditionalFunction(Predicate<T> predicate, Function<T, R> function, boolean consumes) {
		this.predicate = predicate;
		this.function = function;
		this.consumes = consumes;
	}

	public ConditionalFunction(Predicate<T> predicate, Function<T, R> function) {
		this(predicate, function, false);
	}

	public boolean isSatisfiedBy(T arg) {
		return predicate.test(arg);
	}

	public R apply(T arg) {
		return function.apply(arg);
	}

	public boolean doesConsume() {
		return consumes;
	}

}
