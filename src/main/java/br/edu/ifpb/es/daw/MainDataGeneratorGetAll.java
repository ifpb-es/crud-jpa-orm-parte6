package br.edu.ifpb.es.daw;

import br.edu.ifpb.es.daw.dao.entityGraph.DataGeneratorGetAllDAO;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MainDataGeneratorGetAll {

	public static void main(String[] args) {
		try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("daw")) {
			DataGeneratorGetAllDAO dao = new DataGeneratorGetAllDAO(emf);
			dao.generateData();
		}

	}

}
