package org.miernik.jajeczko.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Miernik
 * @version 1.0 @created 22-cze-2012 16:41:40
 */
@Entity
@Table(name = "STATUSES")
public class Status {

	public static Status OPEN = new Status(1, "Open");
	public static Status APPROVAL = new Status(2, "Approval");
	public static Status IN_PROGRESS = new Status(3, "In progress");
	public static Status DONE = new Status(4, "Done");
	public static Status REJECTED = new Status(5, "Rejected");
	public static Status PENDING = new Status(6, "Pending");
	public static Status SOMEDAY = new Status(7, "Someday");

	private int id;
	private SimpleStringProperty name = new SimpleStringProperty();

	private Status(int id, String name) {
		this.id = id;
		this.name.set(name);
	}

	Status() {
	}

	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name.get();
	}

	@Column(nullable = false, unique = true)
	public void setName(String name) {
		this.name.set(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Status) {
			Status s = (Status) obj;
			if ((getId() == 0 || s.getId() == 0) || (getId() == s.getId())) {
				if (getName() == s.getName()) {
					return true;
				}
			}
		}
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		return String.format("Status(%2$d,%1$s)", getName(), getId());
	}
}