package org.miernik.jajeczko.model;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class JajeczkoTimer {

	static int NUM_SECONDS_SHORT_BREAK = 300;
	static int NUM_SECONDS_WORKING_TIME = 1500;

	private Timer timer;
	private int timeCounter;
	private TimerStatus status;
	private TimerHandler finishWorkHandler;
	private TimerHandler tickingHandler;
	private Task task;
	private TimerTask timerTask;

	public JajeczkoTimer() {
		status = TimerStatus.DoNothink;
		timer = new Timer();
	}

	public Task getTask() {
		return task;
	}

	public void startWork(Task t) {
		this.task = t;
		runTimer(TimerStatus.WorkingTime, NUM_SECONDS_WORKING_TIME);
	}

	public void startShortBreak() {

	}

	public void startLongBreak() {

	}

	public void iterrupt() {

	}

	public void stop() {
		if (status == TimerStatus.DoNothink)
			throw new IllegalStateException(
					"Timer is not running. Cannot stop it");
		timerTask.cancel();
		timerTask = null;
		status = TimerStatus.DoNothink;
		timeCounter = 0;
	}
	
	public boolean isRunning() {
		return status!=TimerStatus.DoNothink;
	}

	public Date getCurrentTime() {
		return new Date(timeCounter * 1000);
	}

	protected int getTimeCounter() {
		return this.timeCounter;
	}

	protected void runTimer(TimerStatus newStatus, int seconds) {
		if (status != TimerStatus.DoNothink)
			throw new IllegalStateException(
					"Timer is running. Cannot do anythink before stoping or finishing current");
		timerTask = new TimerTask() {
			
			@Override
			public void run() {
				if (status == TimerStatus.DoNothink)
					throw new IllegalStateException(
							"Timer should not have status DO_NOTHINK when is running.");
				if (tickingHandler!=null)
					tickingHandler.handle();
				//TODO: prepare test for setOnTicking method
				timeCounter--;
				if (timeCounter == 0) {
					TimerStatus lastStatus = status;
					stop();
					switch (lastStatus) {
					case WorkingTime:
						if (finishWorkHandler != null)
							finishWorkHandler.handle();
						break;
					}
				}
			}
		};
		timeCounter = seconds;
		status = newStatus;
		timer.scheduleAtFixedRate(timerTask, 0, 1000);
	}

	public TimerStatus getStatus() {
		return status;
	}

	public void setOnFinishWork(TimerHandler handler) {
		finishWorkHandler = handler;
	}
	
	public void setOnTicking(TimerHandler handler) {
		tickingHandler = handler;
	}

}
