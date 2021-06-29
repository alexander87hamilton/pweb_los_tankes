package cu.cujae.pweb.los_tankes.security.util;

import org.springframework.security.core.context.SecurityContextHolder;

import cu.cujae.pweb.los_tankes.security.UserDetailsImpl;

public class CurrentUserUtils {
	
	public CurrentUserUtils() {
		super();
	}

	private static final String ANONYMOUS_USER = "anonymousUser";
	
	public static String getUsername() {
		String username = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			username = SecurityContextHolder.getContext().getAuthentication().getName();
		}
		return username;
	}
	
	public static String getLocale() {
		String locale = null;
		if (isLogged()) {
			locale = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getLocale();
		}
		return locale;
	}
	
	public static String getFullName() {
		String fullName = null;
		if (isLogged()) {
			fullName = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getFullName();
		}
		return fullName;
	}
	
	
	public static boolean isLogged() {
		boolean logged = false;
		if ((SecurityContextHolder.getContext().getAuthentication() != null) && (!getUsername().equals(ANONYMOUS_USER))) {
			logged = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
		}
		return logged;
	}
	
	public static String getPassword() {
		String password = null;
		if (isLogged()) {
			password = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
		}
		return password;
	}
	
}
