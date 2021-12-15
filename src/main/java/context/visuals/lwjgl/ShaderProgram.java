package context.visuals.lwjgl;

import static context.visuals.colour.Colour.normalizedA;
import static context.visuals.colour.Colour.normalizedB;
import static context.visuals.colour.Colour.normalizedG;
import static context.visuals.colour.Colour.normalizedR;
import static org.lwjgl.opengl.GL20.*;

import java.util.ArrayList;
import java.util.List;

import common.math.Matrix4f;
import common.math.Vector2f;
import common.math.Vector3f;
import common.math.Vector4f;
import context.GLContext;

public class ShaderProgram extends GLRegularObject {

	private int id;
	private List<Shader> toAttach = new ArrayList<>(3);
	private List<Integer> toDelete = new ArrayList<>(3);

	public void genId() {
		id = glCreateProgram();
		confirmInitialization();
	}

	/**
	 * Adds a shader that will be attached in the next
	 * {@link ShaderProgram#attachShaders() attachShaders()} call. C
	 * 
	 * @param shader
	 */
	public void addShader(Shader shader) {
		toAttach.add(shader);
	}

	/**
	 * Attaches all shaders added using {@link #addShader(Shader)}. Call this after
	 * {@link #genId()} and before {@link #link()}.
	 */
	public void attachShaders() {
		verifyInitialized();
		for (int i = 0; i < toAttach.size(); i++) {
			int shaderID = toAttach.get(i).id();
			glAttachShader(id, shaderID);
			toDelete.add(shaderID);
		}
	}

	/**
	 * Links the shader program to OpenGL. Should only be called once.
	 */
	public void link() {
		verifyInitialized();
		glLinkProgram(id);
	}

	/**
	 * Uses the current shader program to handle any glDrawArrays() or
	 * glDrawElements() calls. This is likely to be called multiple times. You must
	 * call {@link #link() link()} before using bind().
	 * 
	 * @param glContext TODO
	 * 
	 * @see {@link #link() link()}
	 */
	public void bind(GLContext glContext) {
		verifyInitialized();
		if (glContext.shaderProgramID == id) {
			return;
		}
		glUseProgram(id);
		glContext.shaderProgramID = id;
	}

	/**
	 * Unbinds the current shader program.
	 */
	public static void unbind() {
		glUseProgram(0);
	}

	public void delete() {
		verifyInitialized();
		glUseProgram(0);
		for (int i = 0; i < toDelete.size(); i++) {
			int shaderID = toDelete.get(i);
			glDetachShader(id, shaderID);
			glDeleteShader(shaderID);
		}
		glDeleteProgram(id);
	}

	public void setBoolean(String uniform, boolean value) {
		verifyInitialized();
		glUniform1f(glGetUniformLocation(id, uniform), value ? 1 : 0);
	}

	public void setInt(String uniform, int i) {
		verifyInitialized();
		glUniform1i(glGetUniformLocation(id, uniform), i);
	}

	public void setFloat(String uniform, float value) {
		verifyInitialized();
		glUniform1f(glGetUniformLocation(id, uniform), value);
	}

	public void setVec2(String uniform, Vector2f vec2) {
		verifyInitialized();
		glUniform2f(glGetUniformLocation(id, uniform), vec2.x, vec2.y);
	}

	public void setVec3(String uniform, Vector3f vec3) {
		verifyInitialized();
		glUniform3f(glGetUniformLocation(id, uniform), vec3.x, vec3.y, vec3.z);
	}

	public void setVec4(String uniform, Vector4f vec4) {
		verifyInitialized();
		glUniform4f(glGetUniformLocation(id, uniform), vec4.x, vec4.y, vec4.z, vec4.w);
	}

	public void setColour(String uniform, int colour) {
		setVec4(uniform, new Vector4f(normalizedR(colour), normalizedG(colour), normalizedB(colour), normalizedA(colour)));
	}

	private static final float[] FLOAT_BUFFER = new float[16];

	public void setMat4(String uniform, Matrix4f mat4) {
		verifyInitialized();
		mat4.store(FLOAT_BUFFER);
		glUniformMatrix4fv(glGetUniformLocation(id, uniform), false, FLOAT_BUFFER);
	}

}
