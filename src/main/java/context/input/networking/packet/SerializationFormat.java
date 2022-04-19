package context.input.networking.packet;

import static java.util.Arrays.asList;

import java.util.LinkedList;
import java.util.Queue;

import context.input.networking.packet.cryption.EncryptionAlgorithmType;
import context.input.networking.packet.datatype.SerializationDataType;

public class SerializationFormat {

	private final Queue<SerializationDataType> dataTypes = new LinkedList<>();
	private final Queue<EncryptionAlgorithmType> encryptionTypes = new LinkedList<>();

	public SerializationFormat with(SerializationDataType... types) {
		dataTypes.addAll(asList(types));
		return this;
	}

	public SerializationFormat with(EncryptionAlgorithmType... types) {
		encryptionTypes.addAll(asList(types));
		return this;
	}

	public Queue<SerializationDataType> getDataTypes() {
		return dataTypes;
	}

	public Queue<EncryptionAlgorithmType> getEncryptionTypes() {
		return encryptionTypes;
	}

}
