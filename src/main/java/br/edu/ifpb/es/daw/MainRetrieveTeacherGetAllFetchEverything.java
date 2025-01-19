package br.edu.ifpb.es.daw;

import br.edu.ifpb.es.daw.dao.entityGraph.TeacherDAO;
import br.edu.ifpb.es.daw.dao.entityGraph.impl.TeacherDAOImpl;
import br.edu.ifpb.es.daw.entities.entityGraph.Teacher;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class MainRetrieveTeacherGetAllFetchEverything {

	public static void main(String[] args) throws DawException {
		try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("daw")) {
			TeacherDAO teacherDAO = new TeacherDAOImpl(emf);

			List<Teacher> teachers = teacherDAO.getAllFetchEverything();
			for (Teacher teacher : teachers) {
				System.out.println(teacher);
			}
			
		}
		
	}
	
}
