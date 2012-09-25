package org.miernik.jajeczko.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import org.miernik.jajeczko.model.Project;

/**
 * @author Miernik
 * @version 1.0 @created 22-cze-2012 16:40:01
 */
@Entity
@Table(name = "TASKS")
public class Task {

	private Category category;
	private int id;
	private StringProperty name = new SimpleStringProperty();
	private Status status;
	private IntegerProperty numberOfEggs = new SimpleIntegerProperty();
	private Collection<Egg> eggs;
	private Project project;

	public Task() {
		eggs = new ArrayList<Egg>();
		numberOfEggs.set(eggs.size());
	}

	public Task(String name) {
		this();
		this.name.set(name);
	}

	@ManyToOne
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "task-gen")
	@TableGenerator(name = "task-gen", allocationSize = 1, table = "GENERATORS")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(nullable=false, unique=true)
	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	@OneToOne
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@OneToMany
	@JoinColumn(name = "TASK_ID", referencedColumnName = "ID")
	public Collection<Egg> getEggs() {
		return eggs;
	}

	public void setEggs(Collection<Egg> eggs) {
		this.eggs = eggs;
	}

	// TODO: prepare test for the addEgg method
	public void addEgg() {
		Egg egg = new Egg();
		Date startTime = new Date();
		startTime.setTime(startTime.getTime() - (25 * 60000));
		egg.setStartTime(startTime);
		eggs.add(egg);
		setName(getName() + "x");
		numberOfEggs.set(eggs.size());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Task) {
			Task t = (Task) obj;
			if (getName() == t.getName()) {
				if (getId() == 0 || t.getId() == 0)
					return true;
				else if (getId() == t.getId())
					return true;
			}
		}
		return super.equals(obj);
	}

	@Transient
	public int getNumberOfEggs() {
		return numberOfEggs.get();
	}

	public IntegerProperty numberOfEggsProperty() {
		return numberOfEggs;
	}

	@ManyToOne
	public Project getProject() {
		return project;
	}

	public void setProject(Project param) {
		this.project = param;
	}

}