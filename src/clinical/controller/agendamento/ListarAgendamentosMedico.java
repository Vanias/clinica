package clinical.controller.agendamento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import clinical.bean.Agendamento;
import clinical.bean.Cliente;
import clinical.bean.Clinica;
import clinical.bean.Medico;
import clinical.bean.Usuario;
import clinical.db.Buscar;
import clinical.util.WebUtil;

@ManagedBean(name = "listarAgendamentosMedico")
@ViewScoped
public class ListarAgendamentosMedico {
	private List<Agendamento> agendamentos;

	private Agendamento agendamentoSelecionado;

	private Date dataInicial;

	private Date dataFinal;

	private Medico medico;

	private Cliente cliente;

	private Clinica clinica;

	private List<Cliente> clientes;

	private List<Clinica> clinicas;

	@PostConstruct
	public void init() {
		if (clientes == null || clientes.isEmpty()) {
			clientes = Buscar.buscarTodosClientes();
		}

		if (clinicas == null || clinicas.isEmpty()) {
			clinicas = Buscar.buscarClinicas();
		}

		medico = ((Usuario) WebUtil.getHttpSession().getAttribute("usuarioLogado")).getMedico();

		buscarAgendamentosAction();
	}

	public String buscarAgendamentosAction() {
		Integer idMedico = null;
		Integer idCliente = null;

		idMedico = medico.getId();

		if (cliente != null) {
			idCliente = cliente.getId();
		}

		agendamentos = Buscar.buscarAgendamentosPorParametros(dataInicial, dataFinal, idMedico, idCliente, null, null);

		return null;
	}

	public ListarAgendamentosMedico() {
		dataInicial = new Date();
		dataFinal = new Date();

		cliente = new Cliente();
		agendamentos = new ArrayList<Agendamento>();
	}

	public String receberAgendamentoAction() {
		if (agendamentoSelecionado == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nenhum Agendamento selecionado", ""));
			return "";
		}

		if (agendamentoSelecionado.getPago().equals("Sim")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Agendamento já foi recebido.", ""));
			return "";
		}

		WebUtil.getHttpSession().setAttribute("idAgendamento", agendamentoSelecionado.getId());

		return "Receber.jsf";
	}

	public List<Agendamento> getAgendamentos() {
		return agendamentos;
	}

	public void setAgendamentos(List<Agendamento> agendamentos) {
		this.agendamentos = agendamentos;
	}

	public Agendamento getAgendamentoSelecionado() {
		return agendamentoSelecionado;
	}

	public void setAgendamentoSelecionado(Agendamento agendamentoSelecionado) {
		this.agendamentoSelecionado = agendamentoSelecionado;
	}

	public Date getDataInicial() {
		if (dataInicial == null) {
			return new Date();
		}
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		if (dataFinal == null) {
			return new Date();
		}
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Medico getMedico() {
		if (medico == null) {
			return new Medico();
		}
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Cliente getCliente() {
		if (cliente == null) {
			return new Cliente();
		}
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Integer getQtdeAgendamentos() {
		return agendamentos == null || agendamentos.isEmpty() ? 0 : agendamentos.size();
	}

	public Clinica getClinica() {
		return clinica;
	}

	public void setClinica(Clinica clinica) {
		this.clinica = clinica;
	}

	public List<Clinica> getClinicas() {
		return clinicas;
	}

	public void setClinicas(List<Clinica> clinicas) {
		this.clinicas = clinicas;
	}
}