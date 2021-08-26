package context.input.event;

import common.source.NetworkSource;
import context.input.networking.packet.PacketModel;

public class PacketReceivedInputEvent extends GameInputEvent {

	private PacketModel model;

	public PacketReceivedInputEvent(long time, NetworkSource source, PacketModel model) {
		super(time, source);
		this.model = model;
	}

	public PacketModel getModel() {
		return model;
	}

}