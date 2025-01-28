package br.edu.ifpb.es.daw;

import br.edu.ifpb.es.daw.entities.getall.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class MainSelectCartesianProductQueryProblem {

    public static void main(String[] args) {

        // WARNING: Problema do produto cartesiano ao recuperar múltiplas associações ao mesmo tempo:
        // https://allaroundjava.com/hibernate-cartesian-product-problem/
        // "On the contrary to N+1 selects problem, the number of queries is not an issue here, but take a look at the returned result set."

        Teacher teacher = null;
        try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("daw");
            EntityManager em = emf.createEntityManager()) {

            Long id = em.createQuery("SELECT t.id FROM Teacher t", Long.class).setMaxResults(1).getSingleResult();
            System.out.println(">>> EXEMPLO PROBLEMA PRODUTO CARTESIANO COMEÇA AQUI!");

            TypedQuery<Teacher> query = null;

            // XXX: Não carregar explicitamente os relacionamentos
            query = em.createQuery("SELECT t FROM Teacher t WHERE t.id = :id", Teacher.class);

            // XXX: Carregar explicitamente os relacionamentos do primeiro nível
            //query = em.createQuery("SELECT t FROM Teacher t LEFT JOIN FETCH t.disciplines LEFT JOIN FETCH t.specialties WHERE t.id = :id", Teacher.class);

            // XXX: Carregar os relacionamentos LAZY aninhados usando funcionamento específico do Hibernate e não do JPA
            //query = em.createQuery("SELECT t FROM Teacher t LEFT JOIN FETCH t.disciplines d LEFT JOIN FETCH d.students LEFT JOIN FETCH d.classes LEFT JOIN FETCH t.specialties WHERE t.id = :id", Teacher.class);

            query.setParameter("id", id);
            teacher = query.getSingleResult();
            System.out.println(teacher);
            System.out.println("Imprimindo...");
            System.out.println(teacher.getDisciplines().size());
            System.out.println(teacher);
            System.out.println(">>> EXEMPLO PROBLEMA PRODUTO CARTESIANO TERMINA AQUI!");
        }

    }
}
