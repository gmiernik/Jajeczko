package org.miernik.jajeczko.presenter;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.WindowEvent;

import org.miernik.jajeczko.JajeczkoService;
import org.miernik.jajeczko.model.JajeczkoTimer;
import org.miernik.jajeczko.model.Task;
import org.miernik.jajeczko.model.TimerHandler;
import org.miernik.jfxlib.presenter.AbstractPresenter;
import org.miernik.jfxlib.presenter.ModalWindowPresenter;

public class TimerPresenter extends ModalWindowPresenter<JajeczkoService>
		implements Initializable {

	@FXML
	private Label timerLabel;
	private boolean initiated;
	private JajeczkoTimer timer;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	@Override
	public void show() {
		super.show();
		if (!initiated) {
			getStage().getScene().getStylesheets().add("styles/stylesheet.css");
			timer = getService().getTimer();
			getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent arg0) {
					if (timer.isRunning())
						timer.stop();
				}
			});
			timer.setOnTicking(new TimerHandler() {

				@Override
				public void handle() {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							SimpleDateFormat sdf = new SimpleDateFormat(
									"mm:ss");
							timerLabel.setText(sdf.format(timer
									.getCurrentTime()));
						}
					});
				}
			});
			initiated = true;
		}
	}

	public void start(Task task) {
		show();
		timer.startWork(task);
	}
}
