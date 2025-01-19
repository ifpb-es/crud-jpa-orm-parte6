package br.edu.ifpb.es.daw.dao.impl;

import br.edu.ifpb.es.daw.dao.AddressCriteriaDAO;
import br.edu.ifpb.es.daw.dao.PersistenciaDawException;
import br.edu.ifpb.es.daw.entities.Address;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class AddressCriteriaDAOImpl extends AbstractDAOImpl<Address, Long> implements AddressCriteriaDAO {
    public AddressCriteriaDAOImpl(EntityManagerFactory emf) {
        super(Address.class, emf);
    }

    public List<Address> getAll() throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            List<Address> resultado = null;

            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Address> query = cb.createQuery(Address.class);
            Root<Address> fromAddress = query.from(Address.class);
            query.select(fromAddress);

            TypedQuery<Address> typedQuery = em.createQuery(query);
            resultado = typedQuery.getResultList();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar todos os endereços.", pe);
        }
    }
}
