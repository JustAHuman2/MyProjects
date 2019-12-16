package com.ua.foxminded.university.service;

import com.ua.foxminded.university.dao.GroupDao;
import com.ua.foxminded.university.domain.Auditory;
import com.ua.foxminded.university.domain.Group;
import com.ua.foxminded.university.domain.Lesson;
import com.ua.foxminded.university.domain.Student;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.concurrent.ThreadLocalRandom.current;

@Service
public class GroupService {

    private GroupDao groupDao;

    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public void generateGroups() {
        asList("B-102", "B-103", "M-28", "M-29", "AL-43", "AL-44", "AL-45", "DT-201", "DT-202", "DT-203")
                .stream().map(Group::new).forEach(groupDao::add);
    }

    public Student assignToGroup(Student student) {
        List<Group> groups = groupDao.getAll();
        Group group = groupDao.getAll().get(current().nextInt(groups.size()));
        group.add(student);
        student.setGroupId(group.getId());
        return student;
    }

    public List<Lesson> setGroups(List<Lesson> lessons, List<Group> groups, List<Auditory> auditories) {
        for (Group group : groups) {
            for (int i = 0; i < lessons.size(); i++) {
                Lesson lesson = lessons.get(i);
                if (null == lesson.getGroup()) {
                    lesson.setGroup(group);
                    int index = auditories.size() - i % auditories.size();
                    i = i + index - 1;
                }
            }
        }
        return lessons;
    }

    public void add(Group group) {
        groupDao.add(group);
    }

    public List<Group> getAll() {
        return groupDao.getAll();
    }

    public void delete(Group group) {
        groupDao.delete(group);
    }
}
