package org.miernik.jajeczko.event;

import org.miernik.jajeczko.model.Task;

public class AddTaskEvent extends BaseEvent {

	public AddTaskEvent(Task t) {
		super(t);
	}

}
