package com.ua.foxminded.university.service;

import com.ua.foxminded.university.dao.SubjectDao;
import com.ua.foxminded.university.domain.Subject;

import java.util.List;

@Service
public class SubjectService {

    private SubjectDao subjectDao;

    public SubjectService(SubjectDao subjectDao) {
        this.subjectDao = subjectDao;
    }

    public void add(Subject subject) {
        subjectDao.add(subject);
    }

    public List<Subject> getAll() {
        return subjectDao.getAll();
    }

    public void delete(Subject subject) {
        subjectDao.delete(subject);
    }
}