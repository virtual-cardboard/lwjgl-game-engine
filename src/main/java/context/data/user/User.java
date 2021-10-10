package context.data.user;

import common.source.GameSource;

public class User implements GameSource {

	private String username;

	/**
	 * A user of the game with a username and id.
	 * 
	 * @param username the username
	 * @param id       the id
	 */
	public User(String username) {
		this.username = username;
	}

	@Override
	public String description() {
		return username + " ";
	}

}
