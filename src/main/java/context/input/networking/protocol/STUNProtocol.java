package context.input.networking.protocol;

import static context.input.networking.packet.block.PacketPrimitive.LONG;

import context.input.networking.packet.block.PacketBlockFormat;

public class STUNProtocol {

	public static final PacketBlockFormat STUN_REQUEST = new PacketBlockFormat().with(LONG, LONG); // timestamp, nonce

}
