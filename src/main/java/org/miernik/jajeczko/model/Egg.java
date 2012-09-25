package org.miernik.jajeczko.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * @author Miernik
 * @version 1.0 @created 22-cze-2012 16:49:08
 */
@Entity
@Table(name = "EGGS")
public class Egg {

	private int id;
	private SimpleIntegerProperty interruptionInside;
	private SimpleIntegerProperty interruptionOutside;
	private ObjectProperty<Date> startTime;

	Egg() {
		interruptionInside = new SimpleIntegerProperty();
		interruptionOutside = new SimpleIntegerProperty();
		startTime = new SimpleObjectProperty<Date>();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "egg-gen")
	@TableGenerator(name = "egg-gen", allocationSize = 1, table = "GENERATORS")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInterruptionInside() {
		return interruptionInside.get();
	}

	public void setInterruptionInside(int interruptionInside) {
		this.interruptionInside.set(interruptionInside);
	}

	public int getInterruptionOutside() {
		return interruptionOutside.get();
	}

	public void setInterruptionOutside(int interruptionOutside) {
		this.interruptionOutside.set(interruptionOutside);
	}

	public Date getStartTime() {
		return startTime.get();
	}

	public void setStartTime(Date startTime) {
		this.startTime.set(startTime);
	}
}