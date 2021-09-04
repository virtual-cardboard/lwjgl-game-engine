package context.input.networking.packet.address;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class PacketAddress {

	private InetAddress ip;
	private int port;

	protected PacketAddress() {
	}

	public PacketAddress(InetAddress ip, int port) {
		this.ip = ip;
		this.port = port & 0xFFFF;
	}

	public PacketAddress(InetSocketAddress address) {
		this(address.getAddress(), address.getPort());
	}

	public InetAddress ip() {
		return ip;
	}

	public int port() {
		return port;
	}

	@Override
	public String toString() {
		return ip().toString() + ":" + port();
	}

	public static boolean match(PacketAddress a1, PacketAddress a2) {
		return a1.port() == a2.port() && a1.ip().equals(a2.ip());
	}

	public short shortPort() {
		return (short) port();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof PacketAddress) {
			PacketAddress other = (PacketAddress) obj;
			return port() == other.port() && ip().equals(other.ip());
		}
		return false;
	}

}
