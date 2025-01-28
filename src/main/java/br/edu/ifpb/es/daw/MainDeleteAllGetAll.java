package br.edu.ifpb.es.daw;

import br.edu.ifpb.es.daw.dao.getall.TeacherDAO;
import br.edu.ifpb.es.daw.dao.getall.impl.TeacherDAOImpl;
import br.edu.ifpb.es.daw.entities.getall.Teacher;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class MainDeleteAllGetAll {

	public static void main(String[] args) throws DawException {
		try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("daw")) {
			TeacherDAO dao = new TeacherDAOImpl(emf);

			List<Teacher> teachers = dao.getAll();
			for (Teacher teacher : teachers) {
				dao.delete(teacher.getId());
			}
		}
		
	}
	
}
