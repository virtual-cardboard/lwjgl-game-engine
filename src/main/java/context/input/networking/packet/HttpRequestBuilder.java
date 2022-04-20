package context.input.networking.packet;

import static context.input.networking.packet.datatype.SerializationDataType.BYTE;
import static context.input.networking.packet.datatype.SerializationDataType.INT;
import static context.input.networking.packet.datatype.SerializationDataType.LONG;
import static context.input.networking.packet.datatype.SerializationDataType.SHORT;
import static context.input.networking.packet.datatype.SerializationDataType.STRING_UTF8;
import static context.input.networking.packet.datatype.SerializationDataType.repeated;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.ArrayDeque;
import java.util.InputMismatchException;
import java.util.Queue;

import context.input.networking.packet.cryption.EncryptionAlgorithmType;
import context.input.networking.packet.datatype.SerializationDataType;
import engine.common.loader.serialization.SerializationFormat;

public class HttpRequestBuilder {


	private final Queue<SerializationDataType> dataTypes;
	private final Queue<EncryptionAlgorithmType> encryptions;
	private final Queue<Byte> bytes = new ArrayDeque<>();
	private final String url;

	public HttpRequestBuilder(SerializationFormat format, String url) {
		this.dataTypes = format.dataTypes();
		this.encryptions = format.encryptionTypes();
		this.url = url;
	}

	private void typeValidate(SerializationDataType actual) {
		SerializationDataType expected = dataTypes.poll();
		if (expected == null || !expected.equals(actual)) {
			throw new InputMismatchException("Packet Builder packet creation failed: expected " + expected + ", actual " + actual);
		}
	}

	public HttpRequestBuilder consume(long val) {
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

	public HttpRequestBuilder consume(int val) {
		typeValidate(INT);
		bytes.add((byte) ((val >> 24) & 0xFF));
		bytes.add((byte) ((val >> 16) & 0xFF));
		bytes.add((byte) ((val >> 8) & 0xFF));
		bytes.add((byte) (val & 0xFF));
		return this;
	}

	public HttpRequestBuilder consume(short val) {
		typeValidate(SHORT);
		bytes.add((byte) ((val >> 8) & 0xFF));
		bytes.add((byte) (val & 0xFF));
		return this;
	}

	public HttpRequestBuilder consume(byte val) {
		typeValidate(BYTE);
		bytes.add(val);
		return this;
	}

	public HttpRequestBuilder consume(String val) {
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


	public HttpRequestBuilder consume(byte[] vals) {
		typeValidate(repeated(BYTE));
		bytes.add((byte) vals.length);
		for (byte x : vals) {
			bytes.add(x);
		}
		return this;
	}

	public HttpRequestModel build() {
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
		return new HttpRequestModel(url, new String(buffer, UTF_8));
	}

}
