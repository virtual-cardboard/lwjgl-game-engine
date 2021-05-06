package state.source;

public interface GameSource {

	public default GameSource getSource() {
		return null;
	}

	public String getDescription();

}
