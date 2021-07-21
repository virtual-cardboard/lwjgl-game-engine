package common.loader.linktask;

import static org.lwjgl.opengl.GL11.glGenTextures;

import context.visuals.lwjgl.Texture;

public class GenerateTextureLinkTask extends LinkTask {

	private Texture texture;
	private String imagePath;

	public GenerateTextureLinkTask(Texture texture, String imagePath) {
		this.texture = texture;
		this.imagePath = imagePath;
	}

	@Override
	public void doRun() {
		texture.setId(glGenTextures());
		texture.loadImage(imagePath);
	}

}
