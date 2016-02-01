package clinical.controller.agendamento;

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
import clinical.db.Buscar;
import clinical.db.Persistir;
import clinical.util.WebUtil;

@ManagedBean(name = "listarAgendamentos")
@ViewScoped
public class ListarAgendamentos {
	private List<Agendamento> agendamentos;

	private Agendamento agendamentoSelecionado;

	private Date dataInicial;

	private Date dataFinal;

	private Medico medico;

	private Cliente cliente;

	private Clinica clinica;

	private List<Medico> corpoClinico;

	private List<Cliente> clientes;

	private List<Clinica> clinicas;

	@PostConstruct
	public void init() {
		if (corpoClinico == null || corpoClinico.isEmpty()) {
			corpoClinico = Buscar.buscarTodosMedicos();
		}

		if (clientes == null || clientes.isEmpty()) {
			clientes = Buscar.buscarTodosClientes();
		}

		if (clinicas == null || clinicas.isEmpty()) {
			clinicas = Buscar.buscarClinicas();
		}

		buscarAgendamentosAction();
	}

	public String buscarAgendamentosAction() {
		Integer idMedico = null;
		Integer idCliente = null;

		if (medico != null) {
			idMedico = medico.getId();
		}

		if (cliente != null) {
			idCliente = cliente.getId();
		}

		agendamentos = Buscar.buscarAgendamentosPorParametros(dataInicial, dataFinal, idMedico, idCliente, null, null);

		return null;
	}

	public String lancarFaltaAction() {

		if (agendamentoSelecionado == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nenhum Agendamento selecionado.", ""));
			return "";
		}

		if (agendamentoSelecionado.getSituacao().equals(Agendamento.SITUACAO_CANCELADO)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Situação do agendamento não permite lançar falta.", ""));
			return "";
		}

		agendamentoSelecionado.setSituacao(Agendamento.SITUACAO_FALTA);

		try {
			Persistir.salvarObjeto(agendamentoSelecionado);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	public String cancelarAgendamentoAction() {
		if (agendamentoSelecionado == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nenhum Agendamento selecionado.", ""));
			return "";
		}

		if (agendamentoSelecionado.getSituacao().equals(Agendamento.SITUACAO_FALTA)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Situação do agendamento não permite lançar falta.", ""));
			return "";
		}

		agendamentoSelecionado.setSituacao(Agendamento.SITUACAO_CANCELADO);

		try {
			Persistir.salvarObjeto(agendamentoSelecionado);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	public ListarAgendamentos() {
		dataInicial = new Date();
		dataFinal = new Date();

		medico = new Medico();
		cliente = new Cliente();
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

	public List<Medico> getCorpoClinico() {
		return corpoClinico;
	}

	public void setCorpoClinico(List<Medico> corpoClinico) {
		this.corpoClinico = corpoClinico;
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