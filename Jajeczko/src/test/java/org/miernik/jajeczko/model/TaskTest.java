package org.miernik.jajeczko.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TaskTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEquals1() {
		final String name = "test_123";
		final int id = 123;
		final Task t1 = new Task(name);
		t1.setId(id);
		final Task t2 = new Task(name);
		final Task t3 = new Task(name);
		t3.setId(id);
		final Task t4 = new Task(name);
		t4.setId(id+1);
		final Task t5 = new Task();
		t5.setId(id);
		
		assertTrue(t1.equals(t2));
		assertTrue(t1.equals(t3));
		assertTrue(t2.equals(t1));
		assertFalse(t1.equals(t4));
		assertFalse(t1.equals(t5));
	}

}
