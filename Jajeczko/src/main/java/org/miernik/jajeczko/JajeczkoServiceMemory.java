/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jajeczko;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.miernik.jajeczko.model.JajeczkoTimer;
import org.miernik.jajeczko.model.Task;
import org.miernik.jfxlib.Service;

/**
 *
 * @author Miernik
 */
public class JajeczkoServiceMemory implements Service, JajeczkoService {
    
    private ObservableList<Task> tasks;
    private int idCounter = 0;
    private JajeczkoTimer timer;
    

    public JajeczkoServiceMemory() {
        tasks = FXCollections.observableArrayList();
        addTask("Testowe zadanie!");
        timer = new JajeczkoTimer();
    }
        
    public ObservableList<Task> getTodayTasks() {
        return tasks;
    }
    
    public Task addTask(String name) {
    	Task t = new Task(name);
    	if (tasks.contains(t))
    		throw new IllegalArgumentException("cannot add the same task to the list");
    	t.setId(++idCounter);
    	tasks.add(t);
    	return t;
    }

	@Override
	public JajeczkoTimer getTimer() {
		return timer;
	}
}
