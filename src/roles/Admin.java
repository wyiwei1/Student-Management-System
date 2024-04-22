package roles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import courses.Course;

public class Admin extends User{
	
	//initialize
	public Admin(String id,String name,String username, String password) {
		setUsername(username);
		setID(id);
		setname(name);
		setPassword(password);
	}

    /*
     * print the administrator menu
     */
	public void userMenu1() {
		System.out.println("-------------------------");
		System.out.println("     Welcome, "+super.getName());
		System.out.println("-------------------------");
	}
	
	public void userMenu2() {
		System.out.println("1 -- View all courses");
		System.out.println("2 -- Add new courses");
		System.out.println("3 -- Delete courses");
		System.out.println("4 -- Add new professor");
		System.out.println("5 -- Delete professor");
		System.out.println("6 -- Add new student");
		System.out.println("7 -- Delete student");
		System.out.println("8 -- Return to previous menu");
		System.out.println();
		System.out.println("Please enter your option,eg. '1'");
	}

	
	/**
	 * Handles user interactions based on various menu options. Allows the user to view, add, or delete
	 * courses, professors, and students, or to exit the menu.
	 * This method continuously displays the menu and processes user input until the user chooses to exit.
	 *
	 * @param scanner The scanner object for reading user input.
	 * @param courses The list of courses available.
	 * @param students The list of students.
	 * @param professors The list of professors.
	 */
	@Override
	public void getResponse(Scanner scanner, ArrayList<Course> courses,
			ArrayList<Student> students, ArrayList<Professor> professors) {
		userMenu1();

		while(true) {
			userMenu2();

			String response = scanner.nextLine();
			// view all courses 
			if(response.equals("1")) {
				for(Course course: courses) {
		        	System.out.println(course);
		        }
				System.out.println();

			}
			// add new courses
			else if(response.equals("2")) {
				option2(scanner, courses, professors);

			}
			// delete courses
			else if(response.equals("3")) {
				option3(scanner,courses, students, professors);

			}
			//add new professor
			else if(response.equals("4")) {
				option4(scanner, professors);

			}
			//delete professor
			else if(response.equals("5")) {
				option5(scanner, professors);

			}
			// add new student
			else if(response.equals("6")) {
				option6( scanner, professors, courses, students);

			}
			//delete student
			else if(response.equals("7")) {
				option7(scanner, students, courses);

			}
			//return to previous menu
			else if(response.equals("8")) {
				System.out.println();
				break;
			}
			else {
				System.out.println("please enter an integer between 1-6");
			}
		}
	}
	/**
	 * Adds a new course based on user input. Includes validation for each field of the course
	 * and checks for time conflicts with existing courses for the same lecturer.
	 *
	 * @param scanner The scanner for reading user input.
	 * @param courses The list of existing courses to check against and add to.
	 * @param professors The list of existing professors to validate lecturer IDs and add new course info.
	 */
	public void option2(Scanner scanner, ArrayList<Course> courses,ArrayList<Professor> professors) {
		while(true) {
			System.out.println("Please enter the course ID, or type 'q' to end");
			String courseID = scanner.nextLine();
			if(courseID.equals("q")) {
				break;
			}
			// Check if the course ID already exists
			if(ifCourseIDExist(scanner, courseID, courses)) {
				System.out.println("the course already exist");
				System.out.println("Please enter the course ID, or type 'q' to end");
				courseID = scanner.nextLine();
			}
			
			System.out.println("Please enter the course name, or type 'q' to end");
			String courseName = scanner.nextLine();
			if(courseName.equals("q")) {
				break;
			}
			
			System.out.println("Please enter the course start time, or type 'q' to end. eg.'19:00'");
			String startTime = scanner.nextLine();
			if(startTime.equals("q")) {
				break;
			}
			//check that the entered time format is correct
			while(!startTime.contains(":")) {
				System.out.println("The time format you entered is not correct, please enter again");
				startTime = scanner.nextLine();
			}
			
			System.out.println("Please enter the course end time, or type 'q' to end. eg. '20:00'");
			String endTime = scanner.nextLine();
			if(endTime.equals("q")) {
				break;
			}
			while(!endTime.contains(":")) {
				System.out.println("The time format you entered is not correct, please enter again");
				endTime = scanner.nextLine();
			}
			
			System.out.println("Please enter the course date, or type 'q' to end. eg.'MW'");
			String date = scanner.nextLine().toUpperCase();
			if(date.equals("Q")) {
				break;
			}
			//check that the entered date is correct and does not duplicate
			while(!checkString(date)) {
				System.out.println("The dates you entered are invalid or duplicate, please enter again:");
				date = scanner.nextLine().toUpperCase();
			}
			
			System.out.println("Please enter the course capacity, or type 'q' to end. eg.'72'");
			String capacity = scanner.nextLine();
			if(capacity.equals("q")) {
				break;
			}
			//check if the capacity only contains integer
			while(!isNumeric(capacity)) {
				System.out.println("The capacity you entered is not an integer, please enter again");
				capacity = scanner.nextLine();
			}
			
			System.out.println("Please enter the course lecturer's id, or type 'q' to end. eg. '001'");
			String lecturerID = scanner.nextLine();
			if(lecturerID.equals("q")) {
				break;
			}
			//check if the lecturer's id is numeric
			while(!isNumeric(lecturerID)) {
				System.out.println("The lecturer's ID you entered is not numeric, please enter again");
				lecturerID = scanner.nextLine();
			}
			//check if the lecturer is in the system
			// add the lecturer if the lecturer is not in the system
			if(!isLecturerInSystem(lecturerID, professors)) {
				System.out.println("The professor isn't in the system, please add this professor first.");
                if(!option4(scanner,professors)) {
                	break;
                }
			}
			
			String professorName = getProfessorNameByID(lecturerID, professors);
			
			// add the new course to course list
			Course newCourse = new Course(courseID, courseName,professorName , date, startTime, endTime, capacity);
			
			//check if the course has a time conflict with all of the lecturer's other courses
			if(ifTimeConflict(newCourse, lecturerID, professors)!=null) {
				System.out.println("The new added course has time conflict with course: "+ 
			ifTimeConflict(newCourse, lecturerID, professors));
				System.out.println(" Unable to add new course: "+ 
						ifTimeConflict(newCourse, lecturerID, professors));
				break;
			}
			
			courses.add(newCourse);
			System.out.println("Successfully add the new course: "+newCourse.getCourseName());
			// add the new course to the professor's information
			for(Professor p: professors) {
				if(p.getID().equals(lecturerID)) {
					p.addCourse(newCourse);
					break;
				}
			}
		}
		
	}
	/**
	 * Deletes a specified course by ID from the list of courses and updates related records in students and professors.
	 * The user is prompted to enter a course ID to delete until they choose to exit by typing 'q'.
	 *
	 * @param scanner Scanner to read user input.
	 * @param courses ArrayList of courses from which the course will be deleted.
	 * @param students ArrayList of students from whom the deleted course will be removed.
	 * @param professors ArrayList of professors from whom the deleted course will be removed.
	 */
	public void option3(Scanner scanner,ArrayList<Course> courses, 
			ArrayList<Student> students, ArrayList<Professor> professors) {
		while(true) {
			System.out.println("Please enter the course you want to delete, or type 'q' to quit ");
			String deleteCourseID = scanner.nextLine().toUpperCase();
			if(deleteCourseID.equalsIgnoreCase("q")) {
				break;
			}
			//check if the course is exist
			while(!courseExist(deleteCourseID,courses)) {
				// course not exist
				System.out.println("the course does not exist, please enter again, or type 'q' to quit");
				deleteCourseID = scanner.nextLine().toUpperCase();
				if(deleteCourseID.equalsIgnoreCase("q")) {
					break;
				}
			}
			if(deleteCourseID.equalsIgnoreCase("q")) {
				break;
			}
			
			//the deleting course exist
			Course deleteCourse = getCourseObj(deleteCourseID,courses);
			courses.remove(deleteCourse);
			
			// delete the course in students' information
			for(Student s:students) {
				if(s.getCourses().contains(deleteCourse)) {
					s.deleteCourse(deleteCourse);
				}
			}
				
			// delete the course in the professor's information
			for(Professor p: professors) {
				if(p.getCourses().contains(deleteCourse)) {
					p.deleteCourse(deleteCourse);
				}
			}
			System.out.println("delete the course successfully!");
		}
	}
	/**
	 * Adds a new professor to the list after validating the input for ID and username uniqueness.
	 * This method prompts the user to enter various details about the professor such as ID, name,
	 * username, and password. It checks for valid numeric ID and unique username before addition.
	 *
	 * @param scanner The Scanner object to read input from the console.
	 * @param professors The list of current professors to which the new professor will be added.
	 * @return true if the professor was successfully added, false if the operation was terminated prematurely.
	 */
	public boolean option4(Scanner scanner, ArrayList<Professor> professors) {
		while(true) {
			System.out.println("Please enter the professor's ID, or type 'q' to end");
			String professorID = scanner.nextLine();
			if(professorID.equals("q")) {
				return false;
			}
			//check if the ID only contains numbers
			while(!isNumeric(professorID)) {
				System.out.println("The ID you entered is invalid, please enter again:");
				professorID = scanner.nextLine();
			}
			
			//check if the ID already exists
			while(ifProfessorIDExist(professorID,professors)) {
				System.out.println("The ID already exists");
				System.out.println("Please enter the professor's ID, or type 'q' to quit");
				professorID = scanner.nextLine();
				if(professorID.equals("q")) {
					return false;
				}
			}
			
			System.out.println("Please enter the professor's name, or type 'q' to end");
			String professorName = scanner.nextLine();
			if(professorName.equals("q")) {
				return false;
			}
			
			System.out.println("Please enter a username");
			String username = scanner.nextLine();
			
			//check if the user name already exists
			while(ifProfessorUsernameExist(username, professors)) {
				System.out.println("The username you entered is not available");
				System.out.println("Please enter a username");
				username = scanner.nextLine();
			}
			

			
			System.out.println("Please enter a password");
			String password = scanner.nextLine();
			
			// Create and add the new professor to the list
	        Professor newProfessor = new Professor(professorName, professorID, username, password);
	        professors.add(newProfessor);
			System.out.println("Successfully added the new professor: "+ professorID+" "+ professorName);
			return true;
	
			
		}
	}
	/**
	 * Deletes a professor from the list based on the ID provided by the user.
	 * The user is prompted to enter a professor's ID. The method checks if the professor exists
	 * and removes them from the list if they do.
	 *
	 * @param scanner The Scanner object to read user input.
	 * @param professors The list of professors from which a professor will be deleted.
	 */
	public void option5(Scanner scanner, ArrayList<Professor> professors) {
		while(true) {
			System.out.println("Please enter the professor's id to delete a professor, or type 'q' to exist");
			String professorID = scanner.nextLine();
			if(professorID.equalsIgnoreCase("q")) {
				break;
			}
			while(ifProfessorExist(professorID,professors)==null) {
				System.out.println("the professor you entered does not exist, please enter again, or type 'q' to exist");
				professorID = scanner.nextLine();
				if(professorID.equalsIgnoreCase("q")) {
					break;
				}
			}
			if(professorID.equalsIgnoreCase("q")) {
				break;
			}
			System.out.println("Successfully delete the professor: "+ professorID+" "
			+ ifProfessorExist(professorID,professors).getName());
			professors.remove(ifProfessorExist(professorID,professors));


		}
	}
	
	/**
	 * Adds a new student to the system with detailed verification of each field.
	 * The user is prompted to enter student details like ID, name, username, and password.
	 * Additionally, the user can enter courses that the student has taken along with their grades.
	 *
	 * @param scanner Scanner to read user input.
	 * @param professors List of professors (not used in current implementation).
	 * @param courses List of available courses to ensure valid course entries.
	 * @param students List of existing students to check for ID and username uniqueness.
	 * @return true if a new student is added successfully, false if the operation is terminated prematurely.
	 */
	public boolean option6(Scanner scanner, ArrayList<Professor> professors, ArrayList<Course> courses, ArrayList<Student> students) {
		while(true) {
			System.out.println("Please enter the student's ID, or type 'q' to end");
			String studentID = scanner.nextLine();
			if(studentID.equals("q")) {
				return false;
			}
			//check if the ID only contains numbers
			while(!isNumeric(studentID)) {
				System.out.println("The ID you entered is invalid, please enter again:");
				studentID = scanner.nextLine();
			}
			//check if the student id existed
			while(ifStudentIDExist(studentID, students)) {
				System.out.println("The student ID already existed, please enter again, or type 'q' to end");
				studentID = scanner.nextLine();
				if(studentID.equals("q")) {
					return false;
				}
			}
			
			System.out.println("Please enter the student's name, or type 'q' to end");
			String studentName = scanner.nextLine();
			if(studentName.equals("q")) {
				return false;
			}
			
			System.out.println("Please enter a username");
			String username = scanner.nextLine();
			
			//check if the student user name existed
			while(ifStudentUsernameExist(username, students)) {
				System.out.println("The student username already existed, please enter again, or type 'q' to end");
				username = scanner.nextLine();
				if(username.equals("q")) {
					return false;
				}
			}
			
			System.out.println("Please enter a password");
			String password = scanner.nextLine();
			
			//first create a new student who has no grades
			Student newstudent = new Student(studentID, studentName, username, 
					password, new HashMap<String, String>());
			while(true) {
				
				//enter courses ID the student took
				System.out.println("Please enter ID of a course which this student already took, one in a time ");
				System.out.println("Type 'q' to quit, type 'n' to stop adding");
				String courseID = scanner.nextLine().toUpperCase();
				if(courseID.equalsIgnoreCase("q")) {
					return false;
				}
				else if(courseID.equalsIgnoreCase("n")) {
					break;
				}
				if(!courseExist(courseID,courses)) {
					System.out.println("The course you entered does not exist, please enter again");
					continue;
				}
				
				//enter the grades of courses
				System.out.println("Please enter the grade, eg, 'A'");
				String grade = scanner.nextLine();
				newstudent.addGrades(courseID, grade);
			}
			students.add(newstudent);
			System.out.println("Successfully added the new student:"+ studentID+" "+studentName);
		}
	}
	
	/**
	 * Deletes a student from the list and removes them from any courses they are enrolled in.
	 * The user is prompted to enter a student's ID. The method checks if the student exists
	 * and removes them if they do, along with updating course enrollments to reflect the deletion.
	 *
	 * @param scanner The Scanner object to read user input.
	 * @param students The list of students from which the student will be deleted.
	 * @param courses The list of courses to update the enrollments for the deleted student.
	 */
	public void option7(Scanner scanner, ArrayList<Student> students, ArrayList<Course> courses) {
		while(true) {
			System.out.println("Please enter the student's id to delete a student, or type 'q' to exist");
			String studentID = scanner.nextLine();
			if(studentID.equalsIgnoreCase("q")) {
				break;
			}
			// Check if the student exists before attempting deletion
			while(!ifStudentIDExist(studentID,students)) {
				System.out.println("the student ID you entered does not exist, please enter again, or type 'q' to exist");
				studentID = scanner.nextLine();
				if(studentID.equalsIgnoreCase("q")) {
					break;
				}
			}
			if(studentID.equalsIgnoreCase("q")) {
				break;
			}
			Student deleteStudent = getStudentObjByID(studentID, students);
			System.out.println("Successfully delete the student: "+ studentID+" "
			+ deleteStudent.getName());
			// Remove the student from the list if they exist
			students.remove(deleteStudent);
			for(Course c:courses) {
				if(c.getStudent().contains(deleteStudent)) {
					c.removeStudent(deleteStudent);
				}
			}


		}
	}
	
	/**
	 * Retrieves a Student object from a list based on the student's ID.
	 * This method performs a case-insensitive search through the list of students,
	 * returning the Student object if a match is found. If no match is found,
	 * the method returns null.
	 *
	 * @param studentID The ID of the student to search for. The search is case-insensitive.
	 * @param students An ArrayList of Student objects where the search will be performed.
	 * @return The Student object with the matching ID if found, or null if no match is found.
	 */
	public Student getStudentObjByID(String studentID, ArrayList<Student> students) {
		for(Student s: students) {
			if(s.getID().equalsIgnoreCase(studentID)) {
				return s;
			}
		}
		return null;
	}
	
	/**
	 * Checks if a given student username already exists in the list of students.
	 * This method performs a case-insensitive comparison of the provided username against all usernames
	 * in the list, ensuring that usernames are unique within the system.
	 *
	 * @param studentUsername The username to check for existence.
	 * @param students An ArrayList of Student objects where the username will be searched.
	 * @return true if the username exists, false otherwise.
	 */
	public boolean ifStudentUsernameExist(String studentusername, ArrayList<Student> students) {
		for(Student s: students) {
			if(s.getUsername().equalsIgnoreCase(studentusername)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if a given username already exists among a list of students.
	 * This method performs a case-insensitive search through the list of students,
	 * returning true if the username is found, indicating that it already exists.
	 *
	 * @param studentusername The username to check for existence among the students.
	 * @param students An ArrayList of Student objects to search within.
	 * @return true if the username exists in the list, false otherwise.
	 */
	public boolean ifStudentIDExist(String studentID, ArrayList<Student> students) {
		for(Student s: students) {
			if(s.getID().equalsIgnoreCase(studentID)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if a professor's username already exists in a list of professors.
	 * 
	 * @param professorUsername The username of the professor to check.
	 * @param professors A list of Professor objects.
	 * @return true if the username already exists in the list, false otherwise.
	 */
	public boolean ifProfessorUsernameExist(String professorUsername, ArrayList<Professor> professors) {
		for(Professor c:professors) {
		    if(c.getUsername().equalsIgnoreCase(professorUsername)) {
		    	//exist
		    	return true;
		    }
		}
		return false;
	}
	
	/**
	 * Checks if a given professor's ID already exists in a list of professors.
	 * This method is used to ensure that each professor has a unique ID within the system.
	 *
	 * @param professorID The ID of the professor to check.
	 * @param professors A list of Professor objects, representing all professors currently tracked.
	 * @return true if the ID already exists in the list, false otherwise.
	 */
	public boolean ifProfessorIDExist(String professorID, ArrayList<Professor> professors) {
		// Iterate through the list of professors
		for(Professor c:professors) {
		    if(c.getID().equalsIgnoreCase(professorID)) {
		    	//exist
		    	return true;
		    }
		}
		//not exist
		return false;
	}
	
	
	/**
	 * Checks if a professor exists in the provided list by their ID and returns the professor object if found.
	 * This method performs a case-insensitive comparison of the provided professor ID against all professor IDs
	 * in the list. It returns the professor object if a match is found, otherwise it returns null.
	 *
	 * @param professorID The ID of the professor to search for in the list.
	 * @param professors An ArrayList of Professor objects to be searched.
	 * @return The Professor object if a matching ID is found, null if no match is found.
	 */
	public Professor ifProfessorExist(String professorID, ArrayList<Professor> professors) {
		for(Professor p: professors) {
			if(p.getID().equalsIgnoreCase(professorID)) {
				//exist
				return p;
			}
		}
		//not exist
		return null;
	}
	
	/**
	 * Retrieves a Course object from a list based on the course's ID.
	 * This function performs a case-insensitive search through the provided list of courses,
	 * returning the Course object if a match is found. If no match is found,
	 * the method returns null, indicating the course does not exist in the list.
	 *
	 * @param courseID The ID of the course to search for. The search is case-insensitive.
	 * @param courses An ArrayList of Course objects where the search will be performed.
	 * @return The Course object with the matching ID if found, or null if no match is found.
	 */
	public Course getCourseObj(String courseID, ArrayList<Course> courses) {
		for(Course c: courses) {
			if(c.getCourseID().equalsIgnoreCase(courseID)) {
				return c;
			}
		}
		return null;
	}
	/**
	 * Checks if a course with a given ID exists in the provided list of courses.
	 * This method performs a case-insensitive comparison to determine if any course in the list
	 * matches the given ID. It is used to ensure that operations requiring a specific course,
	 * such as updates or deletions, are performed on existing entries.
	 *
	 * @param courseID The ID of the course to search for. The search is case-insensitive to ensure
	 *                 that differences in character casing do not affect the result.
	 * @param courses An ArrayList of Course objects to be searched.
	 * @return true if a matching course is found in the list, false otherwise.
	 */
	public boolean courseExist(String courseID, ArrayList<Course> courses) {
		for(Course c: courses) {
			if(c.getCourseID().equalsIgnoreCase(courseID)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if a given course has a scheduling conflict with any course taught by a specific professor.
	 * A conflict is determined if two courses overlap in their scheduled times.
	 * 
	 * @param course The new course to be checked for potential time conflicts.
	 * @param professorID The ID of the professor whose courses will be checked against the new course.
	 * @param professors A list of all professors, which is searched to find the relevant professor and their courses.
	 * @return The conflicting course if a conflict is found, null if there is no conflict.
	 */
	public Course ifTimeConflict(Course course, String professorID, ArrayList<Professor> professors) {

		for(Professor p: professors) {
			// find the professor
			if(p.getID().equalsIgnoreCase(professorID)) {
				//get the professor's courses
				ArrayList<Course> theProfessorCourse = p.getCourses();
				//if the professor has no course, the course certainly not has a time conflict
				if(theProfessorCourse.isEmpty()) {
					return null;
				}
				for(Course c: theProfessorCourse) {
					if((course.getStartTime()>c.getStartTime()&&course.getStartTime()<c.getEndTime())||
						(course.getEndTime()>c.getStartTime()&&course.getEndTime()<c.getEndTime())) {
						return c;
					}
				}
				//not conflict
				return null;

			}
		}
		return null;
	}
	
	/**
	 * Retrieves the name of a professor from a list based on the professor's ID.
	 * This function performs a case-insensitive search through the provided list of professors.
	 * If a professor with the matching ID is found, their name is returned. If no match is found,
	 * the method returns null, indicating that no professor with the given ID exists in the list.
	 *
	 * @param ID The ID of the professor whose name is to be retrieved.
	 * @param professors An ArrayList of Professor objects to be searched.
	 * @return The name of the professor if found, null if no professor with the given ID is found.
	 */
	public String getProfessorNameByID(String ID, ArrayList<Professor> professors) {
		for(Professor p: professors) {
			if(p.getID().equalsIgnoreCase(ID)) {
				return p.getName();
			}
		}
		return null;
	}

	/**
	 * Checks if a course ID already exists within a list of courses.
	 * This function performs a case-insensitive comparison to determine if any course in the list
	 * matches the given ID. It is typically used to prevent the duplication of course entries in the system.
	 *
	 * @param scanner The Scanner object used for reading user input, although it is not utilized within this function.
	 * @param courseID The ID of the course to check for existence.
	 * @param courses An ArrayList of Course objects where the search will be performed.
	 * @return true if the course ID exists in the list, indicating a duplicate; false otherwise.
	 */
	public boolean ifCourseIDExist(Scanner scanner, String courseID, ArrayList<Course> courses) {
		for(Course c: courses) {
			if(c.getCourseID().equalsIgnoreCase(courseID)) {
				// the course is already existed
				return true;
			}

		}
		//the course is not existed
		return false;
	}
	
	/**
	 * Determines whether a lecturer with a specified ID exists within a list of professors.
	 * This function checks each professor in the provided list to see if any of their IDs
	 * match the given lecturer ID exactly. It is used primarily to ensure that operations
	 * requiring a valid lecturer, such as assigning courses, are performed on existing records.
	 *
	 * @param lecturerID The ID of the lecturer to search for within the list of professors.
	 * @param professors An ArrayList of Professor objects, which is the list to be searched.
	 * @return true if a matching lecturer ID is found, indicating the lecturer is in the system; false otherwise.
	 */
	public boolean isLecturerInSystem(String lecturerID,ArrayList<Professor> professors) {
		for(Professor p: professors) {
			if(p.getID().equals(lecturerID)) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * check if the capacity or ID only contains integer
	 */
	public boolean isNumeric(String str) {
		return str.matches("\\d+");
	}
	
	/**
	 * Validates a string representing days of the week to ensure that each character is valid and not duplicated.
	 * This method checks if each character in the provided string is a valid day character ('M', 'T', 'W', 'R', 'F')
	 * and ensures that no characters are repeated. It is typically used for validating class schedules or similar
	 * entries that require non-duplicated, specific day markers.
	 *
	 * @param date The string representing days of the week. For example, "MWF" for Monday, Wednesday, Friday.
	 * @return true if all characters in the string are valid day markers and there are no duplicates; false otherwise.
	 */
	public boolean checkString(String date) {
		Set<Character> seenCharacters =  new HashSet<>();
		String validCharacters = "MTWRF";
		for(char ch: date.toCharArray()) {
			//check if the character is valid
			if(!validCharacters.contains(String.valueOf(ch))) {
				return false;
			}
			//check if the character is duplicate
			if(!seenCharacters.add(ch)) {
				return false;
			}
		}
		// all characters are valid and not duplicate
		return true;
	}

	/**
	 * Authenticates an administrator based on a username and password.
	 * This method checks the provided credentials against a list of registered administrators.
	 * It performs a case-sensitive comparison to determine if the provided username and password
	 * match those of any administrator in the list.
	 *
	 * @param AdminInfo An ArrayList of Admin objects containing registered administrator information.
	 * @param username The username entered by the user attempting to authenticate.
	 * @param password The password entered by the user attempting to authenticate.
	 * @return The Admin object if a matching username and password are found, null if there is no match.
	 */
	public static Admin authenticate(ArrayList<Admin> AdminInfo, String username, String password) {
	    for (Admin admin : AdminInfo) {
	        if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
	            return admin;  // returns the matching Administrator object
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
