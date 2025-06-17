package br.edu.ifpb.es.daw.dao.impl;

import br.edu.ifpb.es.daw.dao.PersistenciaDawException;
import br.edu.ifpb.es.daw.dao.PersonDAO;
import br.edu.ifpb.es.daw.entities.Address;
import br.edu.ifpb.es.daw.entities.Dog;
import br.edu.ifpb.es.daw.entities.Person;
import br.edu.ifpb.es.daw.entities.PersonNameWithDogsCount;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class PersonCriteriaDAOImpl extends AbstractDAOImpl<Person, Long> implements PersonDAO {

    public PersonCriteriaDAOImpl(EntityManagerFactory emf) {
        super(Person.class, emf);
    }

    public List<Person> getAll() throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            List<Person> resultado = null;

            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Person> query = cb.createQuery(Person.class);
            Root<Person> fromPerson = query.from(Person.class);
            query.select(fromPerson);

            TypedQuery<Person> typedQuery = em.createQuery(query);
            resultado = typedQuery.getResultList();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar todas as pessoas.", pe);
        }
    }

    /**
     * Consulta que recebe um tipo simples como parâmetro.
     *
     * @param name
     * @return
     */
    public Person findPersonByName(String name) throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            Person resultado = null;

            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Person> query = cb.createQuery(Person.class);
            Root<Person> fromPerson = query.from(Person.class);
            query.select(fromPerson);

            Predicate condition = cb.equal(fromPerson.<String>get("name"), name);
            query.where(condition);

            TypedQuery<Person> typedQuery = em.createQuery(query);
            resultado = typedQuery.getSingleResult();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar a pessoa pelo nome.", pe);
        }
    }

    /**
     * Consulta que recebe um objeto como parâmetro.
     *
     * @param address
     * @return
     */
    public Person findPersonByAddressObject(Address address) throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            Person resultado = null;

            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Person> query = cb.createQuery(Person.class);
            Root<Person> fromPerson = query.from(Person.class);
            query.select(fromPerson);

            Predicate condition = cb.equal(fromPerson.<Address>get("address"), address);
            query.where(condition);

            TypedQuery<Person> typedQuery = em.createQuery(query);
            resultado = typedQuery.getSingleResult();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar a pessoa pelo endereço.", pe);
        }
    }

    /**
     * Consulta que retorna apenas um atributo em vez de uma entidade.
     *
     * @param name
     * @return
     */
    public String findAddressNameOfPerson(String name) throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            String resultado = null;

            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<String> query = cb.createQuery(String.class);
            Root<Person> fromPerson = query.from(Person.class);
            Path<String> selection = fromPerson.<Address>get("address").<String>get("streetName");
            query.select(selection);

            Predicate condition = cb.equal(fromPerson.<String>get("name"), name);
            query.where(condition);

            TypedQuery<String> typedQuery = em.createQuery(query);
            resultado = typedQuery.getSingleResult();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar a rua de uma pessoa com determinado nome.", pe);
        }
    }

    /**
     * Consulta que vai fazer fetch de um relacionamento lazy. Cuidado! Na forma como está escrita esta consulta, apenas
     * pessoas que tiverem cachorros serão retornadas.
     *
     * @param name
     * @return
     */
    public Person findPersonByNameWithAllDogs(String name) throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            Person resultado = null;

            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Person> query = cb.createQuery(Person.class);
            Root<Person> fromPerson = query.from(Person.class);
            fromPerson.fetch("dogs"); // Retorna um objeto do tipo "Fetch<Person, Dog>"
            query.select(fromPerson).distinct(true);

            Predicate condition = cb.equal(fromPerson.<String>get("name"), name);
            query.where(condition);

            TypedQuery<Person> typedQuery = em.createQuery(query);
            resultado = typedQuery.getSingleResult();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar uma pessoa (com todos seus cachorros) pelo nome.", pe);
        }
    }

    /**
     * Consulta que vai fazer fetch de um relacionamento lazy. Cuidado! Na forma como está escrita esta consulta,
     * pessoas que *NÃO* tiverem cachorros serão retornadas também.
     *
     * @param name
     * @return
     */
    public Person findPersonByNameThatMayNotHaveDogs(String name) throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            Person resultado = null;

            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Person> query = cb.createQuery(Person.class);
            Root<Person> fromPerson = query.from(Person.class);
            fromPerson.fetch("dogs", JoinType.LEFT); // Retorna um objeto do tipo "Fetch<Person, Dog>"
            query.select(fromPerson);

            Predicate condition = cb.equal(fromPerson.<String>get("name"), name);
            query.where(condition);

            TypedQuery<Person> typedQuery = em.createQuery(query);
            resultado = typedQuery.getSingleResult();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar uma pessoa pelo nome, incluindo as que não tiverem cachorros.", pe);
        }
    }

    /**
     * Consulta que retorna a média de idade das pessoas. Esta consulta faz uso da função AVG.
     *
     * @return
     */
    public Double getPersonsAgeAverage() throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            Double resultado = null;

            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Double> query = cb.createQuery(Double.class);
            Root<Person> fromPerson = query.from(Person.class);
            Expression<Double> selection = cb.avg(fromPerson.<Double>get("age"));
            query.select(selection);

            TypedQuery<Double> typedQuery = em.createQuery(query);
            resultado = typedQuery.getSingleResult();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar a média de idade das pessoas.", pe);
        }
    }

    /**
     * Consulta que retorna as pessoas que tem cachorro com peso maior que o valor passado como parâmetro. Esta consulta
     * faz uso da função COUNT.
     *
     * @param weight
     * @return
     */
    public List<Object[]> getPersonsWithDogsWeightHigherThan(double weight) throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            List<Object[]> resultado = null;

            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
            Root<Person> fromPerson = query.from(Person.class);

            query.multiselect(fromPerson.<String>get("name"), cb.count(fromPerson));

            Join<Person, Dog> joinDogs = fromPerson.join("dogs");
            Predicate condition = cb.gt(joinDogs.<Double>get("weight"), weight);
            query.where(condition);

            query.groupBy(fromPerson.<Long>get("id"), fromPerson.<String>get("name"));

            TypedQuery<Object[]> typedQuery = em.createQuery(query);
            resultado = typedQuery.getResultList();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar as pessoas que tem cachorro com peso maior que o valor passado como parâmetro.", pe);
        }
    }

    /**
     * Consulta que retorna a soma da idade de todas as pessoas. Esta consulta faz uso da função SUM.
     *
     * @return
     */
    public Long getTheSumOfAllAges() throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            Long resultado = null;

            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Integer> query = cb.createQuery(Integer.class);
            Root<Person> fromPerson = query.from(Person.class);
            Expression<Integer> selection = cb.sum(fromPerson.<Integer>get("age"));
            query.select(selection);

            TypedQuery<Integer> typedQuery = em.createQuery(query);
            resultado = Long.valueOf(typedQuery.getSingleResult());
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar a soma da idade de todas as pessoas.", pe);
        }
    }

    /**
     * Consulta que retorna a pessoa cujo nome em caixa-alta seja igual ao passado como parâmetro. Esta consulta faz uso
     * da função LOWER e UPPER.
     *
     * @param name
     * @return
     */
    public String getLoweredCaseNameFromUpperCase(String name) throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            String resultado = null;

            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<String> query = cb.createQuery(String.class);
            Root<Person> fromPerson = query.from(Person.class);
            Expression<String> selection = cb.lower(fromPerson.<String>get("name"));
            query.select(selection);

            Predicate condition = cb.equal(cb.upper(fromPerson.<String>get("name")), name.toUpperCase());
            query.where(condition);

            TypedQuery<String> typedQuery = em.createQuery(query);
            resultado = typedQuery.getSingleResult();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar a pessoa cujo nome em caixa-alta seja igual ao passado como parâmetro.", pe);
        }
    }

    /**
     * Consulta que retorna a idade de uma dada pessoa MOD um dado número. Esta consulta faz uso da função MOD.
     *
     * @param personName
     * @param modBy
     * @return
     */
    public Integer getPersonAgeMod(String personName, int modBy) throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            Integer resultado = null;

            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Integer> query = cb.createQuery(Integer.class);
            Root<Person> fromPerson = query.from(Person.class);
            Expression<Integer> selection = cb.mod(fromPerson.<Integer>get("age"), modBy);
            query.select(selection);

            Predicate condition = cb.equal(fromPerson.<String>get("name"), personName);
            query.where(condition);

            TypedQuery<Integer> typedQuery = em.createQuery(query);
            resultado = typedQuery.getSingleResult();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar a idade de uma dada pessoa MOD um dado número.", pe);
        }
    }

    /**
     * Consulta que retorna a raiz quadrada da idade de uma dada pessoa. Esta consulta faz uso da função TRIM e SQRT.
     *
     * @param name
     * @return
     */
    public Double getPersonAgeSqrtUsingTrim(String name) throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            Double resultado = null;

            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Double> query = cb.createQuery(Double.class);
            Root<Person> fromPerson = query.from(Person.class);
            Expression<Double> selection = cb.sqrt(fromPerson.<Integer>get("age"));
            query.select(selection);

            Predicate condition = cb.equal(fromPerson.<String>get("name"), name.trim());
            query.where(condition);

            TypedQuery<Double> typedQuery = em.createQuery(query);
            resultado = typedQuery.getSingleResult();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar a raiz quadrada da idade de uma dada pessoa.", pe);
        }
    }

    /**
     * Consulta que retorna as pessoas que tem mais que uma determinada quantidade de cachorros. Esta consulta faz uso
     * da função HAVING e COUNT.
     *
     * @param dogAmount
     * @return
     */
    public List<PersonNameWithDogsCount> getPersonByHavingDogAmountHigherThan(long dogAmount) throws PersistenciaDawException {
        try(EntityManager em = getEntityManager()) {
            List<PersonNameWithDogsCount> resultado = null;

            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<PersonNameWithDogsCount> query = cb.createQuery(PersonNameWithDogsCount.class);
            Root<Person> fromPerson = query.from(Person.class);

            query.multiselect(fromPerson.<String>get("name"), cb.count(fromPerson));

            Join<Person, Dog> joinDogs = fromPerson.join("dogs");

            query.groupBy(fromPerson.<Long>get("id"), fromPerson.<String>get("name"));

            query.having(cb.gt(cb.count(joinDogs), dogAmount));

            TypedQuery<PersonNameWithDogsCount> typedQuery = em.createQuery(query);
            resultado = typedQuery.getResultList();
            return resultado;
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            throw new PersistenciaDawException("Ocorreu algum erro ao tentar recuperar as pessoas que tem mais que uma determinada quantidade de cachorros.", pe);
        }
    }

}
