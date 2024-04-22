package courses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import roles.Student;

public class Course {
	private String courseID;
	private String courseName;
	private String lecturer;
	private String days;
	private String startTime;
	private String endTime;
	private String capacity;
	public ArrayList<Student> students;


	
	public Course(String id, String name, String lecturer, String days, String startTime, String endTime, String capacity) {
		this.courseID = id;
		this.courseName = name;
		this.lecturer = lecturer;
		this.days = days;
		this.startTime = startTime;
		this.endTime = endTime;
		this.capacity = capacity;
		students = new ArrayList<Student>();

	}

	//function
	
	//getter and setter
	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getLecturer() {
		return lecturer;
	}

	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public int getStartTime() {
		//convert string to integer
		String numericTime = startTime.replace(":","");
		return Integer.parseInt(numericTime);
	}
	public String getStringStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		//convert string to integer
		String numericTime = endTime.replace(":", "");
		return Integer.parseInt(numericTime);
	}
	
	public String getStringEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	
	public void addStudent(Student student) {
		students.add(student);
	}
	
	public void removeStudent(Student student) {
		students.remove(student);
	}
	
	public ArrayList<Student> getStudent() {
		return students;
	}
	
	public int getNumStudents() {
		if(students!=null) {
		return students.size();
		}
		else {
			return 0;
		}
	}


	
	// others
	
	/**
	 * Checks if two strings have any characters in common.
	 * This method is useful for comparing strings in a case-insensitive manner
	 * and does not consider the order or frequency of characters.
	 *
	 * @param str1 The first string to compare.
	 * @param str2 The second string to compare.
	 * @return true if there is at least one common character between the two strings, false otherwise.
	 */
	public static boolean haveSameLetters(String str1, String str2) {
		// convert the string to lowercase and store it in a HashSet to 
		//remove duplicates and ignore the order
		Set<Character> set1 = new HashSet<>();
		Set<Character> set2 = new HashSet<>();
		
        for (char c : str1.toLowerCase().toCharArray()) {
            set1.add(c);
        }
        for (char c : str2.toLowerCase().toCharArray()) {
            set2.add(c);
        }
        
        // check if set1 and set2 have the same elements
        return !Collections.disjoint(set1, set2);

	}
	
	/**
	 * Checks if a given course has a scheduling conflict with any course in a list.
	 * A conflict is determined if two courses are scheduled on the same day and their
	 * time slots overlap.
	 *
	 * @param course The course to check for conflicts.
	 * @param courses A list of courses to compare against.
	 * @return The conflicting course if a conflict is found, null if there is no conflict.
	 */
	public static Course checkIfConflict(Course course, ArrayList<Course> courses) {
		if(courses.isEmpty()) {
			return null;
		}
		// Iterate over each course in the list
		for(Course c: courses) {
			if(haveSameLetters(c.getDays(), course.getDays())) {
				
	            // Check for overlapping time slots
	            // Condition 1: Course starts during another course
	            // Condition 2: Course ends during another course
				if((course.getStartTime()>=c.getStartTime()&&course.getStartTime()<c.getEndTime())||
					(course.getEndTime()>c.getStartTime()&&course.getEndTime()<=c.getEndTime())) {
					
					//return the course that conflicts with the time of adding the course
					return c;
				}
				
			}
		}
		// If no conflicts are found after checking all courses, return null
		return null;
	}

	/**
	 * Searches for a course in a provided list of courses by its course ID.
	 * The comparison is case-insensitive, which means 'CIS101' is considered
	 * equivalent to 'cis101'.
	 *
	 * @param courseID The ID of the course to search for.
	 * @param courses A list of courses to search within.
	 * @return The Course object if found, null if no course matches the given ID.
	 */
	public static Course searchCourse(String courseID, ArrayList<Course> courses) {
		// Iterate over each course in the provided list
		for(Course c: courses) {
			// Compare the course ID case-insensitively with the given course I
			if(c.getCourseID().equalsIgnoreCase(courseID)) {
				// If a match is found, return the corresponding Course objec
				return c;
			}
		}
		return null;
	}
	
	/**
	 * Checks if a given course has already been selected by students.
	 * This method verifies if a course is present in a list of selected courses.
	 *
	 * @param course The course to check for selection.
	 * @param courses A list of courses that have been selected by students.
	 * @return Returns null if the course is found in the list (indicating it has been selected),
	 *         otherwise returns the original course indicating it has not been selected.
	 */
	public static Course checkIfSelected(Course course, ArrayList<Course> courses) {
		
		 // Check if the list of courses is null or empty, implying no courses have been selected
		if(courses == null) {
			return course;
		}
		// Iterate over the list of selected courses
		for(Course c: courses) {
			if(c.getCourseID().equalsIgnoreCase(course.getCourseID())) {
				return null;// Return null, indicating the course has already been selected
			}
		}
		return course;
		
	}
	
	public String toString() {
		return getCourseID()+"|"+getCourseName()+ ", " + 
	getStringStartTime()+ "-"+ getStringEndTime()+ " on " + 
				getDays()+ ", with course capacity: " + 
	getCapacity()+", students: "+ getNumStudents()+", lecturer: "+
				getLecturer();
	}

}
