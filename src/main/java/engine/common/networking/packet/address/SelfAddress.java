package engine.common.networking.packet.address;

import java.net.InetAddress;

public class SelfAddress extends PacketAddress {

	public static final SelfAddress SELF_ADDRESS = new SelfAddress();

	private static InetAddress ip;
	private static short port;

	@Override
	public InetAddress ip() {
		return ip;
	}

	@Override
	public short port() {
		return port;
	}

}
