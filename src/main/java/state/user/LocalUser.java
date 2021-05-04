package state.user;

/**
 * The local user
 * 
 * @author Lunkle
 *
 */
public class LocalUser extends User {

	public static final LocalUser LOCAL_USER = new LocalUser();

	private LocalUser() {
		super(-1, "");
	}

}
