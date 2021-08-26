package context.input.networking.packet.block;

import java.util.LinkedList;
import java.util.Queue;

import context.input.networking.packet.cryption.EncryptionAlgorithmType;

public class PacketBlockFormat {

	private Queue<PacketPrimitive> primitives = new LinkedList<>();
	private Queue<EncryptionAlgorithmType> encryptions = new LinkedList<>();

	public PacketBlockFormat with(PacketPrimitive... primitives) {
		for (PacketPrimitive primitive : primitives) {
			this.primitives.add(primitive);
		}
		return this;
	}

	public PacketBlockFormat with(EncryptionAlgorithmType... algorithms) {
		for (EncryptionAlgorithmType algorithm : algorithms) {
			this.encryptions.add(algorithm);
		}
		return this;
	}

	public PacketBlockBuilder builder() {
		return new PacketBlockBuilder(this);
	}

	public PacketBlockReader reader(PacketBlock block) {
		return new PacketBlockReader(this, block);
	}

	public Queue<PacketPrimitive> primitives() {
		return new LinkedList<>(primitives);
	}

	public Queue<EncryptionAlgorithmType> encryptions() {
		return new LinkedList<>(encryptions);
	}

}
