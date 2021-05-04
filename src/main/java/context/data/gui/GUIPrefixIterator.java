package context.data.gui;

import java.util.Iterator;
import java.util.Stack;

public class GUIPrefixIterator implements Iterator<Gui> {

	private Stack<Gui> guis = new Stack<>();
	private Stack<Integer> counters = new Stack<>();

	public GUIPrefixIterator(Gui gui) {
		guis.add(gui);
		counters.add(-1);
	}

	@Override
	public boolean hasNext() {
		return !guis.isEmpty();
	}

	/**
	 * Returns the current data and updates the pointer
	 */
	@Override
	public Gui next() {
		Gui gui = guis.peek();
		Integer counter = counters.peek();
		Gui returnGui = counter == -1 ? gui : gui.getChildrenArray().get(counter);
		if (counter + 1 == gui.getNumChildren()) {
			guis.pop();
			counters.pop();
		} else {
			counters.add(counters.pop() + 1);
		}
		if (counter != -1) {
			guis.add(returnGui);
			counters.add(-1);
		}
		return returnGui;
	}

}
