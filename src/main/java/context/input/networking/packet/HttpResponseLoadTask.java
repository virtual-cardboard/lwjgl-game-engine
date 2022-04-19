package context.input.networking.packet;

import java.io.IOException;

import engine.common.loader.IOLoadTask;

public class HttpResponseLoadTask implements IOLoadTask<byte[]> {
	@Override
	public byte[] loadIO() throws IOException {
		return new byte[0];
	}
}
