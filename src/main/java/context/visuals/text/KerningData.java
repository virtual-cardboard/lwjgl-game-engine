package context.visuals.text;

public class KerningData implements Comparable<KerningData> {

	private int one;
	private int two;
	private int amount;

	public KerningData(int one, int two, int amount) {
		this.one = one;
		this.two = two;
		this.amount = amount;
	}

	public int getOne() {
		return one;
	}

	public int getTwo() {
		return two;
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public int compareTo(KerningData o) {
		return Integer.compare(one, o.one);
	}

}
