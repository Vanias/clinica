package clinical.db;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import clinical.bean.Agendamento;
import clinical.bean.Cliente;
import clinical.bean.Clinica;
import clinical.bean.Especialidade;
import clinical.bean.Medico;
import clinical.bean.Prontuario;
import clinical.bean.Usuario;
import clinical.util.JpaUtil;

public class Buscar {

	private static EntityManager em = JpaUtil.getEntityManager();

	@SuppressWarnings("unchecked")
	public static List<Usuario> buscarTodosUsuarios() throws Exception {
		Query q = em.createQuery("SELECT u FROM Usuario u ");

		try {
			return q.getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw e;
		}
	}

	public static Usuario buscarUsuarioPorLogin(String usuario) {
		Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.usuario = :usuario ");

		q.setParameter("usuario", usuario);

		try {
			return (Usuario) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public static Especialidade buscarEspecialidadePorDescricao(String descricao) {
		Query q = em.createQuery("SELECT e FROM Especialidade e WHERE e.descricao = :descricao ");

		q.setParameter("descricao", descricao);

		try {
			return (Especialidade) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Especialidade> buscarTodasEspecialidades() throws Exception {
		Query q = em.createQuery("SELECT e FROM Especialidade e ");

		try {
			return q.getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw e;
		}
	}

	public static Especialidade buscarEspecialidadePorId(Integer idEspecialidade) {
		Query q = em.createQuery("SELECT e FROM Especialidade e WHERE e.id = :id ");

		q.setParameter("id", idEspecialidade);

		try {
			return (Especialidade) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Medico> buscarTodosMedicos() {
		Query q = em.createQuery("SELECT m FROM Medico m ");

		try {
			return q.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public static Medico buscarMedicoPorCRM(String crm, String ufCrm) {
		Query q = em.createQuery("SELECT m FROM Medico m WHERE m.crm = :crm AND m.ufCrm = :ufCrm");

		q.setParameter("crm", crm);
		q.setParameter("ufCrm", ufCrm);

		try {
			return (Medico) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public static Medico buscarMedicoPorId(Integer idMedico) {
		Query q = em.createQuery("SELECT m FROM Medico m WHERE m.id = :id");

		q.setParameter("id", idMedico);

		try {
			return (Medico) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public static Cliente buscarClientePorNumCns(String numCns) {
		// TODO Auto-generated method stub
		Query q = em.createQuery("Select c FROM Cliente c WHERE c.numCns = :numCns");

		q.setParameter("numCns", numCns);

		try {
			return (Cliente) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public static Usuario autenticarUsuario(String login, String senha) {
		Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.usuario = :login AND u.senha = :senha ");

		q.setParameter("login", login);
		q.setParameter("senha", senha);

		try {
			return (Usuario) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Cliente> buscarTodosClientes() {
		Query q = em.createQuery("SELECT m FROM Cliente m ");

		try {
			return q.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public static Cliente buscarClientePorId(Integer idCliente) {
		// TODO Auto-generated method stub
		Query q = em.createQuery("SELECT m FROM Cliente m WHERE m.id = :id");

		q.setParameter("id", idCliente);

		try {
			return (Cliente) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public static List<Agendamento> buscarAgendamentosPorParametros(Date dataInicial, Date dataFinal, Integer idMedico, Integer idCliente, String situacao, Integer idClinica) {
		StringBuilder jpql = new StringBuilder("SELECT a FROM Agendamento a WHERE a.data BETWEEN :dataInicial AND :dataFinal ");

		if (idMedico != null) {
			jpql.append(" AND a.medico.id = " + idMedico + " ");
		}

		if (idCliente != null) {
			jpql.append(" AND a.cliente.id = " + idCliente + " ");
		}

		if (situacao != null) {
			jpql.append(" AND a.situacao like '" + situacao + "' AND a.valorPago > 0.00");
		}

		if (idClinica != null) {
			jpql.append(" AND a.clinica.id = " + idClinica + " ");
		}

		Query query = em.createQuery(jpql.toString());

		query.setParameter("dataInicial", dataInicial);
		query.setParameter("dataFinal", dataFinal);

		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public static Agendamento buscarAgendamentoPorId(Integer idAgendamento) {
		return em.find(Agendamento.class, idAgendamento);
	}

	public static Agendamento buscarAgendamentoPorMedicoDataHora(Integer idMedico, Date dataAgendamento, Date horaAgendamento, Clinica clinica) {
		Query query = em.createQuery("SELECT a FROM Agendamento a WHERE a.medico.id = :idMedico AND a.data = :dataAgendamento AND a.hora = :horaAgendamento AND a.situacao = 'AG' AND a.clinica.id = :idClinica ");

		query.setParameter("idMedico", idMedico);
		query.setParameter("dataAgendamento", dataAgendamento);
		query.setParameter("horaAgendamento", horaAgendamento);
		query.setParameter("idClinica", clinica.getId());

		try {
			return (Agendamento) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public static Clinica buscarClinicaPorNome(String nome) {
		Query query = em.createQuery("SELECT c FROM Clinica c WHERE c.nome = :nome");

		query.setParameter("nome", nome);

		try {
			return (Clinica) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public static Clinica buscarClinicaPorId(Integer idClinica) {
		return em.find(Clinica.class, idClinica);
	}

	@SuppressWarnings("unchecked")
	public static List<Clinica> buscarClinicas() {
		Query query = em.createQuery("SELECT c FROM Clinica c ");

		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Cliente> buscarClienteComProntuario() {
		Query query = em.createQuery("SELECT c FROM Cliente c JOIN c.prontuarios p ");

		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Prontuario> buscarProntuariosPorCliente(Integer id) {
		Query query = em.createQuery("SELECT p FROM Prontuario p WHERE p.paciente.id = :idCliente ");

		query.setParameter("idCliente", id);

		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
}