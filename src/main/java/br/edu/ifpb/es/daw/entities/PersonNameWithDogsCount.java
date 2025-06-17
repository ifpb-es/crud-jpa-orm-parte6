package br.edu.ifpb.es.daw.entities;

import java.util.Objects;

public class PersonNameWithDogsCount {

    // Classe usada para mostrar o seguinte recurso JPA:
    // https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1#a5500

    private String name;
    private Long count;

    public PersonNameWithDogsCount(String name, Long count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public Long getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonNameWithDogsCount)) return false;
        PersonNameWithDogsCount that = (PersonNameWithDogsCount) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getCount(), that.getCount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCount());
    }

    @Override
    public String toString() {
        return "PersonNameWithDogsCount{" +
                "name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
