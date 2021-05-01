package state.entity;

public abstract class AbstractEntity {

	private long id;

	public AbstractEntity(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return id;
	}

}
