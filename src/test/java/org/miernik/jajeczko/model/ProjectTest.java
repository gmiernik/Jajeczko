package org.miernik.jajeczko.model;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProjectTest {

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
		Project p = new Project("test123");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(p);
		em.getTransaction().commit();
		Project result = em.find(p.getClass(), p.getId());
		em.close();
		assertNotNull(result);
		assertEquals(p.getId(), result.getId());
		assertEquals(p.getName(), result.getName());
	}

	@Test
	public void testPersistId() {
		final Project p = new Project("test123");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(p);
		em.getTransaction().commit();
		em.close();
		assertTrue(p.getId() > 0);
	}

	@Test
	public void testPersistName() {
		final String name = "test123";
		final Project p1 = new Project(name);
		final Project p2 = new Project(name);

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(p1);
		try {
			em.persist(p2);
			em.getTransaction().commit();
			fail("cannot allow save duplicate project name");
		} catch (PersistenceException ex) {
		} finally {
			em.close();
		}
	}

	@Test
	public void testPersistNameNotNullable() {
		final Project p = new Project();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		try {
			em.persist(p);
			em.getTransaction().commit();
			fail("cannot save empty project name");
		} catch (RollbackException ex) {
		} finally {
			em.close();
		}
	}

	@Test
	public void testSetName() {
		final Project p = new Project();
		final String name = "test321";
		final String emptyName = new String();

		p.setName(name);
		assertEquals(name, p.getName());

		try {
			p.setName(null);
			p.setName(emptyName);
			fail("cannot set null or empty value to project name");
		} catch (IllegalArgumentException ex) {
		}
	}

}
