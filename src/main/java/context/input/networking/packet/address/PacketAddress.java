package context.input.networking.packet.address;

import java.net.InetAddress;

public abstract class PacketAddress {

	public abstract InetAddress ip();

	public abstract int port();

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
		if (obj == null)
			return false;
		if (obj instanceof PacketAddress) {
			PacketAddress other = (PacketAddress) obj;
			return port() == other.port() && ip().equals(other.ip());
		}
		return false;
	}

}
