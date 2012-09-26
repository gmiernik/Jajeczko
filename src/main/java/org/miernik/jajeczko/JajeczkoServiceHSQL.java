package org.miernik.jajeczko;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.miernik.jajeczko.model.Status;
import org.miernik.jajeczko.model.Task;
import org.miernik.jajeczko.presenter.NewTaskPresenter;

public class JajeczkoServiceHSQL extends JajeczkoServiceBase {

	final static Logger logger = Logger.getLogger(JajeczkoServiceHSQL.class);

	private EntityManager entityManager;
	private boolean initiated = false;
	private Status statusOpen = Status.OPEN;
	private Status statusApproval = Status.APPROVAL;

	private void init() {
		logger.debug("run init() methid");
		if (!initiated) {
			entityManager.getTransaction().begin();
			if (entityManager.find(Status.class, statusOpen.getId()) != null)
				entityManager.merge(statusOpen);
			else
				entityManager.persist(statusOpen);
			if (entityManager.find(Status.class, statusApproval.getId()) != null)
				entityManager.merge(statusApproval);
			else
				entityManager.persist(statusApproval);
			entityManager.getTransaction().commit();
			initiated = true;
		}
	}

	@Override
	public void dispose() {
		entityManager.close();
		super.dispose();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Task> getTodayTasks() {
		Query q = entityManager.createNamedQuery("TodayTasks");
		@SuppressWarnings("unchecked")
		List<Task> tasks = q.getResultList();
		return tasks;
	}

	@Override
	public Task addTask(String name) {
		logger.debug("run addTask()");
		if (!initiated)
			init();
		Task t = new Task(name);
		t.setStatus(statusOpen);
		logger.debug("begin transaction");
		entityManager.getTransaction().begin();
		entityManager.persist(t);
		entityManager.getTransaction().commit();
		return t;
	}

	protected void updateTask(Task t) {
		if (!initiated)
			init();
		entityManager.getTransaction().begin();
		entityManager.merge(t);
		entityManager.getTransaction().commit();
	}

	public void approvalTask(Task t) {
		t.setStatus(statusApproval);
		updateTask(t);
	}
}
