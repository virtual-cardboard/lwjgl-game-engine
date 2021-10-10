package context.visuals.text;

import context.visuals.lwjgl.Texture;

public final class GameFont {

	private final String name;
	private final int fontSize;
	private CharacterData[] characterDatas;
	private Texture texture;

	public GameFont(String name, int fontSize, Texture texture) {
		this.name = name;
		this.fontSize = fontSize;
		characterDatas = new CharacterData[128];
		this.texture = texture;
	}

	public String getName() {
		return name;
	}

	public int getFontSize() {
		return fontSize;
	}

	public CharacterData[] getCharacterDatas() {
		return characterDatas;
	}

	public Texture texture() {
		return texture;
	}

}
