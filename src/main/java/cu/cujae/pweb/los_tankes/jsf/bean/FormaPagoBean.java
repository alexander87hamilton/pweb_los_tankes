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

import cu.cujae.pweb.los_tankes.domain.FormaPago;
import cu.cujae.pweb.los_tankes.util.ApiRestMapper;
import cu.cujae.pweb.los_tankes.util.JsfUtils;
import cu.cujae.pweb.los_tankes.util.PrimeUtils;
import cu.cujae.pweb.los_tankes.util.RestService;

import org.primefaces.event.CloseEvent;

@Scope(value = "request")
@Component(value = "formaPagoBean")
@ELBeanName(value = "formaPagoBean")
public class FormaPagoBean {

	@Autowired
	private RestService restService;

	private List<FormaPago> formaPagoList;
	private FormaPago formaPago;
	private FormaPago selectedFormaPago;

	
	private TabView tabFormaPagos;
	private int activeIndexTab = 0;


	public FormaPagoBean() {
		formaPagoList = new ArrayList<>();
		formaPago = new FormaPago();
		selectedFormaPago = new FormaPago();
	}

	@PostConstruct
	public void Init() throws IOException {

	}

	public void onTabClose(TabCloseEvent<?> event) {
		tabFormaPagos.getChildren().remove(event.getTab());
	}

	public FormaPago getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}

	public void newFormaPago() throws IOException {
		activeIndexTab = PrimeUtils.createTab(tabFormaPagos, JsfUtils.getStringValueFromBundle("label_newFormaPago"), "/pages/classes/payment/createPayment.xhtml", "formaPagoTabView");

	}

	public void editFormaPago() throws IOException {
		activeIndexTab = PrimeUtils.createTab(tabFormaPagos, JsfUtils.getStringValueFromBundle("label_editFormaPago"), "/pages/classes/payment/editPayment.xhtml", "formaPagoTabView");
	}

		
	public void save() {
		try {
			if(validateField(formaPago)) {
				PrimeUtils.deleteSecondTab(tabFormaPagos);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", 
						JsfUtils.getStringValueFromBundle("label_ErrorLabelFormaPago")));
			}
			else {
				String response = (String)restService.POST("/api/v3/formaPago", formaPago, String.class, null).getBody();
				PrimeUtils.deleteSecondTab(tabFormaPagos);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
						(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}

	}

	public void update() {
		try {
			if(validateField(selectedFormaPago)) {
				PrimeUtils.deleteSecondTab(tabFormaPagos);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", 
						JsfUtils.getStringValueFromBundle("label_ErrorLabelFormaPago")));
			}
			else {
				MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
				String response = (String)restService.PUT("/api/v3/formaPago", params, selectedFormaPago, String.class, null).getBody();
				PrimeUtils.deleteSecondTab(tabFormaPagos);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
						(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}
	}

	public void delete() {
		try {
			String id = selectedFormaPago.getId().toString();
			String response = (String)restService.DELETE("/api/v3/formaPago/"+ id, null, String.class).getBody();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}
	}

	public List<FormaPago> getFormaPagoList() throws IOException {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ApiRestMapper<FormaPago> apiRestMapper = new ApiRestMapper<>();
		String response = (String)restService.GET("/api/v3/formaPago", params, String.class).getBody();
		return apiRestMapper.mapList(response, FormaPago.class);
	}

	public boolean validateField(FormaPago fp) throws IOException {
		boolean validate = false;
		formaPagoList=getFormaPagoList();
		for (int i = 0; i < formaPagoList.size() && !validate; i++) {
			if(formaPagoList.get(i).getNombre().equalsIgnoreCase(fp.getNombre())) {
				validate = true;				
			}				
		}		
		return validate;
	}

	public List<FormaPago> completeFormaPago (String query) throws IOException {
		List<FormaPago> allFormasPago = getFormaPagoList();
		List<FormaPago> filteredFormaPago = new ArrayList<FormaPago>();

		for (int i = 0; i < allFormasPago.size(); i++) {
			FormaPago fp = allFormasPago.get(i);
			if(fp.getNombre().toLowerCase().startsWith(query)) {
				filteredFormaPago.add(fp);
			}
		}

		return filteredFormaPago;
	}

	public void setFormaPagoList(List<FormaPago> formaPagoList) {
		this.formaPagoList = formaPagoList;
	}

	public int getActiveIndexTab() {
		return activeIndexTab;
	}

	public void setActiveIndexTab(int activeIndexTab) {
		this.activeIndexTab = activeIndexTab;
	}

	public TabView getTabFormaPagos() {
		return tabFormaPagos;
	}

	public void setTabFormaPagos(TabView tabFormaPagos) {
		this.tabFormaPagos = tabFormaPagos;
	}

	public FormaPago getSelectedFormaPago() {
		return selectedFormaPago;
	}

	public void setSelectedFormaPago(FormaPago selectedFormaPago) {
		this.selectedFormaPago = selectedFormaPago;
	}

}
