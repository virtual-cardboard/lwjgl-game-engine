package context.visuals.text;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import context.visuals.lwjgl.Texture;

public final class FontLoader {

	private static final Charset CHARSET = Charset.forName("UTF-8");

	private GameFont gameFont;
	private int numCharacters;

	public GameFont loadFont(File source, Texture texture) throws IOException {
		try (FileInputStream fis = new FileInputStream(source)) {
			loadHeaderData(fis, texture);
			loadCharacterData(gameFont, fis);
		}
		return gameFont;
	}

	private void loadHeaderData(FileInputStream fis, Texture texture) throws IOException {
		int nameLength = fis.read();
		byte[] nameBytes = new byte[nameLength];
		for (int i = 0; i < nameBytes.length; i++) {
			nameBytes[i] = (byte) fis.read();
		}
		String name = new String(nameBytes, CHARSET);
		short fontSize = readShort(fis);
		readShort(fis); // The number of pages; Should be 1
		numCharacters = readShort(fis);
		readShort(fis); // The number of kernings; doesn't matter for now
//		System.out.println("Num characters = " + numCharacters);
		gameFont = new GameFont(name, fontSize, texture);
	}

	private void loadCharacterData(GameFont gameFont, FileInputStream fis) throws IOException {
		CharacterData[] characters = gameFont.getCharacterDatas();
		for (int i = 0; i < numCharacters; i++) {
			short c = readShort(fis);
			short x = readShort(fis);
			short y = readShort(fis);
			short width = readShort(fis);
			short height = readShort(fis);
			short xOffset = readShort(fis);
			short yOffset = readShort(fis);
			short xAdvance = readShort(fis);
			short page = (short) fis.read();
			CharacterData charData = new CharacterData((char) c, x, y, width, height, xOffset, yOffset, xAdvance, page);
//			System.out.println(c + " " + x + " " + y + " " + width + " " + height + " " + xOffset + " " + yOffset + " " + xAdvance + " " + page);
			characters[c] = charData;
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
