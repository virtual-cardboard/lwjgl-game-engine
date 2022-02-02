package common.timestep;

import static org.lwjgl.openal.AL.createCapabilities;
import static org.lwjgl.openal.ALC.createCapabilities;
import static org.lwjgl.openal.ALC10.*;

import java.util.concurrent.CountDownLatch;

import context.GameContextWrapper;
import context.audio.GameAudio;

public class AudioUpdater extends TimestepTimer {

	private long alContext;
	private long device;

	private GameContextWrapper wrapper;

	private boolean isDone = false;
	private CountDownLatch contextCountDownLatch;

	public AudioUpdater(CountDownLatch contextCountDownLatch) {
		super(30);
		this.contextCountDownLatch = contextCountDownLatch;
	}

	@Override
	protected void update() {
		GameAudio audio = wrapper.context().audio();
		audio.update();
	}

	@Override
	protected void startActions() {
		String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
		device = alcOpenDevice(defaultDeviceName);

		// Create context
		int[] attributes = { 0 };
		alContext = alcCreateContext(device, attributes);
		alcMakeContextCurrent(alContext);

		// Create capabilities
		createCapabilities(createCapabilities(device));
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
		alcDestroyContext(alContext);
		alcCloseDevice(device);
	}

	public void end() {
		isDone = true;
	}

	public void setWrapper(GameContextWrapper wrapper) {
		this.wrapper = wrapper;
	}

}
