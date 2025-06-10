package br.edu.ifpb.es.daw.entities.getall;

import br.edu.ifpb.es.daw.util.Util;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

// @formatter:off
@Entity
@Table(name = "TEACHERS")
@NamedQueries({
		// OBS: "The association referenced by the right side of the FETCH JOIN clause must be an association or
		// element collection that is referenced from an entity or embeddable that is returned as a result of the
		// query. It is not permitted to specify an identification variable for the objects referenced by the right
		// side of the FETCH JOIN clause, and hence references to the implicitly fetched entities or elements cannot
		// appear elsewhere in the query."
		// Referência: https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1#a4931
		@NamedQuery(name = "Teacher.getAllFetchEverything",
					query = "SELECT DISTINCT t FROM Teacher t " +
							"LEFT JOIN FETCH t.disciplines " +
							"LEFT JOIN FETCH t.specialties"),
		@NamedQuery(name = "Teacher.getAll", 
					query = "SELECT DISTINCT t FROM Teacher t") })
@NamedEntityGraphs({ 
	@NamedEntityGraph(name = "graph.Teacher.everything",
					  attributeNodes = { @NamedAttributeNode(value = "disciplines", subgraph = "disciplines"),
							  			 @NamedAttributeNode(value = "specialties") 
				  					   },
				  	  subgraphs = { @NamedSubgraph(name = "disciplines", 
				  	  							   attributeNodes = { @NamedAttributeNode(value = "students"),
			  	  									   	 			  @NamedAttributeNode(value = "classes") 
				  	  							   }) 
				  	  }) 
	})
//@formatter:on
public class Teacher {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(unique = true)
	private String name;

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "teacher")
	private Set<Discipline> disciplines;
//	private List<Discipline> disciplines;

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "teacher")
	private Set<Specialty> specialties;
//	private List<Specialty> specialties;

	public Teacher() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Discipline> getDisciplines() {
		return disciplines;
	}

	public void setDisciplines(Set<Discipline> disciplines) {
		this.disciplines = disciplines;
	}

	public Set<Specialty> getSpecialties() {
		return specialties;
	}

	public void setSpecialties(Set<Specialty> specialties) {
		this.specialties = specialties;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Teacher other = (Teacher) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", " + "name=" + name + ", disciplines="
				+ Util.safeToStringLazyCollection(disciplines) + ", specialties="
				+ Util.safeToStringLazyCollection(specialties) + "]";
	}

}
