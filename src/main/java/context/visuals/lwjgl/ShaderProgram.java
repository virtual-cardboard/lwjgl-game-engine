package context.visuals.lwjgl;

import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;

import java.util.ArrayList;
import java.util.List;

import common.math.Matrix4f;
import common.math.Vector2f;
import common.math.Vector3f;
import common.math.Vector4f;

public class ShaderProgram extends GLObject {

	private int id;
	private List<Integer> toDelete = new ArrayList<>(3);

	/**
	 * Attach a shader to the shader program. Also tracks it to be deleted when the
	 * shader program is deleted.
	 * 
	 * @param shader the shader
	 */
	public void attachShader(Shader shader) {
		glAttachShader(id, shader.getId());
		toDelete.add(shader.getId());
	}

	/**
	 * Links the shader program to OpenGL. Should only be called once.
	 */
	public void link() {
		glLinkProgram(id);
	}

	/**
	 * Uses the current shader program to handle any glDrawArrays() or
	 * glDrawElements() calls. This is likely to be called multiple times. You must
	 * call {@link #link() link()} before using bind().
	 * 
	 * @see {@link #link() link()}
	 */
	public void bind() {
		glUseProgram(id);
	}

	/**
	 * Unbinds the current shader program.
	 */
	public static void unbind() {
		glUseProgram(0);
	}

	public void delete() {
		glUseProgram(0);
		for (int i = 0; i < toDelete.size(); i++) {
			int shaderID = toDelete.get(i);
			glDetachShader(id, shaderID);
			glDeleteShader(shaderID);
		}
		glDeleteProgram(id);
	}

	public void setBoolean(String uniform, boolean value) {
		glUniform1f(glGetUniformLocation(id, uniform), value ? 1 : 0);
	}

	public void setInt(String uniform, int i) {
		glUniform1i(glGetUniformLocation(id, uniform), i);
	}

	public void setFloat(String uniform, float value) {
		glUniform1f(glGetUniformLocation(id, uniform), value);
	}

	public void setVec2(String uniform, Vector2f vector) {
		glUniform2f(glGetUniformLocation(id, uniform), vector.x, vector.y);
	}

	public void setVec3(String uniform, Vector3f vector) {
		glUniform3f(glGetUniformLocation(id, uniform), vector.x, vector.y, vector.z);
	}

	public void setVec4(String uniform, Vector4f vec4) {
		glUniform4f(glGetUniformLocation(id, uniform), vec4.x, vec4.y, vec4.z, vec4.w);
	}

	private static final float[] FLOAT_BUFFER = new float[16];

	public void setMat4(String uniform, Matrix4f mat4) {
		mat4.store(FLOAT_BUFFER);
		glUniformMatrix4fv(glGetUniformLocation(id, uniform), false, FLOAT_BUFFER);
	}

	public void genId() {
		id = glCreateProgram();
		confirmInitialization();
	}

}
