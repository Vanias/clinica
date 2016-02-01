package clinical.controller.especialidade;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import clinical.bean.Especialidade;
import clinical.db.Buscar;
import clinical.util.WebUtil;

@ManagedBean(name = "listarEspecialidades")
public class ListarEspecialidades {

	private List<Especialidade> especialidades;

	private Especialidade especialidadeSelecionada;

	public ListarEspecialidades() {
		try {
			especialidades = Buscar.buscarTodasEspecialidades();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "[ERRO]"
							+ e.getMessage(), ""));
			e.printStackTrace();
		}
	}

	public String editarAction() {
		if (especialidadeSelecionada == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Nenhum item selecionado", ""));
			return "";
		}
		WebUtil.getHttpSession().setAttribute("idEspecialidade",
				especialidadeSelecionada.getId());

		return "Editar.jsf";
	}

	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	public Especialidade getEspecialidadeSelecionada() {
		return especialidadeSelecionada;
	}

	public void setEspecialidadeSelecionada(
			Especialidade especialidadeSelecionada) {
		this.especialidadeSelecionada = especialidadeSelecionada;
	}
}
