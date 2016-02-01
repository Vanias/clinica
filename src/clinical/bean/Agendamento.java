package clinical.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Agendamento implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String SITUACAO_AGENDADO = "AG";
	public static final String SITUACAO_CANCELADO = "CA";
	public static final String SITUACAO_FALTA = "FA";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_medico")
	private Medico medico;

	@Temporal(TemporalType.DATE)
	private Date data;

	@Temporal(TemporalType.TIME)
	private Date hora;

	private BigDecimal valorPago;

	private String situacao;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_clinica")
	private Clinica clinica;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public String getPago() {
		if (valorPago != null && valorPago.compareTo(BigDecimal.ZERO) > 0) {
			return "Sim";
		}
		return "Não";
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getSituacaoStr() {
		if (situacao == null) {
			return "";
		} else if (situacao.equals(SITUACAO_AGENDADO)) {
			return "Agendado";
		} else if (situacao.equals(SITUACAO_CANCELADO)) {
			return "Cancelado";
		} else {
			return "Falta";
		}
	}

	public Clinica getClinica() {
		return clinica;
	}

	public void setClinica(Clinica clinica) {
		this.clinica = clinica;
	}
}