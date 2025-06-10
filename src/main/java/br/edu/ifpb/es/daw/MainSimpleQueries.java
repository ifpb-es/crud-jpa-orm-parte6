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

import static br.edu.ifpb.es.daw.dao.DataGeneratorDAO.PERSON02_NAME;
import static br.edu.ifpb.es.daw.dao.DataGeneratorDAO.PERSON03_NAME;
import static br.edu.ifpb.es.daw.dao.DataGeneratorDAO.PERSON04_NAME;
import static br.edu.ifpb.es.daw.dao.DataGeneratorDAO.PERSON06_NAME;

public class MainSimpleQueries {

	public static void main(String[] args) throws DawException {
		try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("daw")) {
			PersonDAO personDAO = new PersonDAOImpl(emf);
			DogDAO dogDAO = new DogDAOImpl(emf);

			System.out.println("### getAll:");
			List<Dog> dogs = dogDAO.getAll();

			for (Dog dog : dogs) {
				System.out.println(">>> " + dog.getName());
			}

			System.out.println("### findPersonByName:");
			Person person03 = personDAO.findPersonByName(PERSON03_NAME);
			System.out.println(">>> " + person03.getName());


			System.out.println("### findPersonByAddressObject:");
			Person savedPerson = personDAO.findPersonByAddressObject(person03.getAddress());
			System.out.println(">>> " + savedPerson.getName());
			/*
			System.out.println(">>> Address: " + savedPerson.getAddress().getStreetName());
			for (Dog dog : savedPerson.getDogs()) {
				System.out.println(">>> Saved person Dog: " + dog.getName());
			}
			*/

			System.out.println("### listAllDogsOrderingByWeight:");
			List<Dog> dogsByWeight = dogDAO.listAllDogsOrderingByWeight();

			for (Dog dog : dogsByWeight) {
				System.out.println(">>> " + dog.getWeight());
			}

			System.out.println("### findAddressNameOfPerson:");
			String addressName = personDAO.findAddressNameOfPerson(PERSON04_NAME);
			System.out.println(">>> Person 04 address is: " + addressName);

			System.out.println("### findPersonByNameWithAllDogs:");
			Person person02 = personDAO.findPersonByNameWithAllDogs(PERSON02_NAME);

			for (Dog dog : person02.getDogs()) {
				System.out.println(">>> Person 02 Dog: " + dog.getName());
			}

			System.out.println("### findPersonByNameThatMayNotHaveDogs:");
			Person person06 = null;
			/*
			person06 = personDAO.findPersonByNameWithAllDogs(PERSON06_NAME);
			 */
			person06 = personDAO.findPersonByNameThatMayNotHaveDogs(PERSON06_NAME);
			System.out.println(">>> Name: " + person06.getName());
			System.out.println(">>> Is the list of the Dogs from the Person 06 empty? " + person06.getDogs().size());
		}

	}

}
