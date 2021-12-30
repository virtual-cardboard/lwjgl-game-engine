package context.input.event;

import common.source.NetworkSource;
import context.input.networking.packet.PacketModel;

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
