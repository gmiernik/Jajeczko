package org.miernik.jajeczko;

import static org.junit.Assert.*;
import java.util.List;

import javafx.embed.swing.JFXPanel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.miernik.jajeczko.event.FinishWorkEvent;
import org.miernik.jajeczko.model.Egg;
import org.miernik.jajeczko.model.JajeczkoTimer;
import org.miernik.jajeczko.model.Status;
import org.miernik.jajeczko.model.Task;
import org.miernik.jfxlib.event.EventBus;
import org.miernik.jfxlib.event.SimpleEventBus;

public class JajeczkoServiceBaseTest {

	class TestResult {
		int value = 0;
	}
	
	private JajeczkoService service;
	private EventBus eventBus;
	private boolean updateTask;

	@Before
	public void setUp() throws Exception {
		updateTask = false;
		eventBus = new SimpleEventBus();
		JajeczkoServiceBase serviceBase = new JajeczkoServiceBase(eventBus) {
			
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
				updateTask = true;
			}
		};
		service = serviceBase;
		new JFXPanel();
	}

	@After
	public void tearDown() throws Exception {
		service.dispose();
		service = null;
		eventBus = null;
	}


	@Test
	public void testGetTimer() {
		assertNotNull(service.getTimer());
	}
	
	@Test
	public void testStartWorkEvent() throws InterruptedException {
		final Task t = new Task();
		t.setStatus(Status.APPROVAL);
		
		service.getTimer().startWork(t);
		Thread.sleep(100);
		assertEquals(Status.IN_PROGRESS, t.getStatus());
		assertTrue(updateTask);
	}

	@Test
	public void testCancelWorkEvent() throws InterruptedException {
		final Task t = new Task();
		t.setStatus(Status.APPROVAL);
		
		JajeczkoTimer timer = service.getTimer();
		timer.startWork(t);
		timer.cancel();
		Thread.sleep(100);
		assertEquals(timer.getTask(), t);
		assertEquals(1, t.getNumberOfEggs());
		Egg e = (Egg)t.getEggs().toArray()[0];
		assertNotNull(e);
		assertEquals(timer.getStartTime(), e.getStartTime());
		assertEquals(timer.getStopTime(), e.getStopTime());
		assertFalse(e.isFinished());
		assertTrue(updateTask);
	}
	
	@Test
	public void testFinishWorkEvent() {
		final Task t = new Task();
		t.setStatus(Status.APPROVAL);
		
		JajeczkoTimer timer = service.getTimer();
		timer.startWork(t);
		eventBus.fireEvent(new FinishWorkEvent(t));
		
		assertEquals(timer.getTask(), t);
		assertEquals(1, t.getNumberOfEggs());
		Egg e = (Egg)t.getEggs().toArray()[0];
		assertNotNull(e);
		assertEquals(timer.getStartTime(), e.getStartTime());
		assertEquals(timer.getStopTime(), e.getStopTime());
		assertTrue(e.isFinished());
		assertTrue(updateTask);
	}
}
