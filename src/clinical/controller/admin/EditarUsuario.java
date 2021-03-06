package clinical.controller.admin;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import clinical.bean.Medico;
import clinical.bean.Usuario;
import clinical.db.Buscar;
import clinical.db.Persistir;
import clinical.util.WebUtil;

@ManagedBean(name = "editarUsuario")
@RequestScoped
public class EditarUsuario {

	private Usuario usuario;

	private String senha1;

	private String senha2;

	private List<Medico> corpoClinico;

	public EditarUsuario() {
		usuario = new Usuario();

		corpoClinico = Buscar.buscarTodosMedicos();
	}

	@PostConstruct
	public void init() {
		String login = (String) WebUtil.getHttpSession().getAttribute("loginUsuario");
		if (login != null) {
			try {
				usuario = Buscar.buscarUsuarioPorLogin(login);
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "[ERRO]" + e.getMessage(), ""));
				return;
			}
		} else {
			usuario = new Usuario();
		}
	}

	public String salvarUsuario() {
		if (!senha1.equals(senha2)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senhas n�o conferem.", ""));
			return null;
		}

		usuario.setSenha(senha1);

		if (usuario.getNivel().equals(Usuario.NIVEL_MEDICO)) {
			if (usuario.getMedico() == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "M�dico n�o informado.", ""));
				return "";
			}
		} else {
			if (usuario.getMedico() != null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usu�rio n�o � m�dico, favor remover m�dico selecionado.", ""));
				return "";
			}
		}

		try {
			Usuario u = (Usuario) Persistir.salvarObjeto(usuario);

			if (u == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar usu�rio.", ""));
				return null;
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "[ERRO]" + e.getMessage(), ""));
			e.printStackTrace();
		}

		return "ListarUsuarios.jsf";
	}

	public String cancelarAction() {
		System.out.println("cancelar");
		return "cancelar";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getSenha1() {
		return senha1;
	}

	public void setSenha1(String senha1) {
		this.senha1 = senha1;
	}

	public String getSenha2() {
		return senha2;
	}

	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}

	public List<Medico> getCorpoClinico() {
		return corpoClinico;
	}

	public void setCorpoClinico(List<Medico> corpoClinico) {
		this.corpoClinico = corpoClinico;
	}
}