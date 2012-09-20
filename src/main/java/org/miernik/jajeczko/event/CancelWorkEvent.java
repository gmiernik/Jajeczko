package org.miernik.jajeczko.event;

import org.miernik.jajeczko.model.Task;
import org.miernik.jfxlib.event.Event;

public class CancelWorkEvent implements Event {
	private Task task;

	public Task getTask() {
		return task;
	}

	public CancelWorkEvent(Task t) {
		this.task = t;
	}
}
