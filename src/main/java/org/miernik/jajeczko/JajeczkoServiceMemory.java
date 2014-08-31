/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jajeczko;

import java.util.ArrayList;
import java.util.List;
import org.miernik.jajeczko.model.Task;
import org.miernik.jfxlib.event.EventBus;

/**
 *
 * @author Miernik
 */
public class JajeczkoServiceMemory extends JajeczkoServiceBase {
    
    private List<Task> tasks;
    private int idCounter = 0;    

    public JajeczkoServiceMemory(EventBus eb) {
    	super(eb);
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
	public void approveTask(Task t) {		
	}

	@Override
	public void completeTask(Task task) {
	}

	@Override
	public void rejectTask(Task task) {
	}

	@Override
	public void suspendTask(Task task) {
	}

	@Override
	public void postponeTask(Task task) {
	}


	@Override
	protected void updateTask(Task task) {
	}
	
	@Override
	public void startWorking(Task task) {
	}


	@Override
	public List<Task> getOpenedTasks() {
		// TODO Auto-generated method stub
		return null;
	}
}
