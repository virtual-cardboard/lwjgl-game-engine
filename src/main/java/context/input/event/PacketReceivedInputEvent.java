package context.input.event;

import engine.common.networking.packet.PacketModel;
import engine.common.source.NetworkSource;

public class PacketReceivedInputEvent extends GameInputEvent {

	private NetworkSource source;
	private PacketModel model;

	public PacketReceivedInputEvent(long time, NetworkSource source, PacketModel model) {
		this.source = source;
		this.model = model;
	}

	public NetworkSource source() {
		return source;
	}

	public PacketModel model() {
		return model;
	}

}
