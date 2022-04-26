package engine.common.loader.serialization;

import static java.nio.charset.StandardCharsets.UTF_8;

import engine.common.loader.serialization.format.SerializationFormat;

public class SerializationReader {

	private byte[] bytes;
	private int index = 0;

	public SerializationReader(byte[] bytes) {
		this.bytes = bytes;
	}

	public SerializationReader(SerializationFormat format, SerializationReader reader) {
		this.bytes = reader.bytes;
		this.index = reader.index;
	}

	public long readLong() {
		long val = ((long) bytes[index] << 56)
				| ((long) (bytes[index + 1] & 0xFF) << 48)
				| ((long) (bytes[index + 2] & 0xFF) << 40)
				| ((long) (bytes[index + 3] & 0xFF) << 32)
				| ((long) (bytes[index + 4] & 0xFF) << 24)
				| ((bytes[index + 5] & 0xFF) << 16)
				| ((bytes[index + 6] & 0xFF) << 8)
				| (bytes[index + 7] & 0xFF);
		index += 8;
		return val;
	}

	public int readInt() {
		int val = ((bytes[index]) << 24)
				| ((bytes[index + 1] & 0xFF) << 16)
				| ((bytes[index + 2] & 0xFF) << 8)
				| (bytes[index + 3] & 0xFF);
		index += 4;
		return val;
	}

	public short readShort() {
		short val = (short) (((bytes[index] & 0xFF) << 8)
				| (bytes[index + 1] & 0xFF));
		index += 2;
		return val;
	}

	public byte readByte() {
		byte val = bytes[index];
		index += 1;
		return val;
	}

	public boolean readBoolean() {
		byte val = bytes[index];
		index += 1;
		return (val & 1) == 1;
	}

	public String readStringUtf8() {
		int numBytes = (short) (((bytes[index] & 0xFF) << 8) // TODO make this an unsigned short
				| (0xFF & bytes[index + 1] & 0xFF));
		index += 2;
		String val = new String(bytes, index, numBytes, UTF_8);
		index += numBytes;
		return val;
	}

}
