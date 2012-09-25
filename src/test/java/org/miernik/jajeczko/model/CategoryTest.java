package org.miernik.jajeczko.model;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CategoryTest {

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
		final Category cat = new Category("Test123");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(cat);
		em.getTransaction().commit();
		Category result = em.find(cat.getClass(), cat.getId());
		em.close();
		assertNotNull(result);
		assertEquals(cat.getId(), result.getId());
		assertEquals(cat.getName(), result.getName());
	}

	@Test
	public void testPersistId() {
		final Category cat = new Category("Test123");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(cat);
		em.getTransaction().commit();
		em.close();
		assertTrue(cat.getId() > 0);
	}

	@Test
	public void testPersistName() {
		final String name = "test123";
		final Category cat1 = new Category(name);
		final Category cat2 = new Category(name);

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(cat1);
		try {
			em.persist(cat2);
			em.getTransaction().commit();
			fail("cannot allow save duplicate category name");
		} catch (PersistenceException ex) {
		} finally {
			em.close();
		}
	}

}
