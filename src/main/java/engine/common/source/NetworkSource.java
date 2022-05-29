package engine.common.source;

import engine.common.networking.packet.address.PacketAddress;

public class NetworkSource {

	private PacketAddress address;

	public NetworkSource(PacketAddress address) {
		this.address = address;
	}

	public PacketAddress address() {
		return address;
	}

	public String description() {
		return "packet from " + address.toString();
	}

}
