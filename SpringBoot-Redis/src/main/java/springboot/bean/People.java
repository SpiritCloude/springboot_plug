package springboot.bean;

import java.io.Serializable;

public class People implements Serializable{

	String name;
	int age;
	
	public People() {
		super();
		// TODO Auto-generated constructor stub
	}
	public People(String name, int age) {
		super();
		this.name = name;
		this.age = age;
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
	
	@Override
	public String toString() {
		return "people [name=" + name + ", age=" + age + "]";
	}
	
}
