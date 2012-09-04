/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jajeczko;

import javafx.stage.Stage;

import org.miernik.jajeczko.event.StartWorkEvent;
import org.miernik.jajeczko.presenter.MainPresenter;
import org.miernik.jajeczko.presenter.NewTaskPresenter;
import org.miernik.jajeczko.presenter.ProjectsPresenter;
import org.miernik.jajeczko.presenter.TimerPresenter;
import org.miernik.jajeczko.presenter.TodayToDoPresenter;
import org.miernik.jfxlib.MVPApplication;
import org.miernik.jfxlib.event.EventListener;
import org.miernik.jfxlib.presenter.AbstractMainPresenter;

/**
 * 
 * @author Miernik
 */
public class App extends MVPApplication<JajeczkoService> {
	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	final private EventListener<StartWorkEvent> timerListener = new EventListener<StartWorkEvent>() {

		@Override
		public void performed(StartWorkEvent e) {
			getTimerPresenter().start(e.getTask());
		}
	};

	private MainPresenter mainPresenter;
	private TodayToDoPresenter todayToDoPresenter;
	private ProjectsPresenter projectsPresenter;
	private NewTaskPresenter newTaskPresenter;
	private TimerPresenter timerPresenter;
	private JajeczkoService service = new JajeczkoServiceMemory();;

	public NewTaskPresenter getNewTaskPresenter() {
		if (newTaskPresenter == null) {
			newTaskPresenter = (NewTaskPresenter) load("NewTask", true);
		}
		return newTaskPresenter;
	}

	public TimerPresenter getTimerPresenter() {
		if (timerPresenter == null) {
			timerPresenter = (TimerPresenter) load("Timer", true);
		}
		return timerPresenter;
	}

	@Override
	public void start(final Stage primaryStage) {
		// add listeners
		getEventBus().addListener(timerListener);
		
		primaryStage.setTitle("Jajeczko");
		getMainPresenter().setMainView(primaryStage);
		primaryStage.show();
	}

	@Override
	public AbstractMainPresenter<JajeczkoService> getMainPresenter() {
		if (mainPresenter == null) {
			mainPresenter = (MainPresenter) load("Main", true);
			mainPresenter.setTodayToDo(getTodayToDoPresenter());
			mainPresenter.setProjects(getProjectsPresenter());
		}
		return mainPresenter;
	}

	public TodayToDoPresenter getTodayToDoPresenter() {
		if (todayToDoPresenter == null) {
			todayToDoPresenter = (TodayToDoPresenter) load("TodayToDo", true);
		}
		return todayToDoPresenter;
	}

	public ProjectsPresenter getProjectsPresenter() {
		if (projectsPresenter == null) {
			projectsPresenter = (ProjectsPresenter) load("Projects", true);
		}
		return projectsPresenter;
	}

	public void actionNewTask() {

		getNewTaskPresenter().show();
	}

	@Override
	public JajeczkoService getService() {
		return this.service;
	}
}
