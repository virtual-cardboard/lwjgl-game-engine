package common.event;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import common.source.GameSource;

public abstract class SerializableEvent extends GameEvent implements Serializable {

	private static final long serialVersionUID = 2930005876188291805L;

	public SerializableEvent(long time, GameSource source) {
		super(time, source);
	}

	public SerializableEvent(GameSource source) {
		super(source);
	}

	public static byte[] toBytes(SerializableEvent event) throws IOException {
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream out = new ObjectOutputStream(bos)) {
			out.writeObject(event);
			return bos.toByteArray();
		}
	}

	public static SerializableEvent fromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
		try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
				ObjectInputStream in = new ObjectInputStream(bis)) {
			return (SerializableEvent) in.readObject();
		}
	}

}
