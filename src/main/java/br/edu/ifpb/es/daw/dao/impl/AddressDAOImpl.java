package br.edu.ifpb.es.daw.dao.impl;

import br.edu.ifpb.es.daw.dao.AddressDAO;
import br.edu.ifpb.es.daw.entities.Address;
import jakarta.persistence.EntityManagerFactory;

public class AddressDAOImpl extends AbstractDAOImpl<Address, Long> implements AddressDAO {
    public AddressDAOImpl(EntityManagerFactory emf) {
        super(Address.class, emf);
    }
}
