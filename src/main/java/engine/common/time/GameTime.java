package engine.common.time;

public class GameTime {

	private volatile long offset;

	public GameTime() {
	}

	public GameTime(long offset) {
		this.offset = offset;
	}

	public long currentTimeMillis() {
		return System.currentTimeMillis() + offset;
	}

	public long offset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

}
