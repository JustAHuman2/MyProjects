--DROP TABLE IF EXISTS groups, students, courses, student_courses CASCADE;
CONNECT databse university;

CREATE TABLE groups (
  group_id serial PRIMARY KEY,
  group_name varchar(50)
);

CREATE TABLE students (
  student_id serial PRIMARY KEY,
  group_id int REFERENCES groups ON DELETE CASCADE ON UPDATE CASCADE,
  first_name varchar(50) NOT NULL,
  last_name varchar(50) NOT NULL
);

CREATE TABLE courses (
  course_id serial PRIMARY KEY,
  course_name varchar(50) NOT NULL,
  course_description varchar(300) NOT NULL
);

CREATE TABLE student_courses (
  student_id int REFERENCES students(student_id) ON DELETE CASCADE ON UPDATE CASCADE,
  course_id int REFERENCES courses(course_id) ON DELETE CASCADE ON UPDATE CASCADE
);