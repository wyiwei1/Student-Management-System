# Student-Management-System
a course assignment

**Introduction**  
In this homework, you will implement a console-based student management system. The objective of this assignment is to design a system for students to manage their courses. There will be three main user roles in the application: Admin, Student, and Professor.  
In the student management system, a) A student can log in to their account, view/add/drop courses, check their course schedule, and view grades. b) A professor can view course information they have, and view the student lists for these courses.  
c) An admin  can view course/student/professor lists, and add/delete  
courses/students/professors. The course information will be in the  courseInfo.txt file. There will also be  three files  containing student/professor/admin information.  
The student management system  will read and parse all of the files. Once all information has been loaded into the system, you’ll be able to log in as a(n) student/professor/administrator to test the system.  
You are expected to design several classes and to implement methods to build  the application. For example, you may create a class  FileInfoReader  to load the files.  
You can create an abstract class  User  and a  Professor  class,  Student  class,  and Admin  class which all extend and implement the  User  class. You can have a  Course  class  that represents a single course and a  Controller  class to control the main logic of the entire system.  
Below are explanations (and samples) of the pieces of information in each of the four provided data files.  
courseInfo.txt  -  Courses information file that contains:  course ID; course name; lecturer; days; start time; end time; capacity 
*CIT590; Programming Languages and Techniques; Brandon L  Krakowsky; MW; 16:30; 18:00; 110*
Programming Languages and Techniques  
studentInfo.txt  -  Student information file that contains:  student ID; student name;  student username; password; course ID: course grade (could be multiple)  001; StudentName1; testStudent01; password590; *CIS191: A,  CIS320: A*  
profInfo.txt  -  Professor information file that  contains:  prof name; prof ID; prof username; password  
*Clayton Greenberg; 001; Greenberg; password590*  
adminInfo.txt  -  Admin information file that contains:  admin ID; admin name; admin username; password  
*001; admin; admin01; password590*
Below are examples  of what the system could look like. Feel free to make your program do exactly this or make it look even fancier.  
When entering the system, one can select to log in as a(n) student/professor/admin,  or quit the system
