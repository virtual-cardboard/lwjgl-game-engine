package context.input.event;

import common.source.NetworkSource;
import context.input.networking.packet.PacketModel;

public class PacketReceivedInputEvent extends GameInputEvent {

	private static final long serialVersionUID = 2047153256493450347L;

	private PacketModel model;

	public PacketReceivedInputEvent(long time, NetworkSource source, PacketModel model) {
		super(time, source);
		this.model = model;
	}

	public PacketModel model() {
		return model;
	}

}
