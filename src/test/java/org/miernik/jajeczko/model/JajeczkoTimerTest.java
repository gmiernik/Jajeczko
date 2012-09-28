package org.miernik.jajeczko.model;

import java.util.Date;

import javafx.embed.swing.JFXPanel;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.miernik.jajeczko.event.CancelWorkEvent;
import org.miernik.jajeczko.event.FinishWorkEvent;
import org.miernik.jajeczko.event.StartWorkEvent;
import org.miernik.jfxlib.event.EventBus;
import org.miernik.jfxlib.event.EventListener;
import org.miernik.jfxlib.event.SimpleEventBus;

public class JajeczkoTimerTest extends TestCase {

	private JajeczkoTimer timer;
	private EventBus eventBus;

	class TestResult {
		int value = 0;
	}

	@Before
	public void setUp() throws Exception {
		eventBus = new SimpleEventBus();
		timer = new JajeczkoTimer(eventBus);
		timer.getTickingClip().setVolume(0);
		new JFXPanel();
	}

	@After
	public void tearDown() throws Exception {
		timer = null;
		eventBus = null;
	}

	@Test
	public void testStartWork() {
		final int numSeconds_25minutes = 25 * 60;
		final TimerStatus status = TimerStatus.WorkingTime;
		final Task t = new Task("Test1");

		timer.startWork(t);

		assertTrue(numSeconds_25minutes == timer.getTimeCounter()
				|| numSeconds_25minutes - 1 == timer.getTimeCounter());
		assertEquals(status, timer.getStatus());
	}

	@Test
	public void testStop() throws InterruptedException {
		final int numSeconds = 10;
		final TimerStatus status = TimerStatus.ShortBreak;

		timer.runTimer(status, numSeconds);
		assertEquals(status, timer.getStatus());

		timer.cancel();
		Thread.sleep(100);
		assertEquals(TimerStatus.DoNothink, timer.getStatus());
		assertEquals(0, timer.getTimeCounter());
	}
	
	@Test
	public void testStopStart() {
		final Task t = new Task();

		timer.startWork(t);
		assertEquals(TimerStatus.WorkingTime, timer.getStatus());

		timer.cancel();
		assertEquals(TimerStatus.DoNothink, timer.getStatus());

		timer.startWork(t);
		assertEquals(TimerStatus.WorkingTime, timer.getStatus());

		timer.cancel();
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

		timer.cancel();
		assertFalse(timer.isRunning());
	}

	@Test
	public void testDispose() {
		timer.dispose();

		assertNull(timer.getStatus());
	}

	@Test
	public void testFinishWorkEvent() throws InterruptedException {
		final int numSeconds = 1;
		final Task t = new Task();
		final TestResult result = new TestResult();

		assertEquals(0, result.value);
		eventBus.addListener(new EventListener<FinishWorkEvent>() {

			@Override
			public void performed(FinishWorkEvent arg) {
				if (t==arg.getTask())
					result.value = 1;
			}
		});
		timer.startWork(t, numSeconds);
		Thread.sleep(1050);
		assertEquals(1, result.value);
	}

	@Test
	public void testStartWorkEvent() throws InterruptedException {
		final int numSeconds = 1;
		final Task t = new Task();
		final TestResult result = new TestResult();

		assertEquals(0, result.value);
		eventBus.addListener(new EventListener<StartWorkEvent>() {

			@Override
			public void performed(StartWorkEvent arg) {
				if (t==arg.getTask())
					result.value = 1;
			}
		});
		timer.startWork(t, numSeconds);
		Thread.sleep(100);
		assertEquals(1, result.value);
	}

	@Test
	public void testCancelWorkEvent() throws InterruptedException {
		final int numSeconds = 1;
		final Task t = new Task();
		final TestResult result = new TestResult();

		assertEquals(0, result.value);
		eventBus.addListener(new EventListener<CancelWorkEvent>() {

			@Override
			public void performed(CancelWorkEvent arg) {
				if (t==arg.getTask())
					result.value = 1;
			}
		});
		timer.startWork(t, numSeconds);
		timer.cancel();
		Thread.sleep(100);
		assertEquals(1, result.value);
	}
	
	
	@Test
	public void testPlayClockTicking() throws InterruptedException {

		timer.playTicking();
		assertTrue(timer.getTickingClip().isPlaying());
	}
	
	@Test
	public void testGetStartTime() {
		final int numSeconds = 1;
		final Task t = new Task();

		timer.startWork(t, numSeconds);
		Date presentTime = new Date();
		assertNotNull(timer.getStartTime());
		assertEquals(presentTime.getTime(), timer.getStartTime().getTime());
	}

	@Test
	public void testGetStopTime() {
		final int numSeconds = 1;
		final Task t = new Task();

		timer.startWork(t, numSeconds);
		timer.cancel();
		Date presentTime = new Date();
		assertNotNull(timer.getStopTime());
		assertEquals(presentTime.getTime(), timer.getStopTime().getTime());
	}	
}
