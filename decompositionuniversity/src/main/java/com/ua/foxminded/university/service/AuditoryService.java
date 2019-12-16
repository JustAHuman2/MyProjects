package com.ua.foxminded.university.service;

import com.ua.foxminded.university.dao.AuditoryDao;
import com.ua.foxminded.university.domain.Auditory;

import java.util.List;

import static java.util.Arrays.asList;

@Service
public class AuditoryService {

    private AuditoryDao auditoryDao;

    public AuditoryService(AuditoryDao auditoryDao) {
        this.auditoryDao = auditoryDao;
    }

    public void generateAuditories() {
        asList("FT-11", "FT-12", "FT-21", "FT-31", "FT-22").stream().map(Auditory::new).forEach(auditoryDao::add);
    }

    public void add(Auditory auditory) {
        auditoryDao.add(auditory);
    }

    public List<Auditory> getAll() {
        return auditoryDao.getAll();
    }

    public void delete(Auditory auditory) {
        auditoryDao.delete(auditory);
    }
}