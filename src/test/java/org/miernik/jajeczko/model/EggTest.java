package org.miernik.jajeczko.model;

import static org.junit.Assert.*;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EggTest {

	private EntityManagerFactory emf;

	@Before
	public void setUp() throws Exception {
		emf = Persistence
				.createEntityManagerFactory("jajeczko-test");
	}

	@After
	public void tearDown() throws Exception {
		emf.close();
	}

	@Test
	public void testPersist() {
		Egg e = new Egg();
		e.setStartTime(new Date());
		e.setInterruptionInside(10);
		e.setInterruptionOutside(5);
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(e);
		em.getTransaction().commit();
		Egg result = em.find(e.getClass(), e.getId());
		em.close();
		assertNotNull(result);
		assertEquals(e.getId(), result.getId());
		assertEquals(e.getInterruptionInside(), result.getInterruptionInside());
		assertEquals(e.getInterruptionOutside(), result.getInterruptionOutside());
		assertEquals(e.getStartTime(), result.getStartTime());
	}

	@Test
	public void testPersistId() {
		final Egg e = new Egg();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(e);
		em.getTransaction().commit();
		em.close();
		assertTrue(e.getId() > 0);
	}
	
}
