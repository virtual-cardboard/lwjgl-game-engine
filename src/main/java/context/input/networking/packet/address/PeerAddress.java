package context.input.networking.packet.address;

import java.net.InetAddress;

public class PeerAddress extends PacketAddress {

	private InetAddress ip;
	private int port;

	public PeerAddress(InetAddress ip, int port) {
		this.ip = ip;
		this.port = port & 0xFFFF;
	}

	@Override
	public InetAddress ip() {
		return ip;
	}

	@Override
	public int port() {
		return port;
	}

}
