package clinical.controller.prontuario;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import clinical.bean.Cliente;
import clinical.bean.Prontuario;
import clinical.db.Persistir;
import clinical.util.WebUtil;

@ManagedBean(name = "novoProntuario")
@ViewScoped
public class NovoProntuario {

	private Prontuario prontuario;

	@PostConstruct
	public void init() {
		prontuario = new Prontuario();

		Cliente cliente = (Cliente) WebUtil.getHttpSession().getAttribute("clienteSelecionado");

		prontuario.setPaciente(cliente);
	}

	public NovoProntuario() {
	}

	public String salvarAction() {
		try {
			Persistir.salvarObjeto(prontuario);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "Listar.jsf";
	}

	public Prontuario getProntuario() {
		return prontuario;
	}

	public void setProntuario(Prontuario prontuario) {
		this.prontuario = prontuario;
	}
}