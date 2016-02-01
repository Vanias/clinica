package clinical.controller.clinica;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import clinical.bean.Clinica;
import clinical.db.Buscar;
import clinical.db.Persistir;
import clinical.util.WebUtil;

@ManagedBean(name = "editarClinica")
@ViewScoped
public class EditarClinica {

	private Clinica clinica;

	@PostConstruct
	public void init() {
		Integer idClinica = (Integer) WebUtil.getHttpSession().getAttribute("idClinica");

		if (idClinica != null) {
			try {
				clinica = Buscar.buscarClinicaPorId(idClinica);
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "[ERRO]" + e.getMessage(), ""));
				return;
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Clínica não encontrada.", ""));
			return;
		}
	}

	public String salvarAction() {
		try {
			Persistir.salvarObjeto(clinica);
			return "Listar.jsf";
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro ao salvar Clínica: ." + e.getMessage(), ""));
			return "";
		}
	}

	public Clinica getClinica() {
		return clinica;
	}

	public void setClinica(Clinica clinica) {
		this.clinica = clinica;
	}
}
