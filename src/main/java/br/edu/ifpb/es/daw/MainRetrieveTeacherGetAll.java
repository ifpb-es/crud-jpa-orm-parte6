package br.edu.ifpb.es.daw;

import br.edu.ifpb.es.daw.dao.getall.TeacherDAO;
import br.edu.ifpb.es.daw.dao.getall.impl.TeacherDAOImpl;
import br.edu.ifpb.es.daw.entities.getall.Teacher;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class MainRetrieveTeacherGetAll {

	public static void main(String[] args) throws DawException {
		try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("daw")) {
			TeacherDAO teacherDAO = new TeacherDAOImpl(emf);
		
			List<Teacher> teachers = teacherDAO.getAll();
			for (Teacher teacher : teachers) {
				System.out.println(teacher);
			}
			
		}
		
	}
	
}
