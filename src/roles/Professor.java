package roles;

import java.util.ArrayList;
import java.util.Scanner;

import courses.Course;

public class Professor extends User{
	
	public Professor(String name,String id,String username, String password) {
		setUsername(username);
		setID(id);
		setname(name);
		setPassword(password);
	}

	public void userMenu1() {
		System.out.println("-------------------------");
		System.out.println("     Welcome, "+super.getName());
		System.out.println("-------------------------");
	}
	
	public void userMenu2() {
		System.out.println("1 -- View given courses");
		System.out.println("2 -- View student list of the given course");
		System.out.println("3 -- Return to the previous menu");
		System.out.println();
		System.out.println("Please enter your option,eg. '1'");


	}
	
	/**
	 * Handles user interactions based on menu selections in a console-based interface.
	 * This method displays a menu, reads user responses, and performs actions based on the selected option.
	 * Users can view a list of all courses, view the list of students for a specific course, or return to a previous menu.
	 *
	 * @param scanner Scanner object for reading user input from the console.
	 * @param courses ArrayList containing all available courses (used for displaying in option 1).
	 * @param students ArrayList containing all registered students (not directly used here but could be passed to methods).
	 * @param professors ArrayList containing all registered professors (not directly used here but could be passed to methods).
	 */
	@Override
	public void getResponse(Scanner scanner, ArrayList<Course> courses,
			ArrayList<Student> students, ArrayList<Professor> professors) {

		userMenu1();

		while(true) {
			userMenu2();
			String response = scanner.nextLine();
			//view given courses
			if(response.equals("1")) {
				System.out.println("--------The course list--------");
				ArrayList<Course> givenCourse = this.getCourses();
				for(Course c: givenCourse) {
					System.out.println(c);
				}
				System.out.println();

			}
			// view student list of the given course
			else if(response.equals("2")) {
				option2(scanner);

			}
			//return to the previous menu
			else if(response.equals("3")) {
	        	System.out.println();
				break;
			}

			else {
				System.out.println("please enter an integer between 1-6");
			}
		}
	}
	/**
	 * Allows users to view the list of students enrolled in a specific course.
	 * This method first displays all available courses, then prompts the user to enter a course ID.
	 * If the entered course ID matches one from the list, it displays all enrolled students. 
	 * The user can continue to query different courses or exit by typing 'q'.
	 *
	 * @param scanner Scanner object for reading user input from the console.
	 */
	public void option2(Scanner scanner) {
		System.out.println("--------The course list--------");
		ArrayList<Course> givenCourses = this.getCourses();
		for(Course c: givenCourses) {
			System.out.println(c);
		}
		while(true) {
			System.out.println();
			System.out.println("Please enter the course ID, eg. 'CIS519'");
			System.out.println("Or type 'q' to quit");
			String courseID = scanner.nextLine();
			if(courseID.equalsIgnoreCase("q")) {
				System.out.println();
				break;
			}
			boolean ifCourseExist = false;
			//check if the course is in the lecturer's Course list 
			for(Course c : givenCourses) {
				if(c.getCourseID().equalsIgnoreCase(courseID)) {
					ifCourseExist = true;
					// get the students taking the course
					ArrayList<Student>students = c.getStudent();
					
					if(students.isEmpty()) {
						System.out.println("No students are currently enrolled in this course");

					}
					else {
						System.out.println("Students in your course "+ c.getCourseID()+" "+ c.getCourseName()+": ");

						//print all students' ID and name
						for(Student s: students) {
							System.out.println(s.getID()+" " + s.getName());
					    }
					}
					
				}
			}
			if(!ifCourseExist) {
				System.out.println("This course is not in your course list");
			}
		}
	}
	
	
	/**
	 * Authenticates a professor by verifying the username and password against a list of professors.
	 * This method iterates over a list of Professor objects, comparing the provided username and password
	 * against those stored for each professor. The comparison is case-sensitive, which means the username
	 * and password must match exactly with what is stored.
	 *
	 * @param professorInfo An ArrayList of Professor objects containing the credentials of registered professors.
	 * @param username The username entered by the user attempting to authenticate.
	 * @param password The password entered by the user attempting to authenticate.
	 * @return The matching Professor object if the credentials are correct, null if no match is found.
	 */
	public static Professor authenticate(ArrayList<Professor> professorInfo, String username, String password) {
	    for (Professor professor : professorInfo) {
	        if (professor.getUsername().equals(username) && professor.getPassword().equals(password)) {
	            return professor;  // returns the matching Professor object
	        }
	    }
	    return null;  // Not match found, return null
	}
	
	@Override
	public String toString() {
		return "Professor{" +
	               "ID='" + getID() + '\'' +
	               ", Name='" + getName() + '\'' +
	               ", Username='" + getUsername() + '\'' +
	               ", Password=" + getPassword() +
	               '}';
	}

}
