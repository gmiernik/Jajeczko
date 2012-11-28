package org.miernik.jajeczko;

import org.miernik.jajeczko.event.CancelWorkEvent;
import org.miernik.jajeczko.event.FinishWorkEvent;
import org.miernik.jajeczko.event.StartWorkEvent;
import org.miernik.jajeczko.model.Egg;
import org.miernik.jajeczko.model.JajeczkoTimer;
import org.miernik.jajeczko.model.Task;
import org.miernik.jfxlib.event.EventBus;
import org.miernik.jfxlib.event.EventListener;

public abstract class JajeczkoServiceBase implements JajeczkoService {
	private JajeczkoTimer timer;
	private EventBus eventBus;
	private EventListener<StartWorkEvent> startWorkListener = new EventListener<StartWorkEvent>() {
		
		@Override
		public void performed(StartWorkEvent arg) {
			startWorking(arg.getTask());
		}
	};
	private EventListener<CancelWorkEvent> cancelWorkListener = new EventListener<CancelWorkEvent>() {

		@Override
		public void performed(CancelWorkEvent arg) {
			Egg e = arg.getTask().addEgg(timer.getStartTime(), timer.getStopTime());
			e.setFinished(false);
			updateTask(arg.getTask());
		}
	};
	private EventListener<FinishWorkEvent> finishWorkListener = new EventListener<FinishWorkEvent>() {

		@Override
		public void performed(FinishWorkEvent arg) {
			Egg e = arg.getTask().addEgg(timer.getStartTime(), timer.getStopTime());
			e.setFinished(true);
			updateTask(arg.getTask());
		}
	};
	
	protected abstract void updateTask(Task task);
	
	protected final EventBus getEventBus() {
		return eventBus;
	}

	public JajeczkoServiceBase(EventBus eb) {
		eventBus = eb;
		timer = new JajeczkoTimer(eb);
		eventBus.addListener(startWorkListener);
		eventBus.addListener(cancelWorkListener);
		eventBus.addListener(finishWorkListener);
	}

	@Override
	public JajeczkoTimer getTimer() {
		return timer;
	}

	@Override
	public void dispose() {
		timer.dispose();
	}
}
