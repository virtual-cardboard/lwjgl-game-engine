package context.visuals.renderer.shader;

import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryUtil;

import common.math.Matrix4f;
import common.math.Vector2f;
import common.math.Vector3f;
import common.math.Vector4f;

public class ShaderProgram {

	private int id;
	private boolean linked;
	private Queue<Shader> toAttach = new ArrayDeque<>();
	private List<Shader> toDelete = new ArrayList<>();

	/**
	 * Attach a shader to the shader program. Also tracks it to be deleted when the
	 * shader program is deleted.
	 * 
	 * @param shader the shader
	 */
	public void attachShader(Shader shader) {
		attachShader(shader, true);
	}

	/**
	 * Use this method over {@link #attachShader(Shader) attachShader} when a shader
	 * should not be deleted upon deletion of the shader program.
	 * <p>
	 * Some use cases of this functionality can be found here: <a href="
	 * https://stackoverflow.com/questions/9168252/attaching-multiple-shaders-of-the-same-type-in-a-single-opengl-program">
	 * Attaching multiple shaders of the same type in a single OpenGL program? </a>
	 * 
	 * 
	 * @param shader   the shader
	 * @param doDelete set this to false if the shader should not be deleted upon
	 *                 deletion of the shader program.
	 */
	public void attachShader(Shader shader, boolean doDelete) {
		toAttach.add(shader);
		if (doDelete) {
			toDelete.add(shader);
		}
	}

	/**
	 * Links the shader program to OpenGL. Should only be called once.
	 */
	public void link() {
		int numShaders = toAttach.size();
		for (int i = 0; i < numShaders; i++) {
			glAttachShader(id, toAttach.poll().getId());
		}
		glLinkProgram(id);
		linked = true;
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

	public static void unbind() {
		glUseProgram(0);
	}

	public void delete() {
		unbind();
		for (Shader shader : toDelete) {
			glDetachShader(id, shader.getId());
			shader.delete();
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

	public void setMat4(String uniform, Matrix4f mat4) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		mat4.store(buffer);
		buffer.flip();
		glUniformMatrix4fv(glGetUniformLocation(id, uniform), false, buffer);
		MemoryUtil.memFree(buffer);
	}

	public void generateId() {
		id = glCreateProgram();
	}

	public boolean isLinked() {
		return linked;
	}

}
