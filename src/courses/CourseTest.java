package courses;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import files.FileInfoReader;
import roles.Admin;
import roles.Professor;
import roles.Student;

class CourseTest {

Scanner scanner = new Scanner(System.in);
	
	//create ArrayLists to store the information of students, professors and administrators
	ArrayList<Student> studentInfo = new ArrayList<Student>();
	ArrayList<Professor> professorInfo = new ArrayList<Professor>();
	ArrayList<Admin> adminInfo = new ArrayList<Admin>();
	ArrayList<Course> courses = new ArrayList<Course>();
	Course testCourse = new Course("CIT590","Programming Languages and Techniques" ,"Brandon L Krakowsky",
			"MW","16:30","18:00", "110");
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
	//test: whether a class is taught on the same day as another class
    void testHaveSameLetters() {
		assertTrue(Course.haveSameLetters("MW", "MT"));
		assertFalse(Course.haveSameLetters("MW", "TF"));

	}
	
	@Test
	//test: if one courses has a time conflict with another course
	void testCheckIfConflict() {
		// create some courses and compare time with the test course 
		Course newCourse1 = new Course("CIT100","Python" ,"Brandon L Krakowsky",
				"MF","17:30","18:00", "100");
		Course newCourse2 = new Course("CIT100","Python" ,"Brandon L Krakowsky",
				"MF","18:00","19:00", "100");
		Student student = new Student("003", "wyw", "wyw123", 
				"123456", new HashMap<String, String>());
		student.addCourse(testCourse);
		assertEquals(testCourse.getCourseID(),Course.checkIfConflict(newCourse1, student.getCourses()).getCourseID());
		assertEquals(null,Course.checkIfConflict(newCourse2, student.getCourses()));

		Course newCourse3 = new Course("CIS620","Python" ,"Brandon L Krakowsky",
				"M","15:00","18:00", "40");
		assertEquals(testCourse.getCourseID(),Course.checkIfConflict(newCourse3, student.getCourses()).getCourseID());

		
		
	}
	
	@Test
	//test: try to find a course in courses list
	void testSearchCourse() {
		// test an existed course
		assertEquals("CIT590" ,Course.searchCourse("CIT590", courses).getCourseID());
		assertEquals("Programming Languages and Techniques" ,Course.searchCourse("CIT590", courses).getCourseName());
		// test a course not existed
		assertEquals(null ,Course.searchCourse("CIT500", courses));

	}
	@Test
	void test() {
		
		Student student = new Student("003", "wyw", "wyw123", 
				"123456", new HashMap<String, String>());
		//if the course entered is not in the student's schedule, return it
		assertEquals("CIT590",Course.checkIfSelected(testCourse, student.getCourses()).getCourseID());
		student.addCourse(testCourse);
		assertEquals(null,Course.checkIfSelected(testCourse, student.getCourses()));
		// create a new course
		Course newCourse1 = new Course("CIT100","Python" ,"Brandon L Krakowsky",
				"MF","17:30","18:00", "100");
		// test when the new course is not in the student's schedule
		assertEquals("CIT100",Course.checkIfSelected(newCourse1, student.getCourses()).getCourseID());
	}
	
	
}

























