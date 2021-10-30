package context;

import java.util.HashMap;
import java.util.Map;

import context.visuals.builtin.ColourFragmentShader;
import context.visuals.builtin.RectangleVertexArrayObject;
import context.visuals.builtin.TexturedTransformationVertexShader;
import context.visuals.builtin.TransformationVertexShader;
import context.visuals.lwjgl.ElementBufferObject;
import context.visuals.lwjgl.ShaderProgram;
import context.visuals.lwjgl.Texture;
import context.visuals.lwjgl.VertexArrayObject;
import context.visuals.lwjgl.VertexBufferObject;
import context.visuals.text.GameFont;

/**
 * A nice leather pack of {@link VertexArrayObject VertexArrayObjects} and
 * {@link ShaderProgram ShaderPrograms}.
 * 
 * @author Jay
 *
 */
public final class ResourcePack {

	private Map<String, VertexArrayObject> vaos = new HashMap<>();
	private Map<String, VertexBufferObject> vbos = new HashMap<>();
	private Map<String, ElementBufferObject> ebos = new HashMap<>();
	private Map<String, ShaderProgram> shaderPrograms = new HashMap<>();
	private Map<String, Texture> textures = new HashMap<>();
	private Map<String, GameFont> fonts = new HashMap<>();
	private RectangleVertexArrayObject rectangleVAO;
	private TransformationVertexShader transformationVS;
	private TexturedTransformationVertexShader texturedTransformationVS;
	private ColourFragmentShader colourFS;
	private ShaderProgram defaultSP;

	public void init(RectangleVertexArrayObject rectangleVAO, TransformationVertexShader transformationVS,
			TexturedTransformationVertexShader texturedTransformationVS, ColourFragmentShader colourFS, ShaderProgram defaultSP) {
		this.rectangleVAO = rectangleVAO;
		this.transformationVS = transformationVS;
		this.texturedTransformationVS = texturedTransformationVS;
		this.colourFS = colourFS;
		this.defaultSP = defaultSP;
	}

	public VertexArrayObject getVAO(String name) {
		return vaos.get(name);
	}

	public void putVAO(String name, VertexArrayObject vao) {
		vaos.put(name, vao);
	}

	public VertexBufferObject getVBO(String name) {
		return vbos.get(name);
	}

	public void putVBO(String name, VertexBufferObject vbo) {
		vbos.put(name, vbo);
	}

	public ElementBufferObject getEBO(String name) {
		return ebos.get(name);
	}

	public void putEBO(String name, ElementBufferObject ebo) {
		ebos.put(name, ebo);
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

	public RectangleVertexArrayObject rectangleVAO() {
		return rectangleVAO;
	}

	public TransformationVertexShader transformationVertexShader() {
		return transformationVS;
	}

	public TexturedTransformationVertexShader texturedTransformationVertexShader() {
		return texturedTransformationVS;
	}

	public ColourFragmentShader colourFragmentShader() {
		return colourFS;
	}

	public ShaderProgram defaultShaderProgram() {
		return defaultSP;
	}

	public void terminate() {
		vaos.values().forEach(VertexArrayObject::delete);
		shaderPrograms.values().forEach(ShaderProgram::delete);
		textures.values().forEach(Texture::delete);
		fonts.values().forEach(GameFont::delete);
	}

}
