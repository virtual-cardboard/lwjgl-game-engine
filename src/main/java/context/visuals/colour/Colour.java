package context.visuals.colour;

/**
 * In the real world, a colour can be represented using RGBA. Each of these 4
 * values is a number between 0 and 255. In computer world, we can represent a
 * colour using an int. Each of RGBA can be represented by a byte. This class
 * provides some basic functions
 *
 * @author Lunkle
 *
 */
public class Colour {

	public static void main(String[] args) {
		int c = colour(255, 255, 255, 255);
		print(c);
		System.out.print(r(c) + ":\t");
		print(r(c));
		System.out.print(g(c) + ":\t");
		print(g(c));
		System.out.print(b(c) + ":\t");
		print(b(c));
		System.out.print(a(c) + ":\t");
		print(a(c));
	}

	private Colour() {
	}

	private static void print(int i) {
		System.out.println(String.format("%32s", Integer.toBinaryString(i)).replaceAll(" ", "0"));
	}

	public static int colour(int r, int g, int b, int a) {
		return (r << 24) + (g << 16) + (b << 8) + (a);
	}

	public static int r(int colour) {
		return 255 & (colour >> 24);
	}

	public static int g(int colour) {
		return 255 & (colour >> 16);
	}

	public static int b(int colour) {
		return 255 & (colour >> 8);
	}

	public static int a(int colour) {
		return 255 & (colour);
	}

}
