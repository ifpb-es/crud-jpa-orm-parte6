package br.edu.ifpb.es.daw.dao.impl;

import br.edu.ifpb.es.daw.dao.DogDAO;
import br.edu.ifpb.es.daw.dao.PersistenciaDawException;
import br.edu.ifpb.es.daw.entities.Dog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class DogCriteriaDAOImpl extends AbstractDAOImpl<Dog, Long> implements DogDAO {
    public DogCriteriaDAOImpl(EntityManagerFactory emf) {
        super(Dog.class, emf);
    }

    public List<Dog> getAll() throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            List<Dog> resultado = null;

            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Dog> query = cb.createQuery(Dog.class);
            Root<Dog> fromDog = query.from(Dog.class);
            query.select(fromDog);

            TypedQuery<Dog> typedQuery = em.createQuery(query);
            resultado = typedQuery.getResultList();
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

            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Dog> query = cb.createQuery(Dog.class);
            Root<Dog> fromDog = query.from(Dog.class);
            query.select(fromDog);
            query.orderBy(cb.desc(fromDog.<Double>get("weight")));

            TypedQuery<Dog> typedQuery = em.createQuery(query);
            resultado = typedQuery.getResultList();
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

            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
            Root<Dog> fromDog = query.from(Dog.class);
            Expression<Double> selection1 = cb.min(fromDog.<Double>get("weight"));
            Expression<Double> selection2 = cb.max(fromDog.<Double>get("weight"));
            query.multiselect(selection1, selection2);

            TypedQuery<Object[]> typedQuery = em.createQuery(query);
            resultado = typedQuery.getSingleResult();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar o menor e maior peso dos cachorros.", pe);
        }
    }

}
