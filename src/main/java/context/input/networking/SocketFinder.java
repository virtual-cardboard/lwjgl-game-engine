package context.input.networking;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

public class SocketFinder {

	public static DatagramSocket findSocket() throws IOException {
		DatagramSocket socket = null;
		int port = 44999;
		while (socket == null) {
			if (port < 8080) {
				throw new IOException("All ports in range 8081-44999 in use");
			}
			try {
				socket = new DatagramSocket(port);
				socket.setSoTimeout(10000);
			} catch (SocketException e) {
				e.printStackTrace();
			}
			port--;
		}
		System.out.println("Started UDP socket on port " + port);
		return socket;
	}

}
