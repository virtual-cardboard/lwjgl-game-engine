package engine.common.networking.packet;

import static derealizer.SerializationClassGenerator.generate;
import static derealizer.datatype.SerializationDataType.BYTE;
import static derealizer.datatype.SerializationDataType.SHORT;
import static derealizer.format.SerializationFormat.types;

import derealizer.format.FieldNames;
import derealizer.format.SerializationFormat;
import derealizer.format.SerializationFormatEnum;
import derealizer.format.SerializationPojo;

public enum NetworkingSerializationFormats implements SerializationFormatEnum<SerializationPojo> {

	@FieldNames({ "b0", "b1", "b2", "b3", "port" })
	PACKET_ADDRESS(types(BYTE, BYTE, BYTE, BYTE, SHORT), null),
	;

	// Do not edit auto-generated code below this line.

	private final SerializationFormat format;
	private final Class<? extends SerializationPojo> pojoClass;
	private final Class<? extends SerializationPojo> superClass;

	private NetworkingSerializationFormats(SerializationFormat format, Class<? extends SerializationPojo> pojoClass) {
		this(format, pojoClass, SerializationPojo.class);
	}

	private NetworkingSerializationFormats(SerializationFormat format, Class<? extends SerializationPojo> pojoClass, Class<? extends SerializationPojo> superClass) {
		this.format = format;
		this.pojoClass = pojoClass;
		this.superClass = superClass;
	}

	@Override
	public SerializationFormat format() {
		return format;
	}

	@Override
	public Class<? extends SerializationPojo> pojoClass() {
		return pojoClass;
	}

	@Override
	public Class<? extends SerializationPojo> superClass() {
		return superClass;
	}

	public static void main(String[] args) {
		generate(NetworkingSerializationFormats.class, SerializationPojo.class);
	}

}
