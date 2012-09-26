package org.miernik.jajeczko;

import java.util.List;
import org.miernik.jajeczko.model.JajeczkoTimer;
import org.miernik.jajeczko.model.Task;
import org.miernik.jfxlib.Service;

public interface JajeczkoService extends Service {
	List<Task> getTodayTasks();
	Task addTask(String name);
	void approvalTask(Task t);
	JajeczkoTimer getTimer();
	void dispose();
}