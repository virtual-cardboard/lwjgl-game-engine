package engine.common.loader.serialization.datatype;

import engine.common.loader.serialization.format.SerializationFormat;

public class SerializationDataType {

	public static final SerializationDataType LONG = new SerializationDataType(DataTypeType.LONG);
	public static final SerializationDataType INT = new SerializationDataType(DataTypeType.INT);
	public static final SerializationDataType SHORT = new SerializationDataType(DataTypeType.SHORT);
	public static final SerializationDataType BYTE = new SerializationDataType(DataTypeType.BYTE);
	public static final SerializationDataType BOOLEAN = new SerializationDataType(DataTypeType.BOOLEAN);
	public static final SerializationDataType STRING_UTF8 = new SerializationDataType(DataTypeType.STRING_UTF8);

	public final DataTypeType type;

	protected SerializationDataType(DataTypeType type) {
		this.type = type;
	}

	public static RepeatedDataType repeated(SerializationDataType type) {
		return new RepeatedDataType(type);
	}

	public static FormatDataType format(SerializationFormat format) {
		return new FormatDataType(format);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SerializationDataType)) return false;
		SerializationDataType that = (SerializationDataType) o;
		return type == that.type;
	}

}
