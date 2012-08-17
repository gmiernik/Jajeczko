/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jajeczko;

import javafx.stage.Stage;
import org.miernik.jajeczko.presenter.MainPresenter;
import org.miernik.jajeczko.presenter.NewTaskPresenter;
import org.miernik.jajeczko.presenter.ProjectsPresenter;
import org.miernik.jajeczko.presenter.TodayToDoPresenter;
import org.miernik.jfxlib.MVPApplication;
import org.miernik.jfxlib.presenter.AbstractMainPresenter;

/**
 *
 * @author Miernik
 */
public class App extends MVPApplication<JajeczkoService> {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private MainPresenter mainPresenter;
    private TodayToDoPresenter todayToDoPresenter;
    private ProjectsPresenter projectsPresenter;
    private NewTaskPresenter newTaskPresenter;

    public NewTaskPresenter getNewTaskPresenter() {
        if (newTaskPresenter==null) {
            newTaskPresenter = (NewTaskPresenter) load("NewTask", true);            
        }
        return newTaskPresenter;
    }
    private JajeczkoService service = new JajeczkoService();
        
    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Jajeczko");
        getMainPresenter().setMainView(primaryStage);
        primaryStage.show();
    }

    @Override
    public AbstractMainPresenter<JajeczkoService> getMainPresenter() {
        if (mainPresenter==null) {
            mainPresenter = (MainPresenter) load("Main", true);
            mainPresenter.setTodayToDo(getTodayToDoPresenter());
            mainPresenter.setProjects(getProjectsPresenter());
        }
        return mainPresenter;
    }
    
    public TodayToDoPresenter getTodayToDoPresenter() {
        if (todayToDoPresenter==null) {
            todayToDoPresenter = (TodayToDoPresenter) load("TodayToDo", true);
        }
        return todayToDoPresenter;
    }

    public ProjectsPresenter getProjectsPresenter() {
        if (projectsPresenter==null) {
            projectsPresenter = (ProjectsPresenter) load("Projects", true);
        }
        return projectsPresenter;
    }
    
    public void actionNewTask() {
        
        getNewTaskPresenter().showDialog();
    }

	@Override
	public JajeczkoService getService() {
		return this.service;
	}
}
