package context.visuals.colour;

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
	public static int colour(int r, int g, int b) {
		return colour(r, g, b, 255);
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
	public static int colour(int r, int g, int b, int a) {
		return (r << 24) + (g << 16) + (b << 8) + (a);
	}

	/**
	 * Extracts the {@code r} value from an integer that represents a colour.
	 * 
	 * @param colour the colour
	 * @return The {@code r} value of the colour.
	 * @see {@link #colour(int, int, int, int) colour(r, g, b, a)}
	 */
	public static int r(int colour) {
		return 255 & (colour >> 24);
	}

	/**
	 * Extracts the {@code g} value from an integer that represents a colour.
	 * 
	 * @param colour the colour
	 * @return The {@code g} value of the colour.
	 * @see {@link #colour(int, int, int, int) colour(r, g, b, a)}
	 */
	public static int g(int colour) {
		return 255 & (colour >> 16);
	}

	/**
	 * Extracts the {@code b} value from an integer that represents a colour.
	 * 
	 * @param colour the colour
	 * @return The {@code b} value of the colour.
	 * @see {@link #colour(int, int, int, int) colour(r, g, b, a)}
	 */
	public static int b(int colour) {
		return 255 & (colour >> 8);
	}

	/**
	 * Extracts the {@code alpha} value from an integer that represents a colour.
	 * 
	 * @param colour the colour
	 * @return The {@code alpha} value of the colour.
	 * @see {@link #colour(int, int, int, int) colour(r, g, b, a)}
	 */
	public static int a(int colour) {
		return 255 & (colour);
	}

}
