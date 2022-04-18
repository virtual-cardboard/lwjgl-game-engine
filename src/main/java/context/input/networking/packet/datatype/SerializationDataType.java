package context.input.networking.packet.datatype;

public class SerializationDataType {

	private final DataTypeCategory category;

	protected SerializationDataType(DataTypeCategory category) {
		this.category = category;
	}

	public DataTypeCategory category() {
		return category;
	}
}
