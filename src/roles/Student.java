package roles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import courses.Course;
import files.FileInfoReader;

public class Student extends User{


	//initialize
	public Student(String id, String name, String username, 
			String password, HashMap<String, String> grades) {
		setID(id);
        setname(name);
        setUsername(username);
        setPassword(password);
        setGrades(grades);
	}
	
	

	public void userMenu1() {
		System.out.println("-------------------------");
		System.out.println("  Welcome, "+super.getName());
		System.out.println("-------------------------");
	}
	
    // student menu
	public void userMenu2() {
		System.out.println("1 -- View all courses");
		System.out.println("2 -- Add courses to your list");
		System.out.println("3 -- View enrolled courses");
		System.out.println("4 -- Drop courses in your list");
		System.out.println("5 -- View grades");
		System.out.println("6 -- Return to previous menu");
		System.out.println();
		System.out.println("Please enter your option,eg. '1'");
	}

	/**
	 * Handles user interactions for various administrative and academic operations.
	 * This method continuously displays a menu and processes user input to perform actions such as
	 * viewing courses, adding courses to a student's list, viewing enrolled courses, dropping courses,
	 * viewing grades, or exiting to the previous menu.
	 *
	 * @param scanner The Scanner object used to read user input.
	 * @param courses The list of all courses available in the system.
	 * @param students The list of all students registered in the system.
	 * @param professors The list of all professors in the system.
	 */
	@Override
	public void getResponse(Scanner scanner, ArrayList<Course> courses
			,ArrayList<Student> students, ArrayList<Professor> professors) {
		System.out.println();
        userMenu1();
		while(true) {
			//print the menu
	        userMenu2();

			String response = scanner.nextLine();
			
			// view all courses
			if(response.equals("1")) {
		        for(Course course: courses) {
		        	System.out.println(course);
		        }
				System.out.println();

			}
			//add courses to the student's list
			else if(response.equals("2")) {
				option2(scanner,courses);
			}
			//view enrolled courses
			else if(response.equals("3")) {
				option3();
				
			}
			//drop courses in your list
			else if(response.equals("4")) {
				option4(scanner);
				
			}
			//view grades
			else if(response.equals("5")) {
				option5(courses);
				
			}
			//return to previous menu
			else if(response.equals("6")) {
	        	System.out.println();

				break;
			}
			else {
				System.out.println("please enter an integer between 1-6");
			}
		}

	}
	
	
	/**
	 * Adds courses to a student's list after validating for existence, selection status, and time conflicts.
	 * Users can select courses by their IDs and add them to their list if they pass the validation checks.
	 * This function loops continuously, allowing multiple courses to be added until the user decides to exit.
	 *
	 * @param scanner The Scanner object used to read user input.
	 * @param courses The list of all courses available for selection.
	 */
	public  void option2(Scanner scanner, ArrayList<Course> courses) {
		while(true) {
			System.out.println("Please select the course ID you want to add to your list, eg.'CIT590'.");
			System.out.println("Or enter 'q' to return to the previous menu.");
			
			String courseID = scanner.nextLine();
			
			// return to the previous menu
			if(courseID.equals("q")) break;

			//check if the course exist
			Course newCourse = Course.searchCourse(courseID, courses);
			if(newCourse != null) {
				
				// check if the course has been selected
				newCourse = Course.checkIfSelected(newCourse, getCourses());
				if(newCourse!= null) {
					// check if the course has a time conflict with another added course
					Course ConflictCourse = Course.checkIfConflict(newCourse, getCourses());
					
					//no conflict
					if(ConflictCourse == null) {
						addCourse(newCourse);
						newCourse.addStudent(this);
						System.out.println("Course added successfully");
					}
					else {
						System.out.println("The course you selected has time conflict with "+
					ConflictCourse.getCourseID()+" "+
					ConflictCourse.getCourseName());

					}
					
				}
				else {
					System.out.println("The course you selected is already in your list");
					System.out.println();
				}

			}
			
			else {
				System.out.println("The course dose not exist.");
				System.out.println();

			}
		}
	}
	
	/**
	 * Displays a list of courses that the user (typically a student) is currently enrolled in.
	 * This method retrieves the list of courses from the student's profile and prints each one.
	 */
	public void option3() {
		ArrayList<Course> courses = getCourses();
		System.out.println("The courses in your list: ");
		for(Course c: courses) {
			System.out.println(c);
		}
		System.out.println();
	}
	
	/**
	 * Allows a student to drop courses they have previously added to their list.
	 * This method displays all the courses currently enrolled by the student and prompts for an ID of the course
	 * they wish to drop. The student can continue to drop courses or exit to the previous menu by typing 'q'.
	 *
	 * @param scanner The Scanner object used to read user input, facilitating interaction in a console environment.
	 */
	public void option4(Scanner scanner) {
		
		while(true) {
			// print the courses the student has added
			System.out.println();
			ArrayList<Course> courses = getCourses();
			System.out.println("The courses in your list: ");
			for(Course c: courses) {
				System.out.println(c);
			}
			System.out.println();
			
			System.out.println("Please enter the ID of the course which you want to drop, eg 'CIT591'");
			System.out.println("Or enter 'q' to return to the previous menu");
			String courseID = scanner.nextLine();
			if(courseID.equals("q")) {
				break;
			}
			ifInListAndRemove(courses, courseID);


		}

	}
	/**
	 * Displays the grades for courses that the student has taken.
	 * This method fetches the student's grades from a hashmap where each course ID is mapped to a letter grade.
	 * It then displays each course along with its name and the corresponding grade.
	 *
	 * @param courses An ArrayList of Course objects that may include all available courses in the system.
	 *                This is used to fetch the course name based on its ID for a more descriptive output.
	 */
	public void option5( ArrayList<Course> courses) {
		System.out.println("Here are the courses you already taken, with your grade in a letter format: ");
		HashMap<String,String> grades = this.getGrades();
		for(Map.Entry<String, String> entry: grades.entrySet()) {
			String course = entry.getKey();
			String grade = entry.getValue();
			String courseName = getCourseNameByID(course, courses);
			System.out.println("Grade of "+ course +" "+ courseName + ": "+ grade);
		}

	}
	
	/**
	 * Retrieves the name of a course from a list of courses based on the specified course ID.
	 * This method performs a linear search through an ArrayList of Course objects, comparing each
	 * course's ID to the provided ID. If a match is found, it returns the name of the course.
	 *
	 * @param courseID The ID of the course whose name is being sought.
	 * @param courses An ArrayList of Course objects to be searched.
	 * @return The name of the course if a matching ID is found, otherwise returns null.
	 */
	public String getCourseNameByID(String courseID, ArrayList<Course> courses) {
		for(Course c : courses) {
			if(c.getCourseID().equals(courseID)){
				return c.getCourseName();
			}
		}
		return null;
	}
	
	/*
	 * check if a course is in the student's list and remove it
	 * @Param courses All courses in a student's list
	 * @Param course  The course searched
	 */
	public void ifInListAndRemove(ArrayList<Course> courses, String courseID) {
		Course findCourse = null;
		//find the course needed to be removed
		for(Course c: courses) {
			if(c.getCourseID().equalsIgnoreCase(courseID)){
				findCourse = c;
				break;
			}
		}
		
		if(findCourse!=null) {
			courses.remove(findCourse);
			findCourse.removeStudent(this);
			System.out.println("Successfully drop :"+ courseID+" "+ findCourse.getCourseName());
		}
		else {
			System.out.println("The course isn't in your schedule.");

		}
			
	}
	
	/**
	 * Authenticates a student by verifying the username and password against a list of registered students.
	 * This method iterates over a list of Student objects, comparing the provided username and password
	 * against those stored for each student. The comparison is case-sensitive, which means the username
	 * and password must match exactly with what is stored.
	 *
	 * @param studentInfo An ArrayList of Student objects containing the credentials of registered students.
	 * @param username The username entered by the user attempting to authenticate.
	 * @param password The password entered by the user attempting to authenticate.
	 * @return The Student object if a matching username and password are found, null if no match is found.
	 */
	public static Student authenticate(ArrayList<Student> studentInfo, String username, String password) {
	    for (Student student : studentInfo) {
	        if (student.getUsername().equals(username) && student.getPassword().equals(password)) {


	            return student;  // returns the matching Student object
	        }
	    }

	    return null;  // Not match found, return null
	}
	
	@Override
	public String toString() {
		return "Student{" +
	               "ID='" + getID() + '\'' +
	               ", Name='" + getName() + '\'' +
	               ", Username='" + getUsername() + '\'' +
	               ", Grades=" + getGrades() +
	               '}';
	}

	
	

}
