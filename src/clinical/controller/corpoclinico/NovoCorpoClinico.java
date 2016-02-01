package clinical.controller.corpoclinico;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import clinical.bean.Clinica;
import clinical.bean.Especialidade;
import clinical.bean.Medico;
import clinical.db.Buscar;
import clinical.db.Persistir;

@ManagedBean(name = "novoCorpoClinico")
@RequestScoped
public class NovoCorpoClinico {

	private Medico medico;

	private List<Especialidade> especialidades;

	private List<Clinica> clinicas;

	public NovoCorpoClinico() {
		medico = new Medico();

		clinicas = Buscar.buscarClinicas();
	}

	public String salvarAction() {
		Medico med = Buscar.buscarMedicoPorCRM(medico.getCrm(), medico.getUfCrm());

		if (med != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Médico já cadastrado.", ""));
			return "";
		}

		if (medico.getClinica() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Clínica não informada.", ""));
			return "";
		}

		try {
			medico.setDataCadastro(new Date());

			Medico m = (Medico) Persistir.salvarObjeto(medico);
			if (m == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar Médico.", ""));
				return "";
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "[ERRO]" + e.getMessage(), ""));
			e.printStackTrace();
		}

		return "Listar.jsf";
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public List<Especialidade> getEspecialidades() {
		if (especialidades == null) {
			try {
				especialidades = Buscar.buscarTodasEspecialidades();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "[ERRO]" + e.getMessage(), ""));
				e.printStackTrace();
			}
		}
		return especialidades;
	}

	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	public List<Clinica> getClinicas() {
		return clinicas;
	}

	public void setClinicas(List<Clinica> clinicas) {
		this.clinicas = clinicas;
	}
}