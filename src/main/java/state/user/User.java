package state.user;

import state.entity.AbstractEntity;
import state.source.GameSource;

public class User extends AbstractEntity implements GameSource {

	private String username;

	/**
	 * A user of the game with a username and id.
	 * 
	 * @param username the username
	 * @param id       the id
	 */
	public User(int id, String username) {
		super(id);
		this.username = username;
	}

	@Override
	public String getDescription() {
		return username + " ";
	}

}
