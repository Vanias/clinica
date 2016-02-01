package clinical.controller.faturamento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import clinical.bean.Agendamento;
import clinical.bean.Medico;
import clinical.bean.RelatorioFaturamentoMedicoBean;
import clinical.bean.Usuario;
import clinical.db.Buscar;
import clinical.util.WebUtil;

@ManagedBean(name = "faturamentoMedico")
@RequestScoped
public class FaturamentoMedico {

	private List<RelatorioFaturamentoMedicoBean> faturamento;

	private Medico medicoSelecionado;

	private Date dataInicial;

	private Date dataFinal;

	@PostConstruct
	public void init() {
		medicoSelecionado = ((Usuario) WebUtil.getHttpSession().getAttribute("usuarioLogado")).getMedico();
	}

	public FaturamentoMedico() {
		// TODO Auto-generated constructor stub
	}

	public String buscarDados() {
		if (medicoSelecionado == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione um médico.", ""));
			return null;
		}

		List<Agendamento> agendamentos = Buscar.buscarAgendamentosPorParametros(dataInicial, dataFinal, medicoSelecionado.getId(), null, Agendamento.SITUACAO_AGENDADO, null);
		faturamento = new ArrayList<RelatorioFaturamentoMedicoBean>();

		Map<String, RelatorioFaturamentoMedicoBean> dados = new HashMap<String, RelatorioFaturamentoMedicoBean>();

		if (agendamentos != null && agendamentos.size() > 0) {
			for (Agendamento ag : agendamentos) {
				RelatorioFaturamentoMedicoBean bean = new RelatorioFaturamentoMedicoBean();

				bean.setData(ag.getData());
				bean.setQtdePacientes(1);
				bean.setValor(ag.getValorPago() == null ? BigDecimal.ZERO : ag.getValorPago());

				if (dados.containsKey(WebUtil.formatDate(bean.getData()))) {
					RelatorioFaturamentoMedicoBean r = dados.get(WebUtil.formatDate(bean.getData()));

					r.setQtdePacientes(r.getQtdePacientes() + 1);
					r.setValor(r.getValor().add(bean.getValor()));
				} else {
					dados.put(WebUtil.formatDate(bean.getData()), bean);
				}
			}
		}

		faturamento.addAll(dados.values());

		return null;
	}

	public Medico getMedicoSelecionado() {
		return medicoSelecionado;
	}

	public void setMedicoSelecionado(Medico medicoSelecionado) {
		this.medicoSelecionado = medicoSelecionado;
	}

	public List<RelatorioFaturamentoMedicoBean> getFaturamento() {
		return faturamento;
	}

	public void setFaturamento(List<RelatorioFaturamentoMedicoBean> faturamento) {
		this.faturamento = faturamento;
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

	public BigDecimal getValorTotalFaturamento() {
		if (faturamento != null && faturamento.size() > 0) {
			BigDecimal soma = BigDecimal.ZERO;

			for (RelatorioFaturamentoMedicoBean bean : faturamento) {
				soma = soma.add(bean.getValor());
			}

			return soma;
		}
		return BigDecimal.ZERO;
	}

	public Integer getQuantidadePacientes() {
		if (faturamento != null && faturamento.size() > 0) {
			Integer soma = 0;

			for (RelatorioFaturamentoMedicoBean bean : faturamento) {
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
}
