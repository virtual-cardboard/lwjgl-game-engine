package engine.common.math;

import static derealizer.SerializationClassGenerator.generate;
import static derealizer.datatype.SerializationDataType.LONG;
import static derealizer.format.SerializationFormat.types;

import derealizer.format.FieldNames;
import derealizer.format.SerializationFormat;
import derealizer.format.SerializationFormatEnum;
import derealizer.format.SerializationPojo;

public enum MathSerializationFormats implements SerializationFormatEnum {

	@FieldNames({ "seed" })
	SERIALIZABLE_RANDOM(types(LONG), SerializableRandom.class),
	;

	private final SerializationFormat format;
	private final Class<? extends SerializationPojo<?>> pojoClass;

	MathSerializationFormats(SerializationFormat format, Class<? extends SerializationPojo<?>> pojoClass) {
		this.format = format;
		this.pojoClass = pojoClass;
	}

	@Override
	public SerializationFormat format() {
		return format;
	}

	@Override
	public Class<? extends SerializationPojo<?>> pojoClass() {
		return pojoClass;
	}

	public static void main(String[] args) {
		generate(MathSerializationFormats.class, SerializationPojo.class);
	}

}
