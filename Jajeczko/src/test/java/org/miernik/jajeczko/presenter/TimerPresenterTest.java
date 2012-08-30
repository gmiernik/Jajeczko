package org.miernik.jajeczko.presenter;

import java.util.Date;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;

import org.jodah.concurrentunit.junit.ConcurrentTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.miernik.jajeczko.JajeczkoService;
import org.miernik.jajeczko.JajeczkoServiceMemory;
import org.miernik.jajeczko.model.Task;
import org.miernik.jajeczko.model.TimerStatus;
import org.miernik.jfxlib.Service;
import org.miernik.jfxlib.presenter.ModalWindowPresenter;

public class TimerPresenterTest extends ConcurrentTestCase {

	@BeforeClass
	public static void initJFX() throws Exception {
		new JFXPanel();
	}

	private JajeczkoService service;

	@Before
	public void setUp() throws Exception {
		service = new JajeczkoServiceMemory();
	}

	@After
	public void tearDown() throws Exception {
		service = null;
	}

	@Test
	public void testStart() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				final TimerPresenter p = new TimerPresenter();
				final Task t = new Task("Test1");
				p.setView(new AnchorPane());
				p.setService(service);

				p.start(t);

				threadAssertEquals(t, service.getTimer().getTask());
				threadAssertEquals(TimerStatus.WorkingTime, service.getTimer().getStatus());

				resume();
			}
		});
		threadWait(1000);
	}

}
