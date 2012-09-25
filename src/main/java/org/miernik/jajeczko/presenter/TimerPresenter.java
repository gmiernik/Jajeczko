package org.miernik.jajeczko.presenter;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.WindowEvent;
import org.miernik.jajeczko.JajeczkoService;
import org.miernik.jajeczko.event.CancelWorkEvent;
import org.miernik.jajeczko.event.FinishWorkEvent;
import org.miernik.jajeczko.model.JajeczkoTimer;
import org.miernik.jajeczko.model.Task;
import org.miernik.jajeczko.model.TimerHandler;
import org.miernik.jfxlib.presenter.ModalWindowPresenter;

public class TimerPresenter extends ModalWindowPresenter<JajeczkoService>
		implements Initializable {

	@FXML
	private Label timerLabel;
	private boolean initiated;
	private JajeczkoTimer timer;
	private Task task;
	@FXML
	private Button actionButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		actionButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (timer.isRunning()) {
					timer.stop();
					actionButton.setText("start"); // TODO: replace to use
													// ResourceBundle
				} else {
					timer.startWork(task);
					actionButton.setText("stop");
				}
			}
		});
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
					getEventBus().fireEvent(new CancelWorkEvent(task));
					close();
				}
			});
			timer.setOnTicking(new TimerHandler() {

				@Override
				public void handle() {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
							if (timerLabel != null) {
								timerLabel.setText(sdf.format(timer
										.getCurrentTime()));
							}
						}
					});
				}
			});
			timer.setOnFinishWork(new TimerHandler() {

				@Override
				public void handle() {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							getEventBus().fireEvent(new FinishWorkEvent(task));
							close();
						}
					});
				}
			});
			initiated = true;
		}
	}

	protected void close() {
		if (timer.isRunning())
			timer.stop();
		getStage().close();
	}

	public void start(Task task) {
		this.task = task;
		show();
		timer.startWork(task);
	}
}
