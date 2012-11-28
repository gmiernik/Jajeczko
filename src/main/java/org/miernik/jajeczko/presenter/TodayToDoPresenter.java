/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jajeczko.presenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import org.apache.log4j.Logger;
import org.miernik.jajeczko.JajeczkoService;
import org.miernik.jajeczko.event.AddTaskEvent;
import org.miernik.jajeczko.event.UpdateTaskEvent;
import org.miernik.jajeczko.model.Status;
import org.miernik.jajeczko.model.Task;
import org.miernik.jfxlib.dialogs.MessageBox;
import org.miernik.jfxlib.event.EventListener;
import org.miernik.jfxlib.presenter.BasePresenter;

/**
 * 
 * @author Miernik
 */
public class TodayToDoPresenter extends BasePresenter<JajeczkoService> {

	final static Logger logger = Logger.getLogger(TodayToDoPresenter.class);

	@FXML
	private Pane todayToDo;
	@FXML
	private TableView<Task> taskTable;
	@FXML
	private Button addButton;
	@FXML
	private Button startButton;
	@FXML
	private Button doneButton;
	@FXML
	private Button rejectButton;

	@Override
	public void onShow() {
		logger.debug("run onShow() method");
		ObservableList<Task> tasks = FXCollections.observableList(getService()
				.getTodayTasks());
		taskTable.setItems(tasks);
	}

	private Task getSelectedTask() {
		return taskTable.getSelectionModel().getSelectedItem();
	}

	@Override
	public void onInit() {
		getEventBus().addListener(new EventListener<AddTaskEvent>() {

			@Override
			public void performed(AddTaskEvent arg0) {
				taskTable.getItems().add(arg0.getTask());
			}
		});
		getEventBus().addListener(new EventListener<UpdateTaskEvent>() {

			@Override
			public void performed(UpdateTaskEvent event) {
				Task task = event.getTask();
				if (!task.getStatus().equals(Status.APPROVAL) && !task.getStatus().equals(Status.IN_PROGRESS)) {
					taskTable.getItems().remove(task);
				}
			}
		});
		todayToDo.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent arg0) {
				if (arg0.getCode() == KeyCode.INSERT) {
					fireAction("NewTask");
				}
			}
		});
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Task t = taskTable.getSelectionModel().getSelectedItem();
				if (t == null)
					(new MessageBox(
							getResource().getString("StartTimerWindow"),
							getResource().getString("ChooseTaskToWork")))
							.show();
				else {
					getService().getTimer().startWork(t);
					getWindow().hide();
				}
			}
		});
		addButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				fireAction("NewTask");
			}
		});
		doneButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				final Task t = getSelectedTask();
				logger.info("status of selected task: "+t.getStatus());
				logger.info("status in progress: "+Status.IN_PROGRESS);
				logger.info(t.getStatus().equals(Status.IN_PROGRESS));
				if (t.getStatus().equals(Status.IN_PROGRESS)) {
					AcceptanceDialog d = new AcceptanceDialog(getResource()
							.getString("CompleteTaskWindow"), getResource()
							.getString("CompleteTaskQuestion") + t.getName());
					d.setDialogResultHandler(new DialogResultHandler() {

						@Override
						public void handle(DialogResult result) {
							if (result == DialogResult.Yes)
								getService().completeTask(t);
						}
					});
					d.show();
				} else {
					new MessageBox(getResource()
							.getString("CompleteTaskWindow"), getResource()
							.getString("CompleteTaskWithoutWorking")).show();
				}
			}
		});
		rejectButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent ae) {
				final Task t = getSelectedTask();
				AcceptanceDialog d = new AcceptanceDialog(getResource()
						.getString("RejectTaskWindow"), getResource()
						.getString("RejectTaskQuestion") + t.getName());
				d.setDialogResultHandler(new DialogResultHandler() {

					@Override
					public void handle(DialogResult result) {
						if (result == DialogResult.Yes)
							getService().rejectTask(t);
					}
				});
				d.show();
			}
		});
		
	}

}
