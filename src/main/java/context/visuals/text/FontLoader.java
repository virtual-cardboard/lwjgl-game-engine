package context.visuals.text;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public final class FontLoader {

	private static final Charset CHARSET = Charset.forName("UTF-8");

	private GameFont gameFont;
	private int numCharacters;

	public GameFont readVcFont(File source) throws IOException {
		try (FileInputStream fis = new FileInputStream(source)) {
			System.out.println("Reading header");
			loadHeaderData(fis);
			System.out.println("Reading characters");
			loadCharacterData(gameFont, fis);
		}
		gameFont.makeImmutable();
		return gameFont;
	}

	private void loadHeaderData(FileInputStream fis) throws IOException {
		int nameLength = fis.read();
		byte[] nameBytes = new byte[nameLength];
		for (int i = 0; i < nameBytes.length; i++) {
			nameBytes[i] = (byte) fis.read();
		}
		String name = new String(nameBytes, CHARSET);
		short fontSize = readShort(fis);
		short numPages = readShort(fis);
		numCharacters = readShort(fis);
		gameFont = new GameFont(name, fontSize, numPages);
	}

	private void loadCharacterData(GameFont gameFont, FileInputStream fis) throws IOException {
		List<CharacterData> characters = gameFont.getCharacterDatas();
		for (int i = 0; i < numCharacters; i++) {
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
			characters.add(c);
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
