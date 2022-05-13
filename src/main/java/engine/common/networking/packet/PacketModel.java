package engine.common.networking.packet;

import static java.util.Arrays.copyOf;

import java.net.DatagramPacket;

import engine.common.networking.packet.address.PacketAddress;
import engine.common.networking.packet.address.SelfAddress;

public class PacketModel {

	private final PacketAddress dest;
	private final byte[] bytes;

	public PacketModel(byte[] bytes, PacketAddress dest) {
		this.dest = dest;
		this.bytes = bytes;
	}

	public PacketAddress dest() {
		return dest;
	}

	public byte[] bytes() {
		return bytes;
	}

	public static DatagramPacket toPacket(PacketModel model) {
		return new DatagramPacket(model.bytes, model.bytes.length, model.dest.ip(), model.dest.port());
	}

	public static PacketModel toModel(DatagramPacket packet) {
		return new PacketModel(copyOf(packet.getData(), packet.getLength()), SelfAddress.SELF_ADDRESS);
	}

}
