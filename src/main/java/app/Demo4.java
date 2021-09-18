package app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Usuario;

public class Demo4 {
	
	public static void main(String[] args) {
		// 1. fabrica el acceso a los datos
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("clase1");
		// 2. crea el manejador de entidades
		EntityManager em = fabrica.createEntityManager();
		// 4. lista
		Usuario u = em.find(Usuario.class, 1);
		if(u == null) System.out.println("Usuario no existe");
		else System.out.println("Usuario encontrado\n"+u);

		em.close();
		
		
	}
}
