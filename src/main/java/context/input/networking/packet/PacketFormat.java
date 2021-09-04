package context.input.networking.packet;

import java.util.LinkedList;
import java.util.Queue;

import context.input.networking.packet.address.PacketAddress;
import context.input.networking.packet.cryption.EncryptionAlgorithmType;

public class PacketFormat {

	private Queue<PacketPrimitive> primitives = new LinkedList<>();
	private Queue<EncryptionAlgorithmType> encryptions = new LinkedList<>();

	public PacketFormat with(PacketPrimitive... primitives) {
		for (PacketPrimitive primitive : primitives) {
			this.primitives.add(primitive);
		}
		return this;
	}

	public PacketFormat with(EncryptionAlgorithmType... algorithms) {
		for (EncryptionAlgorithmType algorithm : algorithms) {
			this.encryptions.add(algorithm);
		}
		return this;
	}

	public PacketBuilder builder(PacketAddress dest) {
		return new PacketBuilder(this, dest);
	}

	public PacketReader reader(PacketModel packet) {
		return new PacketReader(this, packet);
	}

	public Queue<PacketPrimitive> primitives() {
		return new LinkedList<>(primitives);
	}

	public Queue<EncryptionAlgorithmType> encryptions() {
		return new LinkedList<>(encryptions);
	}

}
