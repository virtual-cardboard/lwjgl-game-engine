package common.loader.loadtask;

import java.io.File;
import java.io.IOException;

import common.loader.IOLoadTask;
import context.visuals.lwjgl.Texture;
import context.visuals.text.FontLoader;
import context.visuals.text.GameFont;

public class FontLoadTask extends IOLoadTask<GameFont> {

	public static final String VC_FONT = "vcfont";

	private File fontFile;
	private Texture fontTexture;

	public FontLoadTask(File fontFile, Texture fontTexture) {
		this.fontFile = fontFile;
		this.fontTexture = fontTexture;
	}

	@Override
	protected GameFont load() throws IOException {
		return new FontLoader().loadFont(fontFile, fontTexture);
	}

}
