package common.event;

import static context.input.networking.packet.PacketPrimitive.BYTE_ARRAY;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

import common.source.GameSource;
import context.input.networking.packet.PacketFormat;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.address.PacketAddress;

public abstract class NetworkEvent extends SerializableEvent implements Serializable {

	private static final long serialVersionUID = 4203143549059106367L;

	private static final PacketFormat SERIALIZED_FORMAT = new PacketFormat().with(BYTE_ARRAY);

	public NetworkEvent(GameSource source) {
		super(source);
	}

	public NetworkEvent(long time, GameSource source) {
		super(time, source);
	}

	public static PacketModel toPacket(NetworkEvent event, PacketAddress dest) {
		byte[] bytes = null;
		try {
			bytes = toBytes(event);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SERIALIZED_FORMAT
				.builder(dest)
				.consume(bytes)
				.build();
	}

	public static NetworkEvent fromPacket(PacketModel packet) {
		byte[] bytes = SERIALIZED_FORMAT.reader(packet).readByteArray();
		System.out.println(Arrays.toString(bytes));
		NetworkEvent event = null;
		try {
			event = (NetworkEvent) fromBytes(bytes);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Packet event data is not correctly formatted");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return event;
	}

}
