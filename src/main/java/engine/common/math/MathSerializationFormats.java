package engine.common.math;

import static derealizer.SerializationClassGenerator.generate;
import static derealizer.datatype.SerializationDataType.FLOAT;
import static derealizer.datatype.SerializationDataType.INT;
import static derealizer.datatype.SerializationDataType.LONG;
import static derealizer.format.SerializationFormat.types;

import derealizer.format.FieldNames;
import derealizer.format.Serializable;
import derealizer.format.SerializationFormat;
import derealizer.format.SerializationFormatEnum;

public enum MathSerializationFormats implements SerializationFormatEnum {

	@FieldNames({ "seed" })
	SERIALIZABLE_RANDOM(types(LONG), SerializableRandom.class),
	@FieldNames({ "x", "y" })
	VECTOR_2F(types(FLOAT, FLOAT), Vector2f.class),
	@FieldNames({ "x", "y", "z" })
	VECTOR_3F(types(FLOAT, FLOAT, FLOAT), Vector3f.class),
	@FieldNames({ "x", "y", "z", "w" })
	VECTOR_4F(types(FLOAT, FLOAT, FLOAT, FLOAT), Vector4f.class),
	@FieldNames({ "x", "y" })
	VECTOR_2I(types(INT, INT), Vector2i.class),
	@FieldNames({ "x", "y", "z" })
	VECTOR_3I(types(INT, INT, INT), Vector3i.class),
	@FieldNames({ "x", "y" })
	VECTOR_2L(types(LONG, LONG), Vector2l.class),
	@FieldNames({ "x", "y", "z" })
	VECTOR_3L(types(LONG, LONG, LONG), Vector3l.class),

	@FieldNames({ "m00", "m01", "m10", "m11" })
	MATRIX_2F(types(FLOAT, FLOAT, FLOAT, FLOAT), Matrix2f.class),
	@FieldNames({ "m00", "m01", "m02", "m10", "m11", "m12", "m20", "m21", "m22" })
	MATRIX_3F(types(FLOAT, FLOAT, FLOAT, FLOAT, FLOAT, FLOAT, FLOAT, FLOAT, FLOAT), Matrix3f.class),
	@FieldNames({ "m00", "m01", "m02", "m03", "m10", "m11", "m12", "m13", "m20", "m21", "m22", "m23", "m30", "m31", "m32", "m33" })
	MATRIX_4F(types(FLOAT, FLOAT, FLOAT, FLOAT, FLOAT, FLOAT, FLOAT, FLOAT, FLOAT, FLOAT, FLOAT, FLOAT, FLOAT, FLOAT, FLOAT, FLOAT), Matrix4f.class),
	;

	private final SerializationFormat format;
	private final Class<? extends Serializable> pojoClass;

	MathSerializationFormats(SerializationFormat format, Class<? extends Serializable> pojoClass) {
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
		generate(MathSerializationFormats.class, Serializable.class);
	}

}
