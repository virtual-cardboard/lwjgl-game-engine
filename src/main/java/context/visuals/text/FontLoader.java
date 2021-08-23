package context.visuals.text;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class FontLoader {

	private static final Charset CHARSET = Charset.forName("UTF-8");

	public GameFont readVcFont(File source) throws IOException {
		GameFont gameFont = new GameFont();
		try (FileInputStream fis = new FileInputStream(source)) {
			loadHeaderData(fis, gameFont);
		}
		return gameFont;
	}

	private void loadHeaderData(FileInputStream fis, GameFont gameFont) throws IOException {
		int nameLength = fis.read();
		byte[] nameBytes = fis.readNBytes(nameLength);
		String name = new String(nameBytes, CHARSET);
		gameFont.setName(name);
	}

	private int read2ByteInt(FileInputStream fis) throws IOException {
		return (fis.read() << 8) + fis.read();
	}

	public static void main(String[] args) throws IOException {
		GameFont vcFont = new FontLoader()
				.readVcFont(new File("C:\\Users\\Jay\\Documents\\GitHub\\virtual-cardboard-lwjgl-game-engine\\src\\main\\resources\\fonts\\langar.vcfont"));
		System.out.println(vcFont);
	}

}
