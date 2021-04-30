package common.physics;

import common.coordinates.VectorCoordinates;

/**
 * Something that has a direction and speed.
 * 
 * @author Jay
 *
 */
public interface HasDirectionSpeed {

	/**
	 * Getter for the direction. Preserves the invariant that the direction is
	 * always normalized.
	 * 
	 * @return the direction
	 */
	public VectorCoordinates getDirection();

	public void setDirection(VectorCoordinates direction);

	public default VectorCoordinates getVelocity() {
		return getDirection().copy().scale(getSpeed());
	}

	/**
	 * Getter for the speed. Preserves the invariant that the speed is never
	 * negative.
	 * 
	 * @return the speed
	 */
	public float getSpeed();

	public void setSpeed(float speed);

}
