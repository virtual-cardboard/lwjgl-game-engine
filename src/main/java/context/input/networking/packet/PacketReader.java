package context.input.networking.packet;

import static context.input.networking.packet.address.SelfAddress.SELF_DEST;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import context.input.networking.packet.address.PacketAddress;
import context.input.networking.packet.address.PeerAddress;
import context.input.networking.packet.block.PacketBlock;

public class PacketReader {

	private DatagramPacket packet;

	public PacketReader(DatagramPacket packet) {
		this.packet = packet;
	}

	public PacketModel model() throws DataFormatException {
		int index = 0;
		int length = packet.getLength();
		byte[] buffer = packet.getData();
		System.out.println("Length: " + length);
		for (int i = 0; i < length; i++) {
			System.out.print(buffer[i] + ", ");
		}
		System.out.println();
		List<PacketBlock> blocks = new ArrayList<>();
		while (index < length) {
			short size = (short) (((buffer[index] & 0xFF) << 8) | (buffer[index + 1] & 0xFF));
			if (index + size > length) {
				throw new DataFormatException("Unreadable packet format");
			}
			byte[] bytes = new byte[size];
			System.arraycopy(buffer, index + 2, bytes, 0, size);
			index = index + 2 + size;
			blocks.add(new PacketBlock(bytes));
		}
		return new PacketModel(SELF_DEST, blocks);
	}

	public PacketAddress address() {
		return new PeerAddress(packet.getAddress(), packet.getPort());
	}

}
