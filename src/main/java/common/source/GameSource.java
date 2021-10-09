package common.source;

public interface GameSource {

	public default GameSource source() {
		return null;
	}

	public default String getDescription() {
		return toString();
	}

}
