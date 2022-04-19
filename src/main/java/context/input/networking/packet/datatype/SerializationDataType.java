package context.input.networking.packet.datatype;

import context.input.networking.packet.SerializationFormat;

public class SerializationDataType {

    public static final SerializationDataType LONG = new SerializationDataType(DataTypeType.LONG);
    public static final SerializationDataType INT = new SerializationDataType(DataTypeType.INT);
    public static final SerializationDataType SHORT = new SerializationDataType(DataTypeType.SHORT);
    public static final SerializationDataType BYTE = new SerializationDataType(DataTypeType.BYTE);
    public static final SerializationDataType BOOLEAN = new SerializationDataType(DataTypeType.BOOLEAN);
    public static final SerializationDataType STRING_UTF8 = new SerializationDataType(DataTypeType.STRING_UTF8);

    private final DataTypeType type;

    protected SerializationDataType(DataTypeType type) {
        this.type = type;
    }

    public DataTypeType type() {
        return type;
    }

    public static RepeatedDataType repeated(SerializationDataType type) {
        return new RepeatedDataType(type);
    }

    public static FormatDataType format(SerializationFormat format) {
        return new FormatDataType(format);
    }

}
