package context.audio.lwjgl;

import static org.lwjgl.openal.AL10.*;

import common.math.Vector3f;

public class AudioSource extends ALObject {

	private int id;

	public void genID() {
		initialize();
		id = alGenSources();
	}

	public void setGain(float gain) {
		verifyInitialized();
		alSourcef(id, AL_GAIN, gain);
	}

	public void setPitch(float pitch) {
		verifyInitialized();
		alSourcef(id, AL_PITCH, pitch);
	}

	public void setPosition(Vector3f pos) {
		verifyInitialized();
		alSource3f(id, AL_POSITION, pos.x, pos.y, pos.z);
	}

	public void setPosition(float x, float y, float z) {
		verifyInitialized();
		alSource3f(id, AL_POSITION, x, y, z);
	}

	public void setVelocity(Vector3f pos) {
		verifyInitialized();
		alSource3f(id, AL_VELOCITY, pos.x, pos.y, pos.z);
	}

	public void setVelocity(float x, float y, float z) {
		verifyInitialized();
		alSource3f(id, AL_VELOCITY, x, y, z);
	}

	public void setAudioBuffer(AudioBuffer audioBuffer) {
		verifyInitialized();
		alSourcei(id, AL_BUFFER, audioBuffer.id());
	}

	public void play() {
		verifyInitialized();
		alSourcePlay(id);
		int error;
		if ((error = alGetError()) != AL_NO_ERROR) {
			System.err.println("Error: " + alGetString(error));
		}
	}

	public void pause() {
		verifyInitialized();
		alSourcePause(id);
	}

	public void delete() {
		verifyInitialized();
		alDeleteSources(id);
	}

}
