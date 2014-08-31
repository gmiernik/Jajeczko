package org.miernik.jajeczko;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.miernik.jfxlib.event.Event;
import org.miernik.jfxlib.event.EventBus;
import org.miernik.jfxlib.event.EventListener;
import org.miernik.jfxlib.event.SimpleEventBus;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		assertTrue(true);
	}

	public void testEventBus() {
		EventBus bus = new SimpleEventBus();
		EventListener<Event> l1 = new EventListener<Event>() {

			@Override
			public void performed(Event arg0) {
			}
		};
		final EventListener<TestEvent1> l2 = new EventListener<TestEvent1>() {

			@Override
			public void performed(TestEvent1 arg0) {
			}
		};

		bus.addListener(l1);
		bus.addListener(l2);
		bus.fireEvent(new Event() {
		});
		bus.fireEvent(new TestEvent1());
	}
}
