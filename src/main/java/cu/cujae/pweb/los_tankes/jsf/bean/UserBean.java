package cu.cujae.pweb.los_tankes.jsf.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabCloseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import cu.cujae.pweb.los_tankes.domain.Role;
import cu.cujae.pweb.los_tankes.domain.User;
import cu.cujae.pweb.los_tankes.security.util.CurrentUserUtils;
import cu.cujae.pweb.los_tankes.util.ApiRestMapper;
import cu.cujae.pweb.los_tankes.util.JsfUtils;
import cu.cujae.pweb.los_tankes.util.PrimeUtils;
import cu.cujae.pweb.los_tankes.util.RestService;

@Scope(value = "request")
@Component(value = "userBean")
@ELBeanName(value = "userBean")
public class UserBean {

	@Autowired
	private RestService restService;

	private List<User> userList;
	private User user;
	private User selectedUser;
	private List<Role> roles;
	private List<String> rolesName;

	
	private TabView tabUsers;
	private int activeIndexTab = 0;
	private String userLogged;


	public UserBean() {
		userList = new ArrayList<>();
		user = new User();
		this.selectedUser = new User();
		rolesName = new ArrayList<String>();
	}


	public List<Role> getRoles() {
		return roles;
	}


	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}


	public List<String> getRolesName() {
		return rolesName;
	}


	public void setRolesName(List<String> rolesName) {
		this.rolesName = rolesName;
	}


	@PostConstruct
	public void Init() throws IOException {
	
	}

	public void onTabClose(TabCloseEvent<?> event) {
		tabUsers.getChildren().remove(event.getTab());
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void newUser() throws IOException {
		activeIndexTab = PrimeUtils.createTab(tabUsers, JsfUtils.getStringValueFromBundle("label_newUser"), "/pages/security/createUser.xhtml", "userTabView");

	}

	public void editUser() throws IOException {
		activeIndexTab = PrimeUtils.createTab(tabUsers, JsfUtils.getStringValueFromBundle("label_editUser"), "/pages/security/editUser.xhtml", "userTabView");
	}

		
	public void save() {
		try {
			user.setRoles(new ArrayList<Role>());
			for(int i = 0; i<rolesName.size(); i++) {
				MultiValueMap<String, String> params = new LinkedMultiValueMap<>(); 	
				ApiRestMapper<Role> apiRestMapper = new ApiRestMapper<>();
				int rolId2=Integer.parseInt(rolesName.get(i).toString());
				Long rolId=(long) rolId2;
				String response2 = (String)restService.GET("/api/role/role/"+ rolId, params, String.class).getBody();
				Role r = (apiRestMapper.mapOne(response2, Role.class));				
				user.getRoles().add(r);
			}
			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			String response = (String)restService.POST("/api/v1/user", user, String.class, null).getBody();
			
			PrimeUtils.deleteSecondTab(tabUsers);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}

	}

	public void update() {
		try {
			selectedUser.setPassword(new BCryptPasswordEncoder().encode(selectedUser.getPassword()));
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			String response = (String)restService.PUT("/api/v1/user", params, selectedUser, String.class, null).getBody();
			PrimeUtils.deleteSecondTab(tabUsers);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}
	}

	public void delete() {
		try {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			String id = selectedUser.getId().toString();
			String response = (String)restService.DELETE("/api/v1/user/" + id, null, String.class).getBody();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}
	}

	public List<User> getUserList() throws IOException {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>(); 	
		ApiRestMapper<User> apiRestMapper = new ApiRestMapper<>();
		String response = (String)restService.GET("/api/v1/user", params, String.class).getBody();
		return apiRestMapper.mapList(response, User.class);
	}

	public List<Role> getRol() throws IOException {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>(); 	
		ApiRestMapper<Role> apiRestMapper = new ApiRestMapper<>();
		String response = (String)restService.GET("/api/role/role", params, String.class).getBody();
		return apiRestMapper.mapList(response, Role.class);
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public int getActiveIndexTab() {
		return activeIndexTab;
	}

	public void setActiveIndexTab(int activeIndexTab) {
		this.activeIndexTab = activeIndexTab;
	}

	public TabView getTabUsers() {
		return tabUsers;
	}

	public void setTabUsers(TabView tabUsers) {
		this.tabUsers = tabUsers;
	}

	public String getUserLogged() {
		return CurrentUserUtils.getFullName();
	}

	public void setUserLogged(String userLogged) {
		this.userLogged = userLogged;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

}
