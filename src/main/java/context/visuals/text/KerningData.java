package context.visuals.text;

public class KerningData implements Comparable<KerningData> {

	private char one;
	private char two;
	private short amount;

	public KerningData(char one, char two, short amount) {
		this.one = one;
		this.two = two;
		this.amount = amount;
	}

	public char getOne() {
		return one;
	}

	public char getTwo() {
		return two;
	}

	public short getAmount() {
		return amount;
	}

	@Override
	public int compareTo(KerningData o) {
		return Integer.compare(one, o.one);
	}

}
