package br.edu.ifpb.es.daw.dao;

import br.edu.ifpb.es.daw.entities.Address;
import br.edu.ifpb.es.daw.entities.Person;
import br.edu.ifpb.es.daw.entities.PersonNameWithDogsCount;

import java.util.List;

public interface PersonDAO extends DAO<Person, Long> {

    /**
     * Consulta que recebe um tipo simples como parâmetro.
     *
     * @param name
     * @return
     */
    Person findPersonByName(String name) throws PersistenciaDawException;

    /**
     * Consulta que recebe um objeto como parâmetro.
     *
     * @param address
     * @return
     */
    Person findPersonByAddressObject(Address address) throws PersistenciaDawException;

    /**
     * Consulta que retorna apenas um atributo em vez de uma entidade.
     *
     * @param name
     * @return
     */
    String findAddressNameOfPerson(String name) throws PersistenciaDawException;

    /**
     * Consulta que vai fazer fetch de um relacionamento lazy. Cuidado! Na forma como está escrita esta consulta, apenas
     * pessoas que tiverem cachorros serão retornadas.
     *
     * @param name
     * @return
     */
    Person findPersonByNameWithAllDogs(String name) throws PersistenciaDawException;

    /**
     * Consulta que vai fazer fetch de um relacionamento lazy. Cuidado! Na forma como está escrita esta consulta,
     * pessoas que *NÃO* tiverem cachorros serão retornadas também.
     *
     * @param name
     * @return
     */
    Person findPersonByNameThatMayNotHaveDogs(String name) throws PersistenciaDawException;

    /**
     * Consulta que retorna a média de idade das pessoas. Esta consulta faz uso da função AVG.
     *
     * @return
     */
    Double getPersonsAgeAverage() throws PersistenciaDawException;

    /**
     * Consulta que retorna as pessoas que tem cachorro com peso maior que o valor passado como parâmetro. Esta consulta
     * faz uso da função COUNT.
     *
     * @param weight
     * @return
     */
    List<Object[]> getPersonsWithDogsWeightHigherThan(double weight) throws PersistenciaDawException;

    /**
     * Consulta que retorna a soma da idade de todas as pessoas. Esta consulta faz uso da função SUM.
     *
     * @return
     */
    Long getTheSumOfAllAges() throws PersistenciaDawException;

    /**
     * Consulta que retorna a pessoa cujo nome em caixa-alta seja igual ao passado como parâmetro. Esta consulta faz uso
     * da função LOWER e UPPER.
     *
     * @param name
     * @return
     */
    String getLoweredCaseNameFromUpperCase(String name) throws PersistenciaDawException;

    /**
     * Consulta que retorna a idade de uma dada pessoa MOD um dado número. Esta consulta faz uso da função MOD.
     *
     * @param personName
     * @param modBy
     * @return
     */
    Integer getPersonAgeMod(String personName, int modBy) throws PersistenciaDawException;

    /**
     * Consulta que retorna a raiz quadrada da idade de uma dada pessoa. Esta consulta faz uso da função TRIM e SQRT.
     *
     * @param name
     * @return
     */
    Double getPersonAgeSqrtUsingTrim(String name) throws PersistenciaDawException;

    /**
     * Consulta que retorna as pessoas que tem mais que uma determinada quantidade de cachorros. Esta consulta faz uso
     * da função HAVING e COUNT.
     *
     * @param dogAmount
     * @return
     */
    List<PersonNameWithDogsCount> getPersonByHavingDogAmountHigherThan(long dogAmount) throws PersistenciaDawException;

}
