package roles;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import courses.Course;
import files.FileInfoReader;


class AdminTest {

Scanner scanner = new Scanner(System.in);
	
	//create ArrayLists to store the information of students, professors and administrators
	ArrayList<Student> studentInfo = new ArrayList<Student>();
	ArrayList<Professor> professorInfo = new ArrayList<Professor>();
	ArrayList<Admin> adminInfo = new ArrayList<Admin>();
	ArrayList<Course> courses = new ArrayList<Course>();
	Admin admin = new Admin("001","admin", "admin01", "password590");		
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
	// the test is whether you can find student by entering only the ID
    void testGetStudentObjByID() {
		//test if the function can get the correct student information
		
		Student student = admin.getStudentObjByID("001", studentInfo);
		assertEquals("StudentName1",student.getName());
		assertEquals("001",student.getID());
		assertEquals("testStudent01",student.getUsername());
		assertEquals("password590",student.getPassword());
	}
	
	@Test
	// test if the entered student user name existed
	void testIfStudentUsernameExist() {
		
		assertTrue(admin.ifStudentUsernameExist("testStudent01",studentInfo ));
		assertTrue(admin.ifStudentUsernameExist("testStudent02",studentInfo ));
		assertFalse(admin.ifStudentUsernameExist("testStudent03",studentInfo ));
	}
	
	@Test
	//test if the entered student id existed
	void testIfStudentIDExist() {
		assertTrue(admin.ifStudentIDExist("001",studentInfo ));
		assertTrue(admin.ifStudentIDExist("002",studentInfo ));
		assertFalse(admin.ifStudentIDExist("003",studentInfo ));
	}
	
	@Test
	//test if the entered professor user name existed
	void testIfProfessorUsernameExist() {
		assertTrue(admin.ifProfessorUsernameExist("Greenberg",professorInfo ));
		assertTrue(admin.ifProfessorUsernameExist("Krakowsky",professorInfo ));
		assertFalse(admin.ifProfessorUsernameExist("abcdefg",professorInfo ));

	}
	
	@Test
	//test if the entered professor ID existed
	void testIfProfessorIDExist() {
		assertTrue(admin.ifProfessorIDExist("001",professorInfo ));
		assertTrue(admin.ifProfessorIDExist("011",professorInfo ));
		assertTrue(admin.ifProfessorIDExist("013",professorInfo ));
		assertFalse(admin.ifProfessorIDExist("099",professorInfo ));

	}
	
	@Test
	//test if the professor can be found by ID
	void testIfProfessorExist() {
		assertEquals("Greenberg",admin.ifProfessorExist("001",professorInfo ).getUsername());
		assertEquals("001",admin.ifProfessorExist("001",professorInfo ).getID());
		assertEquals("Clayton Greenberg",admin.ifProfessorExist("001",professorInfo ).getName());
		assertEquals("password590",admin.ifProfessorExist("001",professorInfo ).getPassword());
		
		// the ID '055' does not exist, so the return value is null
		assertEquals(null, admin.ifProfessorExist("055",professorInfo ));

	}
	
	@Test
	// test: get course by course ID
	void testGetCourseObj() {
		assertEquals( "CIT590",admin.getCourseObj("cit590",courses).getCourseID());
		assertEquals( "Programming Languages and Techniques",admin.getCourseObj("CIT590",courses).getCourseName());
		assertEquals( "Brandon L Krakowsky",admin.getCourseObj("cit590",courses).getLecturer());

	}
	
	@Test
	// test: if a course exist
	void testCourseExist() {
		assertTrue(admin.courseExist("cit590",courses));
		assertFalse(admin.courseExist("CIT500",courses));

	}
	
	@Test
	//test if the course has a time conflict with all of the lecturer's other courses
	void testIfTimeConflict() {
		Course newCourse1 = new Course("CIT789", "Python", "Krakowsky", "MF", "9:00", "10:00", "100");
		Course newCourse2 = new Course("CIT100", "Python", "Krakowsky", "F", "16:30", "17:00", "100");

		// the course1 does not conflict with the course "CIT590" for Krakowsky
		assertEquals(null,admin.ifTimeConflict(newCourse1,"029",professorInfo));
		// the course2 conflicts with the course "CIT590" for Krakowsky
		assertEquals("CIT590",admin.ifTimeConflict(newCourse2,"029",professorInfo).getCourseID());

	}
	
	@Test
	//test: get professor name by his ID
	void testGetProfessorNameByID() {
		assertEquals("Brandon L Krakowsky",admin.getProfessorNameByID("029",professorInfo));
		// when the professor id does not exist, the function should return null
		assertEquals(null,admin.getProfessorNameByID("099",professorInfo));

	}
	
	// test if a course id exist
	@Test
	void testIfCourseIDExist() {
		assertTrue(admin.ifCourseIDExist(scanner,"CIT590",courses));
		assertFalse(admin.ifCourseIDExist(scanner,"CIT500",courses));

	}
	
	//test: if the lecturer is in the system
	@Test
	void testIsLecturerInSystem() {
		// the lecturer is in the system
		assertTrue(admin.isLecturerInSystem("029", professorInfo));
		// not in the system
		assertFalse(admin.isLecturerInSystem("055", professorInfo));

	}
	
	//test: if the capacity or ID only contains integer
	@Test
	void testIsNumeric() {
		assertTrue(admin.isNumeric("15"));
		assertFalse(admin.isNumeric("abc"));

	}
	
	//test : if the entered date is correct(only contains:"MTWRF") and not duplicate
	@Test
	void testCheckString() {
		assertTrue(admin.checkString("MTWRF"));
		//duplicate
		assertFalse(admin.checkString("MTWRFF"));
		// not correct
		assertFalse(admin.checkString("ABC"));


	}
	
	@Test
	// test the process of verifying user names and passwords
	void testAuthenticate() {

		//verify the user name and password are entered correctly and incorrectly
       assertEquals(admin.getID(), Admin.authenticate(adminInfo, "admin01", "password590").getID());
       assertEquals(admin.getName(), Admin.authenticate(adminInfo, "admin01", "password590").getName());
       assertEquals(admin.getUsername(), Admin.authenticate(adminInfo, "admin01", "password590").getUsername());
       assertEquals(admin.getPassword(), Admin.authenticate(adminInfo, "admin01", "password590").getPassword());


	}
}

