package context.input.networking.packet.datatype;

import static context.input.networking.packet.datatype.DataTypeType.REPEATED;

public class RepeatedDataType extends SerializationDataType {

	public final SerializationDataType dataType;

	protected RepeatedDataType(SerializationDataType dataType) {
		super(REPEATED);
		this.dataType = dataType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof RepeatedDataType)) return false;
		RepeatedDataType that = (RepeatedDataType) o;
		return dataType == that.dataType;
	}

}
