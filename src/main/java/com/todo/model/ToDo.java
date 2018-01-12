package com.todo.model;

import java.util.Date;

public class ToDo {

	private String title;
	private String description;
	private Date dueDate;

	public ToDo(String title, String description, Date dueDate) {
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
	}

	public ToDo() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

}
