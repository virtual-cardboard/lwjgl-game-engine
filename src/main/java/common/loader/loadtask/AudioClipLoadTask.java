package common.loader.loadtask;

import static org.lwjgl.openal.AL10.AL_FORMAT_MONO16;
import static org.lwjgl.openal.AL10.AL_FORMAT_STEREO16;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;
import static org.lwjgl.system.MemoryStack.stackMallocInt;
import static org.lwjgl.system.MemoryStack.stackPop;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.libc.LibCStdlib.free;

import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import common.loader.IOLoadTask;
import context.audio.lwjgl.AudioClip;

public class AudioClipLoadTask implements IOLoadTask<AudioClip> {

	private String path;

	public AudioClipLoadTask(String path) {
		this.path = path;
	}

	@Override
	public AudioClip loadIO() throws IOException {
		stackPush();
		IntBuffer channelsBuffer = stackMallocInt(1);
		stackPush();
		IntBuffer sampleRateBuffer = stackMallocInt(1);

		// Make slashes all go the same way
		path = new File(path).toString();
		ShortBuffer rawAudioBuffer = stb_vorbis_decode_filename(path, channelsBuffer, sampleRateBuffer);
		if (rawAudioBuffer == null) {
			String reason;
			if (path.contains("//") || path.contains("\\\\")) {
				reason = "the path had a double slash.";
			} else if (!new File(path).exists()) {
				reason = "the file did not exist.";
			} else if (!path.endsWith(".ogg")) {
				reason = "the file was not a .ogg file.";
			} else {
				reason = "of an unkown reason.";
			}
			throw new RuntimeException("Failed to load audio buffer at " + path + " because " + reason);
		}

		// Retreive the extra information that was stored in the buffers by the function
		int channels = channelsBuffer.get();
		int sampleRate = sampleRateBuffer.get();
		// Free the space we allocated earlier
		stackPop();
		stackPop();

		// Find the correct OpenAL format
		int format = -1;
		if (channels == 1) {
			format = AL_FORMAT_MONO16;
		} else if (channels == 2) {
			format = AL_FORMAT_STEREO16;
		}

		AudioClip buffer = new AudioClip();
		buffer.genID();
		buffer.setData(format, rawAudioBuffer, sampleRate);

		// Free the memory allocated by STB
		free(rawAudioBuffer);
		return buffer;
	}

}
