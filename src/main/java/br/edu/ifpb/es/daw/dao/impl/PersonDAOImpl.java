package br.edu.ifpb.es.daw.dao.impl;

import br.edu.ifpb.es.daw.dao.PersistenciaDawException;
import br.edu.ifpb.es.daw.dao.PersonDAO;
import br.edu.ifpb.es.daw.entities.Address;
import br.edu.ifpb.es.daw.entities.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PersonDAOImpl extends AbstractDAOImpl<Person, Long> implements PersonDAO {

    public PersonDAOImpl(EntityManagerFactory emf) {
        super(Person.class, emf);
    }

    @Override
    public Person findPersonByName(String name) throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            Person resultado = null;

            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.name = :name", Person.class);
            query.setParameter("name", name);
            resultado = query.getSingleResult();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar a pessoa pelo nome.", pe);
        }
    }

    @Override
    public Person findPersonByAddressObject(Address address) throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            Person resultado = null;

            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.address = :address", Person.class);
            query.setParameter("address", address);
            resultado = query.getSingleResult();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar a pessoa pelo endereço.", pe);
        }
    }

    @Override
    public String findAddressNameOfPerson(String name) throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            String resultado = null;

            TypedQuery<String> query = em.createQuery("SELECT p.address.streetName FROM Person p WHERE p.name = :name",
                    String.class);
            query.setParameter("name", name);
            resultado = query.getSingleResult();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar a rua de uma pessoa com determinado nome.", pe);
        }
    }

    @Override
    public Person findPersonByNameWithAllDogs(String name) throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            Person resultado = null;

            TypedQuery<Person> query = em.createQuery("SELECT DISTINCT p FROM Person p JOIN FETCH p.dogs WHERE p.name = :name",
                    Person.class);
            query.setParameter("name", name);
            resultado = query.getSingleResult();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar uma pessoa (com todos seus cachorros) pelo nome.", pe);
        }
    }

    @Override
    public Person findPersonByNameThatMayNotHaveDogs(String name) throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            Person resultado = null;
            TypedQuery<Person> query = em.createQuery(
                    "SELECT p FROM Person p LEFT JOIN FETCH p.dogs WHERE p.name = :name", Person.class);
            query.setParameter("name", name);
            resultado = query.getSingleResult();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar uma pessoa pelo nome, incluindo as que não tiverem cachorros.", pe);
        }
    }

    @Override
    public Double getPersonsAgeAverage() throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            Double resultado = null;

            TypedQuery<Double> query = em.createQuery("SELECT AVG(p.age) FROM Person p", Double.class);
            resultado = query.getSingleResult();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar a média de idade das pessoas.", pe);
        }
    }

    @Override
    public List<Object[]> getPersonsWithDogsWeightHigherThan(double weight) throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            List<Object[]> resultado = null;

            TypedQuery<Object[]> query = em
                    .createQuery("SELECT p.name, COUNT(p) FROM Person p JOIN p.dogs d WHERE d.weight > :weight GROUP BY p.id, p.name", Object[].class);
            query.setParameter("weight", weight);
            resultado = query.getResultList();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar as pessoas que tem cachorro com peso maior que o valor passado como parâmetro.", pe);
        }
    }

    @Override
    public Long getTheSumOfAllAges() throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            Long resultado = null;

            TypedQuery<Long> query = em.createQuery("SELECT SUM(p.age) FROM Person p", Long.class);
            resultado = query.getSingleResult();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar a soma da idade de todas as pessoas.", pe);
        }
    }

    @Override
    public String getLoweredCaseNameFromUpperCase(String name) throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            String resultado = null;

            TypedQuery<String> query = em.createQuery("SELECT LOWER(p.name) FROM Person p WHERE UPPER(p.name) = :name",
                    String.class);
            query.setParameter("name", name.toUpperCase());
            resultado = query.getSingleResult();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar a pessoa cujo nome em caixa-alta seja igual ao passado como parâmetro.", pe);
        }
    }

    @Override
    public Integer getPersonAgeMod(String personName, int modBy) throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            Integer resultado = null;

            TypedQuery<Integer> query = em.createQuery("SELECT MOD(p.age, :modBy) FROM Person p WHERE p.name = :name",
                    Integer.class);
            query.setParameter("modBy", modBy);
            query.setParameter("name", personName);
            resultado = query.getSingleResult();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar a idade de uma dada pessoa MOD um dado número.", pe);
        }
    }

    @Override
    public Double getPersonAgeSqrtUsingTrim(String name) throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            Double resultado = null;

            TypedQuery<Double> query = em.createQuery("SELECT SQRT(p.age) FROM Person p WHERE p.name = TRIM(:name)",
                    Double.class);
            query.setParameter("name", name);
            resultado = query.getSingleResult();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar a raiz quadrada da idade de uma dada pessoa.", pe);
        }
    }

    @Override
    public List<Object[]> getPersonByHavingDogAmountHigherThan(long dogAmount) throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            List<Object[]> resultado = null;

            TypedQuery<Object[]> query = em
                    .createQuery("SELECT p.name, COUNT(p) FROM Person p JOIN p.dogs d GROUP BY p.id, p.name HAVING COUNT(d) > :dogAmount",
                            Object[].class);
            query.setParameter("dogAmount", dogAmount);
            resultado = query.getResultList();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar as pessoas que tem mais que uma determinada quantidade de cachorros.", pe);
        }
    }

}
