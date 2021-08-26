package common.source;

import context.input.networking.packet.address.PacketAddress;

public class NetworkSource implements GameSource {

	private PacketAddress address;

	public NetworkSource(PacketAddress address) {
		this.address = address;
	}

	public PacketAddress getAddress() {
		return address;
	}

	@Override
	public String getDescription() {
		return "packet from " + address.toString();
	}

}
