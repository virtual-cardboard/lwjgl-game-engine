package engine.common.loader.serialization.format;

import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.Queue;

import engine.common.loader.serialization.datatype.SerializationDataType;

public class SerializationFormat {

	private final Queue<SerializationDataType> dataTypes = new ArrayDeque<>();

	protected SerializationFormat() {
	}

	public static SerializationFormat types(SerializationDataType... types) {
		return new SerializationFormat().with(types);
	}

	public SerializationFormat with(SerializationDataType... types) {
		dataTypes.addAll(asList(types));
		return this;
	}

	public ArrayDeque<SerializationDataType> dataTypes() {
		return new ArrayDeque<>(dataTypes);
	}

}
