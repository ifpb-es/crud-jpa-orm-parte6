package br.edu.ifpb.es.daw;

import br.edu.ifpb.es.daw.dao.DogDAO;
import br.edu.ifpb.es.daw.dao.PersonDAO;
import br.edu.ifpb.es.daw.dao.impl.DogDAOImpl;
import br.edu.ifpb.es.daw.dao.impl.PersonDAOImpl;
import br.edu.ifpb.es.daw.entities.Dog;
import br.edu.ifpb.es.daw.entities.Person;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class MainDeleteAll {

	public static void main(String[] args) throws DawException {
		try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("daw")) {
			PersonDAO dao = new PersonDAOImpl(emf);
			DogDAO dogDao = new DogDAOImpl(emf);

			List<Person> persons = dao.getAll();
			for (Person person : persons) {
				dao.delete(person.getId());
			}
			
			List<Dog> dogs = dogDao.getAll();
			for (Dog dog : dogs) {
				dogDao.delete(dog.getId());
			}
		}
	}

}
