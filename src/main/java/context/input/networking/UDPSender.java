package context.input.networking;

import java.io.IOException;
import java.net.DatagramSocket;
import java.util.Queue;

import context.input.networking.packet.PacketModel;
import engine.common.TerminateableRunnable;

public class UDPSender extends TerminateableRunnable {

	private DatagramSocket socket;
	private Queue<PacketModel> networkQueue;

	public UDPSender(DatagramSocket socket, Queue<PacketModel> networkQueue) {
		this.socket = socket;
		this.networkQueue = networkQueue;
	}

	public void sendAsync(PacketModel packet) {
		networkQueue.add(packet);
	}

	@Override
	public void doRun() {
		while (!networkQueue.isEmpty()) {
			try {
				socket.send(PacketModel.toPacket(networkQueue.poll()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
