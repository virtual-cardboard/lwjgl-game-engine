package common.entity;

import common.source.GameSource;

public class User extends AbstractEntity implements GameSource {

	String username;

	/**
	 * A user of the game with a username and id.
	 * 
	 * @param username the username
	 * @param id       the id
	 */
	public User(String username, int id) {
		super(id);
		this.username = username;
	}

	@Override
	public String getDescription() {
		return username + " ";
	}

}
