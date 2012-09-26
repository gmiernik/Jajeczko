/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jajeczko.presenter;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.log4j.Logger;
import org.miernik.jajeczko.App;
import org.miernik.jajeczko.JajeczkoService;
import org.miernik.jfxlib.presenter.ModalWindowPresenter;

/**
 * 
 * @author Miernik
 */
public class NewTaskPresenter extends ModalWindowPresenter<JajeczkoService>
		implements Initializable {

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

	public NewTaskPresenter() {
		// TODO: set window title
		// TODO: add functionality to read window title from resource bundle
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
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
			final Task<Void> t = new Task<Void>() {

				@Override
				protected Void call() {
					getService().addTask(taskNameField.getText());
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
					logger.error(t.getException());
				}
			});
			Thread th = new Thread(t);
			th.setDaemon(true);
			th.start();
		}
	}

	@Override
	public void show() {
		taskNameField.clear();
		minLengthInfo.setVisible(false);
		super.show();
	}

}
