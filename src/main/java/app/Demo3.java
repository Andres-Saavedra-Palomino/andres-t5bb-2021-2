package app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Usuario;

public class Demo3 {
	
	public static void main(String[] args) {
		// 1. fabrica el acceso a los datos
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("clase1");
		// 2. crea el manejador de entidades
		EntityManager em = fabrica.createEntityManager();
		// 3. empezar mi transacción
		em.getTransaction().begin();
		// 4. procesos
		Usuario u = new Usuario();
		u.setCodigo(10);
		u.setNombre("Andres");
		u.setApellido("Saavedra");
		u.setUsuario("andsaa@gmail.com");
		u.setClave("123");
		u.setFnacim("2021/07/10");
		u.setTipo(1);
		u.setEstado(1);
		
		//4.1 transaction --> create,update or delete
		em.getTransaction().begin();
		em.remove(u); //delete
		em.getTransaction().commit();
		em.close();
		
		
	}
}
