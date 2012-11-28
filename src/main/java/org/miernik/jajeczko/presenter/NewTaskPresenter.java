/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jajeczko.presenter;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.log4j.Logger;
import org.miernik.jajeczko.JajeczkoService;
import org.miernik.jajeczko.event.AddTaskEvent;
import org.miernik.jajeczko.model.Task;
import org.miernik.jfxlib.presenter.ModalWindowPresenter;

/**
 * 
 * @author Miernik
 */
public class NewTaskPresenter extends ModalWindowPresenter<JajeczkoService> {

	final static Logger logger = Logger.getLogger(NewTaskPresenter.class);
	
	/**
	 * define minimum length of task name to validation
	 */
	static byte minTaskNameLength = 3;
	@FXML
	private Button cancelButton;
	@FXML
	private TextField taskNameField;
	@FXML
	private Button addButton;
	@FXML
	private Label minLengthInfo;

	@Override
	public void onInit() {
		getStage().setTitle(getResource().getString("WindowTitle"));
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				getStage().close();
			}
		});
		addButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				addTask();
			}
		});
		taskNameField.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent arg0) {
				if (arg0.getCode()==KeyCode.ENTER)
					addTask();
			}
		});	
	}

	public void addTask() {
		if (taskNameField.getText().length() < minTaskNameLength)
			minLengthInfo.setVisible(true);
		else {
			final javafx.concurrent.Task<Void> t = new javafx.concurrent.Task<Void>() {
				
				@Override
				protected Void call() throws Exception {
					Task task = getService().addTask(taskNameField.getText());
					//FIXME: temporary approve automatically until prepare 'waiting tasks' list
					getService().approveTask(task);
					getEventBus().fireEvent(new AddTaskEvent(task));
					return null;
				}
			}; 
			t.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
				
				@Override
				public void handle(WorkerStateEvent arg0) {
					getStage().close();
				}
			});
			t.setOnFailed(new EventHandler<WorkerStateEvent>() {
				
				@Override
				public void handle(WorkerStateEvent arg0) {
					logger.error(t.getException(), t.getException());
				}
			});
			Thread th = new Thread(t);
			th.setDaemon(true);
			th.start();
		}
	}

	@Override
	public void onShow() {
		taskNameField.clear();
		minLengthInfo.setVisible(false);
		taskNameField.requestFocus();
	}

}
