package br.edu.ifpb.es.daw.dao.impl;

import br.edu.ifpb.es.daw.dao.DogDAO;
import br.edu.ifpb.es.daw.dao.PersistenciaDawException;
import br.edu.ifpb.es.daw.entities.Dog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DogDAOImpl extends AbstractDAOImpl<Dog, Long> implements DogDAO {
    public DogDAOImpl(EntityManagerFactory emf) {
        super(Dog.class, emf);
    }

    public List<Dog> getAll() throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            List<Dog> resultado = null;

            TypedQuery<Dog> query = em.createQuery("SELECT d FROM Dog d", Dog.class);
            resultado = query.getResultList();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar todos os cachorros.", pe);
        }
    }

    /**
     * Consulta que vai retornar todos os cachorros numa determinada ordem.
     *
     * @return
     */
    public List<Dog> listAllDogsOrderingByWeight() throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            List<Dog> resultado = null;

            TypedQuery<Dog> query = em.createQuery("SELECT d FROM Dog d ORDER BY d.weight DESC", Dog.class);
            resultado = query.getResultList();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar os cachorros ordenados pelo peso.", pe);
        }
    }

    /**
     * Consulta que retorna o menor e maior peso dos cachorros. Esta consulta faz uso da função MIN e MAX.
     *
     * @return
     */
    public Object[] getDogMinAndMaxWeight() throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            Object[] resultado = null;

            TypedQuery<Object[]> query = em.createQuery("SELECT MIN(d.weight), MAX(d.weight) FROM Dog d", Object[].class);
            resultado = query.getSingleResult();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar o menor e maior peso dos cachorros.", pe);
        }
    }
}
