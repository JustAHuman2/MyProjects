package com.ua.foxminded.university.dao;

import com.ua.foxminded.university.TestConfig;
import com.ua.foxminded.university.domain.Auditory;;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class AuditoryDaoTest {

    private final String ADD_AUDITORY = "INSERT INTO auditories (auditory_name) VALUES (?)";
    @Autowired
    private AuditoryDao auditoryDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws IOException {
        jdbcTemplate.execute(Files.lines(new File(ClassLoader.getSystemClassLoader()
                .getResource("schema.sql").getFile()).toPath()).collect(Collectors.joining("")));
    }

    @Test
    @Rollback
    public void givenEmptyTable_whenAdd_thenAuditoriesAdded() {
        int expected = 4;

        asList("A-1", "A-2", "A-3", "A-4").stream().map(Auditory::new).forEach(auditoryDao::add);

        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "auditories");
        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenEmptyTable_whenGetAll_thenEmptyList() {
        List<Auditory> expected = new ArrayList<>();

        List<Auditory> actual = auditoryDao.getAll();

        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenExistingAuditories_whenGetAll_thenAuditories() {
        List<Auditory> expected = asList("AB-1", "AB-2", "AB-3", "AB-4").stream()
                .map(Auditory::new).peek(a -> jdbcTemplate.update(ADD_AUDITORY, a.getName())).collect(toList());

        List<Auditory> actual = auditoryDao.getAll();

        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenExistingAuditory_whenDelete_thenDeleted() {
        int expected = 0;
        Auditory auditory = new Auditory("CD-1");
        jdbcTemplate.update(ADD_AUDITORY, auditory.getName());

        auditoryDao.delete(auditory);

        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "auditories");
        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenEmptyTable_whenDelete_thenEmptyTable() {
        int expected = 0;

        auditoryDao.delete(new Auditory("TZ-1"));

        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "auditories");
        assertEquals(expected, actual);
    }
}