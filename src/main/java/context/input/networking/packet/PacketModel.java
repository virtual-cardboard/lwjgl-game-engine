package context.input.networking.packet;

import static context.input.networking.packet.address.SelfAddress.SELF_ADDRESS;
import static java.util.Arrays.copyOf;

import java.net.DatagramPacket;

import context.input.networking.packet.address.PacketAddress;

public class PacketModel {

	private PacketAddress dest;
	private byte[] bytes;

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
		DatagramPacket datagramPacket = new DatagramPacket(model.bytes, model.bytes.length, model.dest.ip(), model.dest.port());
		return datagramPacket;
	}

	public static PacketModel toModel(DatagramPacket packet) {
		return new PacketModel(copyOf(packet.getData(), packet.getLength()), SELF_ADDRESS);
	}

}
