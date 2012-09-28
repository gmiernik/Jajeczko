package org.miernik.jajeczko.event;

import org.miernik.jajeczko.model.Task;
import org.miernik.jfxlib.event.Event;

public abstract class BaseEvent implements Event {
	private Task task;

	public Task getTask() {
		return task;
	}

	public BaseEvent(Task t) {
		this.task = t;
	}
}
