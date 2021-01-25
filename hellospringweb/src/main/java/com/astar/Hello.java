package com.astar;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Hello {
	
	private String name;
	
	private String greeting;
	
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGreeting() {
		return greeting;
	}

	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}
	
	public Hello(String name, String greeting, Date date) {
		super();
		this.name = name;
		this.greeting = greeting;
		this.date = date;
	}
	public Hello() {}
}
