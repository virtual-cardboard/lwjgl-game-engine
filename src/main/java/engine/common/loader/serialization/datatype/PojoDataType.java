package engine.common.loader.serialization.datatype;

import static engine.common.loader.serialization.datatype.DataTypeType.POJO;

import engine.common.loader.serialization.format.SerializationPojo;

public class PojoDataType extends SerializationDataType {

	private final Class<? extends SerializationPojo> pojoClass;

	protected PojoDataType(Class<? extends SerializationPojo> pojoClass) {
		super(POJO);
		this.pojoClass = pojoClass;
	}

	public Class<? extends SerializationPojo> pojoClass() {
		return pojoClass;
	}

}
