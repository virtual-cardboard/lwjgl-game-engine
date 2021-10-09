package context.visuals.text;

public class CharacterData implements Comparable<CharacterData> {

	private char character;
	private short x;
	private short y;
	private short width;
	private short height;
	private short xOffset;
	private short yOffset;
	private short xAdvance;
	private short page;

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

	public short getX() {
		return x;
	}

	public short getY() {
		return y;
	}

	public short getWidth() {
		return width;
	}

	public short getHeight() {
		return height;
	}

	public short getxOffset() {
		return xOffset;
	}

	public short getyOffset() {
		return yOffset;
	}

	public short getxAdvance() {
		return xAdvance;
	}

	public short getPage() {
		return page;
	}

	@Override
	public int compareTo(CharacterData o) {
		return Integer.compare(character, o.character);
	}

}
