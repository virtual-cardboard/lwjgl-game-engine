package common.coordinates.buffered;

import common.coordinates.VectorCoordinates;

public class BufferedVectorCoordinates {

	private VectorCoordinates[] coordinates;

	public BufferedVectorCoordinates(int numOfBuffers) {
		coordinates = new VectorCoordinates[numOfBuffers];
		for (int i = 0; i < numOfBuffers; i++) {
			coordinates[i] = new VectorCoordinates(0f, 0f);
		}
	}

	public VectorCoordinates getCoordinates(int bufferIndex) {
		return coordinates[bufferIndex];
	}

}
