package clinical.controller.corpoclinico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import clinical.bean.Especialidade;
import clinical.bean.Medico;
import clinical.db.Buscar;
import clinical.db.Persistir;
import clinical.util.WebUtil;

@ManagedBean(name = "editarCorpoClinico")
@ViewScoped
public class EditarCorpoClinico {

	private Medico medico;

	private List<Especialidade> especialidades;

	@PostConstruct
	public void init() {
		Integer idMedico = (Integer) WebUtil.getHttpSession().getAttribute("idMedico");
		if (idMedico != null) {
			try {
				medico = Buscar.buscarMedicoPorId(idMedico);
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "[ERRO]" + e.getMessage(), ""));
				return;
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "[ERRO] Erro ao carregar especialidade.", ""));
			return;
		}
	}

	public String salvarAction() {
		try {
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
}