package clinical.controller;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import clinical.util.WebUtil;

@ManagedBean(name = "navigation")
@RequestScoped
public class Navigation {
	private String redirect(String url) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
			return null;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public String principalAtendenteAction() {
		return redirect("/ClinicalManager/atendimento/Principal.jsf");
	}

	public String principalGerenteAction() {
		return redirect("/ClinicalManager/gerente/Principal.jsf");
	}

	public String principalMedicoAction() {
		return redirect("/ClinicalManager/medico/Principal.jsf");
	}

	public String sairAction() {
		WebUtil.getHttpSession().removeAttribute("usuarioLogado");

		return redirect("/ClinicalManager/pub/Login.jsf");
	}

	public String novoAgendamentoAction() {
		return redirect("/ClinicalManager/atendente/agendamento/Novo.jsf");
	}

	public String novoClienteAction() {
		return redirect("/ClinicalManager/atendente/cliente/Novo.jsf");
	}

	public String novoCorpoClinicoAction() {
		return redirect("/ClinicalManager/gerente/corpoclinico/Novo.jsf");
	}

	public String listarCorpoClinicoAction() {
		return redirect("/ClinicalManager/gerente/corpoclinico/Listar.jsf");
	}

	public String novaEspecialidadeAction() {
		return redirect("/ClinicalManager/gerente/especialidade/Nova.jsf");
	}

	public String listarEspecialidadeAction() {
		return redirect("/ClinicalManager/gerente/especialidade/Listar.jsf");
	}

	public String novoUsuarioAction() {
		return redirect("/ClinicalManager/gerente/admin/NovoUsuario.jsf");
	}

	public String listarUsuariosAction() {
		return redirect("/ClinicalManager/gerente/admin/ListarUsuarios.jsf");
	}

	public String listarClienteAction() {
		return redirect("/ClinicalManager/atendente/cliente/Listar.jsf");
	}

	public String listarAgendamentosAction() {
		return redirect("/ClinicalManager/atendente/agendamento/Listar.jsf");
	}

	public String relatorioFaturamentoPorMedicoAction() {
		return redirect("/ClinicalManager/medico/faturamento/RelatorioPorMedico.jsf");
	}

	public String novaClinicaAction() {
		return redirect("/ClinicalManager/gerente/clinica/Nova.jsf");
	}

	public String listarClinicasAction() {
		return redirect("/ClinicalManager/gerente/clinica/Listar.jsf");
	}

	public String listarAgendamentosMedicosAction() {
		return redirect("/ClinicalManager/medico/agendamento/Listar.jsf");
	}

	public String gerenciarProntuariosAction() {
		return redirect("/ClinicalManager/medico/prontuario/Listar.jsf");
	}

	public String relatorioFaturamentoGerenteAction() {
		return redirect("/ClinicalManager/gerente/relatorios/Faturamento.jsf");
	}
}