package context.visuals.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import context.visuals.lwjgl.Texture;

public class GameFont {

	private String name;
	private int fontSize;
	private int numPages;
	private List<CharacterData> characterDatas;
	private List<Texture> pageTextures;

	public GameFont() {
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

	void setName(String name) {
		this.name = name;
	}

	public int getFontSize() {
		return fontSize;
	}

	void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public int getNumPages() {
		return numPages;
	}

	void setNumPages(int numPages) {
		this.numPages = numPages;
	}

	public List<CharacterData> getCharacterDatas() {
		return characterDatas;
	}

	public List<Texture> getPageTextures() {
		return pageTextures;
	}

}
