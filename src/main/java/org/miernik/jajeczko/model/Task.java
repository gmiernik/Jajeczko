package org.miernik.jajeczko.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Miernik
 * @version 1.0 @created 22-cze-2012 16:40:01
 */
public class Task {

	private Category category;
	private int id;
	private StringProperty name = new SimpleStringProperty();
	private Status status;
	private List<Egg> eggs;
	private IntegerProperty numberOfEggs = new SimpleIntegerProperty();

	public Task() {
		eggs = new ArrayList<Egg>();
		numberOfEggs.set(eggs.size());
	}

	public Task(String name) {
		this();
		this.name.set(name);
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
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	//TODO: prepare test for the addEgg method
	public void addEgg() {
		Egg egg = new Egg();
		Date startTime = new Date();
		startTime.setTime(startTime.getTime()-(25*60000));
		egg.setStartTime(startTime);
		eggs.add(egg);
		setName(getName()+"x");
		numberOfEggs.set(eggs.size());
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
	
	public int getNumberOfEggs() {
		return numberOfEggs.get();
	}
	
	public IntegerProperty numberOfEggsProperty() {
		return numberOfEggs;
	}
}