package com.ua.foxminded.university.domain;

import java.util.List;

public class Teacher {

    private int id;
    private String name;
    private List<Subject> subjects;

    public Teacher(String name) {
        this.name = name;
    }

    public Teacher(String name, List<Subject> subjects) {
        this.name = name;
        this.subjects = subjects;
    }

    public Teacher(int id, String name, List<Subject> subjects) {
        this.id = id;
        this.name = name;
        this.subjects = subjects;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name + ", Subjects: " + subjects;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Teacher teacher = (Teacher) o;
//        return name.equals(teacher.name);
//    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Teacher) || (this.hashCode() != obj.hashCode()))
            return false;
        if (this == obj)
            return true;
        Teacher thatTeacher = (Teacher) obj;
        if (this.getName().equals(thatTeacher.getName())) {
            return true;
        }
        return false;
    }
}