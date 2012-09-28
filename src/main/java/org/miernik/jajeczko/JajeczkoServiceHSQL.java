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
	private Status statusOpen = Status.OPEN;
	private Status statusApproval = Status.APPROVAL;
	private Status statusInProgress = Status.IN_PROGRESS;
	private Status statusDone = Status.DONE;
	private Status statusRejected = Status.REJECTED;
	private Status statusPending = Status.PENDING;
	private Status statusSomeday = Status.SOMEDAY;

	private void init() {
		logger.debug("run init() methid");
		if (!initiated) {
			entityManager.getTransaction().begin();
			persistStatus(statusOpen);
			persistStatus(statusApproval);
			persistStatus(statusInProgress);
			persistStatus(statusDone);
			persistStatus(statusRejected);
			persistStatus(statusPending);
			persistStatus(statusSomeday);
			entityManager.getTransaction().commit();
			initiated = true;
		}
	}
	
	private void persistStatus(Status s) {
		if (entityManager.find(Status.class, s.getId()) != null)
			entityManager.merge(s);
		else
			entityManager.persist(s);
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
		entityManager.persist(t);
		entityManager.getTransaction().commit();
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
	public void postponeTask(Task task) {
		// TODO Auto-generated method stub
		
	}

}
