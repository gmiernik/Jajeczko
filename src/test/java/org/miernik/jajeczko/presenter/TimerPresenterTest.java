package org.miernik.jajeczko.presenter;

import java.util.List;
import javafx.embed.swing.JFXPanel;
import org.jodah.concurrentunit.junit.ConcurrentTestCase;
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
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void rejectTask(Task task) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void postponeTask(Task task) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public List<Task> getTodayTasks() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void completeTask(Task task) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void approveTask(Task task) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Task addTask(String name) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			protected void updateTask(Task task) {
				// TODO Auto-generated method stub
				
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
