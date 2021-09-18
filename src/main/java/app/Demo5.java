package app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Usuario;

public class Demo5 {

	public static void main(String[] args) {
		// 1. fabrica el acceso a los datos
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
		// 2. crea el manejador de entidades
		EntityManager em = fabrica.createEntityManager();
		// 4. lista
		Usuario u = em.find(Usuario.class, 10);
		if (u == null) {
			System.out.println("Usuario no existe");
		} else {
			em.getTransaction().begin();
			em.remove(u); // delete
			em.getTransaction().commit();
			System.out.println("Usuario eliminado");
		}
		em.close();

	}
}
