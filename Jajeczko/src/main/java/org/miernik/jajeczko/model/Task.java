package org.miernik.jajeczko.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Miernik
 * @version 1.0 @created 22-cze-2012 16:40:01
 */
public class Task {

	private Category category;
	private int id;
	private String name;
	private Status status;
	private List<Egg> eggs;

	public Task() {
		this.eggs = new ArrayList<Egg>();
	}

	public Task(String name) {
		this();
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void addEgg(Egg e) {
		this.eggs.add(e);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Task) {
			Task t = (Task)obj;
			if (getName()==t.getName()) {
				if (getId()==0 || t.getId()==0)
					return true;
				else if (getId()==t.getId())
					return true;
			}
		}
		return super.equals(obj);
	}
}