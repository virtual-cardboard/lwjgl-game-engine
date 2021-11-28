package context.input.networking.packet;

import static context.input.networking.packet.PacketPrimitive.BYTE;
import static context.input.networking.packet.PacketPrimitive.BYTE_ARRAY;
import static context.input.networking.packet.PacketPrimitive.INT;
import static context.input.networking.packet.PacketPrimitive.INT_ARRAY;
import static context.input.networking.packet.PacketPrimitive.IP_V4;
import static context.input.networking.packet.PacketPrimitive.IP_V6;
import static context.input.networking.packet.PacketPrimitive.LONG;
import static context.input.networking.packet.PacketPrimitive.LONG_ARRAY;
import static context.input.networking.packet.PacketPrimitive.SHORT;
import static context.input.networking.packet.PacketPrimitive.SHORT_ARRAY;
import static context.input.networking.packet.PacketPrimitive.STRING;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.InputMismatchException;
import java.util.Queue;

public class PacketReader {

	private byte[] bytes;
	private Queue<PacketPrimitive> primitives;
	private int index = 0;

	public PacketReader(PacketFormat format, PacketModel model) {
		this.primitives = format.primitives();
		// TODO
		// apply encryption inverses
		this.bytes = model.bytes();
	}

	public PacketReader(PacketFormat format, PacketReader reader) {
		this.primitives = format.primitives();
		// encryption here too?
		this.bytes = reader.bytes;
		this.index = reader.index;
	}

	private void typeValidate(PacketPrimitive actual) {
		PacketPrimitive expected = primitives.poll();
		if (expected != actual) {
			throw new InputMismatchException("Packet block creation failed: expected " + expected + ", actual " + actual);
		}
	}

	public long readLong() {
		typeValidate(LONG);
		long val = ((long) bytes[index] << 56) | ((long) (bytes[index + 1] & 0xFF) << 48) |
				((long) (bytes[index + 2] & 0xFF) << 40) | ((long) (bytes[index + 3] & 0xFF) << 32) |
				((long) (bytes[index + 4] & 0xFF) << 24) | ((bytes[index + 5] & 0xFF) << 16) |
				((bytes[index + 6] & 0xFF) << 8) | (bytes[index + 7] & 0xFF);
		index += 8;
		return val;
	}

	public int readInt() {
		typeValidate(INT);
		int val = ((bytes[index]) << 24) | ((bytes[index + 1] & 0xFF) << 16) |
				((bytes[index + 2] & 0xFF) << 8) | (bytes[index + 3] & 0xFF);
		index += 4;
		return val;
	}

	public short readShort() {
		typeValidate(SHORT);
		short val = (short) (((bytes[index] & 0xFF) << 8) | (bytes[index + 1] & 0xFF));
		index += 2;
		return val;
	}

	public byte readByte() {
		typeValidate(BYTE);
		byte val = bytes[index];
		index += 1;
		return val;
	}

	public long[] readLongArray() {
		typeValidate(LONG_ARRAY);
		int numElements = bytes[index] & 0xFF;
		index += 1;
		long[] val = new long[numElements];
		for (int i = 0; i < numElements; i++) {
			long x = (bytes[index] << 56) | ((bytes[index + 1] & 0xFF) << 48) |
					((bytes[index + 2] & 0xFF) << 40) | ((bytes[index + 3] & 0xFF) << 32) |
					((bytes[index + 4] & 0xFF) << 24) | ((bytes[index + 5] & 0xFF) << 16) |
					((bytes[index + 6] & 0xFF) << 8) | (bytes[index + 7] & 0xFF);
			index += 8;
			val[i] = x;
		}
		return val;
	}

	public int[] readIntArray() {
		typeValidate(INT_ARRAY);
		int numElements = bytes[index] & 0xFF;
		index += 1;
		int[] val = new int[numElements];
		for (int i = 0; i < numElements; i++) {
			int x = ((bytes[index]) << 24) | ((bytes[index + 1] & 0xFF) << 16) |
					((bytes[index + 2] & 0xFF) << 8) | (bytes[index + 3] & 0xFF);
			index += 4;
			val[i] = x;
		}
		return val;
	}

	public short[] readShortArray() {
		typeValidate(SHORT_ARRAY);
		int numElements = bytes[index] & 0xFF;
		index += 1;
		short[] val = new short[numElements];
		for (int i = 0; i < numElements; i++) {
			short x = (short) (((bytes[index] & 0xFF) << 8) | (0xFF & bytes[index + 1] & 0xFF));
			index += 2;
			val[i] = x;
		}
		return val;
	}

	public byte[] readByteArray() {
		typeValidate(BYTE_ARRAY);
		int numElements = bytes[index] & 0xFF;
		index += 1;
		byte[] val = new byte[numElements];
		for (int i = 0; i < numElements; i++) {
			byte x = bytes[index];
			index += 1;
			val[i] = x;
		}
		return val;
	}

	public String readString() {
		typeValidate(STRING);
		short numBytes = (short) (((bytes[index] & 0xFF) << 8) | (0xFF & bytes[index + 1] & 0xFF));
		index += 2;
		String val = new String(bytes, index, numBytes, UTF_8);
		index += numBytes;
		return val;
	}

	public byte[] readIPv4() {
		typeValidate(IP_V4);
		byte[] val = new byte[] { bytes[index], bytes[index + 1], bytes[index + 2], bytes[index + 3] };
		index += 4;
		return val;
	}

	public byte[] readIPv6() {
		typeValidate(IP_V6);
		byte[] val = new byte[] { bytes[index], bytes[index + 1], bytes[index + 2], bytes[index + 3], bytes[index + 4], bytes[index + 5] };
		index += 6;
		return val;
	}

}
