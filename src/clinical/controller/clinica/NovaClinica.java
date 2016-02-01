package clinical.controller.clinica;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import clinical.bean.Clinica;
import clinical.db.Buscar;
import clinical.db.Persistir;

@ManagedBean(name = "novaClinica")
@ViewScoped
public class NovaClinica {

	private Clinica clinica;

	public NovaClinica() {
		setClinica(new Clinica());
	}

	public String salvarAction() {
		Clinica c = Buscar.buscarClinicaPorNome(clinica.getNome());

		if (c != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Clínica já cadastrada.", ""));
			return "";
		}

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
