DROP TABLE IF EXISTS subjects, teachers, auditories, students, groups, lesson_time, lessons CASCADE;

CREATE TABLE subjects
(
    id           serial      NOT NULL PRIMARY KEY,
    subject_name varchar(50) NOT NULL
);

CREATE TABLE teachers
(
    id           serial      NOT NULL PRIMARY KEY,
    teacher_name varchar(50) NOT NULL,
    subject_id   int REFERENCES subjects ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE auditories
(
    id            serial      NOT NULL primary key,
    auditory_name varchar(20) NOT NULL
);

CREATE TABLE students
(
    id           serial      NOT NULL PRIMARY KEY,
    student_name varchar(50) NOT NULL,
    group_id     int
);

CREATE TABLE groups
(
    id         serial      NOT NULL PRIMARY KEY,
    group_name varchar(20) NOT NULL
);

CREATE TABLE lesson_time
(
    id         serial NOT NULL PRIMARY KEY,
    start_time TIME WITHOUT TIME ZONE,
    end_time   TIME WITHOUT TIME ZONE
);

CREATE TABLE lessons
(
    id             serial NOT NULL PRIMARY KEY,
    subject_id     int REFERENCES subjects (id) ON DELETE CASCADE ON UPDATE CASCADE,
    teacher_id     int REFERENCES teachers (id) ON DELETE CASCADE ON UPDATE CASCADE,
    auditory_id    int REFERENCES auditories (id) ON DELETE CASCADE ON UPDATE CASCADE,
    group_id       int REFERENCES groups (id) ON DELETE CASCADE ON UPDATE CASCADE,
    lesson_date    DATE,
    lesson_time_id int REFERENCES lesson_time (id) ON DELETE CASCADE ON UPDATE CASCADE
);