Task 6 - SQL Create an application that inserts/updates/deletes data in the database
Tables: groups(group_id int, group_name String)
students(student_id int, group_id int, first_name String, last_name String)
courses(course_id int, course_name String, course_description String)
1. Create SQL files with data:
	a. create user and database, assign all privileges on the database to the user
	b. create a file with tables creation
2. Create a java application
	a. On startup, it should run SQL scripts from previously created files
	b. Generate test data:
		* 10 groups with randomly generated names (2 characters, hyphen, 2 numbers in every name)
		* Create 10 courses
		* 200 students, take 20 first names and 20 last names and randomly combine them to generate students
		* Assign 10-30 random students for group, students can be without groups, groups can be empty
		* Randomly assign 1-3 courses for each student
3. Write SQL Queries, it should be available from the application menu:
	a. Find all groups with less or equals student count
	b. Find all students related to course with given name
	c. Add new student
	d. Delete student by STUDENT_ID
	e. Add a student to the course (from a list)
	f. Remove the student from one of courses
4. On programs exit - drop database