package clinical.controller.faturamento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import clinical.bean.Agendamento;
import clinical.bean.Clinica;
import clinical.bean.Medico;
import clinical.bean.RelatorioFaturamentoGeralBean;
import clinical.db.Buscar;
import clinical.util.WebUtil;

@ManagedBean(name = "faturamentoGeral")
@ViewScoped
public class FaturamentoGeral implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<RelatorioFaturamentoGeralBean> faturamento;

	private List<Clinica> clinicas;

	private Medico medicoSelecionado;

	private Clinica clinicaSelecionada;

	private Date dataInicial;

	private Date dataFinal;

	private List<Medico> corpoClinico;

	@PostConstruct
	public void init() {
		corpoClinico = Buscar.buscarTodosMedicos();
		clinicas = Buscar.buscarClinicas();
	}

	public String buscarDados() {
		Integer idMedico = medicoSelecionado != null ? medicoSelecionado.getId() : null;
		Integer idClinica = getClinicaSelecionada() != null ? getClinicaSelecionada().getId() : null;
		List<Agendamento> agendamentos = Buscar.buscarAgendamentosPorParametros(dataInicial, dataFinal, idMedico, null, Agendamento.SITUACAO_AGENDADO, idClinica);
		faturamento = new ArrayList<RelatorioFaturamentoGeralBean>();

		Map<String, RelatorioFaturamentoGeralBean> dados = new HashMap<String, RelatorioFaturamentoGeralBean>();

		if (agendamentos != null && agendamentos.size() > 0) {
			for (Agendamento ag : agendamentos) {
				RelatorioFaturamentoGeralBean bean = new RelatorioFaturamentoGeralBean();

				bean.setData(ag.getData());
				bean.setQtdePacientes(1);
				bean.setMedico(ag.getMedico().getNome());
				bean.setClinica(ag.getClinica().getNome());
				bean.setValor(ag.getValorPago() == null ? BigDecimal.ZERO : ag.getValorPago());

				if (dados.containsKey(WebUtil.formatDate(bean.getData()) + bean.getMedico() + bean.getClinica())) {
					RelatorioFaturamentoGeralBean r = dados.get(WebUtil.formatDate(bean.getData()) + bean.getMedico() + bean.getClinica());

					r.setQtdePacientes(r.getQtdePacientes() + 1);
					r.setValor(r.getValor().add(bean.getValor()));
				} else {
					dados.put(WebUtil.formatDate(bean.getData()) + bean.getMedico() + bean.getClinica(), bean);
				}
			}
		}

		faturamento.addAll(dados.values());

		return "";
	}

	public List<RelatorioFaturamentoGeralBean> getFaturamento() {
		return faturamento;
	}

	public void setFaturamento(List<RelatorioFaturamentoGeralBean> faturamento) {
		this.faturamento = faturamento;
	}

	public Medico getMedicoSelecionado() {
		return medicoSelecionado;
	}

	public void setMedicoSelecionado(Medico medicoSelecionado) {
		this.medicoSelecionado = medicoSelecionado;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public List<Medico> getCorpoClinico() {
		return corpoClinico;
	}

	public void setCorpoClinico(List<Medico> corpoClinico) {
		this.corpoClinico = corpoClinico;
	}

	public BigDecimal getValorTotalFaturamento() {
		if (faturamento != null && faturamento.size() > 0) {
			BigDecimal soma = BigDecimal.ZERO;

			for (RelatorioFaturamentoGeralBean bean : faturamento) {
				soma = soma.add(bean.getValor());
			}

			return soma;
		}
		return BigDecimal.ZERO;
	}

	public Integer getQuantidadePacientes() {
		if (faturamento != null && faturamento.size() > 0) {
			Integer soma = 0;

			for (RelatorioFaturamentoGeralBean bean : faturamento) {
				soma = soma + bean.getQtdePacientes();
			}

			return soma;
		}
		return 0;
	}

	public BigDecimal getMediaGeral() {
		try {
			return getValorTotalFaturamento().divide(new BigDecimal(getQuantidadePacientes()));
		} catch (Exception e) {
			return BigDecimal.ZERO;
		}
	}

	public Clinica getClinicaSelecionada() {
		return clinicaSelecionada;
	}

	public void setClinicaSelecionada(Clinica clinicaSelecionada) {
		this.clinicaSelecionada = clinicaSelecionada;
	}

	public List<Clinica> getClinicas() {
		return clinicas;
	}

	public void setClinicas(List<Clinica> clinicas) {
		this.clinicas = clinicas;
	}
}