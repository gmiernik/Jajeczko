package org.miernik.jajeczko.model;

import static org.junit.Assert.*;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TaskTest {

	private EntityManagerFactory emf;

	@Before
	public void setUp() throws Exception {
		emf = Persistence.createEntityManagerFactory("jajeczko-test");
	}

	@After
	public void tearDown() throws Exception {
		emf.close();
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
		t4.setId(id + 1);
		final Task t5 = new Task();
		t5.setId(id);

		assertTrue(t1.equals(t2));
		assertTrue(t1.equals(t3));
		assertTrue(t2.equals(t1));
		assertFalse(t1.equals(t4));
		assertFalse(t1.equals(t5));
	}

	@Test
	public void testPersist() {
		final Task t = new Task();
		t.setName("Test123");
		t.setStatus(Status.OPEN);
		Category c = new Category("TestCategory");
		t.setCategory(c);
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(Status.OPEN);
		em.persist(c);
		em.persist(t);
		em.getTransaction().commit();
		Task result = em.find(t.getClass(), t.getId());
		em.close();
		assertNotNull(result);
		assertEquals(t.getId(), result.getId());
		assertEquals(t.getName(), result.getName());
		assertEquals(Status.OPEN, result.getStatus());
		assertEquals(c, result.getCategory());
	}

	@Test
	public void testPersistId() {
		final Task t = new Task("Test123");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(t);
		em.getTransaction().commit();
		em.close();
		assertTrue(t.getId() > 0);
	}

	@Test
	public void testPersistName() {
		final String name = "test123";
		final Task t1 = new Task(name);
		final Task t2 = new Task(name);

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(t1);
		try {
			em.persist(t2);
			em.getTransaction().commit();
			fail("cannot allow save duplicate task name");
		} catch (PersistenceException ex) {
		} finally {
			em.close();
		}
	}
}
