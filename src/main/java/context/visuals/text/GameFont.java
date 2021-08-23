package context.visuals.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameFont {

	private String name;
	private int fontSize;
	private int numPages;
	private List<CharacterData> characterDatas;
	private List<KerningData> kerningDatas;

	public GameFont() {
		characterDatas = new ArrayList<>();
		kerningDatas = new ArrayList<>();
	}

	void makeImmutable() {
		this.characterDatas = Collections.unmodifiableList(characterDatas);
		this.kerningDatas = Collections.unmodifiableList(kerningDatas);
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

	public List<KerningData> getKerningDatas() {
		return kerningDatas;
	}

}
