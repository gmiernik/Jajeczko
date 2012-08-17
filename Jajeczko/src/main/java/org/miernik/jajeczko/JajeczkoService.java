/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jajeczko;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.miernik.jajeczko.model.Task;
import org.miernik.jfxlib.Service;

/**
 *
 * @author Miernik
 */
public class JajeczkoService implements Service {
    
    private ObservableList<Task> tasks;

    public JajeczkoService() {
        tasks = FXCollections.observableArrayList();
        tasks.add(new Task(123, "Testowe zadanie!"));
    }
        
    public ObservableList<Task> getTodayTasks() {
        return tasks;
    }
}
