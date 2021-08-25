package context.input.networking.packet;

import static java.util.Arrays.asList;

import java.util.List;

import context.input.networking.packet.block.PacketBlock;
import context.input.networking.packet.destination.PacketDestination;

public class PacketModel {

	private PacketDestination dest;
	private List<PacketBlock> blocks;

	public PacketModel(PacketDestination dest, List<PacketBlock> blocks) {
		this.dest = dest;
		this.blocks = blocks;
	}

	public PacketModel(PacketDestination dest, PacketBlock... blocks) {
		this(dest, asList(blocks));
	}

	public PacketDestination dest() {
		return dest;
	}

	public List<PacketBlock> blocks() {
		return blocks;
	}

}
