package files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import courses.Course;
import roles.Admin;
import roles.Professor;
import roles.Student;

public class FileInfoReader {
	
	
	/**
	 * Reads course information from a specified file and constructs a list of Course objects.
	 * Each line in the file represents one course and should contain seven elements separated by semicolons.
	 *
	 * @param filePath The path to the file containing course data.
	 * @return A list of Course objects constructed from the file data.
	 */
	public static ArrayList<Course> readCourseFromFile(String filePath){
		ArrayList<Course> courses = new ArrayList<>();
		File file = new File(filePath);
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] parts = line.split(";\\s*");
				 // Ensure there are exactly seven parts to the line to form a valid Course object
				if(parts.length==7) {
					String id = parts[0].trim();
					String name = parts[1].trim();
					String lecturer = parts[2].trim();
					String days = parts[3].trim();
					String startTime = parts[4].trim();
					String endTime = parts[5].trim();
					String capacity = parts[6].trim();
					// Add the new Course object to the list of courses
					courses.add(new Course(id, name,lecturer,days,startTime,endTime,capacity));
				}
			}
			scanner.close();
		}catch (FileNotFoundException e) {
			System.out.println("File not found: " + filePath);
			e.printStackTrace();
		}
	
		return courses;
	}
	
	/**
	 * Reads student information from a specified file and constructs a list of Student objects.
	 * Each line in the file should contain at least five parts separated by semicolons:
	 * student ID, name, username, password, and a comma-separated list of grades.
	 *
	 * @param filePath The path to the file containing student data.
	 * @return A list of Student objects constructed from the file data.
	 */
	public static ArrayList<Student> readStudentFromFile(String filePath){
		ArrayList<Student> students = new ArrayList<>();
		File file = new File(filePath);
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] parts = line.split(";\\s*");
				if(parts.length>=5) {
					String id = parts[0].trim();
					String name = parts[1].trim();
					String username = parts[2].trim();
					String password = parts[3].trim();
					
					HashMap<String, String> grades = new HashMap<>();
					String[] gradeParts = parts[4].split(",\\s*");
					for(String grade: gradeParts) {
						String[] gradeDetail = grade.split(":\\s");
						if(gradeDetail.length==2) {
							grades.put(gradeDetail[0].trim(), gradeDetail[1].trim());
							
						}
					}
					students.add(new Student(id,name,username,password,grades));
				}
			}
			scanner.close();
		}catch (FileNotFoundException e) {
			System.out.println("File not found: " + filePath);
			e.printStackTrace();
		}
		return students;
	}
	
	/**
	 * Reads professor information from a file and constructs a list of Professor objects.
	 * Each line in the file should contain exactly four parts separated by semicolons:
	 * name, ID, username, and password.
	 *
	 * @param filePath The path to the file containing professor data.
	 * @return A list of Professor objects constructed from the file data.
	 */
	public static ArrayList<Professor> readProfessorFromFile(String filePath){
		ArrayList<Professor> professors = new ArrayList<>();
		File file = new File(filePath);
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] parts = line.split(";\\s*");
				if(parts.length==4) {
					String name = parts[0].trim();
					String id = parts[1].trim();
					String username = parts[2].trim();
					String password = parts[3].trim();
					professors.add(new Professor(name,id,username,password));
				}

			}
			scanner.close();
		}catch (FileNotFoundException e) {
			System.out.println("File not found: " + filePath);
			e.printStackTrace();
		}
		return professors;
	}
	
	/**
	 * Reads administrator information from a file and constructs a list of Admin objects.
	 * Each line in the file should contain exactly four parts separated by semicolons:
	 * administrator ID, name, username, and password.
	 *
	 * @param filePath The path to the file containing administrator data.
	 * @return A list of Admin objects constructed from the file data.
	 */
	public static ArrayList<Admin> readAdminFromFile(String filePath){
		ArrayList<Admin> admin = new ArrayList<>();
		File file =new File(filePath);
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] parts = line.split(";\\s*");
				if(parts.length==4) {
					String id = parts[0].trim();
					String name = parts[1].trim();
					String username = parts[2].trim();
					String password = parts[3].trim();
					admin.add(new Admin(id,name,username,password));
				}
			}
			scanner.close();
		}catch (FileNotFoundException e) {
			System.out.println("File not found: " + filePath);
			e.printStackTrace();
		}
		return admin;
	}

}
