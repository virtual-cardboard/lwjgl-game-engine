package context.input.networking.packet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import context.input.networking.packet.address.PacketAddress;
import context.input.networking.packet.block.PacketBlock;

public class PacketModel {

	private PacketAddress dest;
	private List<PacketBlock> blocks;

	public PacketModel(PacketAddress dest, List<PacketBlock> blocks) {
		this.dest = dest;
		this.blocks = blocks;
	}

	public PacketModel(PacketAddress dest, PacketBlock... blocks) {
		this(dest, new ArrayList<>(Arrays.asList(blocks)));
	}

	public PacketAddress dest() {
		return dest;
	}

	public List<PacketBlock> blocks() {
		return blocks;
	}

}
