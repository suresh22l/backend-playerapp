package com.example.bcm.code.challenge.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Player {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String name;
    private int age;
    private String address1;
    private String address2;

    public Player() {
    }

    public Player(String name, int age, String add1, String add2) {
        this.setName(name);
        this.setAge(age);
        this.setAddress1(add1);
        this.setAddress2(add2);
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

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	@Override
	public String toString() {
	  return "Player [id=" + id + ", name=" + name + ", age=" + age + ", address1=" + address1 + ", address2=" + address2 + "]";
	}
}