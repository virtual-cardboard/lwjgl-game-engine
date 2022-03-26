package common.timestep;

import static org.lwjgl.openal.AL.createCapabilities;
import static org.lwjgl.openal.AL10.AL_NO_ERROR;
import static org.lwjgl.openal.AL10.alGetError;
import static org.lwjgl.openal.AL10.alGetString;
import static org.lwjgl.openal.ALC.createCapabilities;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.util.concurrent.CountDownLatch;

import org.lwjgl.openal.ALCCapabilities;

import context.GameContextWrapper;
import context.audio.GameAudio;

public class AudioUpdater extends TimestepTimer {

	private long alContext;
	private long device;

	private GameContextWrapper wrapper;

	private boolean isDone = false;
	private CountDownLatch audioCountDownLatch;
	private CountDownLatch contextCountDownLatch;

	public AudioUpdater(CountDownLatch audioCountDownLatch, CountDownLatch contextCountDownLatch) {
		super(30);
		this.audioCountDownLatch = audioCountDownLatch;
		this.contextCountDownLatch = contextCountDownLatch;
	}

	@Override
	protected void update() {
		GameAudio audio = wrapper.context().audio();
		audio.doUpdate();
		printAnyErrors();
	}

	@Override
	protected void startActions() {
		String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
		device = alcOpenDevice(defaultDeviceName);
		if (device == NULL) {
			throw new RuntimeException("No audio driver/device found.");
		}

		// Create context
		int[] attributes = { 0 };
		alContext = alcCreateContext(device, attributes);
		if (alContext == NULL) {
			throw new RuntimeException("Could not create AL context.");
		}
		alcMakeContextCurrent(alContext);

		// Create capabilities
		ALCCapabilities capabilities = createCapabilities(device);
		if (capabilities == null) {
			throw new RuntimeException("Could not create AL capabilities");
		}
		createCapabilities(capabilities);
		printAnyErrors();
		audioCountDownLatch.countDown();
		try {
			contextCountDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected boolean endCondition() {
		return isDone;
	}

	@Override
	protected void endActions() {
		printAnyErrors();
		alcDestroyContext(alContext);
		alcCloseDevice(device);
	}

	private void printAnyErrors() {
		int error;
		while ((error = alGetError()) != AL_NO_ERROR) {
			System.err.println(alGetString(error));
		}
	}

	public void end() {
		isDone = true;
	}

	public void setWrapper(GameContextWrapper wrapper) {
		this.wrapper = wrapper;
	}

}
