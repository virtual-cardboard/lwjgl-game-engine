package engine.common.loader.serialization;

import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.Queue;

import context.input.networking.packet.cryption.EncryptionAlgorithmType;
import context.input.networking.packet.datatype.SerializationDataType;

public class SerializationFormat {

	private final Queue<SerializationDataType> dataTypes = new ArrayDeque<>();
	private final Queue<EncryptionAlgorithmType> encryptionTypes = new ArrayDeque<>();

	public static SerializationFormat format() {
		return new SerializationFormat();
	}

	public SerializationFormat with(SerializationDataType... types) {
		dataTypes.addAll(asList(types));
		return this;
	}

	public SerializationFormat with(EncryptionAlgorithmType... types) {
		encryptionTypes.addAll(asList(types));
		return this;
	}

	public ArrayDeque<SerializationDataType> dataTypes() {
		return new ArrayDeque<>(dataTypes);
	}

	public ArrayDeque<EncryptionAlgorithmType> encryptionTypes() {
		return new ArrayDeque<>(encryptionTypes);
	}

}
