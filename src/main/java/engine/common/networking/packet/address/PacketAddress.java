package engine.common.networking.packet.address;

import static engine.common.networking.packet.NetworkingSerializationFormats.PACKET_ADDRESS;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import derealizer.SerializationReader;
import derealizer.SerializationWriter;
import derealizer.format.Derealizable;
import engine.common.networking.packet.NetworkingSerializationFormats;

public class PacketAddress implements Derealizable {

	private InetAddress ip;
	private short port;

	public PacketAddress() {
	}

	public PacketAddress(InetAddress ip, int port) {
		this.ip = ip;
		this.port = (short) port;
	}

	public PacketAddress(InetSocketAddress address) {
		this(address.getAddress(), (short) address.getPort());
	}

	public PacketAddress(byte[] bytes) {
		read(new SerializationReader(bytes));
	}

	@Override
	public NetworkingSerializationFormats formatEnum() {
		return PACKET_ADDRESS;
	}

	@Override
	public void read(SerializationReader reader) {
		try {
			this.ip = InetAddress.getByAddress(new byte[] { reader.readByte(), reader.readByte(), reader.readByte(), reader.readByte() });
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.port = reader.readShort();
	}

	@Override
	public void write(SerializationWriter writer) {
		for (byte b : ip.getAddress()) {
			writer.consume(b);
		}
		writer.consume(port);
	}

	public InetAddress ip() {
		return ip;
	}

	public int port() {
		return port & 0xFFFF;
	}

	@Override
	public String toString() {
		return ip().toString() + ":" + port();
	}

	public static boolean match(PacketAddress a1, PacketAddress a2) {
		return a1.port() == a2.port() && a1.ip().equals(a2.ip());
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
