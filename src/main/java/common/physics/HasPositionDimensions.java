package common.physics;

import common.coordinates.PixelCoordinates;

public interface HasPositionDimensions extends HasPosition {

	public PixelCoordinates getDimensions();

	public void setDimensions(PixelCoordinates dimensions);

}
