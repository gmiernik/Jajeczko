package org.miernik.jajeczko.model;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.miernik.jajeczko.event.CancelWorkEvent;
import org.miernik.jajeczko.event.FinishWorkEvent;
import org.miernik.jajeczko.event.StartWorkEvent;
import org.miernik.jajeczko.event.TickingTimerEvent;
import org.miernik.jfxlib.event.Event;
import org.miernik.jfxlib.event.EventBus;

import javafx.application.Platform;
import javafx.scene.media.AudioClip;

public class JajeczkoTimer {

	static int NUM_SECONDS_SHORT_BREAK = 300;
	static int NUM_SECONDS_WORKING_TIME = 1500;

	private Timer timer;
	private int timeCounter;
	private TimerStatus status;
	private Task task;
	private TimerTask timerTask;
	private AudioClip tickingClip;
	private Date startTime;
	private Date stopTime;
	private EventBus eventBus;
	
	public Date getStartTime() {
		return startTime;
	}
	
	public Date getStopTime() {
		return stopTime;
	}
	
	protected AudioClip getTickingClip() {
		return tickingClip;
	}
	
	protected void fireEvent(final Event event) {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				eventBus.fireEvent(event);
			}
		});
	}

	public JajeczkoTimer(EventBus eb) {
		eventBus = eb;
		status = TimerStatus.DoNothink;
		timer = new Timer();
		tickingClip = new AudioClip(getClass().getResource("/audio/clock_ticking.mp3").toString());
	}

	public Task getTask() {
		return task;
	}
	
	protected void startWork(Task t, int numberOfSeconds) {
		this.task = t;
		runTimer(TimerStatus.WorkingTime, numberOfSeconds);
		fireEvent(new StartWorkEvent(t));
	}
	
	public void startWork(Task t) {
		startWork(t, NUM_SECONDS_WORKING_TIME);
	}

	public void startShortBreak() {

	}

	public void startLongBreak() {

	}

	public void iterrupt() {

	}
	
	protected void stopTimer() {
		timerTask.cancel();
		timerTask = null;
		status = TimerStatus.DoNothink;
		timeCounter = 0;
		stopTime = new Date();		
	}
	
	/**
	 * Stop timer on demand and cancel work/break time
	 */
	public void cancel() {
		if (status == TimerStatus.DoNothink)
			throw new IllegalStateException(
					"Timer is not running. Cannot cancel it");
		stopTimer();
		fireEvent(new CancelWorkEvent(getTask()));
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
	
	protected void playTicking() {
		tickingClip.play();
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
				playTicking();
				timeCounter--;
				fireEvent(new TickingTimerEvent(getCurrentTime()));
				if (timeCounter == 0) {
					TimerStatus lastStatus = status;
					stopTimer();
					switch (lastStatus) {
					case WorkingTime:
						fireEvent(new FinishWorkEvent(getTask()));
						break;
					default:
						break;
					}
				}
			}
		};
		timeCounter = seconds;
		status = newStatus;
		timer.scheduleAtFixedRate(timerTask, 0, 1000);
		startTime = new Date();
	}

	public TimerStatus getStatus() {
		return status;
	}

	public void dispose() {
		if (status!=TimerStatus.DoNothink)
			stopTimer();
		timer.cancel();
		timer = null;
		status = null;
	}
}
