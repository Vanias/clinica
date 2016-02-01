package clinical.controller.cliente;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import clinical.bean.Cliente;
import clinical.db.Buscar;
import clinical.util.WebUtil;

@ManagedBean(name = "listarCliente")
@RequestScoped
public class ListarCliente {

	private List<Cliente> clientes;
	private Cliente cliSelect;
	
	public List<Cliente> getClientes() {
		return clientes;
	}
	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	public Cliente getCliSelect() {
		return cliSelect;
	}
	public void setCliSelect(Cliente cliSelect) {
		this.cliSelect = cliSelect;
	}
	
	public ListarCliente() {
		try {
			clientes = Buscar.buscarTodosClientes();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "[ERRO]"
							+ e.getMessage(), ""));
			e.printStackTrace();
		}
	}
	
	public String editarAction() {
		if (cliSelect == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Nenhum Cliente selecionado", ""));
			return "";
		}
		WebUtil.getHttpSession().setAttribute("idCliente",
				cliSelect.getId());

		return "Editar.jsf";
	}
	
	
}
