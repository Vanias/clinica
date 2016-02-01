package clinical.controller.especialidade;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import clinical.bean.Especialidade;
import clinical.db.Buscar;
import clinical.db.Persistir;

@ManagedBean(name = "novaEspecialidade")
@RequestScoped
public class NovaEspecialidade {

	private Especialidade especialidade;

	public NovaEspecialidade() {
		especialidade = new Especialidade();
	}

	public String salvarEspecialidade() {
		Especialidade espec = Buscar.buscarEspecialidadePorDescricao(especialidade.getDescricao());

		if (espec != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Especialidade já cadastrada.", ""));
			return "";
		}

		try {
			Especialidade e = (Especialidade) Persistir.salvarObjeto(especialidade);
			if (e == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar Especialidade.", ""));
				return "";
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "[ERRO]" + e.getMessage(), ""));
			e.printStackTrace();
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