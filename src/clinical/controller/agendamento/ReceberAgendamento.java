package clinical.controller.agendamento;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import clinical.bean.Agendamento;
import clinical.bean.Medico;
import clinical.db.Buscar;
import clinical.db.Persistir;
import clinical.util.WebUtil;

@ManagedBean(name = "receberAgendamento")
@RequestScoped
public class ReceberAgendamento {

	private Agendamento agendamento;

	@PostConstruct
	public void init() {
		Integer idAgendamento = (Integer) WebUtil.getHttpSession().getAttribute("idAgendamento");
		if (idAgendamento != null) {
			try {
				agendamento = Buscar.buscarAgendamentoPorId(idAgendamento);

				Medico m = Buscar.buscarMedicoPorId(agendamento.getMedico().getId());

				agendamento.setValorPago(m.getValorConsulta());
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "[ERRO]" + e.getMessage(), ""));
				return;
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "[ERRO] Erro ao carregar agendamento.", ""));
			return;
		}
	}

	public String receberAction() {
		try {
			Persistir.salvarObjeto(agendamento);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "[ERRO] Erro ao salvar agendamento." + e.getMessage(), ""));
			e.printStackTrace();
			return null;
		}

		return "Listar.jsf";
	}

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}
}
