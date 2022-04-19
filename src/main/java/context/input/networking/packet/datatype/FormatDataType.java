package context.input.networking.packet.datatype;

import static context.input.networking.packet.datatype.DataTypeType.FORMAT;

import context.input.networking.packet.SerializationFormat;

public class FormatDataType extends SerializationDataType {

	private final SerializationFormat format;

	protected FormatDataType(SerializationFormat format) {
		super(FORMAT);
		this.format = format;
	}

	public SerializationFormat getFormat() {
		return format;
	}

}
