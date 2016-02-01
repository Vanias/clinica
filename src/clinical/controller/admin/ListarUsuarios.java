package clinical.controller.admin;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import clinical.bean.Usuario;
import clinical.db.Buscar;
import clinical.util.WebUtil;

@ManagedBean(name = "listarUsuarios")
@RequestScoped
public class ListarUsuarios {

	private List<Usuario> usuarios;

	private Usuario usuarioSelecionado;

	public List<Usuario> getUsuarios() {
		if (usuarios == null) {
			try {
				usuarios = Buscar.buscarTodosUsuarios();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL, "[ERRO]"
								+ e.getMessage(), ""));
				e.printStackTrace();
			}
		}

		return usuarios;
	}

	public String editarAction() {
		if (usuarioSelecionado == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Nenhum item selecionado", ""));
			return "";
		}
		WebUtil.getHttpSession().setAttribute("loginUsuario",
				usuarioSelecionado.getUsuario());

		return "EditarUsuario.jsf";
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}
}