package context.visuals.text;

public class CharacterData implements Comparable<CharacterData> {

	private int asciiCode;
	private int x;
	private int y;
	private int width;
	private int height;
	private int xOffset;
	private int yOffset;
	private int xAdvance;
	private int page;

	public CharacterData(int asciiCode, int x, int y, int width, int height, int xOffset, int yOffset, int xAdvance, int page) {
		if (asciiCode > 255) {
			throw new IllegalArgumentException("Invalid ascii character: " + asciiCode);
		}
		this.asciiCode = asciiCode;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.xAdvance = xAdvance;
		this.page = page;
	}

	public int getAsciiCode() {
		return asciiCode;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getxOffset() {
		return xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}

	public int getxAdvance() {
		return xAdvance;
	}

	public int getPage() {
		return page;
	}

	@Override
	public int compareTo(CharacterData o) {
		return Integer.compare(asciiCode, o.asciiCode);
	}

}
