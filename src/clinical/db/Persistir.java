package clinical.db;

import javax.persistence.EntityManager;

import clinical.util.JpaUtil;

public class Persistir {

	private static EntityManager em = JpaUtil.getEntityManager();

	public static Object salvarObjeto(Object obj) throws Exception {
		try {
			em.getTransaction().begin();
			Object o = em.merge(obj);
			em.getTransaction().commit();
			return o;
		} catch (Exception e) {
			throw e;
		}
	}
}