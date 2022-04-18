package context.input.networking.packet;

import java.util.LinkedList;
import java.util.Queue;

import context.input.networking.packet.address.PacketAddress;
import context.input.networking.packet.cryption.EncryptionAlgorithmType;

public class SerializationFormat {

	private Queue<PacketPrimitive> primitives = new LinkedList<>();
	private Queue<EncryptionAlgorithmType> encryptions = new LinkedList<>();

	public SerializationFormat with(PacketPrimitive... primitives) {
		for (PacketPrimitive primitive : primitives) {
			this.primitives.add(primitive);
		}
		return this;
	}

	public SerializationFormat with(EncryptionAlgorithmType... algorithms) {
		for (EncryptionAlgorithmType algorithm : algorithms) {
			this.encryptions.add(algorithm);
		}
		return this;
	}

	public PacketBuilder builder(PacketAddress dest) {
		return new PacketBuilder(this, dest);
	}

	public PacketBuilder builder(PacketBuilder builder) {
		return new PacketBuilder(this, builder);
	}

	public PacketReader reader(PacketModel packet) {
		return new PacketReader(this, packet);
	}

	public PacketReader reader(PacketReader reader) {
		return new PacketReader(this, reader);
	}

	public Queue<PacketPrimitive> primitives() {
		return new LinkedList<>(primitives);
	}

	public Queue<EncryptionAlgorithmType> encryptions() {
		return new LinkedList<>(encryptions);
	}

}
