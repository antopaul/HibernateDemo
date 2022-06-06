package test.hibernate.interceptor;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "StudentDetails")
@Table(name = "student_details")
public class StudentDetail implements Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "student_details_seq")
	@SequenceGenerator(name = "student_details_seq", sequenceName="student_details_seq")
	@Column(name = "student_id", unique = true)
	private int id;

	@Column(name = "student_name", nullable = false)
	private String name;

	@Column(name = "student_age", nullable = false)
	private int age;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		return id + "\t" + name + "\t" + age;
	}
}
