package engine.common.loader.serialization.format;

import engine.common.loader.serialization.SerializationReader;
import engine.common.loader.serialization.SerializationWriter;

public interface SerializationPojo {

	public static <T extends SerializationPojo> T createPojo(Class<T> pojoClass) {
		try {
			return pojoClass.getConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public abstract void read(SerializationReader reader);


	public abstract byte[] toByteArray(SerializationWriter writer);

}
