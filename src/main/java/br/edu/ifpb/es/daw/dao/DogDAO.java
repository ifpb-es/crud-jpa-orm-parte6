package br.edu.ifpb.es.daw.dao;

import br.edu.ifpb.es.daw.entities.Dog;

import java.util.List;

public interface DogDAO extends DAO<Dog, Long> {

    /**
     * Consulta que vai retornar todos os cachorros numa determinada ordem.
     *
     * @return
     */
    List<Dog> listAllDogsOrderingByWeight() throws PersistenciaDawException;

    /**
     * Consulta que retorna o menor e maior peso dos cachorros. Esta consulta faz uso da função MIN e MAX.
     *
     * @return
     */
    Object[] getDogMinAndMaxWeight() throws PersistenciaDawException;

}
