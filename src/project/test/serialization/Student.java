package project.test.serialization;

import java.io.Serializable;

public class Student implements Serializable {

	private static final long serialVersionUID = 439616180046765748L;
	private String name;
	private int age;
	private String address;
	
	public Student() {}
	
	public Student(String name, int age, String address) {
		this.name = name;
		this.age = age;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() { // 본래 collection 객체명을 출력하면 해당 객체의 주소값이 나오는데 toString을 재정의 하면 해당 String값이 나옴. 
		return "학생명 : " + name + ", 나이 : " + age + "세, 주소 : " + address;
	}
	
	
	
	
	
	
}
