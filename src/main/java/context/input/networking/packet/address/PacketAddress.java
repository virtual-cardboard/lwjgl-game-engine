package context.input.networking.packet.address;

import java.net.DatagramPacket;
import java.net.InetAddress;

public abstract class PacketAddress {

	public abstract InetAddress ip();

	public abstract int port();

	@Override
	public String toString() {
		return ip().toString() + ":" + port();
	}

	public static boolean match(DatagramPacket packet, PacketAddress address) {
		return packet.getAddress().equals(address.ip()) && packet.getPort() == address.port();
	}

}
