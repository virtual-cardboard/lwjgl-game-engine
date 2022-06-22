package context.input.networking;

import static engine.common.networking.packet.PacketModel.toModel;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.util.concurrent.ArrayBlockingQueue;

import context.input.event.PacketReceivedInputEvent;
import engine.common.TerminateableRunnable;
import engine.common.networking.packet.address.PacketAddress;
import engine.common.source.NetworkSource;

public class UDPReceiver extends TerminateableRunnable {

	private DatagramSocket socket;
	private ArrayBlockingQueue<PacketReceivedInputEvent> networkReceiveBuffer;
	private byte[] buffer = new byte[65536];
	private DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

	public UDPReceiver(DatagramSocket socket, ArrayBlockingQueue<PacketReceivedInputEvent> networkReceiveBuffer) {
		this.socket = socket;
		this.networkReceiveBuffer = networkReceiveBuffer;
	}

	@Override
	public void doRun() {
		try {
			socket.receive(packet);
			NetworkSource source = new NetworkSource(new PacketAddress((InetSocketAddress) packet.getSocketAddress()));
			PacketReceivedInputEvent event = new PacketReceivedInputEvent(source, toModel(packet));
			networkReceiveBuffer.put(event);
		} catch (SocketTimeoutException e) {
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
