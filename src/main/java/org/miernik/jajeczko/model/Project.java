package org.miernik.jajeczko.model;

import javafx.beans.property.SimpleStringProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import org.miernik.jajeczko.model.Task;
import java.util.Collection;
import javax.persistence.OneToMany;

/**
 * @author Miernik
 * @version 1.0 @created 22-cze-2012 16:45:36
 */
@Entity
@Table(name = "PROJECTS")
public class Project {

	private int id;
	private SimpleStringProperty name;
	public Category category;
	public Status status;
	private Collection<Task> tasks;
	
	Project() {
		this.name = new SimpleStringProperty();
	}
	
	public Project(String name) {
		this();
		setName(name);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "proj-gen")
	@TableGenerator(name = "proj-gen", allocationSize = 1, table = "GENERATORS")
	public int getId() {
		return id;
	}

	@Column(nullable=false, unique=true)
	public String getName() {
		return name.get();
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		if (name==null || name.isEmpty())
			throw new IllegalArgumentException("cannot set null or empty value as project name");
		this.name.set(name);
	}

	@OneToMany(mappedBy = "project")
	public Collection<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Collection<Task> param) {
		this.tasks = param;
	}
}