package common.coordinates.buffered;

import common.coordinates.IntCoordinates;

public class BufferedIntCoordinates {

	private IntCoordinates[] coordinates;

	public BufferedIntCoordinates(int numOfBuffers) {
		coordinates = new IntCoordinates[numOfBuffers];
		for (int i = 0; i < numOfBuffers; i++) {
			coordinates[i] = new IntCoordinates(0, 0);
		}
	}

	public IntCoordinates getCoordinates(int bufferIndex) {
		return coordinates[bufferIndex];
	}

}
