package context.input.networking.packet.address;

import java.net.InetAddress;

public class SelfAddress extends PacketAddress {

	public static final SelfAddress SELF_ADDRESS = new SelfAddress();

	private static InetAddress ip;
	private static int port;

	@Override
	public InetAddress ip() {
		return ip;
	}

	@Override
	public int port() {
		return port;
	}

}
