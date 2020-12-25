package common.physics;

import common.coordinates.Vector2f;

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
	public Vector2f getDirection();

	public void setDirection(Vector2f direction);

	public default Vector2f getVelocity() {
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
