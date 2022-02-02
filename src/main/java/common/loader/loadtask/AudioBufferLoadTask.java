package common.loader.loadtask;

import static java.nio.ByteOrder.BIG_ENDIAN;
import static java.nio.ByteOrder.LITTLE_ENDIAN;
import static org.lwjgl.openal.AL10.AL_FORMAT_MONO16;
import static org.lwjgl.openal.AL10.AL_FORMAT_MONO8;
import static org.lwjgl.openal.AL10.AL_FORMAT_STEREO16;
import static org.lwjgl.openal.AL10.AL_FORMAT_STEREO8;
import static org.lwjgl.system.libc.LibCStdlib.free;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import common.loader.IOLoadTask;
import context.audio.lwjgl.AudioBuffer;

public class AudioBufferLoadTask extends IOLoadTask<AudioBuffer> {

	private File file;

	public AudioBufferLoadTask(String path) {
		file = getFile(path);
	}

	protected File getFile(String path) {
		// First check absolute path
		File file = new File(path);
		if (!file.exists()) {
			String shaderPath = AudioBuffer.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			// Then check relative path for built-in shaders
			file = new File(shaderPath + path);
		}
		return file;
	}

	@Override
	protected AudioBuffer load() throws IOException {
		// Load audio from an AudioInputStream into a ByteBuffer

		AudioInputStream ais;
		try {
			ais = AudioSystem.getAudioInputStream(file);
		} catch (UnsupportedAudioFileException | IOException e) {
			throw new RuntimeException(e);
		}
		// get format of data
		AudioFormat audioformat = ais.getFormat();

		// get channels
		int channels = 0;
		if (audioformat.getChannels() == 1) {
			if (audioformat.getSampleSizeInBits() == 8) {
				channels = AL_FORMAT_MONO8;
			} else if (audioformat.getSampleSizeInBits() == 16) {
				channels = AL_FORMAT_MONO16;
			} else {
				assert false : "Illegal sample size";
			}
		} else if (audioformat.getChannels() == 2) {
			if (audioformat.getSampleSizeInBits() == 8) {
				channels = AL_FORMAT_STEREO8;
			} else if (audioformat.getSampleSizeInBits() == 16) {
				channels = AL_FORMAT_STEREO16;
			} else {
				assert false : "Illegal sample size";
			}
		} else {
			assert false : "Only mono or stereo is supported";
		}

		// read data into buffer
		ByteBuffer buffer = null;
		try {
			int available = ais.available();
			if (available <= 0) {
				available = ais.getFormat().getChannels() * (int) ais.getFrameLength() * ais.getFormat().getSampleSizeInBits() / 8;
			}
			byte[] buf = new byte[ais.available()];
			int read = 0, total = 0;
			while ((read = ais.read(buf, total, buf.length - total)) != -1
					&& total < buf.length) {
				total += read;
			}
			buffer = convertAudioBytes(buf, audioformat.getSampleSizeInBits() == 16,
					audioformat.isBigEndian() ? BIG_ENDIAN : LITTLE_ENDIAN);
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		// Load buffer into OpenAL
		AudioBuffer audioBuffer = new AudioBuffer();
		audioBuffer.genID();
		audioBuffer.setData(channels, buffer, (int) audioformat.getSampleRate());

		free(buffer);

		return audioBuffer;
	}

	private static ByteBuffer convertAudioBytes(byte[] audio_bytes, boolean two_bytes_data, ByteOrder order) {
		ByteBuffer dest = ByteBuffer.allocateDirect(audio_bytes.length);
		dest.order(ByteOrder.nativeOrder());
		ByteBuffer src = ByteBuffer.wrap(audio_bytes);
		src.order(order);
		if (two_bytes_data) {
			ShortBuffer dest_short = dest.asShortBuffer();
			ShortBuffer src_short = src.asShortBuffer();
			while (src_short.hasRemaining())
				dest_short.put(src_short.get());
		} else {
			while (src.hasRemaining())
				dest.put(src.get());
		}
		dest.rewind();
		return dest;
	}

}
