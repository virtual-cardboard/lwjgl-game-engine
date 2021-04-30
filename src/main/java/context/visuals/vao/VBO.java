package context.visuals.vao;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryUtil;

// A buffer object stores the data about one aspect for each vertex of a mesh
public class VBO {

	private static List<Integer> vbos = new ArrayList<>();

	protected int vboID;

	// Creates a new buffer object
	public VBO() {
		vboID = GL15.glGenBuffers();
		vbos.add(vboID);
	}

	public void loadData(float[] data) {
		FloatBuffer buffer = convertToFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		MemoryUtil.memFree(buffer); // Freeing up memory after we are done with it
	}

	// Binding the buffer object so opengl knows to read or write to it
	public void bindVBO() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
	}

	// Unbinding the buffer object so opengl knows to not read or write to it
	public void unbindVBO() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	// Methods for transforming the data to a float buffer so we can load the data
	// into opengl later
	private FloatBuffer convertToFloatBuffer(float[] data) {
		FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
		buffer.put(data).flip();
		return buffer;
	}

	// Method for deleting all the buffer objects when program is closing
	// Called in Visual class's cleanUp method
	public static void cleanUp() {
		for (int vbo : vbos) {
			GL15.glDeleteBuffers(vbo);
		}
	}

}
