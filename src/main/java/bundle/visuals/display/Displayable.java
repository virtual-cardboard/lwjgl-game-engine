package bundle.visuals.display;

/**
 * An interface for describing things that can be displayed.
 * <p>
 * If you want to add a {@link Displayable}, make sure you also add a
 * corresponding {@link AbstractDisplayer}. To do so, follow the steps outlined
 * below:
 * <p>
 * 1. Create a class implementing the {@link Displayable} interface. Override
 * the getDisplayerName() method to return getDefaultDisplayerName().
 * <p>
 * 2. Create a class extending the {@link AbstractDisplayer} class and place it
 * in the same package as the {@link Displayable}. You must give the class the
 * same name as the Displayable with the suffix "Displayer".
 * <p>
 * Example:
 * 
 * <pre>
 * public class Rectangle implements Displayable
 * public class RectangleDisplayer extends Displayer
 * </pre>
 * 
 * If you want to put the Displayer into a different package then you will
 * 
 * @author Jay, Lunkle
 *
 */
public interface Displayable {

	public String getDisplayerName();

	/**
	 * Gets the displayable's corresponding displayer. The corresponding displayer
	 * must be in the same package of the displayable, and must be named identically
	 * plus the suffix "Displayer".
	 * 
	 * @return the name of the corresponding displayer
	 */
	public default String getDefaultDisplayerName() {
		String name = this.getClass().getName();
		int lastDotIndex = name.lastIndexOf('.');
		return name.substring(0, lastDotIndex + 1) + this.getClass().getSimpleName() + "Displayer";
	}

}
