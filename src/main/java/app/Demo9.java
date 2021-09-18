package app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Usuario;

public class Demo9 {
	public static void main(String[] args) {
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
		EntityManager em = fabrica.createEntityManager();

		// busca segun PK
		// Usuario u = em.find(Usuario.class, 1);

		// busca por usuario y password
		String sql = "{call usp_validaAcceso (?1, ?2)}";
		Query query = em.createNativeQuery(sql, Usuario.class);
		query.setParameter(1, "U002@gmail.com");
		query.setParameter(2, "10002");

		Usuario u = null;
		try {
			u = (Usuario)query.getSingleResult();
			System.out.println("Usuario encontrado\n" + u);

		} catch (NoResultException e) {
			// TODO Auto-generated catch block
			System.out.println("Usuario no existe");
		}

		em.close();

	}
}