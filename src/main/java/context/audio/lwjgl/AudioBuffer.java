package context.audio.lwjgl;

import static org.lwjgl.openal.AL10.alBufferData;
import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.AL10.alGenBuffers;

import java.nio.ByteBuffer;

public class AudioBuffer extends ALObject {

	private int id;

	public void genID() {
		initialize();
		id = alGenBuffers();
	}

	public void setData(int channels, ByteBuffer data, int sampleRate) {
		verifyInitialized();
		alBufferData(id, channels, data, sampleRate);
	}

	int id() {
		return id;
	}

	public void delete() {
		verifyInitialized();
		alDeleteBuffers(id);
	}

}
