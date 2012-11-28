package org.miernik.jajeczko;

import static org.junit.Assert.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.miernik.jajeczko.model.Status;
import org.miernik.jajeczko.model.Task;
import org.miernik.jfxlib.event.SimpleEventBus;

public class JajeczkoServiceHSQLTest {

	private JajeczkoService service;
	private EntityManagerFactory emf;

	@Before
	public void setUp() throws Exception {
		emf = Persistence.createEntityManagerFactory("jajeczko-test");
		JajeczkoServiceHSQL js = new JajeczkoServiceHSQL(new SimpleEventBus());
		js.setEntityManager(emf.createEntityManager());
		service = js;
	}

	@After
	public void tearDown() throws Exception {
		service.dispose();
		emf.close();
	}

	@Test
	public void testAddTask() {
		final String name = "test214";
		final EntityManager em = emf.createEntityManager();
		
		Task result = service.addTask(name);
		assertNotNull(result);
		assertTrue(result.getId()>0);
		assertEquals(Status.OPEN, result.getStatus());
		Task result2 = em.find(Task.class, result.getId());
		em.close();
		assertNotNull(result2);
		assertEquals(result.getId(), result2.getId());
	}
	
	@Test
	public void testApproveTask() {
		final Task t = service.addTask("t1");
		
		service.approveTask(t);
		assertEquals(Status.APPROVAL, t.getStatus());
	}

	@Test
	public void testCompleteTask() {
		final Task t = service.addTask("t1");
		
		service.completeTask(t);
		assertEquals(Status.DONE, t.getStatus());
	}
	
	@Test
	public void testRejectTask() {
		final Task t = service.addTask("t1");
		
		service.rejectTask(t);
		assertEquals(Status.REJECTED, t.getStatus());
	}

	@Test
	public void testSuspendTask() {
		final Task t = service.addTask("t1");
		
		service.suspendTask(t);
		assertEquals(Status.PENDING, t.getStatus());
	}

	@Test
	public void testGetTodayTasks() {
		final Task t1 = service.addTask("t1");
		service.approveTask(t1);
		@SuppressWarnings("unused")
		final Task t2 = service.addTask("t2");
		final Task t3 = service.addTask("t3");
		service.approveTask(t3);
		
		List<Task> result = service.getTodayTasks();
		assertNotNull(result);
		assertEquals(2, result.size());
	}
		
	@Test
	public void testGetStatus() {
		final Task t1 = service.addTask("t1");
		service.approveTask(t1);
		
		List<Task> result = service.getTodayTasks();
		assertNotNull(result);
		assertEquals(1, result.size());
		Task rTask = result.get(0);
		assertNotNull(rTask);
		assertNotNull(rTask.getStatus());
		assertTrue(rTask.getStatus()==Status.APPROVAL);
	}
}
