package org.miernik.jajeczko.event;

import org.miernik.jajeczko.model.Task;

public class UpdateTaskEvent extends BaseEvent {

	public UpdateTaskEvent(Task t) {
		super(t);
	}

}
