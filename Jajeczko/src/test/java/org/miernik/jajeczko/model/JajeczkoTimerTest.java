package org.miernik.jajeczko.model;

import static org.junit.Assert.*;

import java.util.Date;

import javafx.scene.layout.AnchorPane;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.miernik.jajeczko.presenter.TimerPresenter;

public class JajeczkoTimerTest extends TestCase {

	private JajeczkoTimer timer;

	class TestResult {
		int value = 0;
	}

	@Before
	public void setUp() throws Exception {
		timer = new JajeczkoTimer();
	}

	@After
	public void tearDown() throws Exception {
		timer = null;
	}

	@Test
	public void testStartWork() {
		final int numSeconds_25minutes = 25 * 60;
		final TimerStatus status = TimerStatus.WorkingTime;
		final Task t = new Task("Test1");

		timer.startWork(t);

		assertEquals(numSeconds_25minutes, timer.getTimeCounter());
		assertEquals(status, timer.getStatus());
	}

	@Test
	public void testStop() {
		final int numSeconds = 10;
		final TimerStatus status = TimerStatus.ShortBreak;

		timer.runTimer(status, numSeconds);
		assertEquals(status, timer.getStatus());

		timer.stop();
		assertEquals(TimerStatus.DoNothink, timer.getStatus());
		assertEquals(0, timer.getTimeCounter());
	}

	@Test
	public void testStopStart() {
		final int numSeconds = 10;
		final Task t = new Task();

		timer.startWork(t);
		assertEquals(TimerStatus.WorkingTime, timer.getStatus());

		timer.stop();
		assertEquals(TimerStatus.DoNothink, timer.getStatus());

		timer.startWork(t);
		assertEquals(TimerStatus.WorkingTime, timer.getStatus());

		timer.stop();
		assertEquals(TimerStatus.DoNothink, timer.getStatus());
	}

	@Test
	public void testRunTimer() throws InterruptedException {
		final int numSeconds = 2;
		final TimerStatus status = TimerStatus.ShortBreak;

		assertEquals(TimerStatus.DoNothink, timer.getStatus());

		timer.runTimer(status, numSeconds);

		assertEquals(numSeconds, timer.getTimeCounter());
		assertEquals(status, timer.getStatus());

		Thread.sleep(numSeconds * 1000 + 100);

		assertEquals(0, timer.getTimeCounter());
		assertEquals(TimerStatus.DoNothink, timer.getStatus());
	}

	@Test
	public void testGetCurrentTime() {
		final int numSeconds = 2;
		final Date time = new Date(numSeconds * 1000);

		timer.runTimer(TimerStatus.ShortBreak, numSeconds);

		assertEquals(time, timer.getCurrentTime());
	}
	
	@Test
	public void testIsRunning() {
		final int numSeconds = 2;

		timer.runTimer(TimerStatus.ShortBreak, numSeconds);
		assertTrue(timer.isRunning());
		
		timer.stop();
		assertFalse(timer.isRunning());
	}

	
	
	@Test
	public void testSetOnFinishedWork() throws InterruptedException {
		final int numSeconds = 1;
		final TestResult result = new TestResult();
		
		assertEquals(0, result.value);
		timer.setOnFinishWork(new TimerHandler() {
			
			@Override
			public void handle() {
				result.value = 1;
			}
		});
		timer.runTimer(TimerStatus.WorkingTime, numSeconds);
		
		Thread.sleep(1050);
		
		assertEquals(1, result.value);
	}
	
	

}
