package clinical.controller.cliente;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import clinical.bean.Cliente;
import clinical.db.Buscar;
import clinical.db.Persistir;

@ManagedBean(name = "novoCliente")
@RequestScoped
public class NovoCliente {

	private Cliente novo;

	public NovoCliente() {
		novo = new Cliente();
	}

	public String salvarAction() {
		Cliente cli = Buscar.buscarClientePorNumCns(novo.getNumCns());

		if (cli != null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Cliente já cadastrado.", ""));
			return "";
		}

		try {

			Cliente c = (Cliente) Persistir.salvarObjeto(novo);
			if (c == null) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Erro ao salvar Cliente.", ""));
				return "";
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "[ERRO]"
							+ e.getMessage(), ""));
			e.printStackTrace();
		}

		return "Listar.jsf";
	}

	public Cliente getNovo() {
		return novo;
	}

	public void setNovo(Cliente novo) {
		this.novo = novo;
	}
}
