package app;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.Usuario;

public class Demo6 {

	public static void main(String[] args) {

		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
		EntityManager em = fabrica.createEntityManager();

		// process
		TypedQuery<Usuario> query = em.createQuery("Select u from Usuario u", Usuario.class);

		List<Usuario> usuarios = query.getResultList();

		//
		System.out.println("Cantidad de usuario : " + usuarios.size());

		// mapping
		if (usuarios.size() == 0)
			System.out.println("Listado vacío");
		else
			for (Usuario u : usuarios)
				System.out.println(">>> " + u);
		System.out.println("\n\n");

		// process
		String sql = "Select u from Usuario u where u.tipo = :xidtipo";
		TypedQuery<Usuario> query2 = em.createQuery(sql, Usuario.class);
		query2.setParameter("xidtipo", 1);

		List<Usuario> usuarios2 = query2.getResultList();

		//
		System.out.println("Cantidad de usuario : " + usuarios2.size());

		// mapping
		if (usuarios2.size() == 0)
			System.out.println("Listado vacío");
		else
			for (Usuario u : usuarios2)
				System.out.println(">>> " + u);
		System.out.println("\n\n");

		em.close();
	}
}
