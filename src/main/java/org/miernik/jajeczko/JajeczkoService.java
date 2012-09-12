package org.miernik.jajeczko;

import javafx.collections.ObservableList;

import org.miernik.jajeczko.model.JajeczkoTimer;
import org.miernik.jajeczko.model.Task;
import org.miernik.jfxlib.Service;

public interface JajeczkoService extends Service {
	ObservableList<Task> getTodayTasks();
	Task addTask(String name);
	JajeczkoTimer getTimer();
}