/*****
Author: Abrar Ahmad
Date: 14 / 04 / 2022
Purpose: To Demonstrate Maven, as well as Java 8 features such as:
	>Streams
	>Lambdas

TODO: Add GPA	
TODO: Add more functionality to filterStudents-- Allow user to choose which ID to filter out 
*****/
package com.hcl;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class MainManager {
	
	public static void main(String[] args) {
		greeting(); //Greets the user to the Student Management System (SMS)
		
		Set<Student> studentSet = new TreeSet<>();
		String cmdKey = ""; //Checking input for Switch Case	
		
		//Sorting by name (Lambda)
		studentSet = new TreeSet<>((o1, o2) -> o1.getName().compareTo(o2.getName()));
		
		//Adding in a set of students
		studentSet.add(new Student(1, "Larry", 40, 'Z'));
		studentSet.add(new Student(2, "Jacob-Thomas", 30, 'Z'));
		studentSet.add(new Student(3, "George", 25, 'Z'));
		studentSet.add(new Student(4, "Lenny", 24, 'Z'));
		studentSet.add(new Student(5, "Brian", 32, 'Z'));
		studentSet.add(new Student(6, "Edgar-George", 27, 'Z'));
		studentSet.add(new Student(7, "Tom", 20, 'H'));
		studentSet.add(new Student(8, "Penny", 25, 'H'));
		
		//Creating a file to write the Set data to
			try {
				File reader = new File("Students.txt");
				if(reader.createNewFile()) {
					System.out.println("File created: " + reader.getName());
				} else {
					System.out.println("The file already exists");
				}
			} catch (Exception r) {
				System.out.println("File could not be made...");
				r.printStackTrace();
			}
		
		try (Scanner read = new Scanner(System.in);) {	
			writeFile(studentSet); //Writing the information to a file
			while(!(cmdKey.equals("X") || cmdKey.equals("Exit"))) {
				commandMenu(); //Displays the command menu
				System.out.println("Enter a command: ");
				cmdKey = read.next();
				switch(cmdKey) {
					case "1":
					case "Add":
						addStudent(studentSet, read);
						break;
					case "2":
					case "Remove":	
						removeStudent(studentSet, read);
						break;
					case "3":	
					case "Update":	
						updateStudent(studentSet, read);
						break;
					case "4":
					case "Search":	
						searchStudents(studentSet, read);
						break;
					case "5":
					case "Show":
						showStudents(studentSet, read);
						break;
					case "6":
					case "Filter":
						filterStudents(studentSet);
						break;
					case "7":
					case "Write":
						writeFile(studentSet);
						break;
					case "X":
					case "Exit":
						break;
					default:
						System.out.println("Invalid option. Try again...");
						break;
				}
			}
		} catch (Exception r) {
			System.out.println("An error occured...");
			r.printStackTrace();
		}
		
		averageAge(studentSet); //Calculating the Average Age of the students
		
	}
	
	private static void greeting()
	{
		System.out.println("Hello and welcome to the Student Management System \n" +
				"For the University of Suburbia!");
		System.out.println("Here you will get to choose between viewing, \n" +
			"adding, deleting students and updating their info from the database.");
		System.out.println("NOTE: IDs -- 'Z' = Zombie, 'H' = Human, 'P' = Plants");
	}
	
	private static void commandMenu() {
		System.out.println("Welcome to the command menu! \n Below are the list of options");
		System.out.println("Option 1: Add -- \n >>Adds a student to the SMS.");
		System.out.println("Option 2: Remove -- \n >>Removes a student from the SMS");
		System.out.println("Option 3: Update -- \n >>Updates a student's info in the SMS");
		System.out.println("Option 4: Search -- \n >>Searches the SMS for a student");
		System.out.println("Option 5: Show -- \n >>Shows all the students in the SMS");
		System.out.println("Option 6: FilteredShow -- \n >>Shows a Filtered list of students");
		System.out.println("Option 7: Write -- \n >>Writes to the created file");
		System.out.println("Option 8: Read -- \n >>Reads from the created file");
	}

	//This is the function where the file is written to
	private static void readFile(Set<Student> studentSet) throws ParseException {
		try(Scanner fileReader = new Scanner(System.in);){
			while(fileReader.hasNextLine()) {
				String fileData[] = fileReader.nextLine().split(" - ");
				int rollNo = Integer.parseInt(fileData[0]);
				String name = fileData[1];
				int age = Integer.parseInt(fileData[2]);
				char ID = fileData[3].charAt(0);
			}
		} catch (Exception fn) {
			System.out.println("File not found...");
			fn.printStackTrace();
		}
		
	}
	
	//This is the function where the file is written to
	private static void writeFile(Set<Student> studentSet) throws IOException {
		try (FileWriter write = new FileWriter("Students.txt");) {
			for(Student i: studentSet) {
				String insert = i.getRollNo() + ", " + i.getName() + ", " +
						i.getAge() + ", " + i.getID() + '\n';
				write.write(insert);
			}
			System.out.println("File writing successful!");
		} catch (Exception r) {
		System.out.println("An error has occured");
		}		
	}
	
	private static void addStudent(Set<Student> studentSet, Scanner read) {
		try {
			System.out.println("Enter the new student's name: ");
			String name = read.next();
			
			System.out.println("Enter the new student's ID or Roll #:");
			int rollNo = read.nextInt();
			
			System.out.println("Enter the age of the new student");
			int age = read.nextInt();
			
			System.out.println("Enter the ID of the new student (Z, H or P)");
			char ID = read.next().charAt(0);
			if(!(ID == 'Z' || ID == 'H' || ID == 'P')) {
				System.out.println("Wrong Char Input.");
				ID = read.next().charAt(0);
			}
			studentSet.add(new Student(rollNo, name, age, ID)); //Adding the student in
		} catch (Exception err) {
			System.out.println("Student Addition Attempt Failed.");
			System.out.println("Check for incorrect input.");
			return;
		}
	}
	
	private static void removeStudent(Set<Student> studentSet, Scanner read) {
		try {
			System.out.println("Enter the ID of the student to delete: ");
			int idRemove = read.nextInt();
		
			for(Student set: studentSet) {
				if(set.getID() == idRemove) {
					studentSet.remove(set);
					System.out.println("Successfully removed " + set.getName());
					return;
				}
			}
		} catch (Exception r) {
			nonExistentRollNo();
		}
	}
	
	private static void updateStudent(Set<Student> studentSet, Scanner read) {
		try {
			System.out.println("Would you like to update Age or Name?");
			String change = read.next();
			if(!(change.equals("age") || change.equals("name"))) {
				System.out.println("You input neither option. Try again");
				change = read.next();
				}
			System.out.println("Enter the student ID: ");
			int rollNo = read.nextInt();
			for(Student i : studentSet) {
				if(i.getRollNo() == rollNo) {
					if(change.equals("age")) {
						System.out.println("Enter the new age: ");
						i.setAge(read.nextInt());
					}
					else if(change.equals("name")) {	
						System.out.println("Enter the new name: ");
						i.setName(read.next());
					}
					System.out.println("Successfully updated " + i.getName());
					return;
				}
			}
		} catch (Exception r) {
			System.out.println("Updating went wrong...");
			return;
		}
	}
	
	//This is option 4- Search Students
	private static void searchStudents(Set<Student> studentSet, Scanner read) {
		System.out.println("Enter the student roll #: ");
		int rollNo = read.nextInt();
		for(Student i : studentSet) {
			if(i.getRollNo() == rollNo) {
				System.out.println("Student Information: \n Name: "
					+ i.getName() + "\n Age: " + i.getAge() + "\n ID: " + i.getID());
				return;
			}
		}
	}
	
	//This is option 5- Show All Students 
	private static void showStudents(Set<Student> studentSet, Scanner read) {
		try {
			List<String> studentList = studentSet.stream() 
					.map(x -> x.getName())
					.collect(Collectors.toList());
			System.out.println(studentList);
		
		} catch (Exception r) {
			System.out.println("Something went wrong...");
			return;
		}
	}
	
	//Testing out the stream class to filter ID
	private static void filterStudents(Set<Student> studentSet) {
		try {
			List<String> studentList = studentSet.stream()
					.filter(x -> x.getID() == 'Z')
					.map(x -> x.getName())
					.collect(Collectors.toList());
			System.out.println("List of all students with ID 'Z': " + studentList);
		} catch (Exception r) {
			System.out.println("List could not be filtered");
			return;
		}
	}

	//Utilizes the stream to calculate the average ages of all or certain students
	//For example, Students with ID 'Z' are averaged
	private static void averageAge(Set<Student> studentSet) {
		try {
			double avgAgeStudents = studentSet.stream()
					.mapToDouble(x -> x.getAge())
					.average()
					.orElse(Double.NaN);
			System.out.println("This is the average age of all the students: " 
					+ avgAgeStudents);
			double avgAgeZStudents = studentSet.stream()
					.filter(x -> x.getID() == 'Z')
					.mapToDouble(x -> x.getAge())
					.average()
					.orElse(Double.NaN);
			System.out.println("This is the average age of all the students of Class Z: "
					+ avgAgeZStudents);
		} catch (Exception r) {
			System.out.println("Something went wrong...");
			return;
		}
	}
	
	private static void nonExistentRollNo() {
		System.out.println("The Roll # Does Not Exist In The SMS.");
	}
}