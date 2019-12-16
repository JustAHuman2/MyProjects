package com.ua.foxminded.university.domain;

public class Auditory {

    private int id;
    private String name;

    public Auditory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Auditory(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auditory auditory = (Auditory) o;
        return name.equals(auditory.name);
    }

    @Override
    public String toString() {
        return "[Auditory:" + name + ", id:" + id + "]";
    }
}