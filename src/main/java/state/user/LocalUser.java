package state.user;

/**
 * The local user
 * 
 * @author Lunkle
 *
 */
public class LocalUser extends User {

	public static final LocalUser localUser = new LocalUser();

	private LocalUser() {
		super(-1, "");
	}

}
