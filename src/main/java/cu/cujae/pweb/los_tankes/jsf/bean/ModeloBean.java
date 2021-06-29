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

import cu.cujae.pweb.los_tankes.domain.Modelo;
import cu.cujae.pweb.los_tankes.util.ApiRestMapper;
import cu.cujae.pweb.los_tankes.util.JsfUtils;
import cu.cujae.pweb.los_tankes.util.PrimeUtils;
import cu.cujae.pweb.los_tankes.util.RestService;



@Component(value = "modeloBean")
@ELBeanName(value = "modeloBean")
public class ModeloBean {

	@Autowired
	private RestService restService;

	private List<Modelo> modeloList;
	private Modelo modelo;
	private Modelo selectedModelo;
	private List<Modelo> modelosSelected;

	
	private TabView tabModelos;
	private int activeIndexTab = 0;


	public ModeloBean() {
		modeloList = new ArrayList<>();
		modelo = new Modelo();
		this.selectedModelo = new Modelo();
	}

	@PostConstruct
	public void Init() throws IOException {
		
	}

	public void onTabClose(TabCloseEvent<?> event) {
		tabModelos.getChildren().remove(event.getTab());
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public List<Modelo> getModelosSelected() {
		return modelosSelected;
	}

	public void setModelosSelected(List<Modelo> modelosSelected) {
		this.modelosSelected = modelosSelected;
	}

	public void newModelo() throws IOException {
		activeIndexTab = PrimeUtils.createTab(tabModelos, JsfUtils.getStringValueFromBundle("label_newModelo"), "/pages/classes/model/createModel.xhtml", "modeloTabView");

	}

	public void editModelo() throws IOException {
		activeIndexTab = PrimeUtils.createTab(tabModelos, JsfUtils.getStringValueFromBundle("label_editModelo"), "/pages/classes/model/editModel.xhtml", "modeloTabView");
	}

	
	public void save() {
		try {
			if(validateField(modelo)) {
				PrimeUtils.deleteSecondTab(tabModelos);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", 
						JsfUtils.getStringValueFromBundle("label_modeloNombreExistente")));
			} else {
				String response = (String)restService.POST("/api/v7/modelo", modelo, String.class, null).getBody();
				PrimeUtils.deleteSecondTab(tabModelos);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}

	}

	public void update() {
		try {

			if(validateField(selectedModelo)) {
				PrimeUtils.deleteSecondTab(tabModelos);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", 
						JsfUtils.getStringValueFromBundle("label_modeloNombreExistente")));
			} else {
				MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
				String response = (String)restService.PUT("/api/v7/modelo", params, selectedModelo, String.class, null).getBody();
				PrimeUtils.deleteSecondTab(tabModelos);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}
	}

	public void delete() {
		try {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			String id = selectedModelo.getId().toString();
			String response = (String)restService.DELETE("/api/v7/modelo/" + id, null, String.class).getBody();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}
	}

	public List<Modelo> getModeloList() throws IOException {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>(); 	
		ApiRestMapper<Modelo> apiRestMapper = new ApiRestMapper<>();
		String response = (String)restService.GET("/api/v7/modelo", params, String.class).getBody();
		return apiRestMapper.mapList(response, Modelo.class);
	}

	public boolean validateField(Modelo m) throws IOException
	{
		boolean validate = false;
		modeloList = getModeloList();

		for (int i = 0; i < modeloList.size() && !validate; i++) {
			if(modeloList.get(i).getNombre().equalsIgnoreCase(m.getNombre()))
				validate = true;
		}

		return validate;
	}

	public void setModeloList(List<Modelo> modeloList) {
		this.modeloList = modeloList;
	}

	public int getActiveIndexTab() {
		return activeIndexTab;
	}

	public void setActiveIndexTab(int activeIndexTab) {
		this.activeIndexTab = activeIndexTab;
	}

	public TabView getTabModelos() {
		return tabModelos;
	}

	public void setTabModelos(TabView tabModelos) {
		this.tabModelos = tabModelos;
	}

	public Modelo getSelectedModelo() {
		return selectedModelo;
	}

	public void setSelectedModelo(Modelo selectedModelo) {
		this.selectedModelo = selectedModelo;
	}

	public void onMarcaChange(String marca) throws IOException {
		Iterator<Modelo> i = getModeloList().iterator();
		modelosSelected = new ArrayList<>();

		while(i.hasNext()) 
		{
			Modelo m = i. next();
			if(m.getMarca().getNombre().equalsIgnoreCase(marca))
				modelosSelected.add(m);
		}
	}

	public Modelo findModeloByModelo(String modelo) throws IOException {
		Modelo m1 = new Modelo();
		Iterator<Modelo> i = getModeloList().iterator();
		boolean found = false;
		while(i.hasNext() && !found) 
		{
			Modelo m = i. next();
			if(m.getNombre().equalsIgnoreCase(modelo)) {
				m1=m;
				found = true;
			}
		}		
		return m1;
	}

}



