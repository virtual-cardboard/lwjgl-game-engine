package context;

import java.util.HashMap;
import java.util.Map;

import context.audio.lwjgl.AudioClip;
import context.visuals.builtin.ColourFragmentShader;
import context.visuals.builtin.RectangleVertexArrayObject;
import context.visuals.builtin.TexturedTransformationVertexShader;
import context.visuals.builtin.TransformationVertexShader;
import context.visuals.lwjgl.FrameBufferObject;
import context.visuals.lwjgl.GLObject;
import context.visuals.lwjgl.ScreenFrameBufferObject;
import context.visuals.lwjgl.ShaderProgram;
import context.visuals.lwjgl.Texture;
import context.visuals.lwjgl.VertexArrayObject;
import context.visuals.renderer.GameRenderer;
import context.visuals.text.GameFont;

/**
 * A nice leather pack of {@link VertexArrayObject VertexArrayObjects},
 * {@link ShaderProgram ShaderPrograms}, and other objects that are likely to be
 * used many times in many different contexts.
 * 
 * @author Jay
 *
 */
public final class ResourcePack {

	private Map<String, VertexArrayObject> vaos = new HashMap<>(5);
	private Map<String, FrameBufferObject> fbos = new HashMap<>(2);
	private Map<String, ShaderProgram> shaderPrograms = new HashMap<>(8);
	private Map<String, GameRenderer> renderers = new HashMap<>(8);
	private Map<String, Texture> textures = new HashMap<>(16);
	private Map<String, GameFont> fonts = new HashMap<>(1);
	private Map<String, AudioClip> audioClips = new HashMap<>(0);
	private RectangleVertexArrayObject rectangleVAO;
	private TransformationVertexShader transformationVS;
	private TexturedTransformationVertexShader texturedTransformationVS;
	private ColourFragmentShader colourFS;
	private ShaderProgram defaultSP;
	private ScreenFrameBufferObject screenFBO;

	private GLContext glContext;

	public ResourcePack(GLContext glContext) {
		this.glContext = glContext;
	}

	public void init(RectangleVertexArrayObject rectangleVAO, TransformationVertexShader transformationVS,
			TexturedTransformationVertexShader texturedTransformationVS, ColourFragmentShader colourFS, ShaderProgram defaultSP,
			ScreenFrameBufferObject screenFBO) {
		this.rectangleVAO = rectangleVAO;
		this.transformationVS = transformationVS;
		this.texturedTransformationVS = texturedTransformationVS;
		this.colourFS = colourFS;
		this.defaultSP = defaultSP;
		this.screenFBO = screenFBO;
	}

	public VertexArrayObject getVAO(String name) {
		return vaos.get(name);
	}

	public void putVAO(String name, VertexArrayObject vao) {
		vaos.put(name, vao);
	}

	public FrameBufferObject getFBO(String name) {
		return fbos.get(name);
	}

	public void putFBO(String name, FrameBufferObject fbo) {
		fbos.put(name, fbo);
	}

	public ShaderProgram getShaderProgram(String name) {
		return shaderPrograms.get(name);
	}

	public <T extends ShaderProgram> T getShaderProgram(String name, Class<T> clazz) {
		return clazz.cast(shaderPrograms.get(name));
	}

	public void putShaderProgram(String name, ShaderProgram shaderProgram) {
		shaderPrograms.put(name, shaderProgram);
	}

	public <T extends GameRenderer> T getRenderer(String name, Class<T> clazz) {
		return clazz.cast(renderers.get(name));
	}

	public void putRenderer(String name, GameRenderer renderer) {
		renderer.setGLContext(glContext);
		renderers.put(name, renderer);
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

	public AudioClip getAudioClip(String name) {
		return audioClips.get(name);
	}

	public void putAudioClip(String name, AudioClip audioClip) {
		audioClips.put(name, audioClip);
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

	public ScreenFrameBufferObject screenFrameBufferObject() {
		return screenFBO;
	}

	public void terminate() {
		vaos.values().forEach(VertexArrayObject::delete);
		shaderPrograms.values().forEach(ShaderProgram::delete);
		textures.values().forEach(Texture::delete);
		fonts.values().forEach(GameFont::delete);
		fbos.values().forEach(FrameBufferObject::delete);
		audioClips.values().forEach(AudioClip::delete);
		rectangleVAO.delete();
		transformationVS.delete();
		texturedTransformationVS.delete();
		colourFS.delete();
		defaultSP.delete();
	}

	public void put(String name, GLObject object) {
		object.putInto(name, this);
	}

}
