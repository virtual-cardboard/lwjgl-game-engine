package common.coordinates.buffered;

import common.coordinates.FloatCoordinates;

public class BufferedFloatCoordinates {

	private FloatCoordinates[] coordinates;

	public BufferedFloatCoordinates(int numOfBuffers) {
		coordinates = new FloatCoordinates[numOfBuffers];
		for (int i = 0; i < numOfBuffers; i++) {
			coordinates[i] = new FloatCoordinates(0f, 0f);
		}
	}

	public FloatCoordinates getCoordinates(int bufferIndex) {
		return coordinates[bufferIndex];
	}

}
