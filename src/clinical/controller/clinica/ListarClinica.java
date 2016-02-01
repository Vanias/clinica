package clinical.controller.clinica;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import clinical.bean.Clinica;
import clinical.db.Buscar;
import clinical.util.WebUtil;

@ManagedBean(name = "listarClinica")
@RequestScoped
public class ListarClinica {

	private List<Clinica> clinicas;

	private Clinica clinicaSelecionada;

	public ListarClinica() {
		try {
			clinicas = Buscar.buscarClinicas();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "[ERRO]" + e.getMessage(), ""));
			e.printStackTrace();
		}
	}

	public String editarAction() {
		if (clinicaSelecionada == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nenhuma clínica selecionada", ""));
			return "";
		}

		WebUtil.getHttpSession().setAttribute("idClinica", clinicaSelecionada.getId());

		return "Editar.jsf";
	}

	public List<Clinica> getClinicas() {
		return clinicas;
	}

	public void setClinicas(List<Clinica> clinicas) {
		this.clinicas = clinicas;
	}

	public Clinica getClinicaSelecionada() {
		return clinicaSelecionada;
	}

	public void setClinicaSelecionada(Clinica clinicaSelecionada) {
		this.clinicaSelecionada = clinicaSelecionada;
	}
}