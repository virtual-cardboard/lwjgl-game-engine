package context.input.networking;

import static java.lang.System.currentTimeMillis;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.util.Queue;
import java.util.zip.DataFormatException;

import common.source.NetworkSource;
import context.input.event.GameInputEvent;
import context.input.event.PacketReceivedInputEvent;
import context.input.networking.packet.PacketReader;

public class UDPReceiver implements Runnable {

	private DatagramSocket socket;
	private Queue<GameInputEvent> inputBuffer;
	private byte[] buffer = new byte[65536];
	private DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

	private boolean isDone = false;

	public UDPReceiver(DatagramSocket socket, Queue<GameInputEvent> inputBuffer) {
		this.socket = socket;
		this.inputBuffer = inputBuffer;
	}

	@Override
	public void run() {
		while (!isDone) {
			try {
				socket.receive(packet);
				PacketReader packetReader = new PacketReader(packet);
				NetworkSource source = new NetworkSource(packetReader.address());
				GameInputEvent event = new PacketReceivedInputEvent(currentTimeMillis(), source, packetReader.model());
				inputBuffer.add(event);
			} catch (SocketTimeoutException e) {

			} catch (IOException e) {
				e.printStackTrace();
				e.printStackTrace();
			} catch (DataFormatException e) {
			}
			Thread.yield();
		}
	}

}
