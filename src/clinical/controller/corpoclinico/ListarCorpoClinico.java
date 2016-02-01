package clinical.controller.corpoclinico;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import clinical.bean.Medico;
import clinical.db.Buscar;
import clinical.util.WebUtil;

@ManagedBean(name = "listarCorpoClinico")
@RequestScoped
public class ListarCorpoClinico {

	private List<Medico> corpoClinico;

	private Medico medicoSelecionado;

	public ListarCorpoClinico() {
		try {
			corpoClinico = Buscar.buscarTodosMedicos();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "[ERRO]"
							+ e.getMessage(), ""));
			e.printStackTrace();
		}
	}

	public String editarAction() {
		if (medicoSelecionado == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Nenhum Médico selecionado", ""));
			return "";
		}
		WebUtil.getHttpSession().setAttribute("idMedico",
				medicoSelecionado.getId());

		return "Editar.jsf";
	}

	public List<Medico> getCorpoClinico() {
		return corpoClinico;
	}

	public void setCorpoClinico(List<Medico> corpoClinico) {
		this.corpoClinico = corpoClinico;
	}

	public Medico getMedicoSelecionado() {
		return medicoSelecionado;
	}

	public void setMedicoSelecionado(Medico medicoSelecionado) {
		this.medicoSelecionado = medicoSelecionado;
	}
}
