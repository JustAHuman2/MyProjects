package com.ua.foxminded.university.domain;

public class Subject {

    private int id;
    private String name;

    public Subject(String name) {
        this.name = name;
    }

    public Subject(int id, String name) {
        this.id = id;
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
    public String toString() {
        return "name:" + name + ", id:" + id;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Subject subject = (Subject) o;
//        return name.equals(subject.name);
//    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Subject) || (this.hashCode() != obj.hashCode()))
            return false;
        if (this == obj)
            return true;
        Subject thatSubject = (Subject) obj;
        if (this.getName().equals(thatSubject.getName())) {
            return true;
        }
        return false;
    }
}