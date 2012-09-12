package org.miernik.jajeczko;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.miernik.jajeczko.model.JajeczkoTimer;
import org.miernik.jajeczko.model.Task;

import junit.framework.TestCase;

public class JajeczkoServiceMemoryTest extends TestCase {

	private JajeczkoService service;
	
	@Before
	protected void setUp() throws Exception {
		service = new JajeczkoServiceMemory();
	}
	
	@After
	protected void tearDown() throws Exception {
		service = null;
	}
	
	@Test
	public void testAddTask() {
		final String name = "test123";

		Task t = service.addTask(name);

		// check
		assertNotNull(t);
		assertEquals(name, t.getName());
		assertTrue(t.getId() > 0);
	}

	@Test
	public void testAddDuplicateTask() {
		final String name = "test123";

		Task t = service.addTask(name);

		// check
		assertNotNull(t);
		assertEquals(name, t.getName());
		assertTrue(t.getId() > 0);

		try {
			service.addTask(name);
			fail("it shouldn't allow add the same task name into list");
		} catch (IllegalArgumentException ex) {
		}
	}
	
	@Test
	public void testGetTimer() {
		
		JajeczkoTimer t1 = service.getTimer();
		assertNotNull(t1);
		JajeczkoTimer t2 = service.getTimer();
		assertNotNull(t2);
		assertEquals(t1, t2);
	}

}
