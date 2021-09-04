package context.input.networking;

import static context.input.networking.packet.PacketModel.toModel;
import static java.lang.System.currentTimeMillis;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.util.Queue;

import common.TerminateableRunnable;
import common.source.NetworkSource;
import context.input.event.PacketReceivedInputEvent;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.address.PacketAddress;

public class UDPReceiver extends TerminateableRunnable {

	private DatagramSocket socket;
	private Queue<PacketReceivedInputEvent> networkReceiveBuffer;
	private byte[] buffer = new byte[65536];
	private DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

	public UDPReceiver(DatagramSocket socket, Queue<PacketReceivedInputEvent> networkReceiveBuffer) {
		this.socket = socket;
		this.networkReceiveBuffer = networkReceiveBuffer;
	}

	@Override
	public void doRun() {
		try {
			socket.receive(packet);
			NetworkSource source = new NetworkSource(new PacketAddress((InetSocketAddress) packet.getSocketAddress()));
			PacketReceivedInputEvent event = new PacketReceivedInputEvent(currentTimeMillis(), source, toModel(packet));
			networkReceiveBuffer.add(event);
		} catch (SocketTimeoutException e) {

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
