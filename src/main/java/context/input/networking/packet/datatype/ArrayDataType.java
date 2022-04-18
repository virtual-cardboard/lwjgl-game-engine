package context.input.networking.packet.datatype;

import static context.input.networking.packet.datatype.DataTypeCategory.MANY_OF;

public class ArrayDataType extends SerializationDataType {

	public ArrayDataType() {
		super(MANY_OF);
	}

}
