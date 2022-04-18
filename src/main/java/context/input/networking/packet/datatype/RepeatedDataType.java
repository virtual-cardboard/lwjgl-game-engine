package context.input.networking.packet.datatype;

import static context.input.networking.packet.datatype.DataTypeType.REPEATED;

public class RepeatedDataType extends SerializationDataType {

	private SerializationDataType dataType;

	protected RepeatedDataType(SerializationDataType dataType) {
		super(REPEATED);
		this.dataType = dataType;
	}

	public SerializationDataType getDataType() {
		return dataType;
	}

}
