package common.physics;

import common.coordinates.VectorCoordinates;

/**
 * Something that has 8 directions it can point to and a speed.
 * 
 * @author Jay
 *
 */
public interface HasCardinalDirectionSpeedPositionDimensions extends HasDirectionSpeedPositionDimensions {

	public CardinalCompass getCompass();

	@Override
	public default VectorCoordinates getDirection() {
		return getCompass().getDirection();
	}

	@Override
	public default void setDirection(VectorCoordinates direction) {
		double theta = Math.atan2(direction.y, direction.x);
		double remainder = Math.abs(theta % (Math.PI / 4));
		if (remainder < Math.PI / 8) {
			theta -= remainder;
		}
		// TODO
	}

	public default void north() {
		getCompass().north();
	}

	public default void east() {
		getCompass().east();
	}

	public default void south() {
		getCompass().south();
	}

	public default void west() {
		getCompass().west();
	}

}
