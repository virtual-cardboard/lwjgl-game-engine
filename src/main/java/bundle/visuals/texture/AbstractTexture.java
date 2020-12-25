package bundle.visuals.texture;

import common.coordinates.PixelCoordinates;

public abstract class AbstractTexture {

	private PixelCoordinates defaultDimensions;

	public PixelCoordinates getDefaultDimensions() {
		return defaultDimensions;
	}

	public void setDefaultDimensions(PixelCoordinates defaultDimensions) {
		this.defaultDimensions = defaultDimensions;
	}

}
