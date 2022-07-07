package engine.common.networking.packet;

import static derealizer.SerializationClassGenerator.generate;
import static derealizer.datatype.SerializationDataType.BYTE;
import static derealizer.datatype.SerializationDataType.SHORT;
import static derealizer.format.SerializationFormat.types;

import derealizer.format.FieldNames;
import derealizer.format.Serializable;
import derealizer.format.SerializationFormat;
import derealizer.format.SerializationFormatEnum;

public enum NetworkingSerializationFormats implements SerializationFormatEnum {

	@FieldNames({ "b0", "b1", "b2", "b3", "port" })
	PACKET_ADDRESS(types(BYTE, BYTE, BYTE, BYTE, SHORT), null),
	;

	// Do not edit auto-generated code below this line.

	private final SerializationFormat format;
	private final Class<? extends Serializable> pojoClass;

	private NetworkingSerializationFormats(SerializationFormat format, Class<? extends Serializable> pojoClass) {
		this.format = format;
		this.pojoClass = pojoClass;
	}

	@Override
	public SerializationFormat format() {
		return format;
	}

	@Override
	public Class<? extends Serializable> pojoClass() {
		return pojoClass;
	}

	public static void main(String[] args) {
		generate(NetworkingSerializationFormats.class, Serializable.class);
	}

}
