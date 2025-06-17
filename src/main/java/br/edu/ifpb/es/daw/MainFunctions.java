package br.edu.ifpb.es.daw;

import br.edu.ifpb.es.daw.dao.DogDAO;
import br.edu.ifpb.es.daw.dao.PersonDAO;
import br.edu.ifpb.es.daw.dao.impl.DogDAOImpl;
import br.edu.ifpb.es.daw.dao.impl.PersonDAOImpl;
import br.edu.ifpb.es.daw.entities.PersonNameWithDogsCount;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

import static br.edu.ifpb.es.daw.dao.DataGeneratorDAO.PERSON03_NAME;
import static br.edu.ifpb.es.daw.dao.DataGeneratorDAO.PERSON04_NAME;
import static br.edu.ifpb.es.daw.dao.DataGeneratorDAO.PERSON05_NAME;

public class MainFunctions {

	public static void main(String[] args) throws DawException {
		// AVG - Does a number average
		// COUNT - Counts the records amount found by the query
		// MAX - Gets the higher value of a column
		// MIN - Gets the lower value of a column
		// TRIM - Removes the white space at the begging/end of the text
		// SUM - Sums all the values of a column
		// UPPER - Modifies all the column text to UPPER CASE
		// LOWER - Modifies all the column text to lower case
		// MOD - Returns the modulus of a column
		// LENGTH - Returns the size of a String
		// SQRT - Returns the square root of a number
		// TODO: ADD new functions added in JPA 3.1

		try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("daw")) {
			PersonDAO personDAO = new PersonDAOImpl(emf);
			DogDAO dogDAO = new DogDAOImpl(emf);

			System.out.println("### getPersonsAgeAverage:");
			Double average = personDAO.getPersonsAgeAverage();
			System.out.println(">>> " + average);

			System.out.println("### getPersonsWithDogsWeightHigherThan:");
			List<Object[]> personsFilteredByDogsWeight = personDAO.getPersonsWithDogsWeightHigherThan(4d);

			for (Object[] objects : personsFilteredByDogsWeight) {
				String name = (String) objects[0];
				Long count = (Long) objects[1];
				System.out.println(">>> The person : " + name + " has " + count + " dogs with the weight > 4");
			}

			System.out.println("### getDogMinAndMaxWeight:");
			Object[] dogMinAndMaxWeightResult = dogDAO.getDogMinAndMaxWeight();
			System.out.println(">>> Min: " + dogMinAndMaxWeightResult[0] + " Max: " + dogMinAndMaxWeightResult[1]);

			System.out.println("### getTheSumOfAllAges:");
			Long sumOfAllAges = personDAO.getTheSumOfAllAges();
			System.out.println(">>> All summed ages are: " + sumOfAllAges);

			System.out.println("### getLoweredCaseNameFromUpperCase:");
			String loweredCaseName = personDAO.getLoweredCaseNameFromUpperCase(PERSON03_NAME);
			System.out.println(">>> " + loweredCaseName);

			System.out.println("### getPersonAgeMode:");
			Integer personAgeMod = personDAO.getPersonAgeMod(PERSON05_NAME, 6);
			System.out.println(">>> Person modulus age: " + personAgeMod);

			System.out.println("### getPersonAgeSqrtUsingTrim:");
			Double personAgeSqrt = personDAO.getPersonAgeSqrtUsingTrim(" " + PERSON04_NAME + " ");
			System.out.println(">>> Person sqrt age: " + personAgeSqrt);

			System.out.println("### getPersonByHavingDogAmountHigherThan:");
			List<PersonNameWithDogsCount> personsByDogsAmount = personDAO.getPersonByHavingDogAmountHigherThan(3);

			for (PersonNameWithDogsCount obj : personsByDogsAmount) {
				String name = obj.getName();
				Long count = obj.getCount();
				System.out.println(">>> " + name + " has " + count + " dogs");
			}
		}

	}

}
