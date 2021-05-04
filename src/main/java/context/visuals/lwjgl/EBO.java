package context.visuals.lwjgl;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryUtil;

public class EBO {

	private static List<Integer> ebos = new ArrayList<>();

	protected int eboID;

	// Creates a new buffer object
	public EBO() {
		eboID = GL15.glGenBuffers();
		ebos.add(eboID);
	}

	public void loadData(List<Integer> data) {
		int numberOfElements = data.size();
		int[] arrayData = new int[numberOfElements];
		for (int i = 0; i < numberOfElements; i++) {
			arrayData[i] = data.get(i);
		}
		loadData(arrayData);
	}

	protected void loadData(int[] data) {
		IntBuffer buffer = convertToIntBuffer(data);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		MemoryUtil.memFree(buffer);
	}

	// Methods for transforming the data to an int buffer so we can load the data
	// into opengl later
	private IntBuffer convertToIntBuffer(int[] data) {
		IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
		buffer.put(data).flip();
		return buffer;
	}

	// Binding the buffer object so opengl knows to read or write to it
	public void bindEBO() {
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, eboID);
	}

	// Method for deleting all the buffer objects when program is closing
	// Called in Visual class's cleanUp method
	public static void cleanUp() {
		for (int ebo : ebos) {
			GL15.glDeleteBuffers(ebo);
		}
	}

}
