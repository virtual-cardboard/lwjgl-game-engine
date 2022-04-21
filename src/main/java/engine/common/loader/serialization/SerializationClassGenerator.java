package engine.common.loader.serialization;

import java.util.Queue;

import context.input.networking.packet.datatype.RepeatedDataType;
import context.input.networking.packet.datatype.SerializationDataType;

public class SerializationClassGenerator {

	private static final String SERIALIZATION_DATA_TYPE_CANONICAL_NAME = SerializationDataType.class.getCanonicalName();
	private static final String SERIALIZATION_FORMAT_SIMPLE_NAME = SerializationFormat.class.getSimpleName();
	private static final String SERIALIZATION_FORMAT_CANONICAL_NAME = SerializationFormat.class.getCanonicalName();
	private static final String SERIALIZATION_FORMAT_COLLECTION_CANONICAL_NAME = SerializationFormatCollection.class.getCanonicalName();
	private static final String SERIALIZATION_FORMAT_COLLECTION_SIMPLE_NAME = SerializationFormatCollection.class.getSimpleName();

	public static void generate(Class<? extends SerializationFormatCollection> formatsClass) {
		System.out.println(separator("Enum class"));
		System.out.println(generateEnumClass(formatsClass));
		System.out.println(separator());
		System.out.println();
		for (SerializationFormatCollection format : formatsClass.getEnumConstants()) {
			Enum<?> e = (Enum<?>) format;
			String camelCase = toCamelCase(e.name());
			System.out.println(separator(camelCase));
			System.out.println(generatePOJOClass(formatsClass, format));
			System.out.println(separator());
		}
	}

	private static String generatePOJOClass(Class<? extends SerializationFormatCollection> formatsClass, SerializationFormatCollection format) {
		Enum<?> e = (Enum<?>) format;
		String s = "";
		s += formatsClass.getPackage() + ".pojo;\n\n";
		s += "public class " + toCamelCase(e.toString()) + " {\n";
		Queue<SerializationDataType> dataTypes = format.getFormat().dataTypes();
		FormatLabels annotation = null;
		try {
			annotation = formatsClass.getField(e.name()).getAnnotation(FormatLabels.class);
		} catch (NoSuchFieldException ex) {
			throw new RuntimeException("Serialization format definition " + e.name() + " needs field names for the class generator to create a POJO class." +
					"\nAdd a @FormatLabels annotation to define field names. For example:\n\t@FormatLabels({\"field1\", \"field2\"})");
		}
		String[] labels = annotation.value();
		for (int i = 0; i < labels.length; i++) {
			SerializationDataType dataType = dataTypes.poll();
			String label = labels[i];
			s += "\tprivate " + dataType.type.toString().toLowerCase() + " " + label + ";\n";
		}
		s += "}\n";
		return s;
	}

	private static String generateEnumClass(Class<? extends SerializationFormatCollection> formatsClass) {
		String s = "";
		s += formatsClass.getPackage() + ";\n";
		s += "\n";
		s += "import static " + SerializationClassGenerator.class.getCanonicalName() + ".generate;\n";
		s += "import static " + SERIALIZATION_FORMAT_CANONICAL_NAME + ".format;\n";
		s += "import static " + SERIALIZATION_DATA_TYPE_CANONICAL_NAME + ".LONG;\n";
		s += "import static " + SERIALIZATION_DATA_TYPE_CANONICAL_NAME + ".INT;\n";
		s += "import static " + SERIALIZATION_DATA_TYPE_CANONICAL_NAME + ".SHORT;\n";
		s += "import static " + SERIALIZATION_DATA_TYPE_CANONICAL_NAME + ".BYTE;\n";
		s += "import static " + SERIALIZATION_DATA_TYPE_CANONICAL_NAME + ".BOOLEAN;\n";
		s += "import static " + SERIALIZATION_DATA_TYPE_CANONICAL_NAME + ".STRING_UTF8;\n";
		s += "import static " + SERIALIZATION_DATA_TYPE_CANONICAL_NAME + ".repeated;\n";
		s += "\n";
		s += "import " + SERIALIZATION_FORMAT_COLLECTION_CANONICAL_NAME + ";\n";
		s += "import " + SERIALIZATION_FORMAT_CANONICAL_NAME + ";\n";
		s += "\n";
		s += "public enum " + formatsClass.getSimpleName() + " implements " + SERIALIZATION_FORMAT_COLLECTION_SIMPLE_NAME + " {\n";
		s += "\n";
		for (SerializationFormatCollection format : formatsClass.getEnumConstants()) {
			Enum<?> e = (Enum<?>) format;
			Queue<SerializationDataType> dataTypes = format.getFormat().dataTypes();
			s += "	" + e.name() + "(format()" + dataTypesToString(dataTypes) + "),\n";
		}
		s += "	;\n";
		s += "\n";
		s += "	private " + SERIALIZATION_FORMAT_SIMPLE_NAME + " format;\n";
		s += "\n";
		s += "	private " + formatsClass.getSimpleName() + "(" + SERIALIZATION_FORMAT_SIMPLE_NAME + " format) {\n";
		s += "		this.format = format;\n";
		s += "	}\n";
		s += "\n";
		s += "	@Override\n";
		s += "	public " + SERIALIZATION_FORMAT_SIMPLE_NAME + " getFormat() {\n";
		s += "		return format;\n";
		s += "	}\n";
		s += "\n";
		s += "	public static void main(String[] args) {\n";
		s += "		generate(" + formatsClass.getSimpleName() + ".class);\n";
		s += "	}\n";
		s += "\n";
		s += "}\n";
		return s;
	}

	private static String dataTypesToString(Queue<SerializationDataType> dataTypes) {
		if (dataTypes.isEmpty()) {
			return "";
		}
		String s = ".with(";
		while (!dataTypes.isEmpty()) {
			SerializationDataType dataType = dataTypes.poll();
			s += dataTypeToString(dataType) + ", ";
		}
		return s.substring(0, s.length() - 2) + ")";
	}

	private static String dataTypeToString(SerializationDataType dataType) {
		switch (dataType.type) {
			case REPEATED:
				RepeatedDataType repeated = (RepeatedDataType) dataType;
				return "repeated(" + dataTypeToString(repeated.dataType) + ")";
			default:
				return dataType.type.name();
		}
	}

	private static String toCamelCase(String s) {
		String[] parts = s.split("_");
		String camelCaseString = "";
		for (String part : parts) {
			camelCaseString = camelCaseString + toProperCase(part);
		}
		return camelCaseString;
	}

	private static String toProperCase(String s) {
		if (s.isEmpty()) {
			return s;
		}
		return s.substring(0, 1).toUpperCase() +
				s.substring(1).toLowerCase();
	}

	private static String separator() {
		return separator("");
	}

	private static String separator(String text) {
		String s = "";
		int half = Math.max(0, (90 - text.length()) / 2);
		for (int i = 0; i < half; i++) {
			s += "=";
		}
		s += text;
		for (int i = 0; i < 90 - half - text.length(); i++) {
			s += "=";
		}
		return s;
	}

}
