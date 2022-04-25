package engine.common.loader.serialization.datatype;

import static engine.common.loader.serialization.datatype.DataTypeType.FORMAT;

import engine.common.loader.serialization.format.SerializationFormat;

public class FormatDataType extends SerializationDataType {

	private final SerializationFormat format;

	protected FormatDataType(SerializationFormat format) {
		super(FORMAT);
		this.format = format;
	}

	public SerializationFormat format() {
		return format;
	}

}
