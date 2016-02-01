package clinical.controller.cliente;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import clinical.bean.Cliente;
import clinical.db.Buscar;
import clinical.db.Persistir;
import clinical.util.WebUtil;


@ManagedBean(name = "editarcliente")
@RequestScoped
public class EditarCliente {

	private Cliente cliente;
	
	public EditarCliente() {
		setCliente(new Cliente());
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	@PostConstruct
	public void init() {
		Integer idCliente = (Integer) WebUtil.getHttpSession()
				.getAttribute("idCliente");
		if (idCliente != null) {
			try {
				cliente = Buscar
						.buscarClientePorId(idCliente);
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL, "[ERRO]"
								+ e.getMessage(), ""));
				return;
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"[ERRO] Erro ao carregar Cliente.", ""));
			return;
		}
	}
	
	public String salvarCLiente() {
		
		try {
			Cliente e = (Cliente) Persistir
					.salvarObjeto(cliente);
			if (e == null) {
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
			return "";
		}

		return "Listar.jsf";
	}
	
}
