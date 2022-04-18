package context.input.networking.packet;

import static context.input.networking.packet.PacketPrimitive.BYTE;
import static context.input.networking.packet.PacketPrimitive.BYTE_ARRAY;
import static context.input.networking.packet.PacketPrimitive.INT;
import static context.input.networking.packet.PacketPrimitive.INT_ARRAY;
import static context.input.networking.packet.PacketPrimitive.IP_V4;
import static context.input.networking.packet.PacketPrimitive.IP_V4_ARRAY;
import static context.input.networking.packet.PacketPrimitive.IP_V6;
import static context.input.networking.packet.PacketPrimitive.LONG;
import static context.input.networking.packet.PacketPrimitive.LONG_ARRAY;
import static context.input.networking.packet.PacketPrimitive.SHORT;
import static context.input.networking.packet.PacketPrimitive.SHORT_ARRAY;
import static context.input.networking.packet.PacketPrimitive.STRING;
import static context.input.networking.packet.PacketPrimitive.STRING_ARRAY;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.ArrayDeque;
import java.util.InputMismatchException;
import java.util.Queue;

import context.input.networking.packet.address.PacketAddress;
import context.input.networking.packet.cryption.EncryptionAlgorithmType;

public class PacketBuilder {

	private Queue<PacketPrimitive> primitives;
	@SuppressWarnings("unused")
	private Queue<EncryptionAlgorithmType> encryptions;
	private Queue<Byte> bytes = new ArrayDeque<>();
	private PacketAddress dest;

	public PacketBuilder(PacketFormat format, PacketAddress dest) {
		this.dest = dest;
		primitives = format.primitives();
		encryptions = format.encryptions();
	}

	public PacketBuilder(PacketFormat format, PacketBuilder builder) {
		this.dest = builder.dest;
		this.bytes = builder.bytes;
		primitives = format.primitives();
		encryptions = format.encryptions();
	}

	private void typeValidate(PacketPrimitive actual) {
		PacketPrimitive expected = primitives.poll();
		if (expected != actual) {
			throw new InputMismatchException("Packet Builder packet creation failed: expected " + expected + ", actual " + actual);
		}
	}

	public PacketBuilder consume(long val) {
		typeValidate(LONG);
		bytes.add((byte) ((val >> 56) & 0xFF));
		bytes.add((byte) ((val >> 48) & 0xFF));
		bytes.add((byte) ((val >> 40) & 0xFF));
		bytes.add((byte) ((val >> 32) & 0xFF));
		bytes.add((byte) ((val >> 24) & 0xFF));
		bytes.add((byte) ((val >> 16) & 0xFF));
		bytes.add((byte) ((val >> 8) & 0xFF));
		bytes.add((byte) (val & 0xFF));
		return this;
	}

	public PacketBuilder consume(int val) {
		typeValidate(INT);
		bytes.add((byte) ((val >> 24) & 0xFF));
		bytes.add((byte) ((val >> 16) & 0xFF));
		bytes.add((byte) ((val >> 8) & 0xFF));
		bytes.add((byte) (val & 0xFF));
		return this;
	}

	public PacketBuilder consume(short val) {
		typeValidate(SHORT);
		bytes.add((byte) ((val >> 8) & 0xFF));
		bytes.add((byte) (val & 0xFF));
		return this;
	}

	public PacketBuilder consume(byte val) {
		typeValidate(BYTE);
		bytes.add(val);
		return this;
	}

	public PacketBuilder consume(long[] val) {
		typeValidate(LONG_ARRAY);
		bytes.add((byte) val.length);
		for (long x : val) {
			bytes.add((byte) ((x >> 56) & 0xFF));
			bytes.add((byte) ((x >> 48) & 0xFF));
			bytes.add((byte) ((x >> 40) & 0xFF));
			bytes.add((byte) ((x >> 32) & 0xFF));
			bytes.add((byte) ((x >> 24) & 0xFF));
			bytes.add((byte) ((x >> 16) & 0xFF));
			bytes.add((byte) ((x >> 8) & 0xFF));
			bytes.add((byte) (x & 0xFF));
		}
		return this;
	}

	public PacketBuilder consume(int[] val) {
		typeValidate(INT_ARRAY);
		bytes.add((byte) val.length);
		for (int x : val) {
			bytes.add((byte) ((x >> 24) & 0xFF));
			bytes.add((byte) ((x >> 16) & 0xFF));
			bytes.add((byte) ((x >> 8) & 0xFF));
			bytes.add((byte) (x & 0xFF));
		}
		return this;
	}

	public PacketBuilder consume(short[] val) {
		typeValidate(SHORT_ARRAY);
		bytes.add((byte) val.length);
		for (short x : val) {
			bytes.add((byte) ((x >> 8) & 0xFF));
			bytes.add((byte) (x & 0xFF));
		}
		return this;
	}

	public PacketBuilder consume(byte[] val) {
		typeValidate(BYTE_ARRAY);
		bytes.add((byte) val.length);
		for (byte x : val) {
			bytes.add(x);
		}
		return this;
	}

	public PacketBuilder consume(String val) {
		typeValidate(STRING);
		byte[] b = val.getBytes(UTF_8);
		short numBytes = (short) b.length;
		bytes.add((byte) ((numBytes >> 8) & 0xFF));
		bytes.add((byte) (numBytes & 0xFF));
		for (byte x : b) {
			bytes.add(x);
		}
		return this;
	}

	public PacketBuilder consume(String[] val) {
		typeValidate(STRING_ARRAY);
		bytes.add((byte) val.length);
		for (String x : val) {
			byte[] b = x.getBytes(UTF_8);
			short numBytes = (short) b.length;
			bytes.add((byte) ((numBytes >> 8) & 0xFF));
			bytes.add((byte) (numBytes & 0xFF));
			for (byte y : b) {
				bytes.add(y);
			}
		}
		return this;
	}

	public PacketBuilder consume(InetAddress val) {
		if (val instanceof Inet6Address) {
			typeValidate(IP_V6);
			byte[] address = val.getAddress();
			bytes.add(address[0]);
			bytes.add(address[1]);
			bytes.add(address[2]);
			bytes.add(address[3]);
			bytes.add(address[4]);
			bytes.add(address[5]);
		} else if (val instanceof Inet4Address) {
			typeValidate(IP_V4);
			byte[] address = val.getAddress();
			bytes.add(address[0]);
			bytes.add(address[1]);
			bytes.add(address[2]);
			bytes.add(address[3]);
		} else {
			if (val == null) {
				throw new InputMismatchException("Packet Builder consumed IP cannot be null");
			}
			throw new InputMismatchException("Packet Builder consumed unknown IP type");
		}
		return this;
	}

	public PacketBuilder consume(Inet4Address[] val) {
		typeValidate(IP_V4_ARRAY);
		bytes.add((byte) val.length);
		for (Inet4Address x : val) {
			byte[] address = x.getAddress();
			bytes.add(address[0]);
			bytes.add(address[1]);
			bytes.add(address[2]);
			bytes.add(address[3]);
		}
		return this;
	}

	public PacketBuilder consume(Inet6Address[] val) {
		typeValidate(IP_V4_ARRAY);
		bytes.add((byte) val.length);
		for (Inet6Address x : val) {
			byte[] address = x.getAddress();
			bytes.add(address[0]);
			bytes.add(address[1]);
			bytes.add(address[2]);
			bytes.add(address[3]);
			bytes.add(address[4]);
			bytes.add(address[5]);
		}
		return this;
	}

	public PacketBuilder consume(int ipv4_0, int ipv4_1, int ipv4_2, int ipv4_3) {
		typeValidate(IP_V4);
		bytes.add((byte) ipv4_0);
		bytes.add((byte) ipv4_1);
		bytes.add((byte) ipv4_2);
		bytes.add((byte) ipv4_3);
		return this;
	}

	public PacketBuilder consume(int ipv6_0, int ipv6_1, int ipv6_2, int ipv6_3, int ipv6_4, int ipv6_5) {
		typeValidate(IP_V4);
		bytes.add((byte) ipv6_0);
		bytes.add((byte) ipv6_1);
		bytes.add((byte) ipv6_2);
		bytes.add((byte) ipv6_3);
		bytes.add((byte) ipv6_4);
		bytes.add((byte) ipv6_5);
		return this;
	}

	public PacketModel build() {
		if (!primitives.isEmpty()) {
			throw new RuntimeException("Did not complete packet format");
		}
		byte[] buffer = new byte[bytes.size()];
		int i = 0;
		while (!bytes.isEmpty()) {
			buffer[i] = bytes.poll();
			i++;
		}
		// TODO
		// apply encryption
		return new PacketModel(buffer, dest);
	}

}
