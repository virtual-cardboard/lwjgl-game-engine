package common.loader.loadtask;

import static java.io.File.pathSeparator;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.concurrent.CountDownLatch;

import org.lwjgl.system.MemoryStack;

import common.loader.GLLoadTask;
import context.visuals.lwjgl.Texture;
import context.visuals.text.FontLoader;
import context.visuals.text.GameFont;

public final class FontLoadTask extends GLLoadTask {

	private static final String PATH = FontLoadTask.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	public static final String VC_FONT = "vcfont";

	private String fontName;
	private Texture texture;
	private ByteBuffer textureData;

	private GameFont font;

	public FontLoadTask(String fontName) {
		this.fontName = fontName;
	}

	public FontLoadTask(CountDownLatch countDownLatch, String fontName) {
		super(countDownLatch);
		this.fontName = fontName;
	}

	@Override
	public void loadIO() throws IOException {
		String pngName = fontName + ".png";
		String vcfontName = fontName + VC_FONT;
		File textureFile = new File(pngName);
		File vcfontFile = new File(vcfontName);
		if (!textureFile.exists()) {
			textureFile = new File(PATH + "fonts" + pathSeparator + pngName);
			vcfontFile = new File(PATH + "fonts" + pathSeparator + vcfontName);
		}
		textureLoadIO(textureFile.getPath());
		font = new FontLoader().loadFont(vcfontFile, texture);
	}

	@Override
	public void loadGL() {
		texture.setId(glGenTextures());
		texture.bind();
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, texture.getWidth(), texture.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, textureData);
		glGenerateMipmap(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		stbi_image_free(textureData);
		texture.link();
	}

	/**
	 * Normal texture loading boilerplate code
	 * 
	 * @param texturePath the path of the texture to load
	 */
	private void textureLoadIO(String texturePath) {
		texture = new Texture(31); // Use 31st texture unit
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer comp = stack.mallocInt(1);
			textureData = stbi_load(texturePath, w, h, comp, 4);
			if (textureData == null) {
				System.err.println("Failed to load texture at " + texturePath);
				throw new RuntimeException(stbi_failure_reason());
			}
			texture.setWidth(w.get());
			texture.setHeight(h.get());
		}
	}

	public GameFont getFont() {
		return font;
	}

}
