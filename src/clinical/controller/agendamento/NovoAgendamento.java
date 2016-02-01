package clinical.controller.agendamento;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import clinical.bean.Agendamento;
import clinical.bean.Cliente;
import clinical.bean.Clinica;
import clinical.bean.Medico;
import clinical.db.Buscar;
import clinical.db.Persistir;
import clinical.util.WebUtil;

@ManagedBean(name = "novoAgendamento")
@RequestScoped
public class NovoAgendamento {

	private Medico medico;

	private Cliente cliente;

	private List<Medico> corpoClinico;

	private List<Cliente> clientes;

	private Date dataAgendamento;

	private Date horaAgendamento;

	private BigDecimal valorPago;

	private Clinica clinica;

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
	}

	public NovoAgendamento() {
		medico = new Medico();
		cliente = new Cliente();
		valorPago = BigDecimal.ZERO;
	}

	public String agendarAction() {
		try {
			boolean passou = true;

			if (dataAgendamento == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data do agendamento não informada.", ""));
				passou = false;
			}

			if (horaAgendamento == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hora do agendamento não informada.", ""));
				passou = false;
			}

			if (medico == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Médico não informado.", ""));
				passou = false;
			}

			if (cliente == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cliente não informado.", ""));
				passou = false;
			}

			if (clinicas == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Clínica não informado.", ""));
				passou = false;
			}

			if (passou == false) {
				return null;
			}

			Date dataAtual = WebUtil.parseDate(WebUtil.formatDate(new Date()));

			if (dataAgendamento.compareTo(dataAtual) < 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data do agendamento é anterior a data atual.", ""));

				return null;
			}

			Agendamento agRetorno = Buscar.buscarAgendamentoPorMedicoDataHora(medico.getId(), dataAgendamento, horaAgendamento, clinica);

			if (agRetorno != null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Já existe um agendamento para este horário.", ""));

				return null;
			}

			Agendamento agendamento = new Agendamento();

			agendamento.setCliente(cliente);
			agendamento.setData(dataAgendamento);
			agendamento.setHora(horaAgendamento);
			agendamento.setMedico(medico);
			agendamento.setValorPago(BigDecimal.ZERO);
			agendamento.setSituacao(Agendamento.SITUACAO_AGENDADO);
			agendamento.setClinica(clinica);

			agendamento = (Agendamento) Persistir.salvarObjeto(agendamento);
		} catch (ParseException e1) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao converter data: " + e1.getMessage(), ""));

			e1.printStackTrace();
			return null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar agendamento: " + e.getMessage(), ""));

			e.printStackTrace();
			return null;
		}

		return "Listar.jsf";
	}

	public Medico getMedico() {
		if (medico == null) {
			medico = new Medico();
		}
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Cliente getCliente() {
		if (cliente == null) {
			cliente = new Cliente();
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

	public Date getDataAgendamento() {
		return dataAgendamento;
	}

	public void setDataAgendamento(Date dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}

	public Date getHoraAgendamento() {
		return horaAgendamento;
	}

	public void setHoraAgendamento(Date horaAgendamento) {
		this.horaAgendamento = horaAgendamento;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
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