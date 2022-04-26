package engine.common.loader.serialization.datatype;

import static engine.common.loader.serialization.datatype.DataTypeType.OPTIONAL;

public class OptionalDataType extends SerializationDataType {

	public final SerializationDataType optionalDataType;

	protected OptionalDataType(SerializationDataType optionalDataType) {
		super(OPTIONAL);
		this.optionalDataType = optionalDataType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof OptionalDataType)) return false;
		OptionalDataType that = (OptionalDataType) o;
		return optionalDataType == that.optionalDataType;
	}

}
