package br.edu.ifpb.es.daw.dao.entityGraph;

import br.edu.ifpb.es.daw.dao.DAO;
import br.edu.ifpb.es.daw.dao.PersistenciaDawException;
import br.edu.ifpb.es.daw.entities.entityGraph.Teacher;

import java.util.List;

public interface TeacherDAO extends DAO<Teacher, Long> {

	List<Teacher> getAllFetchEverything() throws PersistenciaDawException;
	
	List<Teacher> getAllFetchEverythingWithEntityGraph() throws PersistenciaDawException;
}
