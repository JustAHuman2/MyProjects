package com.ua.foxminded.university;

import com.ua.foxminded.university.dao.*;
import com.ua.foxminded.university.dao.mapper.*;
import com.ua.foxminded.university.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.ua")
@PropertySource("classpath:database.properties")
public class Config {

    @Autowired
    Environment environment;

    @Bean
    DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(environment.getProperty("url"));
        driverManagerDataSource.setUsername(environment.getProperty("user"));
        driverManagerDataSource.setPassword(environment.getProperty("password"));
        driverManagerDataSource.setDriverClassName(environment.getProperty("driver"));
        return driverManagerDataSource;
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    DBManager dbManager() {
        return new DBManager(jdbcTemplate(dataSource()));
    }

    @Bean
    AuditoryMapper auditoryMapper() {
        return new AuditoryMapper();
    }

    @Bean
    GroupMapper groupMapper() {
        return new GroupMapper();
    }

    @Bean
    LessonMapper lessonMapper() {
        return new LessonMapper();
    }

    @Bean
    LessonTimeMapper lessonTimeMapper() {
        return new LessonTimeMapper();
    }

    @Bean
    SubjectMapper subjectMapper() {
        return new SubjectMapper();
    }

    @Bean
    TeacherMapper teacherMapper() {
        return new TeacherMapper();
    }

    @Bean
    StudentMapper studentMapper() {
        return new StudentMapper();
    }

    @Bean
    AuditoryService auditoryService() {
        return new AuditoryService(new AuditoryDao(jdbcTemplate(dataSource()), auditoryMapper()));
    }

    @Bean
    SubjectService subjectService() {
        return new SubjectService(new SubjectDao(jdbcTemplate(dataSource()), subjectMapper()));
    }

    @Bean
    GroupService groupService() {
        return new GroupService(new GroupDao(jdbcTemplate(dataSource()), groupMapper()));
    }

    @Bean
    LessonTimeService lessonTimeService() {
        return new LessonTimeService(new LessonTimeDao(jdbcTemplate(dataSource()), lessonTimeMapper()));
    }

    @Bean
    StudentService studentService() {
        return new StudentService(new StudentDao(jdbcTemplate(dataSource()), studentMapper()), new GroupService(new GroupDao(jdbcTemplate(dataSource()), groupMapper())));
    }

    @Bean
    TeacherService teacherService() {
        return new TeacherService(new TeacherDao(jdbcTemplate(dataSource()), teacherMapper()), new SubjectDao(jdbcTemplate(dataSource()), subjectMapper()));
    }

    @Bean
    LessonService lessonService() {
        return new LessonService(new LessonDao(jdbcTemplate(dataSource()), lessonMapper()),
                new LessonTimeDao(jdbcTemplate(dataSource()), lessonTimeMapper()),
                new AuditoryDao(jdbcTemplate(dataSource()), auditoryMapper()),
                new TeacherDao(jdbcTemplate(dataSource()), teacherMapper()),
                new GroupDao(jdbcTemplate(dataSource()), groupMapper()),
                new TeacherService(new TeacherDao(jdbcTemplate(dataSource()), teacherMapper()),
                        new SubjectDao(jdbcTemplate(dataSource()), subjectMapper())),
                new GroupService(new GroupDao(jdbcTemplate(dataSource()), groupMapper())));
    }

    @Bean
    CommandExecutor executor() {
        return new CommandExecutor
                (new SubjectService(new SubjectDao(jdbcTemplate(dataSource()), subjectMapper())),
                        new TeacherService(new TeacherDao(jdbcTemplate(dataSource()), teacherMapper()), new SubjectDao(jdbcTemplate(dataSource()), subjectMapper())),
                        new GroupService(new GroupDao(jdbcTemplate(dataSource()), groupMapper())),
                        new StudentService(new StudentDao(jdbcTemplate(dataSource()), studentMapper()), new GroupService(new GroupDao(jdbcTemplate(dataSource()), groupMapper()))),
                        new AuditoryService(new AuditoryDao(jdbcTemplate(dataSource()), auditoryMapper())),
                        new LessonService(new LessonDao(jdbcTemplate(dataSource()), lessonMapper()),
                                new LessonTimeDao(jdbcTemplate(dataSource()), lessonTimeMapper()),
                                new AuditoryDao(jdbcTemplate(dataSource()), auditoryMapper()),
                                new TeacherDao(jdbcTemplate(dataSource()), teacherMapper()),
                                new GroupDao(jdbcTemplate(dataSource()), groupMapper()),
                                new TeacherService(new TeacherDao(jdbcTemplate(dataSource()), teacherMapper()),
                                        new SubjectDao(jdbcTemplate(dataSource()), subjectMapper())),
                                new GroupService(new GroupDao(jdbcTemplate(dataSource()), groupMapper()))),
                        new LessonTimeService(new LessonTimeDao(jdbcTemplate(dataSource()), lessonTimeMapper())));
    }
}