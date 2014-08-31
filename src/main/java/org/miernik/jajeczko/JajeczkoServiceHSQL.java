package org.miernik.jajeczko;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.miernik.jajeczko.event.UpdateTaskEvent;
import org.miernik.jajeczko.model.Status;
import org.miernik.jajeczko.model.Task;
import org.miernik.jfxlib.event.EventBus;

public class JajeczkoServiceHSQL extends JajeczkoServiceBase {

	public JajeczkoServiceHSQL(EventBus eb) {
		super(eb);
	}

	final static Logger logger = Logger.getLogger(JajeczkoServiceHSQL.class);

	private EntityManager entityManager;
	private boolean initiated = false;
	private Status statusOpen;
	private Status statusApproval;
	private Status statusInProgress;
	private Status statusDone;
	private Status statusRejected;
	private Status statusPending;
	private Status statusSomeday;

	private void init() {
		logger.debug("run init() methid");
		if (!initiated) {
			entityManager.getTransaction().begin();
			statusOpen = persistStatus(Status.OPEN);
			statusApproval = persistStatus(Status.APPROVAL);
			statusInProgress = persistStatus(Status.IN_PROGRESS);
			statusDone = persistStatus(Status.DONE);
			statusRejected = persistStatus(Status.REJECTED);
			statusPending = persistStatus(Status.PENDING);
			statusSomeday = persistStatus(Status.SOMEDAY);
			entityManager.getTransaction().commit();
			initiated = true;
		}
	}

	private Status persistStatus(Status s) {
		Status result = entityManager.find(Status.class, s.getId());
		if (result != null) {
			return entityManager.merge(s);
		} else {
			entityManager.persist(s);
			return s;
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
		if (!initiated)
			init();
		Query q = entityManager.createNamedQuery("TodayTasks");
		@SuppressWarnings("unchecked")
		List<Task> tasks = q.getResultList();
		return tasks;
	}

	@Override
	public Task addTask(String name) throws AppException {
		logger.debug("run addTask()");
		if (!initiated)
			init();
		if (taskExists(name)) 
			throw new AppException("Cannot add duplicate task name: " + name);
		Task t = new Task(name);
		t.setStatus(statusOpen);
		if (entityManager.contains(statusOpen))
			logger.info("persistent context include OPEN status");
		logger.debug("begin transaction");
		entityManager.getTransaction().begin();
		entityManager.persist(t);
		entityManager.getTransaction().commit();
		logger.debug("commit transaction");
		return t;
	}

	protected boolean taskExists(String name) {
		logger.debug("run taskExists()");
		if (!initiated)
			init();
		Query q = entityManager.createNamedQuery("TaskByName");
		q.setParameter("name", name);
		@SuppressWarnings("unchecked")
		List<Task> tasks = q.getResultList();
		return tasks.size() != 0;
	}

	protected void updateTask(Task t) {
		if (!initiated)
			init();
		entityManager.getTransaction().begin();
		entityManager.persist(t);
		entityManager.getTransaction().commit();
		logger.debug("fire: UpdateTaskEvent, " + t);
		getEventBus().fireEvent(new UpdateTaskEvent(t));
	}

	public void approveTask(Task t) {
		t.setStatus(statusApproval);
		updateTask(t);
	}

	@Override
	public void completeTask(Task task) {
		task.setStatus(statusDone);
		updateTask(task);
	}

	@Override
	public void rejectTask(Task task) {
		task.setStatus(statusRejected);
		updateTask(task);
	}

	@Override
	public void suspendTask(Task task) {
		task.setStatus(statusPending);
		updateTask(task);
	}

	@Override
	public void startWorking(Task task) {
		task.setStatus(statusInProgress);
		updateTask(task);
	}

	@Override
	public void postponeTask(Task task) {
		task.setStatus(statusSomeday);
		updateTask(task);
	}

	@Override
	public List<Task> getOpenedTasks() {
		if (!initiated)
			init();
		Query q = entityManager.createNamedQuery("OpenedTasks");
		@SuppressWarnings("unchecked")
		List<Task> tasks = q.getResultList();
		return tasks;
	}

}
