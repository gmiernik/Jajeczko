/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jajeczko;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.miernik.jajeczko.model.Task;

/**
 *
 * @author Miernik
 */
public class JajeczkoServiceMemory extends JajeczkoServiceBase {
    
    private List<Task> tasks;
    private int idCounter = 0;    

    public JajeczkoServiceMemory() {
        tasks = new ArrayList<>();
        addTask("Testowe zadanie!");
    }
        
    public List<Task> getTodayTasks() {
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
	public void approvalTask(Task t) {
		// TODO Auto-generated method stub
		
	}
}
