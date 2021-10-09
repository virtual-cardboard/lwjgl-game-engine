package context.visuals.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import context.visuals.lwjgl.Texture;

public final class GameFont {

	private final String name;
	private final int fontSize;
	private final int numPages;
	private List<CharacterData> characterDatas;
	private List<Texture> pageTextures;

	public GameFont(String name, int fontSize, int numPages) {
		this.name = name;
		this.fontSize = fontSize;
		this.numPages = numPages;
		characterDatas = new ArrayList<>();
		pageTextures = new ArrayList<>();
	}

	void makeImmutable() {
		this.characterDatas = Collections.unmodifiableList(characterDatas);
		this.pageTextures = Collections.unmodifiableList(pageTextures);
	}

	public String getName() {
		return name;
	}

	public int getFontSize() {
		return fontSize;
	}

	public int getNumPages() {
		return numPages;
	}

	public List<CharacterData> getCharacterDatas() {
		return characterDatas;
	}

	public List<Texture> getPageTextures() {
		return pageTextures;
	}

}
