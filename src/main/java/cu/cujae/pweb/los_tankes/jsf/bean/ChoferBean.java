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
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import cu.cujae.pweb.los_tankes.domain.Chofer;
import cu.cujae.pweb.los_tankes.domain.Pais;
import cu.cujae.pweb.los_tankes.util.ApiRestMapper;
import cu.cujae.pweb.los_tankes.util.JsfUtils;
import cu.cujae.pweb.los_tankes.util.PrimeUtils;
import cu.cujae.pweb.los_tankes.util.RestService;

@Scope(value = "request")
@Component(value = "choferBean")
@ELBeanName(value = "choferBean")
public class ChoferBean {

	@Autowired
	private RestService restService;

	private List<Chofer> choferList;
	private Chofer chofer;
	private Chofer selectedChofer;


	private TabView tabChofers;
	private int activeIndexTab = 0;


	public ChoferBean() {
		choferList = new ArrayList<>();
		chofer = new Chofer();
		selectedChofer = new Chofer();
	}

	@PostConstruct
	public void Init() throws IOException {

	}

	public void onTabClose(TabCloseEvent<?> event) {
		tabChofers.getChildren().remove(event.getTab());
	}

	public Chofer getChofer() {
		return chofer;
	}

	public void setChofer(Chofer chofer) {
		this.chofer = chofer;
	}

	public void newChofer() throws IOException {
		activeIndexTab = PrimeUtils.createTab(tabChofers, JsfUtils.getStringValueFromBundle("label_newChofer"), "/pages/classes/driver/createDriver.xhtml", "choferTabView");

	}

	public void editChofer() throws IOException {
		activeIndexTab = PrimeUtils.createTab(tabChofers, JsfUtils.getStringValueFromBundle("label_editChofer"), "/pages/classes/driver/editDriver.xhtml", "choferTabView");
	}

	
	public void save() {
		try {

			String response = (String)restService.POST("/api/v6/chofer", chofer, String.class, null).getBody();
			PrimeUtils.deleteSecondTab(tabChofers);
			if(response.equalsIgnoreCase("500 null")) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", JsfUtils.getStringValueFromBundle("label_keyDuplicate")));
			}else
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}

	}

	public void update() {
		try {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			String response = (String)restService.PUT("/api/v6/chofer", params, selectedChofer, String.class, null).getBody();
			PrimeUtils.deleteSecondTab(tabChofers);
			if(response.equalsIgnoreCase("500 null")) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", JsfUtils.getStringValueFromBundle("label_keyDuplicate")));
			}else
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}
	}

	public void delete() {
		try {
			String id = selectedChofer.getId().toString();
			String response = (String)restService.DELETE("/api/v6/chofer/"+ id, null, String.class).getBody();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}
	}

	public List<Chofer> getChoferList() throws IOException {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ApiRestMapper<Chofer> apiRestMapper = new ApiRestMapper<>();
		String response = (String)restService.GET("/api/v6/chofer", params, String.class).getBody();
		return apiRestMapper.mapList(response, Chofer.class);
	}

	public List<Chofer> completeChofer (String query) throws IOException {
		List<Chofer> allChoferes = getChoferList();
		List<Chofer> filteredChoferes = new ArrayList<Chofer>();

		for (int i = 0; i < allChoferes.size(); i++) {
			Chofer ch = allChoferes.get(i);
			if(ch.getNombre().toLowerCase().startsWith(query)) {
				filteredChoferes.add(ch);
			}
		}

		return filteredChoferes;
	}


	public void setChoferList(List<Chofer> choferList) {
		this.choferList = choferList;
	}


	public int getActiveIndexTab() {
		return activeIndexTab;
	}

	public void setActiveIndexTab(int activeIndexTab) {
		this.activeIndexTab = activeIndexTab;
	}

	public TabView getTabChofers() {
		return tabChofers;
	}

	public void setTabChofers(TabView tabChofers) {
		this.tabChofers = tabChofers;
	}

	public Chofer getSelectedChofer() {
		return selectedChofer;
	}

	public void setSelectedChofer(Chofer selectedChofer) {
		this.selectedChofer = selectedChofer;
	}

}
