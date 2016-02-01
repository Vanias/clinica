package clinical.controller.login;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import clinical.bean.Usuario;
import clinical.db.Buscar;
import clinical.util.WebUtil;

@ManagedBean(name = "logar")
@RequestScoped
public class Logar {

	private String login;

	private String senha;

	private Usuario usuario;

	public Logar() {
	}

	public String logarAction() {
		System.out.println("Logar.logarAction()");

		usuario = Buscar.autenticarUsuario(login, senha);

		if (usuario == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"usuário ou senha inválido.", ""));
			return "";
		}

		WebUtil.getHttpSession().setAttribute("usuarioLogado", usuario);
		
		try {
			if (usuario.getNivel().equals(Usuario.NIVEL_ATENDENTE)) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"/ClinicalManager/atendente/Principal.jsf?faces-redirect=true");
			} else if (usuario.getNivel().equals(Usuario.NIVEL_GERENTE)) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"/ClinicalManager/gerente/Principal.jsf?faces-redirect=true");
			} else if (usuario.getNivel().equals(Usuario.NIVEL_MEDICO)) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"/ClinicalManager/medico/Principal.jsf?faces-redirect=true");
			}

			return null;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}