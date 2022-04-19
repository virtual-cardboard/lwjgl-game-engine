package context.input.networking.packet;

import java.net.URL;
import java.util.ArrayDeque;
import java.util.Queue;

import context.input.networking.packet.cryption.EncryptionAlgorithmType;

public class HttpRequestBuilder {


	private Queue<PacketPrimitive> primitives;
	@SuppressWarnings("unused")
	private Queue<EncryptionAlgorithmType> encryptions;
	private Queue<Byte> bytes = new ArrayDeque<>();
	private URL url;

	public HttpRequestBuilder(PacketFormat format, URL url) {
		primitives = format.primitives();
		encryptions = format.encryptions();
		this.url = url;
	}

	public HttpRequestBuilder consume(long l) {
		return this;
	}

//	public HR

}
