package clinical.controller.especialidade;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import clinical.bean.Especialidade;
import clinical.db.Buscar;
import clinical.db.Persistir;
import clinical.util.WebUtil;

@ManagedBean(name = "editarEspecialidade")
@RequestScoped
public class EditarEspecialidade {

	private Especialidade especialidade;

	public EditarEspecialidade() {
		especialidade = new Especialidade();
	}

	@PostConstruct
	public void init() {
		Integer idEspecialidade = (Integer) WebUtil.getHttpSession()
				.getAttribute("idEspecialidade");
		if (idEspecialidade != null) {
			try {
				especialidade = Buscar
						.buscarEspecialidadePorId(idEspecialidade);
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL, "[ERRO]"
								+ e.getMessage(), ""));
				return;
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"[ERRO] Erro ao carregar especialidade.", ""));
			return;
		}
	}

	public String salvarEspecialidade() {
		Especialidade espec = Buscar
				.buscarEspecialidadePorDescricao(especialidade.getDescricao());

		if (espec != null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Especialidade já cadastrada.", ""));
			return "";
		}

		try {
			Especialidade e = (Especialidade) Persistir
					.salvarObjeto(especialidade);
			if (e == null) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Erro ao salvar Especialidade.", ""));
				return "";
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "[ERRO]"
							+ e.getMessage(), ""));
			e.printStackTrace();
			return "";
		}

		return "Listar.jsf";
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}
}