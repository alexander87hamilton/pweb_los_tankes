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
import cu.cujae.pweb.los_tankes.util.ApiRestMapper;
import cu.cujae.pweb.los_tankes.util.JsfUtils;
import cu.cujae.pweb.los_tankes.util.PrimeUtils;
import cu.cujae.pweb.los_tankes.util.RestService;

@Scope(value = "request")
@Component(value = "marcaBean")
@ELBeanName(value = "marcaBean")
public class MarcaBean {

	@Autowired
	private RestService restService;

	private List<Marca> marcaList;
	private Marca marca;
	private Marca selectedMarca;

	
	private TabView tabMarcas;
	private int activeIndexTab = 0;


	public MarcaBean() {
		marcaList = new ArrayList<>();
		marca = new Marca();
		this.selectedMarca = new Marca();

	}

	@PostConstruct
	public void Init() throws IOException {
		
	}

	public void onTabClose(TabCloseEvent<?> event) {
		tabMarcas.getChildren().remove(event.getTab());
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public void newMarca() throws IOException {
		activeIndexTab = PrimeUtils.createTab(tabMarcas, JsfUtils.getStringValueFromBundle("label_newMarca"), "/pages/classes/brand/createBrand.xhtml", "marcaTabView");

	}

	public void editMarca() throws IOException {
		activeIndexTab = PrimeUtils.createTab(tabMarcas, JsfUtils.getStringValueFromBundle("label_editMarca"), "/pages/classes/brand/editBrand.xhtml", "marcaTabView");
	}

		
	public void save() {
		try {
			if(validateField(marca)) {
				PrimeUtils.deleteSecondTab(tabMarcas);				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
						, "Info", JsfUtils.getStringValueFromBundle("label_ErrorLabelPaisNombre")));
			}else {
				String response = (String)restService.POST("/api/v5/marca", marca, String.class, null).getBody();
				PrimeUtils.deleteSecondTab(tabMarcas);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO
						, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}

	}

	public void update() {
		try {
			if(validateField(marca)) {
				PrimeUtils.deleteSecondTab(tabMarcas);				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
						, "Info", JsfUtils.getStringValueFromBundle("label_ErrorLabelPaisNombre")));
			}else {
				MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
				String response = (String)restService.PUT("/api/v5/marca", params, selectedMarca, String.class, null).getBody();
				PrimeUtils.deleteSecondTab(tabMarcas);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
						(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}
	}

	public void delete() {
		try {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			String id = selectedMarca.getId().toString();
			String response = (String)restService.DELETE("/api/v5/marca/" + id, null, String.class).getBody();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}
	}

	public List<Marca> getMarcaList() throws IOException {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>(); 	
		ApiRestMapper<Marca> apiRestMapper = new ApiRestMapper<>();
		String response = (String)restService.GET("/api/v5/marca", params, String.class).getBody();
		return apiRestMapper.mapList(response, Marca.class);
	}

	public boolean validateField(Marca m) throws IOException {
		boolean validate = false;
		marcaList=getMarcaList();
		for (int i = 0; i < marcaList.size() && !validate; i++) {
			if(marcaList.get(i).getNombre().equalsIgnoreCase(m.getNombre())) {
				validate = true;				
			}				
		}		
		return validate;
	}
	
	public List<Marca> completeMarca (String query) throws IOException {
        List<Marca> allMarcas = getMarcaList();
        List<Marca> filteredMarcas = new ArrayList<Marca>();
         
        for (int i = 0; i < allMarcas.size(); i++) {
        	Marca e = allMarcas.get(i);
            if(e.getNombre().toLowerCase().startsWith(query)) {
            	filteredMarcas.add(e);
            }
        }
         
        return filteredMarcas;
    }

	public void setMarcaList(List<Marca> marcaList) {
		this.marcaList = marcaList;
	}

	public int getActiveIndexTab() {
		return activeIndexTab;
	}

	public void setActiveIndexTab(int activeIndexTab) {
		this.activeIndexTab = activeIndexTab;
	}

	public TabView getTabMarcas() {
		return tabMarcas;
	}

	public void setTabMarcas(TabView tabMarcas) {
		this.tabMarcas = tabMarcas;
	}

	public Marca getSelectedMarca() {
		return selectedMarca;
	}

	public void setSelectedMarca(Marca selectedMarca) {
		this.selectedMarca = selectedMarca;
	}

}
