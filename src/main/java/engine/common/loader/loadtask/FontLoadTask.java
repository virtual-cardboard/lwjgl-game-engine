package engine.common.loader.loadtask;

import java.io.File;
import java.io.IOException;

import context.visuals.lwjgl.Texture;
import context.visuals.text.FontLoader;
import context.visuals.text.GameFont;
import engine.common.loader.IOLoadTask;

public class FontLoadTask implements IOLoadTask<GameFont> {

	public static final String VC_FONT = "vcfont";

	private File fontFile;
	private Texture fontTexture;

	public FontLoadTask(File fontFile, Texture fontTexture) {
		this.fontFile = fontFile;
		this.fontTexture = fontTexture;
	}

	@Override
	public GameFont loadIO() throws IOException {
		return new FontLoader().loadFont(fontFile, fontTexture);
	}

}
