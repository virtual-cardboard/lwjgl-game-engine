package common.coordinates;

public class BufferedCoordinates {

	private AbstractCoordinates[] coordinates;

	public BufferedCoordinates(int numOfBuffers) {
		coordinates = new Vector2f[numOfBuffers];
		for (int i = 0; i < numOfBuffers; i++) {
			coordinates[i] = new Vector2f(0, 0);
		}
	}

	public AbstractCoordinates getCoordinates(int bufferIndex) {
		return coordinates[bufferIndex];
	}

}
