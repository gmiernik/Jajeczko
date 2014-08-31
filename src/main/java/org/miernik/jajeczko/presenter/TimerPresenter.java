package org.miernik.jajeczko.presenter;

import java.text.SimpleDateFormat;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.WindowEvent;
import org.apache.log4j.Logger;
import org.miernik.jajeczko.JajeczkoService;
import org.miernik.jajeczko.event.CloseTimerEvent;
import org.miernik.jajeczko.event.FinishWorkEvent;
import org.miernik.jajeczko.event.TickingTimerEvent;
import org.miernik.jajeczko.model.JajeczkoTimer;
import org.miernik.jfxlib.event.EventListener;
import org.miernik.jfxlib.presenter.ModalWindowPresenter;

public class TimerPresenter extends ModalWindowPresenter<JajeczkoService> {

	final static Logger logger = Logger.getLogger(TimerPresenter.class);

	@FXML
	private Label timerLabel;
	@FXML
	private Button actionButton;

	@Override
	public void onInit() {
		getScene().getStylesheets().add("styles/stylesheet.css");
		getStage().setTitle(getResource().getString("windowTitle"));
		actionButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (getTimer().isRunning())
					getTimer().cancel();
				else
					getTimer().startWork(getTimer().getTask());
				setActionText();
			}
		});
		getEventBus().addListener(new EventListener<TickingTimerEvent>() {

			@Override
			public void performed(TickingTimerEvent arg) {
				SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
				if (timerLabel != null) {
					timerLabel.setText(sdf.format(getTimer().getCurrentTime()));
				}
			}
		});
		getEventBus().addListener(new EventListener<FinishWorkEvent>() {

			@Override
			public void performed(FinishWorkEvent arg) {
				logger.debug("finish event, close timer window");
				getEventBus().fireEvent(new CloseTimerEvent(arg.getTask()));
				hide();
			}
		});
		getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent arg0) {
				logger.debug("close request, fire CloseTimerEvent");
				getEventBus().fireEvent(
						new CloseTimerEvent(getTimer().getTask()));
				if (getTimer().isRunning())
					getTimer().cancel();
			}
		});
	}

	protected JajeczkoTimer getTimer() {
		return getService().getTimer();
	}
	
	@Override
	protected void onShow() {
		super.onShow();
		setActionText();
	}
	
	private void setActionText() {
		if (getTimer().isRunning())
			actionButton.setText(getResource().getString("stopButton"));
		else
			actionButton.setText(getResource().getString("startButton"));
	}
}
