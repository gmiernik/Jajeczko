package org.miernik.jajeczko.event;

import org.miernik.jajeczko.model.Task;

public class CloseTimerEvent extends BaseEvent {

	public CloseTimerEvent(Task t) {
		super(t);
	}

}
