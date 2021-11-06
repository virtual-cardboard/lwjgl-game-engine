package context.visuals.colour;

import common.math.Vector4f;

/**
 * In the real world, a colour can be represented using RGBA. Each of these 4
 * values is a number between 0 and 255. In computer world, we can represent a
 * colour using an {@code int}. Each of RGBA can be represented by a byte. This
 * class provides some basic functions
 *
 * @author Lunkle
 *
 */
public class Colour {

	private Colour() {
	}

	/**
	 * Returns an integer containing the RGB values. The alpha is 255, fully opaque.
	 * 
	 * <p>
	 * 0 {@code <= r, g, b <=} 255
	 * </p>
	 * 
	 * @param r the r value
	 * @param g the g value
	 * @param b the b value
	 * @return An integer that contains all of the RGB values.
	 */
	public static int rgb(int r, int g, int b) {
		return rgb(r, g, b, 255);
	}

	/**
	 * Returns an integer containing the RGBA values.
	 * 
	 * <p>
	 * 0 {@code <= r, g, b, a <=} 255
	 * </p>
	 * 
	 * @param r the r value
	 * @param g the g value
	 * @param b the b value
	 * @param a the alpha value
	 * @return An integer that contains all of the RGBA values.
	 */
	public static int rgb(int r, int g, int b, int a) {
		return (r << 24) + (g << 16) + (b << 8) + (a);
	}

	/**
	 * Extracts the {@code r} value from an integer that represents a colour.
	 * 
	 * @param colour the colour
	 * @return The {@code r} value of the colour.
	 * @see {@link #rgb(int, int, int, int) colour(r, g, b, a)}
	 */
	public static int r(int colour) {
		return 255 & (colour >> 24);
	}

	/**
	 * Extracts the {@code g} value from an integer that represents a colour.
	 * 
	 * @param colour the colour
	 * @return The {@code g} value of the colour.
	 * @see {@link #rgb(int, int, int, int) colour(r, g, b, a)}
	 */
	public static int g(int colour) {
		return 255 & (colour >> 16);
	}

	/**
	 * Extracts the {@code b} value from an integer that represents a colour.
	 * 
	 * @param colour the colour
	 * @return The {@code b} value of the colour.
	 * @see {@link #rgb(int, int, int, int) colour(r, g, b, a)}
	 */
	public static int b(int colour) {
		return 255 & (colour >> 8);
	}

	/**
	 * Extracts the {@code alpha} value from an integer that represents a colour.
	 * 
	 * @param colour the colour
	 * @return The {@code alpha} value of the colour.
	 * @see {@link #rgb(int, int, int, int) colour(r, g, b, a)}
	 */
	public static int a(int colour) {
		return 255 & (colour);
	}

	public static float normalizedR(int colour) {
		return r(colour) / 255f;
	}

	public static float normalizedG(int colour) {
		return g(colour) / 255f;
	}

	public static float normalizedB(int colour) {
		return b(colour) / 255f;
	}

	public static float normalizedA(int colour) {
		return a(colour) / 255f;
	}

	public static Vector4f toVector(int colour) {
		return new Vector4f(r(colour), g(colour), b(colour), a(colour));
	}

}
