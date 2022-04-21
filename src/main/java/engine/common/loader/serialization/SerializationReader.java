package engine.common.loader.serialization;

import static context.input.networking.packet.datatype.SerializationDataType.BYTE;
import static context.input.networking.packet.datatype.SerializationDataType.INT;
import static context.input.networking.packet.datatype.SerializationDataType.LONG;
import static context.input.networking.packet.datatype.SerializationDataType.SHORT;
import static context.input.networking.packet.datatype.SerializationDataType.STRING_UTF8;
import static context.input.networking.packet.datatype.SerializationDataType.repeated;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import context.input.networking.packet.datatype.DataTypeType;
import context.input.networking.packet.datatype.SerializationDataType;

public class SerializationReader {

	private byte[] bytes;
	private ArrayDeque<SerializationDataType> dataTypes;
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
			throw new InputMismatchException("Deserialization failed: expected data type " + expected + ", actual " + actual);
		}
	}

	private void typeValidate(DataTypeType actual) {
		SerializationDataType expected = dataTypes.poll();
		if (!actual.equals(expected.type)) {
			throw new InputMismatchException("Deserialization failed: expected data type type " + expected + ", actual " + actual);
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

	public String readStringUtf8() {
		typeValidate(STRING_UTF8);
		short numBytes = (short) (((bytes[index] & 0xFF) << 8) | (0xFF & bytes[index + 1] & 0xFF));
		index += 2;
		String val = new String(bytes, index, numBytes, UTF_8);
		index += numBytes;
		return val;
	}

	public List<Long> readRepeatedLong() {
		typeValidate(repeated(LONG));
		int numElements = bytes[index] & 0xFF;
		index += 1;
		List<Long> l = new ArrayList<>();
		for (int i = 0; i < numElements; i++) {
			dataTypes.add(LONG);
			l.add(readLong());
		}
		return l;
	}

	public List<Integer> readRepeatedInt() {
		typeValidate(repeated(INT));
		int numElements = bytes[index] & 0xFF;
		index += 1;
		List<Integer> l = new ArrayList<>();
		for (int i = 0; i < numElements; i++) {
			dataTypes.add(INT);
			l.add(readInt());
		}
		return l;
	}

	public List<Short> readRepeatedShort() {
		typeValidate(repeated(SHORT));
		int numElements = bytes[index] & 0xFF;
		index += 1;
		List<Short> l = new ArrayList<>();
		for (int i = 0; i < numElements; i++) {
			dataTypes.add(SHORT);
			l.add(readShort());
		}
		return l;
	}

	public List<Byte> readRepeatedByte() {
		typeValidate(repeated(BYTE));
		int numElements = bytes[index] & 0xFF;
		index += 1;
		List<Byte> l = new ArrayList<>();
		for (int i = 0; i < numElements; i++) {
			dataTypes.add(BYTE);
			l.add(readByte());
		}
		return l;
	}

	public List<String> readRepeatedStringUtf8() {
		typeValidate(repeated(STRING_UTF8));
		int numElements = bytes[index] & 0xFF;
		index += 1;
		List<String> l = new ArrayList<>();
		for (int i = 0; i < numElements; i++) {
			dataTypes.add(STRING_UTF8);
			l.add(readStringUtf8());
		}
		return l;
	}

	// TODO
//	public Object readFormat() {
//		SerializationDataType dataType = dataTypes.peek();
//		typeValidate(FORMAT);
//		FormatDataType formatDataType = (FormatDataType) dataType;
//		return null;
//	}

}
