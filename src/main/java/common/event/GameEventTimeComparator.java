package common.event;

import java.util.Comparator;

public class GameEventTimeComparator implements Comparator<AbstractGameEvent> {

	@Override
	public int compare(AbstractGameEvent e1, AbstractGameEvent e2) {
		if (e1.getTime() < e2.getTime()) {
			return -1;
		} else if (e1.getTime() > e2.getTime()) {
			return 1;
		}
		return 0;
	}

}
