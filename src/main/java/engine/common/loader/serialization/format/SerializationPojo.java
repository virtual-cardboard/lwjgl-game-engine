package engine.common.loader.serialization.format;

public interface SerializationPojo {

	public static <T extends SerializationPojo> T createPojo(Class<T> pojoClass) {
		try {
			return pojoClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
