package cu.cujae.pweb.los_tankes.domain;

import java.util.List;

public class User {
	
	private Long id;
	private String username;
	private String fullname;
	private String email;
	private String password;
	private List<Role> roles;
	
	public User() {}
	
	public User(Long id, String username, String fullname, String email, String password) {
		this.id = id;
		this.username = username;
		this.fullname = fullname;
		this.email = email;
		this.password = password;
	}
	
	public User(Long id, String username, String fullname, String email, List<Role> roles, String password) {
		this.id = id;
		this.setUsername(username);
		this.setfullname(fullname);
		this.email = email;
		this.roles = roles;
		this.password = password;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getfullname() {
		return fullname;
	}

	public void setfullname(String fullname) {
		this.fullname = fullname;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	
}
