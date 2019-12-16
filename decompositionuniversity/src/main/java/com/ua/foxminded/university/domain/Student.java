package com.ua.foxminded.university.domain;

public class Student {

    private int id;
    private String name;
    private int groupId;

    public Student(String name) {
        this.name = name;
    }

    public Student(int id, String name, int groupId) {
        this.id = id;
        this.name = name;
        this.groupId = groupId;
    }

    public Student(String name, int groupId) {
        this.name = name;
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Student [" + name + ", id:" + id + ", groupId:" + groupId + "]";
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Student) || (this.hashCode() != obj.hashCode()))
            return false;
        if (this == obj)
            return true;
        Student thatStudent = (Student) obj;
        if (this.getName().equals(thatStudent.getName())) {
            return true;
        }
        return false;
    }
}