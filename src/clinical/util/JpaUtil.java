package clinical.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {
	private static EntityManagerFactory emf;

	static {
		getEntityManagerFactory();
	}

	public static EntityManager getEntityManager() {
		return getEntityManagerFactory().createEntityManager();
	}

	public static synchronized EntityManagerFactory getEntityManagerFactory() {
		if (emf == null) {
			try {
				emf = Persistence
						.createEntityManagerFactory("ClinicalManagerPU");
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		return emf;
	}

	public static void shutdown() {
		if (emf != null) {
			emf.close();
			emf = null;
		}
	}
}
