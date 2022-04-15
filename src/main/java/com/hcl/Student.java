package com.hcl;

public class Student {
	private int rollNo;
	private String name;
	private int age;
	private char ID;

	public Student(int rollNo, String name, int age, char ID)
	{
		this.rollNo = rollNo;
		this.name = name;
		this.age = age;
		this.ID = ID;
	}

	//Getters
	public int getRollNo() {return rollNo;}
	public String getName() {return name;}
	public int getAge() {return age;}
	public char getID() {return ID;}

	//Setters
	public void setRollNo(int rollNo) {this.rollNo = rollNo;}
	public void setName(String name) {this.name = name;}
	public void setAge(int age) {this.age = age;}
	public void setID(char ID) {this.ID = ID;}

	@Override
	public int hashCode() {return this.rollNo + this.name.hashCode() + this.age;}

	@Override
	public boolean equals(Object obj) {
		Student s = (Student) obj;
		return s.age == age && s.rollNo == rollNo && s.name.equals(name) && s.ID == ID;
	}
}
