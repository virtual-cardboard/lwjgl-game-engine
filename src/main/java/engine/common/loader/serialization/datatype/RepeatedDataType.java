package engine.common.loader.serialization.datatype;

import static engine.common.loader.serialization.datatype.DataTypeType.REPEATED;

public class RepeatedDataType extends SerializationDataType {

	public final SerializationDataType repeatedDataType;

	protected RepeatedDataType(SerializationDataType repeatedDataType) {
		super(REPEATED);
		this.repeatedDataType = repeatedDataType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof RepeatedDataType)) return false;
		RepeatedDataType that = (RepeatedDataType) o;
		return repeatedDataType == that.repeatedDataType;
	}

}
