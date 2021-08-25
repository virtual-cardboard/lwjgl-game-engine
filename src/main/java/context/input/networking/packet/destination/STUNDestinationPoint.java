package context.input.networking.packet.destination;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class STUNDestinationPoint extends PacketDestination {

	public static final STUNDestinationPoint STUN_DEST = new STUNDestinationPoint();

	private static final InetAddress STUN_IP;
	private static final int STUN_PORT = 45002;

	static {
		InetAddress serverDest = null;
		try {
			serverDest = InetAddress.getByName("72.140.156.47");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		STUN_IP = serverDest;
	}

	@Override
	public InetAddress ip() {
		return STUN_IP;
	}

	@Override
	public int port() {
		return STUN_PORT;
	}

}
