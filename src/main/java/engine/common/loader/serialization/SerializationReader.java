package engine.common.loader.serialization;

import static context.input.networking.packet.datatype.SerializationDataType.BYTE;
import static context.input.networking.packet.datatype.SerializationDataType.INT;
import static context.input.networking.packet.datatype.SerializationDataType.LONG;
import static context.input.networking.packet.datatype.SerializationDataType.SHORT;
import static context.input.networking.packet.datatype.SerializationDataType.repeated;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Queue;
import java.util.function.Supplier;

import context.input.networking.packet.datatype.SerializationDataType;

public class SerializationReader {

	private byte[] bytes;
	private Queue<SerializationDataType> dataTypes;
	private int index = 0;

	public SerializationReader(SerializationFormat format, byte[] bytes) {
		this.dataTypes = format.dataTypes();
		// TODO
		// apply encryption inverses
		this.bytes = bytes;
	}

	public SerializationReader(SerializationFormat format, SerializationReader reader) {
		this.dataTypes = format.dataTypes();
		// encryption here too?
		this.bytes = reader.bytes;
		this.index = reader.index;
	}

	private void typeValidate(SerializationDataType actual) {
		SerializationDataType expected = dataTypes.poll();
		if (!actual.equals(expected)) {
			throw new InputMismatchException("Packet block creation failed: expected " + expected + ", actual " + actual);
		}
	}

	public long readLong() {
		typeValidate(LONG);
		long val = ((long) bytes[index] << 56) | ((long) (bytes[index + 1] & 0xFF) << 48) | ((long) (bytes[index + 2] & 0xFF) << 40) | ((long) (bytes[index + 3] & 0xFF) << 32) | ((long) (bytes[index + 4] & 0xFF) << 24) | ((bytes[index + 5] & 0xFF) << 16) | ((bytes[index + 6] & 0xFF) << 8) | (bytes[index + 7] & 0xFF);
		index += 8;
		return val;
	}

	public int readInt() {
		typeValidate(INT);
		int val = ((bytes[index]) << 24) | ((bytes[index + 1] & 0xFF) << 16) | ((bytes[index + 2] & 0xFF) << 8) | (bytes[index + 3] & 0xFF);
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

}
