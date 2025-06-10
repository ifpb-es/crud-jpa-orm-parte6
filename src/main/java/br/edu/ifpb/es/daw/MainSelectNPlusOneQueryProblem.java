package br.edu.ifpb.es.daw;

import br.edu.ifpb.es.daw.entities.getall.Discipline;
import br.edu.ifpb.es.daw.entities.getall.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class MainSelectNPlusOneQueryProblem {

    public static void main(String[] args) {

        // WARNING: Problema do SELECT N+1:
        // https://allaroundjava.com/hibernate-n1-selects-problem/
        // "Accessing all the items of lazily loaded entity association leads to what we call an N+1 Selects Problem."

        List<Teacher> teachers = null;
        try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("daw");
            EntityManager em = emf.createEntityManager()) {

            // Regra de negócio: Recuperar todos os professores e imprimir
            // seu nome e o nome das disciplinas que leciona.

            //Long id = em.createQuery("SELECT t.id FROM Teacher t", Long.class).setMaxResults(1).getSingleResult();
            System.out.println(">>> EXEMPLO PROBLEMA N+1 COMEÇA AQUI!");

            TypedQuery<Teacher> query = null;
            // XXX: NÃO carregando explicitamente o relacionamento com disciplinas
            query = em.createQuery("SELECT t FROM Teacher t", Teacher.class);

            // XXX: Carregando explicitamente o relacionamento com disciplinas
            //query = em.createQuery("SELECT t FROM Teacher t LEFT JOIN FETCH t.disciplines", Teacher.class);

            //query.setParameter("id", id);
            teachers = query.getResultList();
            for (Teacher t : teachers) {
                System.out.println(">>> Teacher name: " + t.getName());
                for (Discipline d : t.getDisciplines()) {
                    System.out.println(">>> Discipline name: " + d.getName());
                }
            }

            /*
            System.out.println(">>> " + teachers);
            System.out.println(">>> Imprimindo...");
            System.out.println(">>> " + teachers.getDisciplines().size());
            System.out.println(">>> " + teachers);
            */
            System.out.println(">>> EXEMPLO PROBLEMA N+1 TERMINA AQUI!");
        }

    }
}
