package br.edu.ifpb.es.daw.dao.getall;

import br.edu.ifpb.es.daw.entities.getall.Class;
import br.edu.ifpb.es.daw.entities.getall.Discipline;
import br.edu.ifpb.es.daw.entities.getall.Specialty;
import br.edu.ifpb.es.daw.entities.getall.Student;
import br.edu.ifpb.es.daw.entities.getall.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class DataGeneratorGetAllDAO {

	private EntityManagerFactory emf;

	public DataGeneratorGetAllDAO(EntityManagerFactory emf) {
		this.emf = emf;
	}

	protected EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	private LocalDateTime getDate(int ano, int mes, int dia, int hora, int minuto) {
		return LocalDateTime.of(ano, mes, dia, hora, minuto);
	}

	private <T> Set<T> emptyCollection() {
//		return new ArrayList<T>();
		return new HashSet<T>();
	}

	private Teacher getTeacher(String name) {
		Teacher teacher = new Teacher();
		teacher.setName(name);
		teacher.setDisciplines(emptyCollection());
		teacher.setSpecialties(emptyCollection());
		return teacher;
	}

	private Discipline getDiscipline(String name, Teacher teacher) {
		Discipline discipline = new Discipline();

		discipline.setName(name);
		discipline.setStudents(emptyCollection());
		discipline.setClasses(emptyCollection());

		discipline.setTeacher(teacher);
		teacher.getDisciplines().add(discipline);

		return discipline;
	}

	private Student createAndAssociateStudent(String name, Discipline discipline) {
		Student student = new Student();

		student.setName(name);
		student.setDisciplines(emptyCollection());

		student.getDisciplines().add(discipline);
		discipline.getStudents().add(student);

		return student;
	}

	private Class createAndAssociateClass(LocalDateTime dataHora, Discipline discipline) {
		Class clazz = new Class();

		clazz.setDataHora(dataHora);

		clazz.setDiscipline(discipline);
		discipline.getClasses().add(clazz);

		return clazz;
	}

	private Specialty createAndAssociateSpecialty(String name, Teacher teacher) {
		Specialty specialty = new Specialty();

		specialty.setName(name);

		specialty.setTeacher(teacher);
		teacher.getSpecialties().add(specialty);

		return specialty;
	}

	public void generateData() {

		Teacher teacher1 = getTeacher("Teacher " + System.nanoTime());

		Discipline discipline1Teacher1 = getDiscipline("Discipline " + System.nanoTime(), teacher1);
		createAndAssociateStudent("Student " + System.nanoTime(), discipline1Teacher1);
		createAndAssociateStudent("Student " + System.nanoTime(), discipline1Teacher1);
		createAndAssociateStudent("Student " + System.nanoTime(), discipline1Teacher1);
		createAndAssociateClass(getDate(2024, 3, 30, 16,30), discipline1Teacher1);
		createAndAssociateClass(getDate(2024, 3, 30, 17, 30), discipline1Teacher1);
		createAndAssociateClass(getDate(2024, 3, 30, 18, 30), discipline1Teacher1);

		Discipline discipline2Teacher1 = getDiscipline("Discipline " + System.nanoTime(), teacher1);
		createAndAssociateStudent("Student " + System.nanoTime(), discipline2Teacher1);
		createAndAssociateStudent("Student " + System.nanoTime(), discipline2Teacher1);
		createAndAssociateStudent("Student " + System.nanoTime(), discipline2Teacher1);
		createAndAssociateClass(getDate(2024, 3, 30, 16,30), discipline2Teacher1);
		createAndAssociateClass(getDate(2024, 3, 30, 17, 30), discipline2Teacher1);
		createAndAssociateClass(getDate(2024, 3, 30, 18, 30), discipline2Teacher1);

		Discipline discipline3Teacher1 = getDiscipline("Discipline " + System.nanoTime(), teacher1);
		createAndAssociateStudent("Student " + System.nanoTime(), discipline3Teacher1);
		createAndAssociateStudent("Student " + System.nanoTime(), discipline3Teacher1);
		createAndAssociateStudent("Student " + System.nanoTime(), discipline3Teacher1);
		createAndAssociateClass(getDate(2024, 3, 30, 16,30), discipline3Teacher1);
		createAndAssociateClass(getDate(2024, 3, 30, 17, 30), discipline3Teacher1);
		createAndAssociateClass(getDate(2024, 3, 30, 18, 30), discipline3Teacher1);

		createAndAssociateSpecialty("Specialty " + System.nanoTime(), teacher1);
		createAndAssociateSpecialty("Specialty " + System.nanoTime(), teacher1);
		createAndAssociateSpecialty("Specialty " + System.nanoTime(), teacher1);
		createAndAssociateSpecialty("Specialty " + System.nanoTime(), teacher1);
		createAndAssociateSpecialty("Specialty " + System.nanoTime(), teacher1);

		Teacher teacher2 = getTeacher("Teacher " + System.nanoTime());

		Discipline discipline1Teacher2 = getDiscipline("Discipline " + System.nanoTime(), teacher2);
		createAndAssociateStudent("Student " + System.nanoTime(), discipline1Teacher2);
		createAndAssociateStudent("Student " + System.nanoTime(), discipline1Teacher2);
		createAndAssociateStudent("Student " + System.nanoTime(), discipline1Teacher2);
		createAndAssociateClass(getDate(2024, 3, 30, 16,30), discipline1Teacher2);
		createAndAssociateClass(getDate(2024, 3, 30, 17, 30), discipline1Teacher2);
		createAndAssociateClass(getDate(2024, 3, 30, 18, 30), discipline1Teacher2);

		Discipline discipline2Teacher2 = getDiscipline("Discipline " + System.nanoTime(), teacher2);
		createAndAssociateStudent("Student " + System.nanoTime(), discipline2Teacher2);
		createAndAssociateStudent("Student " + System.nanoTime(), discipline2Teacher2);
		createAndAssociateStudent("Student " + System.nanoTime(), discipline2Teacher2);
		createAndAssociateClass(getDate(2024, 3, 30, 16,30), discipline2Teacher2);
		createAndAssociateClass(getDate(2024, 3, 30, 17, 30), discipline2Teacher2);
		createAndAssociateClass(getDate(2024, 3, 30, 18, 30), discipline2Teacher2);

		Discipline discipline3Teacher2 = getDiscipline("Discipline " + System.nanoTime(), teacher2);
		createAndAssociateStudent("Student " + System.nanoTime(), discipline3Teacher2);
		createAndAssociateStudent("Student " + System.nanoTime(), discipline3Teacher2);
		createAndAssociateStudent("Student " + System.nanoTime(), discipline3Teacher2);
		createAndAssociateClass(getDate(2024, 3, 30, 16,30), discipline3Teacher2);
		createAndAssociateClass(getDate(2024, 3, 30, 17, 30), discipline3Teacher2);
		createAndAssociateClass(getDate(2024, 3, 30, 18, 30), discipline3Teacher2);

		createAndAssociateSpecialty("Specialty " + System.nanoTime(), teacher2);
		createAndAssociateSpecialty("Specialty " + System.nanoTime(), teacher2);
		createAndAssociateSpecialty("Specialty " + System.nanoTime(), teacher2);

		try(EntityManager em = getEntityManager()) {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			try {
				// Basta salvar o Teacher porque fizemos cascade de todas as operações em todas as associações. Fizemos isto
				// para facilitar nossa vida ao mostrar esse exemplo.
				em.persist(teacher1);
				em.persist(teacher2);

				transaction.commit();
			} catch (PersistenceException pe) {
				pe.printStackTrace();
				if (transaction.isActive()) {
					transaction.rollback();
				}
			}
		}
	}
}
