package common.source;

import context.input.networking.packet.address.PacketAddress;

public class NetworkSource implements GameSource {

	private PacketAddress source;

	public NetworkSource(PacketAddress source) {
		this.source = source;
	}

	@Override
	public String getDescription() {
		return "packet from " + source.toString();
	}

}
