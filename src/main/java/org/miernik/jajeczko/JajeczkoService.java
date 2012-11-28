package org.miernik.jajeczko;

import java.util.List;
import org.miernik.jajeczko.model.JajeczkoTimer;
import org.miernik.jajeczko.model.Task;
import org.miernik.jfxlib.Service;

public interface JajeczkoService extends Service {
	/**
	 * Generate list of task with status: APPROVAL and IN_PROGRESS
	 * @return
	 */
	List<Task> getTodayTasks();
	/**
	 * Add new task to the collection. The status of the task will OPEN
	 * @param name
	 * @return
	 */
	Task addTask(String name);
	/**
	 * Approve the task to work. Status will change to APPROVAL
	 * @param task
	 */
	void approveTask(Task task);
	/**
	 * Set task as completed. Status will change to DONE.
	 * @param task
	 */
	void completeTask(Task task);
	/**
	 * Set task as rejected. Status will change to REJECTED.
	 * @param task
	 */
	void rejectTask(Task task);
	/**
	 * Set task as pending or waiting for external activity. Status will change to PENDING.
	 * @param task
	 */
	void suspendTask(Task task);
	/**
	 * Set the task for the future. Status will change to SOMEDAY.
	 * @param task
	 */
	void postponeTask(Task task);
	
	/**
	 * Start working on the task. Status will change to IN PROGRESS.
	 * @param task
	 */
	void startWorking(Task task);
	
	/**
	 * Access to Timer object
	 * @return
	 */
	JajeczkoTimer getTimer();
	/**
	 * Start timer for work
	 * @param task
	 */
	void dispose();
}