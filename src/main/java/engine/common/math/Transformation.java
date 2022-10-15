package engine.common.math;

public abstract class Transformation {

	/**
	 * This is the actual {@link Matrix4f} object that contains all the transformational data. The calculateMatrix()
	 * method should update the value of this variable.
	 */
	protected Matrix4f matrix;

	/**
	 * A flag to signify whether the matrix needs to be recalculated.
	 */
	private boolean isDirty = true;

	/**
	 * Carries out the actual calculations of the matrix.
	 */
	protected abstract void calculateMatrix();

	/**
	 * Raise the isDirty flag. Whenever a subclass modifies any variables that are used in the calculateMatrix() method
	 * it should call the raiseFlag() method as well so that the instance knows it has to recalculate the matrix before
	 * it is retrieved by the getter.
	 */
	public final void raiseFlag() {
		isDirty = true;
	}

	/**
	 * Using the lazy initialization of the matrix with the isDirty flag. If it is dirty then it will recalculate the
	 * matrix using the currently set variables, then return it. If it is not dirty then it simply returns the original
	 * matrix.
	 *
	 * @return the matrix
	 */
	public final Matrix4f getMatrix() {
		if (isDirty) {
			calculateMatrix();
			isDirty = false;
		}
		return new Matrix4f(matrix);
	}

}
