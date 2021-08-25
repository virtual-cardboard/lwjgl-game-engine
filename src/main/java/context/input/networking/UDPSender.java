package context.input.networking;

import java.io.IOException;
import java.net.DatagramSocket;
import java.util.Queue;

import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketModel;

public class UDPSender implements Runnable {

	private DatagramSocket socket;
	private byte[] buffer = new byte[65536];
	private boolean isDone = false;
	private Queue<PacketModel> networkQueue;

	public UDPSender(DatagramSocket socket, Queue<PacketModel> networkQueue) {
		this.socket = socket;
		this.networkQueue = networkQueue;
	}

	public void sendAsync(PacketModel packet) {
		networkQueue.add(packet);
	}

	@Override
	public void run() {
		while (!isDone) {
			while (!networkQueue.isEmpty()) {
				try {
					socket.send(new PacketBuilder(networkQueue.poll(), buffer).packet());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			Thread.yield();
		}
	}

}
