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

public class JajeczkoServiceHSQLTest {

	private JajeczkoService service;
	private EntityManagerFactory emf;

	@Before
	public void setUp() throws Exception {
		emf = Persistence.createEntityManagerFactory("jajeczko-test");
		JajeczkoServiceHSQL js = new JajeczkoServiceHSQL();
		js.setEntityManager(emf.createEntityManager());
		service = js;
	}

	@After
	public void tearDown() throws Exception {
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
	public void testGetTodayTasks() {
		final Task t1 = service.addTask("t1");
		service.approvalTask(t1);
		final Task t2 = service.addTask("t2");
		final Task t3 = service.addTask("t3");
		service.approvalTask(t3);
		
		List<Task> result = service.getTodayTasks();
		assertNotNull(result);
		assertEquals(2, result.size());
	}
	
}
