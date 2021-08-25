package context.input.networking.protocol;

import static context.input.networking.packet.block.PacketPrimitive.IP_V4;
import static context.input.networking.packet.block.PacketPrimitive.LONG;
import static context.input.networking.packet.block.PacketPrimitive.SHORT;

import context.input.networking.packet.block.PacketBlockFormat;

public class BootstrapProtocol {

	public static final PacketBlockFormat BOOTSTRAP_REQUEST = new PacketBlockFormat().with(LONG, IP_V4, SHORT, IP_V4, SHORT);
	public static final PacketBlockFormat BOOTSTRAP_RESPONSE = new PacketBlockFormat().with(IP_V4, SHORT, IP_V4, SHORT);
	public static final PacketBlockFormat PEER_CONNECT_REQUEST = new PacketBlockFormat().with(IP_V4, SHORT, IP_V4, SHORT);
	public static final PacketBlockFormat PEER_CONNECT_ACK = new PacketBlockFormat().with(IP_V4, SHORT, IP_V4, SHORT);

}
