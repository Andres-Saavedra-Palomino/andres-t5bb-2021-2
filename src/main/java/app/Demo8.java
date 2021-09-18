package app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.Usuario;

public class Demo8 {
	public static void main(String[] args) {
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
		EntityManager em = fabrica.createEntityManager();

		// busca segun PK
		// Usuario u = em.find(Usuario.class, 1);

		// busca por usuario y password
		String sql = "Select u from Usuario u where u.usuario = :xusr and u.clave = :xcla";
		TypedQuery<Usuario> query = em.createQuery(sql, Usuario.class);
		query.setParameter("xusr", "U002@gmail.com");
		query.setParameter("xcla", "10002");

		Usuario u = null;
		try {
			u = query.getSingleResult();
			System.out.println("Usuario encontrado\n" + u);

		} catch (NoResultException e) {
			// TODO Auto-generated catch block
			System.out.println("Usuario no existe");
		}

		em.close();

	}
}
