package roles;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import courses.Course;


public abstract class User {
	
	private String name;
	
	private String username;
	
	private String password;
	
	private String ID;
	
	private HashMap<String, String> grades = new HashMap<String, String>();
	
	private ArrayList<Course> courses = new ArrayList<Course>();
	
	//Getters
	public String getUsername(){
		return this.username;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getID() {
		return ID;
	}
	
	public HashMap<String,String> getGrades(){
		return grades;
	}
	
	public ArrayList<Course> getCourses() {
		return courses;
	}
	
	//setters
	public void setname(String name) {
		this.name = name;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setID(String iD) {
		ID = iD;
	}
	
	public void setGrades(HashMap<String,String> grades) {
		this.grades = grades;
	}
	
	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}
	
	public void addCourse(Course course) {
		this.courses.add(course);
	}
	
	public void deleteCourse(Course course) {
		this.courses.remove(course);
	}
	
	public void addGrades(String course, String grades) {
		this.grades.put(course, grades);
	}


	// abstract method
	
	
	//print the corresponding menu for the user type
	public abstract void userMenu1();
	public abstract void userMenu2();

	
	//get the option chosen by the student
	public abstract void getResponse(Scanner scanner, ArrayList<Course> courses,
			ArrayList<Student> students, ArrayList<Professor> professors);
	
	//output the information of the user(Used to verify that the contents of the file were successfully read)
	public abstract String toString();






}
