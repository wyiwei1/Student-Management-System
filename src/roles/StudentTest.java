package roles;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import courses.Course;
import files.FileInfoReader;

class StudentTest {
	Scanner scanner = new Scanner(System.in);
	
	//create ArrayLists to store the information of students, professors and administrators
	ArrayList<Student> studentInfo = new ArrayList<Student>();
	ArrayList<Professor> professorInfo = new ArrayList<Professor>();
	ArrayList<Admin> adminInfo = new ArrayList<Admin>();
	ArrayList<Course> courses = new ArrayList<Course>();
			
	@BeforeEach
	void setUp() throws Exception {


		//read and store student information
		studentInfo = FileInfoReader.readStudentFromFile("studentInfo.txt");
		professorInfo = FileInfoReader.readProfessorFromFile("profInfo.txt");
		adminInfo = FileInfoReader.readAdminFromFile("adminInfo.txt");
		courses = FileInfoReader.readCourseFromFile("courseInfo.txt");
		        
		//add all the classes taught by the professor to the list
		for(Course c: courses) {
			for(Professor p: professorInfo) {
				if(p.getName().equalsIgnoreCase(c.getLecturer())) {
					p.addCourse(c);
				}
			}
		}
	}

	@Test
	// test the function of adding a course 
	void testGetCourseNameByID() {
		Student student = new Student("001","StudentName1","testStudent01","password590", null);
		assertEquals("Programming Languages and Techniques", student.getCourseNameByID("CIT590", courses));
		assertEquals("Introduction to Software Development", student.getCourseNameByID("CIT591", courses));

	}
	@Test
	void testRemoveCourseSuccessfully() {
		Course course = new Course("CIT590","Programming Languages and Techniques",
				"Brandon L Krakowsky", "MW","16:30" ,"18:00" , "110");
		Student student = new Student("001","StudentName1","testStudent01","password590", null);
		course.addStudent(student);
		student.addCourse(course);
		
		//test if the student and course was added successfully
		assertTrue(course.getStudent().contains(student));
		assertTrue(student.getCourses().contains(course));
		
        student.ifInListAndRemove(student.getCourses(), "CIT590");
        // verify the course is removed from the course list
  
        assertFalse(student.getCourses().contains(course), "The course should be removed from the student schedule.");
        assertFalse(course.getStudent().contains(student), "The student should be removed from the course.");

    }
	@Test
	// test the process of verifying user names and passwords
	void testAuthenticate() {
		//create a student existed in the given file
		Student student = new Student("001","StudentName1","testStudent01","password590", new HashMap<String,String>());
		
		student.addGrades("CIS191", "A");
		student.addGrades("CIS320", "A");

		
		//verify the user name and password are entered correctly and incorrectly
       assertEquals(student.getID(), Student.authenticate(studentInfo, "testStudent01", "password590").getID());
       assertEquals(student.getName(), Student.authenticate(studentInfo, "testStudent01", "password590").getName());
       assertEquals(student.getUsername(), Student.authenticate(studentInfo, "testStudent01", "password590").getUsername());
       assertEquals(student.getPassword(), Student.authenticate(studentInfo, "testStudent01", "password590").getPassword());
       assertEquals(student.getGrades(), Student.authenticate(studentInfo, "testStudent01", "password590").getGrades());

	}
	
	


}
