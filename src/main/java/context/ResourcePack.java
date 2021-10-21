package context;

import java.util.HashMap;
import java.util.Map;

import context.visuals.lwjgl.ShaderProgram;
import context.visuals.lwjgl.Texture;
import context.visuals.lwjgl.VertexArrayObject;
import context.visuals.text.GameFont;

/**
 * A nice leather pack of {@link VertexArrayObject VertexArrayObjects} and
 * {@link ShaderProgram ShaderPrograms}.
 * 
 * @author Jay
 *
 */
public final class ResourcePack {

	private Map<String, VertexArrayObject> vertexArrayObjects = new HashMap<>();
	private Map<String, ShaderProgram> shaderPrograms = new HashMap<>();
	private Map<String, Texture> textures = new HashMap<>();
	private Map<String, GameFont> fonts = new HashMap<>();

	public void init(VertexArrayObject rectangleVAO) {
		vertexArrayObjects.put("rectangle", rectangleVAO);
	}

	public VertexArrayObject getVAO(String name) {
		return vertexArrayObjects.get(name);
	}

	public void putVertexArrayObject(String name, VertexArrayObject vao) {
		vertexArrayObjects.put(name, vao);
	}

	public ShaderProgram getShaderProgram(String name) {
		return shaderPrograms.get(name);
	}

	public void putShaderProgram(String name, ShaderProgram shaderProgram) {
		shaderPrograms.put(name, shaderProgram);
	}

	public Texture getTexture(String name) {
		return textures.get(name);
	}

	public void putTexture(String name, Texture texture) {
		textures.put(name, texture);
	}

	public GameFont getFont(String name) {
		return fonts.get(name);
	}

	public void putFont(String name, GameFont font) {
		fonts.put(name, font);
	}

	public VertexArrayObject rectangleVAO() {
		return vertexArrayObjects.get("rectangle");
	}

	public void terminate() {
		vertexArrayObjects.values().forEach(VertexArrayObject::delete);
		shaderPrograms.values().forEach(ShaderProgram::delete);
		textures.values().forEach(Texture::delete);
		fonts.values().forEach(GameFont::delete);
	}

}
