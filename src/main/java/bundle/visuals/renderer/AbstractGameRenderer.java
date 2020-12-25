package bundle.visuals.renderer;

import bundle.visuals.texture.AbstractTexture;
import common.coordinates.PixelCoordinates;

public abstract class AbstractGameRenderer {

	public abstract void drawLine(float x1, float y1, float x2, float y2);

	public abstract void drawRectangle(float x, float y, float width, float height);

	public abstract void drawRectangle(float x, float y, float width, float height, float radius);

	public abstract void drawTexturedRectangle(float x, float y, float width, float height);

	public abstract void drawEllipse(float centerX, float centerY, float width, float height);

	public abstract void drawTexturedEllipse(float centerX, float centerY, float width, float height);

	public void drawCircle(float centerX, float centerY, float radius) {
		drawEllipse(centerX, centerY, radius * 2, radius * 2);
	}

	public void drawImage(AbstractTexture texture, float x, float y) {
		PixelCoordinates defaultDimensions = texture.getDefaultDimensions();
		drawTexture(texture, x, y, defaultDimensions.x, defaultDimensions.y);
	}

	public abstract void drawTexture(AbstractTexture texture, float x, float y, float width, float height);

	public void fill(float grey) {
		fill(grey, grey, grey);
	}

	public abstract void fill(float r, float g, float b);

	public abstract void outlineColour(float r, float g, float b);

	public abstract void clear(float r, float g, float b);

	public abstract void textSize(float size);

	public abstract void text(String text, float x, float y);

}
