package common.coordinates;

import common.physics.HasPositionDimensions;

public class RelativeCoordinates extends AbstractCoordinates {

	private HasPositionDimensions parent;
	private boolean preserveRatio;

	public RelativeCoordinates(float x, float y, HasPositionDimensions parent, boolean preserveRatio) {
		super(x, y);
		this.parent = parent;
		this.setPreserveRatio(preserveRatio);
	}

	public HasPositionDimensions getParent() {
		return parent;
	}

	public void setParent(HasPositionDimensions parent) {
		this.parent = parent;
	}

	public boolean isPreserveRatio() {
		return preserveRatio;
	}

	public void setPreserveRatio(boolean preserveRatio) {
		this.preserveRatio = preserveRatio;
	}

	public PixelCoordinates toPixelCoordinates() {
		PixelCoordinates parentDimensions = parent.getDimensions();
		PixelCoordinates parentPosition = parent.getPosition();
		return new PixelCoordinates(x * parentDimensions.x + parentPosition.x,
				y * parentDimensions.y + parentPosition.y);
	}

}
