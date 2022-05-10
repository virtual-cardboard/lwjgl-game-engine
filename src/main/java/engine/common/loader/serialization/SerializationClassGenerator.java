package engine.common.loader.serialization;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.lang.reflect.Field;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Queue;

import engine.common.loader.serialization.datatype.OptionalDataType;
import engine.common.loader.serialization.datatype.PojoDataType;
import engine.common.loader.serialization.datatype.RepeatedDataType;
import engine.common.loader.serialization.datatype.SerializationDataType;
import engine.common.loader.serialization.format.FormatLabels;
import engine.common.loader.serialization.format.SerializationFormat;
import engine.common.loader.serialization.format.SerializationFormatEnum;
import engine.common.loader.serialization.format.SerializationPojo;

public class SerializationClassGenerator {

	public static <T extends SerializationPojo> void generate(Class<? extends SerializationFormatEnum<T>> formatsClass, Class<T> pojoBaseClass) {
		System.out.println(separator("Enum class"));
		System.out.println(generateEnumClass(formatsClass, pojoBaseClass));
		System.out.println(separator());
		System.out.println();
		for (SerializationFormatEnum<T> format : formatsClass.getEnumConstants()) {
			Enum<?> e = (Enum<?>) format;
			verifyEnumLabels(format);
			System.out.println(separator(toCamelCase(e.name())));
			System.out.println(generatePOJOClass(format, pojoBaseClass));
			System.out.println(separator());
		}
	}

	private static <T extends SerializationPojo> String generateEnumClass(Class<? extends SerializationFormatEnum<T>> formatsClass, Class<T> pojoBaseClass) {
		String s = "";
		s += formatsClass.getPackage() + ";\n";
		s += "\n";
		s += "import static " + SerializationClassGenerator.class.getCanonicalName() + ".generate;\n";
		s += "import static " + SerializationDataType.class.getCanonicalName() + ".BOOLEAN;\n";
		s += "import static " + SerializationDataType.class.getCanonicalName() + ".BYTE;\n";
		s += "import static " + SerializationDataType.class.getCanonicalName() + ".LONG;\n";
		s += "import static " + SerializationDataType.class.getCanonicalName() + ".INT;\n";
		s += "import static " + SerializationDataType.class.getCanonicalName() + ".SHORT;\n";
		s += "import static " + SerializationDataType.class.getCanonicalName() + ".STRING_UTF8;\n";
		s += "import static " + SerializationDataType.class.getCanonicalName() + ".optional;\n";
		s += "import static " + SerializationDataType.class.getCanonicalName() + ".pojo;\n";
		s += "import static " + SerializationDataType.class.getCanonicalName() + ".repeated;\n";
		s += "import static " + SerializationFormat.class.getCanonicalName() + ".types;\n";
		s += "\n";
		s += "import " + SerializationFormatEnum.class.getCanonicalName() + ";\n";
		s += "import " + SerializationFormat.class.getCanonicalName() + ";\n";
		s += "import " + FormatLabels.class.getCanonicalName() + ";\n";
		s += "import " + SerializationPojo.class.getCanonicalName() + ";\n";
		for (SerializationFormatEnum<T> format : formatsClass.getEnumConstants()) {
			Enum<?> e = (Enum<?>) format;
			s += "import " + formatsClass.getPackage().getName() + ".pojo." + toCamelCase(e.name()) + ";\n";
		}
		s += "\n";
		s += "public enum " + formatsClass.getSimpleName() + " implements " + SerializationFormatEnum.class.getSimpleName() + "<" + pojoBaseClass.getSimpleName() + "> {\n";
		s += "\n";
		for (SerializationFormatEnum<T> format : formatsClass.getEnumConstants()) {
			Enum<?> e = (Enum<?>) format;
			Queue<SerializationDataType> dataTypes = format.format().dataTypes();
			s += "	@" + FormatLabels.class.getSimpleName() + "({ " + commaify(quotify(getFieldNames(format))) + " })\n";
			if (format.superClass().equals(pojoBaseClass)) {
				s += "	" + e.name() + "(types(" + commaify(dataTypes) + ")),\n";
			} else {
				s += "	" + e.name() + "(types(" + commaify(dataTypes) + "), " + format.superClass().getSimpleName() + ".class),\n";
			}
		}
		s += "	;\n";
		s += "\n";
		s += "	// Do not edit auto-generated code below this line.\n";
		s += "\n";
		s += "	private final " + SerializationFormat.class.getSimpleName() + " format;\n";
		s += "	private final Class<? extends " + pojoBaseClass.getSimpleName() + "> superClass;\n";
		s += "\n";
		s += "	private " + formatsClass.getSimpleName() + "(" + SerializationFormat.class.getSimpleName() + " format) {\n";
		s += "		this(format, " + pojoBaseClass.getSimpleName() + ".class);\n";
		s += "	}\n";
		s += "\n";
		s += "	private " + formatsClass.getSimpleName() + "(" + SerializationFormat.class.getSimpleName() + " format, Class<? extends " + pojoBaseClass.getSimpleName() + "> superClass) {\n";
		s += "		this.format = format;\n";
		s += "		this.superClass = superClass;\n";
		s += "	}\n";
		s += "\n";
		s += "	@Override\n";
		s += "	public " + SerializationFormat.class.getSimpleName() + " format() {\n";
		s += "		return format;\n";
		s += "	}\n";
		s += "\n";
		s += "	@Override\n";
		s += "	public Class<? extends " + pojoBaseClass.getSimpleName() + "> superClass() {\n";
		s += "		return superClass;\n";
		s += "	}\n";
		s += "\n";
		s += "	public static void main(String[] args) {\n";
		s += "		generate(" + formatsClass.getSimpleName() + ".class, " + pojoBaseClass.getSimpleName() + ".class);\n";
		s += "	}\n";
		s += "\n";
		s += "}\n";
		return s;
	}

	private static <T extends SerializationPojo> String generatePOJOClass(SerializationFormatEnum<T> format, Class<T> pojoBaseClass) {
		String implementsOrExtends = pojoBaseClass.isInterface() ? "implements" : "extends";
		Enum<?> e = (Enum<?>) format;
		String s = "";
		s += format.getClass().getPackage() + ".pojo;\n";
		s += "\n";
		s += "import java.util.List;\n";
		s += "\n";
		s += "public class " + toCamelCase(e.name()) + " " + implementsOrExtends + " " + pojoBaseClass.getSimpleName() + " {\n";
		s += "\n";
		String[] fieldNames = getFieldNames(format);
		String[] fieldTypes = format.format().dataTypes().stream().map(SerializationClassGenerator::toFieldType).toArray(String[]::new);
		for (int i = 0; i < fieldNames.length; i++) {
			s += "	private " + fieldTypes[i] + " " + fieldNames[i] + ";\n";
		}
		s += "\n";
		SerializationDataType[] dataTypes = format.format().dataTypes().stream().toArray(SerializationDataType[]::new);
		s += "	public " + toCamelCase(e.name()) + "() {\n";
		s += "	}\n";
		s += "\n";
		s += "	@Override\n";
		s += "	public void read(" + SerializationReader.class.getSimpleName() + " reader) {\n";
		for (int i = 0; i < fieldNames.length; i++) {
			SerializationDataType dataType = dataTypes[i];
			s += toReadMethod(fieldNames[i], dataType);
		}
		s += "	}\n";
		s += "\n";
		s += "	@Override\n";
		s += "	public void write(SerializationWriter writer) {\n";
		for (int i = 0; i < fieldNames.length; i++) {
			s += toWriteMethod(fieldNames[i], dataTypes[i]);
		}
		s += "	}\n";
		s += "\n";
		for (int i = 0; i < fieldNames.length; i++) {
			s += "	public " + fieldTypes[i] + " " + fieldNames[i] + "() {\n";
			s += "		return " + fieldNames[i] + ";\n";
			s += "	}\n";
			s += "\n";
		}
		s += "}\n";
		return s;
	}

	private static String toReadMethod(String fieldName, SerializationDataType dataType) {
		return toReadMethod(fieldName, dataType, 0, 0, null);
	}

	private static String toReadMethod(String variableName, SerializationDataType dataType, int numIndents, int variablesCount, String listName) {
		String indents = "\t\t";
		for (int i = 0; i < numIndents; i++) {
			indents += "\t";
		}
		if (listName == null) {
			// Set a field
			switch (dataType.type) {
				case LONG:
				case INT:
				case SHORT:
				case BYTE:
				case BOOLEAN:
				case STRING_UTF8:
					return indents + "this." + variableName + " = reader.read" + toCamelCase(dataType.type.name().toLowerCase()) + "();\n";
				case REPEATED: {
					SerializationDataType repeatedDataType = ((RepeatedDataType) dataType).repeatedDataType;
					String iterVariable = "i" + variablesCount;
					String numVariable = "numElements" + variablesCount;
					String s = indents + "this." + variableName + " = new ArrayList<>();\n";
					s += indents + "for (byte " + iterVariable + " = 0, " + numVariable + " = reader.readByte(); " + iterVariable + " < " + numVariable + "; " + iterVariable + "++) {\n";
					numIndents++;
					variablesCount++;
					s += toReadMethod(null, repeatedDataType, numIndents, variablesCount, variableName);
					s += indents + "}\n";
					return s;
				}
				case OPTIONAL:
					SerializationDataType optionalDataType = ((OptionalDataType) dataType).optionalDataType;
					return indents + "if (reader.readBoolean()) {\n" +
							toReadMethod(variableName, optionalDataType, numIndents, variablesCount, null) +
							indents + "};\n";
				case POJO: {
					Class<? extends SerializationPojo> pojoClass = ((PojoDataType) dataType).pojoClass();
					String pojoVariableType = pojoClass.getSimpleName();
					return indents + "this." + variableName + " = new " + pojoVariableType + "();\n" +
							indents + "this." + variableName + ".read(reader);\n";
				}
				default:
					throw new RuntimeException("Unhandled SerializationDataType: " + dataType.type + "\nCould not interpret data type as a field type.");
			}
		} else {
			// Add something to a list
			switch (dataType.type) {
				case LONG:
				case INT:
				case SHORT:
				case BYTE:
				case BOOLEAN:
				case STRING_UTF8:
					return indents + listName + ".add(reader.read" + toCamelCase(dataType.type.name().toLowerCase()) + "());\n";
				case REPEATED:
					SerializationDataType repeatedDataType = ((RepeatedDataType) dataType).repeatedDataType;
					String iterVariable = "i" + variablesCount;
					String numVariable = "numElements" + variablesCount;
					String newListName = "list" + variablesCount;
					return indents + "List<" + convertPrimitiveToWrapper(repeatedDataType) + "> " + newListName + " = new ArrayList<>();\n" +
							indents + "for (byte " + iterVariable + " = 0, " + numVariable + " = reader.readByte(); " + iterVariable + " < " + numVariable + "; " + iterVariable + "++) {\n" +
							toReadMethod(null, repeatedDataType, ++numIndents, ++variablesCount, variableName) +
							indents + "}\n" +
							indents + "this." + variableName + ".add(" + newListName + ");\n";
//			    case ONE_OF:
				case OPTIONAL:
					SerializationDataType optionalDataType = ((OptionalDataType) dataType).optionalDataType;
					return indents + "if (reader.readBoolean()) {\n" +
							toReadMethod(variableName, optionalDataType, numIndents, variablesCount, listName) +
							indents + "} else {\n" +
							indents + "\t" + listName + ".add(null);\n" +
							indents + "}\n";
				case POJO: {
					Class<? extends SerializationPojo> pojoClass = ((PojoDataType) dataType).pojoClass();
					String pojoVariableType = pojoClass.getSimpleName();
					String pojoVariableName = "pojo" + variablesCount;
					return indents + pojoVariableType + " " + pojoVariableName + " = new " + pojoVariableType + "();\n" +
							indents + pojoVariableName + ".read(reader);\n" +
							indents + listName + ".add(" + pojoVariableName + ");\n";
				}

				default:
					throw new RuntimeException("Unhandled SerializationDataType: " + dataType.type + "\nCould not add data type to " + listName);
			}
		}
	}

	private static String toWriteMethod(String variableName, SerializationDataType dataType) {
		return toWriteMethod(variableName, dataType, 0, 0);
	}

	private static String toWriteMethod(String variableName, SerializationDataType dataType, int numIndents, int variablesCount) {
		String indents = "\t\t";
		for (int i = 0; i < numIndents; i++) {
			indents += "\t";
		}
		// Set a field
		switch (dataType.type) {
			case LONG:
			case INT:
			case SHORT:
			case BYTE:
			case BOOLEAN:
			case STRING_UTF8:
				return indents + "writer.consume(" + variableName + ");\n";
			case REPEATED:
				SerializationDataType repeatedDataType = ((RepeatedDataType) dataType).repeatedDataType;
				String iterVariable = "i" + variablesCount;
				String s = indents + "writer.consume((byte) " + variableName + ".size());\n" +
						indents + "for (int " + iterVariable + " = 0; " + iterVariable + " < " + variableName + ".size(); " + iterVariable + "++) {\n";
				s += toWriteMethod(variableName + ".get(" + iterVariable + ")", repeatedDataType, ++numIndents, ++variablesCount);
				s += indents + "}\n";
				return s;
			case OPTIONAL:
				SerializationDataType optionalDataType = ((OptionalDataType) dataType).optionalDataType;
				return indents + "if (variable == null) {\n" +
						indents + " writer.consume(false);\n" +
						indents + "} else {\n" +
						indents + " writer.consume(true);\n" +
						toWriteMethod(variableName, optionalDataType, numIndents, variablesCount) +
						indents + "};\n";
			case POJO:
				return indents + variableName + ".write(writer);\n";
			default:
				throw new RuntimeException("Unhandled SerializationDataType: " + dataType.type + "\nCould not interpret data type as a field type.");
		}
	}

	private static <T extends SerializationPojo> void verifyEnumLabels(SerializationFormatEnum<T> format) {
		if (getFieldNames(format).length != format.format().dataTypes().size()) {
			Enum<?> e = (Enum<?>) format;
			throw new InputMismatchException("Serialization format definition " + e.name() + " needs the same number of labels in the @FormatLabels annotation " + "as data types in the format for the class generator to create a POJO class." + "\nFor example:\n	@FormatLabels({\"field1\", \"field2\"})\n	FORMAT_1(format().with(INT, INT))");
		}
	}

	private static String toFieldType(SerializationDataType dataType) {
		switch (dataType.type) {
			case LONG:
			case INT:
			case SHORT:
			case BYTE:
			case BOOLEAN:
				return dataType.type.name().toLowerCase();
			case STRING_UTF8:
				return "String";
			case ONE_OF:
				// TODO figure out how to concisely express a "one of ..." data type
				return "Object";
			case REPEATED:
				return "List<" + convertPrimitiveToWrapper(((RepeatedDataType) dataType).repeatedDataType) + ">";
			case POJO:
				return ((PojoDataType) dataType).pojoClass().getSimpleName();
			case OPTIONAL:
			default:
				throw new RuntimeException("Unhandled SerializationDataType: " + dataType.type + "\nCould not interpret data type as a field type.");
		}
	}

	private static String convertPrimitiveToWrapper(SerializationDataType dataType) {
		switch (dataType.type) {
			case INT:
				return "Integer";
			case LONG:
			case SHORT:
			case BYTE:
			case BOOLEAN:
				String n = dataType.type.name();
				return n.charAt(0) + n.substring(1).toLowerCase();
			default:
				return toFieldType(dataType);
		}
	}

	private static <T extends SerializationPojo> String[] getFieldNames(SerializationFormatEnum<T> format) {
		Enum<?> formatEnum = (Enum<?>) format;
		try {
			Field field = format.getClass().getField(formatEnum.name());
			if (!field.isAnnotationPresent(FormatLabels.class)) {
				throw new RuntimeException("Serialization format definition " + formatEnum.name() + " is missing an @FormatLabels annotation.");
			}
			return field.getAnnotation(FormatLabels.class).value();
		} catch (NoSuchFieldException e) {
			throw new RuntimeException("Serialization format definition " + formatEnum.name() + " needs field names for the class generator to create a POJO class." + "\nAdd a @FormatLabels annotation to define field names. For example:\n	@FormatLabels({\"field1\", \"field2\"})");
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	private static String dataTypeToString(SerializationDataType dataType) {
		switch (dataType.type) {
			case OPTIONAL:
				OptionalDataType optionalDataType = (OptionalDataType) dataType;
				return "optional(" + dataTypeToString(optionalDataType.optionalDataType) + ")";
			case POJO:
				PojoDataType pojoDataType = (PojoDataType) dataType;
				return "pojo(" + pojoDataType.pojoClass().getSimpleName() + ".class)";
			case REPEATED:
				RepeatedDataType repeatedDataType = (RepeatedDataType) dataType;
				return "repeated(" + dataTypeToString(repeatedDataType.repeatedDataType) + ")";
			default:
				return dataType.type.name();
		}
	}

	private static String[] quotify(String[] strings) {
		for (int i = 0; i < strings.length; i++) {
			strings[i] = "\"" + strings[i] + "\"";
		}
		return strings;
	}

	private static String commaify(String[] strings) {
		return commaify(asList(strings));
	}

	private static String commaify(Queue<SerializationDataType> dataTypes) {
		List<String> strings = dataTypes.stream().map(SerializationClassGenerator::dataTypeToString).collect(toList());
		return commaify(strings);
	}

	private static String commaify(Iterable<String> strings) {
		String s = "";
		for (String string : strings) {
			s += string + ", ";
		}
		if (s.length() == 0) {
			return "";
		}
		return s.substring(0, s.length() - 2);

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
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
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
