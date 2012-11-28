/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jajeczko;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.miernik.jajeczko.event.StartWorkEvent;
import org.miernik.jajeczko.presenter.MainPresenter;
import org.miernik.jajeczko.presenter.NewTaskPresenter;
import org.miernik.jajeczko.presenter.ProjectsPresenter;
import org.miernik.jajeczko.presenter.TimerPresenter;
import org.miernik.jajeczko.presenter.TodayToDoPresenter;
import org.miernik.jfxlib.MVPApplication;
import org.miernik.jfxlib.event.EventListener;
import org.miernik.jfxlib.presenter.MainWindowPresenter;

/**
 * 
 * @author Miernik
 */
public class App extends MVPApplication<JajeczkoService> {

	final static Logger logger = Logger.getLogger(App.class);

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
			getTimerPresenter().show();
		}
	};

	private EntityManagerFactory emf;
	private MainPresenter mainPresenter;
	private TodayToDoPresenter todayToDoPresenter;
	private ProjectsPresenter projectsPresenter;
	private NewTaskPresenter newTaskPresenter;
	private TimerPresenter timerPresenter;
	private JajeczkoService service;

	public NewTaskPresenter getNewTaskPresenter() {
		if (newTaskPresenter == null) {
			newTaskPresenter = loadPresenter(NewTaskPresenter.class, "NewTask",
					true);
		}
		return newTaskPresenter;
	}

	public TimerPresenter getTimerPresenter() {
		if (timerPresenter == null) {
			timerPresenter = loadPresenter(TimerPresenter.class, "Timer", true);
		}
		return timerPresenter;
	}

	@Override
	public void init() throws Exception {
		getEventBus().addListener(timerListener);
		super.init();
	}

	public TodayToDoPresenter getTodayToDoPresenter() {
		if (todayToDoPresenter == null) {
			todayToDoPresenter = loadPresenter(TodayToDoPresenter.class,
					"TodayToDo", true);
		}
		return todayToDoPresenter;
	}

	public ProjectsPresenter getProjectsPresenter() {
		if (projectsPresenter == null) {
			projectsPresenter = loadPresenter(ProjectsPresenter.class,
					"Projects", true);
		}
		return projectsPresenter;
	}

	public void actionNewTask() {

		getNewTaskPresenter().show();
	}

	@Override
	public void stop() throws Exception {
		getService().dispose();
		emf.close();
		super.stop();
	}

	@Override
	public JajeczkoService getService() {
		if (service == null) {
			logger.debug("Create JajeczkoServiceHSQL object");
			emf = Persistence.createEntityManagerFactory("jajeczko");
			JajeczkoServiceHSQL js = new JajeczkoServiceHSQL(getEventBus());
			js.setEntityManager(emf.createEntityManager());
			service = js;
		}
		return this.service;
	}

	@Override
	public MainWindowPresenter<JajeczkoService> initMainPresenter(Stage s) {
		s.getIcons().add(new Image("images/jajeczko2.png"));
		s.setTitle("Jajeczko v0.3");
		mainPresenter = initPresenter(new MainPresenter(s), "Main", true);
		mainPresenter.setTodayToDo(getTodayToDoPresenter());
		mainPresenter.setProjects(getProjectsPresenter());
		return mainPresenter;
	}	
}
