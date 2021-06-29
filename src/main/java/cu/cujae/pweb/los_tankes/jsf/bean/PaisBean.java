package cu.cujae.pweb.los_tankes.jsf.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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

import cu.cujae.pweb.los_tankes.domain.Marca;
import cu.cujae.pweb.los_tankes.domain.Pais;
import cu.cujae.pweb.los_tankes.util.ApiRestMapper;
import cu.cujae.pweb.los_tankes.util.JsfUtils;
import cu.cujae.pweb.los_tankes.util.PrimeUtils;
import cu.cujae.pweb.los_tankes.util.RestService;

@Scope(value = "request")
@Component(value = "paisBean")
@ELBeanName(value = "paisBean")
public class PaisBean {

	@Autowired
	private RestService restService;

	private List<Pais> paisList;
	private Pais pais;
	private Pais selectedPais;


	private TabView tabPaiss;
	private int activeIndexTab = 0;


	public PaisBean() {
		paisList = new ArrayList<>();
		pais = new Pais();
		selectedPais = new Pais();
	}

	@PostConstruct
	public void Init() throws IOException {

	}

	public void onTabClose(TabCloseEvent<?> event) {
		tabPaiss.getChildren().remove(event.getTab());
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public void newPais() throws IOException {
		activeIndexTab = PrimeUtils.createTab(tabPaiss, JsfUtils.getStringValueFromBundle("label_newPais"), "/pages/classes/country/createCountry.xhtml", "paisTabView");
	}

	public void editPais() throws IOException {
		activeIndexTab = PrimeUtils.createTab(tabPaiss, JsfUtils.getStringValueFromBundle("label_editPais"), "/pages/classes/country/editCountry.xhtml", "paisTabView");
	}

		
	public void save() {
		try {
			if(validateField(pais)) {
				PrimeUtils.deleteSecondTab(tabPaiss);				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", JsfUtils.getStringValueFromBundle("label_ErrorLabelPaisNombre")));
			}else {
				String response = (String)restService.POST("/api/v2/pais", pais, String.class, null).getBody();
				PrimeUtils.deleteSecondTab(tabPaiss);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
			}
		} catch (Exception e) {			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}

	}

	public void update() {
		try {
			if(validateField(selectedPais)) {
				PrimeUtils.deleteSecondTab(tabPaiss);				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", JsfUtils.getStringValueFromBundle("label_ErrorLabelPaisNombre")));
			}else {
				MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
				String response = (String)restService.PUT("/api/v2/pais", params, selectedPais, String.class, null).getBody();
				PrimeUtils.deleteSecondTab(tabPaiss);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}
	}

	public void delete() {
		try {
			String id = selectedPais.getId().toString();
			String response = (String)restService.DELETE("/api/v2/pais/"+ id, null, String.class).getBody();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}
	}

	public List<Pais> getPaisList() throws IOException {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ApiRestMapper<Pais> apiRestMapper = new ApiRestMapper<>();
		String response = (String)restService.GET("/api/v2/pais", params, String.class).getBody();
		return apiRestMapper.mapList(response, Pais.class);
	}

	public Pais getPaisById(Integer id) throws IOException {
		Iterator<Pais> i = getPaisList().iterator();
		Pais pais = null;
		boolean found = false;

		while (i.hasNext() && !found) {
			Pais p = i.next();

			if(p.getId().intValue() == id)
			{
				pais = new Pais(p.getId(), p.getNombre());
				found = true;
			}
		}

		return pais;
	}

	public boolean validateField(Pais p) throws IOException {
		boolean validate = false;
		paisList=getPaisList();
		for (int i = 0; i < paisList.size() && !validate; i++) {
			if(paisList.get(i).getNombre().equalsIgnoreCase(p.getNombre())) {
				validate = true;				
			}				
		}		
		return validate;
	}
	
	public List<Pais> completePais (String query) throws IOException {
		List<Pais> allPaises = getPaisList();
		List<Pais> filteredPaises = new ArrayList<Pais>();

		for (int i = 0; i < allPaises.size(); i++) {
			Pais p = allPaises.get(i);
			if(p.getNombre().toLowerCase().startsWith(query)) {
				filteredPaises.add(p);
			}
		}

		return filteredPaises;
	}

	public String validate(Pais p) {
		String val = "";

		if(p.getNombre()!=null) {
			val = "paisTabView";
		}

		return val;
	}

	public void setPaisList(List<Pais> paisList) {
		this.paisList = paisList;
	}

	public int getActiveIndexTab() {
		return activeIndexTab;
	}

	public void setActiveIndexTab(int activeIndexTab) {
		this.activeIndexTab = activeIndexTab;
	}

	public TabView getTabPaiss() {
		return tabPaiss;
	}

	public void setTabPaiss(TabView tabPaiss) {
		this.tabPaiss = tabPaiss;
	}

	public Pais getSelectedPais() {
		return selectedPais;
	}

	public void setSelectedPais(Pais selectedPais) {
		this.selectedPais = selectedPais;
	}

}
