package clinical.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RelatorioFaturamentoGeralBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Date data;

	private String medico;

	private String clinica;

	private Integer qtdePacientes;

	private BigDecimal valor;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Integer getQtdePacientes() {
		return qtdePacientes;
	}

	public void setQtdePacientes(Integer qtdePacientes) {
		this.qtdePacientes = qtdePacientes;
	}

	public BigDecimal mediaDiaria() {
		try {
			return valor.divide(new BigDecimal(qtdePacientes));
		} catch (Exception e) {
			return BigDecimal.ZERO;
		}
	}

	public String getMedico() {
		return medico;
	}

	public void setMedico(String medico) {
		this.medico = medico;
	}

	public String getClinica() {
		return clinica;
	}

	public void setClinica(String clinica) {
		this.clinica = clinica;
	}
}