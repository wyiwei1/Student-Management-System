package roles;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import courses.Course;
import files.FileInfoReader;


class ProfessorTest {

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
	// test the process of verifying user names and passwords
	void testAuthenticate() {
		//create a professor existed in the given file
		Professor professor = new Professor("Brandon L Krakowsky","029","Krakowsky","password590");

		
		//verify the user name and password are entered correctly and incorrectly
       assertEquals(professor.getID(), Professor.authenticate(professorInfo, "Krakowsky", "password590").getID());
       assertEquals(professor.getName(), Professor.authenticate(professorInfo, "Krakowsky", "password590").getName());
       assertEquals(professor.getUsername(), Professor.authenticate(professorInfo, "Krakowsky", "password590").getUsername());
       assertEquals(professor.getPassword(), Professor.authenticate(professorInfo, "Krakowsky", "password590").getPassword());


	}

}
