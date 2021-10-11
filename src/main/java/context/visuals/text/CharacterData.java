package context.visuals.text;

public final class CharacterData {

	private final char character;
	private final short x;
	private final short y;
	private final short width;
	private final short height;
	private final short xOffset;
	private final short yOffset;
	private final short xAdvance;
	private final short page;

	public CharacterData(char character, short x, short y, short width, short height, short xOffset, short yOffset, short xAdvance, short page) {
		this.character = character;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.xAdvance = xAdvance;
		this.page = page;
	}

	public char getCharacter() {
		return character;
	}

	public short x() {
		return x;
	}

	public short y() {
		return y;
	}

	public short width() {
		return width;
	}

	public short height() {
		return height;
	}

	public short xOffset() {
		return xOffset;
	}

	public short yOffset() {
		return yOffset;
	}

	public short xAdvance() {
		return xAdvance;
	}

	public short getPage() {
		return page;
	}

}
