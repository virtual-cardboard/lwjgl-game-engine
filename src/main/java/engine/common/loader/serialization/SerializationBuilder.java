package engine.common.loader.serialization;

import static engine.common.loader.serialization.datatype.SerializationDataType.BYTE;
import static engine.common.loader.serialization.datatype.SerializationDataType.INT;
import static engine.common.loader.serialization.datatype.SerializationDataType.LONG;
import static engine.common.loader.serialization.datatype.SerializationDataType.SHORT;
import static engine.common.loader.serialization.datatype.SerializationDataType.STRING_UTF8;
import static engine.common.loader.serialization.datatype.SerializationDataType.repeated;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.ArrayDeque;
import java.util.InputMismatchException;
import java.util.Queue;

import context.input.networking.packet.HttpRequestModel;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.address.PacketAddress;
import engine.common.loader.serialization.datatype.SerializationDataType;
import engine.common.loader.serialization.format.SerializationFormat;

public class SerializationBuilder {

	private final Queue<SerializationDataType> dataTypes;
	private final Queue<Byte> bytes = new ArrayDeque<>();

	public SerializationBuilder(SerializationFormat format) {
		this.dataTypes = format.dataTypes();
	}

	private void typeValidate(SerializationDataType actual) {
		SerializationDataType expected = dataTypes.poll();
		if (expected == null || !expected.equals(actual)) {
			throw new InputMismatchException("Packet Builder packet creation failed: expected " + expected + ", actual " + actual);
		}
	}

	public SerializationBuilder consume(long val) {
		typeValidate(LONG);
		bytes.add((byte) ((val >> 56) & 0xFF));
		bytes.add((byte) ((val >> 48) & 0xFF));
		bytes.add((byte) ((val >> 40) & 0xFF));
		bytes.add((byte) ((val >> 32) & 0xFF));
		bytes.add((byte) ((val >> 24) & 0xFF));
		bytes.add((byte) ((val >> 16) & 0xFF));
		bytes.add((byte) ((val >> 8) & 0xFF));
		bytes.add((byte) (val & 0xFF));
		return this;
	}

	public SerializationBuilder consume(int val) {
		typeValidate(INT);
		bytes.add((byte) ((val >> 24) & 0xFF));
		bytes.add((byte) ((val >> 16) & 0xFF));
		bytes.add((byte) ((val >> 8) & 0xFF));
		bytes.add((byte) (val & 0xFF));
		return this;
	}

	public SerializationBuilder consume(short val) {
		typeValidate(SHORT);
		bytes.add((byte) ((val >> 8) & 0xFF));
		bytes.add((byte) (val & 0xFF));
		return this;
	}

	public SerializationBuilder consume(byte val) {
		typeValidate(BYTE);
		bytes.add(val);
		return this;
	}

	public SerializationBuilder consume(String val) {
		typeValidate(STRING_UTF8);
		byte[] b = val.getBytes(UTF_8);
		short numBytes = (short) b.length;
		bytes.add((byte) ((numBytes >> 8) & 0xFF));
		bytes.add((byte) (numBytes & 0xFF));
		for (byte x : b) {
			bytes.add(x);
		}
		return this;
	}

	public SerializationBuilder consume(byte[] vals) {
		typeValidate(repeated(BYTE));
		bytes.add((byte) vals.length);
		for (byte x : vals) {
			bytes.add(x);
		}
		return this;
	}

	public HttpRequestModel buildHttpRequestModel(String urlPath) {
		if (!dataTypes.isEmpty()) {
			throw new RuntimeException("Did not complete packet format");
		}
		byte[] buffer = new byte[bytes.size()];
		int i = 0;
		while (!bytes.isEmpty()) {
			buffer[i] = bytes.poll();
			i++;
		}
		// TODO: apply encryption
		return new HttpRequestModel(buffer, urlPath);
	}

	public PacketModel buildPacketModel(PacketAddress address) {
		if (!dataTypes.isEmpty()) {
			throw new RuntimeException("Did not complete packet format");
		}
		byte[] buffer = new byte[bytes.size()];
		int i = 0;
		while (!bytes.isEmpty()) {
			buffer[i] = bytes.poll();
			i++;
		}
		// TODO: apply encryption
		return new PacketModel(buffer, address);
	}

}
