
import java.util.ArrayList;
import java.util.Scanner;

import courses.Course;
import roles.User;
import roles.Student;
import roles.Professor;
import roles.Admin;

import files.FileInfoReader;

public class Controller {
	
	/*
	 * main menu
	 */
	public static void menu() {
		System.out.println("-------------------------");
		System.out.println("Student Management System");
		System.out.println("-------------------------");
		System.out.println("1 -- Login as a student");
		System.out.println("2 -- Login as a professor");
		System.out.println("3 -- Login as an admin");
		System.out.println("4 -- Quit the system");
		System.out.println();
		System.out.println("Please enter your option,eg. '1'.");


	}
	
	
	public static void main(String[] args) {
		//Load the main menu

		Scanner scanner = new Scanner(System.in);
		
		//create ArrayLists to store the information of students, professors and administrators
		ArrayList<Student> studentInfo = new ArrayList<Student>();
		ArrayList<Professor> professorInfo = new ArrayList<Professor>();
		ArrayList<Admin> adminInfo = new ArrayList<Admin>();
		ArrayList<Course> courses = new ArrayList<Course>();
		
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
		
		boolean ifQuitSystem = false;

		/**
		 * Handles the main login process and user type selection.
		 * Users are prompted to enter a choice to log in as a student, professor, or administrator,
		 * or to quit the system. Based on the user's input, they are further prompted for username
		 * and password and, if authenticated successfully, directed to their respective interfaces.
		 */
		while(!ifQuitSystem) {
		while(true) {
			Controller.menu();
			String answer = scanner.nextLine();

			//log in as a student
			if(answer.equals("1")) {
			    System.out.println("Please enter your username, or type 'q' to quit.");
			    String inputUsername = scanner.nextLine();
			    if(inputUsername.equals("q")) {
			        break;
			    }
			    System.out.println("Please enter your password, or type 'q' to quit.");
			    String inputPassword = scanner.nextLine();
			    if(inputPassword.equals("q")) {
			        break;
			    }
			    
			    // validate the call and return the Student object
			    Student student = Student.authenticate(studentInfo, inputUsername, inputPassword);
			    
			    if(student != null) {
			        System.out.println("Login successful!");

			        // go to student page
			        student.getResponse(scanner, courses, studentInfo, professorInfo);
			    }
			    else {
			        System.out.println("Invalid username or password");

			    }
			}
			
			//log in as a professor
			else if(answer.equals("2")) {
				System.out.println("Please enter your username, or type 'q' to quit.");
			    String inputUsername = scanner.nextLine();
			    if(inputUsername.equals("q")) {
			        break;
			    }
			    System.out.println("Please enter your password, or type 'q' to quit.");
			    String inputPassword = scanner.nextLine();
			    if(inputPassword.equals("q")) {
			        break;
			    }
			    
			    // validate the call and return the Professor object
			    Professor professor = Professor.authenticate(professorInfo, inputUsername, inputPassword);
			    
			    if(professor != null) {
			        System.out.println("Login successful!");
			        professor.getResponse(scanner, courses, studentInfo, professorInfo);
			    }
			    else {
			        System.out.println("Invalid username or password");

			    }
			}
			//log in as an administrator
			else if(answer.equals("3")) {
				System.out.println("Please enter your username, or type 'q' to quit.");
			    String inputUsername = scanner.nextLine();
			    if(inputUsername.equals("q")) {
			        break;
			    }
			    System.out.println("Please enter your password, or type 'q' to quit.");
			    String inputPassword = scanner.nextLine();
			    if(inputPassword.equals("q")) {
			        break;
			    }
			    
			    // validate the call and return the Administrator object
			    Admin admin = Admin.authenticate(adminInfo, inputUsername, inputPassword);
			    
			    if(admin != null) {
			    	System.out.println("Login successful");
			    	admin.getResponse(scanner, courses, studentInfo, professorInfo);

			    }
			    else {
			        System.out.println("Invalid username or password");

			    }
			    
			}
			
			//quit the system
			else if(answer.equals("4")) {
				ifQuitSystem = true;
				break;
			}
			else {
				System.out.println("please enter an integer between 1-4");

			}
		}
		}
		scanner.close();
		System.out.println("Syetem closed.");
	}
	

}
