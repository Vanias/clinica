package clinical.controller.admin;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import clinical.bean.Medico;
import clinical.bean.Usuario;
import clinical.db.Buscar;
import clinical.db.Persistir;

@ManagedBean(name = "novoUsuario")
@RequestScoped
public class NovoUsuario {

	private Usuario usuario;

	private String senha1;

	private String senha2;

	private List<Medico> corpoClinico;

	private Medico corpoClinicoSelecionado;

	public NovoUsuario() {
		usuario = new Usuario();
		
		corpoClinico = Buscar.buscarTodosMedicos();
	}

	public String salvarUsuario() {
		if (!senha1.equals(senha2)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senhas não conferem.", ""));
			return "";
		}

		usuario.setSenha(senha1);

		if (Buscar.buscarUsuarioPorLogin(usuario.getUsuario()) != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login já cadastrado.", ""));
			return "";
		}

		if (usuario.getNivel().equals(Usuario.NIVEL_MEDICO)) {
			if (corpoClinicoSelecionado == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Médico não informado.", ""));
				return "";
			} else {
				usuario.setMedico(corpoClinicoSelecionado);
			}
		} else {
			if (corpoClinicoSelecionado != null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não é médico, favor remover médico selecionado.", ""));
				return "";
			}
		}

		try {
			Usuario u = (Usuario) Persistir.salvarObjeto(usuario);

			if (u == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar usuário.", ""));
				return "";
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

	public Medico getCorpoClinicoSelecionado() {
		return corpoClinicoSelecionado;
	}

	public void setCorpoClinicoSelecionado(Medico corpoClinicoSelecionado) {
		this.corpoClinicoSelecionado = corpoClinicoSelecionado;
	}

	public List<Medico> getCorpoClinico() {
		return corpoClinico;
	}

	public void setCorpoClinico(List<Medico> corpoClinico) {
		this.corpoClinico = corpoClinico;
	}
}