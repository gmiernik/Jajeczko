package org.miernik.jajeczko.presenter;

import java.util.List;
import javafx.embed.swing.JFXPanel;
import org.jodah.concurrentunit.ConcurrentTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.miernik.jajeczko.JajeczkoService;
import org.miernik.jajeczko.JajeczkoServiceBase;
import org.miernik.jajeczko.model.Task;
import org.miernik.jfxlib.event.EventBus;
import org.miernik.jfxlib.event.SimpleEventBus;

public class TimerPresenterTest extends ConcurrentTestCase {

	@BeforeClass
	public static void initJFX() throws Exception {
		new JFXPanel();
	}

	private JajeczkoService service;
	private EventBus eventBus;

	@Before
	public void setUp() throws Exception {
		eventBus = new SimpleEventBus();
		service = new JajeczkoServiceBase(eventBus) {
			
			@Override
			public void suspendTask(Task task) {
				
			}
			
			@Override
			public void rejectTask(Task task) {
			}
			
			@Override
			public void postponeTask(Task task) {
			}
			
			@Override
			public List<Task> getTodayTasks() {
				return null;
			}
			
			@Override
			public void completeTask(Task task) {
			}
			
			@Override
			public void approveTask(Task task) {
			}
			
			@Override
			public Task addTask(String name) {
				return null;
			}
			
			@Override
			protected void updateTask(Task task) {
			}

			@Override
			public void startWorking(Task task) {
			}

			@Override
			public List<Task> getOpenedTasks() {
				return null;
			}
		};
	}

	@After
	public void tearDown() throws Exception {
		service.dispose();
		service = null;
		eventBus = null;
	}
}
