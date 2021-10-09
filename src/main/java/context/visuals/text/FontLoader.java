package context.visuals.text;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class FontLoader {

	private static final Charset CHARSET = Charset.forName("UTF-8");

	private GameFont gameFont;
	private int numCharacterData;

	public GameFont readVcFont(File source) throws IOException {
		gameFont = new GameFont();
		try (FileInputStream fis = new FileInputStream(source)) {
			System.out.println("Reading header");
			loadHeaderData(fis);
			System.out.println("Reading characters");
			loadCharacterData(fis);
		}
		return gameFont;
	}

	private void loadHeaderData(FileInputStream fis) throws IOException {
		int nameLength = fis.read();
		byte[] nameBytes = new byte[nameLength];
		for (int i = 0; i < nameBytes.length; i++) {
			nameBytes[i] = (byte) fis.read();
		}
		String name = new String(nameBytes, CHARSET);
		gameFont.setName(name);
		gameFont.setFontSize(readShort(fis));
		gameFont.setNumPages(readShort(fis));
		numCharacterData = readShort(fis);
	}

	private void loadCharacterData(FileInputStream fis) throws IOException {
		List<CharacterData> characterDatas = gameFont.getCharacterDatas();
		for (int i = 0; i < numCharacterData; i++) {
			short character = readShort(fis);
			short x = readShort(fis);
			short y = readShort(fis);
			short width = readShort(fis);
			short height = readShort(fis);
			short xOffset = readShort(fis);
			short yOffset = readShort(fis);
			short xAdvance = readShort(fis);
			short page = (short) fis.read();
			CharacterData c = new CharacterData((char) character, x, y, width, height, xOffset, yOffset, xAdvance, page);
			characterDatas.add(c);
		}
	}

	private short readShort(FileInputStream fis) throws IOException {
		byte b1 = (byte) fis.read();
		byte b2 = (byte) fis.read();
		return convertBytesToShort(b1, b2);
	}

	private short convertBytesToShort(byte b1, byte b2) {
		return (short) ((b1 << 8) | (b2 & 0xFF));
	}

}
