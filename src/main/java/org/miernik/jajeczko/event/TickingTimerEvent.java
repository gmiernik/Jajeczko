package org.miernik.jajeczko.event;

import java.util.Date;

import org.miernik.jfxlib.event.Event;

public class TickingTimerEvent implements Event {
	private Date time;
	
	public Date getTime() {
		return time;
	}
	
	public TickingTimerEvent(Date time) {
		this.time = time;
	}

}
