package br.edu.ifpb.es.daw.dao.getall.impl;

import br.edu.ifpb.es.daw.dao.PersistenciaDawException;
import br.edu.ifpb.es.daw.dao.getall.TeacherDAO;
import br.edu.ifpb.es.daw.dao.impl.AbstractDAOImpl;
import br.edu.ifpb.es.daw.entities.getall.Teacher;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class TeacherDAOImpl extends AbstractDAOImpl<Teacher, Long> implements TeacherDAO {

    public TeacherDAOImpl(EntityManagerFactory emf) {
        super(Teacher.class, emf);
    }

    @Override
    public List<Teacher> getAll() throws PersistenciaDawException {
        EntityManager em = getEntityManager();
        List<Teacher> resultado = null;
        try {
            TypedQuery<Teacher> query = em.createNamedQuery("Teacher.getAll", Teacher.class);
            resultado = query.getResultList();
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar todos os professores.", pe);
        } finally {
            em.close();
        }
        return resultado;
    }

    @Override
    public List<Teacher> getAllFetchEverything() throws PersistenciaDawException {
        EntityManager em = getEntityManager();
        List<Teacher> resultado = null;
        try {
            TypedQuery<Teacher> query = em.createNamedQuery("Teacher.getAllFetchEverything", Teacher.class);

            resultado = query.getResultList();
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar todos os professores carregando todos os dados.", pe);
        } finally {
            em.close();
        }
        return resultado;
    }

    @Override
    public List<Teacher> getAllFetchEverythingWithEntityGraph() throws PersistenciaDawException {
        EntityManager em = getEntityManager();
        List<Teacher> resultado = null;
        try {

            TypedQuery<Teacher> query = em.createNamedQuery("Teacher.getAll", Teacher.class);

            // jakarta.persistence.fetchgraph:
            // "attributes that are specified by attribute nodes of the entity graph are treated as FetchType.EAGER and
            // attributes that are not specified are treated as FetchType.LAZY."
            //
            // jakarta.persistence.loadgraph:
            // "...attributes that are specified by attribute nodes of the entity graph are treated as FetchType.EAGER
            // and attributes that are not specified are treated according to their specified or default FetchType."
            //
            // Referência: https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1#a2814

            EntityGraph<?> graph = em.getEntityGraph("graph.Teacher.everything");
            //query.setHint("jakarta.persistence.fetchgraph", graph);
            query.setHint("jakarta.persistence.loadgraph", graph);

            resultado = query.getResultList();
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar todos os professores carregando todos os dados.", pe);
        } finally {
            em.close();
        }
        return resultado;
    }
}
