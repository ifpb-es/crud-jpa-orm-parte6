package br.edu.ifpb.es.daw;

import br.edu.ifpb.es.daw.entities.getall.Teacher;
import br.edu.ifpb.es.daw.util.Util;
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

            // A): Não carregar explicitamente os relacionamentos
            // Relacionamentos carregados:
            // Teacher#disciplines:          [   ]
            // Teacher#disciplines#students: [   ]
            // Teacher#disciplines#classes:  [   ]
            // Teacher#specialties:          [   ]
            query = em.createQuery("SELECT t FROM Teacher t WHERE t.id = :id", Teacher.class);

            // B): Carregar explicitamente os relacionamentos do primeiro nível
            // Relacionamentos carregados:
            // Teacher#disciplines:          [ X ]
            // Teacher#disciplines#students: [   ]
            // Teacher#disciplines#classes:  [   ]
            // Teacher#specialties:          [ X ]
            //query = em.createQuery("SELECT t FROM Teacher t LEFT JOIN FETCH t.disciplines LEFT JOIN FETCH t.specialties WHERE t.id = :id", Teacher.class);

            // C): Carregar os relacionamentos LAZY aninhados usando funcionamento específico do Hibernate e não do JPA
            // Segundo o JPA, o "LEFT JOIN FETCH" não pode ter ALIAS como usado aqui.
            // Referência: https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1#a4931
            // Relacionamentos carregados:
            // Teacher#disciplines:          [ X ]
            // Teacher#disciplines#students: [ X ]
            // Teacher#disciplines#classes:  [ X ]
            // Teacher#specialties:          [ X ]
            //query = em.createQuery("SELECT t FROM Teacher t LEFT JOIN FETCH t.disciplines d LEFT JOIN FETCH d.students LEFT JOIN FETCH d.classes LEFT JOIN FETCH t.specialties WHERE t.id = :id", Teacher.class);

            query.setParameter("id", id);
            teacher = query.getSingleResult();

            // D)+A): Não carregar explicitamente os relacionamentos + acessar os relacionamentos para eles serem carregados dinamicamente em consultas separadas
            teacher.getDisciplines().size();
            teacher.getSpecialties().size();
            System.out.println(">>> 1) teacher.id: " + id);
            System.out.println(">>> 2) #toString(): " + teacher);
            System.out.println(">>> 3) Imprimindo...");
            System.out.println(">>> 4) #disciplinas: " + Util.safeToStringLazyCollection(teacher.getDisciplines()));
            teacher.getDisciplines().forEach(d -> {
                System.out.println(String.format(">>> 5)    #%s-alunos: %s", d.getName(), Util.safeToStringLazyCollection(d.getStudents())));
            });
            System.out.println(">>> 6) #especialidades: " + Util.safeToStringLazyCollection(teacher.getSpecialties()));
            System.out.println(">>> 7) #toString(): " + teacher);
            System.out.println(">>> EXEMPLO PROBLEMA PRODUTO CARTESIANO TERMINA AQUI!");
        }

    }
}
