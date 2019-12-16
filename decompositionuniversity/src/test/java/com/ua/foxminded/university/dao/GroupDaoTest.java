package com.ua.foxminded.university.dao;

import com.ua.foxminded.university.TestConfig;
import com.ua.foxminded.university.domain.Group;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class GroupDaoTest {

    private final String ADD_GROUP = "INSERT INTO groups (group_name) VALUES (?)";
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws IOException {
        jdbcTemplate.execute(Files.lines(new File(ClassLoader.getSystemClassLoader()
                .getResource("schema.sql").getFile()).toPath()).collect(Collectors.joining("")));
    }

    @Test
    @Rollback
    public void givenEmptyTable_whenAdd_thenGroupAdded() {
        int expected = 4;

        asList("A-1", "A-2", "A-3", "A-4").stream().map(Group::new).forEach(groupDao::add);

        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "groups");
        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenEmptyTable_whenGetAll_thenEmptyList() {
        List<Group> expected = new ArrayList<>();

        List<Group> actual = groupDao.getAll();

        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenExistingGroups_whenGetAll_thenGroups() {
        List<Group> expected = asList("Gr-1", "Gr-2", "Gr-3", "Gr-4").stream().map(Group::new)
                .peek(g -> jdbcTemplate.update(ADD_GROUP, g.getName())).collect(toList());

        List<Group> actual = groupDao.getAll();

        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenExistingGroup_whenDelete_thenDeleted() {
        int expected = 0;
        Group group = new Group("Group-1");
        jdbcTemplate.update(ADD_GROUP, group.getName());

        groupDao.delete(group);

        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "groups");
        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenEmptyTable_whenDelete_thenEmptyTable() {
        int expected = 0;

        groupDao.delete(new Group("Group-1"));

        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "groups");
        assertEquals(expected, actual);
    }
}