package org.miernik.jajeczko.model;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.media.AudioClip;

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
	private AudioClip tickingClip;
	
	protected AudioClip getTickingClip() {
		return tickingClip;
	}

	public JajeczkoTimer() {
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
				if (tickingHandler!=null)
					tickingHandler.handle();
				//TODO: prepare test for setOnTicking method
				timeCounter--;
				if (timeCounter == 0) {
					TimerStatus lastStatus = status;
					stop();
					switch (lastStatus) {
					case WorkingTime:
						getTask().addEgg();
						if (finishWorkHandler != null)
							finishWorkHandler.handle();
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

	public void dispose() {
		if (status!=TimerStatus.DoNothink)
			stop();
		timer.cancel();
		timer = null;
		status = null;
		finishWorkHandler = null;
		tickingHandler = null;
	}
}
