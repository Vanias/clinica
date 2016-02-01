package clinical.controller.prontuario;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import clinical.bean.Prontuario;
import clinical.db.Persistir;
import clinical.util.WebUtil;

@ManagedBean(name = "editarProntuario")
@ViewScoped
public class EditarProntuario {

	private Prontuario prontuario;

	public EditarProntuario() {
	}

	@PostConstruct
	public void init() {
		setProntuario((Prontuario) WebUtil.getHttpSession().getAttribute("prontuarioEdit"));
	}

	public String salvarAction() {
		try {
			Persistir.salvarObjeto(prontuario);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "[ERRO] " + e.getMessage(), ""));
			return "";
		}

		return "Listar.jsf";
	}

	public Prontuario getProntuario() {
		return prontuario;
	}

	public void setProntuario(Prontuario prontuario) {
		this.prontuario = prontuario;
	}
}