package clinical.controller.prontuario;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import clinical.bean.Cliente;
import clinical.bean.Prontuario;
import clinical.db.Buscar;
import clinical.util.WebUtil;

@ManagedBean(name = "listarProntuario")
@SessionScoped
public class ListarProntuario implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Prontuario> prontuarios;

	private Prontuario prontuarioSelecionado;

	private Cliente cliente;

	private List<Cliente> clientes;

	@PostConstruct
	public void init() {
	}

	public ListarProntuario() {
	}

	public String buscarProntuariosAction() {
		if (cliente == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nenhum cliente selecionado.", ""));
			return "";
		}

		prontuarios = Buscar.buscarProntuariosPorCliente(cliente.getId());

		return "";
	}

	public String novoProntuarioPacienteAction() {
		if (cliente == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nenhum cliente selecionado.", ""));
			return "";
		}

		WebUtil.getHttpSession().setAttribute("clienteSelecionado", cliente);

		return "Novo.jsf";
	}

	public String editarProntuarioAction() {
		if (prontuarioSelecionado == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nenhum Prontuário selecionado.", ""));
			return "";
		}

		WebUtil.getHttpSession().setAttribute("prontuarioEdit", prontuarioSelecionado);

		return "Editar.jsf";
	}

	public Cliente getCliente() {
		clientes = Buscar.buscarTodosClientes();

		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Prontuario> getProntuarios() {
		return prontuarios;
	}

	public void setProntuarios(List<Prontuario> prontuarios) {
		this.prontuarios = prontuarios;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Prontuario getProntuarioSelecionado() {
		return prontuarioSelecionado;
	}

	public void setProntuarioSelecionado(Prontuario prontuarioSelecionado) {
		this.prontuarioSelecionado = prontuarioSelecionado;
	}
}