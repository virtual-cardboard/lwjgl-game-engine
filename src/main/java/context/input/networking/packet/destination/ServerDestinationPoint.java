package context.input.networking.packet.destination;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerDestinationPoint extends PacketDestination {

	public static final ServerDestinationPoint SERVER_DEST = new ServerDestinationPoint();

	private static final InetAddress SERVER_IP;
	private static final int SERVER_PORT = 45000;

	static {
		InetAddress serverDest = null;
		try {
			serverDest = InetAddress.getByName("72.140.156.47");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		SERVER_IP = serverDest;
	}

	@Override
	public InetAddress ip() {
		return SERVER_IP;
	}

	@Override
	public int port() {
		return SERVER_PORT;
	}

}
