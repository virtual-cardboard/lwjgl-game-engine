package context.audio.lwjgl;

import static org.lwjgl.openal.AL10.alBufferData;
import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.AL10.alGenBuffers;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

public class AudioBuffer extends ALObject {

	private int id;

	public void genID() {
		initialize();
		id = alGenBuffers();
	}

	public void setData(int format, ByteBuffer data, int sampleRate) {
		verifyInitialized();
		alBufferData(id, format, data, sampleRate);
	}

	public void setData(int format, ShortBuffer data, int sampleRate) {
		verifyInitialized();
		alBufferData(id, format, data, sampleRate);
	}

	int id() {
		return id;
	}

	public void delete() {
		verifyInitialized();
		alDeleteBuffers(id);
	}

}
