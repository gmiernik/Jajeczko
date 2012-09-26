package org.miernik.jajeczko.model;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StatusTest {

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
	public void testPersist() {
		final Status s = Status.OPEN;
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(s);
		em.getTransaction().commit();
		Status result = em.find(s.getClass(), s.getId());
		em.close();
		assertNotNull(result);
		assertEquals(s.getId(), result.getId());
		assertEquals(s.getName(), result.getName());
	}
	
	@Test
	public void testPersistName() {
		final String name = "test123";
		final Status s1 = new Status();
		final Status s2 = new Status();
		s1.setName(name);
		s2.setName(name);

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(s1);
		try {
			em.persist(s2);
			em.getTransaction().commit();
			fail("cannot allow save duplicate status name");
		} catch (PersistenceException ex) {
		} finally {
			em.close();
		}
	}
	
	@Test
	public void testEquals1() {
		final String name = "test_123";
		final int id = 123;
		final Status s1 = new Status();
		s1.setId(id);
		s1.setName(name);
		final Status s2 = new Status();
		s2.setName(name);
		final Status s3 = new Status();
		s3.setName(name);
		s3.setId(id+1);

		assertTrue(s1.equals(s2));
		assertTrue(s2.equals(s1));
		assertTrue(s2.equals(s3));
		assertFalse(s1.equals(s3));
	}
}
